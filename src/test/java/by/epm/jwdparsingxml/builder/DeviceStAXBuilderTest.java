package by.epm.jwdparsingxml.builder;

import static org.testng.Assert.assertEquals;

import java.util.Comparator;
import java.util.List;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import by.epam.jwdparsingxml.builder.AbstractDeviceBuilder;
import by.epam.jwdparsingxml.entity.AbstractDevice;
import by.epam.jwdparsingxml.exception.DeviceXMLParsingException;
import by.epam.jwdparsingxml.factory.DeviceBuilderFactory;
import by.epam.jwdparsingxml.validator.XMLFileValidator;
import by.epam.jwdparsingxml.validator.impl.XMLFileValidatorImpl;
import by.epm.jwdparsingxml.SourceDataCreator;

public class DeviceStAXBuilderTest {
	
	private AbstractDeviceBuilder builder;
	private XMLFileValidator validator;
	private List<AbstractDevice> expectedList;
	private Comparator<AbstractDevice> comparator;

	@BeforeClass
	public void setupStartValues() throws DeviceXMLParsingException {
		builder = new DeviceBuilderFactory().createDeviceBuilder("stax");
		String xsdFile = this.getClass().getResource("/xml/devices.xsd").getFile();
		validator = new XMLFileValidatorImpl(xsdFile);
		expectedList = SourceDataCreator.EXPECTED_LIST;
		comparator = SourceDataCreator.DEVICE_ID_COMPARATOR;
	}

	@Test
	public void testValidFileWithValidation() throws DeviceXMLParsingException {
		String xmlFile = this.getClass().getResource("/xml/devicesShort.xml").getFile();
		builder.buildDevicesList(xmlFile, validator);
		List<AbstractDevice> actualList = builder.getDevices();
		actualList.sort(comparator);
		assertEquals(actualList, expectedList);
	}

	@Test
	public void testValidFileWithoutValidation() throws DeviceXMLParsingException {
		String xmlFile = this.getClass().getResource("/xml/devicesShort.xml").getFile();
		builder.buildDevicesList(xmlFile);
		List<AbstractDevice> actualList = builder.getDevices();
		actualList.sort(comparator);
		assertEquals(actualList, expectedList);
	}

	@Test(expectedExceptions = DeviceXMLParsingException.class, expectedExceptionsMessageRegExp = "XML file.*not valid")
	public void testWrongFileWithValidation() throws DeviceXMLParsingException {
		String xmlFile = this.getClass().getResource("/xml/devicesWrong.xml").getFile();
		builder.buildDevicesList(xmlFile, validator);
	}

	@Test(expectedExceptions = DeviceXMLParsingException.class, expectedExceptionsMessageRegExp = "SAX Parser error when parsing file.*")
	public void testWrongFileWithoutValidation() throws DeviceXMLParsingException {
		String xmlFile = this.getClass().getResource("/xml/devicesWrong.xml").getFile();
		builder.buildDevicesList(xmlFile);
	}

}
