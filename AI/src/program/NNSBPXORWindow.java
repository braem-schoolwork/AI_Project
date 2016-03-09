package program;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.jblas.DoubleMatrix;

import neural_network.NeuralNetwork;
import neural_network.NeuralNetworkIO;
import neural_network.NeuralNetworkParams;
import training_algorithms.SBP;
import training_algorithms.SBPParams;
import training_data.TrainingData;
import training_data.TrainingTuple;
import training_data.XORTrainingDataGenerator;

import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.JComboBox;

/**
 * Window to train a neural network on the XOR problem
 * 
 * @author braemen
 * @version
 */
public class NNSBPXORWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3856668708286379758L;
	private JFrame thisFrame = this;
	private JPanel contentPane;
	private JTextField epochsTF;
	private JTextField trainingIterationsTF;
	private JTextField errorThresholdTF;
	private JTextField learningRateTF;
	private JTextField momentumRateTF;
	private JTextField trainingErrorTF;
	private JComboBox<TrainingTuple> tupleCB;
	
	//default NN params
	private int defaultInputLayerSize = 2;
	private int defaultHiddenLayerSize = 2;
	private int defaultOutputLayerSize = 1;
	
	//default SBP params
	private String defaultEpochsStr = "50";
	private String defaultTrainingIterationsStr = "3500";
	private String defaultErrorThresholdStr = "0.00001";
	private String defaultLearningRateStr = "0.1";
	private String defaultMomentumRateStr = "0.1";
	private NeuralNetwork NN = null;
	private JTextField ffTF;

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
		setBounds(100, 100, 721, 554);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
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
		lblError.setBounds(10, 257, 201, 27);
		contentPane.add(lblError);
		
		JButton btnRun = new JButton("Run");
		btnRun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					int inputLayerSize = defaultInputLayerSize;
					ArrayList<Integer> hiddenLayerSizes = new ArrayList<Integer>();
					hiddenLayerSizes.add(defaultHiddenLayerSize);
					int outputLayerSize = defaultOutputLayerSize;
					
					int epochs = Integer.parseInt(epochsTF.getText());
					int trainingIterations = Integer.parseInt(trainingIterationsTF.getText());
					double errorThreshold = Double.parseDouble(errorThresholdTF.getText());
					double learningRate = Double.parseDouble(learningRateTF.getText());
					double momentumRate = Double.parseDouble(momentumRateTF.getText());
					
					NeuralNetworkParams NNparams = new NeuralNetworkParams(1.0, inputLayerSize, hiddenLayerSizes, outputLayerSize);
					NN = new NeuralNetwork(NNparams);
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
		btnRun.setBounds(222, 11, 97, 110);
		contentPane.add(btnRun);
		
		JButton btnBack = new JButton("back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				NNWindow window = new NNWindow();
				thisFrame.dispose();
				window.enable();
			}
		});
		btnBack.setFont(new Font("Tahoma", Font.PLAIN, 30));
		btnBack.setBounds(10, 11, 97, 45);
		contentPane.add(btnBack);
		
		JButton btnResetToDefaults = new JButton("Reset to Defaults");
		btnResetToDefaults.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setDefaults();
			}
		});
		btnResetToDefaults.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnResetToDefaults.setBounds(10, 73, 195, 45);
		contentPane.add(btnResetToDefaults);
		
		JLabel lblTrainingError = new JLabel("Training Error:");
		lblTrainingError.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblTrainingError.setBounds(10, 227, 172, 27);
		contentPane.add(lblTrainingError);
		
		trainingErrorTF = new JTextField();
		trainingErrorTF.setFont(new Font("Tahoma", Font.PLAIN, 20));
		trainingErrorTF.setEditable(false);
		trainingErrorTF.setBounds(189, 227, 333, 27);
		contentPane.add(trainingErrorTF);
		trainingErrorTF.setColumns(10);
		
		JButton btnWriteThisNetwork = new JButton("Write this network to .txt file");
		btnWriteThisNetwork.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(NN != null)
					NeuralNetworkIO.writeNetworkToFile(NN);
			}
		});
		btnWriteThisNetwork.setBounds(10, 138, 302, 23);
		contentPane.add(btnWriteThisNetwork);
		
		JButton btnWriteBestNetwork = new JButton("Write best network to .txt file");
		btnWriteBestNetwork.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				NeuralNetworkIO.writeBestNetworkToFile();
			}
		});
		btnWriteBestNetwork.setBounds(10, 177, 302, 23);
		contentPane.add(btnWriteBestNetwork);
		
		JLabel lblXorTuples = new JLabel("XOR Tuples");
		lblXorTuples.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblXorTuples.setBounds(10, 293, 128, 25);
		contentPane.add(lblXorTuples);
		
		tupleCB = new JComboBox<TrainingTuple>();
		tupleCB.setBounds(173, 296, 201, 26);
		contentPane.add(tupleCB);
		TrainingData data = XORTrainingDataGenerator.gen();
		for(TrainingTuple tt : data.getData())
			tupleCB.addItem(tt);
		
		JButton btnFeedForward = new JButton("Feed Forward");
		btnFeedForward.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TrainingTuple tt = (TrainingTuple)tupleCB.getSelectedItem();
				DoubleMatrix output = NN.feedForward(tt.getInputs());
				ffTF.setText(""+output);
			}
		});
		btnFeedForward.setFont(new Font("Tahoma", Font.PLAIN, 24));
		btnFeedForward.setBounds(433, 279, 229, 52);
		contentPane.add(btnFeedForward);
		
		ffTF = new JTextField();
		ffTF.setEditable(false);
		ffTF.setFont(new Font("Tahoma", Font.PLAIN, 20));
		ffTF.setBounds(253, 334, 409, 31);
		contentPane.add(ffTF);
		ffTF.setColumns(10);
		
		JLabel lblFeedForwardResult = new JLabel("Feed Forward Result:");
		lblFeedForwardResult.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblFeedForwardResult.setBounds(10, 334, 237, 31);
		contentPane.add(lblFeedForwardResult);

		setDefaults();
	}
}
