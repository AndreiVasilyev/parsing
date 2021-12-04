package by.epam.jwdparsingxml.builder.imp;

import java.io.IOException;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

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

public class DevicesDOMBuilder extends AbstractDeviceBuilder {

	private static final Logger log = LogManager.getLogger();
	private DocumentBuilder builder;
	private Map<String, DeviceEnum> tagsOfDevices;

	public DevicesDOMBuilder() throws DeviceXMLParsingException {
		this.devices = new ArrayList<>();
		this.tagsOfDevices = new HashMap<>();
		for (DeviceEnum deviceEnumName : DeviceEnum.values()) {
			if (deviceEnumName.ordinal() > 0 && deviceEnumName.ordinal() < 5) {
				tagsOfDevices.put(deviceEnumName.getQName(), deviceEnumName);
			}
		}
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			log.error("Error DOM Parser configuration", e);
			throw new DeviceXMLParsingException("Error DOM Parser configuration", e);
		}
	}

	public void buildDevicesList(String xmlFilePath) throws DeviceXMLParsingException {
		Document document = null;
		devices.clear();
		try {
			document = builder.parse(xmlFilePath);
			Element root = document.getDocumentElement();
			for (String tagName : tagsOfDevices.keySet()) {
				NodeList devicesNodesList = root.getElementsByTagName(tagName);
				for (int i = 0; i < devicesNodesList.getLength(); i++) {
					Element deviceElement = (Element) devicesNodesList.item(i);
					AbstractDevice device = buildDevice(deviceElement);
					devices.add(device);
				}
			}

		} catch (SAXException e) {
			log.error("SAX Parser error when parsing file {}", xmlFilePath, e);
			throw new DeviceXMLParsingException("SAX Parser error when parsing file " + xmlFilePath, e);
		} catch (IOException e) {
			log.error("Error reading xml file {}", xmlFilePath, e);
			e.printStackTrace();
			throw new DeviceXMLParsingException("Error reading xml file " + xmlFilePath, e);
		}
	}

	private AbstractDevice buildDevice(Element deviceElement) {
		DeviceEnum tagName = tagsOfDevices.get(deviceElement.getTagName());
		AbstractDevice device = switch (tagName) {
		case PROCESSOR -> {
			device = new Processor();

			String codeName = extractTagTextContent(deviceElement, DeviceEnum.CODE_NAME.getQName());
			((Processor) device).setCodeName(codeName);

			String frequencyValue = extractTagTextContent(deviceElement, DeviceEnum.FREQUENCY.getQName());
			int frequency = Integer.parseInt(frequencyValue);
			((Processor) device).setFrequency(frequency);

			String powerValue = extractTagTextContent(deviceElement, DeviceEnum.POWER.getQName());
			int power = Integer.parseInt(powerValue);
			((Processor) device).setPower(power);

			yield device;
		}
		case MOTHERBOARD -> {
			device = new Motherboard();

			String sizeType = extractTagTextContent(deviceElement, DeviceEnum.SIZE_TYPE.getQName());
			((Motherboard) device).setSizeType(SizeType.valueOf(sizeType.toUpperCase()));

			String isCooler = extractTagTextContent(deviceElement, DeviceEnum.IS_COOLER.getQName());
			((Motherboard) device).setCooler(Boolean.parseBoolean(isCooler));

			String powerValue = extractTagTextContent(deviceElement, DeviceEnum.POWER.getQName());
			int power = Integer.parseInt(powerValue);
			((Motherboard) device).setPower(power);

			yield device;
		}
		case STORAGE_DEVICE -> {
			device = new StorageDevice();

			String deviceType = extractTagTextContent(deviceElement, DeviceEnum.STORAGE_DEVICE_TYPE.getQName());
			((StorageDevice) device).setDeviceType(StorageDeviceType.valueOf(deviceType.toUpperCase()));

			String volume = extractTagTextContent(deviceElement, DeviceEnum.VOLUME.getQName());
			((StorageDevice) device).setVolume(volume);

			yield device;
		}
		case MOUSE -> {
			device = new Mouse();

			String portType = extractTagTextContent(deviceElement, DeviceEnum.PORT_TYPE.getQName());
			((Mouse) device).setConnectionInterface(PortType.valueOf(portType.toUpperCase()));

			String connectionType = extractTagTextContent(deviceElement, DeviceEnum.CONNECTION_TYPE.getQName());
			((Mouse) device).setConnectionType(ConnectionType.valueOf(connectionType.toUpperCase()));

			yield device;
		}
		default -> {
			log.warn("Unexpected device tagName {}", tagName);
			yield new AbstractDevice();
		}
		};
		BaseInfo baseInfo = new BaseInfo();

		String idName = deviceElement.getAttribute(DeviceAttributeEnum.ID.getQName());
		Long id = Long.parseLong(idName.substring(1));
		device.setId(id);

		String photoRef = deviceElement.getAttribute(DeviceAttributeEnum.PHOTO_REF.getQName());
		device.setPhotoRef(photoRef);

		String name = extractTagTextContent(deviceElement, DeviceEnum.NAME.getQName());
		device.setName(name);

		NodeList nodeList = deviceElement.getElementsByTagName(DeviceEnum.BASE_INFO.getQName());
		Element baseInfoElement = (Element) nodeList.item(0);

		String producer = extractTagTextContent(baseInfoElement, DeviceEnum.PRODUCER.getQName());
		baseInfo.setProducer(producer);

		String model = extractTagTextContent(baseInfoElement, DeviceEnum.MODEL.getQName());
		baseInfo.setModel(model);

		String serial = extractTagTextContent(baseInfoElement, DeviceEnum.SERIAL.getQName());
		baseInfo.setSerial(serial);
		device.setBaseInfo(baseInfo);

		String type = extractTagTextContent(deviceElement, DeviceEnum.DEVICE_TYPE.getQName());
		device.setType(DeviceType.valueOf(type.toUpperCase()));

		String origin = extractTagTextContent(deviceElement, DeviceEnum.ORIGIN.getQName());
		device.setOrigin(origin);

		String release = extractTagTextContent(deviceElement, DeviceEnum.RELEASE.getQName());
		device.setRelease(YearMonth.parse(release));

		String priceValue = extractTagTextContent(deviceElement, DeviceEnum.PRICE.getQName());
		int price = Integer.parseInt(priceValue);
		device.setPrice(price);

		String isCritical = extractTagTextContent(deviceElement, DeviceEnum.IS_CRITICAL.getQName());
		device.setCritical(Boolean.parseBoolean(isCritical));

		return device;
	}

	private String extractTagTextContent(Element element, String tagName) {
		NodeList nodeList = element.getElementsByTagName(tagName);
		Node node = nodeList.item(0);
		return node.getTextContent();
	}

}
