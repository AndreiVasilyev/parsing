package by.epam.jwdparsingxml.entity;

public class Mouse extends Device {

	private PortType connectionInterface;
	private ConnectionType connectionType;

	public PortType getConnectionInterface() {
		return connectionInterface;
	}

	public void setConnectionInterface(PortType connectionInterface) {
		this.connectionInterface = connectionInterface;
	}

	public ConnectionType getConnectionType() {
		return connectionType;
	}

	public void setConnectionType(ConnectionType connectionType) {
		this.connectionType = connectionType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((connectionInterface == null) ? 0 : connectionInterface.hashCode());
		result = prime * result + ((connectionType == null) ? 0 : connectionType.hashCode());
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
		Mouse other = (Mouse) obj;
		if (connectionInterface != other.connectionInterface)
			return false;
		if (connectionType != other.connectionType)
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Mouse [");
		builder.append(super.toString());
		builder.append(", connectionInterface=");
		builder.append(connectionInterface);
		builder.append(", connectionType=");
		builder.append(connectionType);
		builder.append("]");
		return builder.toString();
	}
}
