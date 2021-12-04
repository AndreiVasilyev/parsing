package by.epam.jwdparsingxml.builder;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.jwdparsingxml.entity.AbstractDevice;
import by.epam.jwdparsingxml.exception.DeviceXMLParsingException;
import by.epam.jwdparsingxml.validator.XMLFileValidator;

public abstract class AbstractDeviceBuilder {

	private static final Logger log = LogManager.getLogger();
	protected List<AbstractDevice> devices;

	protected AbstractDeviceBuilder() {
		devices = new ArrayList<>();
	}

	public List<AbstractDevice> getDevices() {
		return devices;
	}

	public void buildDevicesList(String xmlFilePath, XMLFileValidator validator) throws DeviceXMLParsingException {
		if (!validator.validate(xmlFilePath)) {
			log.error("XML file {} not valid", xmlFilePath);
			throw new DeviceXMLParsingException("XML file " + xmlFilePath + " not valid");
		}
		buildDevicesList(xmlFilePath);
	}

	public abstract void buildDevicesList(String xmlFilePath) throws DeviceXMLParsingException;
}
