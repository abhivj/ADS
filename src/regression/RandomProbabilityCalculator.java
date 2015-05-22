package regression;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class RandomProbabilityCalculator {
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
	public double[] calculateProbabilities(int totalAlgorithms)
	{
		double[] prob = new double[totalAlgorithms];
		for(int i=1;i<=totalAlgorithms;i++)
		{
			int start = totalAlgorithms-i;
			int divider = totalAlgorithms;
			double result=1;
			for(int j=1;j<=i;j++)
			{
				result *= (double)start/divider;
				start--;
				divider--;
			}
			//System.out.println(result);
			result = 1 - result;
			
			prob[i-1] = result;
		}
		return prob;
	}
	public void printProbability(String filepath,int totalAlgorithms)
	{
		StringBuilder sb = new StringBuilder();
		double[] prob = calculateProbabilities(totalAlgorithms);
		for(int i=0;i<totalAlgorithms;i++)
		{
			sb.append(i+1+",");
			sb.append(prob[i]);
			sb.append("\n");
		}
		sb.deleteCharAt(sb.length()-1);
		writeCSVReport(sb.toString(), filepath);
	}
	
}
