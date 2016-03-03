package program;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import neural_network.NeuralNetwork;
import neural_network.NeuralNetworkParams;
import training_algorithms.SBP;
import training_algorithms.SBPParams;
import training_data.XORTrainingDataGenerator;

import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class NNSBPXORWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3856668708286379758L;
	private JFrame thisFrame = this;
	private JPanel contentPane;
	private JTextField initialEdgeWeightsTF;
	private JTextField AValTF;
	private JTextField biasValTF;
	private JTextField epochsTF;
	private JTextField trainingIterationsTF;
	private JTextField errorThresholdTF;
	private JTextField learningRateTF;
	private JTextField momentumRateTF;
	private JTextField trainingErrorTF;
	
	//default NN params
	private int defaultInputLayerSize = 2;
	private int defaultHiddenLayerSize = 2;
	private int defaultOutputLayerSize = 1;
	private String defaultAvalStr = "1";
	private String defaultBiasValStr = "2.1";
	private String defaultInitialEdgeWeightsStr = "0.1";
	
	//default SBP params
	private String defaultEpochsStr = "500";
	private String defaultTrainingIterationsStr = "3500";
	private String defaultErrorThresholdStr = "0.00001";
	private String defaultLearningRateStr = "0.3";
	private String defaultMomentumRateStr = "0.3";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NNSBPXORWindow frame = new NNSBPXORWindow();
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
	
	private void setDefaults() {
		initialEdgeWeightsTF.setText(defaultInitialEdgeWeightsStr);
		AValTF.setText(defaultAvalStr);
		biasValTF.setText(defaultBiasValStr);
		epochsTF.setText(defaultEpochsStr);
		trainingIterationsTF.setText(defaultTrainingIterationsStr);
		errorThresholdTF.setText(defaultErrorThresholdStr);
		learningRateTF.setText(defaultLearningRateStr);
		momentumRateTF.setText(defaultMomentumRateStr);
	}
	
	/**
	 * Create the frame.
	 */
	public NNSBPXORWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 721, 376);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNeuralNetworkParams = new JLabel("Neural Network Params");
		lblNeuralNetworkParams.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNeuralNetworkParams.setBounds(10, 11, 224, 25);
		contentPane.add(lblNeuralNetworkParams);
		
		JLabel lblInitialEdgeWeights = new JLabel("Initial Edge Weights:");
		lblInitialEdgeWeights.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblInitialEdgeWeights.setBounds(10, 47, 194, 25);
		contentPane.add(lblInitialEdgeWeights);
		
		JLabel lblA = new JLabel("A Value:");
		lblA.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblA.setBounds(10, 83, 83, 25);
		contentPane.add(lblA);
		
		JLabel lblBiasValue = new JLabel("Bias Value:");
		lblBiasValue.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblBiasValue.setBounds(10, 119, 150, 25);
		contentPane.add(lblBiasValue);
		
		initialEdgeWeightsTF = new JTextField();
		initialEdgeWeightsTF.setFont(new Font("Tahoma", Font.PLAIN, 20));
		initialEdgeWeightsTF.setColumns(10);
		initialEdgeWeightsTF.setBounds(206, 47, 106, 25);
		contentPane.add(initialEdgeWeightsTF);
		
		AValTF = new JTextField();
		AValTF.setFont(new Font("Tahoma", Font.PLAIN, 20));
		AValTF.setColumns(10);
		AValTF.setBounds(206, 83, 106, 25);
		contentPane.add(AValTF);
		
		biasValTF = new JTextField();
		biasValTF.setFont(new Font("Tahoma", Font.PLAIN, 20));
		biasValTF.setColumns(10);
		biasValTF.setBounds(206, 119, 106, 25);
		contentPane.add(biasValTF);
		
		JLabel lblStochasticBackPropagation = new JLabel("Stochastic Back Propagation Params");
		lblStochasticBackPropagation.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblStochasticBackPropagation.setBounds(334, 11, 328, 25);
		contentPane.add(lblStochasticBackPropagation);
		
		JLabel lblEpochs = new JLabel("Epochs:");
		lblEpochs.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblEpochs.setBounds(344, 47, 178, 25);
		contentPane.add(lblEpochs);
		
		epochsTF = new JTextField();
		epochsTF.setFont(new Font("Tahoma", Font.PLAIN, 20));
		epochsTF.setColumns(10);
		epochsTF.setBounds(556, 47, 106, 25);
		contentPane.add(epochsTF);
		
		JLabel lblTrainingIterations = new JLabel("Training Iterations:");
		lblTrainingIterations.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblTrainingIterations.setBounds(344, 83, 178, 25);
		contentPane.add(lblTrainingIterations);
		
		JLabel lblErrorThreshold = new JLabel("Error Threshold:");
		lblErrorThreshold.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblErrorThreshold.setBounds(344, 119, 178, 25);
		contentPane.add(lblErrorThreshold);
		
		JLabel lblLearningRate = new JLabel("Learning Rate:");
		lblLearningRate.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblLearningRate.setBounds(344, 155, 178, 25);
		contentPane.add(lblLearningRate);
		
		JLabel lblMomentumRate = new JLabel("Momentum Rate:");
		lblMomentumRate.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblMomentumRate.setBounds(344, 191, 178, 25);
		contentPane.add(lblMomentumRate);
		
		trainingIterationsTF = new JTextField();
		trainingIterationsTF.setFont(new Font("Tahoma", Font.PLAIN, 20));
		trainingIterationsTF.setColumns(10);
		trainingIterationsTF.setBounds(556, 83, 106, 25);
		contentPane.add(trainingIterationsTF);
		
		errorThresholdTF = new JTextField();
		errorThresholdTF.setFont(new Font("Tahoma", Font.PLAIN, 20));
		errorThresholdTF.setColumns(10);
		errorThresholdTF.setBounds(556, 119, 106, 25);
		contentPane.add(errorThresholdTF);
		
		learningRateTF = new JTextField();
		learningRateTF.setFont(new Font("Tahoma", Font.PLAIN, 20));
		learningRateTF.setColumns(10);
		learningRateTF.setBounds(556, 155, 106, 25);
		contentPane.add(learningRateTF);
		
		momentumRateTF = new JTextField();
		momentumRateTF.setFont(new Font("Tahoma", Font.PLAIN, 20));
		momentumRateTF.setColumns(10);
		momentumRateTF.setBounds(556, 191, 106, 25);
		contentPane.add(momentumRateTF);
		
		JLabel lblError = new JLabel("");
		lblError.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblError.setForeground(Color.RED);
		lblError.setBounds(133, 263, 201, 33);
		contentPane.add(lblError);
		
		JButton btnRun = new JButton("Run");
		btnRun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					int inputLayerSize = defaultInputLayerSize;
					ArrayList<Integer> hiddenLayerSizes = new ArrayList<Integer>();
					hiddenLayerSizes.add(defaultHiddenLayerSize);
					int outputLayerSize = defaultOutputLayerSize;
					double initialEdgeWeight = Double.parseDouble(initialEdgeWeightsTF.getText());
					double AVal = Double.parseDouble(AValTF.getText());
					double biasVal = Double.parseDouble(biasValTF.getText());
					
					int epochs = Integer.parseInt(epochsTF.getText());
					int trainingIterations = Integer.parseInt(trainingIterationsTF.getText());
					double errorThreshold = Double.parseDouble(errorThresholdTF.getText());
					double learningRate = Double.parseDouble(learningRateTF.getText());
					double momentumRate = Double.parseDouble(momentumRateTF.getText());
					NeuralNetworkParams NNparams = new NeuralNetworkParams(AVal, biasVal, inputLayerSize, hiddenLayerSizes, outputLayerSize, initialEdgeWeight);
					NeuralNetwork NN = new NeuralNetwork(NNparams);
					SBPParams sbpParams = new SBPParams(epochs, trainingIterations, errorThreshold, learningRate, momentumRate);
					SBP sbp = new SBP(sbpParams);
					sbp.setTrainee(NN);
					sbp.apply(XORTrainingDataGenerator.gen());
					trainingErrorTF.setText(sbp.getError()+"");
					lblError.setText("");
				} catch(NumberFormatException e) {
					lblError.setText("Invalid Parameters");
					return;
				}
			}
		});
		btnRun.setFont(new Font("Tahoma", Font.PLAIN, 30));
		btnRun.setBounds(15, 160, 97, 56);
		contentPane.add(btnRun);
		
		JButton btnBack = new JButton("back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ChoiceWindow window = new ChoiceWindow();
				thisFrame.dispose();
				window.enable();
			}
		});
		btnBack.setFont(new Font("Tahoma", Font.PLAIN, 30));
		btnBack.setBounds(556, 228, 106, 56);
		contentPane.add(btnBack);
		
		JLabel lblnoteThatAll = new JLabel("*All default parameters are chosen through experimentation");
		lblnoteThatAll.setBounds(20, 263, 541, 14);
		contentPane.add(lblnoteThatAll);
		
		JButton btnResetToDefaults = new JButton("Reset to Defaults");
		btnResetToDefaults.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setDefaults();
			}
		});
		btnResetToDefaults.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnResetToDefaults.setBounds(117, 160, 195, 56);
		contentPane.add(btnResetToDefaults);
		
		JLabel lblTrainingError = new JLabel("Training Error:");
		lblTrainingError.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblTrainingError.setBounds(10, 232, 172, 27);
		contentPane.add(lblTrainingError);
		
		trainingErrorTF = new JTextField();
		trainingErrorTF.setBounds(182, 232, 359, 27);
		contentPane.add(trainingErrorTF);
		trainingErrorTF.setColumns(10);

		setDefaults();
	}
}
