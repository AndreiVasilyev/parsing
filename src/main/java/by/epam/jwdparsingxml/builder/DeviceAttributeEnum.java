package by.epam.jwdparsingxml.builder;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum DeviceAttributeEnum {
	ID, PHOTO_REF;

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
		return buffer.toString();
	}
}
