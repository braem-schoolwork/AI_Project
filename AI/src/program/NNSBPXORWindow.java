package program;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import neural_network.NeuralNetwork;
import training_algorithms.SBP;
import training_data.XORTrainingDataGenerator;

import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class NNSBPXORWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3856668708286379758L;
	private JFrame thisFrame = this;
	private JPanel contentPane;
	private JTextField inputLayerSizeTF;
	private JTextField hiddenLayerSizeTF;
	private JTextField outputLayerSizeTF;
	private JTextField initialEdgeWeightsTF;
	private JTextField AValTF;
	private JTextField biasValTF;
	private JTextField epochsTF;
	private JTextField trainingIterationsTF;
	private JTextField errorThresholdTF;
	private JTextField learningRateTF;
	private JTextField momentumRateTF;
	private JTextField trainingErrorTF;

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
	
	/**
	 * Create the frame.
	 */
	public NNSBPXORWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 738, 455);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		inputLayerSizeTF = new JTextField();
		inputLayerSizeTF.setText("2");
		inputLayerSizeTF.setFont(new Font("Tahoma", Font.PLAIN, 20));
		inputLayerSizeTF.setBounds(206, 47, 106, 25);
		contentPane.add(inputLayerSizeTF);
		inputLayerSizeTF.setColumns(10);
		
		JLabel lblNeuralNetworkParams = new JLabel("Neural Network Params");
		lblNeuralNetworkParams.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNeuralNetworkParams.setBounds(10, 11, 224, 25);
		contentPane.add(lblNeuralNetworkParams);
		
		JLabel lblInputLayerSize = new JLabel("Input Layer Size:");
		lblInputLayerSize.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblInputLayerSize.setBounds(10, 47, 178, 25);
		contentPane.add(lblInputLayerSize);
		
		JLabel lblHiddenLayerSize = new JLabel("Hidden Layer Size:");
		lblHiddenLayerSize.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblHiddenLayerSize.setBounds(10, 83, 178, 25);
		contentPane.add(lblHiddenLayerSize);
		
		JLabel lblOutputLayerSize = new JLabel("Output Layer Size:");
		lblOutputLayerSize.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblOutputLayerSize.setBounds(10, 119, 172, 25);
		contentPane.add(lblOutputLayerSize);
		
		JLabel lblInitialEdgeWeights = new JLabel("Initial Edge Weights:");
		lblInitialEdgeWeights.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblInitialEdgeWeights.setBounds(10, 155, 194, 25);
		contentPane.add(lblInitialEdgeWeights);
		
		JLabel lblA = new JLabel("A Value:");
		lblA.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblA.setBounds(10, 191, 83, 25);
		contentPane.add(lblA);
		
		JLabel lblBiasValue = new JLabel("Bias Value:");
		lblBiasValue.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblBiasValue.setBounds(10, 227, 150, 25);
		contentPane.add(lblBiasValue);
		
		hiddenLayerSizeTF = new JTextField();
		hiddenLayerSizeTF.setText("2");
		hiddenLayerSizeTF.setFont(new Font("Tahoma", Font.PLAIN, 20));
		hiddenLayerSizeTF.setColumns(10);
		hiddenLayerSizeTF.setBounds(206, 83, 106, 25);
		contentPane.add(hiddenLayerSizeTF);
		
		outputLayerSizeTF = new JTextField();
		outputLayerSizeTF.setText("1");
		outputLayerSizeTF.setFont(new Font("Tahoma", Font.PLAIN, 20));
		outputLayerSizeTF.setColumns(10);
		outputLayerSizeTF.setBounds(206, 119, 106, 25);
		contentPane.add(outputLayerSizeTF);
		
		initialEdgeWeightsTF = new JTextField();
		initialEdgeWeightsTF.setText("0.1");
		initialEdgeWeightsTF.setFont(new Font("Tahoma", Font.PLAIN, 20));
		initialEdgeWeightsTF.setColumns(10);
		initialEdgeWeightsTF.setBounds(206, 155, 106, 25);
		contentPane.add(initialEdgeWeightsTF);
		
		AValTF = new JTextField();
		AValTF.setText("1.716");
		AValTF.setFont(new Font("Tahoma", Font.PLAIN, 20));
		AValTF.setColumns(10);
		AValTF.setBounds(206, 191, 106, 25);
		contentPane.add(AValTF);
		
		biasValTF = new JTextField();
		biasValTF.setText("0.667");
		biasValTF.setFont(new Font("Tahoma", Font.PLAIN, 20));
		biasValTF.setColumns(10);
		biasValTF.setBounds(206, 227, 106, 25);
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
		epochsTF.setText("5000");
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
		trainingIterationsTF.setText("3500");
		trainingIterationsTF.setFont(new Font("Tahoma", Font.PLAIN, 20));
		trainingIterationsTF.setColumns(10);
		trainingIterationsTF.setBounds(556, 83, 106, 25);
		contentPane.add(trainingIterationsTF);
		
		errorThresholdTF = new JTextField();
		errorThresholdTF.setText("0.0001");
		errorThresholdTF.setFont(new Font("Tahoma", Font.PLAIN, 20));
		errorThresholdTF.setColumns(10);
		errorThresholdTF.setBounds(556, 119, 106, 25);
		contentPane.add(errorThresholdTF);
		
		learningRateTF = new JTextField();
		learningRateTF.setText("0.3");
		learningRateTF.setFont(new Font("Tahoma", Font.PLAIN, 20));
		learningRateTF.setColumns(10);
		learningRateTF.setBounds(556, 155, 106, 25);
		contentPane.add(learningRateTF);
		
		momentumRateTF = new JTextField();
		momentumRateTF.setText("0.3");
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
					int inputLayerSize = Integer.parseInt(inputLayerSizeTF.getText());
					int hiddenLayerSize = Integer.parseInt(hiddenLayerSizeTF.getText());
					int outputLayerSize = Integer.parseInt(outputLayerSizeTF.getText());
					double initialEdgeWeight = Double.parseDouble(initialEdgeWeightsTF.getText());
					double AVal = Double.parseDouble(AValTF.getText());
					double biasVal = Double.parseDouble(biasValTF.getText());
					
					int epochs = Integer.parseInt(epochsTF.getText());
					int trainingIterations = Integer.parseInt(trainingIterationsTF.getText());
					double errorThreshold = Double.parseDouble(errorThresholdTF.getText());
					double learningRate = Double.parseDouble(learningRateTF.getText());
					double momentumRate = Double.parseDouble(momentumRateTF.getText());
					NeuralNetwork NN = new NeuralNetwork(AVal, biasVal, inputLayerSize, hiddenLayerSize, outputLayerSize, initialEdgeWeight);
					SBP.setEpochs(epochs);
					SBP.setErrorThreshold(errorThreshold);
					SBP.setTrainingIterations(trainingIterations);
					SBP.setLearningRate(learningRate);
					SBP.setMomentumRate(momentumRate);
					SBP.setTrainee(NN);
					SBP.apply(XORTrainingDataGenerator.gen());
					trainingErrorTF.setText(SBP.getError()+"");
					lblError.setText("");
				} catch(NumberFormatException e) {
					lblError.setText("Invalid Parameters");
					return;
				}
			}
		});
		btnRun.setFont(new Font("Tahoma", Font.PLAIN, 30));
		btnRun.setBounds(10, 263, 113, 50);
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
		btnBack.setBounds(549, 263, 113, 50);
		contentPane.add(btnBack);
		
		JLabel lblnoteThatAll = new JLabel("*All default parameters are chosen through experimentation");
		lblnoteThatAll.setBounds(10, 362, 630, 14);
		contentPane.add(lblnoteThatAll);
		
		JButton btnResetToDefaults = new JButton("Reset to Defaults");
		btnResetToDefaults.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				initialEdgeWeightsTF.setText("0.1");
				AValTF.setText("1.716");
				biasValTF.setText("0.667");
				epochsTF.setText("5000");
				trainingIterationsTF.setText("3500");
				errorThresholdTF.setText("0.0001");
				learningRateTF.setText("0.3");
				momentumRateTF.setText("0.3");
			}
		});
		btnResetToDefaults.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnResetToDefaults.setBounds(344, 263, 195, 50);
		contentPane.add(btnResetToDefaults);
		
		JLabel lblTrainingError = new JLabel("Training Error:");
		lblTrainingError.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblTrainingError.setBounds(10, 324, 172, 27);
		contentPane.add(lblTrainingError);
		
		trainingErrorTF = new JTextField();
		trainingErrorTF.setBounds(180, 324, 292, 27);
		contentPane.add(trainingErrorTF);
		trainingErrorTF.setColumns(10);
	}
}
