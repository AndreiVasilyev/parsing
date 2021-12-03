package by.epam.jwdparsingxml.builder.imp;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

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

public class DeviceHandler extends DefaultHandler {

	private List<Device> devices;
	private Device currentDevice;
	private BaseInfo currentBaseInfo;
	private DeviceEnum currentTag;
	private Map<String, DeviceEnum> tagsOfProperties;
	private Map<String, DeviceEnum> tagsOfDevices;
	private Map<String, DeviceAttributeEnum> deviceAttributes;

	public DeviceHandler() {
		devices = new ArrayList<>();
		tagsOfProperties = new HashMap<>();
		tagsOfDevices = new HashMap<>();
		deviceAttributes = new HashMap<>();
		for (DeviceEnum enumValue : DeviceEnum.values()) {
			if (enumValue.ordinal() > 0 && enumValue.ordinal() < 5) {
				tagsOfDevices.put(enumValue.getQName(), enumValue);
			} else if (enumValue.ordinal() != 0) {
				tagsOfProperties.put(enumValue.getQName(), enumValue);
			}
		}
		for (DeviceAttributeEnum enumValue : DeviceAttributeEnum.values()) {
			deviceAttributes.put(enumValue.getQName(), enumValue);
		}
	}

	public List<Device> getDevices() {
		return devices;
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if (tagsOfDevices.containsKey(qName)) {
			currentDevice = switch (tagsOfDevices.get(qName)) {
			case PROCESSOR -> new Processor();
			case MOTHERBOARD -> new Motherboard();
			case STORAGE_DEVICE -> new StorageDevice();
			case MOUSE -> new Mouse();
			default -> new Device();
			};
			for (int i = 0; i < attributes.getLength(); i++) {
				String attributeName = attributes.getQName(i);
				switch (deviceAttributes.get(attributeName)) {
				case ID:
					String attributeValue = attributes.getValue(i).substring(1);
					long id = Long.parseLong(attributeValue);
					currentDevice.setId(id);
					break;
				case PHOTO_REF:
					currentDevice.setPhotoRef(attributes.getValue(i));
					break;
				}
			}
		} else if (tagsOfProperties.containsKey(qName)) {
			if (qName.equals(DeviceEnum.BASE_INFO.getQName())) {
				currentBaseInfo = new BaseInfo();
			} else {
				currentTag = tagsOfProperties.get(qName);
			}
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (tagsOfDevices.containsKey(qName)) {
			devices.add(currentDevice);
			currentDevice = null;
		} else if (qName.equals(DeviceEnum.BASE_INFO.getQName())) {
			currentDevice.setBaseInfo(currentBaseInfo);
			currentBaseInfo = null;
		}
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		String propertyValue = new String(ch, start, length);
		if (currentTag != null) {
			switch (currentTag) {
			case NAME -> currentDevice.setName(propertyValue);
			case PRODUCER -> currentBaseInfo.setProducer(propertyValue);
			case MODEL -> currentBaseInfo.setModel(propertyValue);
			case SERIAL -> currentBaseInfo.setSerial(propertyValue);
			case DEVICE_TYPE -> currentDevice.setType(DeviceType.valueOf(propertyValue.toUpperCase()));
			case ORIGIN -> currentDevice.setOrigin(propertyValue);
			case RELEASE -> currentDevice.setRelease(YearMonth.parse(propertyValue));
			case PRICE -> currentDevice.setPrice(Integer.parseInt(propertyValue));
			case IS_CRITICAL -> currentDevice.setCritical(Boolean.parseBoolean(propertyValue));
			case CODE_NAME -> ((Processor) currentDevice).setCodeName(propertyValue);
			case FREQUENCY -> ((Processor) currentDevice).setFrequency(Integer.parseInt(propertyValue));
			case POWER -> {
				if (currentDevice instanceof Processor currentProcessor) {
					currentProcessor.setPower(Integer.parseInt(propertyValue));
				} else {
					((Motherboard) currentDevice).setPower(Integer.parseInt(propertyValue));
				}
			}
			case SIZE_TYPE -> ((Motherboard) currentDevice).setSizeType(SizeType.valueOf(propertyValue.toUpperCase()));
			case IS_COOLER -> ((Motherboard) currentDevice).setCooler(Boolean.parseBoolean(propertyValue));
			case STORAGE_DEVICE_TYPE -> ((StorageDevice) currentDevice)
					.setDeviceType(StorageDeviceType.valueOf(propertyValue.toUpperCase()));
			case VOLUME -> ((StorageDevice) currentDevice).setVolume(propertyValue);
			case PORT_TYPE -> ((Mouse) currentDevice)
					.setConnectionInterface(PortType.valueOf(propertyValue.toUpperCase()));
			case CONNECTION_TYPE -> ((Mouse) currentDevice)
					.setConnectionType(ConnectionType.valueOf(propertyValue.toUpperCase()));
			default -> throw new SAXException("Unexpected tag value: " + currentTag);
			}
			currentTag = null;
		}
	}
}
