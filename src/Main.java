import DataCleaning.*;
import MultivariateNormalDistributionMixture.*;
import Histogram.*;
import Stats.*;
import Clustering.*;
import regression.*;
import plots.*;
public class Main {

	public static void main(String[] args) throws Exception {
		
		//TrainingDataCleaner.CleanAndModify("./data/BinaryDatasetRaw/", "./data/BinaryDatasetRaw/ss/", true, true, true,true,true,8,100);
		//TrainingDataCleaner.CleanAndModify("d:/st/", "d:/st/st/", true, true, true,true,true,8,100);
		
		//ExpectationMaximizationAlgorithm.getMeanOfGaussionsFromEM("d:/st/",4);
		
		//ModifyBinary.modifyTo01("d:/st/st/", "d:/st/st/st/");
		//MultivariateNormalDistribution.runModelOverFolderForMean("d:/st/st/st/","MultivariateMeanVector.csv");
	
		//MakeHistogram.createBins("d:/st/st/st/", 0, 20, "d:/st/st/st/", "csvReport.csv");
		//CompareHistogram.BinaryClassComparision("d:/st/st/st/csvReport.csv", 28, "d:/st/st/st/BinaryComparision.txt", 10);
		//CompareHistogram.BinaryClassComparision("d:/st/st/st/csvReport.csv", 28, "d:/st/st/st/BinaryComparisionKolmogrov.txt", 10);
		
		//CompareHistogram.BinaryClassComparision("d:/st/st/st/csvReport.csv", 28, "d:/st/st/st/BinaryComparisionKolmogrov1.txt", 10);
		
		
		//CompareHistogram.saveHistogramInFileAnova("d:/st/st/st/csvReport.csv", 28, "d:/st/st/st/AnovaTest.csv");
		//CompareHistogram.saveHistogramInFileFriedman("d:/st/st/st/csvReport.csv", 28, "d:/st/st/st/FriedManTest.csv");
		//double dr = StatisticalSignificanceTest.AnovaPrValueForHistogramBin("d:/st/st/st/BinaryComparision.csv");
		//System.out.println(dr);
		
		
		//K Means Clustering
		
		/*
		KMeansClustering kmc = new KMeansClustering();
		//kmc.kMeansCluster("0kc3.arff","d:/st/st/st/",4);
		kmc.createFile("d:/st/st/st/", 4, "kMeansFeatureVector4.csv", 8);
		*/
		
		//Starting Experiment on complete data
		//TrainingDataCleaner.CleanAndModify("d:/Experiment/AllDatasets/", "d:/Experiment/exp1/", true, true, true,true,true,8,100);
		//ModifyBinary.modifyTo01("d:/Experiment/exp1/", "d:/Experiment/exp2/");
		
		//rg.createTrainingData("d:/Experiment/exp3/", "d:/Experiment/exp3/temp1/", "d:/Experiment/exp3/temp2/", 20, true, true, true, true, true, 8, 100);
		/*
		Regressor reg = new Regressor();
		RegressorTraining[] RT = reg.createTrainingData("d:/Experiment/exp3/", "d:/Experiment/exp3/temp1/", "d:/Experiment/exp3/temp2/", true, true, true, true, true, 8, 100, 0, 20, 2, 2, 2);
		
		RegressorTraining print = new RegressorTraining();
		print.printInFeatureVector("d:/Experiment/exp3/reports/", "FeatureWithHeadersAllDatasets.csv", RT,2);
		*/
		
		/*
		Regression regi = new Regression();
		regi.createModel("d:/Experiment/exp3/reports/reportsTry.csv","d:/Experiment/exp3/reports/RegressionReportsMPC5000.csv");
		*/
		/*
		MultiLayerPerceptronNetwork MPN = new MultiLayerPerceptronNetwork();
		MPN.createModel("d:/Experiment/exp4/regressionTrainingFiles/Accuracy/MultilayerPerceptron.csv",
				"d:/Experiment/exp4/baggingResults/bag-Accuracy-MultilayerPerceptron-50-80.csv", 50, 80);
		*/
		
		/*
		MPN.createModel("d:/Experiment/exp3/reports/reportsTry.csv",
				"d:/Experiment/exp3/reports/RegressionReportsMPCHidden2.csv",
				"d:/Experiment/exp3/reports/RegressionReportsMPCTraining2.csv",
				"d:/Experiment/exp3/reports/RegressionReportsMPCBoth1.csv");
		*/
		//Plots p = new Plots();
		//p.plotting();
		
		//GenerateRegressionData GRD = new GenerateRegressionData();
		//GRD.generateFiles("d:/Experiment/exp4/AllAttributes.csv", "d:/Experiment/exp4/performanceMeasures/TNRate.csv", "d:/Experiment/exp4/regressionTrainingFiles/TNRate/");
		
		
		//RegressionAndRanking RAR = new RegressionAndRanking();
		//RAR.createModel("d:/Experiment/exp4/regressionTrainingFiles/Precision/", "d:/Experiment/exp4/regressionResultsFromExperiment/Precision.csv", "d:/Experiment/exp4/performanceMeasures/Precision.csv", 1080, 99);
		
		/*
		GenerateRanks GR = new GenerateRanks();
		//GR.generateRankFile("d:/Experiment/exp4/resultFromAllAlgorithms.csv", "d:/Experiment/exp4/resultRank/Ranks.csv", "d:/Experiment/exp4/resultRank/topK.txt", "d:/Experiment/exp4/resultRank/topKNames.txt", 5);
		GR.generateRankFile("d:/Experiment/exp4/performanceMeasures/TNRate.csv", 
				"d:/Experiment/exp4/performanceMeasures/topResults/TNRate-Experiment.csv", 
				"d:/Experiment/exp4/performanceMeasures/topResults/TNRate-ExperimentTopK.txt", 
				"d:/Experiment/exp4/performanceMeasures/topResults/TNRate-ExperimenttopKNames1.txt", 10);
		*/
		
		//Class called for converting multiclass to binary
		ConvertToBinary ctb = new ConvertToBinary();
		ctb.convertBinary("d:/Experiment/AllDatasets/", "d:/Experiment/convertedToBinary/");
		
	}

}
