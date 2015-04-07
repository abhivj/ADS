package plots;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import regression.*;
import weka.core.converters.ConverterUtils.DataSource;

import com.opencsv.CSVReader;

public class TopMatching {
	public int match(String[] a, String[] b, int k)
	{
		int ans=0;
		for(int i=0;i<k;i++)
		{
			for(int j=0;j<k;j++)
			{
				if(a[i].equals(b[j]))
					ans++;
					//return true;
			}
		}
		
		return ans;
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

	public void compareTwoFile(String actualFilePath,String calculatedFilePath,int topk,String saveFilePath) throws Exception
	{
		BufferedReader brRow = new BufferedReader(new FileReader(actualFilePath));
		String line;
		int lineCount=0;
		while((line=brRow.readLine())!=null)
		{
			lineCount++;
		}
		brRow.close();
		
		BufferedReader br1 = new BufferedReader(new FileReader(actualFilePath));
		BufferedReader br2 = new BufferedReader(new FileReader(calculatedFilePath));

		double[] dd = new double[topk];
		boolean matrix[][] = new boolean[lineCount][topk];
		
		String line1;
		String line2;
		int k=0;
		while ((line1 = br1.readLine()) != null && (line2=br2.readLine())!=null)
		{
			String[] first = line1.split(",");
			String[] second = line2.split(",");
			
			Set<String> s1 = new HashSet<String>();
			Set<String> s2 = new HashSet<String>();
			
			for(int i=1;i<=topk;i++)
			{
				s1.add(first[i-1]);
				for(int j=0;j<i;j++)
				{
					if(s1.contains(second[j]))
					{
						matrix[k][i-1] = true;
						break;
					}
				}
				
			}
			k++;
		}
		br1.close();
		br2.close();
		
		for(int i=0;i<topk;i++)
		{
			for(int j=0;j<lineCount;j++)
			{
				if(matrix[j][i])
				{
					dd[i]++;
				}
			}
			
		}
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<topk;i++)
		{
			sb.append((i+1)+",");
			dd[i] = dd[i]/lineCount;
			sb.append(dd[i]+"\n");
		}
		sb.deleteCharAt(sb.length()-1);
		writeCSVReport(sb.toString(), saveFilePath);
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
	
	public void allResult(String FolderPath,String savePath,int topk) throws Exception
	{
		File[] arffFiles = readAllFiles(FolderPath);
	
		RandomProbabilityCalculator rbc = new RandomProbabilityCalculator();
		rbc.printProbability(savePath+"-prob.csv", topk);
		
		String[][] infoMatrix = new String[arffFiles.length+2][topk+1];
		//Adding count till top k
		infoMatrix[0][0] = "Count";
		for(int i=1;i<=topk;i++)
		{
			infoMatrix[0][i] = String.valueOf(i);
		}
		//Adding Probability file
		
		infoMatrix[1][0] = "Random Probability";
		BufferedReader probReader = new BufferedReader(new FileReader(savePath+"-prob.csv"));
	    String probLine;
	    int p=1;
	    while ((probLine = probReader.readLine()) != null) {
	       int index = probLine.indexOf(',');
	       String value = probLine.substring(index+1);
	       infoMatrix[1][p] = value;
	       p++;
	    }
		for(int j=1;j<=arffFiles.length;j++)
		{
			infoMatrix[j+1][0] = arffFiles[j-1].getName().toString();
				int i=1;
				BufferedReader reader = new BufferedReader(new FileReader(arffFiles[j-1]));
			    String line;
			    while ((line = reader.readLine()) != null) {
			       int index = line.indexOf(',');
			       String value = line.substring(index+1);
			       infoMatrix[j+1][i] = value;
			       i++;
			    }
		}
		String[][] printableMatrix = transpose(infoMatrix);
		StringBuilder sb = stringPrep(printableMatrix);
		writeCSVReport(sb.toString(), savePath);
		
	}
	
	public void compareExpectation(String baseFile,String ExperimentFile,int algorithms,int start,int stepSize,String resultFile) throws Exception
	{
		File bsFile = new File(baseFile);
		File exFile = new File(ExperimentFile);
		
		int resultArraySize = algorithms/stepSize;
		double res[] = new double[resultArraySize];
		StringBuilder sb = new StringBuilder();
		String bsStr = "";
		String exStr="";
		BufferedReader br = new BufferedReader(new FileReader(bsFile));
		BufferedReader be = new BufferedReader(new FileReader(ExperimentFile));
        int i = 0;
		while (((bsStr = br.readLine()) != null) && ((exStr = be.readLine())!=null) && i<algorithms) {
            String[] split1 = bsStr.split(",");
            String[] split2 = exStr.split(",");
            
            for(int k=stepSize;k<=algorithms;k=k+stepSize)
            {
            	
            		 res[(k/stepSize) - 1 ] += match(split1,split2,k);
            	 
            		
            }
           i++;
         }
		for(int k=0;k<resultArraySize;k++)
		{
			sb.append((k+1)*stepSize);
			sb.append(",");
			res[k] = (double)res[k]/algorithms;
			sb.append(res[k]+"\n");
		}
		writeCSVReport(sb.toString(), resultFile);
	}

}

