package metaData;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import weka.classifiers.trees.J48;
import weka.classifiers.trees.Id3;
import weka.classifiers.trees.SimpleCart;
import weka.clusterers.EM;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;
import Stats.Statistics;
public class ModelBased {

String dataset;
J48 j48;
SimpleCart id3;
BinaryTree root = null;

public ModelBased(String dataset) throws Exception
{
	this.dataset = dataset;
	this.j48 = trainmodel();
	this.id3 = trainId3();
	this.root = generateTree();
}
private BufferedReader readDataFile()
{
    BufferedReader inputReader = null;
    try
    {
        inputReader = new BufferedReader(new FileReader(dataset));
    }
    catch (FileNotFoundException ex)
    {
        System.err.println("File not found: " + dataset);
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
public J48 trainmodel() throws Exception
{
	int numberOfAttr=0;
	BufferedReader datafile = readDataFile();
	Instances data = new Instances(datafile);
	data.setClassIndex(data.numAttributes() - 1);
	
	J48 j48 = new J48();
	j48.setBinarySplits(true);
	j48.buildClassifier(data);
	return j48;
}
private int minElement(int[] vector)
{
	int min = Integer.MAX_VALUE;
	for(int i=0;i<vector.length;i++)
	{
		if(min>vector[i])
			min=vector[i];
	}
	return min;
}
private int maxElement(int[] vector)
{
	int max = 0;
	for(int i=0;i<vector.length;i++)
	{
		if(max<vector[i])
			max=vector[i];
	}
	return max;
}
private double meanElement(int[] vector)
{
	double[] dataInDouble = new double[vector.length];
	for(int i=0;i<dataInDouble.length;i++)
	{
		dataInDouble[i]=(double)vector[i];
	}
	Statistics st = new Statistics(dataInDouble);
	return st.getMean();
}
private double devElement(int[] vector)
{
	double[] dataInDouble = new double[vector.length];
	for(int i=0;i<dataInDouble.length;i++)
	{
		dataInDouble[i]=(double)vector[i];
	}
	Statistics st = new Statistics(dataInDouble);
	return st.getStdDev();
}
public SimpleCart trainId3() throws Exception
{
	int numberOfAttr=0;
	BufferedReader datafile = readDataFile();
	Instances data = new Instances(datafile);
	data.setClassIndex(data.numAttributes() - 1);
	
	SimpleCart id3 = new SimpleCart();
	id3.buildClassifier(data);
	//System.out.println(id3.toString());
	return id3;
}
public String printTree() throws Exception
{
	return j48.graph();
}
public String prefixTree() throws Exception
{
	return j48.prefix();
}
public double numberOfLeavesID3()
{
	
	return j48.measureNumRules();
}
public BinaryTree generateTree() throws Exception
{
	int numberOfNodes = (int)j48.measureNumLeaves();
	BinaryTree root= new BinaryTree();
	BinaryTree[] BT  = new BinaryTree[2*numberOfNodes-1];
	
	for(int i=0;i<BT.length;i++)
	{
		BT[i] = new BinaryTree();
	}
	//System.out.println(numberOfNodes);
	for(int i=0;i<BT.length;i++)
	{
		//System.out.println(i);
		BT[i].setLabel("N"+String.valueOf(i));
	}
	String graph = j48.graph();
	String currentLine;
	BufferedReader reader = new BufferedReader(new StringReader(graph));
	//reader.readLine();
	while((currentLine=reader.readLine())!=null)
	{
		//System.out.println(currentLine);
		if(currentLine.charAt(0)=='N')
		{
			if(currentLine.contains("->"))
			{
				String allData[] = currentLine.split(" ");
				String nodes[] = allData[0].split("->");
				
				String parentNode = nodes[0].substring(1);
				String childNode = nodes[1].substring(1);
				
				//System.out.println(parentNode+":"+childNode);
				int parent = Integer.parseInt(parentNode);
				int child = Integer.parseInt(childNode);
				
				root.linkNode(BT[parent], BT[child]);
			}
		}
	}
	//root.printInorder(BT[0]);
	return BT[0];
}
public int width() throws Exception
{
	 int maxWidth = 0;   
	 int width;
	 int h = treeHeight();
	 int i;
	   
	  /* Get width of each level and compare 
	     the width with maximum width so far */
	  for(i=1; i<=h; i++)
	  {
	    width = calculateWidth(root, i);
	    if(width > maxWidth)
	      maxWidth = width;
	  }     
	   
	  return maxWidth;
}
private int calculateWidth(BinaryTree root,int level)
{
	 if(root == null)
		    return 0;
		  if(level == 1)
		    return 1;
		  else if (level > 1)
		    return calculateWidth(root.left, level-1) + calculateWidth(root.right, level-1);
		return 0;	  
}
private int calculateHeight(BinaryTree root)
{
	if(root==null)
		return 0;
	else
	{
		int left = calculateHeight(root.left);
		int right = calculateHeight(root.right);
		return (left > right)? (left+1): (right+1);
	}
	
}

private int getWidthMaxLeft(BinaryTree root,int level) throws Exception
{
	if(root==null)
		return level;
	else
	{
		int m=level,n=level;
		if(root.left!=null)
		{
			m = getWidthMaxLeft(root.left, level-1);
		}
		if(root.right!=null)
		{
			n=getWidthMaxLeft(root.right, level+1);
		}
		int min = Math.min(m, n);
		//System.out.println(min);
		return min;
	}
}
private int getWidthMaxRight(BinaryTree root,int level) throws Exception
{
	if(root==null)
		return level;
	else
	{
		int m=level,n=level;
		if(root.left!=null)
		{
			m = getWidthMaxRight(root.left, level-1);
		}
		if(root.right!=null)
		{
			n=getWidthMaxRight(root.right, level+1);
		}
		int min = Math.max(m, n);
	//	System.out.println(min);
		return min;
	}
}

private int[] attributeVector() throws Exception
{
	int numberOfAttr=0;
	BufferedReader datafile = readDataFile();
	Instances data = new Instances(datafile);
	data.setClassIndex(data.numAttributes() - 1);	
	String[] attributes = new String[data.numAttributes()-1];
	for(int i=0;i<data.numAttributes()-1;i++)
	{
		attributes[i] = data.attribute(i).name();
	}
	int attributeVector[] = new int[data.numAttributes()-1];
	String graph = j48.prefix();
	String currentLine;
	BufferedReader reader = new BufferedReader(new StringReader(graph));
	//reader.readLine();
	while((currentLine=reader.readLine())!=null)
	{
		if(currentLine.charAt(currentLine.length()-1)==',')
		{
			int startIndex = currentLine.lastIndexOf('[');
			int lastIndex = currentLine.lastIndexOf(':');
			String attributeName = currentLine.substring(startIndex+1, lastIndex);
			//System.out.println(attributeName);
			int position = search(attributes, attributeName);
			if(position!=-1)
				attributeVector[position]++;
		}
		
	}
	return attributeVector;
}
private int[] levelVector() throws Exception
{
	int[] levelVec = new int[treeHeight()];
	levelCalculator(root, 0, levelVec);
	return levelVec;
}
private void levelCalculator(BinaryTree root,int level,int[] levelVec)
{
	if(root==null)
		return;
	levelVec[level]++;
	if(root.left!=null)
	{
		levelCalculator(root.left, level+1, levelVec);
	}
	if(root.right!=null)
	{
		levelCalculator(root.right, level+1, levelVec);
	}
	return;
}

private void branchCalculator(BinaryTree root,int level,List<Integer> branch) throws Exception
{
	if(root==null)return;
	if(root.left==null && root.right==null)
	{
		branch.add(level);
	}
	if(root.left!=null)
	{
		branchCalculator(root.left, level+1, branch);
	}
	if(root.right!=null)
	{
		branchCalculator(root.right, level+1, branch);
	}
	return;
}
private int[] branchVector() throws Exception
{
	List<Integer> branch = new ArrayList<Integer>();
	branchCalculator(root, 0, branch);
	int branchVec[] = new int[branch.size()];
	for(int i=0;i<branch.size();i++)
	{
		branchVec[i]=branch.get(i);
	}
	return branchVec;
}

public int treeHeight() throws Exception
{
	return calculateHeight(root);
}
public int treeWidth() throws Exception
{
	return (-getWidthMaxLeft(root, 0) + getWidthMaxRight(root, 0));
}
public int noLeaves()
{
	return (int)j48.measureNumLeaves();
}
public int noNodes()
{
	return (int)j48.measureTreeSize();
}
public int maxLevel() throws Exception
{
	return maxElement(levelVector());
}
public int minLevel() throws Exception
{
	return minElement(levelVector());
}
public double meanLevel() throws Exception
{
	return meanElement(levelVector());
}
public double devLevel() throws Exception
{
	return devElement(levelVector());
}
public int longBranch() throws Exception
{
	return maxElement(branchVector());
}
public int shortBranch() throws Exception
{
	return minElement(branchVector());
}
public double meanBranch() throws Exception
{
	return meanElement(branchVector());
}
public double devBranch() throws Exception
{
	return devElement(branchVector());
}
public int maxAtt() throws Exception
{
	return maxElement(attributeVector());
}
public int minAtt() throws Exception
{
	return minElement(attributeVector());
}
public double meanAtt() throws Exception
{
	return meanElement(attributeVector());
}
public double devAtt() throws Exception
{
	return devElement(attributeVector());
}


}
