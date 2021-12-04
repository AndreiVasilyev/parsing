package by.epam.jwdparsingxml.builder.imp;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import by.epam.jwdparsingxml.builder.AbstractDeviceBuilder;
import by.epam.jwdparsingxml.exception.DevicesErrorHandler;
import by.epam.jwdparsingxml.exception.DeviceXMLParsingException;

public class DevicesSAXBuilder extends AbstractDeviceBuilder {

	private static final Logger log = LogManager.getLogger();
	private DeviceHandler handler;
	private XMLReader reader;

	public DevicesSAXBuilder() throws DeviceXMLParsingException {
		handler = new DeviceHandler();		
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser parser = factory.newSAXParser();
			reader = parser.getXMLReader();
			reader.setContentHandler(handler);
			reader.setErrorHandler(new DevicesErrorHandler());
		} catch (ParserConfigurationException e) {
			log.error("Error congiguration SAX XML parser", e);
			throw new DeviceXMLParsingException("Error congiguration SAX XML parser", e);
		} catch (SAXException e) {
			log.error("SAX Parser error when creating SAX Builder", e);
			throw new DeviceXMLParsingException("SAX Parser error when creating SAX Builder", e);
		}
	}

	public void buildDevicesList(String xmlFilePath) throws DeviceXMLParsingException {
		try {
			reader.parse(xmlFilePath);
		} catch (IOException e) {
			log.error("Error reading xml file {}", xmlFilePath, e);
			throw new DeviceXMLParsingException("Error reading xml file " + xmlFilePath, e);
		} catch (SAXException e) {
			log.error("SAX Parser error when parsing file {}", xmlFilePath, e);
			throw new DeviceXMLParsingException("SAX Parser error when parsing file " + xmlFilePath, e);
		}		
		devices = handler.getDevices();
	}

}
