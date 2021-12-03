package by.epam.jwdparsingxml.entity;

public class StorageDevice extends Device {

	private StorageDeviceType deviceType;
	private String volume;

	public StorageDeviceType getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(StorageDeviceType deviceType) {
		this.deviceType = deviceType;
	}

	public String getVolume() {
		return volume;
	}

	public void setVolume(String volume) {
		this.volume = volume;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((deviceType == null) ? 0 : deviceType.hashCode());
		result = prime * result + ((volume == null) ? 0 : volume.hashCode());
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
		if (volume == null) {
			if (other.volume != null)
				return false;
		} else if (!volume.equals(other.volume))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("StorageDevice [");
		builder.append(super.toString());
		builder.append(", deviceType=");
		builder.append(deviceType);
		builder.append(", volume=");
		builder.append(volume);
		builder.append("]");
		return builder.toString();
	}
}
