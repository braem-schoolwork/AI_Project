package neural_network;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jblas.DoubleMatrix;

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
		contents.add(NN.getHiddenLayerSizes()+""); //list of hidden layer sizes
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
	
	static NeuralNetwork readNetwork() {
		NeuralNetwork NN = new NeuralNetwork();
		List<String> contents;
		try {
			contents = readFile(FILE_NAME);
		} catch (IOException e) {
			return null;
		}
		try {
			NeuralNetworkParams params = new NeuralNetworkParams();
			params.setInputLayerSize(Integer.parseInt(contents.get(0)));
			List<Integer> hiddenLayerSizes = new ArrayList<Integer>();
			String[] line = contents.get(1).replace('[', ' ').replace(']', ' ').split(",");
			for(int i=0; i<line.length; i++) 
				hiddenLayerSizes.add(Integer.parseInt(line[i]));
			params.setHiddenLayerSizes(hiddenLayerSizes);
			params.setOutputLayerSize(Integer.parseInt(contents.get(2)));
			params.setA(Double.parseDouble(contents.get(3)));
			params.setBias(Double.parseDouble(contents.get(4)));
			NN.setParams(params);
			NN.setWji(DoubleMatrix.valueOf(contents.get(5).replace('[', ' ').replace(']', ' ').replace(',', ' ')));
			NN.setWjbias(DoubleMatrix.valueOf(contents.get(6).replace('[', ' ').replace(']', ' ').replace(',', ' ')));
			NN.setWkj(DoubleMatrix.valueOf(contents.get(7).replace('[', ' ').replace(']', ' ').replace(',', ' ')));
			NN.setWkbias(DoubleMatrix.valueOf(contents.get(8).replace('[', ' ').replace(']', ' ').replace(',', ' ')));
		} catch(NumberFormatException e) {
			return null;
		}
		return NN;
	}
	
	static boolean isBestNetworkSoFar(double error) {
		try {
			List<String> contents = readFile(FILE_NAME);
			if(Double.parseDouble(contents.get(9)) > error) {
				System.out.println("New Best Neural Network Found!");
				return true;
			}
			else
				return false;
		} catch (IOException e) {
			System.out.println("New Best Neural Network Found!");
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
