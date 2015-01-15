package Histogram;

import Stats.Statistics;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Array;
import java.util.*;
import java.io.*;

import weka.clusterers.EM;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.classifiers.*;
import weka.filters.*;
import weka.core.*;
import weka.classifiers.Evaluation;
import weka.classifiers.meta.Bagging;
import weka.classifiers.trees.RandomForest;
import weka.classifiers.trees.SimpleCart;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.ReplaceMissingValues;
import weka.filters.unsupervised.attribute.Remove;
import weka.attributeSelection.GainRatioAttributeEval;
import weka.classifiers.Evaluation;
import weka.classifiers.functions.Logistic;
import weka.classifiers.meta.Bagging;
import weka.classifiers.trees.BFTree;
import weka.classifiers.trees.J48;
import weka.classifiers.trees.RandomForest;
import weka.classifiers.trees.j48.*;
import weka.core.Debug;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.Utils;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import weka.attributeSelection.RankSearch;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;
import weka.filters.unsupervised.attribute.ReplaceMissingValues;
import weka.attributeSelection.*;
import weka.classifiers.evaluation.MarginCurve;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import MultivariateNormalDistributionMixture.ExpectationMaximizationAlgorithm;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.evaluation.NominalPrediction;
import weka.classifiers.rules.DecisionTable;
import weka.classifiers.rules.OneR;
import weka.classifiers.rules.PART;
import weka.classifiers.trees.DecisionStump;
import weka.classifiers.trees.J48;
import weka.core.FastVector;
import weka.core.Instances;
import weka.classifiers.lazy.IBk;
import weka.classifiers.meta.AdaBoostM1;
import weka.classifiers.meta.Bagging;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.functions.Logistic;
import weka.classifiers.functions.VotedPerceptron;
import wlsvm.WLSVM;
import weka.filters.supervised.attribute.NominalToBinary;
//import weka.attributeSelection.PrincipalComponents;
import weka.filters.unsupervised.attribute.Normalize;
import weka.filters.unsupervised.attribute.PrincipalComponents;
import weka.filters.unsupervised.attribute.Remove;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.NominalToString;
public class MakeHistogram {

	/**
	 * Reads a filename at specific path and return a inputReader object
	 * @param filename
	 * @param path
	 * @return
	 */
	public static BufferedReader readDataFile(String filename,String pathOfFile)
	{
		String pathToSave = pathOfFile;
        BufferedReader inputReader = null;
        try
        {
            inputReader = new BufferedReader(new FileReader(pathToSave+filename));
        }
        catch (FileNotFoundException ex)
        {
            System.err.println("File not found: " + filename);
        }
        
        return inputReader;
    }
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
	/**
	 * take a string and path and write csv report on that location
	 * @param stringToWrite
	 * @param path
	 */
	public static void writeCSVReport(String stringToWrite,String path)
	{
		String savePath = path;
		try {
			
			File file = new File(savePath);
 
			// if file doesnt exists, then create it
			if (!file.exists())
			{
				file.createNewFile();
			}
 			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(stringToWrite);
			bw.close();
			System.out.println("Done File Writing : "+savePath);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
	}
	public static void createBins(String fileFolderPath,int type,int numberOfBins,String saveFileFolderPath,String csvFileName) throws Exception
	{
		/*
		 * Type 0 - Std dev based Binning
		 * Type 1 - Mean Bases Bining
		 * Type 2 - Median Based Partitioning
		 */
		MakeHistogram hist = new MakeHistogram();
		File[] arffFiles = hist.readAllFiles(fileFolderPath);
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<arffFiles.length;i++)
		{
			hist = new MakeHistogram();
			BufferedReader datafile = hist.readDataFile(arffFiles[i].getName().toString(),fileFolderPath);
			System.out.println("Converting..... : "+arffFiles[i].getName().toString());
			Instances data = new Instances(datafile);
			String completeName = arffFiles[i].getName().toString();
			//We are assuming that data are in a way that 0a1a.arff where 0 denote class and a1a.arff is name.
			char classOfData =  completeName.charAt(0);
			String fileName = completeName.substring(1, completeName.length());
			sb.append(fileName);
			sb.append(",");
			sb.append(classOfData);
			sb.append(",");
			int totalAttribute = data.numAttributes();
			double bin[] = new double[numberOfBins];
			double binSize = 1.0/(double)numberOfBins;
			double values[][] = new double[totalAttribute][data.numInstances()];
			for(int j=0;j<data.numInstances();j++)
			{
				Instance currentInstance = data.instance(j);
				for(int k=0;k<totalAttribute;k++)
				{
					values[k][j]=currentInstance.value(k);
				}
			}
			for(int j=0;j<totalAttribute;j++)
			{
			Statistics st = new Statistics(values[j]);
			double getStat=0.0;
			if(type==0)
			{
				getStat = st.getStdDev();
			}
			if(type==1)
			{
				getStat = st.getMean();
			}
			if(type==2)
			{
				getStat = st.median();
			}
			
			int binToplace = (int)(getStat/binSize);
			bin[binToplace]++;
			
			}
			for(int j=0;j<numberOfBins;j++)
			{
				sb.append(bin[j]);
				if(j!=numberOfBins-1)
					sb.append(",");
				else
					sb.append("\n");
			}
			
		}
		
		writeCSVReport(sb.toString(),saveFileFolderPath+csvFileName);
		
		//type is 1 then binning based on mean. If 2 then based on variance.
		
		
		
		
		
		
	}
}
