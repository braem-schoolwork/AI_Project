package program;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.jblas.DoubleMatrix;

import experimental_data.Experiment;
import experimental_data.Phase1Experiment;
import neural_network.NeuralNetwork;
import neural_network.NeuralNetworkIO;
import neural_network.NeuralNetworkParams;
import rubiks.IllegalDepthException;
import rubiks.Move;
import rubiks.Perturber;
import rubiks.RubiksCube;
import training_algorithms.SBP;
import training_algorithms.SBPParams;
import training_data.RubiksCubeTrainingDataGenerator;
import training_data.TrainingData;
import training_data.TrainingDataGenerator;
import javax.swing.JCheckBox;

public class RCNNWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 896981787739461357L;
	private JPanel contentPane;
	private RubiksCube cube;
	private JFrame thisFrame = this;
	private JTextField face0Illustration;
	private JTextField face1Illustration;
	private JTextField face2Illustration;
	private JTextField face3Illustration;
	private JTextField face4Illustration;
	private JTextField face5Illustration;
	private TextArea face0TextArea;
	private TextArea face1TextArea;
	private TextArea face2TextArea;
	private TextArea face3TextArea;
	private TextArea face4TextArea;
	private TextArea face5TextArea;
	private JButton btnApplyMove;
	private String face0Str;
	private String face1Str;
	private String face2Str;
	private String face3Str;
	private String face4Str;
	private String face5Str;
	private JButton btnReset;
	private JButton btnBack;
	private JComboBox<String> PerspectiveComboBox;
	private JTextField perturbDepthTextField;
	private JButton btnPerturb;
	private JLabel lblInvalidDepth;
	List<Move> recommendedMoves;
	
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
	private TrainingData trainingData = null;
	private JLabel lblInvalidInputs;
	private NeuralNetwork NN = null;
	private JTextField ffTF;
	private JButton btnFeedForward;
	private JButton btnGenerateRubiksCube;
	private JButton btnLoadTrainingData;
	private JLabel lblRecommendedMoveFrom;
	private JButton btnApplyRecommendedMove;
	private JLabel lblFileNotFound;
	private JCheckBox chckbxLoadedTrainingData;
	private JCheckBox chckbxTrainedNetwork;
	private JLabel lblSetupSteps;
	private JCheckBox chckbxGeneratedTrainingData;
	
	private Move recommendedMove = null;
	private JButton btnLoadNetworkFrom;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RCNNWindow frame = new RCNNWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private String buildString(byte[][] arr, int face, String perspective) {
		String rtnStr = "";
		for(int i=0; i<arr.length; i++) {
			for(int j=0; j<arr[i].length; j++) {
				if(perspective.equals("From Top Right Corner"))
					rtnStr += (char)arr[i][j]+" ";
				else
					if(face == 0 || face == 4)
						rtnStr += (char)arr[i][this.cube.getSize()-1-j] + " ";
					else if(face == 2)
						rtnStr += (char)arr[this.cube.getSize()-1-i][j] + " ";
					else
						rtnStr += (char)arr[i][j]+" ";
			}
			rtnStr += "\n";
		}
		return rtnStr;
	}
	
	private void repaintCube(RubiksCube cube) {
		//display new cube
		face0Str = buildString(cube.getCube()[0], 0, (String)PerspectiveComboBox.getSelectedItem());
		face1Str = buildString(cube.getCube()[1], 1, (String)PerspectiveComboBox.getSelectedItem());
		face2Str = buildString(cube.getCube()[2], 2, (String)PerspectiveComboBox.getSelectedItem());
		face3Str = buildString(cube.getCube()[3], 3, (String)PerspectiveComboBox.getSelectedItem());
		face4Str = buildString(cube.getCube()[4], 4, (String)PerspectiveComboBox.getSelectedItem());
		face5Str = buildString(cube.getCube()[5], 5, (String)PerspectiveComboBox.getSelectedItem());
		face0TextArea.setText(face0Str);
		face1TextArea.setText(face1Str);
		face2TextArea.setText(face2Str);
		face3TextArea.setText(face3Str);
		face4TextArea.setText(face4Str);
		face5TextArea.setText(face5Str);
	}
	
	public void enable() {
		this.setVisible(true);
	}
	
	/**
	 * Create the frame.
	 */
	public RCNNWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1418, 804);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		PerspectiveComboBox = new JComboBox<String>();
		PerspectiveComboBox.setFont(new Font("Tahoma", Font.PLAIN, 20));
		PerspectiveComboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"From Top Right Corner", "Looking Directly at Face"}));
		PerspectiveComboBox.setSelectedIndex(0);
		PerspectiveComboBox.setBounds(504, 204, 310, 39);
		contentPane.add(PerspectiveComboBox);
		
		this.cube = new RubiksCube(3);
		
		//generate moveset
		Move[] moveset = cube.getMoveSet();
		
		//displaying the cube
		face0Str = buildString(cube.getCube()[0], 0, (String)PerspectiveComboBox.getSelectedItem());
		face1Str = buildString(cube.getCube()[1], 1, (String)PerspectiveComboBox.getSelectedItem());
		face2Str = buildString(cube.getCube()[2], 2, (String)PerspectiveComboBox.getSelectedItem());
		face3Str = buildString(cube.getCube()[3], 3, (String)PerspectiveComboBox.getSelectedItem());
		face4Str = buildString(cube.getCube()[4], 4, (String)PerspectiveComboBox.getSelectedItem());
		face5Str = buildString(cube.getCube()[5], 5, (String)PerspectiveComboBox.getSelectedItem());
		
		face0TextArea = new TextArea();
		face0TextArea.setText(face0Str);
		face0TextArea.setFont(new Font("Consolas", Font.PLAIN, 20));
		face0TextArea.setEditable(false);
		face0TextArea.setBounds(36, 184, 150, 158);
		contentPane.add(face0TextArea);
		
		face1TextArea = new TextArea();
		face1TextArea.setText(face1Str);
		face1TextArea.setFont(new Font("Consolas", Font.PLAIN, 20));
		face1TextArea.setEditable(false);
		face1TextArea.setBounds(348, 184, 150, 158);
		contentPane.add(face1TextArea);
		
		face2TextArea = new TextArea();
		face2TextArea.setText(face2Str);
		face2TextArea.setFont(new Font("Consolas", Font.PLAIN, 20));
		face2TextArea.setEditable(false);
		face2TextArea.setBounds(192, 512, 150, 158);
		contentPane.add(face2TextArea);
		
		face3TextArea = new TextArea();
		face3TextArea.setText(face3Str);
		face3TextArea.setFont(new Font("Consolas", Font.PLAIN, 20));
		face3TextArea.setEditable(false);
		face3TextArea.setBounds(192, 184, 150, 158);
		contentPane.add(face3TextArea);
		
		face4TextArea = new TextArea();
		face4TextArea.setText(face4Str);
		face4TextArea.setFont(new Font("Consolas", Font.PLAIN, 20));
		face4TextArea.setEditable(false);
		face4TextArea.setBounds(192, 20, 150, 158);
		contentPane.add(face4TextArea);
		
		face5TextArea = new TextArea();
		face5TextArea.setText(face5Str);
		face5TextArea.setFont(new Font("Consolas", Font.PLAIN, 20));
		face5TextArea.setEditable(false);
		face5TextArea.setBounds(192, 348, 150, 158);
		contentPane.add(face5TextArea);
		
		face0Illustration = new JTextField();
		face0Illustration.setFont(new Font("Tahoma", Font.PLAIN, 25));
		face0Illustration.setEditable(false);
		face0Illustration.setText("0");
		face0Illustration.setBounds(62, 501, 24, 31);
		contentPane.add(face0Illustration);
		face0Illustration.setColumns(10);
		
		face1Illustration = new JTextField();
		face1Illustration.setText("3");
		face1Illustration.setFont(new Font("Tahoma", Font.PLAIN, 25));
		face1Illustration.setEditable(false);
		face1Illustration.setColumns(10);
		face1Illustration.setBounds(88, 501, 24, 31);
		contentPane.add(face1Illustration);
		
		face2Illustration = new JTextField();
		face2Illustration.setText("4");
		face2Illustration.setFont(new Font("Tahoma", Font.PLAIN, 25));
		face2Illustration.setEditable(false);
		face2Illustration.setColumns(10);
		face2Illustration.setBounds(88, 467, 24, 31);
		contentPane.add(face2Illustration);
		
		face3Illustration = new JTextField();
		face3Illustration.setText("1");
		face3Illustration.setFont(new Font("Tahoma", Font.PLAIN, 25));
		face3Illustration.setEditable(false);
		face3Illustration.setColumns(10);
		face3Illustration.setBounds(114, 501, 24, 31);
		contentPane.add(face3Illustration);
		
		face4Illustration = new JTextField();
		face4Illustration.setText("2");
		face4Illustration.setFont(new Font("Tahoma", Font.PLAIN, 25));
		face4Illustration.setEditable(false);
		face4Illustration.setColumns(10);
		face4Illustration.setBounds(88, 568, 24, 31);
		contentPane.add(face4Illustration);
		
		face5Illustration = new JTextField();
		face5Illustration.setText("5");
		face5Illustration.setFont(new Font("Tahoma", Font.PLAIN, 25));
		face5Illustration.setEditable(false);
		face5Illustration.setColumns(10);
		face5Illustration.setBounds(88, 534, 24, 31);
		contentPane.add(face5Illustration);
		
		//drop down menu with all of the moves
		JComboBox<Move> MoveSetDropDown = new JComboBox<Move>();
		for(Move move : moveset) { //add all the moves
			MoveSetDropDown.addItem(move);
		}
		MoveSetDropDown.setSelectedIndex(0);
		MoveSetDropDown.setBounds(615, 52, 199, 31);
		contentPane.add(MoveSetDropDown);
		
		//apply the selected move from the move set
		btnApplyMove = new JButton("Apply Move");
		btnApplyMove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { //want to apply a move
				Move move = (Move)MoveSetDropDown.getSelectedItem(); //get the move they want to do
				move.apply(cube);
				repaintCube(cube); //redraw cube
			}
		});
		btnApplyMove.setFont(new Font("Tahoma", Font.PLAIN, 25));
		btnApplyMove.setBounds(615, 94, 199, 63);
		contentPane.add(btnApplyMove);
		
		//reset the cube button
		btnReset = new JButton("Reset Cube");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RubiksCube newCube = new RubiksCube(cube.getSize());
				cube = newCube;
				repaintCube(cube);
			}
		});
		btnReset.setFont(new Font("Tahoma", Font.PLAIN, 25));
		btnReset.setBounds(388, 20, 199, 63);
		contentPane.add(btnReset);
		
		//back button
		btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				NNWindow window = new NNWindow();
				thisFrame.dispose();
				window.enable();
			}
		});
		btnBack.setFont(new Font("Tahoma", Font.PLAIN, 25));
		btnBack.setBounds(388, 94, 177, 63);
		contentPane.add(btnBack);
		
		//labels
		JLabel lblMoveSet = new JLabel("Move Set:");
		lblMoveSet.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblMoveSet.setBounds(615, 20, 199, 31);
		contentPane.add(lblMoveSet);
		JLabel lblOrginization = new JLabel("Orginization:");
		lblOrginization.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblOrginization.setBounds(36, 436, 147, 31);
		contentPane.add(lblOrginization);
		JLabel lblFace = new JLabel("Face");
		lblFace.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblFace.setBounds(80, 402, 58, 31);
		contentPane.add(lblFace);
		JLabel lblIsBottom = new JLabel("*2 is bottom face");
		lblIsBottom.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblIsBottom.setBounds(31, 621, 141, 21);
		contentPane.add(lblIsBottom);
		JLabel lblIsTop = new JLabel("*3 is top face");
		lblIsTop.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblIsTop.setBounds(31, 644, 128, 21);
		contentPane.add(lblIsTop);
		JLabel lblPerterbDepth = new JLabel("Perterb Depth:");
		lblPerterbDepth.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblPerterbDepth.setBounds(504, 254, 199, 31);
		contentPane.add(lblPerterbDepth);
		JLabel lblPerspective = new JLabel("Perspective:");
		lblPerspective.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblPerspective.setBounds(504, 168, 141, 31);
		contentPane.add(lblPerspective);
		//error msg
		lblInvalidDepth = new JLabel("invalid depth");
		lblInvalidDepth.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblInvalidDepth.setForeground(Color.RED);
		lblInvalidDepth.setBounds(528, 347, 75, 15);
		lblInvalidDepth.setVisible(false);
		contentPane.add(lblInvalidDepth);
		
		perturbDepthTextField = new JTextField();
		perturbDepthTextField.setFont(new Font("Tahoma", Font.PLAIN, 25));
		perturbDepthTextField.setBounds(504, 296, 116, 46);
		contentPane.add(perturbDepthTextField);
		perturbDepthTextField.setColumns(10);
		
		btnPerturb = new JButton("Perturb");
		btnPerturb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int depth = 0;
				boolean isValid = false;
				try {
					depth = Integer.parseInt(perturbDepthTextField.getText());
					isValid = true;
				} catch(Exception exc) {
					lblInvalidDepth.setVisible(true);
				}
				if(isValid) {
					lblInvalidDepth.setVisible(false);
					try {
						Perturber.perturb(depth, cube);
						repaintCube(cube);
					} catch(IllegalDepthException exc) {
						lblInvalidDepth.setVisible(true);
					}
				}
			}
		});
		btnPerturb.setFont(new Font("Tahoma", Font.PLAIN, 25));
		btnPerturb.setBounds(630, 296, 184, 46);
		contentPane.add(btnPerturb);
		
		JButton btnSwitchPerspective = new JButton("Swtich");
		btnSwitchPerspective.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				repaintCube(cube);
			}
		});
		btnSwitchPerspective.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnSwitchPerspective.setBounds(649, 170, 165, 31);
		contentPane.add(btnSwitchPerspective);
		
		JLabel lblIsFront = new JLabel("*5 is front face");
		lblIsFront.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblIsFront.setBounds(31, 665, 128, 21);
		contentPane.add(lblIsFront);
		
		JLabel lblIsBack = new JLabel("*4 is back face");
		lblIsBack.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblIsBack.setBounds(31, 686, 128, 21);
		contentPane.add(lblIsBack);
		
		JLabel lblIsLeft = new JLabel("*0 is left face");
		lblIsLeft.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblIsLeft.setBounds(169, 676, 128, 21);
		contentPane.add(lblIsLeft);
		
		JLabel lblIsRight = new JLabel("*1 is right face");
		lblIsRight.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblIsRight.setBounds(169, 698, 116, 21);
		contentPane.add(lblIsRight);
		
		hiddenLayerSizesTF = new JTextField();
		hiddenLayerSizesTF.setFont(new Font("Tahoma", Font.PLAIN, 20));
		hiddenLayerSizesTF.setBounds(1044, 168, 228, 29);
		contentPane.add(hiddenLayerSizesTF);
		hiddenLayerSizesTF.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Hidden Layer Sizes:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblNewLabel.setBounds(824, 168, 220, 29);
		contentPane.add(lblNewLabel);
		
		JLabel lblExample = new JLabel("example: 13,2,5,7");
		lblExample.setBounds(1275, 182, 130, 14);
		contentPane.add(lblExample);
		
		JLabel lblInputLayerSizes = new JLabel("Input Layer Size:");
		lblInputLayerSizes.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblInputLayerSizes.setBounds(824, 204, 220, 29);
		contentPane.add(lblInputLayerSizes);
		
		inputLayerSizeTF = new JTextField();
		inputLayerSizeTF.setEditable(false);
		inputLayerSizeTF.setFont(new Font("Tahoma", Font.PLAIN, 20));
		inputLayerSizeTF.setColumns(10);
		inputLayerSizeTF.setBounds(1017, 204, 55, 29);
		contentPane.add(inputLayerSizeTF);
		
		JLabel lblOutputLayerSize = new JLabel("Output Layer Size:");
		lblOutputLayerSize.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblOutputLayerSize.setBounds(1082, 204, 220, 29);
		contentPane.add(lblOutputLayerSize);
		
		outputLayerSizeTF = new JTextField();
		outputLayerSizeTF.setEditable(false);
		outputLayerSizeTF.setFont(new Font("Tahoma", Font.PLAIN, 20));
		outputLayerSizeTF.setColumns(10);
		outputLayerSizeTF.setBounds(1285, 206, 55, 29);
		contentPane.add(outputLayerSizeTF);
		
		JLabel lblInputOutput = new JLabel("*input & output layer sizes are determined by the training data loaded");
		lblInputOutput.setBounds(824, 236, 548, 14);
		contentPane.add(lblInputOutput);
		
		lblInvalidInput = new JLabel("invalid input");
		lblInvalidInput.setForeground(Color.RED);
		lblInvalidInput.setBounds(1276, 168, 96, 14);
		contentPane.add(lblInvalidInput);
		
		JLabel lblNewLabel_1 = new JLabel("Learning Rate:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblNewLabel_1.setBounds(824, 254, 163, 29);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblMomentumRate = new JLabel("Momentum Rate:");
		lblMomentumRate.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblMomentumRate.setBounds(824, 296, 191, 29);
		contentPane.add(lblMomentumRate);
		
		JLabel lblEpochs = new JLabel("Epochs:");
		lblEpochs.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblEpochs.setBounds(824, 333, 89, 29);
		contentPane.add(lblEpochs);
		
		JLabel lblTrainingIterations = new JLabel("Training Iterations:");
		lblTrainingIterations.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblTrainingIterations.setBounds(824, 373, 220, 29);
		contentPane.add(lblTrainingIterations);
		
		learningRateTF = new JTextField();
		learningRateTF.setFont(new Font("Tahoma", Font.PLAIN, 20));
		learningRateTF.setColumns(10);
		learningRateTF.setBounds(1054, 254, 244, 29);
		contentPane.add(learningRateTF);
		
		momentumRateTF = new JTextField();
		momentumRateTF.setFont(new Font("Tahoma", Font.PLAIN, 20));
		momentumRateTF.setColumns(10);
		momentumRateTF.setBounds(1054, 296, 244, 29);
		contentPane.add(momentumRateTF);
		
		epochsTF = new JTextField();
		epochsTF.setFont(new Font("Tahoma", Font.PLAIN, 20));
		epochsTF.setColumns(10);
		epochsTF.setBounds(1054, 333, 244, 29);
		contentPane.add(epochsTF);
		
		trainingIterationsTF = new JTextField();
		trainingIterationsTF.setFont(new Font("Tahoma", Font.PLAIN, 20));
		trainingIterationsTF.setColumns(10);
		trainingIterationsTF.setBounds(1054, 373, 244, 29);
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
					chckbxTrainedNetwork.setSelected(true);
				} catch(NumberFormatException e) {
					lblInvalidInputs.setText("Invalid Parameters");
					return;
				}
			}
		});
		btnRun.setFont(new Font("Tahoma", Font.PLAIN, 25));
		btnRun.setBounds(819, 456, 310, 53);
		contentPane.add(btnRun);
		
		JLabel lblErrorThreshold = new JLabel("Error Threshold:");
		lblErrorThreshold.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblErrorThreshold.setBounds(824, 413, 183, 29);
		contentPane.add(lblErrorThreshold);
		
		errorThresholdTF = new JTextField();
		errorThresholdTF.setFont(new Font("Tahoma", Font.PLAIN, 20));
		errorThresholdTF.setColumns(10);
		errorThresholdTF.setBounds(1054, 413, 244, 29);
		contentPane.add(errorThresholdTF);
		
		JLabel lblTrainingError = new JLabel("Training Error:");
		lblTrainingError.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblTrainingError.setBounds(492, 576, 163, 29);
		contentPane.add(lblTrainingError);
		
		trainingErrorTF = new JTextField();
		trainingErrorTF.setEditable(false);
		trainingErrorTF.setFont(new Font("Tahoma", Font.PLAIN, 20));
		trainingErrorTF.setColumns(10);
		trainingErrorTF.setBounds(657, 576, 683, 29);
		contentPane.add(trainingErrorTF);
		
		JButton btnWriteNetworkToFile = new JButton("Write Network to .txt");
		btnWriteNetworkToFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(NN != null)
					NeuralNetworkIO.writeNetworkToFile(NN);
			}
		});
		btnWriteNetworkToFile.setFont(new Font("Tahoma", Font.PLAIN, 24));
		btnWriteNetworkToFile.setBounds(579, 612, 292, 37);
		contentPane.add(btnWriteNetworkToFile);
		
		JButton btnSerializeNetwork = new JButton("Serialize Network");
		btnSerializeNetwork.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(NN != null)
					NeuralNetworkIO.writeNetwork(NN);
			}
		});
		btnSerializeNetwork.setFont(new Font("Tahoma", Font.PLAIN, 24));
		btnSerializeNetwork.setBounds(883, 612, 246, 37);
		contentPane.add(btnSerializeNetwork);
		
		lblInvalidInputs = new JLabel("invalid parameters");
		lblInvalidInputs.setForeground(Color.RED);
		lblInvalidInputs.setBounds(1129, 478, 196, 20);
		contentPane.add(lblInvalidInputs);
		
		JLabel lblwritingSerializing = new JLabel("*Writing & Serializing Networks will overwrite");
		lblwritingSerializing.setBounds(579, 649, 338, 20);
		contentPane.add(lblwritingSerializing);
		
		JLabel lblNetworktxtAndorNetworkser = new JLabel("Network.txt and/or Network.ser");
		lblNetworktxtAndorNetworkser.setBounds(579, 666, 338, 20);
		contentPane.add(lblNetworktxtAndorNetworkser);
		
		btnFeedForward = new JButton("Feed Forward");
		btnFeedForward.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DoubleMatrix inputVector = RubiksCubeTrainingDataGenerator.rubiksCubeToInputVector(cube);
				DoubleMatrix outputVector = NN.feedForward(inputVector);
				recommendedMove = RubiksCubeTrainingDataGenerator.outputVectorToMove(outputVector);
				ffTF.setText(""+recommendedMove);
			}
		});
		btnFeedForward.setFont(new Font("Tahoma", Font.PLAIN, 25));
		btnFeedForward.setBounds(348, 373, 466, 51);
		contentPane.add(btnFeedForward);
		btnFeedForward.setEnabled(false);
		
		ffTF = new JTextField();
		ffTF.setEditable(false);
		ffTF.setFont(new Font("Tahoma", Font.PLAIN, 20));
		ffTF.setBounds(348, 470, 461, 39);
		contentPane.add(ffTF);
		ffTF.setColumns(10);
		
		btnGenerateRubiksCube = new JButton("Generate Rubik's Cube Training Data");
		btnGenerateRubiksCube.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Experiment exp = new Phase1Experiment();
				exp.runExperiment(".csv");
				chckbxGeneratedTrainingData.setSelected(true);
			}
		});
		btnGenerateRubiksCube.setFont(new Font("Tahoma", Font.PLAIN, 25));
		btnGenerateRubiksCube.setBounds(824, 20, 486, 76);
		contentPane.add(btnGenerateRubiksCube);
		
		btnLoadTrainingData = new JButton("Load Training Data");
		btnLoadTrainingData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				trainingData = TrainingDataGenerator.genFromFile();
				btnFeedForward.setEnabled(false);
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
				chckbxLoadedTrainingData.setSelected(true);
				chckbxGeneratedTrainingData.setSelected(true);
			}
		});
		
		lblFileNotFound = new JLabel("File Not Found");
		lblFileNotFound.setForeground(Color.RED);
		lblFileNotFound.setBounds(10, 362, 191, 14);
		contentPane.add(lblFileNotFound);
		
		btnLoadTrainingData.setFont(new Font("Tahoma", Font.PLAIN, 25));
		btnLoadTrainingData.setBounds(824, 102, 486, 63);
		contentPane.add(btnLoadTrainingData);
		
		lblRecommendedMoveFrom = new JLabel("Recommended Move from Network:");
		lblRecommendedMoveFrom.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblRecommendedMoveFrom.setBounds(348, 436, 439, 31);
		contentPane.add(lblRecommendedMoveFrom);
		
		btnApplyRecommendedMove = new JButton("Apply Recommended Move");
		btnApplyRecommendedMove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(recommendedMove != null) {
					recommendedMove.apply(cube);
					ffTF.setText("");
					repaintCube(cube);
				}
			}
		});
		btnApplyRecommendedMove.setFont(new Font("Tahoma", Font.PLAIN, 25));
		btnApplyRecommendedMove.setBounds(348, 512, 461, 53);
		contentPane.add(btnApplyRecommendedMove);
		
		chckbxGeneratedTrainingData = new JCheckBox("Generated Training Data");
		chckbxGeneratedTrainingData.setEnabled(false);
		chckbxGeneratedTrainingData.setBounds(348, 623, 217, 23);
		contentPane.add(chckbxGeneratedTrainingData);
		
		chckbxLoadedTrainingData = new JCheckBox("Loaded Training Data From File");
		chckbxLoadedTrainingData.setEnabled(false);
		chckbxLoadedTrainingData.setBounds(348, 646, 217, 23);
		contentPane.add(chckbxLoadedTrainingData);
		
		chckbxTrainedNetwork = new JCheckBox("Trained Network");
		chckbxTrainedNetwork.setEnabled(false);
		chckbxTrainedNetwork.setBounds(348, 667, 121, 23);
		contentPane.add(chckbxTrainedNetwork);
		
		lblSetupSteps = new JLabel("Setup Steps");
		lblSetupSteps.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblSetupSteps.setBounds(348, 596, 195, 31);
		contentPane.add(lblSetupSteps);
		
		btnLoadNetworkFrom = new JButton("Load Network From File");
		btnLoadNetworkFrom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				NN = NeuralNetworkIO.readNetwork();
				if(NN != null) {
					chckbxTrainedNetwork.setSelected(true);
					chckbxGeneratedTrainingData.setSelected(true);
					chckbxLoadedTrainingData.setSelected(true);
					btnFeedForward.setEnabled(true);
					trainingErrorTF.setText(""+NN.getError());
				}
			}
		});
		btnLoadNetworkFrom.setFont(new Font("Tahoma", Font.PLAIN, 25));
		btnLoadNetworkFrom.setBounds(819, 512, 397, 53);
		contentPane.add(btnLoadNetworkFrom);
		
		lblInvalidInput.setVisible(false);
		lblInvalidInputs.setVisible(false);
		lblFileNotFound.setVisible(false);
	}
}
