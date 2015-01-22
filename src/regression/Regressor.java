package regression;
import java.io.BufferedReader;
import java.io.File;


import org.apache.commons.io.FileUtils;

import weka.core.Instances;
import DataCleaning.*;
import MultivariateNormalDistributionMixture.*;
import Histogram.*;
import Stats.*;
import Clustering.*;

class Data
{
	
}

public class Regressor {
	
	

	public File[] readAllFiles(String inputFolder)
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
	    		 if (files.endsWith(".arff"))
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
	    		 if (files.endsWith(".arff"))
	    		 {
	    			 arffFiles[fileCounter] = listOfFiles[i];
	    			 fileCounter++;
	          
	    		 }
	    	 }
	     }
		return arffFiles;
	}
	
	public RegressorTraining[] createTrainingData(String inputFolder,String tempFolder1,String tempFolder2,boolean convertNominal,boolean doPCA,boolean doNormalization,boolean doRejectIfLess,boolean rejectIfNotBinary, int dimOfPCA,int minimumInstance,int type,int numberOfBins,int numberOfClasses,int numberOfClaster,int numOfGaussionsInEM) throws Exception
	{
		
		//We got all files in arffFile array. 
		File[] arffFiles = readAllFiles(inputFolder);
		RegressorTraining[] RT = new RegressorTraining[arffFiles.length];
		for(int i=0;i<arffFiles.length;i++)
		{
			RT[i] = new RegressorTraining();
			String fileName = arffFiles[i].getName().toString();
			RT[i].fileName = fileName;
			//Cleaning Traning data
			TrainingDataCleaner TDC = new TrainingDataCleaner();
			TDC.cleanAndModifyFile(inputFolder, fileName, tempFolder1, convertNominal, doPCA, doNormalization, doRejectIfLess, rejectIfNotBinary, dimOfPCA, minimumInstance);
			
			//Delete all previous
			
			
			//Modifying to {0 to N-1} classes and separating it
			ModifyBinary MB = new ModifyBinary();
			MB.modifyTo01File(tempFolder1,fileName, tempFolder2);
			
			
			//Creating Filename array
			String[] st = new String[numberOfClasses];
			for(int j=0;j<st.length;j++)
			{
				st[j] = String.valueOf(j)+fileName;
			}
			
			//Creating bin of Histogram
			MakeHistogram MH = new MakeHistogram();
			RT[i].histogramBins = new int[numberOfClasses][numberOfBins];
			RT[i].histogramBins = MH.createBinFile(tempFolder2, type, numberOfBins, numberOfClasses,st);
			
			//KMeans Clustering
			File[] splittedFiles = readAllFiles(tempFolder2);
			RT[i].co = new ClusterObject[splittedFiles.length];
			for(int j=0;j<st.length;j++)
			{
				KMeansClustering KMS = new KMeansClustering();
				RT[i].co[j] =  KMS.kMeansCluster(st[j], tempFolder2, numberOfClaster);
			}
			
			//Expectation Maximization algorithm
			RT[i].pp = new Pairs[splittedFiles.length];
			for(int j=0;j<st.length;j++)
			{
				ExpectationMaximizationAlgorithm EMA =new ExpectationMaximizationAlgorithm();
				RT[i].pp[j] = EMA.getMeanOfGaussionsFromEMFile(tempFolder2, st[j], numOfGaussionsInEM);
			}
			
			//Multivariate Normal Distribution
			RT[i].mnd = new MultivariateNormalDistribution[splittedFiles.length];
			for(int j=0;j<st.length;j++)
			{
				MultivariateNormalDistribution mndr = new MultivariateNormalDistribution();
				BufferedReader datafile = mndr.readDataFile(st[j],tempFolder2);
				Instances data = new Instances(datafile);
				RT[i].mnd[j] = mndr.fitModel(data);
			}
			
			/*
			File dir = new File(tempFolder2);
			String[] myFiles;
			if(dir.isDirectory())
			{
				 myFiles = dir.list();
	               for (int k=0; k<myFiles.length; k++) {
	                   File myFile = new File(dir, myFiles[k]); 
	                   if(myFile.delete())
	                   	System.out.println("deleted");
	                   	else	
	                   	System.out.println("rejected");
	                   	
	               }
			}
			*/
		//	File dir = new File(tempFolder2);
		//	FileUtils.cleanDirectory(dir);
			/*
			for(int j=0;j<splittedFiles.length;j++)
			{
				splittedFiles[j].setWritable(true);
				if(splittedFiles[j].delete())
					System.out.println("deleted");
               	else	
               	System.out.println("rejected");
			
			}
			*/
			
			/*
			File dir = new File(tempFolder2);
			//File dir = new File(tempFolder2);
			//Delete all files in tempfolder2
			FileUtils.cleanDirectory(dir); 
			//for(File file: splittedFiles) file.delete(); 
		*/
		}

		return RT;
	}
	
	
}
