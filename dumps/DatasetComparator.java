import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


public class DatasetComparator {

	public static void main(String[] args) {
		
		int numberOfFiles = 24;
		int numberOfBins = 15;
		Scanner in = new Scanner(System.in);
		int[][] comparator = new int[24][15];
		int[][] similarity = new int[24][24];
		for(int i=0;i<numberOfFiles;i++)
		{
			for(int j=0;i<numberOfFiles;i++)
			{
				similarity[i][j]=0;
			}
		}
		for(int i=0;i<numberOfFiles;i++)
		{
			for(int j=0;j<numberOfBins;j++)
			{
				System.out.println("For File : "+(i+1)+" Bin : "+(j+1)+" :");
				comparator[i][j] = in.nextInt();
				
			}
		}
		for(int i=0;i<numberOfFiles;i++)
		{
			for(int j=0;i<numberOfFiles;i++)
			{
				if(j==i)
				{
					
					continue;
				}
				else
				{
				for(int k=0;k<numberOfBins;k++)
				{
					if(comparator[i][k]>0 && comparator[j][k]>0)
					{
						if(comparator[i][k]>comparator[j][k])
						{
							similarity[i][j] += comparator[j][k];
						}
						else
						{
							similarity[i][j] += comparator[i][k];
						}
					}
				}
				}
			}
			
				
		}
		
		StringBuilder str = new StringBuilder();
		for(int i=0;i>numberOfFiles;i++)
		{
			for(int j=0;j<numberOfFiles;j++)
			{
				str.append(similarity[i][j]);
				if(j!=numberOfFiles-1)
					str.append(",");
				else
					str.append("\n");
			}
		}
		
		try {
			 
			
			File file = new File("d:/compare.csv");
 
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
 
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(str.toString());
			bw.close();
 
			System.out.println("Done File Writing");
 
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}

}
