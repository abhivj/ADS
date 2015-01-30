package regression;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import Clustering.ClusterObject;
import MultivariateNormalDistributionMixture.MultivariateNormalDistribution;
import MultivariateNormalDistributionMixture.Pairs;

public class RegressorTraining {

	String fileName;
	int histogramBins[][];
	ClusterObject[] co;
	Pairs[] pp;
	MultivariateNormalDistribution[] mnd;
	
	/**
	 * take a string and path and write csv report on that location
	 * @param stringToWrite
	 * @param path
	 */
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
	public void printInFeatureVector(String outputFolder,String csvFile,RegressorTraining[] RT,int classes)
	{
		StringBuilder sb = new StringBuilder();
		StringBuilder header = new StringBuilder();
		int coloumnCounter=0;
		boolean flag = false;
		for(int i=0;i<RT.length;i++)
		{
			fileName = RT[i].fileName;
			histogramBins = RT[i].histogramBins;
			co = RT[i].co;
			pp = RT[i].pp;
			mnd = RT[i].mnd;
			sb.append(fileName+",");
			coloumnCounter++;
			for(int j=0;j<classes;j++)
			{
				for(int k=0;k<(histogramBins[j]).length/2;k++)
				{
					sb.append(histogramBins[j][k]+",");
					coloumnCounter++;
				}
			}
			//System.out.println(RT[i].co.length);
			for(int j=0;j<classes;j++)
			{
			//	System.out.println((co[j].getClusterInstances()).length);
				for(int k=0;k<(co[j].getClusterInstances()).length;k++)
				{
					sb.append(co[j].getClusterInstances()[k]+",");
					coloumnCounter++;
				}
				for(int k=0;k<(co[j].getClusterInstances()).length;k++)
				{
					sb.append(co[j].getClusterValues()[k]+",");
					coloumnCounter++;
				}
				for(int k=0;k<(co[j].getDistance()).length;k++)
				{
					for(int l=0;l<(co[j].getDistance()[k]).length;l++)
					{
						sb.append(co[j].getDistance()[k][l]+",");
						coloumnCounter++;
					}
				}
			}
			for(int j=0;j<classes;j++)
			{
				for(int k=0;k<(pp[j].getMean()).length;k++)
				{
					for(int l=0;l<(pp[j].getMean()[k]).length;l++)
					{
						sb.append(pp[j].getMean()[k][l]+",");
						coloumnCounter++;
					}
				}
			}
			
			for(int j=0;j<classes;j++)
			{
				for(int k=0;k<(mnd[j].getMean()).length;k++)
				{
					sb.append(mnd[j].getMean()[k]+",");
					coloumnCounter++;
				}
				
				for(int k=0;k<(mnd[j].getCovariances()).length;k++)
				{
					for(int l=0;l<(mnd[j].getCovariances()[k]).length;l++)
					{
						sb.append(mnd[j].getCovariances()[k][l]+",");
						coloumnCounter++;
					}
				}
				
			}
			sb.deleteCharAt(sb.length()-1);
			sb.append("\n");
			if(flag==false)
			{
				flag=true;
				for(int k=0;k<coloumnCounter;k++)
				{
					header.append("Attr"+(k+1)+",");
				}
				if(header.length()>0)
					header.deleteCharAt(header.length()-1);
				header.append("\n");
				
			}
			
		}
		if(sb.length()>0)
		sb.deleteCharAt(sb.length()-1);
		header.append(sb.toString());
		writeCSVReport(header.toString(),outputFolder+csvFile);
	}
}
