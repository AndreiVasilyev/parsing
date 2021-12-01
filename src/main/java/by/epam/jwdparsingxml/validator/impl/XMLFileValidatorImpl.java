package by.epam.jwdparsingxml.validator.impl;

import java.io.File;
import java.io.IOException;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;

import by.epam.jwdparsingxml.exception.DevicesErrorHandler;
import by.epam.jwdparsingxml.exception.XMLParsingException;
import by.epam.jwdparsingxml.validator.XMLFileValidator;

public class XMLFileValidatorImpl implements XMLFileValidator {

	private static final Logger log = LogManager.getLogger();
	private final String xsdfilePath;

	public XMLFileValidatorImpl(String xsdfilePath) {
		this.xsdfilePath = xsdfilePath;
	}

	public boolean validate(String xmlfilePath) throws XMLParsingException {
		String language = XMLConstants.W3C_XML_SCHEMA_NS_URI;
		SchemaFactory factory = SchemaFactory.newInstance(language);
		File xsdFile = new File(xsdfilePath);
		DevicesErrorHandler errorHandler = new DevicesErrorHandler();
		try {
			Schema schema = factory.newSchema(xsdFile);
			Validator validator = schema.newValidator();
			Source source = new StreamSource(xmlfilePath);
			validator.setErrorHandler(errorHandler);
			validator.validate(source);
		} catch (IOException e) {
			log.error("Error when reading files(XML:{} XSD{})", xmlfilePath, xsdFile);
			throw new XMLParsingException("", e);
		} catch (SAXException e) {
			return false;
		}
		return true;
	}

}
