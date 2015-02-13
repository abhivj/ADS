package plots;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RandomRankGenerator {

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
	
	public void fileGenerator(String actualFilePath,String saveFile,int k) throws Exception
	{
		
		BufferedReader brRow = new BufferedReader(new FileReader(actualFilePath));
		String line;
		int lineCount=0;
		while((line=brRow.readLine())!=null)
		{
			lineCount++;
		}
		brRow.close();
		
		int randomRank[] = new int[k];
		for(int i=0;i<k;i++)
		{
			randomRank[i]=i;
		}
		StringBuilder sb = new StringBuilder();
		for(int j=0;j<lineCount;j++)
		{
			
			List<Integer> list = new ArrayList<>();
			  for (int i : randomRank) {
			    list.add(i);
			  }

			  Collections.shuffle(list);

			  for (int i = 0; i < list.size(); i++) {
				  randomRank[i] = list.get(i);
			  }
			  for(int i=0;i<randomRank.length;i++)
			  {
				  sb.append(randomRank[i]+",");
			  }
			  sb.deleteCharAt(sb.length()-1);
			  sb.append("\n");
		}
		sb.deleteCharAt(sb.length()-1);
		writeCSVReport(sb.toString(), saveFile);
		
	}
	
	
	
}
