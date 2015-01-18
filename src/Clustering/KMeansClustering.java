package Clustering;
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
import Histogram.MakeHistogram;
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
import weka.clusterers.SimpleKMeans;


class ClusterObject
{

	int[] clusterInstances;
	double[] clusterValues;
	double distance[][];
	int numberOfAttribute;
	
	public int getNumberOfAttribute()
	{
		return numberOfAttribute;
	}
	public void setNumberOfAttribute(int numberOfAttribute)
	{
		this.numberOfAttribute = numberOfAttribute;
	}
	public int[] getClusterInstances() {
		return clusterInstances;
	}
	public void setClusterInstances(int[] clusterInstances) {
		this.clusterInstances = clusterInstances;
	}
	public double[] getClusterValues() {
		return clusterValues;
	}
	public void setClusterValues(double[] clusterValues) {
		this.clusterValues = clusterValues;
	}
	public double[][] getDistance() {
		return distance;
	}
	public void setDistance(double[][] distance) {
		this.distance = distance;
	}
	public void setInitializeDistanceArray(int numberOfCluster,int attributes)
	{
		distance = new double[numberOfCluster][attributes];
	}
	
}
public class KMeansClustering {

	
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
	public double compareInstances(Instance first,Instance second)
	{
		double diff=0;
		double[] firstInDouble = first.toDoubleArray();
		double[] secondInDouble = second.toDoubleArray();
		for(int i=0;i<firstInDouble.length;i++)
		{
			diff+=Math.abs(firstInDouble[i]-secondInDouble[i]);
			//diff+=firstInDouble[i]-secondInDouble[i];
		}
		
		return diff;
	}
	
	public ClusterObject kMeansCluster(String filename, String inputFolderPath,int numOfCluster) throws Exception
	{		
		BufferedReader datafile = readDataFile(filename,inputFolderPath);
		System.out.println("Clustering on ..... : "+filename);
		Instances data = new Instances(datafile);
		
		SimpleKMeans skm = new SimpleKMeans();
		skm.setNumClusters(numOfCluster);
		skm.buildClusterer(data);
		
		ClusterObject co = new ClusterObject();
		int[] clusterSizes = skm.getClusterSizes();
		int[] sizeOfClusters = new int[numOfCluster];
		for(int i=0;i<numOfCluster;i++)
		{
			sizeOfClusters[i]=clusterSizes[i];
		}
		Instances centroids  = skm.getClusterCentroids();
		double[] clusterValues = new double[sizeOfClusters.length];
		int maxIndices[] = new int[sizeOfClusters.length];
		int max=0;
		int maxIndex = 0;
		for(int i=0;i<sizeOfClusters.length;i++)
		{
			max=-1;
			maxIndex=-1;
			for(int j=0;j<sizeOfClusters.length;j++)
			{
				if(max<sizeOfClusters[j])
				{
					max = sizeOfClusters[j];
					maxIndex = j;
				}
			}
			maxIndices[i]=maxIndex;
			sizeOfClusters[maxIndex]=-1;
			
			
		}
		for(int i=0;i<numOfCluster;i++)
		{
			sizeOfClusters[i]=clusterSizes[i];
		}

		for(int i=0;i<data.numInstances();i++)
		{
			Instance currentInstance = data.instance(i);
			int clusterOfInstance = skm.clusterInstance(currentInstance);
			clusterValues[clusterOfInstance]+=compareInstances(currentInstance, centroids.instance(clusterOfInstance));
		}
		
		double clusterCentroids[][] = new double[centroids.numInstances()][centroids.numAttributes()];
		
		int[] sizeOfClusters1 = new int[sizeOfClusters.length];
		double[] clusterValues1 = new double[clusterValues.length];
		
		for(int i=0;i<sizeOfClusters.length;i++)
		{
			sizeOfClusters1[i]=sizeOfClusters[maxIndices[i]];
			clusterValues1[i]=clusterValues[maxIndices[i]];
			clusterCentroids[maxIndices[i]]=centroids.instance(i).toDoubleArray();
		}
		
		co.setClusterInstances(sizeOfClusters1);
		co.setClusterValues(clusterValues1);
		co.setInitializeDistanceArray(centroids.numInstances(), centroids.numAttributes());
		co.setDistance(clusterCentroids);
		co.setNumberOfAttribute(centroids.numAttributes());
		return co;
	}	
	public void createFile(String inputFolderPath,int numberOfCluster,String csvFile,int numberOfAttribute) throws Exception
	{
		File[] arffFiles = readAllFiles(inputFolderPath);
		
		StringBuilder sb = new StringBuilder();
		//Header Creation
		sb.append("FileName,");
		for(int i=0;i<numberOfCluster;i++)
		{
			sb.append("NumberOfInstacesInCluster"+i);
			sb.append(",");
		}
		for(int i=0;i<numberOfCluster;i++)
		{
			sb.append("ClusterValue"+i);
			sb.append(",");
		}
		sb.append("NumberOfAttribute,");
		for(int i=0;i<numberOfCluster;i++)
		{
			for(int j=0;j<numberOfAttribute;j++)
			{
				sb.append("C"+i+"V"+j+",");
				
			}
		}
		sb.deleteCharAt(sb.length()-1);
		sb.append("\n");
		//Header Creation Finish
		
		for(int i=0;i<arffFiles.length;i++)
		{
			System.out.println("Clustering for..... : "+arffFiles[i].getName().toString());
			
			ClusterObject co = kMeansCluster(arffFiles[i].getName(), inputFolderPath, numberOfCluster);
			sb.append(arffFiles[i].getName().toString()+",");
			
			int[] numOfObjectInCluster = co.getClusterInstances();
			double[] clusterValues = co.getClusterValues();
			double[][] distance = co.getDistance();
			for(int k=0;k<numberOfCluster;k++)
			{
				sb.append(numOfObjectInCluster[k]);
				sb.append(",");
			}
			for(int k=0;k<numberOfCluster;k++)
			{
				sb.append(clusterValues[k]);
				sb.append(",");
			}
			sb.append(numberOfAttribute+",");
			for(int k=0;k<numberOfCluster;k++)
			{
				for(int j=0;j<numberOfAttribute;j++)
				{
					sb.append(distance[k][j]);
					sb.append(",");
				}
			}
			sb.deleteCharAt(sb.length()-1);
			sb.append("\n");
			
			
		}
		sb.deleteCharAt(sb.length()-1);
		writeCSVReport(sb.toString(), inputFolderPath+csvFile);
	}
}
