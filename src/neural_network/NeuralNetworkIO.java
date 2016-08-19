package neural_network;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.jblas.DoubleMatrix;

/**
 * Handles all the IO for the Neural Network.
 * 
 * @author Braemen Stoltz
 * @version 1.0
 */
public class NeuralNetworkIO
{
	private final static Charset 	ENCODING 				= StandardCharsets.UTF_8;
	private final static String 	FILE_NAME 				= System.getProperty("user.dir")+"\\Network.txt";
	private final static String 	SER_FILE_NAME 			= "Network.ser";
	private final static String 	NEW_BEST_NETWORK_MSG 	= "New Best Neural Network Found!";
	
	/**
	 * Writes a neural network to a .txt file. Meant for human reading
	 * @param NN incoming neural network
	 * @return <code>true</code> if success <code>false</code> if failed
	 */
	public static boolean writeNetworkToFile(NeuralNetwork NN) {
		List<String> contents = new ArrayList<String>();
		contents.add("-Layer Sizes-");
		contents.add("Input Layer Size: "+NN.getParams().getInputLayerSize());
		contents.add("Hidden Layers: "+NN.getParams().getHiddenLayerSizes().size());
		contents.add("Hidden Layer Sizes: "+NN.getParams().getHiddenLayerSizes());
		contents.add("Output Layer Size: "+NN.getOutputLayerSize());
		contents.add("");
		contents.add("-Weight Matrices-");
		contents.add("Hidden Layer 0 to Input Layer edge weights matrix (Wji): "+NN.getWji());
		contents.add("Hidden Layers to bias matrices (Wjbias): "+NN.getWjbias());
		contents.add("Output Layer to Hidden Layer n edge weights matrix (Wkj): "+NN.getWkj());
		contents.add("Output Layer to bias matrix (Wkbias): "+NN.getWkbias());
		contents.add("Hidden Layer i-1 to Hidden Layer i weight matrix (Wjs): "+NN.getWjs());
		contents.add("");
		contents.add("Error of this Network: "+NN.getError());
		
		try {
			writeToFile(contents, FILE_NAME);
			return true;
		} catch (IOException e) {
			return false;
		}
	}
	
	/**
	 * Overwrites the current neural network file
	 */
	public static void writeBestNetworkToFile() {
		NeuralNetwork NN = readNetwork();
		if(NN != null)
			writeNetworkToFile(NN);
	}
	
	/**
	 * Reads a neural network from a serialized class file
	 * @return		the read neural network. Returns <code>null</code>
	 * 				if there was no file found
	 */
	public static NeuralNetwork readNetwork() {
		NeuralNetwork result = null;
		try {
			FileInputStream fis = new FileInputStream(SER_FILE_NAME);
			ObjectInputStream ois = new ObjectInputStream(fis);
			result = (NeuralNetwork) ois.readObject();
			ois.close();
		} catch (IOException e) {
			return null;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * Serializes a neural network to .ser file
	 * @param NN 	a neural network
	 */
	public static void writeNetwork(NeuralNetwork NN) {
		try {
			FileOutputStream fos = new FileOutputStream(SER_FILE_NAME);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(NN);
			oos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Checks if this is the best network so far
	 * @param error		error to be checked
	 * @return			<code>true</code> if the error was the lowest one found
	 * 					<code>false</code> otherwise
	 */
	static boolean isBestNetworkSoFar(DoubleMatrix error) {
		NeuralNetwork NN = readNetwork();
		if(NN != null) {
			boolean isLower = true;
			for(int i=0; i<error.rows; i++)
				for(int j=0; j<error.columns; j++)
					if(error.get(i,j) > NN.getError().get(i,j))
						isLower = false;
			if(isLower) {
				System.out.println(NEW_BEST_NETWORK_MSG);
				return true;
			}
			else
				return false;
		}
		else
			return true;
	}
	
	private static void writeToFile(List<String> lines, String filename) throws IOException {
		Path path = Paths.get(filename);
		Files.write(path, lines, ENCODING);
	}
}
