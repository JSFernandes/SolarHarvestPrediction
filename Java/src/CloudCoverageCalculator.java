import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;

import org.joda.time.DateTime;


public class CloudCoverageCalculator implements Serializable {
	
	static CloudCoverageCalculator instance = null;
	
	
	public static CloudCoverageCalculator getInstance() throws IOException, ClassNotFoundException {
		if(instance != null)
			return instance;
		else {
			File f = new File("CloudData.bin");
			if(f.exists()) {
				FileInputStream input_stream = new FileInputStream(f);
				ObjectInputStream obj_input = new ObjectInputStream(input_stream);
				instance = (CloudCoverageCalculator) obj_input.readObject();
				obj_input.close();
				input_stream.close();
				return instance;
			}
			else {
				instance = new CloudCoverageCalculator();
			}
			
			return instance;
		}
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7292040044653307477L;
	public ArrayList<Double> cloud_coverages;
	public ArrayList<Float> harvested_data;
	public ArrayList<Float> smoothed_cloud_coverages;
	public ArrayList<Float> smoothed_harvested_data_combined;
	public ArrayList<Float> smoothed_harvested_data_separate;
	public ArrayList<Float> predictions_combined;
	public ArrayList<Float> predictions_separate;
	
	public final float alpha = 0.5f;
	public OmegaCalculator calc = OmegaCalculator.LAEVATSU;
	public DateTime last_cloud_update;
	public DateTime last_harvest_update;
	public int current_slot = 0;
	public int last_updated_cloud_slot = 0;
	
	
	public Double calcOmega(double cloud_coverage) {
		if(calc.equals(OmegaCalculator.KIMBALL))
			return calcOmegaKimball(cloud_coverage);
		else
			return calcOmegaLaevatsu(cloud_coverage);
	}
	
	public CloudCoverageCalculator() {
		cloud_coverages = new ArrayList<Double>();
		harvested_data = new ArrayList<Float>();
		smoothed_cloud_coverages = new ArrayList<Float>();
		smoothed_harvested_data_combined = new ArrayList<Float>();
		smoothed_harvested_data_separate = new ArrayList<Float>();
		predictions_combined = new ArrayList<Float>();
		predictions_separate = new ArrayList<Float>();
		
		for(int i = 0; i < 24; ++i) {
			predictions_combined.add(0.0f);
			predictions_separate.add(0.0f);
		}
	}

	public float calcSmoothedHarvestedCombined(int slot) {
		float past_smoothed_harvest;
		if(slot - 24 >= 0) {
			past_smoothed_harvest = smoothed_harvested_data_combined.get(slot-24);
		}
		else {
			past_smoothed_harvest = 0;
		}
		float mew = (float) (harvested_data.get(slot)/cloud_coverages.get(slot));
		float result = alpha*past_smoothed_harvest + (1-alpha)*mew;
		return result;	
	}
	
	public void calcParamsForSeparated(int slot) {
		float prev_smoothed_harvest;
		float prev_smoothed_cloud;
		if(slot-24 >= 0) {
			prev_smoothed_cloud = smoothed_cloud_coverages.get(slot-24);
			prev_smoothed_harvest = smoothed_harvested_data_separate.get(slot-24);
		}
		else {
			prev_smoothed_cloud = 100;
			prev_smoothed_harvest = 0;
		}
		float smoothed_harvest = prev_smoothed_harvest*alpha + (1-alpha)*harvested_data.get(slot);
		smoothed_harvested_data_separate.add(smoothed_harvest);
		
		float smoothed_cloud = (float) (prev_smoothed_cloud*alpha + (1-alpha)*cloud_coverages.get(slot));
		smoothed_cloud_coverages.add(smoothed_cloud);
	}
	
	public void calcPredictionSeparated(int slot) {
		calcParamsForSeparated(slot);
		float result = smoothed_harvested_data_separate.get(slot)/smoothed_cloud_coverages.get(slot);
		result *= cloud_coverages.get(slot+24);
		
		predictions_separate.add(result);
	}
	
	public void calcPredictionCombined(int slot) {
		float smoothed_combined = calcSmoothedHarvestedCombined(slot);
		smoothed_harvested_data_combined.add(smoothed_combined);
		float prediction = (float) (smoothed_combined*cloud_coverages.get(slot+24));
		
		predictions_combined.add(prediction);
	}

	private Double calcOmegaLaevatsu(double cloud_coverage) {
		return (1-0.6*Math.pow(cloud_coverage, 3));
	}


	private Double calcOmegaKimball(double cloud_coverage) {
		return (1-0.71*cloud_coverage);
	}

	public void new_packet(long get_result) {
		harvested_data.add((float) ((float)get_result/100));
		smoothed_harvested_data_combined.add(calcSmoothedHarvestedCombined(current_slot));
		calcParamsForSeparated(current_slot);
		calcPredictionSeparated(current_slot);
		calcPredictionCombined(current_slot);
		++current_slot;
	}
}
