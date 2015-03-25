package statMeasures;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.SingularValueDecomposition;
import org.apache.commons.math3.linear.RealMatrix;


public class SVD {
	
	public double[] getSigma(double[][] matrixVector)
	{
		RealMatrix matrix = MatrixUtils.createRealMatrix(matrixVector);
		SingularValueDecomposition SVD = new SingularValueDecomposition(matrix);
		double[] sigma= SVD.getSingularValues();
		return sigma;
	}
	
	
}
