package Histogram;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


class FileObjects{
	String fileName="";
	int[][] histogramBins;
	public String getFileName()
	{
		return fileName;
	}
	public void setFileName(String name)
	{
		this.fileName = name;
	}
	public void setHistogramValues(int values[],int classOfData)
	{
		histogramBins[classOfData] = values;
	}
	public void setHistogram(int classes,int bins)
	{
		histogramBins = new int[classes][bins];
	}
}

public class CompareHistogram {

	
	public static double criticalDifference(double[] x,double[] y)
	{
		double max=0;
		for(int i=0;i<x.length;i++)
		{
			double diff = Math.abs(x[i]-y[i]);
			if(diff>max)
			{
				max=diff;
			}
		}
		
		return max;
		
	}
	public static int runningDistanceAndCriticalDifference(int[] x,int[] y)
	{
		int[] x1 = new int[x.length];
		x1[0] = x[0];
		int[] y1 = new int[y.length];
		y1[0] = y[0];
		for(int i=1;i<x.length;i++)
		{
			x1[i] = x1[i-1]+x[i];
			y1[i] = y1[i-1]+y[i];
		}
		
		int max=0;
		for(int i=0;i<x.length;i++)
		{
			int diff = Math.abs(x[i]-y[i]);
			if(diff>max)
			{
				max=diff;
			}
		}
		
		return max;

		
	}
	
	public static int searchForObject(String name,FileObjects[] Files,int fileInIt)
	{
		for(int i=0;i<Files.length;i++)
		{
			if(i==fileInIt)
				break;
			String fileName = Files[i].getFileName();
			if(name.equals(fileName))
				return i;
		}
		return -1;
	}
	
	public static int CompareArray(int[] arr1,int[] arr2)
	{
		int count=0;
		for(int i=0;i<arr1.length;i++)
		{
			count+=Math.min(arr1[i], arr2[i]);
		}
		return count;
	}
	public static int compareFileObjects(FileObjects file1,FileObjects file2)
	{
		int[] file1class0 = file1.histogramBins[0];
		int[] file1class1 = file1.histogramBins[1];
		int[] file2class0 = file2.histogramBins[0];
		int[] file2class1 = file2.histogramBins[1];
		
		int maxValue = Math.max(CompareArray(file1class0, file2class0)+ CompareArray(file1class1, file2class1),
				CompareArray(file1class1, file2class0)+ CompareArray(file1class0, file2class1)
				);
		
		
		return maxValue;
	}
	public static int compareFileObjectsKolmogrov(FileObjects file1,FileObjects file2)
	{
		int[] file1class0 = file1.histogramBins[0];
		int[] file1class1 = file1.histogramBins[1];
		int[] file2class0 = file2.histogramBins[0];
		int[] file2class1 = file2.histogramBins[1];
		
		int value1 = runningDistanceAndCriticalDifference(file1class0,file2class0)+runningDistanceAndCriticalDifference(file1class1,file2class1);
		int value2 = runningDistanceAndCriticalDifference(file1class0,file2class1)+runningDistanceAndCriticalDifference(file1class1,file2class0);
		
		return Math.min(value1, value2);
	}
	public static int[][] compareMatrix(FileObjects[] files)
	{
		int[][] compareMat = new int[files.length][files.length];
		for(int i=0;i<files.length;i++)
		{
			for(int j=i+1;j<files.length;j++)
			{
				compareMat[i][j]=compareMat[j][i]=compareFileObjects(files[i], files[j]);
			}
		}
		
		return compareMat;
	}
	
	public static int[][] compareMatrixKolmogrov(FileObjects[] files)
	{
		int[][] compareMat = new int[files.length][files.length];;
		for(int i=0;i<files.length;i++)
		{
			for(int j=i+1;j<files.length;j++)
			{
				compareMat[i][j]=compareMat[j][i]=compareFileObjectsKolmogrov(files[i], files[j]);
			}
		}
		
		return compareMat;
	}
	
	
	public static int[][] calculateRankMatrix(int[][] corelationInput)
	{
		int[][] corelationMatrix = new int[corelationInput.length][corelationInput.length];
		for(int i=0;i<corelationInput.length;i++)
		{
			for(int j=0;j<corelationInput.length;j++)
			{
				corelationMatrix[i][j] = corelationInput[i][j];
			}
		}
		
		int dataset = corelationMatrix.length;
		int[][] rank = new int[dataset][dataset];
		for(int i=0;i<dataset;i++)
		{
			corelationMatrix[i][i]=-(Integer.MAX_VALUE);
		}
		for(int i=0;i<dataset;i++)
		{
			
			for(int j=0;j<dataset;j++)
			{
				int max=-(Integer.MAX_VALUE);
				int maxindex=-1;
				for(int k=0;k<dataset;k++)
				{
					if(corelationMatrix[i][k]>max)
					{
						max=corelationMatrix[i][k];
						maxindex=k;
					}
				}
				if(max!=-(Integer.MAX_VALUE))
				{
					rank[i][j]=maxindex;
					corelationMatrix[i][maxindex]=-(Integer.MAX_VALUE);
				}
				else
				{
					rank[i][j]=0;
				}
			}
		}
		return rank;
	}
	public static void printTopK(int[][] rank, int k,String filePath, FileObjects[] fo)
	{
		String str = "";
		StringBuilder st = new StringBuilder(str);
		if(k>fo.length)
			k=fo.length;
		for(int i=0;i<rank.length;i++)
		{
			st.append("("+fo[i].getFileName()+")->");
			for(int j=0;j<k;j++)
			{
				st.append(fo[rank[i][j]].getFileName());
				if(j!=(k-1))
					st.append(",");
				else
					st.append("\n");
			}
		}
		try 
		{
			File file = new File(filePath);
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
 			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(st.toString());
			bw.close();
 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Create File To use by R in friedman test
	 * @param fileName
	 * @param numberOfFiles
	 * @param resultFile
	 */
	public static void saveHistogramInFileFriedman(String fileName,int numberOfFiles,String resultFile)
	{
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		int actualFiles = numberOfFiles/2;
		FileObjects[] fo = new FileObjects[actualFiles];
		int i=0;
		int objectCounter=0;
		try
		{
			br = new BufferedReader(new FileReader(fileName));
			while(i<numberOfFiles)
			{
			line = br.readLine();
			String[] currentfilestoAll = line.split(cvsSplitBy);
			String name = currentfilestoAll[0];
			String classOfData = currentfilestoAll[1];
			int[] values= new int[currentfilestoAll.length-2];
			for(int j=2;j<currentfilestoAll.length;j++)
			{
				double d = Double.valueOf(currentfilestoAll[j]);
				values[j-2]= (int)d; //Integer.valueOf();
			}
			int position = searchForObject(name, fo,objectCounter);
			if(position==-1)
			{
				fo[objectCounter]=new FileObjects();
				fo[objectCounter].setFileName(name);
				fo[objectCounter].setHistogram(2, values.length);
				fo[objectCounter].setHistogramValues(values, Integer.valueOf(classOfData));
				objectCounter++;
			}
			else
			{
				fo[position].setHistogramValues(values, Integer.valueOf(classOfData));
			}
			i++;
		}
			
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
		
		StringBuilder sb = new StringBuilder();
		
		for(int k=0;k<fo.length;k++)
		{
			for(int l=0;l<fo[k].histogramBins.length;l++)
			{
				//sb.append(String.valueOf(l)+fo[k].getFileName());
				for(int p=0;p<fo[k].histogramBins[l].length;p++)
				{
					if(p!=0)
					sb.append(",");
					sb.append(fo[k].histogramBins[l][p]);
					
				}
	
			   if(k!=fo.length-1 || l!=fo[k].histogramBins.length-1)
				{
					sb.append("\n");
				}
			}
		}
		
		try 
		{
			File file = new File(resultFile);
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
 			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(sb.toString());
			bw.close();
			System.out.println("Done File @"+resultFile);
 
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * Create File which used by R in ANOVA test
	 * @param fileName
	 * @param numberOfFiles
	 * @param resultFile
	 */
	public static void saveHistogramInFileAnova(String fileName,int numberOfFiles,String resultFile)
	{
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		int actualFiles = numberOfFiles/2;
		FileObjects[] fo = new FileObjects[actualFiles];
		int i=0;
		int objectCounter=0;
		try
		{
			br = new BufferedReader(new FileReader(fileName));
			while(i<numberOfFiles)
			{
			line = br.readLine();
			String[] currentfilestoAll = line.split(cvsSplitBy);
			String name = currentfilestoAll[0];
			String classOfData = currentfilestoAll[1];
			int[] values= new int[currentfilestoAll.length-2];
			for(int j=2;j<currentfilestoAll.length;j++)
			{
				double d = Double.valueOf(currentfilestoAll[j]);
				values[j-2]= (int)d; //Integer.valueOf();
			}
			int position = searchForObject(name, fo,objectCounter);
			if(position==-1)
			{
				fo[objectCounter]=new FileObjects();
				fo[objectCounter].setFileName(name);
				fo[objectCounter].setHistogram(2, values.length);
				fo[objectCounter].setHistogramValues(values, Integer.valueOf(classOfData));
				objectCounter++;
			}
			else
			{
				fo[position].setHistogramValues(values, Integer.valueOf(classOfData));
			}
			i++;
		}
			
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
		
		StringBuilder sb = new StringBuilder();
		/*
		for(int k=0;k<fo.length;k++)
		{
			for(int l=0;l<fo[k].histogramBins.length;l++)
			{
				sb.append(String.valueOf(l)+fo[k].getFileName());
				for(int p=0;p<fo[k].histogramBins[l].length;p++)
				{
					sb.append(",");
					sb.append(fo[k].histogramBins[l][p]);
					
				}
	
			   if(k!=fo.length-1 || l!=fo[k].histogramBins.length-1)
				{
					sb.append("\n");
				}
			}
		}
		*/
		
		for(int k=0;k<fo.length;k++)
		{
			for(int l=0;l<fo[k].histogramBins.length;l++)
			{
				
				for(int p=0;p<fo[k].histogramBins[l].length;p++)
				{
					sb.append("hist");
					sb.append(p);
					sb.append(",");
					sb.append(fo[k].histogramBins[l][p]);
					
					if(k!=fo.length-1 || l!=fo[k].histogramBins.length-1 || p!=fo[k].histogramBins[l].length-1)
					{
						sb.append("\n");
					}
					
				}
	
			   
			}
		}
		
		/*
		for(int k=0;k<fo.length;k++)
		{
			for(int l=0;l<fo[k].histogramBins.length;l++)
			{
				//sb.append(String.valueOf(l)+fo[k].getFileName());
				for(int p=0;p<fo[k].histogramBins[l].length;p++)
				{
					if(p!=0)
					sb.append(",");
					sb.append(fo[k].histogramBins[l][p]);
					
				}
	
			   if(k!=fo.length-1 || l!=fo[k].histogramBins.length-1)
				{
					sb.append("\n");
				}
			}
		}
		*/
		try 
		{
			File file = new File(resultFile);
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
 			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(sb.toString());
			bw.close();
			System.out.println("Done File @"+resultFile);
 
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
		
		
	}
	
	
	
	public static void BinaryClassComparision(String fileName,int numberOfFiles,String resultFile,int topk)
	{
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		int actualFiles = numberOfFiles/2;
		FileObjects[] fo = new FileObjects[actualFiles];
		int i=0;
		int objectCounter=0;
		try
		{
			br = new BufferedReader(new FileReader(fileName));
			while(i<numberOfFiles)
			{
			line = br.readLine();
			String[] currentfilestoAll = line.split(cvsSplitBy);
			String name = currentfilestoAll[0];
			String classOfData = currentfilestoAll[1];
			int[] values= new int[currentfilestoAll.length-2];
			for(int j=2;j<currentfilestoAll.length;j++)
			{
				double d = Double.valueOf(currentfilestoAll[j]);
				values[j-2]= (int)d; //Integer.valueOf();
			}
			int position = searchForObject(name, fo,objectCounter);
			if(position==-1)
			{
				fo[objectCounter]=new FileObjects();
				fo[objectCounter].setFileName(name);
				fo[objectCounter].setHistogram(2, values.length);
				fo[objectCounter].setHistogramValues(values, Integer.valueOf(classOfData));
				objectCounter++;
			}
			else
			{
				fo[position].setHistogramValues(values, Integer.valueOf(classOfData));
			}
			i++;
		}
			
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
		
		
		//int[][] matrix = compareMatrix(fo);
		int[][] matrix = compareMatrixKolmogrov(fo);
		int[][] rankmatrix = calculateRankMatrix(matrix);
		printTopK(rankmatrix, topk,resultFile, fo);
		System.out.println("Done");
	}
		

		
	 
		
	}
	

