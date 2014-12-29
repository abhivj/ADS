import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class KolmogrovSmirnovTestWithExtraction {

	public static double criticalDifference(double[] x,double[] y,int totalBinCount)
	{
		double value=1.36/Math.sqrt(totalBinCount);
		double max=0;
		for(int i=0;i<x.length;i++)
		{
			double diff = Math.abs(x[i]-y[i]);
			if(diff>max)
			{
				max=diff;
			}
		}
		if(max<value)
		return max;
		else
			return Double.MAX_VALUE;
		
	}

	public static int getToalBinCount(int[] x)
	{
		int sum=0;
		for(int i=0;i<x.length;i++)
		{
			sum+=x[i];
		}
		return sum;
		
	}
	public static double runningDistanceAndCriticalDifference(double[] x,double[] y,int getTotalBin)
	{
		double criticalValue = 1.36/Math.sqrt(getTotalBin);
		double[] x1 = new double[x.length];
		x1[0] = x[0];
		double[] y1 = new double[y.length];
		y1[0] = y[0];
		for(int i=1;i<x.length;i++)
		{
			x1[i] = x1[i-1]+x[i];
			y[i] = y1[i-1]+y[i];
		}
		
		double max=0;
		for(int i=0;i<x.length;i++)
		{
			double diff = Math.abs(x[i]-y[i]);
			if(diff>max)
			{
				max=diff;
			}
		}
		if(max<criticalValue)
		return max;
		else
			return Double.MAX_VALUE;

		
	}
	@SuppressWarnings("resource")
	public static int[][] readCSVBinary(String path,int dataset,int bin)
	{
		String csvFilePath = path;
		int datasets = dataset;
		int bins = bin;
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		int[][] dataMatrix = new int[bins][2*dataset];
		try{
			br = new BufferedReader(new FileReader(csvFilePath));
			for(int i=0;i<bins;i++)
			{
				line = br.readLine();
				String[] lineVector = line.split(cvsSplitBy);
				for(int j=0;j<2*datasets;j++)
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
	public static double[][] similarityMatrixBinary(int[][] dataMatrix,int datasets,int bin,int getTotalBin)
	{
		double[][] value= new double[datasets][datasets];
		double[][] normalizeMatrix = new double[dataMatrix.length][dataMatrix[0].length];
		for(int i=0;i<dataMatrix[0].length;i++)
		{
			int sum=0;
			for(int j=0;j<dataMatrix.length;j++)
			{
				sum+=dataMatrix[j][i];
			}
			for(int j=0;j<dataMatrix.length;j++)
			{
				normalizeMatrix[j][i]=(double)dataMatrix[j][i]/(double)sum;
			}

		}
		
		for(int i=0;i<2*datasets;i=i+2)
		{
			for(int j=0;j<2*datasets;j=j+2)
			{
				if(i==j)
					continue;
				
				double[] veci = new double[bin];
				double[] vecj = new double[bin];
				double[] veci1 = new double[bin];
				double[] vecj1 = new double[bin];
				
				for(int k=0;k<bin;k++)
				{
					veci[k] = normalizeMatrix[k][i];
					vecj[k] = normalizeMatrix[k][j];
					veci1[k] = normalizeMatrix[k][i+1];
					vecj1[k] = normalizeMatrix[k][j+1];
				}
				
				
				double value1 = runningDistanceAndCriticalDifference(veci,vecj,getTotalBin)+runningDistanceAndCriticalDifference(veci1,vecj1,getTotalBin);
				double value2 = runningDistanceAndCriticalDifference(veci1,vecj,getTotalBin)+runningDistanceAndCriticalDifference(veci,vecj1,getTotalBin);
				int indexi,indexj;
				indexi = i/2;
				indexj = j/2;
				value[indexi][indexj] = Math.min(value1,value2);
				
			}
		}
		return value;
		
		
	}
	
	
	@SuppressWarnings("resource")
	public static int[][] readCSV(String path, int dataset,int bin)
	{
		String csvFilePath = path;
		int datasets = dataset;
		int bins = bin;
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		int[][] dataMatrix = new int[bins][datasets];
		try{
			br = new BufferedReader(new FileReader(csvFilePath));
			for(int i=0;i<bins;i++)
			{
				line = br.readLine();
				String[] lineVector = line.split(cvsSplitBy);
				for(int j=0;j<datasets;j++)
				{
					dataMatrix[i][j] = Integer.parseInt(lineVector[j]);
				}
			}
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
		}
			int[][] transposeMat = transposeMatrix(dataMatrix, dataMatrix[0].length, dataMatrix.length);
			return transposeMat;
		
	}
	
	public static int[][] transposeMatrix(int[][] matrix,int datasets,int bins)
	{
		int[][] transpose = new int[datasets][bins];
		//transpose = matrix
		for(int i=0;i<matrix.length;i++)
		{
			for(int j=0;j<matrix[0].length;j++)
			{
				transpose[j][i] = matrix[i][j];
			}
		}
		return transpose;
		
	}
	
	public static double[][] normalizeHistogram(int[][] matrix)
	{
		
		double[][] dist = new double[matrix.length][matrix[0].length];
		
		for(int i=0;i<matrix.length;i++)
		{
			int sum=0;
			for(int j=0;j<matrix[0].length;j++)
			{
				sum+=matrix[i][j];
			}
			for(int j=0;j<matrix[0].length;j++)
			{
				if(sum!=0)
				dist[i][j] = (double)matrix[i][j]/(double)sum;
				else
				dist[i][j] = 0;
			}
				
		}
		
		return dist;
		
	}
	
	public static double[][] runningTotal(double[][] matrix)
	{
		double[][] runningTotalMatrix = new double[matrix.length][matrix[0].length];
		for(int i=0;i<matrix.length;i++)
		{
			runningTotalMatrix[i][0] = matrix[i][0]; 
			for(int j=1;j<matrix[0].length;j++)
			{
				runningTotalMatrix[i][j] = runningTotalMatrix[i][j-1] + matrix[i][j]; 
			}
		}
		
		return runningTotalMatrix;
		
	}
	
	public static double[][] similarityMatrix(double[][] runningTotalMatrix,int datasets,int totalBinCount)
	{
		double[][] similarMat = new double[datasets][datasets];
		for(int i=0;i<datasets;i++)
		{
			similarMat[i][i] = Double.MAX_VALUE;
			for(int j=0;j<datasets;j++)
			{
				if(i==j)
					continue;
				else
					similarMat[i][j] = criticalDifference(runningTotalMatrix[i], runningTotalMatrix[j],totalBinCount);
					
			}
		}
		return similarMat;
		
	}
	

	public static void printSimilar(double[][] similarityMatrix,String filePath)
	{
		String str = "";
		StringBuilder st = new StringBuilder(str);
		for(int i=0;i<similarityMatrix.length;i++)
		{
			st.append("("+(i+1)+")->");
			for(int j=0;j<similarityMatrix[i].length;j++)
			{
				if(similarityMatrix[i][j]<Double.MAX_VALUE)
				{
					st.append(j+1);
					if(j!=(similarityMatrix[i].length-1))
						st.append(",");
					
				}
			}
			st.append("\n");
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
		System.out.println("Done file printing at :"+filePath);
	}
	
	public static void main(String[] args) {
		int dataset=8;
		int bin=16;
		int[][] data = readCSV("D:/work241/PCAClassData.csv", dataset, bin);
		int getTotalCount = getToalBinCount(data[0]);
		double[][] normalizeMatrix = normalizeHistogram(data);
		double[][] runningTotal = runningTotal(normalizeMatrix);
		double[][] similarityMatrix = similarityMatrix(runningTotal, dataset,getTotalCount);
		printSimilar(similarityMatrix, "D:/work241/kolmogrovTestReject.txt");
		
		//Code for binary Separate classes
		int[][] dataBinary = readCSVBinary("D:/work241/NumericalDataBinary6.csv", dataset, bin);
		double[][] similarityMatrixBinary = similarityMatrixBinary(dataBinary, dataset, bin,getTotalCount);
		printSimilar(similarityMatrixBinary, "D:/work241/kolmogrovTestBinaryReject.txt");
		
		
	}

}
