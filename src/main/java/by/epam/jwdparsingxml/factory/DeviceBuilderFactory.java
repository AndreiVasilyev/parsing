package by.epam.jwdparsingxml.factory;

import by.epam.jwdparsingxml.builder.AbstractDeviceBuilder;
import by.epam.jwdparsingxml.builder.imp.DevicesDOMBuilder;
import by.epam.jwdparsingxml.builder.imp.DevicesSAXBuilder;
import by.epam.jwdparsingxml.builder.imp.DevicesStAXBuilder;
import by.epam.jwdparsingxml.exception.XMLParsingException;

public class DeviceBuilderFactory {

	private enum ParserType {
		SAX, STAX, DOM
	}

	public AbstractDeviceBuilder createDeviceBuilder(String parserType) throws XMLParsingException {
		ParserType type = ParserType.valueOf(parserType.toUpperCase());
		return switch (type) {
		case DOM -> new DevicesDOMBuilder();
		case SAX -> new DevicesSAXBuilder();
		case STAX -> new DevicesStAXBuilder();
		default -> throw new XMLParsingException("Wrong parser type. Can't create builder");
		};
	}
}
