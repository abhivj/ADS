package DataCleaning;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;
import weka.core.*;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Add;

public class ConvertToBinary {

	public File[] readAllFiles(String inputFolder)
	{
		File folder = new File(inputFolder);
		File[] listOfFiles = folder.listFiles();
		String files;
	    
		int fileCounter=0;
	    for (int i = 0; i < listOfFiles.length; i++) 
	     {
	    	 if (listOfFiles[i].isFile()) 
	    	 {
	    		 files = listOfFiles[i].getName();
	    		 if (files.endsWith(".arff"))
	    		 {
	    			 fileCounter++;
	    		 }
	    	 }
	     }
	     File[] arffFiles = new File[fileCounter];
	     fileCounter=0;
	     for (int i = 0; i < listOfFiles.length; i++) 
	     {
	 
	    	 if (listOfFiles[i].isFile()) 
	    	 {
	    		 files = listOfFiles[i].getName();
	    		 if (files.endsWith(".arff"))
	    		 {
	    			 arffFiles[fileCounter] = listOfFiles[i];
	    			 fileCounter++;
	          
	    		 }
	    	 }
	     }
		return arffFiles;
	}
	/**
	 * Reads a filename at specific path and return a inputReader object
	 * @param filename
	 * @param path
	 * @return
	 * @throws Exception 
	 */
	public BufferedReader readDataFile(String filename,String pathOfFile) throws Exception
	{
		String pathToSave = pathOfFile;
        BufferedReader inputReader = null;
        try
        {
            inputReader = new BufferedReader(new FileReader(pathToSave+filename));
           
        }
        catch (FileNotFoundException ex)
        {
            System.err.println("File not found: " + filename);
        }
        
        return inputReader;
    }
	public int search(String[] text,String pattern)
	{
		int position = -1;
		for(int i=0;i<text.length;i++)
		{
			if(text[i].equals(pattern))
				return i;
		}
		
		return position;
	}
	public int[] rankSortMax(int[] classValues)
	{
		int values[] = new int[classValues.length];
		for(int i=0;i<classValues.length;i++)
		{
			values[i] = classValues[i];
		}
		int[] ranks = new int[values.length];
		for(int i=0;i<values.length;i++)
		{
			int max=-1;
			int maxIndex=-1;
			for(int j=0;j<values.length;j++)
			{
				if(values[j]>max)
				{
					max= values[j];
					maxIndex=j;
				}
			}
			values[maxIndex]=Integer.MIN_VALUE;
			ranks[i]=maxIndex;
		}
		
		return ranks;
	}
	public void saveArffFromInstances(String filename,String pathOfFile, Instances dataset) throws Exception
	{
		 BufferedWriter writer = new BufferedWriter(new FileWriter(pathOfFile+filename));
		 writer.write(dataset.toString());
		 writer.flush();
		 writer.close();
		 writer = null;
		 System.gc();
	}
	public void convertBinary(String inputFolderpath,String outputFolderPath) throws Exception
	{
		File[] arffFiles = readAllFiles(inputFolderpath);
		
		for(int i=0;i<arffFiles.length;i++)
		{
			BufferedReader datafile = readDataFile(arffFiles[i].getName().toString(),inputFolderpath);
			System.out.println("Working on..... : "+arffFiles[i].getName().toString());
			Instances data = new Instances(datafile);
			data.setClassIndex(data.numAttributes() - 1);
			int numInstances = data.numInstances();
			Instances ConvertedData = new Instances(data);
			int classIndex = data.classIndex();
			Attribute clsAtr = data.classAttribute();
			
			if(data.numClasses()==2)
			{
				System.out.println("Class is 2 not converting.");
			}
			else if(clsAtr.isString())
			{
				System.out.println("Attribute is string should not be done");
			}
			else
			{
				System.out.println("Converting... .");
				Attribute atr = data.classAttribute();
				
				String attributes[] = new String[data.numClasses()];
				int classCount[] = new int[data.numClasses()];
				for(int j=0;j<data.numClasses();j++)
				{
					attributes[j]= atr.value(j);
				}
				for(int j=0;j<data.numInstances();j++)
				{
					Instance currentInstance = data.instance(j);
					String classValue = currentInstance.stringValue(classIndex);
					int position = search(attributes,classValue);
					if(position==-1)
					{
						System.out.println("not found some error in code" + arffFiles[i].getName().toString());
					}
					else
					{
						classCount[position]++;
					}
				}
				int ranks[] = rankSortMax(classCount);
				Set<Integer> set1 = new HashSet<Integer>();
				Set<Integer> set2 = new HashSet<Integer>();
				
				int set1Count=0;
				int set2Count=0;
				for(int j=0;j<data.numClasses();j++)
				{
					if(set1Count<set2Count)
					{
						set1.add(ranks[j]);
						set1Count+=classCount[ranks[j]];
					}
					else
					{
						set2.add(ranks[j]);
						set2Count+=classCount[ranks[j]];
					}
				}
				
				Iterator<Integer> iterator1 = set1.iterator();
				Iterator<Integer> iterator2 = set2.iterator();
				int firstClass = (int) iterator1.next();
				int secondClass = (int) iterator2.next();
				
				String class1Name = attributes[firstClass];
				String class2Name = attributes[secondClass];
				
				
				for(int j=0;j<data.numInstances();j++)
				{
					Instance currentInstance = data.instance(j);
					String classValue = currentInstance.stringValue(classIndex);
					int position = search(attributes,classValue);
					if(set1.contains(position))
					{
						currentInstance.setValue(data.classIndex(), class1Name);
					}
					else
					{
						currentInstance.setValue(data.classIndex(), class2Name);
					}
				}
				
				Instances modifiedData = new Instances(data);
				modifiedData.delete();
				
				//Removing class
				Remove removeClass = new Remove();
				removeClass.setAttributeIndicesArray(new int [] {modifiedData.classIndex()});
				removeClass.setInvertSelection(false);
				removeClass.setInputFormat(modifiedData);
				modifiedData = Filter.useFilter(modifiedData, removeClass);
				
				//Adding new attribute
		        Add filter;
		        filter = new Add();
		        filter.setAttributeIndex("last");
		        filter.setNominalLabels(class1Name+","+class2Name);
		        filter.setAttributeName("class");
		        filter.setInputFormat(modifiedData);
		        modifiedData = Filter.useFilter(modifiedData, filter);
				
		        //Setting class attribute
		        modifiedData.setClassIndex(data.classIndex());
		        
		        //Inserting all instances to modified
		        
		        Attribute attr1 = data.attribute(data.classIndex());
	        	Attribute attr2 = modifiedData.attribute(data.classIndex());
	        	
	        	String mData[] = new String[data.numClasses()];
	        	String mModified[] = new String[modifiedData.numClasses()];
	        	for(int j=0;j<data.numClasses();j++)
	        	{
	        		mData[j] = attr1.value(j).trim();
	        	}
	        	for(int j=0;j<modifiedData.numClasses();j++)
	        	{
	        		mModified[j] = attr2.value(j).trim();
	        	}
		        for(int j=0;j<data.numInstances();j++)
		        {
		        	Instance curr = data.instance(j);
		        	String clsValue = curr.stringValue(data.classIndex());
		        	int pos = search(mModified, clsValue);
		        	String modValue = mData[pos];
		        	curr.setValue(data.classIndex(), modValue);
		        	modifiedData.add(curr);
		        }
				saveArffFromInstances(arffFiles[i].getName().toString(), outputFolderPath, modifiedData);
				System.out.println("Converted... .");
			}
			
			
			
		}
	}
	
}
