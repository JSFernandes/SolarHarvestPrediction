import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;


public class CloudCoverageMain {
	
	public static DataManager manager;
	
	public static void main(String[] args) {
		
		CloudRetreiver c = new CloudRetreiver();
		try {
			CloudCoverageCalculator calc = CloudCoverageCalculator.getInstance();
			Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {

		        public void run() {
		        	try {
			        	FileOutputStream fileOut = new FileOutputStream("CloudData.bin");
			        	ObjectOutputStream out = new ObjectOutputStream(fileOut);
			        	out.writeObject(CloudCoverageCalculator.getInstance());
			        	out.close();
			        	fileOut.close();
		        	}
		        	catch (Exception e) {
		        		e.printStackTrace();
		        	}
		        }
		    }));
			
			LineChartPredictions predictions = new LineChartPredictions("predictions");
        	LineChartClouds clouds = new LineChartClouds("cloud coverage forecast");
        	
        	manager = new DataManager(c, calc, clouds, predictions);
        	SerialCommunicator communicator = new SerialCommunicator();
        	communicator.manager = manager;
        	manager.comm = communicator;
        	manager.init();
        	manager.updateCloudCoverages();
        	manager.populateCharts();
        	predictions.pack();
        	clouds.pack();
        	predictions.setVisible(true);
        	clouds.setVisible(true);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
