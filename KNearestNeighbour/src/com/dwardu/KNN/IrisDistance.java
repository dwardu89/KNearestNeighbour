package com.dwardu.KNN;

import com.dwardu.KNN.Objects.Iris;

public class IrisDistance {

	private Iris iris;
	private double distance;
		
	public IrisDistance(Iris iris) {
		super();
		this.iris = iris;
	}
	public Iris getIris() {
		return iris;
	}
	public void setIris(Iris iris) {
		this.iris = iris;
	}
	public double getDistance() {
		return distance;
	}
	public void setDistance(double distance) {
		this.distance = distance;
	}
	public void setDistance(Iris other,boolean sepalLength, boolean sepalWidth, boolean petalLength, boolean petalWidth)
	{
		this.distance = this.iris.computeDistance(other,sepalLength,sepalWidth,petalLength,petalWidth);
	}	
}
