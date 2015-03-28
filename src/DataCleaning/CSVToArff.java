package DataCleaning;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.NumericToNominal;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVSaver;
public class CSVToArff {

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
	public void saveArffFromInstances(String filename,String pathOfFile, Instances dataset) throws Exception
	{
		 BufferedWriter writer = new BufferedWriter(new FileWriter(pathOfFile+filename));
		 writer.write(dataset.toString());
		 writer.flush();
		 writer.close();
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
	public String removeCSV(String fileName)
	{
		String st = fileName;
		int last = st.lastIndexOf(".");
		st = st.substring(0, last);
		return st;
	}
	public void cleanAndModify(String inputFolder,String outputFolder) throws Exception
	{
		File[] csvFiles = readAllFiles(inputFolder);
		
		for(int i=0;i<csvFiles.length;i++)
		{
			String fileName = csvFiles[i].getName().toString();
			BufferedReader datafile = readDataFile(inputFolder+csvFiles[i].getName().toString());
			System.out.println("Converting..... : "+fileName);
			DataSource source = new DataSource(inputFolder+fileName);
			Instances data = source.getDataSet();
			//data.setClassIndex(data.numAttributes() - 1);
			
			NumericToNominal classMaker = new NumericToNominal();
			classMaker.setAttributeIndices("last");
			classMaker.setInputFormat(data);
			Instances newData = Filter.useFilter(data,classMaker);
			
			ArffSaver arff = new ArffSaver();
			arff.setInstances(newData);
			System.out.println("Saved to : "+outputFolder+removeCSV(fileName)+".arff");
			arff.setFile(new File(outputFolder+removeCSV(fileName)+".arff"));
			arff.writeBatch();
		
		}
		System.out.println("Done!!");
	}

}
