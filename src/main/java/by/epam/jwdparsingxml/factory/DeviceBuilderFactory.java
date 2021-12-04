package by.epam.jwdparsingxml.factory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.jwdparsingxml.builder.AbstractDeviceBuilder;
import by.epam.jwdparsingxml.builder.imp.DevicesDOMBuilder;
import by.epam.jwdparsingxml.builder.imp.DevicesSAXBuilder;
import by.epam.jwdparsingxml.builder.imp.DevicesStAXBuilder;
import by.epam.jwdparsingxml.exception.DeviceXMLParsingException;

public class DeviceBuilderFactory {

	private static final Logger log = LogManager.getLogger();

	private enum ParserType {
		SAX, STAX, DOM
	}

	public AbstractDeviceBuilder createDeviceBuilder(String parserType) throws DeviceXMLParsingException {
		ParserType type = ParserType.valueOf(parserType.toUpperCase());
		return switch (type) {
		case DOM -> new DevicesDOMBuilder();
		case SAX -> new DevicesSAXBuilder();
		case STAX -> new DevicesStAXBuilder();
		default -> {
			log.error("Unexpected parser type {}", type);
			throw new DeviceXMLParsingException("Wrong parser type. Can't create builder" + type);
		}
		};
	}
}
