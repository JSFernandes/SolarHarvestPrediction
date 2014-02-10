
public class CloudUpdateRunner implements Runnable {

	DataManager data_manager;
	
	public CloudUpdateRunner(DataManager manager) {
		data_manager = manager;
	}
	
	@Override
	public void run() {
		data_manager.updateCloudCoverages();
	}

}
