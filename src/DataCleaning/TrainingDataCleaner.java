package DataCleaning;
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
import weka.core.Attribute;
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

public class TrainingDataCleaner {

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
	 * Reads a filename at specific path and return a inputReader object
	 * @param filename
	 * @param path
	 * @return
	 */
	public BufferedReader readDataFile(String filename,String pathOfFile)
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
	
	public void saveArffFromInstances(String filename,String pathOfFile, Instances dataset) throws Exception
	{
		 BufferedWriter writer = new BufferedWriter(new FileWriter(pathOfFile+filename));
		 writer.write(dataset.toString());
		 writer.flush();
		 writer.close();
	}
	
	public Instances nominalToBinaryConverter(Instances instances) throws Exception
	{
		int numInstances = instances.numInstances();
		NominalToBinary nominalFilter = new NominalToBinary();
		nominalFilter.setBinaryAttributesNominal(true);
		nominalFilter.setInputFormat(instances);
		
		Instances modifyData = nominalFilter.getOutputFormat();
		for(int j=0;j<numInstances;j++)
		{
			Instance currentData = instances.instance(j);
			nominalFilter.input(currentData);
		}
		for(int j=0;j<numInstances;j++)
		{
			Instance outputData = nominalFilter.output();
			modifyData.add(outputData);
		}
		return modifyData;
	}
	public Instances PCAConverter(Instances instances,int dimOfPCA) throws Exception
	{
		int numInstances = instances.numInstances();
		PrincipalComponents pc = new PrincipalComponents();
		pc.setMaximumAttributes(dimOfPCA);
		pc.setInputFormat(instances);
		for(int j=0;j<numInstances;j++)
		{
			Instance currentData = instances.instance(j);
			pc.input(currentData);
		}
		pc.batchFinished();
		
		Instances TrasformedByPCA = pc.getOutputFormat();
		
		for(int j=0;j<numInstances;j++)
		{
			Instance outputData = pc.output();
			TrasformedByPCA.add(outputData);
		}
		return TrasformedByPCA;
		
	}
	public Instances NormalizeConverter(Instances instances) throws Exception
	{
		int numInstances = instances.numInstances();
		Normalize normalData = new Normalize();
		Instances normalizeData = new Instances(instances);
		normalizeData.delete();
		normalData.setInputFormat(normalizeData);
		for(int j=0;j<numInstances;j++)
		{
			Instance currentData = instances.instance(j);
			normalData.input(currentData);
		}
		normalData.batchFinished();
		for(int j=0;j<numInstances;j++)
		{
			Instance outputData = normalData.output();
			normalizeData.add(outputData);
		}
		return normalizeData;
		
	}
	
	public static int CleanAndModify(String inputFolder,String outputFolder,boolean convertNominal,boolean doPCA,boolean doNormalization,boolean doRejectIfLess,boolean rejectIfNotBinary, int dimOfPCA,int minimumInstance) throws Exception
	{
		int numberOfOutputFiles = 0;
		TrainingDataCleaner dataObject = new TrainingDataCleaner();
		//Read all arff files available in inputFolder
		File[] arffFiles = dataObject.readAllFiles(inputFolder);
		
		
		
		for(int i=0;i<arffFiles.length;i++)
		{
			dataObject = new TrainingDataCleaner();
			BufferedReader datafile = dataObject.readDataFile(arffFiles[i].getName().toString(),inputFolder);
			System.out.println("Converting..... : "+arffFiles[i].getName().toString());
			Instances data = new Instances(datafile);
			data.setClassIndex(data.numAttributes() - 1);
		
			int numInstances = data.numInstances();
			Instances ConvertedData = new Instances(data);
			
			//Apply Nominal To Binary Filter
			
			
			boolean rejectFlag=false;
			
			for(int k=0;k<data.numAttributes()-1;k++)
			{
				Attribute atr = data.attribute(k);
				if(atr.isString())
				{
				rejectFlag=true;
				System.out.println("string attr : "+(k+1));
				}
			}
			
			
			if(rejectIfNotBinary==true)
			{
				if(ConvertedData.numClasses()!=2)
				{
					rejectFlag=true;
					System.out.println("classes not 2");
				}
					
			}
			//Principal Component Analysis
			if(convertNominal && !rejectFlag)
			{
				ConvertedData = dataObject.nominalToBinaryConverter(ConvertedData);
			}
			if(doPCA && !rejectFlag)
			{
				try{
				ConvertedData = dataObject.PCAConverter(ConvertedData, dimOfPCA);
				}
				catch(Exception e)
				{
					System.out.println(e.toString());
				}
			}
			
			//Normalize Data
			
			if(doNormalization && !rejectFlag)
			{
				ConvertedData = dataObject.NormalizeConverter(ConvertedData);
			}
			
			int totalAttribute = ConvertedData.numAttributes()-1;
			if(totalAttribute!=dimOfPCA)
			{
				rejectFlag = true;
				System.out.println("attr not equal "+totalAttribute);
			}
			
			
			//EM ecl = new EM();

			//ConvertedData.deleteAttributeAt(ConvertedData.numAttributes() - 1);
			//ecl.buildClusterer(ConvertedData);
			//double[][][] attr = ecl.getClusterModelsNumericAtts();
		
			
			if(rejectFlag==false)		
			{
			System.out.println(arffFiles[i].getName().toString()+" : Saved !!");
			System.out.println();
			numberOfOutputFiles++;
			dataObject.saveArffFromInstances(arffFiles[i].getName().toString(),outputFolder,ConvertedData);
			}
			else
			{
			System.out.println(arffFiles[i].getName().toString()+" : Rejected !!");
			System.out.println();
			}
			System.out.println("Total Files : "+numberOfOutputFiles);
			
		}
		
		
		return numberOfOutputFiles;
	}
	
	
}
