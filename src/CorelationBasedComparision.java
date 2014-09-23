import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Compare all dataset based on rank correlation and generate file.
 * @author abhivj
 *
 */
public class CorelationBasedComparision {

	public static void main(String[] args) {
		
		
		
		int[][] sample = readCSV("D:/work241/CorelationNeeded.csv", 8,13);
		//System.out.println(calculateCorelation(sample[0],sample[1]));
		double[][] result = calculateCorelationMatrix(sample);
		int[][] rank = calculateRankMatrix(result);
		printTopK(rank,5,"d:/work241/CorelationMatrixResult3.txt");
		System.out.println("Done!!");
	}
	
	@SuppressWarnings("resource")
	public static int[][] readCSV(String path, int dataset,int algorithms)
	{
		String csvFilePath = path;
		int datasets = dataset;
		int algorithm = algorithms;
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		int[][] dataMatrix = new int[datasets][algorithms];
		try{
			br = new BufferedReader(new FileReader(csvFilePath));
			for(int i=0;i<datasets;i++)
			{
				line = br.readLine();
				String[] lineVector = line.split(cvsSplitBy);
				for(int j=0;j<algorithm;j++)
				{
					dataMatrix[i][j] = Integer.parseInt(lineVector[j]);
				}
			}
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
		}
		
		
	
		return dataMatrix;
		
	}
	public static double calculateCorelation(int[] x,int[] y)
	{
		int sumX=0,sumY=0;
		double meanX=0,meanY=0;
		for(int i=0;i<x.length;i++)
		{
			sumX=sumX+x[i];
		}
		for(int i=0;i<y.length;i++)
		{
			sumY=sumY+y[i];
		}
		if(x.length>0)
			meanX=(double)sumX/(double)x.length;

		if(y.length>0)
			meanY = (double)sumY/(double)y.length;
		double covariance = 0;
		for(int i=0;i<x.length;i++)
		{
			covariance = covariance+(x[i]-meanX)*(y[i]-meanY);
		}
		covariance = covariance/(x.length-1);
		
		double Sx =0,Sy=0;
		for(int i=0;i<x.length;i++)
		{
			Sx = Sx+(x[i]-meanX)*(x[i]-meanX);
		}
		Sx = Sx/(x.length-1);
		Sx = Math.sqrt(Sx);
		for(int i=0;i<y.length;i++)
		{
			Sy = Sy+(y[i]-meanX)*(y[i]-meanX);
		}
		Sy = Sy/(y.length-1);
		Sy = Math.sqrt(Sy);
		double corelation = covariance/(Sx*Sy);
		
		return corelation;
	}
	
	public static double[][] calculateCorelationMatrix(int[][] dataMatrix)
	{
		int dataRow = dataMatrix.length;
		double[][] result = new double[dataRow][dataRow];
		for(int i=0;i<dataRow;i++)
		{
			for(int j=0;j<dataRow;j++)
			{
				result[i][j]=calculateCorelation(dataMatrix[i], dataMatrix[j]);
			}
		}
		
		return result;
		
	}
	
	public static void printTopK(int[][] rank, int k,String filePath)
	{
		String str = "";
		StringBuilder st = new StringBuilder(str);
		for(int i=0;i<rank.length;i++)
		{
			st.append("("+(i+1)+")->");
			for(int j=0;j<k;j++)
			{
				st.append(rank[i][j]);
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
	
	public static int[][] calculateRankMatrix(double[][] corelationInput)
	{
		double[][] corelationMatrix = new double[corelationInput.length][corelationInput.length];
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
			corelationMatrix[i][i]=-(Double.MAX_VALUE);
		}
		for(int i=0;i<dataset;i++)
		{
			int[] sample = new int[dataset];
			for(int j=0;j<dataset;j++)
			{
				sample[j]=j+1;
			}
			for(int j=0;j<dataset;j++)
			{
				double max=-(Double.MAX_VALUE);
				int maxindex=-1;
				for(int k=0;k<dataset;k++)
				{
					if(corelationMatrix[i][k]>max)
					{
						max=corelationMatrix[i][k];
						maxindex=k;
					}
				}
				if(max!=-(Double.MAX_VALUE))
				{
					rank[i][j]=maxindex+1;
					corelationMatrix[i][maxindex]=-(Double.MAX_VALUE);
				}
				else
				{
					rank[i][j]=0;
				}
			}
		}
		return rank;
	}
}
