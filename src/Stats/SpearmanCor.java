package Stats;
import org.apache.commons.math3.stat.correlation.SpearmansCorrelation;
import org.apache.commons.math3.stat.correlation.KendallsCorrelation;
import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;
import org.apache.commons.math3.stat.correlation.StorelessCovariance;

public class SpearmanCor {

	public double value(double[] a, double[] b)
	{
		double val=0;
		SpearmansCorrelation sp = new SpearmansCorrelation();
		val = sp.correlation(a, b);
		return val;
	}
	
	public double kendalls(double[] a,double[] b)
	{
		double val=0;
		KendallsCorrelation KC = new KendallsCorrelation();
		val = KC.correlation(a, b);
		return val;
	}
	public double spearmans(double[] a,double[] b)
	{
		double val=0;
		SpearmansCorrelation sp = new SpearmansCorrelation();
		val = sp.correlation(a, b);
		return val;
	}
	public double pearsons(double[] a,double[] b)
	{
		double val=0;
		PearsonsCorrelation PC = new PearsonsCorrelation();
		val = PC.correlation(a, b);
		return val;
	}
	
}
