package metaData;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;
import weka.filters.Filter;
import weka.clusterers.*;
import weka.filters.unsupervised.attribute.Remove;

import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.distribution.fitting.MultivariateNormalMixtureExpectationMaximization;
public class InformationTheory {

	String dataset;
	Instances[] separated;
	
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
	
	public InformationTheory(String dataset) throws Exception
	{
		this.dataset = dataset;
		this.separated = classSeparation();
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
	public int search(String[] text,String pattern)
	{
		int position = -1;
		for(int i=0;i<text.length;i++)
		{
			if(text[i].equals(pattern))
				return i;
		}
		
		return position;
	}
	public double calculateEntropy(double[] prob)
	{
		double entropy=0;
		for(int i=0;i<prob.length;i++)
		{
			if(prob[i]!=0)
			entropy += prob[i] * (Math.log(prob[i])/Math.log(2));
		}
		return -(entropy);
	}
	public double entropyAttr() throws Exception
	{
		int numberOfAttr=0;
		BufferedReader datafile = readDataFile();
		Instances data = new Instances(datafile);
		data.setClassIndex(data.numAttributes() - 1);
		int numericalAttr = 0;
		boolean numericalAttributes[] = new boolean[data.numAttributes()-1];
		double entropy=0;
		for(int i=0;i<data.numAttributes()-1;i++)
		{
			Attribute atr = data.attribute(i);
			if(atr.isNominal())
			{
				numberOfAttr++;
				int numberOfNominal = atr.numValues();
				String[] categories = new String[numberOfNominal];
				double[] categoryCount= new double[numberOfNominal];
				for(int j=0;j<atr.numValues();j++)
				{
					categories[j] = atr.value(j);
				
				}
				for(int j=0;j<data.numInstances();j++)
				{
					Instance currentInstance = data.instance(j);
					String classValue = currentInstance.stringValue(i);
					int position = search(categories,classValue);
					if(position==-1)
					{
						//System.out.println("attribute missing "+classValue);
					}
					else
					{
						categoryCount[position]++;
					}
				}
				for(int j=0;j<atr.numValues();j++)
				{
					categoryCount[j] = categoryCount[j]/(data.numInstances());
					
				}
			
				entropy += calculateEntropy(categoryCount);
				
			}
			else if(atr.isNumeric())
			{
				numberOfAttr++;
				numericalAttributes[i]=true;
				numericalAttr++;
			}
			
		}
		
		Instances modified = new Instances(data);
	
		int[] removeIndicies = new int[numericalAttr];
		int start=0;
		for(int i=0;i<numericalAttributes.length;i++)
		{
			if(numericalAttributes[i])
			{
				removeIndicies[start]=i;
				start++;
			}
		}
	
		Remove removeAttr = new Remove();
		removeAttr.setAttributeIndicesArray(removeIndicies);
		removeAttr.setInvertSelection(true);
		removeAttr.setInputFormat(modified);
		modified = Filter.useFilter(modified, removeAttr);
		
	
		
		EM emc= new EM();
		emc.setNumClusters(1);
		emc.buildClusterer(modified);
		double[][][] attr = emc.getClusterModelsNumericAtts();
		
		
		double[] mean = new double[modified.numAttributes()];
		double[] variance = new double[modified.numAttributes()];
		for(int k=0;k<modified.numAttributes();k++)
		{
			mean[k]=attr[0][k][0];
		}
		for(int k=0;k<modified.numAttributes();k++)
		{
			variance[k]=attr[0][k][1];
		}
	
		for(int i=0;i<variance.length;i++)
		{
			entropy += (0.5 * Math.log(17.45*Math.pow(variance[i], 2)));
		}

		return entropy/(numberOfAttr);
	}
	public double entropyClass() throws Exception
	{
	
		BufferedReader datafile = readDataFile();
		Instances data = new Instances(datafile);
		data.setClassIndex(data.numAttributes() - 1);
		double entropy=0;
		
		Attribute atr = data.classAttribute();
		String attributes[] = new String[data.numClasses()];
		double categoryCount[] = new double[data.numClasses()];
		for(int j=0;j<data.numClasses();j++)
		{
			attributes[j]= atr.value(j);
		}
		for(int j=0;j<data.numInstances();j++)
		{
			Instance currentInstance = data.instance(j);
			String classValue = currentInstance.stringValue(currentInstance.classIndex());
			int position = search(attributes,classValue);
			if(position==-1)
			{
				System.out.println("class missing "+classValue);
			}
			else
			{
				categoryCount[position]++;
			}
		}
		for(int j=0;j<atr.numValues();j++)
		{
			categoryCount[j] = categoryCount[j]/(data.numInstances());
			
		}
	
		entropy += calculateEntropy(categoryCount);
		
		return entropy;
	}
	
	public double jointEntropy() throws Exception
	{
		int numberOfAttr=0;
		BufferedReader datafile = readDataFile();
		Instances data = new Instances(datafile);
		data.setClassIndex(data.numAttributes() - 1);
		int numericalAttr = 0;
		boolean numericalAttributes[] = new boolean[data.numAttributes()-1];
		double entropy=0;
		
		Attribute classAttr = data.classAttribute();
		String classValues[] = new String[data.numClasses()];
		for(int j=0;j<classValues.length;j++)
		{
			classValues[j] = classAttr.value(j);
		}
		int classCount[] = new int[data.numClasses()];
		double classProb[] = new double[data.numClasses()];
		
		for(int i=0;i<data.numInstances();i++)
		{
			Instance currentInstance = data.instance(i);
			String classValue = currentInstance.stringValue(data.classIndex());
			int classPosition = search(classValues,classValue);
			classCount[classPosition]++;
		}
		for(int i=0;i<data.numClasses();i++)
		{
			classProb[i] = classCount[i]/data.numInstances();
		}
		//actual positions
		int[] maxIndices = new int[data.numClasses()];

		for(int k=0;k<data.numClasses();k++)
		{
			int max = Integer.MIN_VALUE;
			int maxi = -1;
			for(int l=0;l<data.numClasses();l++)
			{
				if(max<classCount[l])
				{
					max = classCount[l];
					maxi=l;
				}
			}
			classCount[maxi] = Integer.MIN_VALUE;
			maxIndices[k]=maxi;
		}
		
		for(int i=0;i<data.numAttributes()-1;i++)
		{
			Attribute atr = data.attribute(i);
			if(atr.isNominal())
			{
				
				int rows = data.numClasses();
				int cols = atr.numValues();
				
				
				String[] categories = new String[cols];
	
				for(int j=0;j<atr.numValues();j++)
				{
					categories[j] = atr.value(j);
				
				}
				
				
				double[][] jointProb = new double[rows][cols];
				for(int j=0;j<data.numInstances();j++)
				{
					Instance currentInstance = data.instance(j);
					String classValue = currentInstance.stringValue(data.classIndex());
					String attrValue = currentInstance.stringValue(i);
					int classPosition = search(classValues,classValue);
					int attributePosition = search(categories,attrValue);
				//	System.out.println(classValue+":"+attrValue);
					if(classPosition!=-1 && attributePosition!=-1)
					jointProb[classPosition][attributePosition]++;
				}
				for(int p=0;p<data.numClasses();p++)
				{
					for(int q=0;q<atr.numValues();q++)
					{
						jointProb[p][q] = jointProb[p][q]/(data.numInstances());
					//	System.out.print(jointProb[p][q]+"**");
					}
					//System.out.println();
				}
				double entropyattr = 0;
				for(int p=0;p<data.numClasses();p++)
				{
			//	System.out.println(calculateEntropy(jointProb[p]));
				entropyattr+=calculateEntropy(jointProb[p]);
				}
				//System.out.println("attr: "+i+":" +entropyattr);
				entropy += entropyattr; 
								
			}
			else if(atr.isNumeric())
			{
				
				numberOfAttr++;
				numericalAttributes[i]=true;
				numericalAttr++;
			}
			
		}
		
		Instances modified = new Instances(data);
		
		int[] removeIndicies = new int[numericalAttr];
		int start=0;
		for(int i=0;i<numericalAttributes.length;i++)
		{
			if(numericalAttributes[i])
			{
				removeIndicies[start]=i;
				start++;
			}
		}
	
		Remove removeAttr = new Remove();
		removeAttr.setAttributeIndicesArray(removeIndicies);
		removeAttr.setInvertSelection(true);
		removeAttr.setInputFormat(modified);
		modified = Filter.useFilter(modified, removeAttr);
		
		
		EM emc= new EM();
		emc.setNumClusters(1);
		emc.buildClusterer(modified);
		double[][][] attr = emc.getClusterModelsNumericAtts();
		
	
		double[] variance = new double[modified.numAttributes()];
	
		for(int k=0;k<modified.numAttributes();k++)
		{
			variance[k]=attr[0][k][1];
		}

		for(int p=0;p<data.numClasses();p++)
		{
			for(int q=0;q<variance.length;q++)
			{
				entropy += (0.5 * Math.log(17.45*Math.pow(variance[q], 2)))*classProb[p];
			}
		}
		
	//	System.out.println(entropy);
		return entropy/(numberOfAttr);
		
	}
	
	public double mutualInformation() throws Exception
	{
		double mutual = entropyAttr()+entropyClass() - jointEntropy();
		return mutual;
	}
	public double equivalaentAttr() throws Exception
	{
		double equivalent = entropyClass()/mutualInformation();
		return equivalent;
	}
	
}
