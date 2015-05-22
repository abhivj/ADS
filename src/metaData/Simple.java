package metaData;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.io.FileNotFoundException;







//Weka Libraries Used
import weka.classifiers.functions.MultilayerPerceptron;
import weka.classifiers.Evaluation;
import weka.classifiers.meta.Bagging;
import weka.classifiers.trees.J48Consolidated;
import weka.classifiers.trees.RandomForest;
import weka.classifiers.trees.SimpleCart;
import weka.core.Attribute;
import weka.core.Instances;
import weka.classifiers.functions.Logistic;
import weka.classifiers.trees.J48;
import weka.classifiers.Classifier;
import weka.classifiers.evaluation.NominalPrediction;
import weka.classifiers.rules.DecisionTable;
import weka.classifiers.rules.PART;
import weka.classifiers.trees.DecisionStump;
import weka.core.FastVector;
import weka.classifiers.lazy.IBk;
import weka.classifiers.meta.AdaBoostM1;
import weka.classifiers.bayes.NaiveBayes;
import wlsvm.WLSVM;
public class Simple {
	String dataset;
	public BufferedReader readDataFile()
	{
        BufferedReader inputReader = null;
        try
        {
            inputReader = new BufferedReader(new FileReader(dataset));
        }
        catch (FileNotFoundException ex)
        {
            System.err.println("File not found: " + dataset);
        }
        
        return inputReader;
    }
	
	public Simple(String dataset)
	{
		this.dataset = dataset;
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
	public int numberOfClasses() throws Exception
	{
		BufferedReader datafile = readDataFile();
		Instances data = new Instances(datafile);
		data.setClassIndex(data.numAttributes() - 1);
		return data.numClasses();	
	}
	public int numberOfAttr() throws Exception
	{
		BufferedReader datafile = readDataFile();
		Instances data = new Instances(datafile);
		data.setClassIndex(data.numAttributes() - 1);
		return data.numAttributes();
	}
	public int numberOfNumericalAttr() throws Exception
	{
		BufferedReader datafile = readDataFile();
		Instances data = new Instances(datafile);
		data.setClassIndex(data.numAttributes() - 1);
		int numericalAttr = 0;
		for(int i=0;i<data.numAttributes();i++)
		{
			Attribute atr = data.attribute(i);
			if(atr.isNumeric())
				numericalAttr++;
		}
		return numericalAttr;
	}
	public int numberOfNominalAttr() throws Exception
	{
		BufferedReader datafile = readDataFile();
		Instances data = new Instances(datafile);
		data.setClassIndex(data.numAttributes() - 1);
		int nominalAttr = 0;
		for(int i=0;i<data.numAttributes();i++)
		{
			Attribute atr = data.attribute(i);
			if(atr.isNominal())
				nominalAttr++;
		}
		return nominalAttr;
	}
	public int numberOfInstances() throws Exception
	{
		BufferedReader datafile = readDataFile();
		Instances data = new Instances(datafile);
		data.setClassIndex(data.numAttributes() - 1);
		return data.numInstances();
	}
	public int rateOfNominalAttr() throws Exception
	{
		BufferedReader datafile = readDataFile();
		Instances data = new Instances(datafile);
		data.setClassIndex(data.numAttributes() - 1);
		int numericalAttr = 0;
		for(int i=0;i<data.numAttributes();i++)
		{
			Attribute atr = data.attribute(i);
			if(atr.isNumeric())
				numericalAttr++;
		}
		return numericalAttr/numberOfAttr();
	}
	public int rateOfNumericalAttr() throws Exception
	{
		BufferedReader datafile = readDataFile();
		Instances data = new Instances(datafile);
		data.setClassIndex(data.numAttributes() - 1);
		int nominalAttr = 0;
		for(int i=0;i<data.numAttributes();i++)
		{
			Attribute atr = data.attribute(i);
			if(atr.isNominal())
				nominalAttr++;
		}
		return nominalAttr/data.numAttributes();
	}
	
	
	
	
	
}
