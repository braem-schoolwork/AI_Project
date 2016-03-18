package neural_network;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.jblas.*;

import org.junit.Test;

public class NNTest {
	
	@Test
	public void matrixIOTest() {
		DoubleMatrix m1 = new DoubleMatrix(new double[][] {{-1,1}});
		String m1str = m1.toString().replace('[', ' ').replace(']', ' ').replace(',', ' ');
		DoubleMatrix m2 = DoubleMatrix.valueOf(m1str);
		assertTrue(m1.equals(m2));
	}
	
	@Test
	public void applyTests() {
		NeuralNetworkParams nnParams = new NeuralNetworkParams();
		ArrayList<Integer> hls = new ArrayList<Integer>();
		hls.add(5);
		hls.add(11);
		hls.add(8);
		nnParams.setBias(1.0);
		nnParams.setInputLayerSize(3);
		nnParams.setOutputLayerSize(6);
		nnParams.setHiddenLayerSizes(hls);
		NeuralNetwork NN = new NeuralNetwork(nnParams);
		NN.init();
		ArrayList<DoubleMatrix> oldWjbias = NN.getWjbias();
		DoubleMatrix oldWkbias = NN.getWkbias();
		DoubleMatrix oldWji = NN.getWji();
		DoubleMatrix oldWkj = NN.getWkj();
		ArrayList<DoubleMatrix> oldWjs = NN.getWjs();
		
		ArrayList<DoubleMatrix> newWjbias = new ArrayList<DoubleMatrix>();
		DoubleMatrix newWkbias = new DoubleMatrix(1,oldWkbias.columns);
		DoubleMatrix newWji = new DoubleMatrix(oldWji.rows,oldWji.columns);
		DoubleMatrix newWkj = new DoubleMatrix(oldWkj.rows,oldWkj.columns);
		ArrayList<DoubleMatrix> newWjs = new ArrayList<DoubleMatrix>();
		for(int i=0; i<oldWjbias.size(); i++) {
			DoubleMatrix m = new DoubleMatrix(1,oldWjbias.get(i).columns);
			m.fill(-6535);
			newWjbias.add(m);
		}
		newWkbias.fill(43243);
		newWji.fill(-4334);
		newWkj.fill(45544);
		for(int i=0; i<oldWjs.size(); i++) {
			DoubleMatrix m = new DoubleMatrix(oldWjs.get(i).rows, oldWjs.get(i).columns);
			m.fill(76565);
			newWjs.add(m);
		}
		NN.applyWjbiasUpdate(newWjbias);
		NN.applyWjiUpdate(newWji);
		NN.applyWjsUpdate(newWjs);
		NN.applyWkbiasUpdate(newWkbias);
		NN.applyWkjUpdate(newWkj);
		assertTrue(oldWjbias.equals(NN.getWjbias()));
		assertTrue(oldWji.equals(NN.getWji()));
		assertTrue(oldWjs.equals(NN.getWjs()));
		assertTrue(oldWkbias.equals(NN.getWkbias()));
		assertTrue(oldWkj.equals(NN.getWkj()));
	}
	
	@Test
	public void multipleLayersTest() {
		NeuralNetworkParams nnParams = new NeuralNetworkParams();
		ArrayList<Integer> hls = new ArrayList<Integer>();
		hls.add(5);
		hls.add(11);
		hls.add(8);
		nnParams.setBias(1.0);
		nnParams.setInputLayerSize(3);
		nnParams.setOutputLayerSize(6);
		nnParams.setHiddenLayerSizes(hls);
		NeuralNetwork NN = new NeuralNetwork(nnParams);
		NN.init();
		ArrayList<DoubleMatrix> Wjbias = NN.getWjbias();
		DoubleMatrix Wkbias = NN.getWkbias();
		DoubleMatrix Wji = NN.getWji();
		DoubleMatrix Wkj = NN.getWkj();
		ArrayList<DoubleMatrix> Wjs = NN.getWjs();
		for(int i=0; i<Wjbias.size(); i++) {
			assertTrue(Wjbias.get(i).columns == hls.get(i));
		}
		assertTrue(Wkbias.columns == nnParams.getOutputLayerSize());
		assertTrue(Wji.rows == nnParams.getInputLayerSize());
		assertTrue(Wji.columns == hls.get(0));
		assertTrue(Wkj.rows == hls.get(hls.size()-1));
		assertTrue(Wkj.columns == nnParams.getOutputLayerSize());
		for(int i=0; i<Wjs.size(); i++) {
			assertTrue(Wjs.get(i).rows == hls.get(i));
			assertTrue(Wjs.get(i).columns == hls.get(i+1));
		}
	}
	
}
