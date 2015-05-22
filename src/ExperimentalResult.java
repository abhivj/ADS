import java.io.File;

import plots.GenerateRanks;
import plots.TopMatching;
import plots.TopRankingGenerator;
import regression.ExpectedValuesInK;
import regression.RandomProbabilityCalculator;
import regression.Regressor;
import regression.RegressorTraining;
import Clustering.KMeansClustering;
import DataCleaning.GenerateRegressionData;
import DataCleaning.ModifyBinary;
import DataCleaning.PerformanceToRank;
import Histogram.*;
public class ExperimentalResult {

	
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
		
		
		// 44 Datasets
		
		//ModifyBinary.modifyTo01("d:/Experiment/large/dataset/dataset44/", "d:/Experiment/large/temp1/");
		//MakeHistogram.createBins("d:/Experiment/large/temp1/", 0, 20, "d:/Experiment/large/Experiments/", "Histogram_Bin.csv");
		//PerformanceToRank ptr = new PerformanceToRank();
		//ptr.ranker("D:/Experiment/large/Experiments/AlgorithmRunAndAccuracyResult-44-70-full.csv", "D:/Experiment/large/Experiments/Ranks-44-70-full.csv","D:/Experiment/large/Experiments/Transpose.csv","D:/Experiment/large/Experiments/Correlation.csv");
	/*	
		CompareHistTopK chtk = new CompareHistTopK();
		chtk.compare("D:/Experiment/large/Experiments/CorrelationTopK.csv", "D:/Experiment/large/Experiments/EuclidianHistCompareTopk.csv", 1, 44, 44, "D:/Experiment/large/Experiments/HistEuc_RankCorr_Comp.csv");
		chtk.compare("D:/Experiment/large/Experiments/CorrelationTopK.csv", "D:/Experiment/large/Experiments/KolMogrovTopk.csv", 1, 44, 44, "D:/Experiment/large/Experiments/HistKol_RankCorr_Comp.csv");
		chtk.compare("D:/Experiment/large/Experiments/CorrelationTopK.csv", "D:/Experiment/large/Experiments/KolMogrovBinarySepTopk.csv", 1, 44, 44, "D:/Experiment/large/Experiments/HistKolBinary_RankCorr_Comp.csv");
		
		
		RandomProbabilityCalculator rbc = new RandomProbabilityCalculator();
		rbc.printProbability("D:/Experiment/large/Experiments/Prob-44.csv",44);
		*/
		
		/*
		RankingByHistogram rh = new RankingByHistogram();
		rh.ranker("D:/Experiment/large/Experiments/EuclidianHistCompareTopk.csv", "D:/Experiment/large/Experiments/Correlation.csv", "D:/Experiment/large/Experiments/HistCompKt.txt", 44, 70, 5);
		
		GenerateRanks GR = new GenerateRanks();
		GR.generateRankFile("d:/Experiment/large/Experiments/AlgorithmRunAndAccuracyResult-44-70-full.csv", 					//Base File which Contains true result
				"D:/Experiment/ExperimentFeb/march24/temp.txt", 
				"d:/Experiment/large/Experiments/actual.txt", 
				"D:/Experiment/ExperimentFeb/march24/temp.txt",70);
		
		
		TopMatching TM = new TopMatching();
		TM.compareTwoFile("d:/Experiment/large/Experiments/actual.txt", 
						  "D:/Experiment/large/Experiments/HistCompKt.txt",70,
						  "D:/Experiment/large/Experiments/HistTopKresultt.csv");
		*/
		//RandomProbabilityCalculator rbc = new RandomProbabilityCalculator();
		//rbc.printProbability("D:/Experiment/large/Experiments/Prob-70.csv",70);
		/*
		
		File[] arffFiles = readAllFiles("d:/Experiment/large/dataset/expDarff/report/allReg/");
		
		for(int i=0;i<arffFiles.length;i++)
		{
		GenerateRanks GR = new GenerateRanks();
		GR.generateRankFile("d:/Experiment/large/dataset/expDarff/report/AlgoAccuracy-484-70-8.csv",						//Base File which Contains true result
				"D:/Experiment/ExperimentFeb/march24/temp.txt", 
				"D:/Experiment/ExperimentFeb/march24/actual1.txt", 
				"D:/Experiment/ExperimentFeb/march24/temp.txt",  12);
		
		GR.generateRankFile("d:/Experiment/large/dataset/expDarff/report/allReg/"+arffFiles[i].getName().toString(),		//Prediction From Experiment
				"D:/Experiment/ExperimentFeb/march24/temp.txt", 
				"D:/Experiment/ExperimentFeb/march24/predicted.txt", 
				"D:/Experiment/ExperimentFeb/march24/temp.txt",  12);
		
		TopMatching TM = new TopMatching();
		TM.compareTwoFile("D:/Experiment/ExperimentFeb/march24/actual1.txt",
				"D:/Experiment/ExperimentFeb/march24/predicted.txt", 12,
						  "d:/Experiment/large/dataset/expDarff/report/topk12/"+arffFiles[i].getName().toString());	
		
		}
		
		TopMatching TM = new TopMatching();
		TM.allResult("d:/Experiment/large/dataset/expDarff/report/topk/", "d:/Experiment/large/Experiments/All_Result_70_FullFeatures_484.csv",70);
		*/
		
		//Writing results-1 two graph plotted
		
		//This is to convert performance to rank
		//PerformanceToRank ptr = new PerformanceToRank();
		//ptr.ranker("D:/Experiment/large/Experiments/result/Accuracy-Base-44-13-8.csv", "D:/Experiment/large/Experiments/result/Ranks-44-13-8.csv","D:/Experiment/large/Experiments/result/Transpose.csv","D:/Experiment/large/Experiments/result/Correlation.csv");
			
		//Creating bins
		//Bins are created considering binary class separation
		//ModifyBinary.modifyTo01("d:/Experiment/large/dataset/dataset44/", "d:/Experiment/large/temp1/");
		//MakeHistogram.createBins("d:/Experiment/large/temp1/", 0, 20, "d:/Experiment/large/Experiments/result/", "Histogram_Bin.csv");

		//Apply rank correlation by correlationBasedComparision.java and get correlationtopk
		//Apply compareBinaryclass on bintranspose
		
		//Till now we get both similaritites
		//1. By correlation of ranks which is our base similaritites.
		//2. By Binary class sepration 1-norm distance
		//Now we compare above two 
		
		//First method is atleast one top k
		//Generate random
		//RandomProbabilityCalculator rbc = new RandomProbabilityCalculator();
		//rbc.printProbability("D:/Experiment/large/Experiments/result/Prob-43.csv",43);
		
		//Generate our similarity
		/*
		GenerateRanks GR = new GenerateRanks();
		GR.generateRankFile("d:/Experiment/large/Experiments/result/Accuracy-Base-44-13-8.csv", 					//Base File which Contains true result
				"d:/Experiment/large/Experiments/result/temp.txt", 
				"d:/Experiment/large/Experiments/result/actual.txt", 
				"d:/Experiment/large/Experiments/result/temp.txt",13);
		*/
		/*
		TopMatching TM = new TopMatching();
		TM.compareTwoFile("d:/Experiment/large/Experiments/result/CorrelationTopK.csv", 
						  "d:/Experiment/large/Experiments/result/EuclidianHistCompareTopk.csv",43,
						  "d:/Experiment/large/Experiments/result/DatasetSimilarity-classSeparation-44-13.csv");
		*/
		//from kolmogorov
		/*
		TopMatching TM = new TopMatching();
		TM.compareTwoFile("d:/Experiment/large/Experiments/result/CorrelationTopK.csv", 
						  "d:/Experiment/large/Experiments/result/KolMogrovTestTopk.csv",43,
						  "d:/Experiment/large/Experiments/result/DatasetSimilarity-kolmogorov-44-13.csv");
		*/
		/*
		ExpectedValuesInK ex = new ExpectedValuesInK();
		//ex.ExpectedValue("ss", 50, 50);
		ex.ExpectationCalculationForAll("D:/Experiment/large/Experiments/result/ExpectedValues.csv", 44, 3, 3);
		
		TopMatching tpm = new TopMatching();
		tpm.compareExpectation("d:/Experiment/large/Experiments/result/CorrelationTopK.csv",  "d:/Experiment/large/Experiments/result/KolMogrovTestTopk.csv", 44, 3, 3, "D:/Experiment/large/Experiments/result/TopK-result-13-44-ExpectedAnalysis.csv");
	*/
		
		//Writing result 2  
		//This is to convert performance to rank
		//PerformanceToRank ptr = new PerformanceToRank();
		//ptr.ranker("D:/Experiment/large/Experiments/result/Accuracy-Base-44-70-8.csv", "D:/Experiment/large/Experiments/result/Ranks-44-70-8.csv","D:/Experiment/large/Experiments/result/Transpose70.csv","D:/Experiment/large/Experiments/result/Correlation70.csv");
					
				//Apply rank correlation by correlationBasedComparision.java and get correlationtopk
				//Apply compareBinaryclass on bintranspose
				
				//Till now we get both similaritites
				//1. By correlation of ranks which is our base similaritites.
				//2. By Binary class sepration 1-norm distance
				//Now we compare above two 
				
				//First method is atleast one top k
				//Generate random
				//RandomProbabilityCalculator rbc = new RandomProbabilityCalculator();
				//rbc.printProbability("D:/Experiment/large/Experiments/result/Prob-43.csv",43);
				

		
				/*
				TopMatching TM = new TopMatching();
				TM.compareTwoFile("d:/Experiment/large/Experiments/result/CorrelationTopK70.csv", 
								  "d:/Experiment/large/Experiments/result/EuclidianHistCompareTopk.csv",43,
								  "d:/Experiment/large/Experiments/result/DatasetSimilarity-classSeparation-44-70.csv");
				
				//from kolmogorov
				
			
				TM.compareTwoFile("d:/Experiment/large/Experiments/result/CorrelationTopK70.csv", 
								  "d:/Experiment/large/Experiments/result/KolMogrovTestTopk.csv",43,
								  "d:/Experiment/large/Experiments/result/DatasetSimilarity-kolmogorov-44-70.csv");
				
				
				//ExpectedValuesInK ex = new ExpectedValuesInK();
				//ex.ExpectedValue("ss", 50, 50);
				//ex.ExpectationCalculationForAll("D:/Experiment/large/Experiments/result/ExpectedValues.csv", 44, 3, 3);
				
				TopMatching tpm = new TopMatching();
				tpm.compareExpectation("d:/Experiment/large/Experiments/result/CorrelationTopK70.csv",  "d:/Experiment/large/Experiments/result/EuclidianHistCompareTopk.csv", 44, 3, 3, "D:/Experiment/large/Experiments/result/TopK-result-70-44-ExpectedAnalysisEuc.csv");
				tpm.compareExpectation("d:/Experiment/large/Experiments/result/CorrelationTopK70.csv",  "d:/Experiment/large/Experiments/result/KolMogrovTestTopk.csv", 44, 3, 3, "D:/Experiment/large/Experiments/result/TopK-result-70-44-ExpectedAnalysisKol.csv");
				*/
		
		
		
		//Writing result 3 for 484 datasets
		//This is to convert performance to rank
		/*
		PerformanceToRank ptr = new PerformanceToRank();
		ptr.ranker("D:/Experiment/large/Experiments/result/Accuracy-Base-484-70-8.csv", "D:/Experiment/large/Experiments/result/Ranks-484-70-8.csv","D:/Experiment/large/Experiments/result/Transpose484.csv","D:/Experiment/large/Experiments/result/Correlation70.csv");
			
		//Creating bins
		//Bins are created considering binary class separation
		ModifyBinary.modifyTo01("d:/Experiment/large/dataset/expDarff/", "d:/Experiment/large/temp2/");
		MakeHistogram.createBins("d:/Experiment/large/temp2/", 0, 20, "d:/Experiment/large/Experiments/result/", "Histogram_Bin_484.csv");
*/
		//Apply rank correlation by correlationBasedComparision.java and get correlationtopk
		//Apply compareBinaryclass on bintranspose
		
		//Till now we get both similaritites
		//1. By correlation of ranks which is our base similaritites.
		//2. By Binary class sepration 1-norm distance
		//Now we compare above two 
		
		//First method is atleast one top k
		//Generate random
		/*
		RandomProbabilityCalculator rbc = new RandomProbabilityCalculator();
		rbc.printProbability("D:/Experiment/large/Experiments/result/Prob-483.csv",483);
		
		//Generate our similarity
		
		
		
		TopMatching TM = new TopMatching();
		TM.compareTwoFile("d:/Experiment/large/Experiments/result/CorrelationTopK70.csv", 
						  "d:/Experiment/large/Experiments/result/EuclidianHistCompareTopk70.csv",483,
						  "d:/Experiment/large/Experiments/result/DatasetSimilarity-classSeparation-484-70.csv");
		
		//from kolmogorov
		
		
		TM.compareTwoFile("d:/Experiment/large/Experiments/result/CorrelationTopK70.csv", 
						  "d:/Experiment/large/Experiments/result/KolMogrovTestTopk70.csv",483,
						  "d:/Experiment/large/Experiments/result/DatasetSimilarity-kolmogorov-484-70.csv");
		
		/*
		ExpectedValuesInK ex = new ExpectedValuesInK();
		//ex.ExpectedValue("ss", 50, 50);
		ex.ExpectationCalculationForAll("D:/Experiment/large/Experiments/result/ExpectedValues.csv", 44, 3, 3);
		*/
		/*
		TopMatching tpm = new TopMatching();
		tpm.compareExpectation("d:/Experiment/large/Experiments/result/CorrelationTopK70.csv",  "d:/Experiment/large/Experiments/result/EuclidianHistCompareTopk70.csv", 484, 10, 10, "D:/Experiment/large/Experiments/result/TopK-result-70-484-ExpectedAnalysisEuc.csv");
		tpm.compareExpectation("d:/Experiment/large/Experiments/result/CorrelationTopK70.csv",  "d:/Experiment/large/Experiments/result/KolMogrovTestTopk70.csv", 484, 10, 10, "D:/Experiment/large/Experiments/result/TopK-result-70-484-ExpectedAnalysisKol.csv");
		*/
		/*
		ExpectedValuesInK ex = new ExpectedValuesInK();

		ex.ExpectationCalculationForAll("D:/Experiment/large/Experiments/result/ExpectedValues484.csv", 484, 10, 10);
		*/
		
		//Writing result 
		//Predicting ranking by histogram
		
		
		
		// 44 Datasets
		
		//ModifyBinary.modifyTo01("d:/Experiment/large/dataset/dataset44/", "d:/Experiment/large/temp1/");
		//MakeHistogram.createBins("d:/Experiment/large/temp1/", 0, 20, "d:/Experiment/large/Experiments/", "Histogram_Bin.csv");
		//PerformanceToRank ptr = new PerformanceToRank();
		//ptr.ranker("D:/Experiment/large/Experiments/result/Accuracy-Base-484-70-8.csv", "D:/Experiment/large/Experiments/result/Ranks-484-70-8.csv","D:/Experiment/large/Experiments/result/Transpose70.csv","D:/Experiment/large/Experiments/result/Correlation70.csv");
		/*
		CompareHistTopK chtk = new CompareHistTopK();
		chtk.compare("D:/Experiment/large/Experiments/result/CorrelationTopK.csv", "D:/Experiment/large/Experiments/result/EuclidianHistCompareTopk.csv", 1, 44, 44, "D:/Experiment/large/Experiments/result/HistEuc_RankCorr_Comp.csv");
		chtk.compare("D:/Experiment/large/Experiments/result/CorrelationTopK.csv", "D:/Experiment/large/Experiments/KolMogrovTopk.csv", 1, 44, 44, "D:/Experiment/large/Experiments/result/HistKol_RankCorr_Comp.csv");
		chtk.compare("D:/Experiment/large/Experiments/result/CorrelationTopK.csv", "D:/Experiment/large/Experiments/result/KolMogrovBinarySepTopk.csv", 1, 44, 44, "D:/Experiment/large/Experiments/result/HistKolBinary_RankCorr_Comp.csv");
		*/
		/*
		RandomProbabilityCalculator rbc = new RandomProbabilityCalculator();
		rbc.printProbability("D:/Experiment/large/Experiments/result/Prob-70.csv",70);
		
		
		
		RankingByHistogram rh = new RankingByHistogram();
		rh.ranker("D:/Experiment/large/Experiments/result/EuclidianHistCompareTopk.csv", "D:/Experiment/large/Experiments/result/Correlation70.csv", "D:/Experiment/large/Experiments/result/HistCompKt.txt", 484, 70, 20);
	//	rh.ranker("D:/Experiment/large/Experiments/result/KolMogrovTopk.csv", "D:/Experiment/large/Experiments/result/Correlation70.csv", "D:/Experiment/large/Experiments/result/KolComp.txt", 484, 70, 20);
		rh.ranker("D:/Experiment/large/Experiments/result/KolMogrovTestTopk.csv", "D:/Experiment/large/Experiments/result/Correlation70.csv", "D:/Experiment/large/Experiments/result/KolCompsep.txt", 484, 70, 20);
		
		GenerateRanks GR = new GenerateRanks();
		GR.generateRankFile("d:/Experiment/large/Experiments/result/Accuracy-Base-484-70-8.csv", 					//Base File which Contains true result
				"D:/Experiment/ExperimentFeb/march24/temp.txt", 
				"d:/Experiment/large/Experiments/result/actual.txt", 
				"D:/Experiment/ExperimentFeb/march24/temp.txt",70);
		
		
		TopMatching TM = new TopMatching();
		TM.compareTwoFile("d:/Experiment/large/Experiments/result/actual.txt", 
						  "D:/Experiment/large/Experiments/result/HistCompKt.txt",70,
						  "D:/Experiment/large/Experiments/result/HistTopKresultt.csv");
		/*
		TM.compareTwoFile("d:/Experiment/large/Experiments/result/actual.txt", 
				  "D:/Experiment/large/Experiments/result/KolComp.txt",70,
				  "D:/Experiment/large/Experiments/result/KolCompTopKresultt.csv");
		
		TM.compareTwoFile("d:/Experiment/large/Experiments/result/actual.txt", 
				  "D:/Experiment/large/Experiments/result/KolCompsep.txt",70,
				  "D:/Experiment/large/Experiments/result/KolCompsepTopKresultt.csv");
*/
		
		//Writing result cluster value analysis vector creation
		
		//We alteardy filtered the data no need to do that again. Use comment method for fresh data. It also descard datasets.
		/*
		Regressor reg = new Regressor();
		//RegressorTraining[] RT = reg.createTrainingData("d:/Experiment/large/dataset/dataset44/", "d:/Experiment/large/dataset/dataset44/temp1/", "d:/Experiment/large/dataset/dataset44/temp2/", true, true, true, true, true, 8, 100, 0, 20, 2, 2, 2);
		RegressorTraining[] RT = reg.createTrainingDataFiltered("d:/Experiment/large/dataset/expDarff/temp1/", "d:/Experiment/large/dataset/expDarff/temp2/", true, true, true, true, true, 8, 100, 0, 20, 2, 2, 2);
		
		RegressorTraining print = new RegressorTraining();
		print.printInFeatureVector("d:/Experiment/large/Experiments/result/", "AttributeFileClusterValues484.csv", RT,2);
	*/
		//Now till we get clustered data vector. Now we associate it with accuracy to build regression models
		/*
		GenerateRegressionData GRD = new GenerateRegressionData();
		GRD.generateFiles("d:/Experiment/large/Experiments/result/AttributeFileClusterValues44.csv",	//Attribute File from above step
				"d:/Experiment/large/Experiments/result/Accuracy-Base-44-13-8.csv", 								//Performance File
				"d:/Experiment/large/Experiments/result/regression/dataset44/algo13/");									//TargetFolder
		GRD.generateFiles("d:/Experiment/large/Experiments/result/AttributeFileClusterValues44.csv",	//Attribute File from above step
				"d:/Experiment/large/Experiments/result/Accuracy-Base-44-70-8.csv", 								//Performance File
				"d:/Experiment/large/Experiments/result/regression/dataset44/algo70/");	
		GRD.generateFiles("d:/Experiment/large/Experiments/result/AttributeFileClusterValues484.csv",	//Attribute File from above step
				"d:/Experiment/large/Experiments/result/Accuracy-Base-484-70-8.csv", 								//Performance File
				"d:/Experiment/large/Experiments/result/regression/dataset484/algo70/");	
		*/
		//Considering all data vector
		/*
		Regressor reg = new Regressor();
		//RegressorTraining[] RT = reg.createTrainingData("d:/Experiment/large/dataset/dataset44/", "d:/Experiment/large/dataset/dataset44/temp1/", "d:/Experiment/large/dataset/dataset44/temp2/", true, true, true, true, true, 8, 100, 0, 20, 2, 2, 2);
		RegressorTraining[] RT = reg.createTrainingDataFiltered("d:/Experiment/large/dataset/expDarff/", "d:/Experiment/large/dataset/expDarff/temp2/", true, true, true, true, true, 8, 100, 0, 20, 2, 2, 2);
		
		RegressorTraining print = new RegressorTraining();
		print.printInFeatureVector("d:/Experiment/large/Experiments/result/", "AttributeFile484.csv", RT,2);
	*/
		//Now till we get clustered data vector. Now we associate it with accuracy to build regression models
		/*
		GenerateRegressionData GRD = new GenerateRegressionData();
		GRD.generateFiles("d:/Experiment/large/Experiments/result/AttributeFile44.csv",	//Attribute File from above step
				"d:/Experiment/large/Experiments/result/Accuracy-Base-44-13-8.csv", 								//Performance File
				"d:/Experiment/large/Experiments/result/regression/dataset44/algo13/all/");									//TargetFolder
		GRD.generateFiles("d:/Experiment/large/Experiments/result/AttributeFile44.csv",	//Attribute File from above step
				"d:/Experiment/large/Experiments/result/Accuracy-Base-44-70-8.csv", 								//Performance File
				"d:/Experiment/large/Experiments/result/regression/dataset44/algo70/all/");	
		GRD.generateFiles("d:/Experiment/large/Experiments/result/AttributeFile484.csv",	//Attribute File from above step
				"d:/Experiment/large/Experiments/result/Accuracy-Base-484-70-8.csv", 								//Performance File
				"d:/Experiment/large/Experiments/result/regression/dataset484/algo70/all/");	
		*/
		/*
		File[] arffFiles = readAllFiles("d:/Experiment/large/Experiments/result/regression/dataset484/algo70/result");
		//D:\Experiment\large\Experiments\result\regression\dataset44\algo13\all\result
		//Accuracy-Base-44-13-8
		for(int i=0;i<arffFiles.length;i++)
		{
		GenerateRanks GR = new GenerateRanks();
		GR.generateRankFile("d:/Experiment/large/Experiments/result/Accuracy-Base-484-70-8.csv",						//Base File which Contains true result
				"D:/Experiment/ExperimentFeb/march24/temp.txt", 
				"D:/Experiment/ExperimentFeb/march24/actual1.txt", 
				"D:/Experiment/ExperimentFeb/march24/temp.txt",  70);
		
		GR.generateRankFile("d:/Experiment/large/Experiments/result/regression/dataset484/algo70/result/"+arffFiles[i].getName().toString(),		//Prediction From Experiment
				"D:/Experiment/ExperimentFeb/march24/temp.txt", 
				"D:/Experiment/ExperimentFeb/march24/predicted"+String.valueOf(i)+".txt", 
				"D:/Experiment/ExperimentFeb/march24/temp.txt",  70);
		
		TopMatching TM = new TopMatching();
		TM.compareTwoFile("D:/Experiment/ExperimentFeb/march24/actual1.txt",
				"D:/Experiment/ExperimentFeb/march24/predicted"+String.valueOf(i)+".txt", 70,
				"d:/Experiment/large/Experiments/result/regression/dataset484/algo70/topk/"+arffFiles[i].getName().toString());	
		
		}
		
		TopMatching TM = new TopMatching();
		TM.allResult("d:/Experiment/large/Experiments/result/regression/dataset484/algo70/topk/", "d:/Experiment/large/Experiments/result/regression/dataset484/algo70/All_Result_70_20.csv",70);
		
		*/
		
		//ExpectedValuesInK ex = new ExpectedValuesInK();
		//ex.ExpectedValue("ss", 50, 50);
		//ex.printPercentage("D:/Experiment/large/Experiments/result/ExpectedValues.csv", 70, 5, 5);
		/*
		File[] arffFiles = readAllFiles("d:/Experiment/large/Experiments/result/regression/dataset44/algo13/result");
		//D:\Experiment\large\Experiments\result\regression\dataset44\algo13\all\result
		//Accuracy-Base-44-13-8
		for(int i=0;i<arffFiles.length;i++)
		{
		GenerateRanks GR = new GenerateRanks();
		GR.generateRankFile("d:/Experiment/large/Experiments/result/Accuracy-Base-44-13-8.csv",						//Base File which Contains true result
				"D:/Experiment/ExperimentFeb/march24/temp.txt", 
				"D:/Experiment/ExperimentFeb/march24/actual.txt", 
				"D:/Experiment/ExperimentFeb/march24/temp.txt",  13);
		
		GR.generateRankFile("d:/Experiment/large/Experiments/result/regression/dataset44/algo13/result/"+arffFiles[i].getName().toString(),		//Prediction From Experiment
				"D:/Experiment/ExperimentFeb/march24/temp.txt", 
				"D:/Experiment/ExperimentFeb/march24/predicted"+arffFiles[i].getName().toString()+".txt", 
				"D:/Experiment/ExperimentFeb/march24/temp.txt",  13);
		
		TopMatching TM = new TopMatching();
		System.out.println("Working on "+arffFiles[i].getName().toString());
		TM.compareTwoFile("D:/Experiment/ExperimentFeb/march24/actual.txt",
				"D:/Experiment/ExperimentFeb/march24/predicted"+arffFiles[i].getName().toString()+".txt",  13,
				"d:/Experiment/large/Experiments/result/regression/dataset44/algo13/topk/"+arffFiles[i].getName().toString());	
		
		TM.compareExpectationPercentage("D:/Experiment/ExperimentFeb/march24/actual.txt",
				"D:/Experiment/ExperimentFeb/march24/predicted"+arffFiles[i].getName().toString()+".txt", 
				13, 1, 1, 
				"d:/Experiment/large/Experiments/result/regression/dataset44/algo13/topkt/"+arffFiles[i].getName().toString(), 44);
		
		}
		TopMatching TM = new TopMatching();
		//TM.allResult("d:/Experiment/large/Experiments/result/regression/dataset484/algo70/topkt/", "d:/Experiment/large/Experiments/result/regression/dataset484/algo70/All_Result_70_20_Expected.csv",70);
		TM.allResultExpectation("d:/Experiment/large/Experiments/result/regression/dataset44/algo13/topkt/", "d:/Experiment/large/Experiments/result/regression/dataset44/result/All_Result_13_Expactation_cluster_value_44_datasets.csv", 13, 1, 1);
		TM.allResult("d:/Experiment/large/Experiments/result/regression/dataset44/algo13/topk/", "d:/Experiment/large/Experiments/result/regression/dataset44/result/All_Result_13_Probability_cluster_value_44_datasets.csv",13);
		*/
		/*
		TopMatching TM = new TopMatching();
		GenerateRanks GR = new GenerateRanks();
		TopRankingGenerator trg = new TopRankingGenerator();
		trg.bestRanking("d:/Experiment/large/Experiments/result/Accuracy-Base-44-70-8.csv", "d:/Experiment/large/Experiments/result/best-44-70-8.txt", 44, 70);
			
		GR.generateRankFile("d:/Experiment/large/Experiments/result/Accuracy-Base-44-70-8.csv",						//Base File which Contains true result
				"D:/Experiment/ExperimentFeb/march24/temp.txt", 
				"D:/Experiment/ExperimentFeb/march24/actual.txt", 
				"D:/Experiment/ExperimentFeb/march24/temp.txt",  70);
		
		TM.compareTwoFile("D:/Experiment/ExperimentFeb/march24/actual.txt",
				"d:/Experiment/large/Experiments/result/best-44-70-8.txt",  70,
				"d:/Experiment/large/Experiments/result/topk-prob-best-44-70-8.csv");
		
		TM.compareExpectationPercentage("D:/Experiment/ExperimentFeb/march24/actual.txt",
				"d:/Experiment/large/Experiments/result/best-44-70-8.txt", 
				70, 5, 5, 
				"d:/Experiment/large/Experiments/result/topk-expected-best-44-70-8.csv", 44);
		*/
		
		//Experiment with autoencoder
		//We have created attribute file with autoencoder
		
		/*GenerateRegressionData GRD = new GenerateRegressionData();
	GRD.generateFiles("d:/Experiment/large/autoencoder/clustervalue/AttributeFileClusterValues44-stack-25.csv",	//Attribute File from above step
				"d:/Experiment/large/Experiments/result/Accuracy-Base-44-13-8.csv", 								//Performance File
				"d:/Experiment/large/autoencoder/clustervalue/datasets/stack-25/dataset44/algo13/");									//TargetFolder
		GRD.generateFiles("d:/Experiment/large/autoencoder/clustervalue/AttributeFileClusterValues44-stack-25.csv",	//Attribute File from above step
				"d:/Experiment/large/Experiments/result/Accuracy-Base-44-70-8.csv", 								//Performance File
				"d:/Experiment/large/autoencoder/clustervalue/datasets/stack-25/dataset44/algo70/");	
		GRD.generateFiles("d:/Experiment/large/autoencoder/clustervalue/AttributeFileClusterValues484-stack-25.csv",	//Attribute File from above step
				"d:/Experiment/large/Experiments/result/Accuracy-Base-484-70-8.csv", 								//Performance File
				"d:/Experiment/large/autoencoder/clustervalue/datasets/stack-25/dataset484/algo70/");	
		*/
	/*	File[] arffFiles = readAllFiles("d:/Experiment/large/autoencoder/clustervalue/datasets/stack-25/dataset484/algo70/result/");
		//D:\Experiment\large\Experiments\result\regression\dataset44\algo13\all\result
		//Accuracy-Base-44-13-8
		for(int i=0;i<arffFiles.length;i++)
		{
		GenerateRanks GR = new GenerateRanks();
		GR.generateRankFile("d:/Experiment/large/Experiments/result/Accuracy-Base-484-70-8.csv",						//Base File which Contains true result
				"D:/Experiment/ExperimentFeb/march24/temp.txt", 
				"D:/Experiment/ExperimentFeb/march24/actual.txt", 
				"D:/Experiment/ExperimentFeb/march24/temp.txt",  70);
		
		GR.generateRankFile("d:/Experiment/large/autoencoder/clustervalue/datasets/stack-25/dataset484/algo70/result/"+arffFiles[i].getName().toString(),		//Prediction From Experiment
				"D:/Experiment/ExperimentFeb/march24/temp.txt", 
				"D:/Experiment/ExperimentFeb/march24/predicted"+arffFiles[i].getName().toString()+".txt", 
				"D:/Experiment/ExperimentFeb/march24/temp.txt",  70);
		
		TopMatching TM = new TopMatching();
		System.out.println("Working on "+arffFiles[i].getName().toString());
		TM.compareTwoFile("D:/Experiment/ExperimentFeb/march24/actual.txt",
				"D:/Experiment/ExperimentFeb/march24/predicted"+arffFiles[i].getName().toString()+".txt",  70,
				"d:/Experiment/large/autoencoder/clustervalue/datasets/stack-25/dataset484/algo70/topk/"+arffFiles[i].getName().toString());	
		
		TM.compareExpectationPercentage("D:/Experiment/ExperimentFeb/march24/actual.txt",
				"D:/Experiment/ExperimentFeb/march24/predicted"+arffFiles[i].getName().toString()+".txt", 
				70, 5, 5, 
				"d:/Experiment/large/autoencoder/clustervalue/datasets/stack-25/dataset484/algo70/topkt/"+arffFiles[i].getName().toString(), 484);
		
		}
		TopMatching TM = new TopMatching();
		//TM.allResult("d:/Experiment/large/Experiments/result/regression/dataset484/algo70/topkt/", "d:/Experiment/large/Experiments/result/regression/dataset484/algo70/All_Result_70_20_Expected.csv",70);
		TM.allResultExpectation("d:/Experiment/large/autoencoder/clustervalue/datasets/stack-25/dataset484/algo70/topkt/", "d:/Experiment/large/Experiments/result/regression/dataset484/result/All_Result_70_Expactation_cluster_value_stack-25_dim_484_datasets.csv", 70, 5, 5);
		TM.allResult("d:/Experiment/large/autoencoder/clustervalue/datasets/stack-25/dataset484/algo70/topk/", "d:/Experiment/large/Experiments/result/regression/dataset484/result/All_Result_70_Probability_cluster_value_stack-25_dim_484_datasets.csv",70);
		
		*/
	
		//test exp
	//	KMeansClustering KMS = new KMeansClustering();
	//	KMS.kMeansCluster("0MultivariateData1.arff", "d:/Experiment/large/dataset/expDarff/temp2/", 2);
		
		/*
		Regressor reg = new Regressor();
		//RegressorTraining[] RT = reg.createTrainingData("d:/Experiment/large/dataset/dataset44/", "d:/Experiment/large/dataset/dataset44/temp1/", "d:/Experiment/large/dataset/dataset44/temp2/", true, true, true, true, true, 8, 100, 0, 20, 2, 2, 2);
		RegressorTraining[] RT = reg.createTrainingDataFiltered("d:/Experiment/large/dataset/expDarff/temp1/", "d:/Experiment/large/dataset/expDarff/temp2/", true, true, true, true, true, 8, 100, 0, 20, 2, 2, 2);
		
		RegressorTraining print = new RegressorTraining();
		print.printInFeatureVector("d:/Experiment/large/Experiments/result/", "AttributeFile484Test.csv", RT,2);
	*/
		
		//ttttttttttt
		Regressor reg = new Regressor();
		//RegressorTraining[] RT = reg.createTrainingData("d:/Experiment/large/dataset/dataset44/", "d:/Experiment/large/dataset/dataset44/temp1/", "d:/Experiment/large/dataset/dataset44/temp2/", true, true, true, true, true, 8, 100, 0, 20, 2, 2, 2);
		RegressorTraining[] RT = reg.createTrainingDataFiltered("d:/Experiment/large/dataset/dataset44/temp1/", "d:/Experiment/large/dataset/dataset44/temp2/", true, true, true, true, true, 8, 100, 0, 20, 2, 2, 2);
		
		RegressorTraining print = new RegressorTraining();
		print.printInFeatureVector("d:/Experiment/large/Experiments/result/", "AttributeFileClusterValues484_test.csv", RT,2);
		
	}
		
	

}
