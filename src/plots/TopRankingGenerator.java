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

import com.panayotis.gnuplot.plot.Axis;

public class TopRankingGenerator {

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
	public int maxValueIndex(double[] dr)
	{
		double k = Double.MIN_VALUE;
		int index = -1;
		for(int i=0;i<dr.length;i++)
		{
			if(dr[i]>k)
			{
				k=dr[i];
				index=i;
			}
		}
		return index;
	}
	public int minValueIndex(int[] dr)
	{
		double k = Integer.MAX_VALUE;
		int index = -1;
		for(int i=0;i<dr.length;i++)
		{
			if(dr[i]<k)
			{
				k=dr[i];
				index=i;
			}
		}
		return index;
	}
	public void bestRanking(String accuracyFile,String saveFile,int datasets,int algorithms) throws Exception
	{
		int ranks[] = new int[algorithms];
		StringBuilder sb = new StringBuilder();
		BufferedReader br = new BufferedReader(new FileReader(accuracyFile));
		double[][] accuracy = new double[datasets][algorithms];
		//To get algo names
		String line = br.readLine();
	
		int k=0;
		while ((line = br.readLine()) != null && k<datasets)
		{
			String instances[] = line.split(",");
			String fileName = instances[0];
			int i=0;
			while(i<algorithms)
			{
				accuracy[k][i]=Double.parseDouble(instances[i+1]);
				i++;
			}
			k++;
		}
		for(int i=0;i<datasets;i++)
		{
			for(int j=0;j<algorithms;j++)
			{
				int index = maxValueIndex(accuracy[i]);
				ranks[index]+= (j+1);
				accuracy[i][index] = Double.MIN_VALUE;
				//System.out.print(ranks[j]);
			}
		}
		int trueRanking[] = new int[algorithms];
		for(int i=0;i<algorithms;i++)
		{
			int index = minValueIndex(ranks);
			trueRanking[i] = index+1;
			ranks[index] = Integer.MAX_VALUE;
		}
		StringBuilder rs = new StringBuilder();
		for(int i=0;i<algorithms;i++)
		{
			rs.append(trueRanking[i]+",");
		}
		rs.deleteCharAt(rs.length()-1);
		rs.append("\n");
		
		for(int i=0;i<datasets;i++)
		{
			sb.append(rs.toString());
		}
		sb.deleteCharAt(sb.length()-1);
		writeCSVReport(sb.toString(), saveFile);
		
	}
	
}
