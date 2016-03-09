package training_data;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.jblas.DoubleMatrix;

/**
 * A general training data generator
 * 
 * @author braemen
 * @version 1.0
 */
public class TrainingDataGenerator {
	
	private final static Charset ENCODING = StandardCharsets.UTF_8;
	private static final String FILE_NAME = System.getProperty("user.dir")+"\\training_data.csv";
	
	/**
	 * Generates Training Data from a file
	 * 
	 * @return training data from training_data.csv
	 */
	public static TrainingData genFromFile() {
		List<String> fileContents;
		try {
			fileContents = readFile(FILE_NAME);
		} catch (IOException e) {
			return null;
		}
		ArrayList<TrainingTuple> tts = new ArrayList<TrainingTuple>();
		for(String tupleStr : fileContents) {
			String[] tupleStrArr = tupleStr.split("[|]+");
			String inputsStr = tupleStrArr[0];
			String outputsStr = tupleStrArr[1];
			String[] inputStrs = inputsStr.split("[,]+");
			String[] outputStrs = outputsStr.split("[,]+");
			DoubleMatrix inputs = new DoubleMatrix(1, inputStrs.length);
			DoubleMatrix outputs = new DoubleMatrix(1, outputStrs.length);
			for(int i=0; i<inputStrs.length; i++)
				inputs.put(0, i, Double.parseDouble(inputStrs[i]));
			for(int i=0; i<outputStrs.length; i++)
				outputs.put(0, i, Double.parseDouble(outputStrs[i]));
			TrainingTuple tt = new TrainingTuple(inputs, outputs);
			tts.add(tt);
		}
		return new TrainingData(tts);
	}

	private static List<String> readFile(String fileName) throws IOException {
		Path path = Paths.get(fileName);
		return Files.readAllLines(path, ENCODING);
	}
	
}
