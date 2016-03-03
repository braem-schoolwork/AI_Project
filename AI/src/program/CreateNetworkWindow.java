package program;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CreateNetworkWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4409889890487667466L;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField learningRateTF;
	private JTextField momentumRateTF;
	private JTextField weightDecayTF;
	private JTextField epochsTF;
	private JTextField trainingIterationsTF;
	private JTextField biasValTF;
	private JTextField AvalTF;
	private JTextField errorThresholdTF;
	private JTextField trainingErrorTF;

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
		setBounds(100, 100, 703, 616);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textField = new JTextField();
		textField.setFont(new Font("Tahoma", Font.PLAIN, 24));
		textField.setBounds(230, 14, 228, 29);
		contentPane.add(textField);
		textField.setColumns(10);
		
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
		
		textField_1 = new JTextField();
		textField_1.setEditable(false);
		textField_1.setFont(new Font("Tahoma", Font.PLAIN, 24));
		textField_1.setColumns(10);
		textField_1.setBounds(203, 54, 75, 29);
		contentPane.add(textField_1);
		
		JLabel lblOutputLayerSize = new JLabel("Output Layer Size:");
		lblOutputLayerSize.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblOutputLayerSize.setBounds(288, 54, 220, 29);
		contentPane.add(lblOutputLayerSize);
		
		textField_2 = new JTextField();
		textField_2.setEditable(false);
		textField_2.setFont(new Font("Tahoma", Font.PLAIN, 24));
		textField_2.setColumns(10);
		textField_2.setBounds(500, 52, 75, 29);
		contentPane.add(textField_2);
		
		JLabel lblInputOutput = new JLabel("*input & output layer sizes are determined by the training data loaded");
		lblInputOutput.setBounds(10, 86, 409, 14);
		contentPane.add(lblInputOutput);
		
		JButton btnLoadTrainingData = new JButton("Load Training Data");
		btnLoadTrainingData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnLoadTrainingData.setFont(new Font("Tahoma", Font.PLAIN, 24));
		btnLoadTrainingData.setBounds(10, 391, 246, 37);
		contentPane.add(btnLoadTrainingData);
		
		JLabel lblInvalidInput = new JLabel("invalid input");
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
		
		JLabel lblWeightDecayRate = new JLabel("Weight Decay Rate:");
		lblWeightDecayRate.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblWeightDecayRate.setBounds(10, 191, 220, 29);
		contentPane.add(lblWeightDecayRate);
		
		JLabel lblEpochs = new JLabel("Epochs:");
		lblEpochs.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblEpochs.setBounds(10, 231, 89, 29);
		contentPane.add(lblEpochs);
		
		JLabel lblTrainingIterations = new JLabel("Training Iterations:");
		lblTrainingIterations.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblTrainingIterations.setBounds(10, 271, 220, 29);
		contentPane.add(lblTrainingIterations);
		
		learningRateTF = new JTextField();
		learningRateTF.setFont(new Font("Tahoma", Font.PLAIN, 24));
		learningRateTF.setColumns(10);
		learningRateTF.setBounds(240, 111, 244, 29);
		contentPane.add(learningRateTF);
		
		momentumRateTF = new JTextField();
		momentumRateTF.setFont(new Font("Tahoma", Font.PLAIN, 24));
		momentumRateTF.setColumns(10);
		momentumRateTF.setBounds(240, 151, 244, 29);
		contentPane.add(momentumRateTF);
		
		weightDecayTF = new JTextField();
		weightDecayTF.setFont(new Font("Tahoma", Font.PLAIN, 24));
		weightDecayTF.setColumns(10);
		weightDecayTF.setBounds(240, 191, 244, 29);
		contentPane.add(weightDecayTF);
		
		epochsTF = new JTextField();
		epochsTF.setFont(new Font("Tahoma", Font.PLAIN, 24));
		epochsTF.setColumns(10);
		epochsTF.setBounds(240, 231, 244, 29);
		contentPane.add(epochsTF);
		
		trainingIterationsTF = new JTextField();
		trainingIterationsTF.setFont(new Font("Tahoma", Font.PLAIN, 24));
		trainingIterationsTF.setColumns(10);
		trainingIterationsTF.setBounds(240, 271, 244, 29);
		contentPane.add(trainingIterationsTF);
		
		JButton btnRun = new JButton("Train Network");
		btnRun.setFont(new Font("Tahoma", Font.PLAIN, 24));
		btnRun.setBounds(288, 391, 196, 37);
		contentPane.add(btnRun);
		
		JLabel lblFileNotFound = new JLabel("File Not Found");
		lblFileNotFound.setForeground(Color.RED);
		lblFileNotFound.setBounds(10, 427, 96, 14);
		contentPane.add(lblFileNotFound);
		
		JLabel lblAValue = new JLabel("A value:");
		lblAValue.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblAValue.setBounds(10, 311, 89, 29);
		contentPane.add(lblAValue);
		
		JLabel lblBiasValue = new JLabel("bias value:");
		lblBiasValue.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblBiasValue.setBounds(238, 311, 122, 29);
		contentPane.add(lblBiasValue);
		
		biasValTF = new JTextField();
		biasValTF.setFont(new Font("Tahoma", Font.PLAIN, 24));
		biasValTF.setColumns(10);
		biasValTF.setBounds(362, 311, 122, 29);
		contentPane.add(biasValTF);
		
		AvalTF = new JTextField();
		AvalTF.setFont(new Font("Tahoma", Font.PLAIN, 24));
		AvalTF.setColumns(10);
		AvalTF.setBounds(109, 311, 121, 29);
		contentPane.add(AvalTF);
		
		JLabel lblErrorThreshold = new JLabel("Error Threshold:");
		lblErrorThreshold.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblErrorThreshold.setBounds(10, 351, 183, 29);
		contentPane.add(lblErrorThreshold);
		
		errorThresholdTF = new JTextField();
		errorThresholdTF.setFont(new Font("Tahoma", Font.PLAIN, 24));
		errorThresholdTF.setColumns(10);
		errorThresholdTF.setBounds(240, 351, 244, 29);
		contentPane.add(errorThresholdTF);
		
		JButton btnBack = new JButton("back");
		btnBack.setFont(new Font("Tahoma", Font.PLAIN, 24));
		btnBack.setBounds(560, 10, 89, 37);
		contentPane.add(btnBack);
		
		JLabel lblTrainingError = new JLabel("Training Error:");
		lblTrainingError.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblTrainingError.setBounds(10, 452, 163, 29);
		contentPane.add(lblTrainingError);
		
		trainingErrorTF = new JTextField();
		trainingErrorTF.setFont(new Font("Tahoma", Font.PLAIN, 24));
		trainingErrorTF.setColumns(10);
		trainingErrorTF.setBounds(175, 452, 309, 29);
		contentPane.add(trainingErrorTF);
		
		JButton btnWriteNetworkTo = new JButton("Write Network To File");
		btnWriteNetworkTo.setFont(new Font("Tahoma", Font.PLAIN, 24));
		btnWriteNetworkTo.setBounds(10, 492, 292, 37);
		contentPane.add(btnWriteNetworkTo);
		
		JButton btnSerializeNetwork = new JButton("Serialize Network");
		btnSerializeNetwork.setFont(new Font("Tahoma", Font.PLAIN, 24));
		btnSerializeNetwork.setBounds(312, 492, 246, 37);
		contentPane.add(btnSerializeNetwork);
	}
}
