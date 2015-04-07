import java.io.File;

import plots.GenerateRanks;
import plots.TopMatching;
import regression.RandomProbabilityCalculator;
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
		}
		
	

}
