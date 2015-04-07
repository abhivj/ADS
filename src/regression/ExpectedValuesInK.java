package regression;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ExpectedValuesInK {
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
	double nCr(int n, int k)
	{
		
		double[][] C = new double[n+1][k+1];
	    int i, j;
	 
	    // Caculate value of Binomial Coefficient in bottom up manner
	    for (i = 0; i <= n; i++)
	    {
	        for (j = 0; j <= Math.min(i, k); j++)
	        {
	            // Base Cases
	            if (j == 0 || j == i)
	                C[i][j] = 1;
	 
	            // Calculate value using previosly stored values
	            else
	                C[i][j] = C[i-1][j-1] + C[i-1][j];
	        }
	    }
	   // System.out.println(n+" : "+k+" "+C[n][k]);
	    return C[n][k];
	}
	
	public double ExpectedValue(int algorithms,int topk)
	{
		double result = 0;
		int n = algorithms;
		int k = topk;
		
		for(int i=0;i<=topk;i++)
		{
			double ans = i*((double)(nCr(topk,i)* nCr(algorithms-topk,topk-i))/(nCr(algorithms,topk)));
			System.out.println(ans);
			//System.out.println("\n");
			result+=ans;
		}
		System.out.println(result);
		
		return result;
	}
	
	public void ExpectationCalculationForAll(String filePath,int algorithms,int start,int stepsize)
	{
		StringBuilder sb = new StringBuilder();
		//sb.append("Values of k out of n,Expected Value\n");
		for(int i=start;i<=algorithms;i=i+stepsize)
		{
			double result = ExpectedValue(algorithms, i);
			sb.append(i+","+result+"\n");
		}
		sb.deleteCharAt(sb.length()-1);
		writeCSVReport(sb.toString(), filePath);
	}
	
	
	
	
}
