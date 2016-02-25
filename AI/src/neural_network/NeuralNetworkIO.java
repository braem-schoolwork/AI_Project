package neural_network;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author braem
 *
 */

public class NeuralNetworkIO
{
	private final static Charset ENCODING = StandardCharsets.UTF_8;
	private final static String FILE_NAME = System.getProperty("user.dir")+"\\Network.txt";
	
	static boolean writeNetwork(NeuralNetwork NN, double error) {
		List<String> contents = new ArrayList<String>();
		contents.add(NN.getInputLayerSize()+"");
		contents.add(NN.getHiddenLayerSize()+"");
		contents.add(NN.getOutputLayerSize()+"");
		contents.add(NN.getAVal()+"");
		contents.add(NN.getBiasVal()+"");
		contents.add(NN.getWji()+"");
		contents.add(NN.getWjbias()+"");
		contents.add(NN.getWkj()+"");
		contents.add(NN.getWkbias()+"");
		contents.add(error+"");
		
		try {
			writeToFile(contents, FILE_NAME);
			return true;
		} catch (IOException e) {
			return false;
		}
	}
	
	static boolean isBestNetworkSoFar(double error) {
		try {
			List<String> contents = readFile(FILE_NAME);
			if(Double.parseDouble(contents.get(9)) > error)
				return true;
			else
				return false;
		} catch (IOException e) {
			return true;
		}
	}
	
	private static List<String> readFile(String filename) throws IOException {
		Path path = Paths.get(filename);
		return Files.readAllLines(path, ENCODING);
	}
	
	private static void writeToFile(List<String> lines, String filename) throws IOException {
		Path path = Paths.get(filename);
		Files.write(path, lines, ENCODING);
	}
}
