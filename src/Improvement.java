import java.io.File;

import plots.GenerateRanks;
import plots.TopMatching;
import plots.TopRankingGenerator;
import org.apache.commons.math3.stat.correlation.SpearmansCorrelation;
import metaData.*;

public class Improvement {
	public static File[] readAllFiles(String inputFolder)
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
	    		 if (files.endsWith(".csv"))
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
	    		 if (files.endsWith(".csv"))
	    		 {
	    			 arffFiles[fileCounter] = listOfFiles[i];
	    			 fileCounter++;
	          
	    		 }
	    	 }
	     }
		return arffFiles;
	}
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		//Comparison with best overall ranking
	/*	TopRankingGenerator trg = new TopRankingGenerator();
		trg.bestRanking("d:/Experiment/large/Improvement/Accuracy-Base-44-70-8.csv", "d:/Experiment/large/Improvement/bestRank-44-70.txt", 44, 70);
	
		File[] csvFiles = readAllFiles("d:/Experiment/large/Experiments/result/regression/dataset44/algo13/result");
		
		for(int i=0;i<csvFiles.length;i++)
		{
		GenerateRanks GR = new GenerateRanks();
		
		GR.generateRankFile("d:/Experiment/large/Improvement/Accuracy-Base-44-13-8.csv",						//Base File which Contains true result
				"D:/Experiment/ExperimentFeb/march24/temp.txt", 
				"d:/Experiment/large/Improvement/actual/actual"+csvFiles[i].getName().toString()+".txt", 
				"D:/Experiment/ExperimentFeb/march24/temp.txt",  13);
		
		GR.generateRankFile("d:/Experiment/large/Experiments/result/regression/dataset44/algo13/result/"+csvFiles[i].getName().toString(),		//Prediction From Experiment
				"D:/Experiment/ExperimentFeb/march24/temp.txt", 
				"d:/Experiment/large/Improvement/predicted/predicted"+csvFiles[i].getName().toString()+".txt", 
				"D:/Experiment/ExperimentFeb/march24/temp.txt",  13);
		
		
		TopMatching TM = new TopMatching();
		/*TM.compareTwoFile("d:/Experiment/large/Improvement/actual/actual"+csvFiles[i].getName().toString()+".txt", 
				"d:/Experiment/large/Improvement/predicted/predicted"+csvFiles[i].getName().toString()+".txt", 70,
						  "d:/Experiment/large/Improvement/topk/"+csvFiles[i].getName().toString());	
		*/
		/*
		TM.compareTwoFileCor("d:/Experiment/large/Improvement/actual/actual"+csvFiles[i].getName().toString()+".txt", 
				"d:/Experiment/large/Improvement/predicted/predicted"+csvFiles[i].getName().toString()+".txt", 44,
						  "d:/Experiment/large/Improvement/topkcor/"+csvFiles[i].getName().toString());	
		}
		TopMatching TM = new TopMatching();
		TM.allResult("d:/Experiment/large/Improvement/topkcor/", "d:/Experiment/large/Improvement/Correlation-13-keddels.csv",44);
		*/
		/*
		TopMatching TM = new TopMatching();
		TM.compareTwoFile("d:/Experiment/large/Improvement/actual.txt", 
						  "d:/Experiment/large/Improvement/bestRank-44-70.txt", 70,
						  "d:/Experiment/large/Improvement/topkBest/tt.csv");	
		TM.allResult("d:/Experiment/large/Improvement/topkBest/", "d:/Experiment/large/Improvement/Comparision-with-best-70.csv",70);
		TM.allResult("d:/Experiment/large/Improvement/topk/", "d:/Experiment/large/Improvement/Comparision-with-actual-70.csv",70);
		*/
		/*
		TopMatching TM = new TopMatching();
		TM.allResult("d:/Experiment/large/Improvement/topkBest/", "d:/Experiment/large/Improvement/Comparision-with-actual-13.csv",13);
*/
		Statistical st = new Statistical("d:/Experiment/large/Improvement/dataset/ada_prior.arff");
	//	System.out.println(st.SDRatio());
	//	System.out.println(st.corr_abs());
	//	System.out.println(st.kurtosis());
	//	System.out.println(st.skewness());
		
		InformationTheory IT = new InformationTheory("d:/Experiment/large/Improvement/dataset/ada_prior.arff");
//		System.out.println(IT.entropyAttr());
	//	System.out.println(IT.entropyClass());
		//System.out.println(IT.jointEntropy());
		//System.out.println(IT.mutualInformation());
		//System.out.println(IT.equivalaentAttr());
		
		ModelBased mb = new ModelBased("d:/Experiment/large/Improvement/dataset/ada_prior.arff");
		System.out.println("Numer Of Leaves "+mb.noLeaves());
		System.out.println("Numer Of Nodes "+mb.noNodes());
		System.out.println("Height of tree "+mb.treeHeight());
		System.out.println("WidthMax of tree "+mb.treeWidth());
		System.out.println("maxLevel "+mb.maxLevel());
		System.out.println("minLevel "+mb.minLevel());
		System.out.println("meanLevel "+mb.meanLevel());
		System.out.println("dev Level "+mb.devLevel());
		System.out.println("Long Branch "+mb.longBranch());
		System.out.println("Short Branch "+mb.shortBranch());
		System.out.println("Mean Branch "+mb.meanBranch());
		System.out.println("dev Branch "+mb.devBranch());
		System.out.println("maxAtt "+mb.maxAtt());
		System.out.println("minAtt "+mb.minAtt());
		System.out.println("meanAtt "+mb.meanAtt());
		System.out.println("dev Attr "+mb.devAtt());
		//System.out.println(mb.numberOfLeavesID3());
		//System.out.println(mb.printTree());
	//	System.out.println(mb.prefixTree());
		//mb.attributeVector();
		//mb.generateTree();
		//mb.levelVector();
		//mb.branchVector();
		
		
		LandMarking LM = new LandMarking("d:/Experiment/large/Improvement/dataset/ada_prior.arff");
		System.out.println(LM.decisionNodeLearning());
		System.out.println(LM.worstNodeLearning());
		System.out.println(LM.randomNodeLearning());
		System.out.println(LM.averageNodeLearning());
		System.out.println(LM.NaiveBayesLearning());
		System.out.println(LM.eliteNNLearning());
		System.out.println(LM.NearestNeighbourLearning());
		//double d = Math.pow(1.5284942461104516E14,0.16*0.5);
		//System.out.println(d);
	}

}
