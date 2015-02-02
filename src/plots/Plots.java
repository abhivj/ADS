package plots;
import com.panayotis.gnuplot.JavaPlot;
public class Plots {

	public void plotting()
	{
		JavaPlot p1 = new JavaPlot();
		double arr[][] = {{1,2,3,4},{5,4,2,1}};
		p1.addPlot(arr);
		p1.plot();
	}
	
	
}
