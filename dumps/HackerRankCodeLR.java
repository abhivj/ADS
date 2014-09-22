
import java.util.*;

import weka.classifiers.functions.LinearRegression;
import weka.core.*;

public class HackerRankCodeLR {
	
	public static void main(String[] args) throws Exception 
	 {
		Scanner in = new Scanner(System.in);
		int F = in.nextInt();
		int N = in.nextInt();
		FastVector fvWekaAttributes = new FastVector(F+1);
		Instances isTrainingSet = new Instances("Rel", fvWekaAttributes, F+1);           
		isTrainingSet.setClassIndex(isTrainingSet.numAttributes()-1);
		
		
		for(int i=0;i<=F;i++)
		 {
			Attribute Attribute1 = new Attribute("atr"+i);
			 fvWekaAttributes.addElement(Attribute1);    
		 }
		
		
		
		
		for(int j=0;j<N;j++)
		{
		 Instance iExample = new Instance(F+1);
		 for(int i=0;i<=F;i++)
		 {
			 double currentFeature = in.nextDouble();
			// iExample.setValue((Attribute)fvWekaAttributes.elementAt(i),currentFeature);
			 iExample.setValue(i, currentFeature);
			
		 }
		// System.out.println(iExample.toString());
		 isTrainingSet.add(iExample);
		 
		 
		}
		 isTrainingSet.setClassIndex(isTrainingSet.numAttributes()-1);
	//	System.out.println(isTrainingSet.toString());
		LinearRegression model = new LinearRegression();
		model.buildClassifier(isTrainingSet);
	//	System.out.println(model);
		
		int testCases = in.nextInt();
		double[] coeff =  model.coefficients();
	//	for(int i=0;i<coeff.length;i++)
		//System.out.println(coeff[i]+" ");
		for(int i=0;i<testCases;i++)
		{
			double ans=0;
			for(int j=0;j<F;j++)
			{
				double currAtr = in.nextDouble();
				ans = ans + currAtr*coeff[j];
			}
			ans  =ans+ coeff[coeff.length-1];
			System.out.println(ans);
		}
		
		//System.out.println(model);
	 }
	 

}
