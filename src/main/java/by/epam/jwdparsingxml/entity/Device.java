package by.epam.jwdparsingxml.entity;

import java.time.LocalDate;

public class Device {

	private long id;
	private String photoRef;
	private String name;
	private BaseInfo baseInfo;
	private DeviceType type;
	private String origin;
	private LocalDate release;
	private int price;
	private boolean isCritical;
}
