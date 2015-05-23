package metaData;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.lazy.IB1;
import weka.classifiers.lazy.IBk;
import weka.classifiers.trees.J48;
import weka.classifiers.trees.SimpleCart;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;
import weka.attributeSelection.InfoGainAttributeEval;

public class LandMarking {

	String dataset;
	Instances sample;
	double[] infoGainVector;
	private BufferedReader readDataFile()
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
	public LandMarking(String dataset) throws Exception
	{
		this.dataset = dataset;
		this.sample = getInstance();
		this.infoGainVector = generateInfoGains();
	}
	private double[] generateInfoGains() throws Exception
	{

		InfoGainAttributeEval et = new InfoGainAttributeEval();
		et.buildEvaluator(sample);
		double infoGain[] = new double[sample.numAttributes()-1];
		
		for(int i=0;i<sample.numAttributes()-1;i++)
		{
			infoGain[i]=et.evaluateAttribute(i);
		}
		return infoGain;
	}
	private Instances getInstance() throws Exception
	{
		BufferedReader datafile = readDataFile();
		Instances data = new Instances(datafile);
		data.setClassIndex(data.numAttributes() - 1);
		return data;
	}
	private int min(double[] vector)
	{
		double min = Double.MAX_VALUE;
		int minIndex = 0;
		for(int i=0;i<vector.length;i++)
		{
			if(min>vector[i])
			{
				min=vector[i];
				minIndex = i;
			}
		}
		return minIndex;
	}
	private int max(double[] vector)
	{
	
		double max = 0;
		int maxIndex=0;
		for(int i=0;i<vector.length;i++)
		{
			if(max<vector[i])
			{
				max=vector[i];
				maxIndex = i;
			}
				
		}
		return maxIndex;
	}
	public void printInfoGains(double[] vector)
	{
		for(int i=0;i<vector.length;i++)
		{
			System.out.println(vector[i]);
		}
	}
	public int[] sortedOrder(double[] vector)
	{
		int[] sorted = new int[vector.length];
		for(int i=0;i<sorted.length;i++)
		{
			int index = max(vector);
			sorted[i] = index;
			vector[index] = Double.MIN_VALUE;
		}
		return sorted;
	}
	private Instances makeSingleAttribute(int removeIndex) throws Exception
	{
		//System.out.println(removeIndex);
		Instances modified = new Instances(sample);
		modified.setClassIndex(modified.numAttributes()-1);
		int[] removeIndicies = new int[2];
		removeIndicies[0]=removeIndex;
		removeIndicies[1]=modified.classIndex();

		Remove removeAttr = new Remove();
		removeAttr.setAttributeIndicesArray(removeIndicies);
		removeAttr.setInvertSelection(true);
		removeAttr.setInputFormat(modified);
		modified = Filter.useFilter(modified, removeAttr);
		
		return modified;
	}
	public double decisionNodeLearning() throws Exception
	{
		
		int bestIndex = max(infoGainVector);
		//System.out.println(bestIndex);
		Instances reducedData = makeSingleAttribute(bestIndex);
		reducedData.setClassIndex(reducedData.numAttributes()-1);
		//System.out.println(reducedData);
		J48 j48 = new J48();
		Evaluation eval = new Evaluation(reducedData);
		j48.buildClassifier(reducedData);

		eval.crossValidateModel(j48, reducedData,5, new Random(1));
		double errormid = eval.errorRate();
		double accuracy = 1-errormid;	
		//printInfoGains(infoGainVector);
		return accuracy;
	}
	public double worstNodeLearning() throws Exception
	{
		int worstIndex = min(infoGainVector);
		Instances reducedData = makeSingleAttribute(worstIndex);
		reducedData.setClassIndex(reducedData.numAttributes()-1);
		//System.out.println(reducedData);
		J48 j48 = new J48();
		Evaluation eval = new Evaluation(reducedData);
		j48.buildClassifier(reducedData);

		eval.crossValidateModel(j48, reducedData,5, new Random(1));
		double errormid = eval.errorRate();
		double accuracy = 1-errormid;	
		//printInfoGains(infoGainVector);
		return accuracy;
	}
	public double randomNodeLearning() throws Exception
	{
		Random rand = new Random();
		int randomNode = rand.nextInt(sample.numAttributes()-1);
		Instances reducedData = makeSingleAttribute(randomNode);
		reducedData.setClassIndex(reducedData.numAttributes()-1);
		//System.out.println(reducedData);
		J48 j48 = new J48();
		Evaluation eval = new Evaluation(reducedData);
		j48.buildClassifier(reducedData);

		eval.crossValidateModel(j48, reducedData,5, new Random(1));
		double errormid = eval.errorRate();
		double accuracy = 1-errormid;	
		//printInfoGains(infoGainVector);
		return accuracy;
	}
	public double averageNodeLearning() throws Exception
	{
		int totalAttribute = sample.numAttributes()-1;
		double accuracies[] = new double[totalAttribute];
		for(int i=0;i<totalAttribute;i++)
		{
			Instances reducedData = makeSingleAttribute(i);
			reducedData.setClassIndex(reducedData.numAttributes()-1);
			//System.out.println(reducedData);
			J48 j48 = new J48();
			Evaluation eval = new Evaluation(reducedData);
			j48.buildClassifier(reducedData);

			eval.crossValidateModel(j48, reducedData,5, new Random(1));
			accuracies[i] = 1-eval.errorRate();
		}
		double mean=0;
		for(int i=0;i<totalAttribute;i++)
		{
			mean+=accuracies[i];
		}
		return mean/totalAttribute;
	}
	public double NaiveBayesLearning() throws Exception
	{
		NaiveBayes NB = new NaiveBayes();
		Evaluation eval = new Evaluation(sample);
		NB.buildClassifier(sample);
		eval.crossValidateModel(NB, sample,5, new Random(1));
		double accuracy = 1-eval.errorRate();
		return accuracy;
	}
	public double NearestNeighbourLearning() throws Exception
	{
		IB1 NN = new IB1();
		Evaluation eval = new Evaluation(sample);
		NN.buildClassifier(sample);
		eval.crossValidateModel(NN, sample,5, new Random(1));
		double accuracy = 1-eval.errorRate();
		return accuracy;
	}
	public double eliteNNLearning() throws Exception
	{
		double[] gain = new double[infoGainVector.length];
		for(int i=0;i<infoGainVector.length;i++)
		{
			gain[i] = infoGainVector[i];
		}
		int[] order = sortedOrder(gain);
		double highestGain = infoGainVector[order[0]];
		int index=0;
		while(index<infoGainVector.length)
		{
			if((highestGain-infoGainVector[order[index]])>0.1)
				break;
			index++;
		}
		int attributeSet[]= new int[index+1];
		for(int i=0;i<index;i++)
		{
			attributeSet[i] = order[i];
		}
		attributeSet[index] = sample.classIndex();
		Instances modified = new Instances(sample);
		
		Remove removeAttr = new Remove();
		removeAttr.setAttributeIndicesArray(attributeSet);
		removeAttr.setInvertSelection(true);
		removeAttr.setInputFormat(modified);
		modified = Filter.useFilter(modified, removeAttr);
		
		IBk ENN = new IBk();
		Evaluation eval = new Evaluation(sample);
		ENN.setKNN(index);
		ENN.buildClassifier(sample);
		eval.crossValidateModel(ENN, sample,5, new Random(1));
		double accuracy = 1-eval.errorRate();
		return accuracy;
	}
	
}
