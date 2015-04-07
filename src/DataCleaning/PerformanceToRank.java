package DataCleaning;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class PerformanceToRank {
	
	public int[][] transpose(int[][] st)
	{
		int[][] tr = new int[st[0].length][st.length];
		for(int i=0;i<tr.length;i++)
		{
			for(int j=0;j<tr[i].length;j++)
			{
				tr[i][j]= st[j][i];
			}
		}
		return tr;
	}
	
	public void ranker(String performanceFile,String rankFile,String transposePath,String correlationFile) throws Exception
	{
		File newFile = new File(performanceFile);
		StringBuilder sb = new StringBuilder();
		 
		//To get dataset count
		
		String thisLine="";
		int dataCount=0;
		BufferedReader brd = new BufferedReader(new FileReader(newFile));
		String algorithmName = brd.readLine();
		sb.append(algorithmName+"\n");
		String[] algorithms = sb.toString().split(",");
		int totalAlgorithms = algorithms.length - 1;
		
		while ((thisLine = brd.readLine()) != null) {
            dataCount++;
         }       
     	double[][] Comparision = new double[dataCount][totalAlgorithms];
    	int[][] weight = new int[dataCount][totalAlgorithms];
    	
    	String[] fileNames = new String[dataCount];
    	
		try (BufferedReader br = new BufferedReader(new FileReader(newFile)))
		{
			algorithmName = br.readLine();
			for(int i=0;i<dataCount;i++)
			{
				String[] accuracy = br.readLine().toString().split(",");
				fileNames[i] = accuracy[0];
				for(int j =1;j<accuracy.length;j++)
				{
					
					Comparision[i][j-1] = Double.parseDouble(accuracy[j]);
				}
			}
	   		for(int k=0;k<dataCount;k++)
    		{
    			double[][] sample = new double[2][totalAlgorithms];
    			for(int l=0;l<totalAlgorithms;l++)
    			{
    				sample[0][l]=Comparision[k][l];
    				sample[1][l]=(l+1);
    			}
    		
    			int n = totalAlgorithms;
    			double swap1,swap2;
    			for (int c = 0; c < ( n - 1 ); c++) 
    			{
    				for (int d = 0; d < n - c - 1; d++) 
    				{
    					if (sample[0][d] > sample[0][d+1]) /* For descending order use < */
    					{
    						swap1 = sample[0][d];
    						sample[0][d]   = sample[0][d+1];
    						sample[0][d+1] = swap1;
                       
    						swap2 = sample[1][d];
    						sample[1][d]   = sample[1][d+1];
    						sample[1][d+1] = swap2;
    					}
    				}
    			}
    			for(int l=0;l<totalAlgorithms;l++)
    			{
    				Double d = new Double(sample[1][l]);
    				weight[k][d.intValue()-1] = totalAlgorithms-l;
    			}
    		}
			
    		System.out.println("Rank Vector ");
    		for(int k=0;k<dataCount;k++)
    		{
    			sb.append(fileNames[k]);
    			sb.append(",");
    			for(int l=0;l<totalAlgorithms;l++)
    			{
    				System.out.print("  ||  "+weight[k][l]);
    				sb.append(weight[k][l]);
    				if(l!=totalAlgorithms-1)
    					sb.append(",");
    				else
    				{
    					//if(k!=arffFiles.length-1)
    					sb.append("\n");
    				} 
    			}

    		}
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		StringBuilder trans = new StringBuilder();
		StringBuilder corr = new StringBuilder();
		int[][] transpose = transpose(weight);
		for(int i=0;i<transpose.length;i++)
		{
			for(int j=0;j<transpose[i].length;j++)
			{
				trans.append(transpose[i][j]);
				if(j!=transpose[i].length-1)
					trans.append(",");
				else
				{
					trans.append("\n");
				} 
				
			}
		}
		
		trans.deleteCharAt(trans.length()-1);
		
		
		for(int i=0;i<weight.length;i++)
		{
			for(int j=0;j<weight[i].length;j++)
			{
				corr.append(weight[i][j]);
				if(j!=weight[i].length-1)
					corr.append(",");
				else
				{
					corr.append("\n");
				} 
				
			}
		}
		
		corr.deleteCharAt(trans.length()-1);
		
		System.out.println("Done");
		writeCSVReport(sb.toString(),rankFile);
		writeCSVReport(trans.toString(),transposePath);
		writeCSVReport(corr.toString(), correlationFile);
		System.out.println(sb.toString());
	 

	}
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
	

}
