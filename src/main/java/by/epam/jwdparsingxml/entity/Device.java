package by.epam.jwdparsingxml.entity;

import java.time.YearMonth;

public class Device {

	private long id;
	private String photoRef;
	private String name;
	private BaseInfo baseInfo;
	private DeviceType type;
	private String origin;
	private YearMonth release;
	private int price;
	private boolean isCritical;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPhotoRef() {
		return photoRef;
	}

	public void setPhotoRef(String photoRef) {
		this.photoRef = photoRef;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BaseInfo getBaseInfo() {
		return baseInfo;
	}

	public void setBaseInfo(BaseInfo baseInfo) {
		this.baseInfo = baseInfo;
	}

	public DeviceType getType() {
		return type;
	}

	public void setType(DeviceType type) {
		this.type = type;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public YearMonth getRelease() {
		return release;
	}

	public void setRelease(YearMonth release) {
		this.release = release;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public boolean isCritical() {
		return isCritical;
	}

	public void setCritical(boolean isCritical) {
		this.isCritical = isCritical;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((baseInfo == null) ? 0 : baseInfo.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + (isCritical ? 1231 : 1237);
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((origin == null) ? 0 : origin.hashCode());
		result = prime * result + ((photoRef == null) ? 0 : photoRef.hashCode());
		result = prime * result + price;
		result = prime * result + ((release == null) ? 0 : release.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Device other = (Device) obj;
		if (baseInfo == null) {
			if (other.baseInfo != null)
				return false;
		} else if (!baseInfo.equals(other.baseInfo))
			return false;
		if (id != other.id)
			return false;
		if (isCritical != other.isCritical)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (origin == null) {
			if (other.origin != null)
				return false;
		} else if (!origin.equals(other.origin))
			return false;
		if (photoRef == null) {
			if (other.photoRef != null)
				return false;
		} else if (!photoRef.equals(other.photoRef))
			return false;
		if (price != other.price)
			return false;
		if (release == null) {
			if (other.release != null)
				return false;
		} else if (!release.equals(other.release))
			return false;
		if (type != other.type)
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Device [id=");
		builder.append(id);
		builder.append(", photoRef=");
		builder.append(photoRef);
		builder.append(", name=");
		builder.append(name);
		builder.append(", baseInfo=");
		builder.append(baseInfo);
		builder.append(", type=");
		builder.append(type);
		builder.append(", origin=");
		builder.append(origin);
		builder.append(", release=");
		builder.append(release);
		builder.append(", price=");
		builder.append(price);
		builder.append(", isCritical=");
		builder.append(isCritical);
		builder.append("]");
		return builder.toString();
	}
}
