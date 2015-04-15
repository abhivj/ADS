import java.io.File;

import DataCleaning.*;
import autoEncoder.*;
import MultivariateNormalDistributionMixture.*;
import Histogram.*;
import Stats.*;
import Clustering.*;
import regression.*;
import plots.*;
public class Main {

	
	public static File[] readAllFiles(String inputFolder)
	{
		File folder = new File(inputFolder);
		File[] listOfFiles = folder.listFiles();
		String files;
	    
		int fileCounter=0;
	    for (int i = 0; i < listOfFiles.length; i++) 
	    {
	    	 if (listOfFiles[i].isFile()) 
	    	 {
	    		 files = listOfFiles[i].getName();
	    		 if (files.endsWith(".csv"))
	    		 {
	    			 fileCounter++;
	    		 }
	    	 }
	     }
	     File[] arffFiles = new File[fileCounter];
	     fileCounter=0;
	     for (int i = 0; i < listOfFiles.length; i++) 
	     {
	 
	    	 if (listOfFiles[i].isFile()) 
	    	 {
	    		 files = listOfFiles[i].getName();
	    		 if (files.endsWith(".csv"))
	    		 {
	    			 arffFiles[fileCounter] = listOfFiles[i];
	    			 fileCounter++;
	          
	    		 }
	    	 }
	     }
		return arffFiles;
	}
	
	
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
		
		//Delete all files in tempfolders before start
		
		
		/*
		Regressor reg = new Regressor();
		RegressorTraining[] RT = reg.createTrainingData("d:/Experiment/large/dataset/dataset44/", "d:/Experiment/large/dataset/dataset44/temp11/", "d:/Experiment/large/dataset/dataset44/temp22/", true, true, true, true, true, 8, 100, 0, 20, 2, 2, 2);
		
		RegressorTraining print = new RegressorTraining();
		print.printInFeatureVector("d:/Experiment/large/dataset/dataset44/report1/", "AttributeFile.csv", RT,2);
		
		
		
		GenerateRegressionData GRD = new GenerateRegressionData();
		
		GRD.generateFiles("d:/Experiment/large/dataset/dataset44/report1/AttributeFile.csv",	//Attribute File from above step
				"d:/Experiment/large/dataset/dataset44/report1/Accuracy-Base.csv", 								//Performance File
				"d:/Experiment/large/dataset/dataset44/regression/algo70/");									//TargetFolder
	
		
		RegressionAndRanking RAR = new RegressionAndRanking();
		RAR.createModel("d:/Experiment/large/dataset/dataset44/regression/algo70/",									//Folder which contains all regression files
				"d:/Experiment/large/dataset/dataset44/report1/Accuracy-Experiment.csv",		//SavePath
				"d:/Experiment/ExperimentFeb/march24/Accuracy-Base.csv",									//Performance File
				1480, 
				141,false,10);				
		
		GenerateRanks GR = new GenerateRanks();
		GR.generateRankFile("d:/Experiment/large/dataset/dataset44/report1/Accuracy-Base.csv", 					//Base File which Contains true result
				"D:/Experiment/ExperimentFeb/march24/temp.txt", 
				"D:/Experiment/ExperimentFeb/march24/actual.txt", 
				"D:/Experiment/ExperimentFeb/march24/temp.txt",  13);
		
		GR.generateRankFile("d:/Experiment/large/dataset/dataset44/report1/Accuracy-Experiment.csv",	//Prediction From Experiment
				"D:/Experiment/ExperimentFeb/march24/temp.txt", 
				"D:/Experiment/ExperimentFeb/march24/predicted.txt", 
				"D:/Experiment/ExperimentFeb/march24/temp.txt",  13);
		
		TopMatching TM = new TopMatching();
		TM.compareTwoFile("D:/Experiment/ExperimentFeb/march24/actual.txt",
						  "D:/Experiment/ExperimentFeb/march24/predicted.txt",7,
						  "d:/Experiment/large/dataset/dataset44/report1/Top-k-result.csv");					//Top K file showing percentage match
		*/
		//ExpectationMaximizationAlgorithm ema = new ExpectationMaximizationAlgorithm();
		//ema.getMeanOfGaussionsFromEMFile("D:/Experiment/ExperimentFeb/dataset441/", "a1a.arff",2);
	

		//Artificial CSV to arff Converter
		//CSVToArff ARF = new CSVToArff();
		//ARF.cleanAndModify("D:/Experiment/large/dataset/expD/", "D:/Experiment/large/dataset/expD/");
	
		
		//Clean Data
		//TrainingDataCleaner.CleanAndModify("D:/Experiment/large/dataset/expD/", "D:/Experiment/large/dataset/expDarff/","D:/Experiment/large/dataset/expDselected/", true, true, true,true,true,8,100);
		
		/*
		Regressor reg = new Regressor();
		RegressorTraining[] RT = reg.createTrainingData("D:/Experiment/large/dataset/dataset44/", "D:/Experiment/large/dataset/dataset44/temp1/","D:/Experiment/large/dataset/dataset44/temp2/", true, true, true, true, true, 8, 100, 0, 20, 2, 2, 2);
		
		RegressorTraining print = new RegressorTraining();
		print.printInFeatureVector("D:/Experiment/large/dataset/dataset44/report/", "AttributeFile.csv", RT,2);
		
		*/
		/*
		GenerateRegressionData GRD = new GenerateRegressionData();
		
		GRD.generateFiles("d:/Experiment/large/dataset/expDarff/report/AttributeFile.csv",	//Attribute File from above step
				"d:/Experiment/large/dataset/expDarff/report/AlgoAccuracy-484-70-8.csv", 								//Performance File
				"d:/Experiment/large/dataset/expDarff/regression/algo70/");									//TargetFolder
	
		
		RegressionAndRanking RAR = new RegressionAndRanking();
		RAR.createModel("d:/Experiment/large/dataset/expDarff/regression/algo70/",									//Folder which contains all regression files
				"d:/Experiment/large/dataset/expDarff/report/ExperimentAccuracy-484-70-8.csv",		//SavePath
				"d:/Experiment/large/dataset/expDarff/report/AlgoAccuracy-484-70-8.csv",									//Performance File
				740, 
				130,
				true,40);				
		
		GenerateRanks GR = new GenerateRanks();
		GR.generateRankFile("d:/Experiment/large/dataset/expDarff/report/AlgoAccuracy-484-12-8.csv",						//Base File which Contains true result
				"D:/Experiment/ExperimentFeb/march24/temp.txt", 
				"D:/Experiment/ExperimentFeb/march24/actual1.txt", 
				"D:/Experiment/ExperimentFeb/march24/temp.txt",  70);
		
		GR.generateRankFile("d:/Experiment/large/dataset/expDarff/report/ExperimentAccuracy-484-12-8.csv", 	//Prediction From Experiment
				"D:/Experiment/ExperimentFeb/march24/temp.txt", 
				"D:/Experiment/ExperimentFeb/march24/predicted1.txt", 
				"D:/Experiment/ExperimentFeb/march24/temp.txt",  70);
		
		TopMatching TM = new TopMatching();
		TM.compareTwoFile("D:/Experiment/ExperimentFeb/march24/actual1.txt",
						  "D:/Experiment/ExperimentFeb/march24/predicted1.txt",70,
						  "d:/Experiment/large/dataset/expDarff/report/TopK-result-70-bag40.csv");		
		*/
		/*
		GenerateRegressionData GRD = new GenerateRegressionData();
		
		GRD.generateFiles("d:/Experiment/large/autoencoder/AttributeFile-50.csv",	//Attribute File from above step
				"d:/Experiment/large/autoencoder/Accuracy-Base.csv", 							//Performance File
				"d:/Experiment/large/autoencoder/algo13/");									//TargetFolder
	
		
		RegressionAndRanking RAR = new RegressionAndRanking();
		RAR.createModel("d:/Experiment/large/autoencoder/algo13/",									//Folder which contains all regression files
				"d:/Experiment/large/autoencoder/ExperimentAccuracy-44-13-8.csv",		//SavePath
				"d:/Experiment/large/autoencoder/Accuracy-Base.csv",									//Performance File
				740, 
				130,
				false,1);
				*/
		/*
		GenerateRanks GR = new GenerateRanks();
		GR.generateRankFile("d:/Experiment/large/dataset/expDarff/report/AlgoAccuracy-484-70-8.csv", 					//Base File which Contains true result
				"D:/Experiment/ExperimentFeb/march24/temp.txt", 
				"D:/Experiment/ExperimentFeb/march24/actual.txt", 
				"D:/Experiment/ExperimentFeb/march24/temp.txt",70);
		
		GR.generateRankFile("d:/Experiment/large/dataset/expDarff/report/ExperimentAccuracy-484-70-8.csv",	//Prediction From Experiment
				"D:/Experiment/ExperimentFeb/march24/temp.txt", 
				"D:/Experiment/ExperimentFeb/march24/predicted.txt", 
				"D:/Experiment/ExperimentFeb/march24/temp.txt",70);
		
		TopMatching TM = new TopMatching();
		TM.compareTwoFile("D:/Experiment/ExperimentFeb/march24/actual.txt",
						  "D:/Experiment/ExperimentFeb/march24/predicted.txt",70,
						  "d:/Experiment/large/dataset/expDarff/report/Top-k-result-70-484-MLP.csv");	
		
		//RandomProbabilityCalculator rbc = new RandomProbabilityCalculator();
		//rbc.printProbability("d:/Experiment/large/autoencoder/Top-k-result-prob-13.csv",13);
		*/
		/*
		File[] arffFiles = readAllFiles("d:/Experiment/large/dataset/dataset44/report1/allRegT");
		
		for(int i=0;i<arffFiles.length;i++)
		{
		GenerateRanks GR = new GenerateRanks();
		GR.generateRankFile("d:/Experiment/large/dataset/dataset44/report1/AlgorithmRunAndAccuracyResult.csv",						//Base File which Contains true result
				"D:/Experiment/ExperimentFeb/march24/temp.txt", 
				"D:/Experiment/ExperimentFeb/march24/actual1.txt", 
				"D:/Experiment/ExperimentFeb/march24/temp.txt",  13);
		
		GR.generateRankFile("d:/Experiment/large/dataset/dataset44/report1/allRegT/"+arffFiles[i].getName().toString(),		//Prediction From Experiment
				"D:/Experiment/ExperimentFeb/march24/temp.txt", 
				"D:/Experiment/ExperimentFeb/march24/predicted"+String.valueOf(i)+".txt", 
				"D:/Experiment/ExperimentFeb/march24/temp.txt",  13);
		
		TopMatching TM = new TopMatching();
		TM.compareTwoFile("D:/Experiment/ExperimentFeb/march24/actual1.txt",
				"D:/Experiment/ExperimentFeb/march24/predicted"+String.valueOf(i)+".txt", 13,
						  "d:/Experiment/large/dataset/dataset44/report1/topkT/"+arffFiles[i].getName().toString());	
		
		}
		
		TopMatching TM = new TopMatching();
		TM.allResult("d:/Experiment/large/dataset/dataset44/report1/topkT/", "d:/Experiment/large/dataset/dataset44/report1/All_Result_13_trail.csv",13);
		*/
		
		//Preparing Data for autoencoder
		
		/*
		Regressor reg = new Regressor();
		RegressorTraining[] RT = reg.createTrainingData("D:/Experiment/large/dataset/expDarff/", "D:/Experiment/large/dataset/expDarff/temp1/","D:/Experiment/large/dataset/expDarff/temp2/", true, true, true, true, true, 8, 100, 0, 20, 2, 2, 2);
		
		RegressorTraining print = new RegressorTraining();
		print.printInFeatureVector("D:/Experiment/large/Experiments/", "AttributeFile-ClusterValues-484.csv", RT,2);
		*/
		/*
		GenerateRegressionData GRD = new GenerateRegressionData();
		
		GRD.generateFiles("D:/Experiment/large/Experiments/AttributeFile-ClusterValues-484.csv",	//Attribute File from above step
				"D:/Experiment/large/Experiments/AlgorithmRunAndAccuracyResult-484-71-8.csv", 								//Performance File
				"D:/Experiment/large/Experiments/algo70/");									//TargetFolder
	
		
		RegressionAndRanking RAR = new RegressionAndRanking();
		RAR.createModel("D:/Experiment/large/Experiments/algo70/",									//Folder which contains all regression files
				"D:/Experiment/large/Experiments/ExperimentAccuracy-484-70-8.csv",		//SavePath
				"D:/Experiment/large/Experiments/AlgorithmRunAndAccuracyResult-484-71-8.csv",									//Performance File
				740, 
				130,
				true,40);				
		*/
		
		/*
		GenerateRanks GR = new GenerateRanks();
		GR.generateRankFile("D:/Experiment/large/Experiments/AlgorithmRunAndAccuracyResult-484-71-8.csv",						//Base File which Contains true result
				"D:/Experiment/ExperimentFeb/march24/temp.txt", 
				"D:/Experiment/ExperimentFeb/march24/actual1.txt", 
				"D:/Experiment/ExperimentFeb/march24/temp.txt",  70);
		
		GR.generateRankFile("D:/Experiment/large/Experiments/ExperimentAccuracy-484-70-8.csv", 	//Prediction From Experiment
				"D:/Experiment/ExperimentFeb/march24/temp.txt", 
				"D:/Experiment/ExperimentFeb/march24/predicted1.txt", 
				"D:/Experiment/ExperimentFeb/march24/temp.txt",  70);
		/*
		TopMatching TM = new TopMatching();
		TM.compareTwoFile("D:/Experiment/ExperimentFeb/march24/actual1.txt",
						  "D:/Experiment/ExperimentFeb/march24/predicted1.txt",70,
						  "D:/Experiment/large/Experiments/TopK-result-70-484-ClusterValue.csv");	
		
		
		*/
/*
		ExpectedValuesInK ex = new ExpectedValuesInK();
		//ex.ExpectedValue("ss", 50, 50);
		ex.ExpectationCalculationForAll("D:/Experiment/large/Experiments/ExpectedValues.csv", 70, 5, 5);
		
		TopMatching tpm = new TopMatching();
		tpm.compareExpectation("D:/Experiment/ExperimentFeb/march24/actual1.txt",  "D:/Experiment/ExperimentFeb/march24/predicted1.txt", 70, 5, 5, "D:/Experiment/large/Experiments/TopK-result-70-484-ExpectedAnalysis.csv");
	*/
		
	//	TopRankingGenerator trg = new TopRankingGenerator();
	//	trg.bestRanking("d:/Experiment/large/dataset/expDarff/report/AlgoAccuracy-484-70-8.csv", "d:/Experiment/large/dataset/expDarff/report/bestRank-484-70.txt", 484, 70);
		/*
		GenerateRanks GR = new GenerateRanks();
		GR.generateRankFile("d:/Experiment/large/dataset/expDarff/report/AlgoAccuracy-484-70-8.csv",						//Base File which Contains true result
				"D:/Experiment/ExperimentFeb/march24/temp.txt", 
				"d:/Experiment/large/dataset/expDarff/report/actual1.txt", 
				"D:/Experiment/ExperimentFeb/march24/temp.txt",  70);
		
		GR.generateRankFile("d:/Experiment/large/dataset/expDarff/report/Gaussion-ExperimentAccuracy-484-70-8.csv", 	//Prediction From Experiment
				"D:/Experiment/ExperimentFeb/march24/temp.txt", 
				"d:/Experiment/large/dataset/expDarff/report/predicted1.txt", 
				"D:/Experiment/ExperimentFeb/march24/temp.txt",  70);
		*/
	//	TopMatching TM = new TopMatching();
		/*TM.compareTwoFile("d:/Experiment/large/dataset/expDarff/report/actual1.txt",
						  "d:/Experiment/large/dataset/expDarff/report/predicted1.txt", 70,
						  "d:/Experiment/large/dataset/expDarff/report/top-k-result-acpc-484-70.csv");
		*/
	//	TM.compareTwoFile("d:/Experiment/large/dataset/expDarff/report/actual1.txt",
	//			  "d:/Experiment/large/dataset/expDarff/report/bestRank-484-70.txt", 70,
	//			  "d:/Experiment/large/dataset/expDarff/report/top-k-result-acbs-484-70.csv");
		/*
		RandomProbabilityCalculator rbc = new RandomProbabilityCalculator();
		rbc.printProbability("d:/Experiment/large/dataset/expDarff/report/Top-k-result-prob-70.csv",70);
		*/
		/*

		ExpectedValuesInK ex = new ExpectedValuesInK();
		//ex.ExpectedValue("ss", 50, 50);
		ex.ExpectationCalculationForAll("D:/Experiment/large/Experiments/ExpectedValues.csv", 70, 5, 5);
		
		TopMatching tpm = new TopMatching();
		tpm.compareExpectation("D:/Experiment/ExperimentFeb/march24/actual1.txt",  "D:/Experiment/ExperimentFeb/march24/predicted1.txt", 70, 5, 5, "D:/Experiment/large/Experiments/TopK-result-70-484-ExpectedAnalysis.csv");
	*/
		/*
		TopMatching tpm = new TopMatching();
		//tpm.compareExpectation("D:/Experiment/ExperimentFeb/march24/actual1.txt",  "D:/Experiment/ExperimentFeb/march24/predicted1.txt", 70, 5, 5, "D:/Experiment/large/Experiments/TopK-result-70-484-ExpectedAnalysis-Trail-Run.csv");
		tpm.compareExpectationPercentage("D:/Experiment/ExperimentFeb/march24/actual1.txt",  "D:/Experiment/ExperimentFeb/march24/predicted1.txt", 70, 5, 5, "D:/Experiment/large/Experiments/TopK-result-70-484-ExpectedAnalysis-Trail-Run.csv",484);
	*/
		double k = - Double.MAX_VALUE;
		System.out.println(k);
		if(k>-5.366)
		{
			System.out.print("jehhe");
		}
		
	}
	

}
