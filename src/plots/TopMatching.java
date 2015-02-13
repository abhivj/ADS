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

public class TopMatching {

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
	
	
}
