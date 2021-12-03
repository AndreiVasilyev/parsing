package by.epam.jwdparsingxml.builder;

import java.util.ArrayList;
import java.util.List;

import by.epam.jwdparsingxml.entity.Device;
import by.epam.jwdparsingxml.exception.XMLParsingException;

public abstract class AbstractDeviceBuilder {

	protected List<Device> devices;

	public AbstractDeviceBuilder() {
		devices = new ArrayList<>();
	}

	public AbstractDeviceBuilder(List<Device> devices) {
		this.devices = devices;
	}

	public List<Device> getDevices() {
		return devices;
	}

	abstract public void buildDevicesList(String xmlFilePath) throws XMLParsingException;

}
