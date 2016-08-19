package program;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.jblas.DoubleMatrix;

import experimental_data.ExperimentSize;
import experimental_data.Phase1Experiment;
import genetic_algorithm.GeneticAlgorithm;
import genetic_algorithm.GeneticAlgorithmParams;
import neural_network.NNFitnessTester;
import neural_network.NeuralNetwork;
import neural_network.NeuralNetworkIO;
import neural_network.NeuralNetworkParams;
import training_algorithms.ErrorCalculator;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;

import training_data.TrainingData;
import training_data.TrainingDataGenerator;
import training_data.TrainingTuple;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

/**
 * Window to run a genetic algorithm on a 324-60-60-7 w/ bias Neural Network
 * on a Rubik's Cube.
 * 
 * @author Braemen Stoltz
 * @version 1.0
 */
public class GARCNNWindow extends JFrame
{
	private static final long 	serialVersionUID 	= -4882463439862166809L;
	private JPanel 				contentPane 		= new JPanel();
	private JFrame 				thisFrame 			= this;
	
	private JTextField crossoverTF 			= new JTextField();
	private JTextField mutationTF 			= new JTextField();
	private JTextField eliteTF 				= new JTextField();
	private JTextField genTF 				= new JTextField();
	private JTextField popsizeTF 			= new JTextField();
	private JTextField eliteFavoritismTF 	= new JTextField();
	private JTextField feedForwardResultTF 	= new JTextField();
	private JTextField nnErrorTF 			= new JTextField();
	private JTextField fitnessThreshTF 		= new JTextField();
	
	private JButton btnFeedForward 		= new JButton("Feed Forward");
	private JButton btnUseThisNetwork 	= new JButton("Use this Neural Network on a Rubik's Cube");
	private JLabel 	lblFitnessThreshold = new JLabel("Fitness Threshold:");
	
	private NeuralNetwork 				currentNN 		= null;
	private JComboBox<TrainingTuple> 	tupleCB 		= new JComboBox<TrainingTuple>();;
	private TrainingData 				trainingData 	= null;
	
	public void enable() { this.setVisible(true); }

	public GARCNNWindow() {
		setTitle				("Run Genetic Algorithm on Rubik's Cube Neural Network");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds				(100, 100, 606, 408);
		contentPane.setBorder	(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout	(null);
		setContentPane			(contentPane);
		
		trainingData = TrainingDataGenerator.genFromFile();
		if(trainingData == null) {
			Phase1Experiment exp = new Phase1Experiment();
			exp.runExperiment(".csv", ExperimentSize.SMALL);
		}
		trainingData = TrainingDataGenerator.genFromFile();

		JLabel 	lblGAParams 		= new JLabel("Genetic Algorithm Parameters:");
		JLabel 	lblCrossoverP 		= new JLabel("Crossover%:");
		JLabel 	lblMutationP 		= new JLabel("Mutation%:");
		JLabel 	lblEliteP 			= new JLabel("Elite%:");
		JLabel 	lblGenNum 			= new JLabel("Generation#:");
		JLabel 	lblPopsize 			= new JLabel("Population Size:");
		JLabel 	lblEliteFavoritism 	= new JLabel("Elite Favoritism Coeff:");
		JLabel 	lblNNError 			= new JLabel("NN Error:");
		JButton btnRunOnRc 			= new JButton("Run on RC NN");
		JButton btnBack 			= new JButton("back");
		JButton btnWriteNetworkTo 	= new JButton("Write Network to .txt File");
		JButton btnSerializeNetwork = new JButton("Serialize Network");
		
		tupleCB = new JComboBox<TrainingTuple>();
		tupleCB.setBounds(228, 106, 204, 29);
		
		feedForwardResultTF.setEditable	(false);
		feedForwardResultTF.setColumns	(10);
		feedForwardResultTF.setBounds	(442, 106, 94, 29);
		nnErrorTF.setFont				(new Font("Tahoma", Font.PLAIN, 14));
		nnErrorTF.setEditable			(false);
		nnErrorTF.setColumns			(10);
		nnErrorTF.setBounds				(291, 73, 245, 23);
		crossoverTF.setText				("0");
		crossoverTF.setFont				(new Font("Tahoma", Font.PLAIN, 14));
		crossoverTF.setColumns			(10);
		crossoverTF.setBounds			(119, 39, 99, 23);
		fitnessThreshTF.setText			("300000");
		fitnessThreshTF.setFont			(new Font("Tahoma", Font.PLAIN, 14));
		fitnessThreshTF.setColumns		(10);
		fitnessThreshTF.setBounds		(153, 243, 99, 23);
		mutationTF.setText				("80");
		mutationTF.setFont				(new Font("Tahoma", Font.PLAIN, 14));
		mutationTF.setColumns			(10);
		mutationTF.setBounds			(119, 73, 99, 23);		
		eliteTF.setText					("20");
		eliteTF.setFont					(new Font("Tahoma", Font.PLAIN, 14));
		eliteTF.setColumns				(10);
		eliteTF.setBounds				(119, 107, 99, 23);
		genTF.setText					("50");
		genTF.setFont					(new Font("Tahoma", Font.PLAIN, 14));
		genTF.setColumns				(10);
		genTF.setBounds					(119, 141, 99, 23);
		popsizeTF.setText				("100");
		popsizeTF.setFont				(new Font("Tahoma", Font.PLAIN, 14));
		popsizeTF.setColumns			(10);
		popsizeTF.setBounds				(119, 175, 99, 23);
		eliteFavoritismTF.setText		("0.667");
		eliteFavoritismTF.setFont		(new Font("Tahoma", Font.PLAIN, 14));
		eliteFavoritismTF.setColumns	(10);
		eliteFavoritismTF.setBounds		(153, 209, 99, 23);
		
		btnWriteNetworkTo.setFont		(new Font("Tahoma", Font.PLAIN, 14));
		btnWriteNetworkTo.setBounds		(262, 209, 274, 23);
		btnSerializeNetwork.setFont		(new Font("Tahoma", Font.PLAIN, 14));
		btnSerializeNetwork.setBounds	(262, 243, 274, 23);
		btnFeedForward.setFont			(new Font("Tahoma", Font.PLAIN, 16));
		btnFeedForward.setEnabled		(false);
		btnFeedForward.setBounds		(228, 141, 308, 57);
		btnRunOnRc.setFont				(new Font("Tahoma", Font.PLAIN, 16));
		btnRunOnRc.setBounds			(228, 11, 308, 51);
		btnBack.setFont					(new Font("Tahoma", Font.PLAIN, 15));
		btnBack.setBounds				(10, 325, 90, 23);
		btnUseThisNetwork.setFont		(new Font("Tahoma", Font.PLAIN, 15));
		btnUseThisNetwork.setBounds		(10, 277, 526, 37);
		
		lblGAParams.setFont				(new Font("Tahoma", Font.PLAIN, 14));
		lblGAParams.setBounds			(10, 11, 194, 17);
		lblCrossoverP.setFont			(new Font("Tahoma", Font.PLAIN, 14));
		lblCrossoverP.setBounds			(10, 39, 99, 23);
		lblMutationP.setFont			(new Font("Tahoma", Font.PLAIN, 14));
		lblMutationP.setBounds			(10, 73, 99, 23);
		lblEliteP.setFont				(new Font("Tahoma", Font.PLAIN, 14));
		lblEliteP.setBounds				(10, 107, 99, 23);
		lblGenNum.setFont				(new Font("Tahoma", Font.PLAIN, 14));
		lblGenNum.setBounds				(10, 141, 99, 23);
		lblPopsize.setFont				(new Font("Tahoma", Font.PLAIN, 14));
		lblPopsize.setBounds			(10, 175, 99, 23);
		lblEliteFavoritism.setFont		(new Font("Tahoma", Font.PLAIN, 14));
		lblEliteFavoritism.setBounds	(10, 209, 133, 23);		
		lblNNError.setFont				(new Font("Tahoma", Font.PLAIN, 14));
		lblNNError.setBounds			(228, 73, 64, 23);
		lblFitnessThreshold.setFont		(new Font("Tahoma", Font.PLAIN, 14));
		lblFitnessThreshold.setBounds	(10, 243, 133, 23);
		
		for(TrainingTuple tt : trainingData.getData())
			tupleCB.addItem(tt);
		
		btnRunOnRc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					GeneticAlgorithmParams GAparams = new GeneticAlgorithmParams();
					GAparams.setPercentCrossOver	(Float.parseFloat(crossoverTF.getText()));
					GAparams.setPercentElite		(Float.parseFloat(eliteTF.getText()));
					GAparams.setPercentMutation		(Float.parseFloat(mutationTF.getText()));
					GAparams.setNumGenerations		(Integer.parseInt(genTF.getText()));
					GAparams.setPopulationSize		(Integer.parseInt(popsizeTF.getText()));
					GAparams.setEliteFavoritismCoeff(Float.parseFloat(eliteFavoritismTF.getText()));
					GAparams.setFitnessTheshold		(Double.parseDouble(fitnessThreshTF.getText()));
					GeneticAlgorithm 	GA 			= new GeneticAlgorithm(GAparams);
					
					NeuralNetworkParams NNparams 	= new NeuralNetworkParams();
					NNparams.setInputLayerSize	(324);
					NNparams.setOutputLayerSize	(7);
					ArrayList<Integer> hls = new ArrayList<Integer>();
					hls.add(60);
					hls.add(60);
					NNparams.setHiddenLayerSizes(hls);
					NeuralNetwork NN = new NeuralNetwork(NNparams);
					NN.init();
					
					GA.apply(NN, new NNFitnessTester(trainingData, NN));
					currentNN 				= (NeuralNetwork) GA.getBestGenomeImpl();
					
					nnErrorTF.setText			(""+ErrorCalculator.calculateError(trainingData, currentNN));
					btnFeedForward.setEnabled	(true);
				} catch(Exception ex) {
					ex.printStackTrace();
				}
			}
		});
	
		btnFeedForward.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TrainingTuple 	tt 		= (TrainingTuple)tupleCB.getSelectedItem();
				DoubleMatrix 	output 	= currentNN.feedForward(tt.getInputs());
				feedForwardResultTF.setText(""+output);
			}
		});
		
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GAChoiceWindow window = new GAChoiceWindow();
				window.enable();
				thisFrame.dispose();
			}
		});
		
		btnUseThisNetwork.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(currentNN != null) {
					RCNNWindow window = new RCNNWindow();
					window.setNeuralNetwork(currentNN, trainingData);
					window.enable();
					thisFrame.dispose();
				}
			}
		});

		
		btnWriteNetworkTo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(currentNN != null)
					NeuralNetworkIO.writeNetworkToFile(currentNN);
			}
		});
		
		btnSerializeNetwork.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(currentNN != null)
					NeuralNetworkIO.writeNetwork(currentNN);
			}
		});

		contentPane.add(lblGAParams);
		contentPane.add(lblCrossoverP);
		contentPane.add(lblMutationP);
		contentPane.add(lblEliteP);
		contentPane.add(lblGenNum);
		contentPane.add(lblPopsize);
		contentPane.add(lblEliteFavoritism);
		contentPane.add(lblNNError);
		contentPane.add(btnRunOnRc);
		contentPane.add(btnBack);
		contentPane.add(btnFeedForward);
		contentPane.add(tupleCB);
		contentPane.add(popsizeTF);
		contentPane.add(crossoverTF);
		contentPane.add(genTF);
		contentPane.add(eliteTF);
		contentPane.add(mutationTF);
		contentPane.add(eliteFavoritismTF);
		contentPane.add(feedForwardResultTF);
		contentPane.add(nnErrorTF);
		contentPane.add(btnUseThisNetwork);
		contentPane.add(lblFitnessThreshold);
		contentPane.add(fitnessThreshTF);
		contentPane.add(btnWriteNetworkTo);
		contentPane.add(btnSerializeNetwork);
	}
}
