package Stats;
import org.rosuda.REngine.*;
import org.rosuda.REngine.Rserve.*;
import org.rosuda.REngine.REXP;
/*
 * To start program in either use this command
 * Process p = Runtime.getRuntime().exec("Rserve.exe");
 * 
 * but this will create new process each time.
 * So open RStudio
 * 
 * library(Rserve)
 * Reserve()
 */
public class StatisticalSignificanceTest {

	public static double AnovaPrValueForHistogramBin(String sourceCSVFile) throws Exception
	{
		double pr=0;
		RConnection c = new RConnection();// make a new local connection on default port (6311)
        String readData = "dataCSV<-read.csv(\'"+sourceCSVFile+"\',header=F)";
        String data= c.eval(readData).asString();
		c.eval("attach(readData)");
		c.eval("aoc<-aov(V2~V1)");
		REXP PrValue = c.eval("summary(aoc)[[1]][[\"Pr(>F)\"]][[1]]");
//		double d[] = c.eval("rnorm(10)").asDoubles();
  //      org.rosuda.REngine.REXP x0 = c.eval("R.version.string");
    //    System.out.println(x0.asString());
		pr = PrValue.asDouble();
		return pr;
	}

	
	
}
