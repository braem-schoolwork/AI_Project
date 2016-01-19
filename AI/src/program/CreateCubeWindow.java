package program;

import java.awt.EventQueue;


import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import rubiks.*;

public class CreateCubeWindow {

	private RubiksCube rubiksCube;
	private JFrame frmCreateRubiksCube;
	private JTextField textField;
	
	public RubiksCube getCube() {
		return rubiksCube;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CreateCubeWindow window = new CreateCubeWindow();
					window.frmCreateRubiksCube.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public CreateCubeWindow() {
		initialize();
	}
	
	public void enable() {
		frmCreateRubiksCube.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmCreateRubiksCube = new JFrame();
		frmCreateRubiksCube.setTitle("Create Rubik's Cube");
		frmCreateRubiksCube.setBounds(100, 100, 321, 207);
		frmCreateRubiksCube.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmCreateRubiksCube.getContentPane().setLayout(null);
		
		JLabel lblInvalidSize = new JLabel("invalid size");
		lblInvalidSize.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblInvalidSize.setForeground(Color.RED);
		lblInvalidSize.setBounds(41, 107, 83, 12);
		frmCreateRubiksCube.getContentPane().add(lblInvalidSize);
		lblInvalidSize.setVisible(false);
		
		textField = new JTextField();
		textField.setFont(new Font("Tahoma", Font.PLAIN, 20));
		textField.setBounds(10, 56, 148, 42);
		frmCreateRubiksCube.getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblEnterASize = new JLabel("enter a size:");
		lblEnterASize.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblEnterASize.setBounds(10, 11, 146, 42);
		frmCreateRubiksCube.getContentPane().add(lblEnterASize);
		
		JButton btnCreate = new JButton("Create");
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int size = 0;
				boolean isValid = false;
				try {
					size = Integer.parseInt(textField.getText());
					isValid = true;
				} catch(Exception e) {
					lblInvalidSize.setVisible(true);
				}
				if(isValid) {
					try {
						rubiksCube = new RubiksCube(size);
						CubeManipulationWindow window = new CubeManipulationWindow(rubiksCube);
						frmCreateRubiksCube.dispose();
						window.enable();
						//TODO open new window
					} catch(IllegalSizeException e) {
						lblInvalidSize.setVisible(true);
					}
				}
			}
		});
		btnCreate.setFont(new Font("Tahoma", Font.PLAIN, 30));
		btnCreate.setBounds(168, 11, 127, 87);
		frmCreateRubiksCube.getContentPane().add(btnCreate);
		
		JButton btnBack = new JButton("back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ChoiceWindow window = new ChoiceWindow();
				frmCreateRubiksCube.dispose();
				window.enable();
			}
		});
		btnBack.setFont(new Font("Tahoma", Font.PLAIN, 25));
		btnBack.setBounds(208, 106, 87, 37);
		frmCreateRubiksCube.getContentPane().add(btnBack);
	}
}
