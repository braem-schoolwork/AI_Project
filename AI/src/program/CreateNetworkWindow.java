package program;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import neural_network.NeuralNetwork;
import neural_network.NeuralNetworkIO;
import neural_network.NeuralNetworkParams;
import training_algorithms.SBP;
import training_algorithms.SBPParams;
import training_data.TrainingData;
import training_data.TrainingDataGenerator;
import training_data.TrainingTuple;

import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;

public class CreateNetworkWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4409889890487667466L;
	private JFrame thisFrame = this;
	private JPanel contentPane;
	private JTextField hiddenLayerSizesTF;
	private JTextField inputLayerSizeTF;
	private JTextField outputLayerSizeTF;
	private JTextField learningRateTF;
	private JTextField momentumRateTF;
	private JTextField epochsTF;
	private JTextField trainingIterationsTF;
	private JTextField errorThresholdTF;
	private JTextField trainingErrorTF;
	private JLabel lblInvalidInput;
	private JLabel lblFileNotFound;
	private TrainingData trainingData = null;
	private JLabel lblInvalidInputs;
	private NeuralNetwork NN = null;
	private JTextField ffTF;
	private JComboBox<TrainingTuple> ttCB;
	private JButton btnFeedForward;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CreateNetworkWindow frame = new CreateNetworkWindow();
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
	public CreateNetworkWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 661, 677);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		hiddenLayerSizesTF = new JTextField();
		hiddenLayerSizesTF.setFont(new Font("Tahoma", Font.PLAIN, 20));
		hiddenLayerSizesTF.setBounds(230, 14, 228, 29);
		contentPane.add(hiddenLayerSizesTF);
		hiddenLayerSizesTF.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Hidden Layer Sizes:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblNewLabel.setBounds(10, 14, 220, 29);
		contentPane.add(lblNewLabel);
		
		JLabel lblExample = new JLabel("example: 13,2,5,7");
		lblExample.setBounds(460, 27, 130, 14);
		contentPane.add(lblExample);
		
		JLabel lblInputLayerSizes = new JLabel("Input Layer Size:");
		lblInputLayerSizes.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblInputLayerSizes.setBounds(10, 54, 220, 29);
		contentPane.add(lblInputLayerSizes);
		
		inputLayerSizeTF = new JTextField();
		inputLayerSizeTF.setEditable(false);
		inputLayerSizeTF.setFont(new Font("Tahoma", Font.PLAIN, 20));
		inputLayerSizeTF.setColumns(10);
		inputLayerSizeTF.setBounds(203, 54, 55, 29);
		contentPane.add(inputLayerSizeTF);
		
		JLabel lblOutputLayerSize = new JLabel("Output Layer Size:");
		lblOutputLayerSize.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblOutputLayerSize.setBounds(268, 54, 220, 29);
		contentPane.add(lblOutputLayerSize);
		
		outputLayerSizeTF = new JTextField();
		outputLayerSizeTF.setEditable(false);
		outputLayerSizeTF.setFont(new Font("Tahoma", Font.PLAIN, 20));
		outputLayerSizeTF.setColumns(10);
		outputLayerSizeTF.setBounds(470, 52, 55, 29);
		contentPane.add(outputLayerSizeTF);
		
		JLabel lblInputOutput = new JLabel("*input & output layer sizes are determined by the training data loaded");
		lblInputOutput.setBounds(10, 86, 548, 14);
		contentPane.add(lblInputOutput);
		
		JButton btnLoadTrainingData = new JButton("Load Training Data");
		btnLoadTrainingData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				trainingData = TrainingDataGenerator.genFromFile();
				btnFeedForward.setEnabled(false);
				ttCB.removeAllItems();
				if(trainingData == null) {
					lblFileNotFound.setVisible(true);
					inputLayerSizeTF.setText("");
					outputLayerSizeTF.setText("");
					return;
				}
				else
					lblFileNotFound.setVisible(false);
				inputLayerSizeTF.setText(""+trainingData.getData().get(0).getInputs().columns);
				outputLayerSizeTF.setText(""+trainingData.getData().get(0).getOutputs().columns);
				for(TrainingTuple tt : trainingData.getData())
					ttCB.addItem(tt);
			}
		});
		btnLoadTrainingData.setFont(new Font("Tahoma", Font.PLAIN, 24));
		btnLoadTrainingData.setBounds(10, 326, 246, 37);
		contentPane.add(btnLoadTrainingData);
		
		lblInvalidInput = new JLabel("invalid input");
		lblInvalidInput.setForeground(Color.RED);
		lblInvalidInput.setBounds(462, 14, 96, 14);
		contentPane.add(lblInvalidInput);
		
		JLabel lblNewLabel_1 = new JLabel("Learning Rate:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblNewLabel_1.setBounds(10, 111, 163, 29);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblMomentumRate = new JLabel("Momentum Rate:");
		lblMomentumRate.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblMomentumRate.setBounds(10, 151, 191, 29);
		contentPane.add(lblMomentumRate);
		
		JLabel lblEpochs = new JLabel("Epochs:");
		lblEpochs.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblEpochs.setBounds(10, 196, 89, 29);
		contentPane.add(lblEpochs);
		
		JLabel lblTrainingIterations = new JLabel("Training Iterations:");
		lblTrainingIterations.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblTrainingIterations.setBounds(10, 236, 220, 29);
		contentPane.add(lblTrainingIterations);
		
		learningRateTF = new JTextField();
		learningRateTF.setFont(new Font("Tahoma", Font.PLAIN, 20));
		learningRateTF.setColumns(10);
		learningRateTF.setBounds(240, 111, 244, 29);
		contentPane.add(learningRateTF);
		
		momentumRateTF = new JTextField();
		momentumRateTF.setFont(new Font("Tahoma", Font.PLAIN, 20));
		momentumRateTF.setColumns(10);
		momentumRateTF.setBounds(240, 151, 244, 29);
		contentPane.add(momentumRateTF);
		
		epochsTF = new JTextField();
		epochsTF.setFont(new Font("Tahoma", Font.PLAIN, 20));
		epochsTF.setColumns(10);
		epochsTF.setBounds(240, 196, 244, 29);
		contentPane.add(epochsTF);
		
		trainingIterationsTF = new JTextField();
		trainingIterationsTF.setFont(new Font("Tahoma", Font.PLAIN, 20));
		trainingIterationsTF.setColumns(10);
		trainingIterationsTF.setBounds(240, 236, 244, 29);
		contentPane.add(trainingIterationsTF);
		
		JButton btnRun = new JButton("Train Network");
		btnRun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(trainingData == null) {
					return;
				}
				try {
					//NN
					int inputLayerSize = Integer.parseInt(inputLayerSizeTF.getText());
					ArrayList<Integer> hiddenLayerSizes = new ArrayList<Integer>();
					String[] hiddenLayerSizesStr = hiddenLayerSizesTF.getText().split("[,]+");
					for(String hiddenLayerSizeStr : hiddenLayerSizesStr)
						hiddenLayerSizes.add(Integer.parseInt(hiddenLayerSizeStr));
					int outputLayerSize = Integer.parseInt(outputLayerSizeTF.getText());
					
					//SBP
					int epochs = Integer.parseInt(epochsTF.getText());
					int trainingIterations = Integer.parseInt(trainingIterationsTF.getText());
					double errorThreshold = Double.parseDouble(errorThresholdTF.getText());
					double learningRate = Double.parseDouble(learningRateTF.getText());
					double momentumRate = Double.parseDouble(momentumRateTF.getText());
					
					//create new NN
					NeuralNetworkParams NNparams = new NeuralNetworkParams(1.0, inputLayerSize, hiddenLayerSizes, outputLayerSize);
					NN = new NeuralNetwork(NNparams);
					
					//create new SBP
					SBPParams sbpParams = new SBPParams(epochs, trainingIterations, errorThreshold, learningRate, momentumRate);
					SBP sbp = new SBP(sbpParams);
					
					//Train!
					sbp.setTrainee(NN);
					sbp.apply(trainingData);
					trainingErrorTF.setText(sbp.getError()+"");
					btnFeedForward.setEnabled(true);
					
					lblInvalidInputs.setText("");
				} catch(NumberFormatException e) {
					lblInvalidInputs.setText("Invalid Parameters");
					return;
				}
			}
		});
		btnRun.setFont(new Font("Tahoma", Font.PLAIN, 24));
		btnRun.setBounds(288, 326, 196, 37);
		contentPane.add(btnRun);
		
		lblFileNotFound = new JLabel("File Not Found");
		lblFileNotFound.setForeground(Color.RED);
		lblFileNotFound.setBounds(10, 362, 191, 14);
		contentPane.add(lblFileNotFound);
		
		JLabel lblErrorThreshold = new JLabel("Error Threshold:");
		lblErrorThreshold.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblErrorThreshold.setBounds(10, 281, 183, 29);
		contentPane.add(lblErrorThreshold);
		
		errorThresholdTF = new JTextField();
		errorThresholdTF.setFont(new Font("Tahoma", Font.PLAIN, 20));
		errorThresholdTF.setColumns(10);
		errorThresholdTF.setBounds(240, 281, 244, 29);
		contentPane.add(errorThresholdTF);
		
		JButton btnBack = new JButton("back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				NNWindow window = new NNWindow();
				thisFrame.dispose();
				window.enable();
			}
		});
		btnBack.setFont(new Font("Tahoma", Font.PLAIN, 24));
		btnBack.setBounds(469, 471, 89, 37);
		contentPane.add(btnBack);
		
		JLabel lblTrainingError = new JLabel("Training Error:");
		lblTrainingError.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblTrainingError.setBounds(10, 387, 163, 29);
		contentPane.add(lblTrainingError);
		
		trainingErrorTF = new JTextField();
		trainingErrorTF.setEditable(false);
		trainingErrorTF.setFont(new Font("Tahoma", Font.PLAIN, 20));
		trainingErrorTF.setColumns(10);
		trainingErrorTF.setBounds(175, 387, 309, 29);
		contentPane.add(trainingErrorTF);
		
		JButton btnWriteNetworkToFile = new JButton("Write Network to .txt");
		btnWriteNetworkToFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(NN != null)
					NeuralNetworkIO.writeNetworkToFile(NN);
			}
		});
		btnWriteNetworkToFile.setFont(new Font("Tahoma", Font.PLAIN, 24));
		btnWriteNetworkToFile.setBounds(10, 427, 292, 37);
		contentPane.add(btnWriteNetworkToFile);
		
		JButton btnSerializeNetwork = new JButton("Serialize Network");
		btnSerializeNetwork.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(NN != null)
					NeuralNetworkIO.writeNetwork(NN);
			}
		});
		btnSerializeNetwork.setFont(new Font("Tahoma", Font.PLAIN, 24));
		btnSerializeNetwork.setBounds(312, 427, 246, 37);
		contentPane.add(btnSerializeNetwork);
		
		lblInvalidInputs = new JLabel("invalid parameters");
		lblInvalidInputs.setForeground(Color.RED);
		lblInvalidInputs.setBounds(288, 359, 196, 20);
		contentPane.add(lblInvalidInputs);
		
		JLabel lblwritingSerializing = new JLabel("*Writing & Serializing Networks will overwrite");
		lblwritingSerializing.setBounds(10, 471, 448, 20);
		contentPane.add(lblwritingSerializing);
		
		JLabel lblNetworktxtAndorNetworkser = new JLabel("Network.txt and/or Network.ser");
		lblNetworktxtAndorNetworkser.setBounds(10, 488, 335, 20);
		contentPane.add(lblNetworktxtAndorNetworkser);
		
		JLabel lblTrainingTuples = new JLabel("Training Tuples");
		lblTrainingTuples.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblTrainingTuples.setBounds(10, 519, 183, 29);
		contentPane.add(lblTrainingTuples);
		
		ttCB = new JComboBox<TrainingTuple>();
		ttCB.setBounds(175, 519, 383, 29);
		contentPane.add(ttCB);
		
		btnFeedForward = new JButton("Feed Forward");
		btnFeedForward.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TrainingTuple tt = (TrainingTuple)ttCB.getSelectedItem();
				ffTF.setText(""+NN.feedForward(tt.getInputs()));
			}
		});
		btnFeedForward.setFont(new Font("Tahoma", Font.PLAIN, 24));
		btnFeedForward.setBounds(10, 559, 191, 37);
		contentPane.add(btnFeedForward);
		btnFeedForward.setEnabled(false);
		
		ffTF = new JTextField();
		ffTF.setEditable(false);
		ffTF.setFont(new Font("Tahoma", Font.PLAIN, 20));
		ffTF.setBounds(211, 559, 347, 37);
		contentPane.add(ffTF);
		ffTF.setColumns(10);
		
		lblInvalidInput.setVisible(false);
		lblFileNotFound.setVisible(false);
		lblInvalidInputs.setVisible(false);
	}
}
