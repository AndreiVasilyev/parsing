package by.epam.jwdparsingxml.exception;

public class DeviceXMLParsingException extends Exception {

	private static final long serialVersionUID = 1L;

	public DeviceXMLParsingException() {
		super();
	}

	public DeviceXMLParsingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public DeviceXMLParsingException(String message, Throwable cause) {
		super(message, cause);
	}

	public DeviceXMLParsingException(String message) {
		super(message);
	}

	public DeviceXMLParsingException(Throwable cause) {
		super(cause);
	}
}
