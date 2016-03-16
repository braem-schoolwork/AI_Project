package experimental_data;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.jblas.DoubleMatrix;
import org.junit.Test;

import neural_network.NeuralNetworkParams;
import training_algorithms.SBPParams;

public class ExperimentTest {

	@Test
	public void test() {
		ArrayList<NeuralNetworkParams> NNparams = ExperimentIO.readNNparams();
		ArrayList<SBPParams> sbpParams = ExperimentIO.readSBPparams();
		ArrayList<DoubleMatrix> errors = ExperimentIO.readErrors();
		System.out.println(errors);
	}

}
