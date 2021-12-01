package by.epam.jwdparsingxml.exception;

public class XMLParsingException extends Exception {

	private static final long serialVersionUID = 1L;

	public XMLParsingException() {
		super();
	}

	public XMLParsingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public XMLParsingException(String message, Throwable cause) {
		super(message, cause);
	}

	public XMLParsingException(String message) {
		super(message);
	}

	public XMLParsingException(Throwable cause) {
		super(cause);
	}
}
