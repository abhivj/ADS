import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * This Class is used to Compare datasets which have binary classification.
 * @author abhivj
 *
 */
public class CompareBinaryClass {

	public static void main(String[] args) {
	
		int datasets = 8;
		int bins = 16;
		int topk=5;
		double[][] dataMatrix = readCSV("D:/work241/NumericalDataBinary6.csv",datasets,bins);
		double[][] valueMatrix = generateCompareMatrix(dataMatrix,datasets,bins);
		int[][] rankMatrix = calculateRankMatrix(valueMatrix);
		printTopK(rankMatrix, topk, "D:/work241/BinaryClassTopk6.txt");
		System.out.println("Done");

	}
	/**
	 *  
	 * @param path path of csv file
	 * @param dataset number of datasets
	 * @param bin number of bin in the histogram
	 * @return 2D matrix of bin and datasets
	 * 
	 * Reads a csv file and returns a double array of data.
	 */
	@SuppressWarnings("resource")
	public static double[][] readCSV(String path, int dataset,int bin)
	{
		String csvFilePath = path;
		int datasets = dataset;
		int bins = bin;
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		double[][] dataMatrix = new double[bins][2*dataset];
		try{
			br = new BufferedReader(new FileReader(csvFilePath));
			for(int i=0;i<bins;i++)
			{
				line = br.readLine();
				String[] lineVector = line.split(cvsSplitBy);
				for(int j=0;j<2*datasets;j++)
				{
					dataMatrix[i][j] = Double.parseDouble(lineVector[j]);
				}
			}
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
		}
		return dataMatrix;
	}
	/**
	 * Takes a dataMatrix which contains histogram bin and datasets and compare them and return compare Matrix.
	 * @param dataMatrix
	 * @param datasets
	 * @param bin
	 * @return
	 */
	public static double[][] generateCompareMatrix(double[][] dataMatrix,int datasets,int bin)
	{
		double[][] value= new double[datasets][datasets];
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
					veci[k] = dataMatrix[k][i];
					vecj[k] = dataMatrix[k][j];
					veci1[k] = dataMatrix[k][i+1];
					vecj1[k] = dataMatrix[k][j+1];
				}
				
				
				double value1 = compareVectors(veci,vecj)+compareVectors(veci1,vecj1);
				double value2 = compareVectors(veci1,vecj)+compareVectors(veci,vecj1);
				int indexi,indexj;
				indexi = i/2;
				indexj = j/2;
				value[indexi][indexj] = Math.max(value1,value2);
				
			}
		}
		return value;
		
	}
	/**
	 * Takes two vectors and comapre them
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static double compareVectors(double[] d1,double[] d2)
	{
		int length = d1.length;
		double sum=0;
		for(int i=0;i<length;i++)
		{
			sum+=Math.min(d1[i], d2[i]);
		}
		return sum;
		
	}
	/**
	 * Takes a matrix and return a rank matrix of each datasets
	 * @param corelationInput
	 * @return
	 */
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
	/**
	 * print top k dataset respect to each dataset to a file.
	 * @param rank
	 * @param k
	 * @param filePath
	 */
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

}
