package by.epam.jwdparsingxml.builder;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum DeviceEnum {

	DEVICES, PROCESSOR, MOTHERBOARD, STORAGE_DEVICE, MOUSE, NAME, BASE_INFO, PRODUCER, MODEL, SERIAL, DEVICE_TYPE,
	ORIGIN, RELEASE, PRICE, IS_CRITICAL, CODE_NAME, FREQUENCY, POWER, SIZE_TYPE, IS_COOLER, STORAGE_DEVICE_TYPE, VOLUME,
	PORT_TYPE, CONNECTION_TYPE;

	public String getQName() {
		String delimiterRegex = "_([a-zA-Z]{1})";
		String qName = name().toLowerCase();
		Pattern pattern = Pattern.compile(delimiterRegex);
		Matcher matcher = pattern.matcher(qName);
		StringBuffer buffer = new StringBuffer();
		while (matcher.find()) {
			matcher.appendReplacement(buffer, matcher.group(1).toUpperCase());
		}
		matcher.appendTail(buffer);
		qName = buffer.toString();
		if (ordinal() < 5) {
			qName = Character.toUpperCase(qName.charAt(0)) + qName.substring(1);
		}
		return qName;
	}

}
