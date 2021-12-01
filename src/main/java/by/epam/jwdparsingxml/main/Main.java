package by.epam.jwdparsingxml.main;

import java.net.URISyntaxException;

import by.epam.jwdparsingxml.builder.DeviceEnum;
import by.epam.jwdparsingxml.exception.XMLParsingException;
import by.epam.jwdparsingxml.validator.XMLFileValidator;
import by.epam.jwdparsingxml.validator.impl.XMLFileValidatorImpl;

public class Main {

	public static void main(String[] args) throws URISyntaxException, XMLParsingException {
		String xsd="/xml/devices.xsd";
		String xml="/xml/devices.xml";
		xsd=Main.class.getResource(xsd).getFile();
		xml=Main.class.getResource(xml).getFile();	
		
		System.out.println(xsd);
		System.out.println(xml);		
		
		XMLFileValidator xfv=new XMLFileValidatorImpl(xsd);
		
		System.out.println(xfv.validate(xml));
		
		System.out.println(DeviceEnum.BASE_INFO.getQName());
		System.out.println(DeviceEnum.STORAGE_DEVICE_TYPE.getQName());
		System.out.println(DeviceEnum.PROCESSOR.getQName());
		System.out.println(DeviceEnum.STORAGE_DEVICE.getQName());
		System.out.println(DeviceEnum.MOTHERBOARD.getQName());
		System.out.println(DeviceEnum.MOUSE.getQName());
		System.out.println(DeviceEnum.DEVICES.getQName());
	}

}
