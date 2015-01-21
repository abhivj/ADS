package MultivariateNormalDistributionMixture;

public class Pairs {
	String filename;
	double meanValue[][];
	
	public Pairs(String name,double[][] dval)
    {
        this.filename = name;
        meanValue = dval;
    }

    public String getFileName()   { return filename; }
    public double[][] getMean() { return meanValue; }
}
