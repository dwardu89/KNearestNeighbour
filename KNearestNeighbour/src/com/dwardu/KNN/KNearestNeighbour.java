package com.dwardu.KNN;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.dwardu.KNN.Objects.Iris;

public class KNearestNeighbour {
	private int k;
	private ArrayList<IrisDistance> irisDistance;

	public KNearestNeighbour(int k, Iris irisList[]) {
		super();
		this.k = k;
		this.irisDistance = new ArrayList<IrisDistance>();

		for (int i = 0; i < irisList.length; i++) {
			this.irisDistance.add(new IrisDistance(irisList[i]));
		}
		// TODO Throw exception if k is greater than the size of irisList
		// if( k>=irisList.length) throw a new error;
	}

	public int getK() {
		return k;
	}

	public void setK(int k) {
		this.k = k;
	}

	public IrisDistance[] getIrisList() {
		return irisDistance.toArray(new IrisDistance[0]);
	}

	public void setIrisList(IrisDistance[] irisList) {
		this.irisDistance = new ArrayList<IrisDistance>();

		for (int i = 0; i < irisList.length; i++) {
			this.irisDistance.add(irisList[i]);
		}
	}

	public void setIrisList(Iris[] irisList) {
		this.irisDistance = new ArrayList<IrisDistance>();

		for (int i = 0; i < irisList.length; i++) {
			this.irisDistance.add(new IrisDistance(irisList[i]));
		}
	}

	public Iris classifyIris(Iris iris,boolean sepalLength, boolean sepalWidth, boolean petalLength, boolean petalWidth) {
		for (IrisDistance item : irisDistance) {
			if (iris.getIndex() != item.getIris().getIndex())
			item.setDistance(iris,sepalLength,sepalWidth,petalLength,petalWidth);
			else	item.setDistance(Double.MAX_VALUE);
		}

		Collections.sort(irisDistance, new IrisDistanceComparator());

		List<IrisDistance> kNearestNeighbours = new ArrayList<IrisDistance>();
		
		for (int i = 0; i < k; i++) {
			kNearestNeighbours.add(irisDistance.get(i));
		} 

		iris.setClassifiedPlantClass(getIrisClass(kNearestNeighbours));

		return iris;
	}

	/*
	 * Classifies a list of iris
	 * 
	 * @param iris[] the array of iris to classify under the k nearest neighbour
	 * 
	 * @return iris[] the classified iris.
	 */
	public Iris[] classifyIris(Iris iris[],boolean sepalLength, boolean sepalWidth, boolean petalLength, boolean petalWidth) {
		for (int i = 0; i < iris.length; i++) {
			iris[i] = classifyIris(iris[i],sepalLength,sepalWidth,petalLength,petalWidth);
		}
		return iris;
	}

	public double getValidationError(Iris iris[]) {
		int correct = 0;
		// int incorrect = 0;

		for (int i = 0; i < iris.length; i++) {
			if (iris[i].classifiedCorrectly()) {
				correct++;
			}
			// else {
			// incorrect++;
			// }
		}

		return ((double) correct / iris.length * 100);
	}

	private String getIrisClass(List<IrisDistance> neighbours) {
		ArrayList<ClassCount> classes = new ArrayList<ClassCount>();
		for (IrisDistance neighbour : neighbours) {
			int index = -1;
			for (int i = 0; i < classes.size(); i++) {
				if (classes.get(i).className.equals(neighbour.getIris()
						.getPlantClass())) {
					index = i;
					break;
				}
			}
			if (index == -1) {
				classes.add(new ClassCount(neighbour.getIris().getPlantClass(),
						1));
			} else { 
				classes.get(index).count++;
			}
		}
		Collections.sort(classes, new ClassCountComparator());

		return classes.get(0).className;
	}

	private class ClassCount {
		private String className;
		private int count;

		public ClassCount(String className, int count) {
			super();
			this.className = className;
			this.count = count;
		}

		@Override
		public boolean equals(Object arg0) {
			if (arg0.getClass() == this.getClass()) {
				ClassCount cc = (ClassCount) arg0;
				return className.toLowerCase().compareTo(
						cc.className.toLowerCase()) == 0;
			} else {
				if (arg0.getClass() == irisDistance.getClass()) {
					IrisDistance cc = (IrisDistance) arg0;
					return className.toLowerCase().compareTo(
							cc.getIris().getPlantClass().toLowerCase()) == 0;
				}
				return false;
			}
		}
	}

	private class ClassCountComparator implements Comparator<ClassCount> {
		@Override
		public int compare(ClassCount a, ClassCount b) {
			double distance = a.count - b.count;

			if (distance < 0)
				return -1;
			if (distance > 0)
				return 1;
			return 0;
		}
	}
}
