package plots;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.opencsv.CSVReader;

public class GenerateRanks {
	
	public int maxIndex(double[] values)
	{
		int maxIndex=-1;
		double max=Double.MIN_VALUE;
		for(int i=0;i<values.length;i++)
		{
			if(max<values[i])
			{
				max=values[i];
				maxIndex = i;
			}
		}
		return maxIndex;
	}
	public int[] generateRanks(double[] values)
	{
		int ranks[] = new int[values.length];
		double max=Double.MIN_VALUE;
		for(int i=0;i<values.length;i++)
		{
			int maxIndex = maxIndex(values);
			if(maxIndex!=-1)
			{
				ranks[maxIndex]=i+1;
				values[maxIndex] = Double.MIN_VALUE;
			}
		}
		return ranks;
	}
	public void writeToFile(String stringToWrite,String path)
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
	public void generateRankFile(String inputFilePath,String outputFilePathRanks,String outputFileTopk,String outputFileTopNames,int k) throws Exception
	{
		CSVReader inputFile = new CSVReader(new FileReader(inputFilePath));
	    String[] algorithms = inputFile.readNext();
	    StringBuilder sb = new StringBuilder();
	    StringBuilder topk = new StringBuilder();
	    StringBuilder topNames = new StringBuilder();
		   
	    
	    for(int i=0;i<algorithms.length;i++)
	    {
	    	sb.append(algorithms[i]+",");
	    }
	    String[] copy = new String[algorithms.length];
	    for(int i=0;i<algorithms.length;i++)
	    {
	    	copy[i]=algorithms[i];
	    }
	    sb.deleteCharAt(sb.length()-1);
    	sb.append("\n");
	    while (((algorithms = inputFile.readNext()) != null)) {
		    sb.append(algorithms[0]+",");
		   
		   // topk.append(algorithms[0]+" -> ");
		    topNames.append(algorithms[0]+" -> ");
		    
	    	double array[] = new double[algorithms.length-1];
	    	for(int i=1;i<algorithms.length;i++)
	    	{
		    	array[i-1] = Double.parseDouble(algorithms[i]);
	    	}
	    	int[] ranks = generateRanks(array);
	    	for(int i=0;i<ranks.length;i++)
	    	{
	    		sb.append(ranks[i]+",");
	    	}
	    	sb.deleteCharAt(sb.length()-1);
	    	sb.append("\n");
	    	for(int i=1;i<=k;i++)
	    	{
	    		for(int j=0;j<ranks.length;j++)
	    		{
	    			if(ranks[j]==i)
	    			{
	    				topk.append(j+",");
	    				topNames.append(copy[j+1]+",");
	    				break;
	    			}
	    		}
	    	}
	    	topk.deleteCharAt(topk.length()-1);
	    	topNames.deleteCharAt(topNames.length()-1);
	    	topk.append("\n");
	    	topNames.append("\n");
	    	
	    }
	    sb.deleteCharAt(sb.length()-1);
	    topk.deleteCharAt(topk.length()-1);
    	topNames.deleteCharAt(topNames.length()-1);
    	
    	
    	writeToFile(sb.toString(), outputFilePathRanks);
    	writeToFile(topk.toString(), outputFileTopk);
    	writeToFile(topNames.toString(), outputFileTopNames);
    	
	}
	

}
