package program;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import rubiks.*;
import search.BFSearch;

import java.awt.TextArea;
import java.util.ArrayList;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTextPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class CubeManipulationWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -28901547254239848L;
	private RubiksCube cube;
	private JFrame thisFrame = this;
	private JPanel contentPane;
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
	private JButton btnApplyAllMoves;
	private JButton btnApplyOneMove;
	private JTextPane recommendedMovesTextPane;
	private JTextField perturbDepthTextField;
	private JButton btnPerturb;
	private JLabel lblInvalidDepth;
	ArrayList<Move> recommendedMoves;
	private JTextField BFSearchTimeTextField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CubeManipulationWindow frame = new CubeManipulationWindow(null);
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
	
	//TODO change for perspective
	private String buildString(char[][] arr) {
		String rtnStr = "";
		for(int i=0; i<arr.length; i++) {
			for(int j=0; j<arr[i].length; j++) {
				rtnStr += arr[i][j]+" ";
			}
			rtnStr += "\n";
		}
		return rtnStr;
	}

	private void repaintCube(RubiksCube cube) {
		//display new cube
		face0Str = buildString(cube.getCube()[0]);
		face1Str = buildString(cube.getCube()[1]);
		face2Str = buildString(cube.getCube()[2]);
		face3Str = buildString(cube.getCube()[3]);
		face4Str = buildString(cube.getCube()[4]);
		face5Str = buildString(cube.getCube()[5]);
		face0TextArea.setText(face0Str);
		face1TextArea.setText(face1Str);
		face2TextArea.setText(face2Str);
		face3TextArea.setText(face3Str);
		face4TextArea.setText(face4Str);
		face5TextArea.setText(face5Str);
	}
	
	/**
	 * Create the frame.
	 */
	public CubeManipulationWindow(RubiksCube rubiksCube) {
		setTitle("Rubiks Cube Manipulation");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 862, 744);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		this.cube = rubiksCube;
		
		//generate moveset
		Move[] moveset = cube.genAllMoves();
		
		//displaying the cube
		face0Str = buildString(cube.getCube()[0]);
		face1Str = buildString(cube.getCube()[1]);
		face2Str = buildString(cube.getCube()[2]);
		face3Str = buildString(cube.getCube()[3]);
		face4Str = buildString(cube.getCube()[4]);
		face5Str = buildString(cube.getCube()[5]);
		
		face0TextArea = new TextArea();
		face0TextArea.setText(face0Str);
		face0TextArea.setFont(new Font("Consolas", Font.PLAIN, 20));
		face0TextArea.setEditable(false);
		face0TextArea.setBounds(36, 184, 150, 158);
		contentPane.add(face0TextArea);
		
		JButton btnBfsearch = new JButton("Breadth-First Search");
		btnBfsearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BFSearch bfSearch = new BFSearch();
				//search to get goal state
				double startTime = System.nanoTime();
				RubiksCube searchResult = (RubiksCube)bfSearch.search(cube, RubiksCube.createSolvedRubiksCube(cube.getSize()));
				double endTime = System.nanoTime();
				double duration = (endTime - startTime)/1000000000;
				if(searchResult == null)
					recommendedMovesTextPane.setText("Search did not\nfind a result");
				else {
					recommendedMoves = searchResult.traceMoves();
					for(Move move : recommendedMoves) {
						recommendedMovesTextPane.setText(recommendedMovesTextPane.getText()+move+"\n");
					}
					String pattern = "####.###";
					DecimalFormat decimalFormat = new DecimalFormat(pattern);
					BFSearchTimeTextField.setText(decimalFormat.format(duration)+"sec.");
				}
			}
		});
		btnBfsearch.setFont(new Font("Tahoma", Font.PLAIN, 25));
		btnBfsearch.setBounds(378, 436, 266, 46);
		contentPane.add(btnBfsearch);
		
		JButton btnASearch = new JButton("A* Search");
		btnASearch.setFont(new Font("Tahoma", Font.PLAIN, 25));
		btnASearch.setBounds(378, 374, 164, 46);
		contentPane.add(btnASearch);
		
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
				move.setCube(cube);
				move.apply();
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
		btnReset.setBounds(378, 20, 199, 63);
		contentPane.add(btnReset);
		
		//back button
		btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CreateCubeWindow window = new CreateCubeWindow();
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
		JLabel lblRecommendMoveSet = new JLabel("Recommended Moves From Search:");
		lblRecommendMoveSet.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblRecommendMoveSet.setBounds(378, 501, 436, 31);
		contentPane.add(lblRecommendMoveSet);
		JLabel lblPerterbDepth = new JLabel("Perterb Depth:");
		lblPerterbDepth.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblPerterbDepth.setBounds(504, 230, 199, 31);
		contentPane.add(lblPerterbDepth);
		//error msg
		lblInvalidDepth = new JLabel("invalid depth");
		lblInvalidDepth.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblInvalidDepth.setForeground(Color.RED);
		lblInvalidDepth.setBounds(527, 328, 75, 15);
		lblInvalidDepth.setVisible(false);
		contentPane.add(lblInvalidDepth);
		
		btnApplyAllMoves = new JButton("Apply all Moves");
		btnApplyAllMoves.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!recommendedMoves.isEmpty()) {
					for(Move move : recommendedMoves) {
						move.setCube(cube);
						move.apply();
						repaintCube(cube);
					}
					recommendedMovesTextPane.setText("");
				}
			}
		});
		btnApplyAllMoves.setFont(new Font("Tahoma", Font.PLAIN, 25));
		btnApplyAllMoves.setBounds(590, 617, 224, 53);
		contentPane.add(btnApplyAllMoves);
		
		btnApplyOneMove = new JButton("Apply one Move");
		btnApplyOneMove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!recommendedMoves.isEmpty()) {
					Move move = recommendedMoves.remove(0);
					move.setCube(cube);
					move.apply();
					repaintCube(cube);
					String textPaneContents = recommendedMovesTextPane.getText();
					recommendedMovesTextPane.setText("");
					String[] lines = textPaneContents.split("\n");
					for(int i=1; i<lines.length; i++) {
						recommendedMovesTextPane.setText(recommendedMovesTextPane.getText()+lines[i] + "\n");
					}
					if(recommendedMoves.isEmpty())
						recommendedMovesTextPane.setText("");
				}
			}
		});
		btnApplyOneMove.setFont(new Font("Tahoma", Font.PLAIN, 25));
		btnApplyOneMove.setBounds(590, 546, 224, 53);
		contentPane.add(btnApplyOneMove);
		
		recommendedMovesTextPane = new JTextPane();
		recommendedMovesTextPane.setEditable(false);
		recommendedMovesTextPane.setFont(new Font("Tahoma", Font.PLAIN, 15));
		recommendedMovesTextPane.setBounds(378, 545, 199, 125);
		contentPane.add(recommendedMovesTextPane);
		
		perturbDepthTextField = new JTextField();
		perturbDepthTextField.setFont(new Font("Tahoma", Font.PLAIN, 25));
		perturbDepthTextField.setBounds(504, 272, 116, 46);
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
						cube.perturb(depth);
						repaintCube(cube);
					} catch(IllegalDepthException exc) {
						lblInvalidDepth.setVisible(true);
					}
				}
			}
		});
		btnPerturb.setFont(new Font("Tahoma", Font.PLAIN, 25));
		btnPerturb.setBounds(630, 271, 184, 46);
		contentPane.add(btnPerturb);
		
		BFSearchTimeTextField = new JTextField();
		BFSearchTimeTextField.setEditable(false);
		BFSearchTimeTextField.setFont(new Font("Tahoma", Font.PLAIN, 25));
		BFSearchTimeTextField.setBounds(654, 436, 160, 46);
		contentPane.add(BFSearchTimeTextField);
		BFSearchTimeTextField.setColumns(10);
	}
}
