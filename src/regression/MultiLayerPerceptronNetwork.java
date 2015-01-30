package regression;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import weka.classifiers.Evaluation;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.unsupervised.instance.ReservoirSample;

public class MultiLayerPerceptronNetwork {

	
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
	public void createModel(String csvFile,String savePathHidden, String savePathTraining,String savePathBoth) throws Exception
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
		
		double actualValue[] = new double[data.numInstances()];
		double predictedValue[] = new double[data.numInstances()];
		double hiddenLayers[] = new double[100];
		for(int i=1;i<=100;i++)
		{
			for(int j=0;j<data.numInstances();j++)
			{
				Instance currentInstance = data.instance(j);
				actualValue[j] = currentInstance.value(data.classIndex());
				modifiedData = new Instances(data);
				modifiedData.delete(j);
				LR = new MultilayerPerceptron();
				//delete it
				//LR.setTrainingTime(50);
				String hiddenLayer = String.valueOf(i);
				LR.setHiddenLayers(hiddenLayer);
				LR.buildClassifier(modifiedData);	
				predictedValue[j] = LR.classifyInstance(currentInstance);
			}
			double absE=0;
			for(int j=0;j<data.numInstances();j++)
			{
				absE+=Math.abs((actualValue[j]-predictedValue[j]));
			}
			absE = absE/data.numInstances();
			hiddenLayers[i] = absE;
			System.out.println("No of Layer "+i+" Error :"+absE);
		}
		StringBuilder sb = new StringBuilder();
		sb.append("Hidden Layer, Error Rate\n");
		for(int i=1;i<data.numInstances();i++)
		{
			sb.append(i+","+hiddenLayers[i]+"\n");
		}
		sb.deleteCharAt(sb.length()-1);
		writeCSVReport(sb.toString(), savePathHidden);
		double trainingTime[] = new double[100];
		for(int i=1;i<100;i++)
		{
			for(int j=0;j<data.numInstances();j++)
			{
				Instance currentInstance = data.instance(j);
				actualValue[j] = currentInstance.value(data.classIndex());
				modifiedData = new Instances(data);
				modifiedData.delete(j);
				LR = new MultilayerPerceptron();
				//delete it
				LR.setTrainingTime(i*20);
				//LR.setHiddenLayers("4");
				
				LR.buildClassifier(modifiedData);	
				predictedValue[j] = LR.classifyInstance(currentInstance);
			}
			double absE=0;
			for(int j=0;j<data.numInstances();j++)
			{
				absE+=Math.abs((actualValue[j]-predictedValue[j]));
			}
			absE = absE/data.numInstances();
			trainingTime[i] = absE;
			System.out.println("TrainingTime "+i*20+" Error :"+absE);
		}
		sb.delete(0, sb.length()-1);
		sb.append("Training Time, Error Rate\n");
		for(int i=1;i<data.numInstances();i++)
		{
			sb.append(i+","+trainingTime[i]+"\n");
		}
		sb.deleteCharAt(sb.length()-1);
		writeCSVReport(sb.toString(), savePathTraining);
		
		sb.delete(0, sb.length()-1);
		sb.append("Hidden Layers,Training Time,Error Rate\n");
		for(int i=1;i<=100;i++)
		{
			for(int k=1;k<=100;k++)
			{
				for(int j=0;j<data.numInstances();j++)
				{
					Instance currentInstance = data.instance(j);
					actualValue[j] = currentInstance.value(data.classIndex());
					modifiedData = new Instances(data);
					modifiedData.delete(j);
					LR = new MultilayerPerceptron();
					//delete it
					String hiddenLayer = String.valueOf(k);
					LR.setTrainingTime(i*20);
					LR.setHiddenLayers(hiddenLayer);
					LR.buildClassifier(modifiedData);	
					predictedValue[j] = LR.classifyInstance(currentInstance);
				}
				double absE=0;
				for(int j=0;j<data.numInstances();j++)
				{
					absE+=Math.abs((actualValue[j]-predictedValue[j]));
				}
				absE = absE/data.numInstances();
				trainingTime[i] = absE;
				System.out.println("Hiddden Layer :"+k+" TrainingTime "+i*20+" Error :"+absE);
				sb.append(k+","+i*20+","+absE+"\n");
			}
		}
		sb.deleteCharAt(sb.length()-1);
		writeCSVReport(sb.toString(), savePathBoth);
		
	}
	public void createModel(String csvFile,String savePath,int numberOfBags,int percentageTraining) throws Exception
	{
		
		System.out.println("DataFile .... : "+csvFile);
		DataSource source = new DataSource(csvFile);
		Instances data = source.getDataSet();
		Instances modifiedData = new Instances(data);
		data.setClassIndex(data.numAttributes() - 1);
		
		
		Instances TrainingData = new Instances(modifiedData);
		TrainingData.delete();
		
	
		Instances TestingData = new Instances(modifiedData);
		TestingData.delete();
		
		Random randNum = new Random();
		
		//LinearRegression LR = new LinearRegression();
		MultilayerPerceptron LR = new MultilayerPerceptron();
	//	LR.buildClassifier(data);
	
		
		double bagsOfError[] = new double[numberOfBags];
		
		for(int i=0;i<numberOfBags;i++)
		{
			TrainingData.delete();
			TestingData.delete();
			Random rand = new Random(randNum.nextLong());
			modifiedData.randomize(rand);
			int trainingInstances = (modifiedData.numInstances() * percentageTraining)/100;
			
			for(int k=0;k<modifiedData.numInstances();k++)
			{
				if(k<trainingInstances)
					TrainingData.add(modifiedData.instance(k));
				else
					TestingData.add(modifiedData.instance(k));
			}
			
			TrainingData.setClassIndex(TrainingData.numAttributes()-1);
			TestingData.setClassIndex(TestingData.numAttributes()-1);
			
			
			LR = new MultilayerPerceptron();
			//LR.setTrainingTime(2000);
			LR.buildClassifier(TrainingData);
			
			
			
			double testingActual[] = new double[TestingData.numInstances()];
			double testingPredicted[] = new double[TestingData.numInstances()];
			
			double averageErrorOnBag=0;
			for(int k=0;k<TestingData.numInstances();k++)
			{
				Instance currentInstance = TestingData.instance(k);
				testingActual[k] = currentInstance.value(data.classIndex());
				testingPredicted[k]=LR.classifyInstance(currentInstance);
				averageErrorOnBag += Math.abs(testingActual[k]-testingPredicted[k]);
			}
			averageErrorOnBag = averageErrorOnBag/TestingData.numInstances();
			bagsOfError[i] = averageErrorOnBag;
			System.out.println("Bag :"+i+" Error :"+averageErrorOnBag*100+"%");
			for(int k=0;k<TestingData.numInstances();k++)
			{
				System.out.println(k+" Testing Actual Error :"+testingActual[k]+" Predicted Error :"+testingPredicted[k]);
			}
		}
		
		double absoluteError = 0;
		StringBuilder sb = new StringBuilder();
		sb.append("Bag Number, Error\n");
		for(int i=0;i<numberOfBags;i++)
		{
			sb.append(i+1);
			sb.append(","+bagsOfError[i]+"\n");
			absoluteError+=bagsOfError[i];
		}
		absoluteError = absoluteError/numberOfBags;
		System.out.println("Absolute Error :"+absoluteError);
		sb.append("\n");
		sb.append("Absolute Error :"+absoluteError*100+"%");
		writeCSVReport(sb.toString(), savePath);
		
	}
}
