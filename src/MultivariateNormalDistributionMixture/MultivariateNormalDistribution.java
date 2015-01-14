package MultivariateNormalDistributionMixture;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
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
import be.ac.ulg.montefiore.run.jahmm.ObservationReal;
import be.ac.ulg.montefiore.run.jahmm.ObservationVector;
import be.ac.ulg.montefiore.run.jahmm.OpdfMultiGaussian;
/**
 * This class uses jahmm model to estimate parameters of Multivariate Gaussion Distribution
 * @author abhivj
 *
 */
public class MultivariateNormalDistribution {
	
	double mean[];
	double covariances[][];
	String fileName;
	
	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}
	public void setMean(double[] mean)
	{
		this.mean = mean;
	}
	public void setCovariance(double[][] cov)
	{
		this.covariances = cov;
	}
	public String getFileName()
	{
		return fileName;
	}
	public double[] getMean()
	{
		return mean;
	}
	public double[][] covariances()
	{
		return covariances;
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
	public void writeCSVReport(String stringToWrite,String path)
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
	 * Get all the arff files in the folder and generate multivariate model for them and save it
	 * @param FileFolderPath
	 * @param fileToSave 
	 * @throws Exception
	 */
	public static void runModelOverFolderForMean(String FileFolderPath ,String fileToSave) throws Exception
	{
		MultivariateNormalDistribution mvn = new MultivariateNormalDistribution();
		//Read all arff files available in inputFolder
		File[] arffFiles = mvn.readAllFiles(FileFolderPath);
		MultivariateNormalDistribution allObjects[] = new MultivariateNormalDistribution[arffFiles.length];
		for(int i=0;i<arffFiles.length;i++)
		{
			allObjects[i] = new MultivariateNormalDistribution();
			BufferedReader datafile = mvn.readDataFile(arffFiles[i].getName().toString(),FileFolderPath);
			System.out.println("Converting..... : "+arffFiles[i].getName().toString());
			Instances data = new Instances(datafile);
			allObjects[i] = fitModel(data);
			allObjects[i].setFileName(arffFiles[i].getName().toString());
			
		}
		mvn.saveAllObjectToCsv(FileFolderPath+fileToSave,allObjects);
		
		//double [] mean = {2., 4.};
		//double [][] covariance = { {3., 2}, {2., 4.} };
		//OpdfMultiGaussian omg = new OpdfMultiGaussian (mean ,covariance);
		//ObservationVector[] obs = new ObservationVector[100];
		//for (int i = 0; i < obs.length; i++)
			//obs[i] = omg.generate();
		//OpdfMultiGaussian omg1 = new OpdfMultiGaussian(2);
		//omg1.fit(obs);
		
	}
	public void saveAllObjectToCsv(String filePath,MultivariateNormalDistribution[] mvn)
	{
		StringBuilder prePare=new StringBuilder();
		for(int i=0;i<mvn.length;i++)
		{
			prePare.append(mvn[i].getFileName());
			prePare.append(",");
			for(int j=0;j<mvn[i].mean.length;j++)
			{
				prePare.append(mvn[i].mean[j]);
				if(j!=mvn[i].mean.length-1)
				prePare.append(",");
				else
				prePare.append("\n");
			}
		}
		writeCSVReport(prePare.toString(),filePath);
	}
	public static MultivariateNormalDistribution fitModel(Instances data)
	{
		MultivariateNormalDistribution mvn = new MultivariateNormalDistribution();
		int totalInstances = data.numInstances();
		ObservationVector[] obs = new ObservationVector[totalInstances];
		for(int i=0;i<totalInstances;i++)
		{
			Instance currentInstance = data.instance(i);
			double[] tuple = currentInstance.toDoubleArray();
			obs[i] = new ObservationVector(tuple);
		}
		
		OpdfMultiGaussian omg = new OpdfMultiGaussian(data.numAttributes());
		omg.fit(obs);
		mvn.setMean(omg.mean());
		mvn.setCovariance(omg.covariance());
		return mvn;
	}
}
