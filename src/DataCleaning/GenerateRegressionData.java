package DataCleaning;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.opencsv.*;
public class GenerateRegressionData {

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
	public void generateFiles(String AttributeFile,String performenceFile,String targetFolder) throws Exception
	{
		CSVReader Attrreader = new CSVReader(new FileReader(AttributeFile));
		CSVReader Perreader = new CSVReader(new FileReader(performenceFile));
	    String[] headers = Attrreader.readNext();
	    String[] algorithms = Perreader.readNext();
	    //first attr is blank so -1 is done
	    StringBuilder[] sb = new StringBuilder[algorithms.length-1];
	    for(int i=0;i<sb.length;i++)
	    {
	    	sb[i] = new StringBuilder();
	    	for(int j=0;j<headers.length;j++)
	    	{
	    		sb[i].append(headers[j]+",");
	    	}
	    	sb[i].append(algorithms[i+1]+"\n");
	    	
	    }
		
	   String [] nextLine;
	   String[] algoLine;
	    while ((nextLine = Attrreader.readNext()) != null) {
	    	algoLine = Perreader.readNext();
	        // nextLine[] is an array of values from the line
	    	for(int i=0;i<sb.length;i++)
		    {
	    		for(int j=0;j<nextLine.length;j++)
	    		{
	    			sb[i].append(nextLine[j]+",");
	    		}
	    		sb[i].append(algoLine[i+1]);
	    		sb[i].append("\n");
		    }
	    	
	    }
	    for(int i=0;i<sb.length;i++)
	    {
	    	writeCSVReport(sb[i].toString(), targetFolder+algorithms[i+1]+".csv");
	    }
	    
	    
	    
	    
		
	}
	
	
}
