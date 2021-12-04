package by.epam.jwdparsingxml.main;

import by.epam.jwdparsingxml.builder.AbstractDeviceBuilder;

import by.epam.jwdparsingxml.entity.AbstractDevice;
import by.epam.jwdparsingxml.exception.DeviceXMLParsingException;
import by.epam.jwdparsingxml.factory.DeviceBuilderFactory;
import by.epam.jwdparsingxml.validator.XMLFileValidator;
import by.epam.jwdparsingxml.validator.impl.XMLFileValidatorImpl;

public class Main {

	public static void main(String[] args) throws DeviceXMLParsingException {
		String xsd = "/xml/devices.xsd";
		String xml = "/xml/devicesShort.xml";
		xsd = Main.class.getResource(xsd).getFile();
		xml = Main.class.getResource(xml).getFile();

		XMLFileValidator xfv = new XMLFileValidatorImpl(xsd);
		System.out.println(xfv.validate(xml) + "\n");

		DeviceBuilderFactory factory = new DeviceBuilderFactory();

		AbstractDeviceBuilder builderSAX = factory.createDeviceBuilder("sax");
		builderSAX.buildDevicesList(xml);
		for (AbstractDevice device : builderSAX.getDevices()) {
			System.out.println(device);
		}
		System.out.println();

		AbstractDeviceBuilder builderDOM = factory.createDeviceBuilder("dom");
		builderDOM.buildDevicesList(xml);
		for (AbstractDevice device : builderDOM.getDevices()) {
			System.out.println(device);
		}
		System.out.println();

		AbstractDeviceBuilder builderStAX = factory.createDeviceBuilder("stax");
		builderStAX.buildDevicesList(xml);
		for (AbstractDevice device : builderStAX.getDevices()) {
			System.out.println(device);
		}
		System.out.println();
	}

}
