package by.epam.jwdparsingxml.validator;

import by.epam.jwdparsingxml.exception.DeviceXMLParsingException;

public interface XMLFileValidator {
	boolean validate(String xmlfilePath) throws DeviceXMLParsingException;
}
