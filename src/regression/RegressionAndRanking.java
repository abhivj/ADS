package regression;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import DataCleaning.TrainingDataCleaner;
import weka.classifiers.functions.GaussianProcesses;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

import com.opencsv.*;

public class RegressionAndRanking {

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
	    		 if (files.endsWith(".csv"))
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
	    		 if (files.endsWith(".csv"))
	    		 {
	    			 arffFiles[fileCounter] = listOfFiles[i];
	    			 fileCounter++;
	          
	    		 }
	    	 }
	     }
		return arffFiles;
	}
	
	public String[][] transpose(String[][] infoStr)
	{
		String[][] newStr = new String[infoStr[0].length][infoStr.length];
		for(int i=0;i<newStr.length;i++)
		{
			for(int j=0;j<newStr[0].length;j++)
				newStr[i][j] = infoStr[j][i];
		}
		
		return newStr;
		
	}
	public StringBuilder stringPrep(String[][] str)
	{
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<str.length;i++)
		{
			for(int j=0;j<str[0].length;j++)
			{
				sb.append(str[i][j]+",");
			}
			sb.deleteCharAt(sb.length()-1);
			sb.append("\n");
		}
		sb.deleteCharAt(sb.length()-1);
		return sb;
	}
	
	public  BufferedReader readDataFile(String filename,String FolderPath) throws Exception
	{    BufferedReader inputReader = null;
        try
        {
            inputReader = new BufferedReader(new FileReader(FolderPath+filename));
           
        }
        catch (FileNotFoundException ex)
        {
            System.err.println("File not found: " + FolderPath+filename);
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
	public void createModel(String FolderPath,String savePath,String performanceFile,int trainingTime,int hiddenLayer,boolean baggingFlag,int bagSize) throws Exception
	{
		System.out.println(FolderPath);
		File[] arffFiles = readAllFiles(FolderPath);
		DataSource sampleSource = new DataSource(FolderPath+arffFiles[0].getName().toString());
		int numberOfInstances = sampleSource.getDataSet().numInstances();
		
		CSVReader Perreader = new CSVReader(new FileReader(performanceFile));
	    String[] algorithms = Perreader.readNext();
		
	    String[][] infoMatrix = new String[algorithms.length][numberOfInstances+1];
	    
	    //Getting File Names as first coloumn
	    
	    String [] nextLine;
	    	int k=1;
	    	infoMatrix[0][0]="";
		    while (((nextLine = Perreader.readNext()) != null) && k<=numberOfInstances) {
		    
		    	infoMatrix[0][k]=nextLine[0];
		    	k++;
		    	
		    }

	    //
		   // boolean baggingFlag = false;
		   // int bagSize = 20;
		    if(baggingFlag==true)
		    {
		    	for(int i=1;i<algorithms.length;i++)
				{
					System.out.println("Applying Regression on : "+algorithms[i]);
					DataSource source = new DataSource(FolderPath+algorithms[i]+".csv");
					Instances data = source.getDataSet();
					Instances modifiedData = new Instances(data);
					data.setClassIndex(data.numAttributes() - 1);
					
					GaussianProcesses LR = new GaussianProcesses();
					
					infoMatrix[i][0] = algorithms[i];
					for(int j=0;j<(int)Math.ceil((double)data.numInstances()/bagSize);j++)
					{
						System.out.println("Total Iteration : "+(int)Math.ceil((double)data.numInstances()/bagSize)+" Working on :"+j);
						
						Instance currentInstances[] = new Instance[bagSize];
						for(int p=0;p<bagSize && (j*bagSize+p)<data.numInstances();p++)
						{
							currentInstances[p] = data.instance(j*bagSize+p);
						}
						for(int p=0;p<bagSize && (j*bagSize+p)<data.numInstances();p++)
						{
							//System.out.println(currentInstances[p]);
						}
						System.out.println();
						//Instance currentInstance = data.instance(j);
						
						modifiedData = new Instances(data);
						
						for(int p=0;p<bagSize && (j*bagSize+p)<data.numInstances();p++)
						{
							modifiedData.delete(j*bagSize);
						}
						
						LR = new GaussianProcesses();
						//LR.setTrainingTime(trainingTime);
						//LR.setHiddenLayers(String.valueOf(hiddenLayer));
						LR.buildClassifier(modifiedData);
						for(int p=0;p<bagSize && (j*bagSize+p)<data.numInstances();p++)
						{
							//System.out.println(currentInstances[p].toString());
							infoMatrix[i][j*bagSize+1+p]= String.valueOf(LR.classifyInstance(currentInstances[p]));
						}
						
					}
					
				
				
				}
		    }
		    
		else
		{
		for(int i=1;i<algorithms.length;i++)
		{
			System.out.println("Applying Regression on : "+algorithms[i]);
			DataSource source = new DataSource(FolderPath+algorithms[i]+".csv");
			Instances data = source.getDataSet();
			Instances modifiedData = new Instances(data);
			data.setClassIndex(data.numAttributes() - 1);
			
			GaussianProcesses LR = new GaussianProcesses();
			
			infoMatrix[i][0] = algorithms[i];
			
			
			
			for(int j=0;j<data.numInstances();j++)
			{
				System.out.println("Ttoal Instance : "+data.numInstances()+" Working on :"+j);
				Instance currentInstance = data.instance(j);
				
				modifiedData = new Instances(data);
				modifiedData.delete(j);
				LR = new GaussianProcesses();
				//LR.setTrainingTime(trainingTime);
				//LR.setHiddenLayers(String.valueOf(hiddenLayer));
				LR.buildClassifier(modifiedData);
				
				infoMatrix[i][j+1]= String.valueOf(LR.classifyInstance(currentInstance));
			}
		}
	}
		String[][] str = transpose(infoMatrix);
		StringBuilder sb = stringPrep(str);
		writeCSVReport(sb.toString(),savePath);
	}
	
	public void createNModel(String FolderPath,String savePath,String performanceFile,int trainingTime,int hiddenLayer,boolean baggingFlag,int bagSize)
	{
		
	}
	
	
	
}
