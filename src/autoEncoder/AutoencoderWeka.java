package autoEncoder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.MLPAutoencoder;
import weka.core.converters.CSVSaver;

public class AutoencoderWeka {
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
	public void runAutoEncoder(String inputFile,String outputFile,int numberOFHiddenUnits) throws Exception
	{
		BufferedReader datafile = readDataFile(inputFile);
		System.out.println("Working on ..... : "+inputFile);
		Instances data = new Instances(datafile);
		
		
	//	DataSource source = new DataSource(inputFile);
	//	Instances data = source.getDataSet();
		Instances modifiedData = new Instances(data);
		data.setClassIndex(data.numAttributes() - 1);
		
		weka.filters.unsupervised.attribute.MLPAutoencoder auto = new weka.filters.unsupervised.attribute.MLPAutoencoder();
		auto.setNumFunctions(numberOFHiddenUnits);
		auto.setInputFormat(modifiedData);
		Instances newData = Filter.useFilter(data,auto);   // apply filter
		
		CSVSaver csv = new CSVSaver();
		csv.setInstances(newData);
		csv.setFile(new File(outputFile));
		csv.writeBatch();
		System.out.println("Done!!");	
	}
	
}
