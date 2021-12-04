package by.epm.jwdparsingxml;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import by.epam.jwdparsingxml.entity.AbstractDevice;
import by.epam.jwdparsingxml.entity.BaseInfo;
import by.epam.jwdparsingxml.entity.Motherboard;
import by.epam.jwdparsingxml.entity.Mouse;
import by.epam.jwdparsingxml.entity.Processor;
import by.epam.jwdparsingxml.entity.StorageDevice;
import by.epam.jwdparsingxml.entity.type.ConnectionType;
import by.epam.jwdparsingxml.entity.type.DeviceType;
import by.epam.jwdparsingxml.entity.type.PortType;
import by.epam.jwdparsingxml.entity.type.SizeType;
import by.epam.jwdparsingxml.entity.type.StorageDeviceType;

public class SourceDataCreator {

	public static final List<AbstractDevice> EXPECTED_LIST = new ArrayList<>();
	public static final Comparator<AbstractDevice> DEVICE_ID_COMPARATOR;

	static {

		AbstractDevice processor = new Processor();
		processor.setId(1);
		processor.setName("processor1");
		BaseInfo baseInfo = new BaseInfo();
		baseInfo.setProducer("Intel");
		baseInfo.setModel("Core i5");
		baseInfo.setSerial("654682");
		processor.setBaseInfo(baseInfo);
		processor.setPhotoRef("/photo/photo1.jpg");
		processor.setOrigin("Vietnam");
		processor.setPrice(50);
		processor.setRelease(YearMonth.parse("2019-01"));
		processor.setType(DeviceType.INTEGRATED);
		processor.setCritical(true);
		((Processor) processor).setCodeName("codeName1");
		((Processor) processor).setPower(30);
		((Processor) processor).setFrequency(2400);

		AbstractDevice motherboard = new Motherboard();
		motherboard.setId(6);
		motherboard.setName("motherboard1");
		baseInfo = new BaseInfo();
		baseInfo.setProducer("Asus");
		baseInfo.setModel("A50D40");
		baseInfo.setSerial("84351");
		motherboard.setBaseInfo(baseInfo);
		motherboard.setPhotoRef("/photo/photo4.jpg");
		motherboard.setOrigin("China");
		motherboard.setPrice(40);
		motherboard.setRelease(YearMonth.parse("2020-05"));
		motherboard.setType(DeviceType.INTEGRATED);
		motherboard.setCritical(true);
		((Motherboard) motherboard).setSizeType(SizeType.ATX);
		((Motherboard) motherboard).setPower(40);
		((Motherboard) motherboard).setCooler(true);

		AbstractDevice storageDevice = new StorageDevice();
		storageDevice.setId(11);
		storageDevice.setName("storageDevice1");
		baseInfo = new BaseInfo();
		baseInfo.setProducer("Seagate");
		baseInfo.setModel("Baracuda");
		baseInfo.setSerial("4735135");
		storageDevice.setBaseInfo(baseInfo);
		storageDevice.setPhotoRef("");
		storageDevice.setOrigin("China");
		storageDevice.setPrice(100);
		storageDevice.setRelease(YearMonth.parse("2020-11"));
		storageDevice.setType(DeviceType.INTEGRATED);
		storageDevice.setCritical(true);
		((StorageDevice) storageDevice).setVolume("4Tb");
		((StorageDevice) storageDevice).setDeviceType(StorageDeviceType.HDD);

		AbstractDevice mouse = new Mouse();
		mouse.setId(16);
		mouse.setName("mouse1");
		baseInfo = new BaseInfo();
		baseInfo.setProducer("Smartby");
		baseInfo.setModel("43335");
		baseInfo.setSerial("427867867");
		mouse.setBaseInfo(baseInfo);
		mouse.setOrigin("China");
		mouse.setPhotoRef("");
		mouse.setPrice(10);
		mouse.setRelease(YearMonth.parse("2021-02"));
		mouse.setType(DeviceType.PERIPHERAL);
		mouse.setCritical(false);
		((Mouse) mouse).setConnectionInterface(PortType.USB);
		((Mouse) mouse).setConnectionType(ConnectionType.WIRELESS);

		EXPECTED_LIST.add(processor);
		EXPECTED_LIST.add(motherboard);
		EXPECTED_LIST.add(storageDevice);
		EXPECTED_LIST.add(mouse);

		DEVICE_ID_COMPARATOR = new Comparator<AbstractDevice>() {

			@Override
			public int compare(AbstractDevice o1, AbstractDevice o2) {
				return (int) (o1.getId() - o2.getId());
			}
		};
		
		EXPECTED_LIST.sort(DEVICE_ID_COMPARATOR);
	}
}
