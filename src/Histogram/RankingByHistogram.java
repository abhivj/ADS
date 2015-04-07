package Histogram;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class RankingByHistogram {
	
	
	public int[] sumVec(int[] a,int[] b)
	{
		int[] sum = new int[a.length];
		for(int i=0;i<a.length;i++)
		{
			sum[i] =a[i]+b[i];
		}
		return sum;
	}
	public int[] arrange(int sum[])
	{
		int[] res = new int[sum.length];
		
		for(int i=0;i<sum.length;i++)
		{
			int minIndex = minIndex(sum);
			res[i] = minIndex + 1;
			sum[minIndex] = Integer.MAX_VALUE;
		}
		return res;
		
	}
	public int minIndex(int a[])
	{
		int min=Integer.MAX_VALUE;
		int minI = -1;
		for(int i=0;i<a.length;i++)
		{
			if(a[i]<min)
			{
				min = a[i];
				minI = i;
			}
		}
		return minI;
	}
	public void ranker(String ExpFile,String actualFile,String saveFile,int dataset,int algo,int k) throws Exception
	{
		File exFile = new File(ExpFile);
		File acFile = new File(actualFile);
		
		
		int actualRank[][] = new int[dataset][algo];
		int similarity[][] = new int[dataset][dataset];
		
		
		StringBuilder sb = new StringBuilder();
		String bsStr = "";
		String exStr="";
		BufferedReader br = new BufferedReader(new FileReader(exFile));
		BufferedReader be = new BufferedReader(new FileReader(acFile));
		int i=0;
		while (((bsStr = br.readLine()) != null)  && i<dataset) {
            String[] split1 = bsStr.split(",");
            for(int p=0;p<dataset;p++)
            {
            	similarity[i][p] = Integer.parseInt(split1[p]);
            }
            i++;
         }
		i=0;
		while (((exStr = be.readLine()) != null)  && i<dataset) {
            String[] split1 = exStr.split(",");
            for(int p=0;p<algo;p++)
            {
            	actualRank[i][p] = Integer.parseInt(split1[p]);
            }
            i++;
         }
		int sum[][] = new int[dataset][algo];
		for(int j=0;j<dataset;j++)
		{
			for(int p=0;p<k;p++)
			{
				//System.out.print(similarity[j][p]+",");
				sum[j] = sumVec(sum[j], actualRank[ similarity[j][p] - 1 ] );
			}
		//	System.out.println();
		}
		for(int t=0;t<dataset;t++)
		{
			sum[t] = arrange(sum[t]);
		}
		
		for(int l=0;l<dataset;l++)
		{
			for(int g=0;g<algo;g++)
			{
				sb.append(sum[l][g]);
				if(g!=(algo-1))
					sb.append(",");
				else
					sb.append("\n");
			}
			
		}
		sb.deleteCharAt(sb.length()-1);
		writeCSVReport(sb.toString(), saveFile);

		
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
