package program;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import rubiks.*;

/**
 * Window to specify the cube size for the cube manipulation window.
 * 
 * @author Braemen Stoltz
 * @version 1.0
 */
public class CreateCubeWindow
{
	private RubiksCube 	rubiksCube 			= null;
	private JFrame 		frmCreateRubiksCube = new JFrame();
	private JTextField 	textField 			= new JTextField();

	public CreateCubeWindow() { initialize(); }
	
	public void 		enable() 	{ frmCreateRubiksCube.setVisible(true); }
	public RubiksCube 	getCube() 	{ return rubiksCube; }

	private void initialize() {
		frmCreateRubiksCube.setTitle					("Create Rubik's Cube");
		frmCreateRubiksCube.setBounds					(100, 100, 340, 210);
		frmCreateRubiksCube.setDefaultCloseOperation	(JFrame.EXIT_ON_CLOSE);
		frmCreateRubiksCube.getContentPane().setLayout	(null);
		
		JLabel 	lblInvalidSize 	= new JLabel("invalid size");
		JLabel 	lblEnterASize 	= new JLabel("enter a size:");
		JButton btnCreate 		= new JButton("Create");
		JButton btnBack 		= new JButton("back");
		
		lblInvalidSize.setFont			(new Font("Tahoma", Font.PLAIN, 16));
		lblInvalidSize.setForeground	(Color.RED);
		lblInvalidSize.setBounds		(41, 107, 83, 12);
		lblInvalidSize.setVisible		(false);
		lblEnterASize.setFont			(new Font("Tahoma", Font.PLAIN, 25));
		lblEnterASize.setBounds			(10, 11, 146, 42);
		btnCreate.setFont				(new Font("Tahoma", Font.PLAIN, 30));
		btnCreate.setBounds				(168, 11, 127, 87);
		btnBack.setFont					(new Font("Tahoma", Font.PLAIN, 25));
		btnBack.setBounds				(208, 106, 87, 37);
		textField.setFont				(new Font("Tahoma", Font.PLAIN, 20));
		textField.setBounds				(10, 56, 148, 42);
		textField.setColumns			(10);
		
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
						rubiksCube 						= new RubiksCube(size);
						CubeManipulationWindow window 	= new CubeManipulationWindow(rubiksCube);
						frmCreateRubiksCube.dispose();
						window.enable();
					} catch(IllegalSizeException e) {
						lblInvalidSize.setVisible(true);
					}
				}
			}
		});
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ChoiceWindow window = new ChoiceWindow();
				frmCreateRubiksCube.dispose();
				window.enable();
			}
		});
		
		frmCreateRubiksCube.getContentPane().add(textField);
		frmCreateRubiksCube.getContentPane().add(lblInvalidSize);
		frmCreateRubiksCube.getContentPane().add(lblEnterASize);
		frmCreateRubiksCube.getContentPane().add(btnCreate);
		frmCreateRubiksCube.getContentPane().add(btnBack);
	}
}
