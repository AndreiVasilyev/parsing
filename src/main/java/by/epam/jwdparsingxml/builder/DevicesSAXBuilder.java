package by.epam.jwdparsingxml.builder;

import java.io.IOException;
import java.util.List;

import javax.management.modelmbean.XMLParseException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import by.epam.jwdparsingxml.entity.Device;
import by.epam.jwdparsingxml.exception.DevicesErrorHandler;
import by.epam.jwdparsingxml.parser.DeviceHandler;

public class DevicesSAXBuilder {
	private List<Device> devices;
	private DeviceHandler handler;
	private XMLReader reader;

	public DevicesSAXBuilder() throws XMLParseException {
		handler = new DeviceHandler();
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser parser = factory.newSAXParser();
			reader = parser.getXMLReader();
			reader.setContentHandler(handler);
			reader.setErrorHandler(new DevicesErrorHandler());
		} catch (ParserConfigurationException e) {
			throw new XMLParseException("Error congiguration SAX XML parser");
		} catch (SAXException e) {
			throw new XMLParseException("SAX Parser error when creating SAX Builder");
		}
	}

	public List<Device> getDevices() {
		return devices;
	}

	public void buildDevicesList(String xmlFilePath) throws XMLParseException {
		try {
			reader.parse(xmlFilePath);
		} catch (IOException e) {
			throw new XMLParseException("Error reading xml file " + xmlFilePath);
		} catch (SAXException e) {
			throw new XMLParseException("SAX Parser error when parsing file " + xmlFilePath);
		}
		devices = handler.getDevices();
	}

}
