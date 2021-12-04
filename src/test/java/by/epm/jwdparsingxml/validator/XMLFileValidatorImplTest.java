package by.epm.jwdparsingxml.validator;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import by.epam.jwdparsingxml.exception.DeviceXMLParsingException;
import by.epam.jwdparsingxml.validator.XMLFileValidator;
import by.epam.jwdparsingxml.validator.impl.XMLFileValidatorImpl;

public class XMLFileValidatorImplTest {

	private XMLFileValidator validator;

	@BeforeClass
	public void setupValidator() throws DeviceXMLParsingException {
		String xsdFile = this.getClass().getResource("/xml/devices.xsd").getFile();
		validator = new XMLFileValidatorImpl(xsdFile);
	}

	@Test
	public void testWithValidFiles() throws DeviceXMLParsingException {
		String xmlFile = this.getClass().getResource("/xml/devices.xml").getFile();
		assertTrue(validator.validate(xmlFile));
	}

	@Test
	public void testWithInvalidXMLFile() throws DeviceXMLParsingException {
		String xmlFile = this.getClass().getResource("/xml/devicesWrong.xml").getFile();
		assertFalse(validator.validate(xmlFile));
	}

	@Test
	public void testWithInvalidXSDFile() throws DeviceXMLParsingException {
		String xsdFile = this.getClass().getResource("/xml/devicesWrong.xsd").getFile();
		XMLFileValidator validator = new XMLFileValidatorImpl(xsdFile);
		String xmlFile = this.getClass().getResource("/xml/devices.xml").getFile();
		assertFalse(validator.validate(xmlFile));
	}

	@Test(expectedExceptions = DeviceXMLParsingException.class, expectedExceptionsMessageRegExp = "Wrong XML file path.*")
	public void testWithNullXMLFile() throws DeviceXMLParsingException {
		validator.validate(null);
	}

	@Test(expectedExceptions = DeviceXMLParsingException.class, expectedExceptionsMessageRegExp = "Wrong XML file path.*")
	public void testWithBlankXMLFile() throws DeviceXMLParsingException {
		validator.validate("");
	}

	@Test(expectedExceptions = DeviceXMLParsingException.class, expectedExceptionsMessageRegExp = "Error when reading files.*")
	public void testWithWrongPathXMLFile() throws DeviceXMLParsingException {
		validator.validate("/devices.xml");
	}

	@Test(expectedExceptions = DeviceXMLParsingException.class, expectedExceptionsMessageRegExp = "Wrong XSD file path.*")
	public void testWithNullXSDFile() throws DeviceXMLParsingException {
		new XMLFileValidatorImpl(null);
	}

	@Test(expectedExceptions = DeviceXMLParsingException.class, expectedExceptionsMessageRegExp = "Wrong XSD file path.*")
	public void testWithBlankXSDFile() throws DeviceXMLParsingException {
		new XMLFileValidatorImpl("");
	}

	@Test(expectedExceptions = DeviceXMLParsingException.class, expectedExceptionsMessageRegExp = "Wrong XSD file path.*")
	public void testWithWrongPathXSDFile() throws DeviceXMLParsingException {
		XMLFileValidator validator = new XMLFileValidatorImpl("/devices.xml");
		String xmlFile = this.getClass().getResource("/xml/devices.xml").getFile();
		validator.validate(xmlFile);
	}

}
