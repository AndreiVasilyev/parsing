package by.epam.jwdparsingxml.entity;

import by.epam.jwdparsingxml.entity.type.SizeType;

public class Motherboard extends AbstractDevice {

	private SizeType sizeType;
	private int power;
	private boolean isCooler;

	public SizeType getSizeType() {
		return sizeType;
	}

	public void setSizeType(SizeType sizeType) {
		this.sizeType = sizeType;
	}

	public int getPower() {
		return power;
	}

	public void setPower(int power) {
		this.power = power;
	}

	public boolean isCooler() {
		return isCooler;
	}

	public void setCooler(boolean isCooler) {
		this.isCooler = isCooler;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (isCooler ? 1231 : 1237);
		result = prime * result + power;
		result = prime * result + ((sizeType == null) ? 0 : sizeType.hashCode());
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
		Motherboard other = (Motherboard) obj;
		if (isCooler != other.isCooler)
			return false;
		if (power != other.power)
			return false;
		if (sizeType != other.sizeType)
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Motherboard [");
		builder.append(super.toString());
		builder.append(", sizeType=");
		builder.append(sizeType);
		builder.append(", power=");
		builder.append(power);
		builder.append(", isCooler=");
		builder.append(isCooler);
		builder.append("]");
		return builder.toString();
	}
}
