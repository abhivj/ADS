import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.evaluation.NominalPrediction;
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
import wlsvm.WLSVM;


public class AlgorithmRunnerNewParameter {
	/**
	 * Reads a filename at specific path and return a inputReader object
	 * @param filename
	 * @param path
	 * @return
	 */
	public static BufferedReader readDataFile(String filename,String pathOfFile)
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
	public static Evaluation simpleClassify(Classifier model, Instances trainingSet, Instances testingSet) throws Exception
	{
        Evaluation validation = new Evaluation(trainingSet);
        model.buildClassifier(trainingSet);
        validation.evaluateModel(model, testingSet);
        return validation;
    }
	public static double calculateAccuracy(FastVector predictions)
	{
        double correct = 0;
        for (int i = 0; i < predictions.size(); i++)
        {
            NominalPrediction np = (NominalPrediction) predictions.elementAt(i);
            if (np.predicted() == np.actual())
            {
                correct++;
            }
        }
        return 100 * correct / predictions.size();
    }
	public static Instances[][] crossValidationSplit(Instances data, int numberOfFolds) {
        Instances[][] split = new Instances[2][numberOfFolds];
        
        for (int i = 0; i < numberOfFolds; i++) {
            split[0][i] = data.trainCV(numberOfFolds, i);
            split[1][i] = data.testCV(numberOfFolds, i);
        }
        
        return split;
    }
	
	public static void Algorunner(String pathOfDataset,String reportPath) throws Exception
	{
		
		//Reading All data files
	     String path = pathOfDataset; //Map to pathOfDataset     
	     String files;
	     File folder = new File(path);
	     File[] listOfFiles = folder.listFiles(); 
	  
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
	  //Code For printing all file name
	  System.out.println("Files for Testing :");
	     for (int i = 0; i < arffFiles.length; i++) 
	     {
	    	 if (arffFiles[i].isFile()) 
	    	 {
	    		 System.out.println((i+1)+") "+arffFiles[i].getName());
	    	 }
	     }
	    
	     Classifier[] models = {     	new J48(),
                						new PART(),
                						new DecisionTable(),
                						new DecisionStump(),
                						new SimpleCart(),
                						new NaiveBayes(), // Naive Bayes Classifier
                						new Logistic(),
                						new Bagging(),
                						new WLSVM(),	// SVM 
                						new RandomForest(),	// Random Forest
                						new IBk(), 	// K- nearest neighbour	
                						new MultilayerPerceptron(), //Neural Network
                						new AdaBoostM1() //Ada boosting
	     						};
	     //
	 
	  //Start writing about csv
	     StringBuilder csv2 = new StringBuilder(",");	     
	  StringBuilder csvFile = new StringBuilder(",");
	  for(int k=0;k<models.length;k++)
	  {
		  csv2.append(models[k].getClass().getSimpleName());
		  if(k!=models.length-1)
			  csv2.append(",");
		  else
			  csv2.append("\n");
	   }
	   System.out.println(csvFile.toString());

    	
    	double[][] Comparision = new double[arffFiles.length][models.length];
    	int[][] weight = new int[arffFiles.length][models.length];
    	for (int i = 0; i < arffFiles.length; i++) 
    	{
    		BufferedReader datafile = readDataFile(arffFiles[i].getName().toString(),pathOfDataset);
    		System.out.println("File Under Testing..... : "+arffFiles[i].getName().toString());
    		Instances data = new Instances(datafile);
    		data.setClassIndex(data.numAttributes() - 1);
       
    		// Choose a type of validation split
    		//Instances[][] split = crossValidationSplit(data, 10);
       
    		// Separate split into training and testing arrays
    		//Instances[] trainingSplits = split[0];
    		//Instances[] testingSplits  = split[1];
       
    		// Choose a set of classifiers
       
    		// Run for each classifier model
    		for(int j = 0; j < models.length; j++) 
    		{
    			
    			//Code For error rate
    			Evaluation eval = new Evaluation(data);
    			models[j].buildClassifier(data);
    			//used when training and test set is given
    			//eval.evaluateModel(svm, filedata2);
    			//single data
    			eval.crossValidateModel(models[j], data,5, new Random(1));
    			double recall = eval.fMeasure(0)+eval.fMeasure(1);
    			//eval.areaUnderROC(arg0)
    			double aoc = eval.areaUnderROC(0);
    			aoc = eval.areaUnderROC(1);
    		
    			double accuracy = 1-eval.errorRate();
    			// System.out.println("SVM error rate : "+errormid*100);
    			// System.out.println(models[j].getClass().getSimpleName() + ": " + String.format("%.2f%%", (1-errormid)*100) + "\n=====================");
       
    			Comparision[i][j]= accuracy;
    			
    			}
       
    		}
    		
    		System.out.println();
    		System.out.println("Accuray of classifiers : Actual : ");
    		for(int k=0;k<arffFiles.length;k++)
    		{
    			csv2.append(arffFiles[k].getName().toString());
    			csv2.append(",");
    			for(int l=0;l<models.length;l++)
    			{
    				//System.out.print("    ||    "+Comparision[k][l]);
    				
    				csv2.append(Comparision[k][l]+",");
    			}
    			csv2.append("\n");
    			//System.out.println();
    			//System.out.println("********************");
    		}
     
    		for(int k=0;k<arffFiles.length;k++)
    		{
    			double[][] sample = new double[2][models.length];
    			for(int l=0;l<models.length;l++)
    			{
    				sample[0][l]=Comparision[k][l];
    				sample[1][l]=(l+1);
    			}
    			//test for work... working :)
    			/*
     			for(int w=0;w<2;w++)
     			{
     				for(int q=0;q<models.length;q++)
     				{
         				System.out.print("    "+sample[w][q]);
       				}
     				System.out.println("*******");
     			}
     */
    			int n = models.length;
    			double swap1,swap2;
    			for (int c = 0; c < ( n - 1 ); c++) 
    			{
    				for (int d = 0; d < n - c - 1; d++) 
    				{
    					if (sample[0][d] > sample[0][d+1]) /* For descending order use < */
    					{
    						swap1 = sample[0][d];
    						sample[0][d]   = sample[0][d+1];
    						sample[0][d+1] = swap1;
                       
    						swap2 = sample[1][d];
    						sample[1][d]   = sample[1][d+1];
    						sample[1][d+1] = swap2;
    					}
    				}
    			}
    			for(int l=0;l<models.length;l++)
    			{
    				Double d = new Double(sample[1][l]);
    				weight[k][d.intValue()-1] = models.length-l;
    			}
    		}
    		//testing
    		System.out.println("Rank Vector ");
    		for(int k=0;k<arffFiles.length;k++)
    		{
    			csvFile.append(arffFiles[k].getName().toString());
    			csvFile.append(",");
    			for(int l=0;l<models.length;l++)
    			{
    				System.out.print("  ||  "+weight[k][l]);
    				csvFile.append(weight[k][l]);
    				if(l!=models.length-1)
    					csvFile.append(",");
    				else
    				{
    					//if(k!=arffFiles.length-1)
    					csvFile.append("\n");
    				} 
    			}
    			System.out.println();
     
    		}
    			csvFile.append("\n");
    			csvFile.append("Sum of Ranks (Lower is Better)");
    			csvFile.append(",");
     
    			StringBuilder rank = new StringBuilder();
     
    			for(int l=0;l<models.length;l++)
    			{
    				int sum=0;
    				for(int k=0;k<arffFiles.length;k++)
    				{
    					sum+=weight[k][l];
    				}
    					rank.append((double)sum/(double)arffFiles.length);
    					csvFile.append(sum);
    					if(l!=models.length-1)
    					{
    						csvFile.append(",");
    						rank.append(",");
    					}
    					else
    					{
    						//if(k!=arffFiles.length-1)
    						csvFile.append("\n");
    						rank.append("\n");
    					} 
    			}
   	  
    			csvFile.append("\n");
    			csvFile.append("Avrage Ranks");
    			csvFile.append(",");
    			csvFile.append(rank.toString());
    			//Ranks of algorithms
     
     
    			int sum=0;
    			int series = models.length;
    			series = (series*(series+1))/2;
    			series = series*(arffFiles.length);
    			for(int l=0;l<models.length;l++)
    			{
    				for(int k=0;k<arffFiles.length;k++)
    				{
    					sum += weight[k][l];
    				}
    				System.out.println("Parameter for "+models[l].getClass().getSimpleName()+ " : "+sum+"/"+series );
    				sum=0;
    				System.out.println();
     
    			}
     
				    			
				     /*
				     for(int w=0;w<2;w++)
				     {
				     	for(int q=0;q<models.length;q++)
				     	{
				         	System.out.print("    "+sample[w][q]);
				       	}
				     	System.out.println(" ");
				     	System.out.println(" ");
				     	System.out.println(" ");
				     }
				    }  
			                     */
			             
    				//arffFiles.length][models.length];
    			System.out.println("Done");
    			writeCSVReport(csvFile.toString(),reportPath);
    			writeCSVReport(csv2.toString(), reportPath);
    			System.out.println(csvFile.toString());
		   
	}
	
	public static void main(String[] args) throws Exception {
		
		//String reportPath = "d:/work241/reportnew4AOC.csv";
		//String filePath = "./data/BinaryDatasets/";
		//Algorunner(filePath, reportPath);
		String reportPath = "d:/Experiment/reports/AllResultInAccuracyFormat.csv";
		String filePath ="d:/Experiment/exp1/";
		Algorunner(filePath, reportPath);
	
	}

}
