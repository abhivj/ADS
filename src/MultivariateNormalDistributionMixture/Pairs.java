package MultivariateNormalDistributionMixture;

public class Pairs {
	String filename;
	double meanValue[][];
	double variance[][];
	
	public Pairs(String name,double[][] mean,double[][] var)
    {
        this.filename = name;
        meanValue = mean;
        variance = var;
    }

    public String getFileName()   { return filename; }
    public double[][] getMean() { return meanValue; }
    public double[][] getVariance() { return variance;}
}
