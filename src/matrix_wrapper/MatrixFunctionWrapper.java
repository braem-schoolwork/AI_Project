package matrix_wrapper;

import org.jblas.DoubleMatrix;

/**
 * Wrapper for jblas' DoubleMatrix
 * 
 * @author Braemen Stoltz
 * @version 1.0
 */
public class MatrixFunctionWrapper
{
	
	/**
	 * Checks to see if every value in the matrix is below a certain value
	 * @param m				matrix
	 * @param threshold		value to be checked against
	 * @return				<code>true</code> if every value in the matrix is below the threshold
	 * 						<code>false</code> otherwise
	 */
	public static boolean isContentsBelowValue(DoubleMatrix m, double threshold) {
		if(m == null)
			return true;
		boolean belowThresh = true;
		for(int i=0; i<m.rows; i++) 
			for(int j=0; j<m.columns; j++) 
				if(m.get(i,j) > threshold)
					belowThresh = false;
		return belowThresh;
	}
	
	/**
	 * Gets the maximum value in the matrix
	 * @param m		matrix
	 * @return		maximum value in matrix m
	 */
	public static double getMaxValueInRowVec(DoubleMatrix m) {
		double maxVal = 0.0;
		for(int i=0; i<m.columns; i++) 
			if(m.get(0,i) > maxVal)
				maxVal = m.get(0,i);
		return maxVal;
	}
	
	/**
	 * Compares two matrix vectors, finding which one is less than the other
	 * @param m1	first matrix
	 * @param m2	second matrix
	 * @return		<code>true</code> if m1 is less than m2
	 * 				<code>false</code> otherwise
	 */
	public static boolean lessThanRowVec(DoubleMatrix m1, DoubleMatrix m2) {
		if(m1 == null || m2 == null)
			return true;
		return isContentsBelowValue(m1, getMaxValueInRowVec(m2));
	}
	
	/**
	 * Computes the average value of the values in the matrix
	 * @param m		matrix
	 * @return		the average value of the values in the matrix
	 */
	public static double avgValues(DoubleMatrix m) {
		double allValues 	= 0;
		double amountOfVals = 0;
		for(int i=0; i<m.rows; i++)
			for(int j=0; j<m.columns; j++) {
				allValues 		+= m.get(i,j);
				amountOfVals	+= 1.0;
			}
		return allValues/amountOfVals;
	}
	
}
