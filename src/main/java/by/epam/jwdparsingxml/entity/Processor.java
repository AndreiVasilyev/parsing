package by.epam.jwdparsingxml.entity;

public class Processor extends Device {

	private String codeName;
	private int frequency;
	private int power;

	public String getCodeName() {
		return codeName;
	}

	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}

	public int getFrequency() {
		return frequency;
	}

	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

	public int getPower() {
		return power;
	}

	public void setPower(int power) {
		this.power = power;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((codeName == null) ? 0 : codeName.hashCode());
		result = prime * result + frequency;
		result = prime * result + power;
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
		Processor other = (Processor) obj;
		if (codeName == null) {
			if (other.codeName != null)
				return false;
		} else if (!codeName.equals(other.codeName))
			return false;
		if (frequency != other.frequency)
			return false;
		if (power != other.power)
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Processor [");
		builder.append(super.toString());
		builder.append(", codeName=");
		builder.append(codeName);
		builder.append(", frequency=");
		builder.append(frequency);
		builder.append(", power=");
		builder.append(power);
		builder.append("]");
		return builder.toString();
	}
}
