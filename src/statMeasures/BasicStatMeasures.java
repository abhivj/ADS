package statMeasures;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import weka.core.Instances;
import weka.core.converters.CSVSaver;


public class BasicStatMeasures {
	
	public BufferedReader readDataFile(String inputFilePath) throws Exception
	{
		
        BufferedReader inputReader = null;
        try
        {
            inputReader = new BufferedReader(new FileReader(inputFilePath));
           
        }
        catch (FileNotFoundException ex)
        {
            System.err.println("File not found: " + inputFilePath);
        }
        
        return inputReader;
    }

	public void convertToCSV(String inputFile,String outputFile) throws Exception
	{
		BufferedReader datafile = readDataFile(inputFile);
		System.out.println("Working on ..... : "+inputFile);
		Instances data = new Instances(datafile);
		
		//Convert to CSV
		CSVSaver csv = new CSVSaver();
		csv.setInstances(data);
		csv.setFile(new File(outputFile));
		csv.writeBatch();
		System.out.println("Done!!");	
	}
	

}
