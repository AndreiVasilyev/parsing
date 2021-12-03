package by.epm.jwdparsingxml.validator;

import static org.testng.Assert.assertTrue;



import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import by.epam.jwdparsingxml.exception.XMLParsingException;
import by.epam.jwdparsingxml.validator.XMLFileValidator;
import by.epam.jwdparsingxml.validator.impl.XMLFileValidatorImpl;

public class XMLFileValidatorImplTest {

	private XMLFileValidator validator;

	@BeforeClass
	public void setupValidator() throws XMLParsingException {
		String xsdFile = this.getClass().getResource("/xml/devices.xsd").getFile();
		validator = new XMLFileValidatorImpl(xsdFile);
	}

	@Test
	public void testWithValidXMLFile() throws XMLParsingException {
		String xmlFile = this.getClass().getResource("/xml/devices.xml").getFile();
		assertTrue(validator.validate(xmlFile));
	}

	@Test(expectedExceptions = XMLParsingException.class, expectedExceptionsMessageRegExp = "Wrong XML file path.*")
	public void testWithNullXMLFile() throws XMLParsingException {
		validator.validate(null);
	}

	@Test(expectedExceptions = XMLParsingException.class, expectedExceptionsMessageRegExp = "Wrong XML file path.*")
	public void testWithBlankXMLFile() throws XMLParsingException {
		validator.validate("");
	}

	
	@Test(expectedExceptions = XMLParsingException.class, expectedExceptionsMessageRegExp = "Error when reading files.*")
	public void testWithWrongPathXMLFile() throws XMLParsingException {		
		validator.validate("/devices.xml");
	}

}
