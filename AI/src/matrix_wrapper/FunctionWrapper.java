package matrix_wrapper;

import org.jblas.DoubleMatrix;

public class FunctionWrapper {
	public static boolean isContentsBelowValue(DoubleMatrix m, double threshold) {
		if(m == null) return true;
		boolean belowThresh = true;
		for(int i=0; i<m.rows; i++) 
			for(int j=0; j<m.columns; j++) 
				if(m.get(i,j) > threshold) {
					belowThresh = false;
				}
		return belowThresh;
	}
	public static double getMaxValueInRowVec(DoubleMatrix m) {
		double maxVal = 0.0;
		for(int i=0; i<m.columns; i++) 
			if(m.get(0,i) > maxVal)
				maxVal = m.get(0,i);
		return maxVal;
	}
	public static boolean lessThanRowVec(DoubleMatrix m1, DoubleMatrix m2) {
		if(m1 == null || m2 == null) return true;
		return isContentsBelowValue(m1, getMaxValueInRowVec(m2));
	}
}
