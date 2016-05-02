package program;

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

/**
 * Window to create a general neural network, load some training data,
 * play with the parameters, and test the network using feed forward.
 * 
 * @author Braemen Stoltz
 * @version 1.0
 */
public class CreateNetworkWindow extends JFrame
{
	private static final long 	serialVersionUID 	= -4409889890487667466L;
	private JFrame 				thisFrame 			= this;
	private JPanel 				contentPane			= new JPanel();
	
	private JTextField 					hiddenLayerSizesTF 		= new JTextField();
	private JTextField 					inputLayerSizeTF 		= new JTextField();
	private JTextField 					outputLayerSizeTF 		= new JTextField();
	private JTextField 					learningRateTF 			= new JTextField();
	private JTextField 					momentumRateTF 			= new JTextField();
	private JTextField 					epochsTF 				= new JTextField();
	private JTextField 					trainingIterationsTF 	= new JTextField();
	private JTextField 					errorThresholdTF 		= new JTextField();
	private JTextField 					trainingErrorTF 		= new JTextField();
	private JTextField 					ffTF 					= new JTextField();
	private JButton 					btnFeedForward 			= new JButton("Feed Forward");
	private JComboBox<TrainingTuple> 	ttCB 					= new JComboBox<TrainingTuple>();
	
	private JLabel 	lblInvalidInput 	= new JLabel("invalid input");
	private JLabel 	lblFileNotFound 	= new JLabel("File Not Found");
	private JLabel 	lblInvalidInputs 	= new JLabel("invalid parameters");
	
	private TrainingData 	trainingData 	= null;
	private NeuralNetwork 	NN 				= null;
	
	public void enable() {
		this.setVisible(true);
	}
	
	public CreateNetworkWindow() {
		setDefaultCloseOperation	(JFrame.EXIT_ON_CLOSE);
		setBounds					(100, 100, 661, 677);
		contentPane.setBorder		(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout		(null);
		setContentPane				(contentPane);
		
		hiddenLayerSizesTF.setFont(new Font("Tahoma", Font.PLAIN, 20));
		hiddenLayerSizesTF.setBounds(230, 14, 228, 29);
		contentPane.add(hiddenLayerSizesTF);
		hiddenLayerSizesTF.setColumns(10);
		
		JLabel lblNewLabel 					= new JLabel("Hidden Layer Sizes:");
		JLabel lblExample 					= new JLabel("example: 13,2,5,7");
		JLabel lblInputLayerSizes 			= new JLabel("Input Layer Size:");
		JLabel lblOutputLayerSize 			= new JLabel("Output Layer Size:");
		JLabel lblInputOutput 				= new JLabel("*input & output layer sizes are determined by the training data loaded");
		JLabel lblNewLabel_1 				= new JLabel("Learning Rate:");
		JLabel lblMomentumRate 				= new JLabel("Momentum Rate:");
		JLabel lblEpochs 					= new JLabel("Epochs:");
		JLabel lblTrainingIterations 		= new JLabel("Training Iterations:");
		JLabel lblErrorThreshold 			= new JLabel("Error Threshold:");
		JLabel lblTrainingError 			= new JLabel("Training Error:");
		JLabel lblwritingSerializing 		= new JLabel("*Writing & Serializing Networks will overwrite");
		JLabel lblNetworktxtAndorNetworkser = new JLabel("Network.txt and/or Network.ser");
		JLabel lblTrainingTuples 			= new JLabel("Training Tuples");
		
		lblNewLabel.setFont						(new Font("Tahoma", Font.PLAIN, 24));
		lblNewLabel.setBounds					(10, 14, 220, 29);
		lblExample.setBounds					(460, 27, 130, 14);
		lblInputLayerSizes.setFont				(new Font("Tahoma", Font.PLAIN, 24));
		lblInputLayerSizes.setBounds			(10, 54, 220, 29);
		lblOutputLayerSize.setFont				(new Font("Tahoma", Font.PLAIN, 24));
		lblOutputLayerSize.setBounds			(268, 54, 220, 29);
		lblInputOutput.setBounds				(10, 86, 548, 14);
		lblInvalidInput.setForeground			(Color.RED);
		lblInvalidInput.setBounds				(462, 14, 96, 14);
		lblNewLabel_1.setFont					(new Font("Tahoma", Font.PLAIN, 24));
		lblNewLabel_1.setBounds					(10, 111, 163, 29);
		lblMomentumRate.setFont					(new Font("Tahoma", Font.PLAIN, 24));
		lblMomentumRate.setBounds				(10, 151, 191, 29);
		lblEpochs.setFont						(new Font("Tahoma", Font.PLAIN, 24));
		lblEpochs.setBounds						(10, 196, 89, 29);
		lblTrainingIterations.setFont			(new Font("Tahoma", Font.PLAIN, 24));
		lblTrainingIterations.setBounds			(10, 236, 220, 29);
		lblFileNotFound.setForeground			(Color.RED);
		lblFileNotFound.setBounds				(10, 362, 191, 14);		
		lblErrorThreshold.setFont				(new Font("Tahoma", Font.PLAIN, 24));
		lblErrorThreshold.setBounds				(10, 281, 183, 29);
		lblTrainingError.setFont				(new Font("Tahoma", Font.PLAIN, 24));
		lblTrainingError.setBounds				(10, 387, 163, 29);		
		lblInvalidInputs.setForeground			(Color.RED);
		lblInvalidInputs.setBounds				(288, 359, 196, 20);
		lblwritingSerializing.setBounds			(10, 471, 448, 20);
		lblNetworktxtAndorNetworkser.setBounds	(10, 488, 335, 20);
		lblTrainingTuples.setFont				(new Font("Tahoma", Font.PLAIN, 24));
		lblTrainingTuples.setBounds				(10, 519, 183, 29);
		
		JButton btnLoadTrainingData 	= new JButton("Load Training Data");
		JButton btnRun 					= new JButton("Train Network");
		JButton btnBack 				= new JButton("back");
		JButton btnWriteNetworkToFile 	= new JButton("Write Network to .txt");
		JButton btnSerializeNetwork 	= new JButton("Serialize Network");
		
		inputLayerSizeTF.setEditable		(false);
		inputLayerSizeTF.setFont			(new Font("Tahoma", Font.PLAIN, 20));
		inputLayerSizeTF.setColumns			(10);
		inputLayerSizeTF.setBounds			(203, 54, 55, 29);
		outputLayerSizeTF.setEditable		(false);
		outputLayerSizeTF.setFont			(new Font("Tahoma", Font.PLAIN, 20));
		outputLayerSizeTF.setColumns		(10);
		outputLayerSizeTF.setBounds			(470, 52, 55, 29);
		learningRateTF.setFont				(new Font("Tahoma", Font.PLAIN, 20));
		learningRateTF.setColumns			(10);
		learningRateTF.setBounds			(240, 111, 244, 29);
		momentumRateTF.setFont				(new Font("Tahoma", Font.PLAIN, 20));
		momentumRateTF.setColumns			(10);
		momentumRateTF.setBounds			(240, 151, 244, 29);
		epochsTF.setFont					(new Font("Tahoma", Font.PLAIN, 20));
		epochsTF.setColumns					(10);
		epochsTF.setBounds					(240, 196, 244, 29);
		trainingIterationsTF.setFont		(new Font("Tahoma", Font.PLAIN, 20));
		trainingIterationsTF.setColumns		(10);
		trainingIterationsTF.setBounds		(240, 236, 244, 29);
		trainingErrorTF.setEditable			(false);
		trainingErrorTF.setFont				(new Font("Tahoma", Font.PLAIN, 20));
		trainingErrorTF.setColumns			(10);
		trainingErrorTF.setBounds			(175, 387, 309, 29);
		errorThresholdTF.setFont			(new Font("Tahoma", Font.PLAIN, 20));
		errorThresholdTF.setColumns			(10);
		errorThresholdTF.setBounds			(240, 281, 244, 29);
		ffTF.setEditable					(false);
		ffTF.setFont						(new Font("Tahoma", Font.PLAIN, 20));
		ffTF.setBounds						(211, 559, 347, 37);
		ffTF.setColumns						(10);
		ttCB.setBounds						(175, 519, 383, 29);
		btnLoadTrainingData.setFont			(new Font("Tahoma", Font.PLAIN, 24));
		btnLoadTrainingData.setBounds		(10, 326, 246, 37);
		btnRun.setFont						(new Font("Tahoma", Font.PLAIN, 24));
		btnRun.setBounds					(288, 326, 196, 37);
		btnBack.setFont						(new Font("Tahoma", Font.PLAIN, 24));
		btnBack.setBounds					(469, 471, 89, 37);
		btnWriteNetworkToFile.setFont		(new Font("Tahoma", Font.PLAIN, 24));
		btnWriteNetworkToFile.setBounds		(10, 427, 292, 37);
		btnSerializeNetwork.setFont			(new Font("Tahoma", Font.PLAIN, 24));
		btnSerializeNetwork.setBounds		(312, 427, 246, 37);
		btnFeedForward.setFont				(new Font("Tahoma", Font.PLAIN, 24));
		btnFeedForward.setBounds			(10, 559, 191, 37);
		btnFeedForward.setEnabled			(false);
		
		btnLoadTrainingData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				trainingData = TrainingDataGenerator.genFromFile();
				btnFeedForward.setEnabled(false);
				ttCB.removeAllItems();
				if(trainingData == null) {
					lblFileNotFound.	setVisible(true);
					inputLayerSizeTF.	setText("");
					outputLayerSizeTF.	setText("");
					return;
				}
				else
					lblFileNotFound.	setVisible(false);
				inputLayerSizeTF.setText	(""+trainingData.getData().get(0).getInputs().columns);
				outputLayerSizeTF.setText	(""+trainingData.getData().get(0).getOutputs().columns);
				for(TrainingTuple tt : trainingData.getData())
					ttCB.addItem(tt);
			}
		});
		
		btnRun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(trainingData == null) {
					return;
				}
				try {
					int inputLayerSize 					= Integer.parseInt(inputLayerSizeTF.getText());
					ArrayList<Integer> hiddenLayerSizes = new ArrayList<Integer>();
					String[] hiddenLayerSizesStr 		= hiddenLayerSizesTF.getText().split("[,]+");
					for(String hiddenLayerSizeStr : hiddenLayerSizesStr)
						hiddenLayerSizes.add(Integer.parseInt(hiddenLayerSizeStr));
					int outputLayerSize = Integer.parseInt(outputLayerSizeTF.getText());
					
					int epochs 				= Integer.parseInt(epochsTF.getText());
					int trainingIterations 	= Integer.parseInt(trainingIterationsTF.getText());
					double errorThreshold 	= Double.parseDouble(errorThresholdTF.getText());
					double learningRate 	= Double.parseDouble(learningRateTF.getText());
					double momentumRate 	= Double.parseDouble(momentumRateTF.getText());
					
					NeuralNetworkParams NNparams 	= new NeuralNetworkParams(1.0, inputLayerSize, hiddenLayerSizes, outputLayerSize);
					NN 								= new NeuralNetwork(NNparams);
					
					SBPParams sbpParams 	= new SBPParams(epochs, trainingIterations, errorThreshold, learningRate, momentumRate);
					SBP sbp 				= new SBP(sbpParams);
					
					sbp.setTrainee	(NN);
					sbp.apply		(trainingData);
					
					trainingErrorTF.	setText(sbp.getError()+"");
					btnFeedForward.		setEnabled(true);
					lblInvalidInputs.	setText("");
				} catch(NumberFormatException e) {
					lblInvalidInputs.setText("Invalid Parameters");
					return;
				}
			}
		});
		
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				NNWindow window = new NNWindow();
				thisFrame.dispose();
				window.enable();
			}
		});
		
		btnWriteNetworkToFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(NN != null)
					NeuralNetworkIO.writeNetworkToFile(NN);
			}
		});
		
		btnSerializeNetwork.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(NN != null)
					NeuralNetworkIO.writeNetwork(NN);
			}
		});
		
		btnFeedForward.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TrainingTuple tt = (TrainingTuple)ttCB.getSelectedItem();
				DoubleMatrix output = NN.feedForward(tt.getInputs());
				ffTF.setText(""+output);
			}
		});
		
		//error labels
		lblInvalidInput.	setVisible(false);
		lblFileNotFound.	setVisible(false);
		lblInvalidInputs.	setVisible(false);
		
		//defaults
		hiddenLayerSizesTF.setText		("24,24");
		learningRateTF.setText			("0.0123");
		momentumRateTF.setText			("0.03");
		epochsTF.setText				("5");
		trainingIterationsTF.setText	("30000");
		errorThresholdTF.setText		("342222");
		
		contentPane.add(lblNewLabel);
		contentPane.add(lblExample);
		contentPane.add(lblInputLayerSizes);
		contentPane.add(lblOutputLayerSize);
		contentPane.add(lblInputOutput);
		contentPane.add(lblInvalidInput);
		contentPane.add(lblNewLabel_1);
		contentPane.add(lblMomentumRate);
		contentPane.add(lblEpochs);
		contentPane.add(lblTrainingIterations);
		contentPane.add(lblFileNotFound);
		contentPane.add(lblErrorThreshold);
		contentPane.add(lblTrainingError);
		contentPane.add(lblInvalidInputs);
		contentPane.add(lblwritingSerializing);
		contentPane.add(lblNetworktxtAndorNetworkser);
		contentPane.add(lblTrainingTuples);
		contentPane.add(inputLayerSizeTF);
		contentPane.add(outputLayerSizeTF);
		contentPane.add(learningRateTF);
		contentPane.add(momentumRateTF);
		contentPane.add(epochsTF);
		contentPane.add(trainingIterationsTF);
		contentPane.add(trainingErrorTF);
		contentPane.add(errorThresholdTF);
		contentPane.add(ffTF);
		contentPane.add(ttCB);
		contentPane.add(btnLoadTrainingData);
		contentPane.add(btnRun);
		contentPane.add(btnBack);
		contentPane.add(btnWriteNetworkToFile);
		contentPane.add(btnSerializeNetwork);
		contentPane.add(btnFeedForward);
	}
}
