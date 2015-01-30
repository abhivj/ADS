package regression;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

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
import DataCleaning.ModifyBinary;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.evaluation.NominalPrediction;
import weka.classifiers.functions.LinearRegression;
import weka.classifiers.functions.Logistic;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.classifiers.lazy.IBk;
import weka.classifiers.meta.AdaBoostM1;
import weka.classifiers.meta.Bagging;
import weka.classifiers.rules.DecisionTable;
import weka.classifiers.rules.PART;
import weka.classifiers.trees.DecisionStump;
import weka.classifiers.trees.J48;
import weka.classifiers.trees.RandomForest;
import weka.classifiers.trees.SimpleCart;
import weka.core.FastVector;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import wlsvm.WLSVM;
import weka.core.converters.CSVSaver;

public class Regression {
	/**
	 * Reads a filename at specific path and return a inputReader object
	 * @param filename
	 * @param path
	 * @return
	 * @throws Exception 
	 */
	public static BufferedReader readDataFile(String filename) throws Exception
	{    BufferedReader inputReader = null;
        try
        {
            inputReader = new BufferedReader(new FileReader(filename));
           
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
	
	public void createModel(String csvFile,String savePath) throws Exception
	{
		File file = new File(csvFile);
		BufferedReader datafile = readDataFile(csvFile);
		System.out.println("DataFile .... : "+csvFile);
		DataSource source = new DataSource(csvFile);
		Instances data = source.getDataSet();
		Instances modifiedData = new Instances(data);
		data.setClassIndex(data.numAttributes() - 1);
		
		//LinearRegression LR = new LinearRegression();
		MultilayerPerceptron LR = new MultilayerPerceptron();
		LR.buildClassifier(data);
		double actualValue[] = new double[data.numInstances()];
		double predictedValue[] = new double[data.numInstances()];
		for(int j=0;j<data.numInstances();j++)
		{
			Instance currentInstance = data.instance(j);
			actualValue[j] = currentInstance.value(data.classIndex());
			modifiedData = new Instances(data);
			modifiedData.delete(j);
			//LR = new MultilayerPerceptron();
		//	LR = new LinearRegression();
			LR = new MultilayerPerceptron();
			//LR.setLearningRate(0.1);
			LR.setTrainingTime(1000);
			LR.setHiddenLayers("50");
			
			LR.buildClassifier(modifiedData);
			
			predictedValue[j] = LR.classifyInstance(currentInstance);
			System.out.println("Number Of Test :"+j+" Actual :"+actualValue[j]+" Predicted :"+predictedValue[j]);
			
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append("Actual Value, Predicted Value, Difference, Squared Diff\n");
		for(int i=0;i<data.numInstances();i++)
		{
			sb.append(actualValue[i]+","+predictedValue[i]+","+(actualValue[i]-predictedValue[i])+","+((actualValue[i]-predictedValue[i])*(actualValue[i]-predictedValue[i]))+"\n");
		}
		sb.deleteCharAt(sb.length()-1);
		writeCSVReport(sb.toString(), savePath);
		double toTalError=0;
		double absError = 0;
		for(int i=0;i<data.numInstances();i++)
		{
			toTalError+=(actualValue[i]-predictedValue[i])*(actualValue[i]-predictedValue[i]);	
			absError+=Math.abs((actualValue[i]-predictedValue[i]));
		}
		System.out.println("Total Squared Error : "+toTalError);
		System.out.println("Absolute Errors Avarage : "+absError/data.numInstances());
		//used when training and test set is given
		//eval.evaluateModel(svm, filedata2);
		//single data
		//Evaluation eval = new Evaluation(data);
		//eval.crossValidateModel(LR, data,5, new Random(1));
		
		
		
		
		
	}
	
	
}
