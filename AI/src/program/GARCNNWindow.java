package program;

import java.awt.EventQueue;

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
 * on a Rubik's Cube
 * 
 * @author braem
 * @version 1.0
 */
public class GARCNNWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4882463439862166809L;
	private JPanel contentPane;
	private JFrame thisFrame = this;
	private JTextField crossoverTF;
	private JTextField mutationTF;
	private JTextField eliteTF;
	private JTextField genTF;
	private JTextField popsizeTF;
	private JTextField eliteFavoritismTF;
	private JTextField feedForwardResultTF;
	private JTextField nnErrorTF;
	private NeuralNetwork currentNN = null;
	private JComboBox<TrainingTuple> tupleCB;
	private TrainingData trainingData;
	private JButton btnFeedForward;
	private JButton btnUseThisNetwork;
	private JLabel lblFitnessThreshold;
	private JTextField fitnessThreshTF;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GARCNNWindow frame = new GARCNNWindow();
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
	public GARCNNWindow() {
		setTitle("Run Genetic Algorithm on Rubik's Cube Neural Network");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 606, 408);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		trainingData = TrainingDataGenerator.genFromFile();
		if(trainingData == null) {
			Phase1Experiment exp = new Phase1Experiment();
			exp.runExperiment(".csv", ExperimentSize.SMALL);
		}
		trainingData = TrainingDataGenerator.genFromFile();
		
		JLabel label = new JLabel("Genetic Algorithm Parameters:");
		label.setFont(new Font("Tahoma", Font.PLAIN, 14));
		label.setBounds(10, 11, 194, 17);
		contentPane.add(label);
		
		JLabel label_1 = new JLabel("Crossover%:");
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		label_1.setBounds(10, 39, 99, 23);
		contentPane.add(label_1);
		
		crossoverTF = new JTextField();
		crossoverTF.setText("0");
		crossoverTF.setFont(new Font("Tahoma", Font.PLAIN, 14));
		crossoverTF.setColumns(10);
		crossoverTF.setBounds(119, 39, 99, 23);
		contentPane.add(crossoverTF);
		
		JLabel label_2 = new JLabel("Mutation%:");
		label_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		label_2.setBounds(10, 73, 99, 23);
		contentPane.add(label_2);
		
		mutationTF = new JTextField();
		mutationTF.setText("80");
		mutationTF.setFont(new Font("Tahoma", Font.PLAIN, 14));
		mutationTF.setColumns(10);
		mutationTF.setBounds(119, 73, 99, 23);
		contentPane.add(mutationTF);
		
		JLabel label_3 = new JLabel("Elite%:");
		label_3.setFont(new Font("Tahoma", Font.PLAIN, 14));
		label_3.setBounds(10, 107, 99, 23);
		contentPane.add(label_3);
		
		eliteTF = new JTextField();
		eliteTF.setText("20");
		eliteTF.setFont(new Font("Tahoma", Font.PLAIN, 14));
		eliteTF.setColumns(10);
		eliteTF.setBounds(119, 107, 99, 23);
		contentPane.add(eliteTF);
		
		JLabel label_4 = new JLabel("Generation#:");
		label_4.setFont(new Font("Tahoma", Font.PLAIN, 14));
		label_4.setBounds(10, 141, 99, 23);
		contentPane.add(label_4);
		
		genTF = new JTextField();
		genTF.setText("50");
		genTF.setFont(new Font("Tahoma", Font.PLAIN, 14));
		genTF.setColumns(10);
		genTF.setBounds(119, 141, 99, 23);
		contentPane.add(genTF);
		
		JLabel label_5 = new JLabel("Population Size:");
		label_5.setFont(new Font("Tahoma", Font.PLAIN, 14));
		label_5.setBounds(10, 175, 99, 23);
		contentPane.add(label_5);
		
		popsizeTF = new JTextField();
		popsizeTF.setText("100");
		popsizeTF.setFont(new Font("Tahoma", Font.PLAIN, 14));
		popsizeTF.setColumns(10);
		popsizeTF.setBounds(119, 175, 99, 23);
		contentPane.add(popsizeTF);
		
		JLabel label_6 = new JLabel("Elite Favoritism Coeff:");
		label_6.setFont(new Font("Tahoma", Font.PLAIN, 14));
		label_6.setBounds(10, 209, 133, 23);
		contentPane.add(label_6);
		
		eliteFavoritismTF = new JTextField();
		eliteFavoritismTF.setText("0.667");
		eliteFavoritismTF.setFont(new Font("Tahoma", Font.PLAIN, 14));
		eliteFavoritismTF.setColumns(10);
		eliteFavoritismTF.setBounds(153, 209, 99, 23);
		contentPane.add(eliteFavoritismTF);
		
		JButton btnRunOnRc = new JButton("Run on RC NN");
		btnRunOnRc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					GeneticAlgorithmParams GAparams = new GeneticAlgorithmParams();
					GAparams.setPercentCrossOver(Float.parseFloat(crossoverTF.getText()));
					GAparams.setPercentElite(Float.parseFloat(eliteTF.getText()));
					GAparams.setPercentMutation(Float.parseFloat(mutationTF.getText()));
					GAparams.setNumGenerations(Integer.parseInt(genTF.getText()));
					GAparams.setPopulationSize(Integer.parseInt(popsizeTF.getText()));
					GAparams.setEliteFavoritismCoeff(Float.parseFloat(eliteFavoritismTF.getText()));
					GAparams.setFitnessTheshold(Double.parseDouble(fitnessThreshTF.getText()));
					GeneticAlgorithm GA = new GeneticAlgorithm(GAparams);
					NeuralNetworkParams NNparams = new NeuralNetworkParams();
					NNparams.setInputLayerSize(324);
					NNparams.setOutputLayerSize(7);
					ArrayList<Integer> hls = new ArrayList<Integer>();
					hls.add(60);
					hls.add(60);
					NNparams.setHiddenLayerSizes(hls);
					NeuralNetwork NN = new NeuralNetwork(NNparams);
					NN.init();
					GA.apply(NN, new NNFitnessTester(trainingData, NN));
					NeuralNetwork resultNN = (NeuralNetwork) GA.getBestGenomeImpl();
					currentNN = resultNN;
					nnErrorTF.setText(""+ErrorCalculator.calculateError(trainingData, currentNN));
					btnFeedForward.setEnabled(true);
					currentNN = resultNN;
				} catch(Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		btnRunOnRc.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnRunOnRc.setBounds(228, 11, 308, 51);
		contentPane.add(btnRunOnRc);
		
		btnFeedForward = new JButton("Feed Forward");
		btnFeedForward.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TrainingTuple tt = (TrainingTuple)tupleCB.getSelectedItem();
				DoubleMatrix output = currentNN.feedForward(tt.getInputs());
				feedForwardResultTF.setText(""+output);
			}
		});
		btnFeedForward.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnFeedForward.setEnabled(false);
		btnFeedForward.setBounds(228, 141, 308, 57);
		contentPane.add(btnFeedForward);
		
		JButton button_2 = new JButton("back");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GAChoiceWindow window = new GAChoiceWindow();
				window.enable();
				thisFrame.dispose();
			}
		});
		button_2.setFont(new Font("Tahoma", Font.PLAIN, 15));
		button_2.setBounds(10, 325, 90, 23);
		contentPane.add(button_2);
		
		tupleCB = new JComboBox<TrainingTuple>();
		tupleCB.setBounds(228, 106, 204, 29);
		contentPane.add(tupleCB);
		
		feedForwardResultTF = new JTextField();
		feedForwardResultTF.setEditable(false);
		feedForwardResultTF.setColumns(10);
		feedForwardResultTF.setBounds(442, 106, 94, 29);
		contentPane.add(feedForwardResultTF);
		
		JLabel label_7 = new JLabel("NN Error:");
		label_7.setFont(new Font("Tahoma", Font.PLAIN, 14));
		label_7.setBounds(228, 73, 64, 23);
		contentPane.add(label_7);
		
		nnErrorTF = new JTextField();
		nnErrorTF.setFont(new Font("Tahoma", Font.PLAIN, 14));
		nnErrorTF.setEditable(false);
		nnErrorTF.setColumns(10);
		nnErrorTF.setBounds(291, 73, 245, 23);
		contentPane.add(nnErrorTF);
		
		btnUseThisNetwork = new JButton("Use this Neural Network on a Rubik's Cube");
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
		btnUseThisNetwork.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnUseThisNetwork.setBounds(10, 277, 526, 37);
		contentPane.add(btnUseThisNetwork);
		
		lblFitnessThreshold = new JLabel("Fitness Threshold:");
		lblFitnessThreshold.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblFitnessThreshold.setBounds(10, 243, 133, 23);
		contentPane.add(lblFitnessThreshold);
		
		fitnessThreshTF = new JTextField();
		fitnessThreshTF.setText("300000");
		fitnessThreshTF.setFont(new Font("Tahoma", Font.PLAIN, 14));
		fitnessThreshTF.setColumns(10);
		fitnessThreshTF.setBounds(153, 243, 99, 23);
		contentPane.add(fitnessThreshTF);
		
		for(TrainingTuple tt : trainingData.getData())
			tupleCB.addItem(tt);
		
		JButton btnWriteNetworkTo = new JButton("Write Network to .txt File");
		btnWriteNetworkTo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(currentNN != null)
					NeuralNetworkIO.writeNetworkToFile(currentNN);
			}
		});
		btnWriteNetworkTo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnWriteNetworkTo.setBounds(262, 209, 274, 23);
		contentPane.add(btnWriteNetworkTo);
		
		JButton btnSerializeNetwork = new JButton("Serialize Network");
		btnSerializeNetwork.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(currentNN != null)
					NeuralNetworkIO.writeNetwork(currentNN);
			}
		});
		btnSerializeNetwork.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnSerializeNetwork.setBounds(262, 243, 274, 23);
		contentPane.add(btnSerializeNetwork);
	}
}
