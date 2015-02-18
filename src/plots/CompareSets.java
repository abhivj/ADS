package plots;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class CompareSets {

	public boolean compareIsContains(String[] baseFileString,String[] experimentFileString)
	{
	
		for(int i=0;i<experimentFileString.length;i++)
		{
			for(int j=0;j<baseFileString.length;j++)
			{
				if(experimentFileString[i].equals(baseFileString[j]))
					return true;
			}
		}
		return false;
	}
	public double CompareContainsInTop(String baseFile,String ExperimentFile) throws Exception
	{
		double matchingPercentage=0;
		BufferedReader brFile1 = new BufferedReader(new FileReader(baseFile));
		BufferedReader brFile2 = new BufferedReader(new FileReader(ExperimentFile));
		String line1,line2;
		String fileName;
		int lineCount=0;
		int positiveCount=0;
		StringBuilder sb = new StringBuilder();
		while((line1=brFile1.readLine())!=null && (line2=brFile2.readLine())!=null)
		{
			String[] splitInSets1 = line1.split(",");
			String[] splitInSets2 = line2.split(",");
			fileName = splitInSets1[0];
			String[] topSetElements1 = splitInSets1[1].split("-");
			String[] topSetElements2 = splitInSets2[1].split("-");
			lineCount++;
			if(compareIsContains(topSetElements1, topSetElements2))
				positiveCount++;
		}
		matchingPercentage = (double)positiveCount/lineCount;
		//System.out.println(matchingPercentage);
		brFile1.close();
		brFile2.close();
		return matchingPercentage;	
	}
	
}
