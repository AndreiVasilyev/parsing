package by.epam.jwdparsingxml.builder.imp;

import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import by.epam.jwdparsingxml.builder.AbstractDeviceBuilder;
import by.epam.jwdparsingxml.entity.Device;
import by.epam.jwdparsingxml.exception.DevicesErrorHandler;
import by.epam.jwdparsingxml.exception.XMLParsingException;


public class DevicesSAXBuilder extends AbstractDeviceBuilder{

	private static final Logger log = LogManager.getLogger();	
	private DeviceHandler handler;
	private XMLReader reader;

	public DevicesSAXBuilder() throws XMLParsingException {
		handler = new DeviceHandler();
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser parser = factory.newSAXParser();
			reader = parser.getXMLReader();
			reader.setContentHandler(handler);
			reader.setErrorHandler(new DevicesErrorHandler());
		} catch (ParserConfigurationException e) {
			log.error("Error congiguration SAX XML parser");
			throw new XMLParsingException("Error congiguration SAX XML parser");
		} catch (SAXException e) {
			log.error("SAX Parser error when creating SAX Builder");
			throw new XMLParsingException("SAX Parser error when creating SAX Builder");
		}
	}
	
	public DevicesSAXBuilder(List<Device> devices) {
		super(devices);
	}

	public void buildDevicesList(String xmlFilePath) throws XMLParsingException {
		try {
			reader.parse(xmlFilePath);
		} catch (IOException e) {
			log.error("Error reading xml file {}", xmlFilePath);
			throw new XMLParsingException("Error reading xml file " + xmlFilePath);
		} catch (SAXException e) {
			log.error("SAX Parser error when parsing file {}", xmlFilePath);
			throw new XMLParsingException("SAX Parser error when parsing file " + xmlFilePath);
		}
		devices = handler.getDevices();
	}

}
