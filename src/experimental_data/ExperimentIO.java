package experimental_data;

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

import neural_network.NeuralNetworkParams;
import training_algorithms.SBPParams;

/**
 * IO used by experiments
 * 
 * @author braemen
 * @version 1.0
 */
public class ExperimentIO
{
	private final static Charset ENCODING = StandardCharsets.UTF_8;
	private final static String NNPARAMS_SERNAME = "BestNNparams.ser";
	private final static String SBPPARAMS_SERNAME = "BestSBPparams.ser";
	private final static String ERRORS_SERNAME = "BestErrors.ser";
	
	static boolean writeToFileTrunc(List<String> lines, String filename) {
		for(int i=0; i<lines.size(); i++) 
			for(int j=0; j<lines.size(); j++)
				if(lines.get(i).equals(lines.get(j)))
					lines.remove(i);
		Path path = Paths.get(filename);
		try {
			Files.write(path, lines, ENCODING);
			return true;
		} catch (IOException e) {
			return false;
		}
	}
	
	static boolean writeToFile(List<String> lines, String filename) {
		Path path = Paths.get(filename);
		try {
			Files.write(path, lines, ENCODING);
			return true;
		} catch (IOException e) {
			return false;
		}
	}
	
	static void serializeNNparams(ArrayList<NeuralNetworkParams> NNparams) {
		try {
			FileOutputStream fos = new FileOutputStream(NNPARAMS_SERNAME);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(NNparams);
			oos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	static void serializeSBPparams(ArrayList<SBPParams> sbpParams) {
		try {
			FileOutputStream fos = new FileOutputStream(SBPPARAMS_SERNAME);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(sbpParams);
			oos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	static void serializeErrors(ArrayList<DoubleMatrix> errors) {
		try {
			FileOutputStream fos = new FileOutputStream(ERRORS_SERNAME);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(errors);
			oos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public static ArrayList<NeuralNetworkParams> readNNparams() {
		ArrayList<NeuralNetworkParams> result = new ArrayList<NeuralNetworkParams>();
		try {
			FileInputStream fis = new FileInputStream(NNPARAMS_SERNAME);
			ObjectInputStream ois = new ObjectInputStream(fis);
			result = (ArrayList<NeuralNetworkParams>) ois.readObject();
			ois.close();
		} catch (IOException e) {
			return null;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public static ArrayList<SBPParams> readSBPparams() {
		ArrayList<SBPParams> result = new ArrayList<SBPParams>();
		try {
			FileInputStream fis = new FileInputStream(SBPPARAMS_SERNAME);
			ObjectInputStream ois = new ObjectInputStream(fis);
			result = (ArrayList<SBPParams>) ois.readObject();
			ois.close();
		} catch (IOException e) {
			return null;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public static ArrayList<DoubleMatrix> readErrors() {
		ArrayList<DoubleMatrix> result = new ArrayList<DoubleMatrix>();
		try {
			FileInputStream fis = new FileInputStream(ERRORS_SERNAME);
			ObjectInputStream ois = new ObjectInputStream(fis);
			result = (ArrayList<DoubleMatrix>) ois.readObject();
			ois.close();
		} catch (IOException e) {
			return null;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return result;
	}
}
