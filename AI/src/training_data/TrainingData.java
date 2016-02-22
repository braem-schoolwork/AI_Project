package training_data;

import java.util.ArrayList;

public class TrainingData
{
	ArrayList<TrainingTuple> data;
	
	public TrainingData(ArrayList<TrainingTuple> data) {
		this.data = data;
	}
	
	public ArrayList<TrainingTuple> getData() {
		return data;
	}
	public void setData(ArrayList<TrainingTuple> data) {
		this.data = data;
	}
	
}
