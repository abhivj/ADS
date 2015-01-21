package Clustering;

public class ClusterObject {
	int[] clusterInstances;
	double[] clusterValues;
	double distance[][];
	int numberOfAttribute;
	
	public int getNumberOfAttribute()
	{
		return numberOfAttribute;
	}
	public void setNumberOfAttribute(int numberOfAttribute)
	{
		this.numberOfAttribute = numberOfAttribute;
	}
	public int[] getClusterInstances() {
		return clusterInstances;
	}
	public void setClusterInstances(int[] clusterInstances) {
		this.clusterInstances = clusterInstances;
	}
	public double[] getClusterValues() {
		return clusterValues;
	}
	public void setClusterValues(double[] clusterValues) {
		this.clusterValues = clusterValues;
	}
	public double[][] getDistance() {
		return distance;
	}
	public void setDistance(double[][] distance) {
		this.distance = distance;
	}
	public void setInitializeDistanceArray(int numberOfCluster,int attributes)
	{
		distance = new double[numberOfCluster][attributes];
	}
}
