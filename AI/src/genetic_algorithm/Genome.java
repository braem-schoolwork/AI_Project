package genetic_algorithm;

import java.util.ArrayList;

import neural_network.NeuralNetwork;

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
		for(Double d : this.genes) {
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
	public NeuralNetwork toNN(Genome genome, int inputLayerSize,
			ArrayList<Double> hiddenLayerSizes, int outputLayerSize) {
		
	}

}
