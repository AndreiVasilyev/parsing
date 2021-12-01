package by.epam.jwdparsingxml.entity;

public class StorageDevice extends Device {

	private StorageDeviceType deviceType;
	private int volume;

	public StorageDeviceType getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(StorageDeviceType deviceType) {
		this.deviceType = deviceType;
	}

	public int getVolume() {
		return volume;
	}

	public void setVolume(int volume) {
		this.volume = volume;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((deviceType == null) ? 0 : deviceType.hashCode());
		result = prime * result + volume;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		StorageDevice other = (StorageDevice) obj;
		if (deviceType != other.deviceType)
			return false;
		if (volume != other.volume)
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("StorageDevice [deviceType=");
		builder.append(deviceType);
		builder.append(", volume=");
		builder.append(volume);
		builder.append("]");
		return builder.toString();
	}
}
