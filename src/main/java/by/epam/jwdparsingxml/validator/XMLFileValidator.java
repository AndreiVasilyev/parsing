package by.epam.jwdparsingxml.validator;

import by.epam.jwdparsingxml.exception.XMLParsingException;

public interface XMLFileValidator {
	boolean validate(String xmlfilePath) throws XMLParsingException;
}
