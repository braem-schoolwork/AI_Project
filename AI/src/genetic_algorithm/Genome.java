package genetic_algorithm;

import java.util.ArrayList;

import org.jblas.DoubleMatrix;

import neural_network.NeuralNetwork;
import neural_network.NeuralNetworkParams;

/**
 * 
 * 
 * @author braemen
 * @version 1.0
 */
public class Genome
{
	private ArrayList<Double> genes;
	
	public Genome() {
		 genes = new ArrayList<Double>();
	}
	public Genome(ArrayList<Double> genes) {
		this.setGenes(genes);
	}
	public Genome(Genome genome) {
		ArrayList<Double> genes = new ArrayList<Double>();
		for(Double d : genome.genes) {
			genes.add(d);
		}
		this.genes = genes;
	}
	
	//getters/setters
	public ArrayList<Double> getGenes() {
		return genes;
	}
	public void setGenes(ArrayList<Double> genes) {
		this.genes = genes;
	}
	
	public static Genome convertTo(GenomeImpl g) {
		if(g instanceof NeuralNetwork) 
			return fromNN((NeuralNetwork)g);
					
		return null; //not supported
	}
	
	/**
	 * Converts a Neural Network into a Genome
	 * @param NN	incoming neural network
	 * @return		incoming neural network as a genome
	 */
	public static Genome fromNN(NeuralNetwork NN) {
		ArrayList<Double> genes = new ArrayList<Double>();

		//add the Wji matrix values
		for(int i=0; i<NN.getWji().rows; i++)
			for(int j=0; j<NN.getWji().columns; j++)
				genes.add(NN.getWji().get(i,j));
		//add the Wkj matrix values
		for(int i=0; i<NN.getWkj().rows; i++)
			for(int j=0; j<NN.getWkj().columns; j++)
				genes.add(NN.getWkj().get(i,j));
		//add the Wjs matrices values
		for(int i=0; i<NN.getWjs().size(); i++)
			for(int j=0; j<NN.getWjs().get(i).rows; j++)
				for(int k=0; k<NN.getWjs().get(i).columns; k++)
					genes.add(NN.getWjs().get(i).get(j,k));
		//add the Wjbias matrices values
		for(int i=0; i<NN.getWjbias().size(); i++)
			for(int j=0; j<NN.getWjbias().get(i).columns; j++)
				genes.add(NN.getWjbias().get(i).get(0,j));
		//add the Wkbias matrix values
		for(int i=0; i<NN.getWkbias().columns; i++)
			genes.add(NN.getWkbias().get(0,i));
		
		return new Genome(genes);
	}
	
	/**
	 * 
	 * @param genome
	 * @param inputLayerSize
	 * @param hiddenLayerSizes
	 * @param outputLayerSize
	 * @return
	 */
	public static NeuralNetwork toNN(Genome genome, NeuralNetworkParams params) {
		int inputLayerSize = params.getInputLayerSize();
		ArrayList<Integer> hiddenLayerSizes = params.getHiddenLayerSizes();
		int outputLayerSize = params.getOutputLayerSize();
		
		int ctr = 0;
		DoubleMatrix Wji = new DoubleMatrix(inputLayerSize, hiddenLayerSizes.get(0));
		for(int i=0; i<Wji.rows; i++)
			for(int j=0; j<Wji.columns; j++) {
				Wji.put(i, j, genome.getGenes().get(ctr));
				ctr++;
			}
		DoubleMatrix Wkj = new DoubleMatrix(hiddenLayerSizes.get(hiddenLayerSizes.size()-1), outputLayerSize);
		for(int i=0; i<Wkj.rows; i++)
			for(int j=0; j<Wkj.columns; j++) {
				Wkj.put(i, j, genome.getGenes().get(ctr));
				ctr++;
			}
		//TODO Wjs
		ArrayList<DoubleMatrix> Wjs = new ArrayList<DoubleMatrix>();
		for(int i=1; i<hiddenLayerSizes.size(); i++) {
			Wjs.add(new DoubleMatrix(hiddenLayerSizes.get(i-1), hiddenLayerSizes.get(i)));
		}
		for(int i=0; i<Wjs.size(); i++)
			for(int j=0; j<Wjs.get(i).rows; j++)
				for(int k=0; k<Wjs.get(i).columns; k++) {
					Wjs.get(i).put(j, k, genome.getGenes().get(ctr));
					ctr++;
				}
		
		ArrayList<DoubleMatrix> Wjbias = new ArrayList<DoubleMatrix>();
		for(int i=0; i<hiddenLayerSizes.size(); i++) {
			Wjbias.add(new DoubleMatrix(1, hiddenLayerSizes.get(i)));
		}
		for(int i=0; i<Wjbias.size(); i++)
			for(int j=0; j<Wjbias.get(i).columns; j++) {
				Wjbias.get(i).put(0, j, genome.getGenes().get(ctr));
				ctr++;
			}
		
		DoubleMatrix Wkbias = new DoubleMatrix(1, outputLayerSize);
		for(int i=0; i<Wkbias.columns; i++) {
			Wkbias.put(0, i, genome.getGenes().get(ctr));
			ctr++;
		}
			
		NeuralNetwork NN = new NeuralNetwork(params);
		NN.setWjbias(Wjbias);
		NN.setWji(Wji);
		NN.setWkbias(Wkbias);
		NN.setWkj(Wkj);
		NN.setWjs(Wjs);

		return NN;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj == this) return true;
		if(obj instanceof Genome)
			if(((Genome)obj).genes.equals(this.genes))
				return true;
		return false;
	}
	
	@Override
	public String toString() {
		return genes+"";
	}
}
