package Histogram;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class CompareHistTopK {

	public boolean match(String[] a, String[] b, int k)
	{
		int ans=0;
		for(int i=0;i<k;i++)
		{
			for(int j=0;j<k;j++)
			{
				if(a[i].equals(b[j]))
					//ans++;
					return true;
			}
		}
		
		return false;
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
	public void compare(String baseFile,String ExperimentFile,int stepSize,int limit,int dataset,String resultFile) throws Exception
	{
		
		File bsFile = new File(baseFile);
		File exFile = new File(ExperimentFile);
		
		int resultArraySize = limit/stepSize;
		double res[] = new double[resultArraySize];
		StringBuilder sb = new StringBuilder();
		String bsStr = "";
		String exStr="";
		BufferedReader br = new BufferedReader(new FileReader(bsFile));
		BufferedReader be = new BufferedReader(new FileReader(ExperimentFile));
        int i = 0;
		while (((bsStr = br.readLine()) != null) && ((exStr = be.readLine())!=null) && i<dataset) {
            String[] split1 = bsStr.split(",");
            String[] split2 = exStr.split(",");
            
            for(int k=stepSize;k<=limit;k=k+stepSize)
            {
            	 if(match(split1,split2,k))
            	 {
            		 res[(k/stepSize) - 1 ]++;
            	 }
            		
            }
            
        
            i++;
         }
		for(int k=0;k<resultArraySize;k++)
		{
			sb.append(k+1);
			sb.append(",");
			res[k] = (double)res[k]/dataset;
			sb.append(res[k]+"\n");
		}
		writeCSVReport(sb.toString(), resultFile);
		
	}
	
	
}
