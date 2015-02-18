package plots;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import Stats.*;

public class GenerateSets {

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
	
	public int[] ranksOfDatasets(double[] performence)
	{
		double[] newPerformence = new double[performence.length];
		for(int i=0;i<performence.length;i++)
		{
			newPerformence[i] = performence[i];
		}
		int[] ranks = new int[newPerformence.length];
		for(int i=0;i<newPerformence.length;i++)
		{
			double max = Double.MIN_VALUE;
			int maxIndex = -1;
			for(int j=0;j<newPerformence.length;j++)
			{
				if(max<newPerformence[j])
				{
					max=newPerformence[j];
					maxIndex = j;
				}
			}
			newPerformence[maxIndex] = Double.MIN_VALUE;
			ranks[maxIndex] = i+1;
		}
		
		return ranks;
	}
	public int findMin(int[] ranks)
	{
		int min = Integer.MAX_VALUE;
		int minIndex = 0;
		for(int i=0;i<ranks.length;i++)
		{
			if(min>ranks[i])
			{
				min = ranks[i];
				minIndex = i;
			}
		}
		return minIndex;
	}
	public int findMax(int[] ranks)
	{
		int max = Integer.MIN_VALUE;
		int maxIndex = 0;
		for(int i=0;i<ranks.length;i++)
		{
			if(max<ranks[i])
			{
				max = ranks[i];
				maxIndex = i;
			}
		}
		return maxIndex;
	}
	public double[] normalizeData(double[] performance,double min,double max)
	{
		double[] normalizeData = new double[performance.length];
		//Statistics stats = new Statistics(performance);
		//double mean = stats.getMean();
		//double stdDev = stats.getStdDev();
		for(int i=0;i<performance.length;i++)
		{
			normalizeData[i] = Math.abs((performance[i] - min)/(max-min)); 
		}
		
		return normalizeData;
	}
	public StringBuilder makeSets(int ranks[],double[] performance,int noOfBins)
	{
		double minimumValue = performance[findMax(ranks)];
		double maximumValue = performance[findMin(ranks)];
		double range = (double)1/noOfBins;
		double[] normalizeData = normalizeData(performance,minimumValue,maximumValue);
		
		StringBuilder sb = new StringBuilder();
		boolean setFlag = false;
		double setPerformance = 0;
		for(int i=0;i<ranks.length;i++)
		{
			int minRank = findMin(ranks);
			ranks[minRank]=Integer.MAX_VALUE;
			if(setFlag==true)
			{
				double diff = setPerformance - normalizeData[minRank];
				
				if(diff>range)
				{
					setFlag = false;
				}
			}
			
			
			
			if(setFlag==false)
			{
				setPerformance = normalizeData[minRank];
				if(sb.length()>0)
				{
					sb.append(",");
				}
				sb.append(minRank);
				setFlag = true;
			}
			else
			{
				sb.append("-"+minRank);
			}
		}
		return sb;
	}
	public void generateSets(String inputFilePath,int range,String outputFilePath) throws Exception
	{
		BufferedReader brRow = new BufferedReader(new FileReader(inputFilePath));
		String line;
		line=brRow.readLine();
		String[] splitter = line.split(",");
		int numberOfAlgorithms = splitter.length - 1;
		StringBuilder sb = new StringBuilder();
		while((line=brRow.readLine())!=null)
		{
			String[] first = line.split(",");
			sb.append(first[0]+",");
			double[] performance = new double[numberOfAlgorithms];
			for(int i=0;i<numberOfAlgorithms;i++)
			{
				performance[i] = Double.parseDouble(first[i+1]);
			}
			int ranks[] = ranksOfDatasets(performance);
			StringBuilder sets = makeSets(ranks, performance, range);
			sb.append(sets);
			sb.append("\n");
		}
		sb.deleteCharAt(sb.length()-1);
		writeCSVReport(sb.toString(), outputFilePath);
		brRow.close();
	}
	
	
}
