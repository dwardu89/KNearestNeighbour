package com.dwardu.KNN;

import java.util.Comparator;

public class IrisDistanceComparator implements Comparator<IrisDistance> {

	@Override
	public int compare(IrisDistance a, IrisDistance b) {
		double distance = a.getDistance() - b.getDistance();

		if (distance < 0)
			return -1;
		if (distance > 0)
			return 1;
		return 0;
	}

}
