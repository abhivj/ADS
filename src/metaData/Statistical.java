package metaData;

import Stats.*;
import DataCleaning.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;


import org.apache.commons.io.FileUtils;

import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;

public class Statistical {

	String dataset;
	Instances[] classSeparated;
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
	
	public Statistical(String dataset) throws Exception
	{
		this.dataset = dataset;
		this.classSeparated = classSeparation();
	}
	

	public Instances[] classSeparation() throws Exception
	{
		BufferedReader datafile = readDataFile();
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
		
		//Sorting based on weights. More instance higher class.
		int[] maxIndices = new int[data.numClasses()];
		int[] totalInstances = new int[data.numClasses()];
		for(int k=0;k<data.numClasses();k++)
		{
			totalInstances[k]=convertedByClass[k].numInstances();
		}
		for(int k=0;k<data.numClasses();k++)
		{
			int max = Integer.MIN_VALUE;
			int maxi = -1;
			for(int l=0;l<data.numClasses();l++)
			{
				if(max<totalInstances[l])
				{
					max = totalInstances[l];
					maxi=l;
				}
			}
			totalInstances[maxi] = Integer.MIN_VALUE;
			maxIndices[k]=maxi;
		}
		//Maxindices is actual position based on number of instances.
		
		Instances classSeparation[] = new Instances[data.numClasses()];
		
		for(int k=0;k<data.numClasses();k++)
		{
			classSeparation[k] = convertedByClass[maxIndices[k]];
		}
		return classSeparation;
	}
	
	public double[] attributesSD(Instances sample, boolean[] numericalAttributes,int numericalCount)
	{
		
/*
 *  double Data[][] = new double[data.numInstances()][data.numAttributes() ]; 
        for (int i = 0; i < data.numInstances(); i++) { 
            for (int j = 0; j < data.numAttributes(); j++) { 
                Data[i][j ] = data.instance(i).value(j); 
            } 
        } 
        return Data; 
 */
		double Data[][] = new double[numericalCount][sample.numInstances()];
		double SDValues[] = new double[numericalCount];
		int count = 0;
		for(int i=0;i<sample.numInstances();i++)
		{
			count=0;
			for(int j=0;j<numericalAttributes.length;j++)
			{
				if(numericalAttributes[j])
				{
					//System.out.println(j);
					Data[count][i]=sample.instance(i).value(j);
					count++;
				}
			}
		}
		
		
		
		for(int i=0;i<numericalCount;i++)
		{
			Statistics st = new Statistics(Data[i]);
			SDValues[i] = st.getStdDev();
		}
		
		return SDValues;
	}
	public double SDRatio() throws Exception
	{
		double sdRatio=0;
		BufferedReader datafile = readDataFile();
		Instances data = new Instances(datafile);
		data.setClassIndex(data.numAttributes() - 1);
		int numericalAttr = 0;
		boolean numericalAttributes[] = new boolean[data.numAttributes()-1];
		for(int i=0;i<data.numAttributes()-1;i++)
		{
			Attribute atr = data.attribute(i);
			if(atr.isNumeric())
			{
				numericalAttributes[i]=true;
				numericalAttr++;
			}
		}
		
		//System.out.println(numericalAttr);
		double[] SDDataset = attributesSD(data, numericalAttributes, numericalAttr);
		double[][] SDPopulation = new double[data.numClasses()][numericalAttr];
		Instances[] classSeparated = classSeparation();
		double upper = 1;
		double lower=1;
		for(int i=0;i<classSeparated.length;i++)
		{
			double[] temp = attributesSD(classSeparated[i], numericalAttributes, numericalAttr);
			for(int j=0;j<temp.length;j++)
			{
				SDPopulation[i][j] = temp[j];
				upper = upper*temp[j];
			}
		}
		for(int i=0;i<SDDataset.length;i++)
		{
			//System.out.println(SDDataset[i]);
			lower = lower * SDDataset[i];
		}
		
//		System.out.println("upper" + upper);
//		System.out.println("lower" + lower);
		double upperD = (double)1/(numericalAttr*data.numClasses());
		double lowerD = (double)1/(numericalAttr);
		upper = Math.pow(upper, upperD);
		lower = Math.pow(lower, lowerD);
//		System.out.println("upper" + upper);
//		System.out.println("lower" + lower);
		sdRatio = upper/lower;
			
		return sdRatio;
		
	}
	public double corr_abs() throws Exception
	{
		BufferedReader datafile = readDataFile();
		Instances data = new Instances(datafile);
		data.setClassIndex(data.numAttributes() - 1);
		int numericalAttr = 0;
		boolean numericalAttributes[] = new boolean[data.numAttributes()-1];
		for(int i=0;i<data.numAttributes()-1;i++)
		{
			Attribute atr = data.attribute(i);
			if(atr.isNumeric())
			{
				numericalAttributes[i]=true;
				numericalAttr++;
			}
		}
	
		double Data[][] = new double[numericalAttr][data.numInstances()];
		double corr_abs_value[] = new double[numericalAttr];
		int count = 0;
		for(int i=0;i<data.numInstances();i++)
		{
			count=0;
			for(int j=0;j<numericalAttributes.length;j++)
			{
				if(numericalAttributes[j])
				{
					//System.out.println(j);
					Data[count][i]=data.instance(i).value(j);
					count++;
				}
			}
		}
		
		double[][] corrMatrix = new double[numericalAttr][numericalAttr];
		double corrValue = 0;
		for(int i=0;i<numericalAttr;i++)
		{
			for(int j=0;j<numericalAttr;j++)
			{
				SpearmanCor SC = new SpearmanCor();
				corrMatrix[i][j] = SC.spearmans(Data[i], Data[j]);
				corrValue += corrMatrix[i][j]; 
			}
		}
		
		corrValue = corrValue / Math.pow(numericalAttr, 2);
		return corrValue;
	
	}
	public double skewness() throws Exception
	{
		BufferedReader datafile = readDataFile();
		Instances data = new Instances(datafile);
		data.setClassIndex(data.numAttributes() - 1);
		int numericalAttr = 0;
		boolean numericalAttributes[] = new boolean[data.numAttributes()-1];
		for(int i=0;i<data.numAttributes()-1;i++)
		{
			Attribute atr = data.attribute(i);
			if(atr.isNumeric())
			{
				numericalAttributes[i]=true;
				numericalAttr++;
			}
		}
	
		double Data[][] = new double[numericalAttr][data.numInstances()];
		int count = 0;
		for(int i=0;i<data.numInstances();i++)
		{
			count=0;
			for(int j=0;j<numericalAttributes.length;j++)
			{
				if(numericalAttributes[j])
				{
					//System.out.println(j);
					Data[count][i]=data.instance(i).value(j);
					count++;
				}
			}
		}
		
		double[] skewnessValues = new double[numericalAttr];
		double skewness = 0;
		for(int i=0;i<numericalAttr;i++)
		{
			Statistics st = new Statistics(Data[i]);
			skewnessValues[i] = st.getSkewness();
			skewness+= skewnessValues[i];
		}
		
		skewness = skewness / Math.pow(numericalAttr, 2);
		return skewness;
	
	}
	public double kurtosis() throws Exception
	{
		BufferedReader datafile = readDataFile();
		Instances data = new Instances(datafile);
		data.setClassIndex(data.numAttributes() - 1);
		int numericalAttr = 0;
		boolean numericalAttributes[] = new boolean[data.numAttributes()-1];
		for(int i=0;i<data.numAttributes()-1;i++)
		{
			Attribute atr = data.attribute(i);
			if(atr.isNumeric())
			{
				numericalAttributes[i]=true;
				numericalAttr++;
			}
		}
	
		double Data[][] = new double[numericalAttr][data.numInstances()];
		int count = 0;
		for(int i=0;i<data.numInstances();i++)
		{
			count=0;
			for(int j=0;j<numericalAttributes.length;j++)
			{
				if(numericalAttributes[j])
				{
					//System.out.println(j);
					Data[count][i]=data.instance(i).value(j);
					count++;
				}
			}
		}
		
		double[] kurtosisValues = new double[numericalAttr];
		double kurtosis = 0;
		for(int i=0;i<numericalAttr;i++)
		{
			Statistics st = new Statistics(Data[i]);
			kurtosisValues[i] = st.getKurtosis();
			kurtosis+= kurtosisValues[i];
		}
		
		kurtosis = kurtosis / Math.pow(numericalAttr, 2);
		return kurtosis;
	
	}
};
	
	
