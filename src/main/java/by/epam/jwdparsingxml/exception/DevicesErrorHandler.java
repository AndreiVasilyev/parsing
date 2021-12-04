package by.epam.jwdparsingxml.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

public class DevicesErrorHandler extends DefaultHandler {

	private Logger log = LogManager.getLogger();
	private final String WARN_MESSAGE_TEMPLATE = "{}-{}";

	@Override
	public void warning(SAXParseException e) {
		log.warn(WARN_MESSAGE_TEMPLATE, getLineAddress(e), e.getMessage());
	}

	@Override
	public void error(SAXParseException e) {
		log.warn(WARN_MESSAGE_TEMPLATE, getLineAddress(e), e.getMessage());
	}

	@Override
	public void fatalError(SAXParseException e) {
		log.warn(WARN_MESSAGE_TEMPLATE, getLineAddress(e), e.getMessage());
	}

	private String getLineAddress(SAXParseException e) {
		return e.getLineNumber() + ":" + e.getColumnNumber();
	}
}
