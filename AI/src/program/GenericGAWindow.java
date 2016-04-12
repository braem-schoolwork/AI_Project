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
import neural_network.NeuralNetworkParams;
import training_algorithms.ErrorCalculator;
import training_data.TrainingData;
import training_data.TrainingDataGenerator;

import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import training_data.TrainingTuple;

/**
 * Window to Load training data & train a specified Neural Network with a
 * Genetic Algorithm
 * 
 * @author braem
 * @version 1.0
 */
public class GenericGAWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -415414550524034308L;
	private JPanel contentPane;
	private JFrame thisFrame = this;
	private TrainingData trainingData;
	private JTextField hiddenLayerSizesTF;
	private JTextField inputLayerSizeTF;
	private JTextField outputLayerSizeTF;
	private JCheckBox chckbxLoadedTrainingData;
	private JTextField feedForwardResultTF;
	private JTextField crossoverTF;
	private JTextField mutationTF;
	private JTextField eliteTF;
	private JTextField genTF;
	private JTextField popsizeTF;
	private JTextField eliteFavoritismTF;
	private JTextField fitnessThreshTF;
	private JButton btnFeedForward;
	private JButton btnRunGaOn;
	private JLabel label_11;
	private JTextField nnErrorTF;
	private JLabel lblFfResult;
	private NeuralNetwork currentNN = null;
	private JComboBox<TrainingTuple> tupleCB;
	private JButton button;
	private JButton button_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GenericGAWindow frame = new GenericGAWindow();
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
	public GenericGAWindow() {
		setTitle("Run GA on custom NN");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 772, 519);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnLoadTrainingData = new JButton("Load Training Data");
		btnLoadTrainingData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TrainingData td = TrainingDataGenerator.genFromFile();
				if(td == null)
					return;
				btnRunGaOn.setEnabled(true);
				btnFeedForward.setEnabled(false);
				trainingData = td;
				tupleCB.removeAllItems();
				inputLayerSizeTF.setText(""+trainingData.getData().get(0).getInputs().columns);
				outputLayerSizeTF.setText(""+trainingData.getData().get(0).getOutputs().columns);
				chckbxLoadedTrainingData.setSelected(true);
				for(TrainingTuple tt : trainingData.getData()) {
					tupleCB.addItem(tt);
				}
			}
		});
		btnLoadTrainingData.setFont(new Font("Tahoma", Font.PLAIN, 24));
		btnLoadTrainingData.setBounds(10, 11, 585, 39);
		contentPane.add(btnLoadTrainingData);
		
		JLabel label = new JLabel("Hidden Layer Sizes:");
		label.setFont(new Font("Tahoma", Font.PLAIN, 24));
		label.setBounds(10, 118, 220, 29);
		contentPane.add(label);
		
		hiddenLayerSizesTF = new JTextField();
		hiddenLayerSizesTF.setFont(new Font("Tahoma", Font.PLAIN, 20));
		hiddenLayerSizesTF.setColumns(10);
		hiddenLayerSizesTF.setBounds(230, 118, 228, 29);
		contentPane.add(hiddenLayerSizesTF);
		
		JLabel label_1 = new JLabel("Input Layer Size:");
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 24));
		label_1.setBounds(10, 61, 220, 29);
		contentPane.add(label_1);
		
		inputLayerSizeTF = new JTextField();
		inputLayerSizeTF.setFont(new Font("Tahoma", Font.PLAIN, 20));
		inputLayerSizeTF.setEditable(false);
		inputLayerSizeTF.setColumns(10);
		inputLayerSizeTF.setBounds(203, 61, 55, 29);
		contentPane.add(inputLayerSizeTF);
		
		outputLayerSizeTF = new JTextField();
		outputLayerSizeTF.setFont(new Font("Tahoma", Font.PLAIN, 20));
		outputLayerSizeTF.setEditable(false);
		outputLayerSizeTF.setColumns(10);
		outputLayerSizeTF.setBounds(471, 63, 55, 29);
		contentPane.add(outputLayerSizeTF);
		
		JLabel label_2 = new JLabel("Output Layer Size:");
		label_2.setFont(new Font("Tahoma", Font.PLAIN, 24));
		label_2.setBounds(268, 61, 220, 29);
		contentPane.add(label_2);
		
		JLabel label_3 = new JLabel("*input & output layer sizes are determined by the training data loaded");
		label_3.setBounds(10, 93, 548, 14);
		contentPane.add(label_3);
		
		JButton btnBack = new JButton("back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GAChoiceWindow window = new GAChoiceWindow();
				window.enable();
				thisFrame.dispose();
			}
		});
		btnBack.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnBack.setBounds(605, 13, 118, 39);
		contentPane.add(btnBack);
		
		chckbxLoadedTrainingData = new JCheckBox("Loaded Training Data");
		chckbxLoadedTrainingData.setBounds(465, 118, 208, 23);
		contentPane.add(chckbxLoadedTrainingData);
		chckbxLoadedTrainingData.setEnabled(false);
		
		btnFeedForward = new JButton("Feed Forward");
		btnFeedForward.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(currentNN != null && trainingData != null) {
					TrainingTuple tt = (TrainingTuple)tupleCB.getSelectedItem();
					DoubleMatrix output = currentNN.feedForward(tt.getInputs());
					feedForwardResultTF.setText(""+output);
				}
			}
		});
		btnFeedForward.setFont(new Font("Tahoma", Font.PLAIN, 24));
		btnFeedForward.setEnabled(false);
		btnFeedForward.setBounds(268, 338, 455, 46);
		contentPane.add(btnFeedForward);
		
		tupleCB = new JComboBox<TrainingTuple>();
		tupleCB.setFont(new Font("Tahoma", Font.PLAIN, 12));
		tupleCB.setBounds(268, 258, 455, 29);
		contentPane.add(tupleCB);
		
		feedForwardResultTF = new JTextField();
		feedForwardResultTF.setFont(new Font("Tahoma", Font.PLAIN, 12));
		feedForwardResultTF.setEditable(false);
		feedForwardResultTF.setColumns(10);
		feedForwardResultTF.setBounds(370, 298, 353, 29);
		contentPane.add(feedForwardResultTF);
		
		JLabel label_4 = new JLabel("Crossover%:");
		label_4.setFont(new Font("Tahoma", Font.PLAIN, 14));
		label_4.setBounds(10, 156, 99, 23);
		contentPane.add(label_4);
		
		crossoverTF = new JTextField();
		crossoverTF.setText("0");
		crossoverTF.setFont(new Font("Tahoma", Font.PLAIN, 14));
		crossoverTF.setColumns(10);
		crossoverTF.setBounds(119, 156, 99, 23);
		contentPane.add(crossoverTF);
		
		JLabel label_5 = new JLabel("Mutation%:");
		label_5.setFont(new Font("Tahoma", Font.PLAIN, 14));
		label_5.setBounds(10, 190, 99, 23);
		contentPane.add(label_5);
		
		mutationTF = new JTextField();
		mutationTF.setText("80");
		mutationTF.setFont(new Font("Tahoma", Font.PLAIN, 14));
		mutationTF.setColumns(10);
		mutationTF.setBounds(119, 190, 99, 23);
		contentPane.add(mutationTF);
		
		JLabel label_6 = new JLabel("Elite%:");
		label_6.setFont(new Font("Tahoma", Font.PLAIN, 14));
		label_6.setBounds(10, 224, 99, 23);
		contentPane.add(label_6);
		
		eliteTF = new JTextField();
		eliteTF.setText("20");
		eliteTF.setFont(new Font("Tahoma", Font.PLAIN, 14));
		eliteTF.setColumns(10);
		eliteTF.setBounds(119, 224, 99, 23);
		contentPane.add(eliteTF);
		
		JLabel label_7 = new JLabel("Generation#:");
		label_7.setFont(new Font("Tahoma", Font.PLAIN, 14));
		label_7.setBounds(10, 258, 99, 23);
		contentPane.add(label_7);
		
		genTF = new JTextField();
		genTF.setText("50");
		genTF.setFont(new Font("Tahoma", Font.PLAIN, 14));
		genTF.setColumns(10);
		genTF.setBounds(119, 258, 99, 23);
		contentPane.add(genTF);
		
		JLabel label_8 = new JLabel("Population Size:");
		label_8.setFont(new Font("Tahoma", Font.PLAIN, 14));
		label_8.setBounds(10, 292, 99, 23);
		contentPane.add(label_8);
		
		popsizeTF = new JTextField();
		popsizeTF.setText("100");
		popsizeTF.setFont(new Font("Tahoma", Font.PLAIN, 14));
		popsizeTF.setColumns(10);
		popsizeTF.setBounds(119, 292, 99, 23);
		contentPane.add(popsizeTF);
		
		JLabel label_9 = new JLabel("Elite Favoritism Coeff:");
		label_9.setFont(new Font("Tahoma", Font.PLAIN, 14));
		label_9.setBounds(10, 326, 133, 23);
		contentPane.add(label_9);
		
		eliteFavoritismTF = new JTextField();
		eliteFavoritismTF.setText("0.667");
		eliteFavoritismTF.setFont(new Font("Tahoma", Font.PLAIN, 14));
		eliteFavoritismTF.setColumns(10);
		eliteFavoritismTF.setBounds(153, 326, 99, 23);
		contentPane.add(eliteFavoritismTF);
		
		JLabel label_10 = new JLabel("Fitness Threshold:");
		label_10.setFont(new Font("Tahoma", Font.PLAIN, 14));
		label_10.setBounds(10, 360, 133, 23);
		contentPane.add(label_10);
		
		fitnessThreshTF = new JTextField();
		fitnessThreshTF.setText("15.99");
		fitnessThreshTF.setFont(new Font("Tahoma", Font.PLAIN, 14));
		fitnessThreshTF.setColumns(10);
		fitnessThreshTF.setBounds(153, 360, 99, 23);
		contentPane.add(fitnessThreshTF);
		
		btnRunGaOn = new JButton("Run GA on this NN");
		btnRunGaOn.addActionListener(new ActionListener() {
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
					NNparams.setInputLayerSize(Integer.parseInt(inputLayerSizeTF.getText()));
					NNparams.setOutputLayerSize(Integer.parseInt(outputLayerSizeTF.getText()));
					ArrayList<Integer> hls = new ArrayList<Integer>();
					String[] hiddenLayerSizesStr = hiddenLayerSizesTF.getText().split("[,]+");
					for(String hiddenLayerSizeStr : hiddenLayerSizesStr)
						hls.add(Integer.parseInt(hiddenLayerSizeStr));
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
					//ex.printStackTrace();
				}
			}
		});
		btnRunGaOn.setFont(new Font("Tahoma", Font.PLAIN, 24));
		btnRunGaOn.setBounds(230, 158, 493, 51);
		contentPane.add(btnRunGaOn);
		
		label_11 = new JLabel("NN Error:");
		label_11.setFont(new Font("Tahoma", Font.PLAIN, 14));
		label_11.setBounds(230, 220, 64, 23);
		contentPane.add(label_11);
		
		nnErrorTF = new JTextField();
		nnErrorTF.setFont(new Font("Tahoma", Font.PLAIN, 14));
		nnErrorTF.setEditable(false);
		nnErrorTF.setColumns(10);
		nnErrorTF.setBounds(296, 220, 427, 23);
		contentPane.add(nnErrorTF);
		
		lblFfResult = new JLabel("FF Result:");
		lblFfResult.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblFfResult.setBounds(268, 298, 92, 29);
		contentPane.add(lblFfResult);
		
		button = new JButton("Write Network to .txt File");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(currentNN != null)
					NeuralNetworkIO.writeNetworkToFile(currentNN);
			}
		});
		button.setFont(new Font("Tahoma", Font.PLAIN, 20));
		button.setBounds(10, 394, 350, 39);
		contentPane.add(button);
		
		button_1 = new JButton("Serialize Network");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(currentNN != null)
					NeuralNetworkIO.writeNetwork(currentNN);
			}
		});
		button_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		button_1.setBounds(370, 395, 353, 38);
		contentPane.add(button_1);
		
		btnRunGaOn.setEnabled(false);
	}
}
