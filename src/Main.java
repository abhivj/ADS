import DataCleaning.*;
import MultivariateNormalDistributionMixture.*;
public class Main {

	public static void main(String[] args) throws Exception {
		
		//TrainingDataCleaner.CleanAndModify("d:/ss/", "d:/ss/ss/", true, true, true,true,true,8,100);
		// TODO Auto-generated method stub
		ExpectationMaximizationAlgorithm.getMeanOfGaussionsFromEM("d:/st/",4);
		
		//ModifyBinary.modifyTo01("d:/st/", "d:/st/st/");
	}

}
