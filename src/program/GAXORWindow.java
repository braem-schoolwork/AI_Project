package program;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.jblas.DoubleMatrix;

import genetic_algorithm.GeneticAlgorithm;
import genetic_algorithm.GeneticAlgorithmParams;
import neural_network.NNFitnessTester;
import neural_network.NeuralNetwork;
import neural_network.NeuralNetworkIO;
import training_algorithms.ErrorCalculator;
import training_data.TrainingData;
import training_data.TrainingTuple;
import training_data.XORTrainingDataGenerator;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Window to run a genetic algorithm on a 2-2-1 w/ bias Neural Network
 * for the XOR problem.
 * 
 * @author Braemen Stoltz
 * @version 1.0
 */
public class GAXORWindow extends JFrame
{
	private static final long 	serialVersionUID 	= -1589915785894380780L;
	private JPanel 				contentPane 		= new JPanel();
	private JFrame 				thisFrame 			= this;
	
	private JTextField crossoverTF 			= new JTextField();
	private JTextField mutationTF 			= new JTextField();
	private JTextField eliteTF 				= new JTextField();
	private JTextField genTF 				= new JTextField();
	private JTextField popsizeTF 			= new JTextField();
	private JTextField eliteFavoritismTF 	= new JTextField();
	private JTextField nnErrorTF 			= new JTextField();
	private JTextField feedForwardResultTF 	= new JTextField();
	private JTextField fitnessThreshTF 		= new JTextField();

	private TrainingData 	trainingData 	= null;
	private NeuralNetwork 	currentNN 		= null;

	public void enable() { this.setVisible(true); }
	
	public GAXORWindow() {
		setTitle				("Genetic Algorithm on XOR NN");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds				(100, 100, 626, 355);
		contentPane.setLayout	(null);
		contentPane.setBorder	(new EmptyBorder(5, 5, 5, 5));
		setContentPane			(contentPane);

		JComboBox<TrainingTuple> tupleCB = new JComboBox<TrainingTuple>();
		
		JButton btnFeedForward 		= new JButton("Feed Forward");
		JButton btnWriteNetworkTo 	= new JButton("Write Network to .txt File");
		JButton btnBack 			= new JButton("back");
		JButton btnTrainOnXor 		= new JButton("Run on XOR NN");
		JButton btnSerializeNetwork = new JButton("Serialize Network");
		
		JLabel lblGeneticAlgorithmParameters 	= new JLabel("Genetic Algorithm Parameters:");
		JLabel lblCrossover 					= new JLabel("Crossover%:");
		JLabel lblMutation 						= new JLabel("Mutation%:");
		JLabel lblElite 						= new JLabel("Elite%:");
		JLabel lblGeneration 					= new JLabel("Generation#:");
		JLabel lblPopulationSize 				= new JLabel("Population Size:");
		JLabel lblEliteFavoritismCoeff 			= new JLabel("Elite Favoritism Coeff:");
		JLabel lblInvalidParameters 			= new JLabel("invalid parameters");
		JLabel lblNnError 						= new JLabel("NN Error:");
		JLabel label 							= new JLabel("Fitness Threshold:");

		tupleCB.setBounds							(240, 112, 158, 29);
		lblGeneticAlgorithmParameters.setFont		(new Font("Tahoma", Font.PLAIN, 14));
		lblGeneticAlgorithmParameters.setBounds		(10, 11, 194, 17);
		lblCrossover.setFont						(new Font("Tahoma", Font.PLAIN, 14));
		lblCrossover.setBounds						(10, 39, 99, 23);
		lblMutation.setFont							(new Font("Tahoma", Font.PLAIN, 14));
		lblMutation.setBounds						(10, 73, 99, 23);
		lblElite.setFont							(new Font("Tahoma", Font.PLAIN, 14));
		lblElite.setBounds							(10, 107, 99, 23);
		lblGeneration.setFont						(new Font("Tahoma", Font.PLAIN, 14));
		lblGeneration.setBounds						(10, 141, 99, 23);
		lblEliteFavoritismCoeff.setFont				(new Font("Tahoma", Font.PLAIN, 14));
		lblEliteFavoritismCoeff.setBounds			(10, 209, 133, 23);
		lblPopulationSize.setFont					(new Font("Tahoma", Font.PLAIN, 14));
		lblPopulationSize.setBounds					(10, 175, 99, 23);
		lblInvalidParameters.setForeground			(Color.RED);
		lblInvalidParameters.setHorizontalAlignment	(SwingConstants.CENTER);
		lblInvalidParameters.setBounds				(250, 57, 265, 14);
		lblInvalidParameters.setVisible				(false);
		lblNnError.setFont							(new Font("Tahoma", Font.PLAIN, 14));
		lblNnError.setBounds						(260, 73, 64, 23);
		label.setFont								(new Font("Tahoma", Font.PLAIN, 14));
		label.setBounds								(10, 243, 133, 23);
		btnTrainOnXor.setFont						(new Font("Tahoma", Font.PLAIN, 16));
		btnTrainOnXor.setBounds						(240, 11, 275, 51);
		btnWriteNetworkTo.setFont					(new Font("Tahoma", Font.PLAIN, 14));
		btnWriteNetworkTo.setBounds					(262, 209, 253, 23);
		btnSerializeNetwork.setFont					(new Font("Tahoma", Font.PLAIN, 14));
		btnSerializeNetwork.setBounds				(262, 243, 253, 23);
		btnBack.setFont								(new Font("Tahoma", Font.PLAIN, 15));
		btnBack.setBounds							(10, 277, 90, 23);
		btnFeedForward.setFont						(new Font("Tahoma", Font.PLAIN, 16));
		btnFeedForward.setBounds					(240, 152, 275, 46);
		btnFeedForward.setEnabled					(false);

		crossoverTF.setFont				(new Font("Tahoma", Font.PLAIN, 14));
		crossoverTF.setBounds			(119, 39, 99, 23);
		crossoverTF.setColumns			(10);
		mutationTF.setFont				(new Font("Tahoma", Font.PLAIN, 14));
		mutationTF.setColumns			(10);
		mutationTF.setBounds			(119, 73, 99, 23);
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
		eliteFavoritismTF.setFont		(new Font("Tahoma", Font.PLAIN, 14));
		eliteFavoritismTF.setColumns	(10);
		eliteFavoritismTF.setBounds		(153, 209, 99, 23);
		nnErrorTF.setEditable			(false);
		nnErrorTF.setFont				(new Font("Tahoma", Font.PLAIN, 14));
		nnErrorTF.setColumns			(10);
		nnErrorTF.setBounds				(323, 73, 192, 23);
		feedForwardResultTF.setEditable	(false);
		feedForwardResultTF.setBounds	(408, 112, 107, 29);
		feedForwardResultTF.setColumns	(10);
		fitnessThreshTF.setFont			(new Font("Tahoma", Font.PLAIN, 14));
		fitnessThreshTF.setColumns		(10);
		fitnessThreshTF.setBounds		(153, 243, 99, 23);
		crossoverTF.setText				("0");
		mutationTF.setText				("80");
		eliteTF.setText					("20");
		eliteFavoritismTF.setText		("0.667");
		fitnessThreshTF.setText			("15.99");
		
		TrainingData data = XORTrainingDataGenerator.gen();
		for(TrainingTuple tt : data.getData())
			tupleCB.addItem(tt);
		trainingData = data;
		
		btnFeedForward.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TrainingTuple 	tt 		= (TrainingTuple)tupleCB.getSelectedItem();
				DoubleMatrix 	output 	= currentNN.feedForward(tt.getInputs());
				feedForwardResultTF.setText(""+output);
			}
		});
		
		btnTrainOnXor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					trainingData = XORTrainingDataGenerator.gen();
					
					GeneticAlgorithmParams GAparams = new GeneticAlgorithmParams();
					GAparams.setPercentCrossOver	(Float.parseFloat(crossoverTF.getText()));
					GAparams.setPercentElite		(Float.parseFloat(eliteTF.getText()));
					GAparams.setPercentMutation		(Float.parseFloat(mutationTF.getText()));
					GAparams.setNumGenerations		(Integer.parseInt(genTF.getText()));
					GAparams.setPopulationSize		(Integer.parseInt(popsizeTF.getText()));
					GAparams.setEliteFavoritismCoeff(Float.parseFloat(eliteFavoritismTF.getText()));
					GAparams.setFitnessTheshold		(Double.parseDouble(fitnessThreshTF.getText()));
					GeneticAlgorithm GA = new GeneticAlgorithm(GAparams);
					
					NeuralNetwork NN = new NeuralNetwork();
					NN.init();
					
					GA.apply(NN, new NNFitnessTester(trainingData, NN));
					NeuralNetwork resultNN = (NeuralNetwork) GA.getBestGenomeImpl();
					currentNN = resultNN;
						
					nnErrorTF.setText				(""+ErrorCalculator.calculateError(trainingData, currentNN));
					btnFeedForward.setEnabled		(true);
					lblInvalidParameters.setVisible	(false);
				} catch(Exception ex) {
					ex.printStackTrace();
					lblInvalidParameters.setVisible(true);
				}
			}
		});
		
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GAChoiceWindow window = new GAChoiceWindow();
				window.enable();
				thisFrame.dispose();
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

		contentPane.add(lblGeneticAlgorithmParameters);
		contentPane.add(lblCrossover);
		contentPane.add(lblMutation);
		contentPane.add(lblElite);
		contentPane.add(lblGeneration);
		contentPane.add(lblPopulationSize);
		contentPane.add(lblEliteFavoritismCoeff);
		contentPane.add(lblInvalidParameters);
		contentPane.add(lblNnError);
		contentPane.add(label);
		contentPane.add(fitnessThreshTF);
		contentPane.add(nnErrorTF);
		contentPane.add(eliteFavoritismTF);
		contentPane.add(popsizeTF);
		contentPane.add(genTF);
		contentPane.add(eliteTF);
		contentPane.add(mutationTF);
		contentPane.add(crossoverTF);
		contentPane.add(feedForwardResultTF);
		contentPane.add(btnFeedForward);
		contentPane.add(btnTrainOnXor);
		contentPane.add(btnBack);
		contentPane.add(btnWriteNetworkTo);
		contentPane.add(btnSerializeNetwork);
		contentPane.add(tupleCB);
	}
}
