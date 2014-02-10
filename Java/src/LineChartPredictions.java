import java.awt.Color;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;


public class LineChartPredictions extends ApplicationFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8838788408219850741L;
	public XYSeries prediction_combined = new XYSeries("Combined Prediction");
	public XYSeries prediction_separate = new XYSeries("Separate Prediction");
	public XYSeries actual_harvesting = new XYSeries("Harvested");
	public XYSeriesCollection dataset;

	public LineChartPredictions(String title) {
		super(title);
		dataset = new XYSeriesCollection();
		dataset.addSeries(prediction_combined);
		dataset.addSeries(prediction_separate);
		dataset.addSeries(actual_harvesting);
		
		JFreeChart chart = createChart(dataset);
		ChartPanel panel = new ChartPanel(chart);
		
		panel.setPreferredSize(new java.awt.Dimension(500, 270));
        setContentPane(panel);
	}

	private JFreeChart createChart(XYSeriesCollection dataset2) {
		JFreeChart chart = ChartFactory.createXYLineChart(
	            "Line Chart Demo 6",      // chart title
	            "Hours",                      // x axis label
	            "Energy",                      // y axis label
	            dataset,                  // data
	            PlotOrientation.VERTICAL,
	            true,                     // include legend
	            true,                     // tooltips
	            false                     // urls
	        );

	        // NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...
	        chart.setBackgroundPaint(Color.white);
	        final XYPlot plot = chart.getXYPlot();
	        plot.setBackgroundPaint(Color.lightGray);
	        plot.setDomainGridlinePaint(Color.white);
	        plot.setRangeGridlinePaint(Color.white);
	        
	        final XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
	        renderer.setSeriesLinesVisible(0, false);
	        renderer.setSeriesShapesVisible(1, false);
	        plot.setRenderer(renderer);

	        // change the auto tick unit selection to integer units only...
	        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
	        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
	        // OPTIONAL CUSTOMISATION COMPLETED.
	                
	        return chart;
	}

}
