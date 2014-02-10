import java.util.ArrayList;

import dme.forecastiolib.FIOHourly;
import dme.forecastiolib.ForecastIO;


public class CloudRetreiver {
	ForecastIO fio;
	FIOHourly hourly;
	
	public CloudRetreiver() {
		fio = new ForecastIO("2f637d75cd3783a2dfc81f4f49ff544f");
		fio.setExcludeURL("minutely");
	}
	
	public void updateForecast() {
		try {
			fio.getForecast("41.893405","12.564325");
			hourly = new FIOHourly(fio);
		}
		catch(Exception e) {
			
		}
	}
	
	public double getCloudCoverInDistance(int n_hours) {
		return hourly.getHour(n_hours+1).cloudCover();
	}
	
	public ArrayList<Double> getHoursFromTo(int first_hour, int last_hour) {
		ArrayList<Double> dubs = new ArrayList<Double>();
		for(int i = first_hour+1; i < last_hour+1 && i < hourly.hours(); ++i) {
			dubs.add(hourly.getHour(i).cloudCover());
		}
		return dubs;
	}
}
