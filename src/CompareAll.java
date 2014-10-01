import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/*
 * This class is used to compare all histograms and gives reuslt store in file.
 */

public class CompareAll {

	public static void generateToppers(int[][] result,int numberOfFiles,int topk,String allResult,String topkResult)
	{
		int[][] topList = new int[numberOfFiles][numberOfFiles];
		for(int i=0;i<numberOfFiles;i++)
		{
			int[] sample = new int[numberOfFiles];
			for(int j=0;j<numberOfFiles;j++)
			{
				sample[j]=j+1;
			}
			for(int j=0;j<numberOfFiles;j++)
			{
				int max=0;
				int maxindex=-1;
				for(int k=j;k<numberOfFiles;k++)
				{
					if(result[i][k]>max)
					{
						max=result[i][k];
						maxindex=k;
					}
				}
				if(max!=0)
				{
				int temp = result[i][j];
				result[i][j] = result[i][maxindex];
				result[i][maxindex]=temp;
				
				int temp2 = sample[j];
				sample[j] = sample[maxindex];
				sample[maxindex]=temp2;
				
				topList[i][j]=sample[j];
				
				}
				else
				{
					topList[i][j]=0;
				}
					
			}
			
		}
		
		StringBuilder str = new StringBuilder();
		for(int i=0;i<numberOfFiles;i++)
		{
			for(int j=0;j<numberOfFiles;j++)
			{
				str.append(topList[j][i]);
				if(j!=numberOfFiles-1)
					str.append(",");
				else if(i!=numberOfFiles-1)
					str.append("\n");
			}
		}
		
		writeCSVReport(str.toString(),allResult);
		
		StringBuilder st = new StringBuilder();
		for(int i=0;i<numberOfFiles;i++)
		{
			st.append("("+(i+1)+") - > ");
			for(int j=0;j<5;j++)
			{
				if(topList[i][j]!=0)
				{
					st.append(topList[i][j]);
					if(j!=4)
					st.append(",");
				}
			}
			st.append("\n");
		}
		writeCSVReport(st.toString(),topkResult);
		
	}

	public static void writeCSVReport(String stringToWrite,String filename)
	{
		try {
			File file = new File(filename);
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
 			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(stringToWrite);
			bw.close();
 
			System.out.println("Done File Writing :"+filename );
 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void compareAllDatasets(String csvFile,String initialResult,String allResult,String topKResult,int numberOfBins,int numberOfFiles,int topk)
	{
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		int[][] result = new int[numberOfFiles][numberOfFiles];
		for(int i=0;i<numberOfFiles;i++)
		{
			for(int j=0;j<numberOfFiles;j++)
			{
				result[i][j]=0;
			}
		}
		try 
		{
			int count=0;
			br = new BufferedReader(new FileReader(csvFile));
			while (count<numberOfBins) 
			{
				line = br.readLine();
			    // use comma as separator
				String[] currentfilestoAll = line.split(cvsSplitBy);
				for(int i=0;i<numberOfFiles;i++)
				{
					for(int j=0;j<numberOfFiles;j++)
					{
					if(j==i)
						continue;
					int temp1 = Integer.parseInt(currentfilestoAll[i]);
					int temp2 = Integer.parseInt(currentfilestoAll[j]);
					if(temp1>0 && temp2>0)
					{
						result[i][j]+=Math.min(temp1, temp2);
					}
				}
			}
			count++;
			}
			StringBuilder str = new StringBuilder();
			for(int i=0;i<numberOfFiles;i++)
			{
				for(int j=0;j<numberOfFiles;j++)
				{
					str.append(result[i][j]);
					if(j!=numberOfFiles-1)
						str.append(",");
					else if(i!=numberOfFiles-1)
						str.append("\n");
				}
			}
			writeCSVReport(str.toString(),initialResult);
			generateToppers(result,numberOfFiles,topk,allResult,topKResult);
			
	 
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (br != null)
			{
				try
				{
					br.close();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
	 
		System.out.println("Done");
	}
	public static void main(String[] args) {
	
		String initialResult = "d:/work241/initialResult2.csv";
		String allResult = "d:/work241/allResult2.csv";
		String topKResult = "d:/work241/topKResult2.txt";
		String csvFile = "d:/work241/PCAClassData.csv";
		int numberOfBins=16;
		int numberOfFiles=8;
		int topk=5;
		compareAllDatasets(csvFile, initialResult, allResult, topKResult, numberOfBins, numberOfFiles, topk);
		
		

	}
	
	
	

}
