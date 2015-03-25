package statMeasures;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import weka.core.Instance;
import weka.core.Instances;
import Stats.Statistics;

public class Moments {
	
	/**
	 * Reads a filename at specific path and return a inputReader object
	 * @param filename
	 * @param path
	 * @return
	 * @throws Exception 
	 */
	public static BufferedReader readDataFile(String filename,String pathOfFile) throws Exception
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
	
	public double[][] secondOrderMoments(String inputFolder,int numberOfClasses,String[] fileArray,int dimOfData) throws Exception
	{
		double[][] variance = new double[numberOfClasses][dimOfData];
		for(int i=0;i<fileArray.length;i++)
		{
			BufferedReader datafile = readDataFile(fileArray[i],inputFolder);
			System.out.println("Converting..... : "+fileArray[i]);
			Instances data = new Instances(datafile);
			String completeName = fileArray[i];
			//We are assuming that data are in a way that 0a1a.arff where 0 denote class and a1a.arff is name.
			int classOfData =  Character.getNumericValue(completeName.charAt(0));

			String fileName = completeName.substring(1, completeName.length());
			int totalAttribute = data.numAttributes();
			double values[][] = new double[totalAttribute][data.numInstances()];
			for(int j=0;j<data.numInstances();j++)
			{
				Instance currentInstance = data.instance(j);
				for(int k=0;k<totalAttribute;k++)
				{
					values[k][j]=currentInstance.value(k);
				}
			}
			
			for(int j=0;j<totalAttribute;j++)
			{
				Statistics st = new Statistics(values[j]);
				double getStat=0.0;
				getStat = st.getStdDev();
				variance[i][j] = getStat;
			}
		}	
		return variance;
	}
	public double[][] thirdOrderMoments(String inputFolder,int numberOfClasses,String[] fileArray,int dimOfData) throws Exception
	{
		double[][] skewness = new double[numberOfClasses][dimOfData];
		for(int i=0;i<fileArray.length;i++)
		{
			BufferedReader datafile = readDataFile(fileArray[i],inputFolder);
			System.out.println("Converting..... : "+fileArray[i]);
			Instances data = new Instances(datafile);
			String completeName = fileArray[i];
			//We are assuming that data are in a way that 0a1a.arff where 0 denote class and a1a.arff is name.
			int classOfData =  Character.getNumericValue(completeName.charAt(0));

			String fileName = completeName.substring(1, completeName.length());
			int totalAttribute = data.numAttributes();
			double values[][] = new double[totalAttribute][data.numInstances()];
			for(int j=0;j<data.numInstances();j++)
			{
				Instance currentInstance = data.instance(j);
				for(int k=0;k<totalAttribute;k++)
				{
					values[k][j]=currentInstance.value(k);
				}
			}
			
			for(int j=0;j<totalAttribute;j++)
			{
				Statistics st = new Statistics(values[j]);
				double getStat=0.0;
				getStat = st.getSkewness();
				skewness[i][j] = getStat;
			}
		}	
		return skewness;
	}
	public double[][] forthOrderMoments(String inputFolder,int numberOfClasses,String[] fileArray,int dimOfData) throws Exception
	{
		double[][] kurtosis = new double[numberOfClasses][dimOfData];
		for(int i=0;i<fileArray.length;i++)
		{
			BufferedReader datafile = readDataFile(fileArray[i],inputFolder);
			System.out.println("Converting..... : "+fileArray[i]);
			Instances data = new Instances(datafile);
			String completeName = fileArray[i];
			//We are assuming that data are in a way that 0a1a.arff where 0 denote class and a1a.arff is name.
			int classOfData =  Character.getNumericValue(completeName.charAt(0));

			String fileName = completeName.substring(1, completeName.length());
			int totalAttribute = data.numAttributes();
			double values[][] = new double[totalAttribute][data.numInstances()];
			for(int j=0;j<data.numInstances();j++)
			{
				Instance currentInstance = data.instance(j);
				for(int k=0;k<totalAttribute;k++)
				{
					values[k][j]=currentInstance.value(k);
				}
			}
			
			for(int j=0;j<totalAttribute;j++)
			{
				Statistics st = new Statistics(values[j]);
				double getStat=0.0;
				getStat = st.getKurtosis();
				kurtosis[i][j] = getStat;
			}
		}	
		return kurtosis;
	}
}
