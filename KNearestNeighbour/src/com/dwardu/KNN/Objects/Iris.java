package com.dwardu.KNN.Objects;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/*
 * This is an object that represents the data in a class
 */
public class Iris {

	private double sepalLength;
	private double sepalWidth;
	private double petalLength;
	private double petalWidth;
	private String plantClass;
	private String classifiedPlantClass;
	private int index;

	public Iris(double sepalLength, double sepalWidth, double petalLength,
			double petalWidth, String plantClass, int index) {
		super();
		this.sepalLength = sepalLength;
		this.sepalWidth = sepalWidth;
		this.petalLength = petalLength;
		this.petalWidth = petalWidth;
		this.plantClass = plantClass;
		this.classifiedPlantClass = "";
		this.index = index;
	}

	public double getSepalLength() {
		return sepalLength;
	}

	public void setSepalLength(double sepalLength) {
		this.sepalLength = sepalLength;
	}

	public double getSepalWidth() {
		return sepalWidth;
	}

	public void setSepalWidth(double sepalWidth) {
		this.sepalWidth = sepalWidth;
	}

	public double getPetalLength() {
		return petalLength;
	}

	public void setPetalLength(double petalLength) {
		this.petalLength = petalLength;
	}

	public double getPetalWidth() {
		return petalWidth;
	}

	public void setPetalWidth(double petalWidth) {
		this.petalWidth = petalWidth;
	}

	public String getPlantClass() {
		return plantClass;
	}

	public void setPlantClass(String plantClass) {
		this.plantClass = plantClass;
	}

	public String getClassifiedPlantClass() {
		return this.classifiedPlantClass;
	}

	public void setClassifiedPlantClass(String irisClass) {
		// TODO Auto-generated method stub
		this.classifiedPlantClass = irisClass;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	/*
	 * Computes the distance between two iris plants
	 */
	public double computeDistance(Iris other,boolean sepalLength, boolean sepalWidth, boolean petalLength, boolean petalWidth) {
		double a = this.getSepalLength() - other.getSepalLength();
		if(!sepalLength) a = 0;
		double b = this.getSepalWidth() - other.getSepalWidth();
		if(!sepalWidth) b = 0;
		double c = this.getPetalLength() - other.getPetalLength();
		if(!petalLength) c = 0;
		double d = this.getPetalWidth() - other.getPetalWidth();
		if(!petalWidth) d = 0;

		return Math.sqrt((a * a) + (b * b) + (c * c) + (d * d));
	}

	/*
	 * Opens a CSV file and parses it and returns iris objects
	 */
	public static Iris[] openCsv(String path) throws IOException {
		List<Iris> irisList = new ArrayList<Iris>();

		// 1.Load the file through a string parser
		String csv = readFile(path, Charset.defaultCharset());

		// 2.Split the lines into an array
		String[] aIrisRaw = csv.split("\n");

		// 3.For each line, split by the commas, and extract the values into an
		// Iris object
		for (int i = 0; i < aIrisRaw.length; i++) {
			String[] aRow = aIrisRaw[i].split(",");
			// TODO Create an Exception that will be thrown if there are not
			// enough columns for the iris data set
			// 4.Add the iris object to the irisList Array
			Iris irisObj = new Iris(Double.parseDouble(aRow[0]),
					Double.parseDouble(aRow[1]), Double.parseDouble(aRow[2]),
					Double.parseDouble(aRow[3]), aRow[4],i);
			irisList.add(irisObj);
		}

		// 5.Return the iris object as an array
		return irisList.toArray(new Iris[0]);
	}

	private static String readFile(String path, Charset encoding)
			throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return encoding.decode(ByteBuffer.wrap(encoded)).toString();
	}

	public boolean classifiedCorrectly() {
		return this.plantClass.toLowerCase().compareTo(
				this.classifiedPlantClass.toLowerCase()) == 0;
	}
}
