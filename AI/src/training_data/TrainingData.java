package training_data;

import java.util.List;

public class TrainingData
{
	List<TrainingTuple> data;
	
	public TrainingData(List<TrainingTuple> data) {
		this.data = data;
	}
	
	public List<TrainingTuple> getData() {
		return data;
	}
	public void setData(List<TrainingTuple> data) {
		this.data = data;
	}
	
	@Override
	public String toString() {
		String rtnStr = "";
		for(TrainingTuple tt: data) {
			rtnStr += tt.toString()+"\n";
		}
		return rtnStr;
	}
}
