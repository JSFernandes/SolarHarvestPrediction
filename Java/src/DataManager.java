import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.joda.time.DateTime;
import org.joda.time.Period;


public class DataManager {
	public CloudRetreiver retreiver;
	public CloudCoverageCalculator calc;
	public LineChartClouds cloud_chart;
	public LineChartPredictions prediction_chart;
	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	public SerialCommunicator comm;
	public boolean can_do_charts = false;
	
	public DataManager(CloudRetreiver r, CloudCoverageCalculator c, LineChartClouds clouds,
			LineChartPredictions predictions) {
		retreiver = r;
		calc = c;
		cloud_chart = clouds;
		prediction_chart = predictions;
	}
	
	public void init(){
		if(calc.last_cloud_update == null) {
			intitCloudCoverages();
		}
		else {
			updateCloudCoverages();
		}
		
		if(calc.last_harvest_update == null) {
			calc.last_harvest_update = DateTime.now();
		}
		else {
			updateHarvestData();
		}
		/*...*/
		populateCharts();
		scheduler.scheduleAtFixedRate(new CloudUpdateRunner(this), 3, 1, TimeUnit.HOURS);
	}

	private void updateHarvestData() {
		
		DateTime d = new DateTime(DateTime.now());
		
		Period p = new Period(calc.last_harvest_update, d);
		long hours_dif = p.getHours();
		calc.last_harvest_update = DateTime.now();
		System.out.println("hours dif: " + hours_dif);
		comm.getData(hours_dif);
	}

	public void populateCharts() {
		while(!can_do_charts ){}
		
		populatePredictionChart();
		populateCloudChart();
	}

	private void populatePredictionChart() {
		for(int i = 0; i < calc.predictions_combined.size(); ++i) {
			prediction_chart.prediction_combined.add(i, calc.predictions_combined.get(i));
		}
		for(int i = 0; i < calc.predictions_separate.size(); ++i) {
			prediction_chart.prediction_separate.add(i, calc.predictions_separate.get(i));
		}
		for(int i = 0; i < calc.harvested_data.size(); ++i) {
			prediction_chart.actual_harvesting.add(i, calc.harvested_data.get(i));
		}
	}

	private void populateCloudChart() {
		for(int i = 0; i < calc.cloud_coverages.size(); ++i)
			cloud_chart.cloud_forecast.add(i, calc.cloud_coverages.get(i)*100);
	}

	public void updateCloudCoverages() {
		DateTime d = new DateTime(DateTime.now());
		
		Period p = new Period(calc.last_cloud_update, d);
		
		long hours_dif = p.getHours();
		
		retreiver.updateForecast();
		
		//System.out.println(hours_dif);
		//System.out.println(calc.cloud_coverages.size());
		//System.out.println(calc.last_updated_cloud_slot);
		try {
			ArrayList<Double> clouds = retreiver.getHoursFromTo
					((int) (48-hours_dif), 50);
			
			for(int i = 0; i < clouds.size(); ++i) {
				calc.cloud_coverages.add(clouds.get(i));
			}
			
			calc.last_updated_cloud_slot += clouds.size();
			calc.last_cloud_update = d;
		}
		catch(Exception e) {
			
		}
	}

	private void intitCloudCoverages() {
		retreiver.updateForecast();
		ArrayList<Double> clouds = retreiver.getHoursFromTo(0, 50);
		
		for(int i = 0; i < clouds.size(); ++i) {
			calc.cloud_coverages.add(calc.calcOmega(clouds.get(i)));
		}
		
		calc.last_updated_cloud_slot = 0;
		calc.last_cloud_update = new DateTime(DateTime.now());
	}

	public void new_packet(long get_result) {
		calc.new_packet(get_result);
	}

	public void updateTime() {
		calc.last_harvest_update = DateTime.now();
	}
}
