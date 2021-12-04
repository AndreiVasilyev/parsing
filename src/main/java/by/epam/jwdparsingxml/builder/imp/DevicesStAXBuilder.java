package by.epam.jwdparsingxml.builder.imp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.jwdparsingxml.builder.AbstractDeviceBuilder;
import by.epam.jwdparsingxml.builder.DeviceAttributeEnum;
import by.epam.jwdparsingxml.builder.DeviceEnum;
import by.epam.jwdparsingxml.entity.BaseInfo;
import by.epam.jwdparsingxml.entity.AbstractDevice;
import by.epam.jwdparsingxml.entity.Motherboard;
import by.epam.jwdparsingxml.entity.Mouse;
import by.epam.jwdparsingxml.entity.Processor;
import by.epam.jwdparsingxml.entity.StorageDevice;
import by.epam.jwdparsingxml.entity.type.ConnectionType;
import by.epam.jwdparsingxml.entity.type.DeviceType;
import by.epam.jwdparsingxml.entity.type.PortType;
import by.epam.jwdparsingxml.entity.type.SizeType;
import by.epam.jwdparsingxml.entity.type.StorageDeviceType;
import by.epam.jwdparsingxml.exception.DeviceXMLParsingException;

public class DevicesStAXBuilder extends AbstractDeviceBuilder {

	private static final Logger log = LogManager.getLogger();
	private XMLInputFactory inputFactory;
	private Map<String, DeviceEnum> tagsOfDevices;
	private Map<String, DeviceEnum> tagsOfProperties;
	private AbstractDevice device;

	public DevicesStAXBuilder() {
		inputFactory = XMLInputFactory.newInstance();
		devices = new ArrayList<>();
		tagsOfDevices = new HashMap<>();
		tagsOfProperties = new HashMap<>();
		for (DeviceEnum enumValue : DeviceEnum.values()) {
			if (enumValue.ordinal() > 0 && enumValue.ordinal() < 5) {
				tagsOfDevices.put(enumValue.getQName(), enumValue);
			} else if (enumValue.ordinal() != 0) {
				tagsOfProperties.put(enumValue.getQName(), enumValue);
			}
		}
	}

	public void buildDevicesList(String xmlFilePath) throws DeviceXMLParsingException {
		devices.clear();
		try (FileInputStream inputStream = new FileInputStream(new File(xmlFilePath))) {
			XMLStreamReader reader = inputFactory.createXMLStreamReader(inputStream);
			String name;
			while (reader.hasNext()) {
				int type = reader.next();
				if (type == XMLStreamConstants.START_ELEMENT) {
					name = reader.getLocalName();
					if (tagsOfDevices.keySet().contains(name)) {
						buildDevice(reader);
						devices.add(device);
						device = null;
					}
				}
			}
		} catch (XMLStreamException e) {
			log.error("StAX Parser error when parsing file {}", xmlFilePath, e);
			throw new DeviceXMLParsingException("SAX Parser error when parsing file " + xmlFilePath, e);
		} catch (FileNotFoundException e) {
			log.error("StAX Parser can't find file {}", xmlFilePath, e);
			throw new DeviceXMLParsingException("StAX Parser can't find file " + xmlFilePath, e);
		} catch (IOException e) {
			log.error("Error reading XML file {}", xmlFilePath, e);
			throw new DeviceXMLParsingException("Error reading XML file " + xmlFilePath, e);
		}
	}

	private void buildDevice(XMLStreamReader reader) throws XMLStreamException {
		DeviceEnum tagName = tagsOfDevices.get(reader.getLocalName());
		switch (tagName) {
		case PROCESSOR -> {
			device = new Processor();
			extractAttributes(reader);
			extactCommonProperties(reader);
			extractProcessorProperties(reader);
		}
		case MOTHERBOARD -> {
			device = new Motherboard();
			extractAttributes(reader);
			extactCommonProperties(reader);
			extractMotherboardProperties(reader);
		}
		case STORAGE_DEVICE -> {
			device = new StorageDevice();
			extractAttributes(reader);
			extactCommonProperties(reader);
			extractStorageDeviceProperties(reader);
		}
		case MOUSE -> {
			device = new Mouse();
			extractAttributes(reader);
			extactCommonProperties(reader);
			extractMouseProperties(reader);
		}
		default -> {
			log.warn("Unexpected device tagName {}", tagName);
			device = new AbstractDevice();
		}
		}
	}

	private void extractAttributes(XMLStreamReader reader) {
		String idName = reader.getAttributeValue(null, DeviceAttributeEnum.ID.getQName());
		long id = Long.parseLong(idName.substring(1));
		device.setId(id);

		String photoREF = reader.getAttributeValue(null, DeviceAttributeEnum.PHOTO_REF.getQName());
		if (photoREF == null) {
			device.setPhotoRef("");
		} else {
			device.setPhotoRef(photoREF);
		}
	}

	private void extactCommonProperties(XMLStreamReader reader) throws XMLStreamException {
		String name;
		while (reader.hasNext()) {
			int type = reader.next();
			switch (type) {
			case XMLStreamConstants.START_ELEMENT -> {
				name = reader.getLocalName();
				DeviceEnum tagName = tagsOfProperties.get(name);
				switch (tagName) {
				case NAME -> device.setName(getXMLText(reader));
				case BASE_INFO -> {
					BaseInfo baseInfo = buildBaseInfo(reader);
					device.setBaseInfo(baseInfo);
				}
				case DEVICE_TYPE -> {
					String deviceType = getXMLText(reader).toUpperCase();
					device.setType(DeviceType.valueOf(deviceType));
				}
				case ORIGIN -> device.setOrigin(getXMLText(reader));
				case RELEASE -> {
					String release = getXMLText(reader);
					device.setRelease(YearMonth.parse(release));
				}
				case PRICE -> {
					String priceValue = getXMLText(reader);
					int price = Integer.parseInt(priceValue);
					device.setPrice(price);
				}
				case IS_CRITICAL -> {
					String isCritical = getXMLText(reader);
					device.setCritical(Boolean.parseBoolean(isCritical));
				}
				default -> log.warn("Unexpected device tagName {}", tagName);
				}

			}
			case XMLStreamConstants.END_ELEMENT -> {
				name = reader.getLocalName();
				if (name.equals(DeviceEnum.IS_CRITICAL.getQName())) {
					return;
				}
			}
			}
		}
	}

	private void extractProcessorProperties(XMLStreamReader reader) throws XMLStreamException {
		String name;
		while (reader.hasNext()) {
			int type = reader.next();
			switch (type) {
			case XMLStreamConstants.START_ELEMENT -> {
				name = reader.getLocalName();
				DeviceEnum tagName = tagsOfProperties.get(name);
				switch (tagName) {
				case CODE_NAME -> ((Processor) device).setCodeName(getXMLText(reader));
				case FREQUENCY -> {
					String frequencyValue = getXMLText(reader);
					int frequency = Integer.parseInt(frequencyValue);
					((Processor) device).setFrequency(frequency);
				}
				case POWER -> {
					String poweryValue = getXMLText(reader);
					int power = Integer.parseInt(poweryValue);
					((Processor) device).setPower(power);
				}
				default -> log.warn("Unexpected tagName {}", tagName);
				}
			}
			case XMLStreamConstants.END_ELEMENT -> {
				name = reader.getLocalName();
				if (tagsOfDevices.containsKey(name)) {
					return;
				}
			}
			}
		}
	}

	private void extractMotherboardProperties(XMLStreamReader reader) throws XMLStreamException {
		String name;
		while (reader.hasNext()) {
			int type = reader.next();
			switch (type) {
			case XMLStreamConstants.START_ELEMENT -> {
				name = reader.getLocalName();
				DeviceEnum tagName = tagsOfProperties.get(name);
				switch (tagName) {
				case SIZE_TYPE -> {
					String sizeType = getXMLText(reader);
					((Motherboard) device).setSizeType(SizeType.valueOf(sizeType.toUpperCase()));
				}
				case IS_COOLER -> {
					String isCooler = getXMLText(reader);
					((Motherboard) device).setCooler(Boolean.parseBoolean(isCooler));
				}
				case POWER -> {
					String poweryValue = getXMLText(reader);
					int power = Integer.parseInt(poweryValue);
					((Motherboard) device).setPower(power);
				}
				default -> log.warn("Unexpected tagName {}", tagName);
				}
			}
			case XMLStreamConstants.END_ELEMENT -> {
				name = reader.getLocalName();
				if (tagsOfDevices.containsKey(name)) {
					return;
				}
			}
			}
		}
	}

	private void extractStorageDeviceProperties(XMLStreamReader reader) throws XMLStreamException {
		String name;
		while (reader.hasNext()) {
			int type = reader.next();
			switch (type) {
			case XMLStreamConstants.START_ELEMENT -> {
				name = reader.getLocalName();
				DeviceEnum tagName = tagsOfProperties.get(name);
				switch (tagName) {
				case STORAGE_DEVICE_TYPE -> {
					String deviceType = getXMLText(reader);
					((StorageDevice) device).setDeviceType(StorageDeviceType.valueOf(deviceType.toUpperCase()));
				}
				case VOLUME -> ((StorageDevice) device).setVolume(getXMLText(reader));
				default -> log.warn("Unexpected tagName {}", tagName);
				}
			}
			case XMLStreamConstants.END_ELEMENT -> {
				name = reader.getLocalName();
				if (tagsOfDevices.containsKey(name)) {
					return;
				}
			}
			}
		}
	}

	private void extractMouseProperties(XMLStreamReader reader) throws XMLStreamException {
		String name;
		while (reader.hasNext()) {
			int type = reader.next();
			switch (type) {
			case XMLStreamConstants.START_ELEMENT -> {
				name = reader.getLocalName();
				DeviceEnum tagName = tagsOfProperties.get(name);
				switch (tagName) {
				case PORT_TYPE -> {
					String portType = getXMLText(reader);
					((Mouse) device).setConnectionInterface(PortType.valueOf(portType.toUpperCase()));
				}
				case CONNECTION_TYPE -> {
					String connectionType = getXMLText(reader);
					((Mouse) device).setConnectionType(ConnectionType.valueOf(connectionType.toUpperCase()));
				}
				default -> log.warn("Unexpected tagName {}", tagName);
				}
			}
			case XMLStreamConstants.END_ELEMENT -> {
				name = reader.getLocalName();
				if (tagsOfDevices.containsKey(name)) {
					return;
				}
			}
			}

		}
	}

	private BaseInfo buildBaseInfo(XMLStreamReader reader) throws XMLStreamException {
		BaseInfo baseInfo = new BaseInfo();
		String name;
		while (reader.hasNext()) {
			int type = reader.next();
			switch (type) {
			case XMLStreamConstants.START_ELEMENT -> {
				name = reader.getLocalName();
				DeviceEnum tagName = tagsOfProperties.get(name);
				switch (tagName) {
				case PRODUCER -> baseInfo.setProducer(getXMLText(reader));
				case MODEL -> baseInfo.setModel(getXMLText(reader));
				case SERIAL -> baseInfo.setSerial(getXMLText(reader));
				default -> log.warn("Unexpected tagName {}", tagName);
				}
			}
			case XMLStreamConstants.END_ELEMENT -> {
				name = reader.getLocalName();
				if (name.equals(DeviceEnum.BASE_INFO.getQName())) {
					return baseInfo;
				}
			}
			}
		}
		return baseInfo;
	}

	private String getXMLText(XMLStreamReader reader) throws XMLStreamException {
		String text = "";
		if (reader.hasNext()) {
			reader.next();
			text = reader.getText();
		}
		return text;
	}
}
