package RMethods;

import be.ac.ulg.montefiore.run.jahmm.ObservationReal;
import be.ac.ulg.montefiore.run.jahmm.ObservationVector;
import be.ac.ulg.montefiore.run.jahmm.OpdfMultiGaussian;
import matlabcontrol.*;
/**
 * This class uses jahmm model to estimate parameters of Multivariate Gaussion Distribution
 * @author abhivj
 *
 */
public class Jhamm {

	public static void main(String[] args) throws Exception {
		/*
		double [] mean = {2., 4.};
		double [][] covariance = { {3., 2}, {2., 4.} };
		OpdfMultiGaussian omg = new OpdfMultiGaussian (mean ,covariance);
		ObservationVector[] obs = new ObservationVector[100];
		for (int i = 0; i < obs.length; i++)
			obs[i] = omg.generate();
		OpdfMultiGaussian omg1 = new OpdfMultiGaussian(2);
		omg1.fit(obs);
		ObservationReal obr = new ObservationReal(2.3);
	//	omg1.
		*/

		
		
		
	    MatlabProxyFactoryOptions opt = new MatlabProxyFactoryOptions.Builder().setUsePreviouslyControlledSession(true).setMatlabLocation("C:/Program Files/MATLAB/R2014a/bin").build();
	    MatlabProxyFactory factory = new MatlabProxyFactory(opt);
		//MatlabProxyFactory factory = new MatlabProxyFactory();
	    MatlabProxy proxy = factory.getProxy();
	    
	    //Set a variable, add to it, retrieve it, and print the result
	    proxy.setVariable("a", 5);
	    proxy.eval("a = a + 6");
	    double result = ((double[]) proxy.getVariable("a"))[0];
	    System.out.println("Result: " + result);

	    //Disconnect the proxy from MATLAB
	    proxy.disconnect();
		
		// TODO Auto-generated method stub

	}

}
