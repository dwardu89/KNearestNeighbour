package com.dwardu.KNN.Objects;

public class IrisDistanceConfig {
	private boolean sepalLength;
	private boolean sepalWidth;
	private boolean petalLength;
	private boolean petalWidth;

	public IrisDistanceConfig(boolean sepalLength, boolean sepalWidth,
			boolean petalLength, boolean petalWidth) {
		super();
		this.sepalLength = sepalLength;
		this.sepalWidth = sepalWidth;
		this.petalLength = petalLength;
		this.petalWidth = petalWidth;
	}

	public boolean isSepalLength() {
		return sepalLength;
	}

	public boolean isSepalWidth() {
		return sepalWidth;
	}

	public boolean isPetalLength() {
		return petalLength;
	}

	public boolean isPetalWidth() {
		return petalWidth;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (sepalLength)
			sb.append("SL");
		if (sepalWidth)
			sb.append("SW");
		if (petalLength)
			sb.append("PL");
		if (petalWidth)
			sb.append("PW");
		return sb.toString();
	}

}
