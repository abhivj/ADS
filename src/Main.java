import DataCleaning.*;
import autoEncoder.*;
import MultivariateNormalDistributionMixture.*;
import Histogram.*;
import Stats.*;
import Clustering.*;
import regression.*;
import weka.filters.unsupervised.attribute.MLPAutoencoder;
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
		//ConvertToBinary ctb = new ConvertToBinary();
		//ctb.convertBinary("d:/Experiment/AllDatasets/", "d:/Experiment/convertedToBinary/");
		
		
		//Step 1
		//ConvertToBinary ctb = new ConvertToBinary();
		//ctb.convertBinary("d:/Experiment/AllDatasets/", "d:/Experiment/convertedToBinary/");
		
		//Step 2
		//TrainingDataCleaner.CleanAndModify("d:/Experiment/ExperimentFeb/AllDatasetsConverted/", "d:/Experiment/ExperimentFeb/BinaryDatasetLarge/", true, true, true,true,true,8,100);
		
		//Step 3
		//ModifyBinary.modifyTo01("d:/Experiment/ExperimentFeb/3BinrayDatasetLargeDeleted/", "d:/Experiment/ExperimentFeb/4BinarySeparatedDatasets/");
				
		//Step4
		//MakeHistogram.createBins("d:/Experiment/ExperimentFeb/4BinarySeparatedDatasets/", 0, 20, "d:/Experiment/ExperimentFeb/reports/", "HistogramBinReport.csv");
		
		//Step 5 (optional)
		/*
		CompareHistogram.BinaryClassComparision("d:/Experiment/ExperimentFeb/reports/HistogramBinReport.csv",
				146, 
				"d:/Experiment/ExperimentFeb/reports/BinaryComparision-HistogramBin-Kolmogrov.txt", 
				10);
		*/
		
		//Fresh Start Independent process
	/*
		Regressor reg = new Regressor();
		RegressorTraining[] RT = reg.createTrainingData("d:/Experiment/ExperimentFeb/1AllDatasetsConverted/", "d:/Experiment/ExperimentFeb/temp1/", "d:/Experiment/ExperimentFeb/temp2/", true, true, true, true, true, 8, 100, 0, 20, 2, 2, 2);
		
		RegressorTraining print = new RegressorTraining();
		print.printInFeatureVector("d:/Experiment/ExperimentFeb/reports/", "AttributeFile.csv", RT,2);
		*/
		
		/*
		GenerateRegressionData GRD = new GenerateRegressionData();
		GRD.generateFiles("d:/Experiment/ExperimentFeb/reports/AttributeFile.csv",
				"d:/Experiment/ExperimentFeb/reports/LargeDatasets-Accuracy.csv", 
				"d:/Experiment/ExperimentFeb/reports/AccuracyReg/");
	*/
		/*
		RegressionAndRanking RAR = new RegressionAndRanking();
		RAR.createModel("d:/Experiment/ExperimentFeb/reports/AccuracyReg/",
				"d:/Experiment/ExperimentFeb/reports/Accuracy-AllModel.csv",
				"d:/Experiment/ExperimentFeb/reports/LargeDatasets-Accuracy.csv", 1480, 141);
		*/
	/*	
		GenerateRanks GR = new GenerateRanks();
		GR.generateRankFile("d:/Experiment/ExperimentFeb/reports/Accuracy-AllModel.csv", 
				"d:/Experiment/ExperimentFeb/reports/Accuracy-AllModel-Experiment-Ranking.csv", 
				"d:/Experiment/ExperimentFeb/reports/Accuracy-AllModel-Experiment-Ranking-TopK.txt", 
				"d:/Experiment/ExperimentFeb/reports/Accuracy-AllModel-Experiment-Ranking-topKNames1.txt", 10);
		*/
		/*
		GenerateRanks GR = new GenerateRanks();
		GR.generateRankFile("d:/Experiment/ExperimentFeb/reports/LargeDatasets-Accuracy.csv", 
				"d:/Experiment/ExperimentFeb/reports/Accuracy-AllModel-Experiment-Ranking-Temp.csv", 
				"d:/Experiment/ExperimentFeb/reports/Accuracy-AllModel-Experiment-Ranking-TopK-Modified-temp.txt", 
				"d:/Experiment/ExperimentFeb/reports/Accuracy-AllModel-Experiment-Ranking-topKNames1-temp.txt", 10);
		*/
		/*
		TopMatching TM = new TopMatching();
		TM.compareTwoFile("d:/Experiment/ExperimentFeb/testForTop7/actual.txt",
				"d:/Experiment/ExperimentFeb/testForTop7/predicted.txt",
				7,"d:/Experiment/ExperimentFeb/testForTop7/TopK-result.csv");
		*/
		/*
		GenerateRanks GR = new GenerateRanks();
		GR.generateRankFile("d:/Experiment/ExperimentFeb/testForTop7-44datasets/Accuracy-Base.csv", 
				"d:/Experiment/ExperimentFeb/testForTop7-44datasets/temp.txt", 
				"d:/Experiment/ExperimentFeb/testForTop7-44datasets/actual.txt", 
				"d:/Experiment/ExperimentFeb/testForTop7-44datasets/temp.txt", 13);
		
		GR.generateRankFile("d:/Experiment/ExperimentFeb/testForTop7-44datasets/Accuracy-Experiment.csv", 
				"d:/Experiment/ExperimentFeb/testForTop7-44datasets/temp.txt", 
				"d:/Experiment/ExperimentFeb/testForTop7-44datasets/predicted.txt", 
				"d:/Experiment/ExperimentFeb/testForTop7-44datasets/temp.txt", 13);
		
		*/
		/*
		RandomRankGenerator RRG = new RandomRankGenerator();
		RRG.fileGenerator("d:/Experiment/ExperimentFeb/reports/Accuracy-AllModel.csv", "d:/Experiment/ExperimentFeb/testForTop7-44datasets/random2.txt", 13);
		
		
		TopMatching TM = new TopMatching();
		TM.compareTwoFile("d:/Experiment/ExperimentFeb/testForTop7-44datasets/actual.txt",
				"d:/Experiment/ExperimentFeb/testForTop7-44datasets/random2.txt",
				7,"d:/Experiment/ExperimentFeb/testForTop7-44datasets/TopK-result-with-Random2.csv");
		/*
		TM.compareTwoFile("d:/Experiment/ExperimentFeb/testForTop7-44datasets/actual.txt",
				"d:/Experiment/ExperimentFeb/testForTop7-44datasets/predicted.txt",
				7,"d:/Experiment/ExperimentFeb/testForTop7-44datasets/TopK-result-with-predicted.csv");
		*/
		//Experiment after autoencoder started
		/*
		GenerateRegressionData GRD = new GenerateRegressionData();
		GRD.generateFiles("D:/Experiment/exp5/PCA/AllAttributesPCA.csv",
				"D:/Experiment/exp5/PCA/AllResultInAccuracyFormat1.csv", 
				"D:/Experiment/exp5/PCA/AccuracyReg/");
		
		RegressionAndRanking RAR = new RegressionAndRanking();
		RAR.createModel("D:/Experiment/exp5/PCA/AccuracyReg/",
				"D:/Experiment/exp5/PCA/Accuracy-auto-encoded.csv",
				"D:/Experiment/exp5/PCA/AllResultInAccuracyFormat1.csv", 1480, 141);
		
		
		GenerateRanks GR = new GenerateRanks();
		GR.generateRankFile("D:/Experiment/exp5/PCA/AllResultInAccuracyFormat1.csv",
				"D:/Experiment/exp5/PCA/temp.txt", 
				"D:/Experiment/exp5/PCA/actual.txt", 
				"D:/Experiment/exp5/PCA/temp.txt", 13);
		
		GR.generateRankFile("D:/Experiment/exp5/PCA/Accuracy-auto-encoded.csv",
				"D:/Experiment/exp5/PCA/temp.txt", 
				"D:/Experiment/exp5/PCA/predicted.txt", 
				"D:/Experiment/exp5/PCA/temp.txt", 13);
		
		TopMatching TM = new TopMatching();
		TM.compareTwoFile("D:/Experiment/exp5/PCA/actual.txt",
				"D:/Experiment/exp5/PCA/predicted.txt",7,
				"D:/Experiment/exp5/PCA/TopK-result-with-Autoencoder.csv");
		*/
		//Experiment after autoencoder ended
		
		
		//Starting Grouping algorithms in sets
		/*
		GenerateSets GS = new GenerateSets();
		
		CompareSets CS = new CompareSets();
		double ranks[] = new double[10];
		for(int i = 1;i<=10;i++)
		{
		//double range = (double)i/100;
		int range=i;
		GS.generateSets("d:/Experiment/ExperimentFeb/testForTop7-44datasets/Accuracy-Base.csv", range, "d:/Experiment/ExperimentFeb/testForTop7-44datasets/Accuracy-Base-SETS.csv");
		GS.generateSets("d:/Experiment/ExperimentFeb/testForTop7-44datasets/Accuracy-AfterAutoEncoder-Full-Dimension.csv", range, "d:/Experiment/ExperimentFeb/testForTop7-44datasets/Accuracy-AfterAutoEncoder-Full-Dimension-SETS.csv");
		ranks[i-1] = CS.CompareContainsInTop("d:/Experiment/ExperimentFeb/testForTop7-44datasets/Accuracy-Base-SETS.csv", "d:/Experiment/ExperimentFeb/testForTop7-44datasets/Accuracy-AfterAutoEncoder-Full-Dimension-SETS.csv");
		}
		for(int i=ranks.length-1;i>=0;i--)
		{
			System.out.println(ranks[i]);
		}
		*/
		/*
		GS.generateSets("d:/Experiment/exp4/Accuracy-AfterAutoEncoder-Full-Dimension.csv", 3, "d:/Experiment/ExperimentFeb/testForTop7-44datasets/Accuracy-Auto-SETS-0.02.csv");
		GS.generateSets("d:/Experiment/exp4/Accuracy-AfterAutoEncoder-Full-Dimension.csv", 3, "d:/Experiment/ExperimentFeb/testForTop7-44datasets/Accuracy-Auto-SETS-0.03.csv");
		GS.generateSets("d:/Experiment/ExperimentFeb/testForTop7-44datasets/Accuracy-Base.csv", 3, "d:/Experiment/ExperimentFeb/testForTop7-44datasets/Accuracy-Base-SETS-0.02.csv");
		GS.generateSets("d:/Experiment/ExperimentFeb/testForTop7-44datasets/Accuracy-Base.csv", 3, "d:/Experiment/ExperimentFeb/testForTop7-44datasets/Accuracy-Base-SETS-0.03.csv");
		GS.generateSets("d:/Experiment/ExperimentFeb/testForTop7-44datasets/Accuracy-Experiment.csv", 3, "d:/Experiment/ExperimentFeb/testForTop7-44datasets/Accuracy-Experiment-SETS-0.02.csv");
		GS.generateSets("d:/Experiment/ExperimentFeb/testForTop7-44datasets/Accuracy-Experiment.csv", 3, "d:/Experiment/ExperimentFeb/testForTop7-44datasets/Accuracy-Experiment-SETS-0.03.csv");
		*/
	
		/*
		AutoencoderWeka AW = new AutoencoderWeka();
		AW.runAutoEncoder("d:/Experiment/exp5/AdaBoostM1.arff", "d:/Experiment/exp5/auto-encoded-file.csv", 10);
		*/
		
		//Less Features
		// started on 24 march 2015
		
		/*
		Regressor reg = new Regressor();
		RegressorTraining[] RT = reg.createTrainingData("d:/Experiment/ExperimentFeb/dataset44/", "d:/Experiment/ExperimentFeb/temp1/", "d:/Experiment/ExperimentFeb/temp2/", true, true, true, true, true, 8, 100, 0, 20, 2, 2, 2);
		
		RegressorTraining print = new RegressorTraining();
		print.printInFeatureVector("d:/Experiment/ExperimentFeb/reports/", "AttributeFileExcludingCovariances44.csv", RT,2);
		*/
		
		/*
		GenerateRegressionData GRD = new GenerateRegressionData();
		GRD.generateFiles("d:/Experiment/ExperimentFeb/march24/AttributeFileExcludingCovariances44.csv",
				"d:/Experiment/ExperimentFeb/march24/Accuracy-Base.csv", 
				"d:/Experiment/ExperimentFeb/march24/AccuracyReg/");
	*/
		
		RegressionAndRanking RAR = new RegressionAndRanking();
		RAR.createModel("d:/Experiment/ExperimentFeb/march24/AccuracyReg/",
				"d:/Experiment/ExperimentFeb/march24/Accuracy-Experiment.csv",
				"d:/Experiment/ExperimentFeb/march24/Accuracy-Base.csv", 1480, 141);
		
		GenerateRanks GR = new GenerateRanks();
		GR.generateRankFile("d:/Experiment/ExperimentFeb/march24/Accuracy-Base.csv",
				"D:/Experiment/ExperimentFeb/march24/temp.txt", 
				"D:/Experiment/ExperimentFeb/march24/actual.txt", 
				"D:/Experiment/ExperimentFeb/march24/temp.txt",  13);
		
		GR.generateRankFile("D:/Experiment/exp5/PCA/Accuracy-auto-encoded.csv",
				"D:/Experiment/ExperimentFeb/march24/temp.txt", 
				"D:/Experiment/ExperimentFeb/march24/predicted.txt", 
				"D:/Experiment/ExperimentFeb/march24/temp.txt",  13);
		
		TopMatching TM = new TopMatching();
		TM.compareTwoFile("D:/Experiment/ExperimentFeb/march24/actual.txt",
						  "D:/Experiment/ExperimentFeb/march24/predicted.txt",7,
				"D:/Experiment/ExperimentFeb/march24/TopK-result.csv");
		
	}

}
