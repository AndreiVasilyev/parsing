package by.epam.jwdparsingxml.builder.imp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import by.epam.jwdparsingxml.entity.ConnectionType;
import by.epam.jwdparsingxml.entity.Device;
import by.epam.jwdparsingxml.entity.DeviceType;
import by.epam.jwdparsingxml.entity.Motherboard;
import by.epam.jwdparsingxml.entity.Mouse;
import by.epam.jwdparsingxml.entity.PortType;
import by.epam.jwdparsingxml.entity.Processor;
import by.epam.jwdparsingxml.entity.SizeType;
import by.epam.jwdparsingxml.entity.StorageDevice;
import by.epam.jwdparsingxml.entity.StorageDeviceType;
import by.epam.jwdparsingxml.exception.XMLParsingException;

public class DevicesStAXBuilder extends AbstractDeviceBuilder {

	private static final Logger log = LogManager.getLogger();
	private XMLInputFactory inputFactory;
	private Map<String, DeviceEnum> tagsOfDevices;
	private Map<String, DeviceEnum> tagsOfProperties;
	private Device device;

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

	public DevicesStAXBuilder(List<Device> devices) {
		super(devices);
	}

	public void buildDevicesList(String xmlFilePath) throws XMLParsingException {

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
			log.error("StAX Parser error when parsing file {}", xmlFilePath);
			throw new XMLParsingException("SAX Parser error when parsing file " + xmlFilePath);
		} catch (FileNotFoundException e) {
			log.error("StAX Parser can't find file {}", xmlFilePath);
			throw new XMLParsingException("StAX Parser can't find file " + xmlFilePath);
		} catch (IOException e) {
			log.error("Error reading XML file {}", xmlFilePath);
			throw new XMLParsingException("Error reading XML file " + xmlFilePath);
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
		default -> device = new Device();
		}
	}

	private void extractAttributes(XMLStreamReader reader) {
		String idName = reader.getAttributeValue(null, DeviceAttributeEnum.ID.getQName());
		long id = Long.parseLong(idName.substring(1));
		device.setId(id);

		String photoREF = reader.getAttributeValue(null, DeviceAttributeEnum.PHOTO_REF.getQName());
		if (photoREF != null) {
			device.setPhotoRef(photoREF);
		}
	}

	private void extactCommonProperties(XMLStreamReader reader) throws XMLStreamException {
		String name;
		while (reader.hasNext()) {
			int type = reader.next();
			switch (type) {
			case XMLStreamConstants.START_ELEMENT:
				name = reader.getLocalName();
				DeviceEnum tagName = tagsOfProperties.get(name);
				switch (tagName) {
				case NAME:
					device.setName(getXMLText(reader));
					break;
				case BASE_INFO:
					BaseInfo baseInfo = buildBaseInfo(reader);
					device.setBaseInfo(baseInfo);
					break;
				case DEVICE_TYPE:
					String deviceType = getXMLText(reader).toUpperCase();
					device.setType(DeviceType.valueOf(deviceType));
					break;
				case ORIGIN:
					device.setOrigin(getXMLText(reader));
					break;
				case RELEASE:
					String release = getXMLText(reader);
					device.setRelease(YearMonth.parse(release));
					break;
				case PRICE:
					String priceValue = getXMLText(reader);
					int price = Integer.parseInt(priceValue);
					device.setPrice(price);
					break;
				case IS_CRITICAL:
					String isCritical = getXMLText(reader);
					device.setCritical(Boolean.parseBoolean(isCritical));
					break;
				default:
					break;
				}

				break;
			case XMLStreamConstants.END_ELEMENT:
				name = reader.getLocalName();
				if (name.equals(DeviceEnum.IS_CRITICAL.getQName())) {
					return;
				}
				break;
			}
		}
	}

	private void extractProcessorProperties(XMLStreamReader reader) throws NumberFormatException, XMLStreamException {
		String name;
		while (reader.hasNext()) {
			int type = reader.next();
			switch (type) {
			case XMLStreamConstants.START_ELEMENT:
				name = reader.getLocalName();
				DeviceEnum tagName = tagsOfProperties.get(name);
				switch (tagName) {
				case CODE_NAME:
					((Processor) device).setCodeName(getXMLText(reader));
					break;
				case FREQUENCY:
					String frequencyValue = getXMLText(reader);
					int frequency = Integer.parseInt(frequencyValue);
					((Processor) device).setFrequency(frequency);
					break;
				case POWER:
					String poweryValue = getXMLText(reader);
					int power = Integer.parseInt(poweryValue);
					((Processor) device).setPower(power);
					break;
				default:
					break;
				}
				break;
			case XMLStreamConstants.END_ELEMENT:
				name = reader.getLocalName();
				if (tagsOfDevices.containsKey(name)) {
					return;
				}
				break;
			}
		}
	}

	private void extractMotherboardProperties(XMLStreamReader reader) throws NumberFormatException, XMLStreamException {
		String name;
		while (reader.hasNext()) {
			int type = reader.next();
			switch (type) {
			case XMLStreamConstants.START_ELEMENT:
				name = reader.getLocalName();
				DeviceEnum tagName = tagsOfProperties.get(name);
				switch (tagName) {
				case SIZE_TYPE:
					String sizeType = getXMLText(reader);
					((Motherboard) device).setSizeType(SizeType.valueOf(sizeType.toUpperCase()));
					break;
				case IS_COOLER:
					String isCooler = getXMLText(reader);
					((Motherboard) device).setCooler(Boolean.parseBoolean(isCooler));
					break;
				case POWER:
					String poweryValue = getXMLText(reader);
					int power = Integer.parseInt(poweryValue);
					((Motherboard) device).setPower(power);
					break;
				default:
					break;
				}
				break;
			case XMLStreamConstants.END_ELEMENT:
				name = reader.getLocalName();
				if (tagsOfDevices.containsKey(name)) {
					return;
				}
				break;
			}
		}
	}

	private void extractStorageDeviceProperties(XMLStreamReader reader)
			throws NumberFormatException, XMLStreamException {
		String name;
		while (reader.hasNext()) {
			int type = reader.next();
			switch (type) {
			case XMLStreamConstants.START_ELEMENT:
				name = reader.getLocalName();
				DeviceEnum tagName = tagsOfProperties.get(name);
				switch (tagName) {
				case STORAGE_DEVICE_TYPE:
					String deviceType = getXMLText(reader);
					((StorageDevice) device).setDeviceType(StorageDeviceType.valueOf(deviceType.toUpperCase()));
					break;
				case VOLUME:
					((StorageDevice) device).setVolume(getXMLText(reader));
					break;
				default:
					break;
				}
				break;
			case XMLStreamConstants.END_ELEMENT:
				name = reader.getLocalName();
				if (tagsOfDevices.containsKey(name)) {
					return;
				}
				break;
			}
		}
	}

	private void extractMouseProperties(XMLStreamReader reader) throws NumberFormatException, XMLStreamException {
		String name;
		while (reader.hasNext()) {
			int type = reader.next();
			switch (type) {
			case XMLStreamConstants.START_ELEMENT:
				name = reader.getLocalName();
				DeviceEnum tagName = tagsOfProperties.get(name);
				switch (tagName) {
				case PORT_TYPE:
					String portType = getXMLText(reader);
					((Mouse) device).setConnectionInterface(PortType.valueOf(portType.toUpperCase()));
					break;
				case CONNECTION_TYPE:
					String connectionType = getXMLText(reader);
					((Mouse) device).setConnectionType(ConnectionType.valueOf(connectionType.toUpperCase()));
					break;
				default:
					break;
				}
				break;
			case XMLStreamConstants.END_ELEMENT:
				name = reader.getLocalName();
				if (tagsOfDevices.containsKey(name)) {
					return;
				}
				break;
			}
		}
	}

	private BaseInfo buildBaseInfo(XMLStreamReader reader) throws XMLStreamException {
		BaseInfo baseInfo = new BaseInfo();
		String name;
		while (reader.hasNext()) {
			int type = reader.next();
			switch (type) {
			case XMLStreamConstants.START_ELEMENT:
				name = reader.getLocalName();
				DeviceEnum tagName = tagsOfProperties.get(name);
				switch (tagName) {
				case PRODUCER:
					baseInfo.setProducer(getXMLText(reader));
					break;
				case MODEL:
					baseInfo.setModel(getXMLText(reader));
					break;
				case SERIAL:
					baseInfo.setSerial(getXMLText(reader));
					break;
				default:
					break;
				}
				break;
			case XMLStreamConstants.END_ELEMENT:
				name = reader.getLocalName();
				if (name.equals(DeviceEnum.BASE_INFO.getQName())) {
					return baseInfo;
				}
				break;
			}
		}

		return baseInfo;
	}

	private String getXMLText(XMLStreamReader reader) throws XMLStreamException {
		String text = null;
		if (reader.hasNext()) {
			reader.next();
			text = reader.getText();
		}
		return text;
	}
}
