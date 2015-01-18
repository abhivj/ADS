import DataCleaning.*;
import MultivariateNormalDistributionMixture.*;
import Histogram.*;
import Stats.*;
import Clustering.*;
public class Main {

	public static void main(String[] args) throws Exception {
		
		//TrainingDataCleaner.CleanAndModify("./data/BinaryDatasetRaw/", "./data/BinaryDatasetRaw/ss/", true, true, true,true,true,8,100);
		//TrainingDataCleaner.CleanAndModify("d:/st/", "d:/st/st/", true, true, true,true,true,8,100);
		// TODO Auto-generated method stub
		//ExpectationMaximizationAlgorithm.getMeanOfGaussionsFromEM("d:/st/",4);
		
		//ModifyBinary.modifyTo01("d:/st/st/", "d:/st/st/st/");
		//MultivariateNormalDistribution.runModelOverFolderForMean("d:/st/st/st/","MultivariateMeanVector.csv");
	
	//	MakeHistogram.createBins("d:/st/st/st/", 0, 20, "d:/st/st/st/", "csvReport.csv");
		//CompareHistogram.BinaryClassComparision("d:/st/st/st/csvReport.csv", 28, "d:/st/st/st/BinaryComparision.txt", 10);
		//CompareHistogram.BinaryClassComparision("d:/st/st/st/csvReport.csv", 28, "d:/st/st/st/BinaryComparisionKolmogrov.txt", 10);
		
		
		
		//CompareHistogram.saveHistogramInFileAnova("d:/st/st/st/csvReport.csv", 28, "d:/st/st/st/AnovaTest.csv");
		//CompareHistogram.saveHistogramInFileFriedman("d:/st/st/st/csvReport.csv", 28, "d:/st/st/st/FriedManTest.csv");
		//double dr = StatisticalSignificanceTest.AnovaPrValueForHistogramBin("d:/st/st/st/BinaryComparision.csv");
		//System.out.println(dr);
		
		
		//K Means Clustering
		
		KMeansClustering kmc = new KMeansClustering();
		//kmc.kMeansCluster("0kc3.arff","d:/st/st/st/",4);
		kmc.createFile("d:/st/st/st/", 4, "kMeansFeatureVector4.csv", 8);
		
		
	}

}
