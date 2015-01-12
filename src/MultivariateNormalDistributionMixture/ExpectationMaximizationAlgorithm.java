package MultivariateNormalDistributionMixture;
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

import DataCleaning.ModifyBinary;
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

class Pairs{
	String filename;
	double meanValue[][];
	
	public Pairs(String name,double[][] dval)
    {
        this.filename = name;
        meanValue = dval;
    }

    public String getFileName()   { return filename; }
    public double[][] getMean() { return meanValue; }
}

public class ExpectationMaximizationAlgorithm {

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
 * Returns a object of filename and mean pair of choosen number of clusters	
 * @param inputFolderPath
 * @param numOfGaussions
 * @return
 * @throws Exception
 */
public static void getMeanOfGaussionsFromEM(String inputFolderPath, int numOfGaussions) throws Exception
{
	double[][] mean = null;
	ExpectationMaximizationAlgorithm EMA = new ExpectationMaximizationAlgorithm();
	//Read all arff files available in inputFolder
	File[] arffFiles = EMA.readAllFiles(inputFolderPath);
	
	Pairs pp[] = new Pairs[arffFiles.length]; 
	
	for(int i=0;i<arffFiles.length;i++)
	{
		EMA = new ExpectationMaximizationAlgorithm();
		BufferedReader datafile = EMA.readDataFile(arffFiles[i].getName().toString(),inputFolderPath);
		System.out.println("Converting..... : "+arffFiles[i].getName().toString());
		Instances data = new Instances(datafile);
		EM EMObject = new EM();
		EMObject.setNumClusters(numOfGaussions);
		EMObject.buildClusterer(data);
		double[][][] attr = EMObject.getClusterModelsNumericAtts();
		mean = new double[data.numAttributes()][numOfGaussions];
		for(int j=0;j<numOfGaussions;j++)
		{
			for(int k=0;k<data.numAttributes();k++)
			{
				mean[k][j]=attr[j][k][0];
			}
		}
		pp[i] = new Pairs(arffFiles[i].getName().toString(), mean);
		
	}
	
	//return pp;
}
	
	
}
