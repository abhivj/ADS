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
import weka.filters.unsupervised.attribute.Remove;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.NominalToString;

public class ModifyBinary {

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
	public void saveArffFromInstances(String filename,String pathOfFile, Instances dataset,int k) throws Exception
	{
		 BufferedWriter writer = new BufferedWriter(new FileWriter(pathOfFile+filename));
		 writer.write(dataset.toString());
		 writer.flush();
		 writer.close();
	}
	
	public static void modifyTo01(String inputFolderPath,String OutputFolderPath) throws Exception
	{
		int numberOfOutputFiles = 0;
		ModifyBinary dataObject = new ModifyBinary();
		//Read all arff files available in inputFolder
		File[] arffFiles = dataObject.readAllFiles(inputFolderPath);
		
		for(int i=0;i<arffFiles.length;i++)
		{
			dataObject = new ModifyBinary();
			BufferedReader datafile = dataObject.readDataFile(arffFiles[i].getName().toString(),inputFolderPath);
			System.out.println("Converting..... : "+arffFiles[i].getName().toString());
			Instances data = new Instances(datafile);
			data.setClassIndex(data.numAttributes() - 1);
			Attribute atr = data.classAttribute();
			String attributes[] = new String[data.numClasses()];
			for(int j=0;j<data.numClasses();j++)
			{
				attributes[j]= atr.value(j);
			}
			Instances convertedByClass[] = new Instances[data.numClasses()];
			int classIndex = data.classIndex();
			
		
			Instances modifiedData = new Instances(data);
			//modifiedData.setClassIndex(-1);
			
		//	NominalToString ntom = new NominalToString();
			//ntom.
			
			
			int numberOfInstances = modifiedData.numInstances();
			
			for(int k=0;k<data.numClasses();k++)
			{
				convertedByClass[k]=new Instances(modifiedData);
				convertedByClass[k].delete();
			}
			
			
			for(int j=0;j<modifiedData.numInstances();j++)
			{
				Instance currentInstance = modifiedData.instance(j);
				for(int k=0;k<data.numClasses();k++)
				{
					if(currentInstance.stringValue(classIndex)==attributes[k])
					{
						convertedByClass[k].add(currentInstance);
					}
				}
			}
			for(int k=0;k<data.numClasses();k++)
			{
				Remove removeClass = new Remove();
				removeClass.setAttributeIndicesArray(new int [] {convertedByClass[k].classIndex()});
				removeClass.setInvertSelection(false);
				removeClass.setInputFormat(convertedByClass[k]);
				convertedByClass[k] = Filter.useFilter(convertedByClass[k], removeClass);
			}
			
			for(int k=0;k<data.numClasses();k++)
			{
				dataObject.saveArffFromInstances(String.valueOf(k)+arffFiles[i].getName().toString(),OutputFolderPath,convertedByClass[k],k);
			}
			
		}
		
	}
	
	
	
	
}
