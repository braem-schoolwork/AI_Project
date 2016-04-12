package program;

import java.awt.EventQueue;

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
 * for the XOR problem
 * 
 * @author braem
 * @version 1.0
 */
public class GAXORWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1589915785894380780L;
	private JPanel contentPane;
	private JFrame thisFrame = this;
	private JTextField crossoverTF;
	private JTextField mutationTF;
	private JTextField eliteTF;
	private JTextField genTF;
	private JTextField popsizeTF;
	private JTextField eliteFavoritismTF;
	private JTextField nnErrorTF;
	private TrainingData trainingData = null;
	private NeuralNetwork currentNN = null;
	private JTextField feedForwardResultTF;
	private JTextField fitnessThreshTF;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GAXORWindow frame = new GAXORWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void enable() {
		this.setVisible(true);
	}
	
	/**
	 * Create the frame.
	 */
	public GAXORWindow() {
		setTitle("Genetic Algorithm on XOR NN");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 626, 355);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JComboBox<TrainingTuple> tupleCB = new JComboBox<TrainingTuple>();
		tupleCB.setBounds(240, 112, 158, 29);
		contentPane.add(tupleCB);
		TrainingData data = XORTrainingDataGenerator.gen();
		for(TrainingTuple tt : data.getData())
			tupleCB.addItem(tt);
		trainingData = data;
		
		JLabel lblGeneticAlgorithmParameters = new JLabel("Genetic Algorithm Parameters:");
		lblGeneticAlgorithmParameters.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblGeneticAlgorithmParameters.setBounds(10, 11, 194, 17);
		contentPane.add(lblGeneticAlgorithmParameters);
		
		crossoverTF = new JTextField();
		crossoverTF.setFont(new Font("Tahoma", Font.PLAIN, 14));
		crossoverTF.setBounds(119, 39, 99, 23);
		contentPane.add(crossoverTF);
		crossoverTF.setColumns(10);
		
		JLabel lblCrossover = new JLabel("Crossover%:");
		lblCrossover.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCrossover.setBounds(10, 39, 99, 23);
		contentPane.add(lblCrossover);
		
		JLabel lblMutation = new JLabel("Mutation%:");
		lblMutation.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblMutation.setBounds(10, 73, 99, 23);
		contentPane.add(lblMutation);
		
		mutationTF = new JTextField();
		mutationTF.setFont(new Font("Tahoma", Font.PLAIN, 14));
		mutationTF.setColumns(10);
		mutationTF.setBounds(119, 73, 99, 23);
		contentPane.add(mutationTF);
		
		JLabel lblElite = new JLabel("Elite%:");
		lblElite.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblElite.setBounds(10, 107, 99, 23);
		contentPane.add(lblElite);
		
		eliteTF = new JTextField();
		eliteTF.setFont(new Font("Tahoma", Font.PLAIN, 14));
		eliteTF.setColumns(10);
		eliteTF.setBounds(119, 107, 99, 23);
		contentPane.add(eliteTF);
		
		JLabel lblGeneration = new JLabel("Generation#:");
		lblGeneration.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblGeneration.setBounds(10, 141, 99, 23);
		contentPane.add(lblGeneration);
		
		genTF = new JTextField();
		genTF.setText("50");
		genTF.setFont(new Font("Tahoma", Font.PLAIN, 14));
		genTF.setColumns(10);
		genTF.setBounds(119, 141, 99, 23);
		contentPane.add(genTF);
		
		JLabel lblPopulationSize = new JLabel("Population Size:");
		lblPopulationSize.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPopulationSize.setBounds(10, 175, 99, 23);
		contentPane.add(lblPopulationSize);
		
		popsizeTF = new JTextField();
		popsizeTF.setText("100");
		popsizeTF.setFont(new Font("Tahoma", Font.PLAIN, 14));
		popsizeTF.setColumns(10);
		popsizeTF.setBounds(119, 175, 99, 23);
		contentPane.add(popsizeTF);
		
		JLabel lblEliteFavoritismCoeff = new JLabel("Elite Favoritism Coeff:");
		lblEliteFavoritismCoeff.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblEliteFavoritismCoeff.setBounds(10, 209, 133, 23);
		contentPane.add(lblEliteFavoritismCoeff);
		
		eliteFavoritismTF = new JTextField();
		eliteFavoritismTF.setFont(new Font("Tahoma", Font.PLAIN, 14));
		eliteFavoritismTF.setColumns(10);
		eliteFavoritismTF.setBounds(153, 209, 99, 23);
		contentPane.add(eliteFavoritismTF);
		
		JLabel lblInvalidParameters = new JLabel("invalid parameters");
		lblInvalidParameters.setForeground(Color.RED);
		lblInvalidParameters.setHorizontalAlignment(SwingConstants.CENTER);
		lblInvalidParameters.setBounds(250, 57, 265, 14);
		lblInvalidParameters.setVisible(false);
		contentPane.add(lblInvalidParameters);
		
		JButton btnFeedForward = new JButton("Feed Forward");
		btnFeedForward.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TrainingTuple tt = (TrainingTuple)tupleCB.getSelectedItem();
				DoubleMatrix output = currentNN.feedForward(tt.getInputs());
				feedForwardResultTF.setText(""+output);
			}
		});
		btnFeedForward.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnFeedForward.setBounds(240, 152, 275, 46);
		btnFeedForward.setEnabled(false);
		contentPane.add(btnFeedForward);
		
		JLabel lblNnError = new JLabel("NN Error:");
		lblNnError.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNnError.setBounds(260, 73, 64, 23);
		contentPane.add(lblNnError);
		
		nnErrorTF = new JTextField();
		nnErrorTF.setEditable(false);
		nnErrorTF.setFont(new Font("Tahoma", Font.PLAIN, 14));
		nnErrorTF.setColumns(10);
		nnErrorTF.setBounds(323, 73, 192, 23);
		contentPane.add(nnErrorTF);
		
		JButton btnTrainOnXor = new JButton("Run on XOR NN");
		btnTrainOnXor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					trainingData = XORTrainingDataGenerator.gen();
					GeneticAlgorithmParams GAparams = new GeneticAlgorithmParams();
					GAparams.setPercentCrossOver(Float.parseFloat(crossoverTF.getText()));
					GAparams.setPercentElite(Float.parseFloat(eliteTF.getText()));
					GAparams.setPercentMutation(Float.parseFloat(mutationTF.getText()));
					GAparams.setNumGenerations(Integer.parseInt(genTF.getText()));
					GAparams.setPopulationSize(Integer.parseInt(popsizeTF.getText()));
					GAparams.setEliteFavoritismCoeff(Float.parseFloat(eliteFavoritismTF.getText()));
					GAparams.setFitnessTheshold(Double.parseDouble(fitnessThreshTF.getText()));
					GeneticAlgorithm GA = new GeneticAlgorithm(GAparams);
					NeuralNetwork NN = new NeuralNetwork();
					NN.init();
					GA.apply(NN, new NNFitnessTester(trainingData, NN));
					NeuralNetwork resultNN = (NeuralNetwork) GA.getBestGenomeImpl();
					currentNN = resultNN;
					nnErrorTF.setText(""+ErrorCalculator.calculateError(trainingData, currentNN));
					btnFeedForward.setEnabled(true);
					lblInvalidParameters.setVisible(false);
				} catch(Exception ex) {
					ex.printStackTrace();
					lblInvalidParameters.setVisible(true);
				}
			}
		});
		btnTrainOnXor.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnTrainOnXor.setBounds(240, 11, 275, 51);
		contentPane.add(btnTrainOnXor);
		
		feedForwardResultTF = new JTextField();
		feedForwardResultTF.setEditable(false);
		feedForwardResultTF.setBounds(408, 112, 107, 29);
		contentPane.add(feedForwardResultTF);
		feedForwardResultTF.setColumns(10);
		
		crossoverTF.setText("0");
		mutationTF.setText("80");
		eliteTF.setText("20");
		eliteFavoritismTF.setText("0.667");
		
		JButton btnBack = new JButton("back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GAChoiceWindow window = new GAChoiceWindow();
				window.enable();
				thisFrame.dispose();
			}
		});
		btnBack.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnBack.setBounds(10, 277, 90, 23);
		contentPane.add(btnBack);
		
		JLabel label = new JLabel("Fitness Threshold:");
		label.setFont(new Font("Tahoma", Font.PLAIN, 14));
		label.setBounds(10, 243, 133, 23);
		contentPane.add(label);
		
		fitnessThreshTF = new JTextField();
		fitnessThreshTF.setText("15.99");
		fitnessThreshTF.setFont(new Font("Tahoma", Font.PLAIN, 14));
		fitnessThreshTF.setColumns(10);
		fitnessThreshTF.setBounds(153, 243, 99, 23);
		contentPane.add(fitnessThreshTF);
		
		JButton btnWriteNetworkTo = new JButton("Write Network to .txt File");
		btnWriteNetworkTo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(currentNN != null)
					NeuralNetworkIO.writeNetworkToFile(currentNN);
			}
		});
		btnWriteNetworkTo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnWriteNetworkTo.setBounds(262, 209, 253, 23);
		contentPane.add(btnWriteNetworkTo);
		
		JButton btnSerializeNetwork = new JButton("Serialize Network");
		btnSerializeNetwork.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(currentNN != null)
					NeuralNetworkIO.writeNetwork(currentNN);
			}
		});
		btnSerializeNetwork.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnSerializeNetwork.setBounds(262, 243, 253, 23);
		contentPane.add(btnSerializeNetwork);
	}
}
