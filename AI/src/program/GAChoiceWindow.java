package program;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Window for selecting what the user wants to do with genetic algorithms.
 * 
 * @author Braemen Stoltz
 * @version 1.0
 */
public class GAChoiceWindow extends JFrame
{
	private static final long 	serialVersionUID 	= -2716082525654016415L;
	private JPanel 				contentPane 		= new JPanel();
	private JFrame 				thisFrame 			= this;

	public void enable() { this.setVisible(true); }
	
	public GAChoiceWindow() {
		setTitle				("Genetic Algorithms");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds				(100, 100, 401, 313);
		contentPane.setBorder	(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout	(null);
		setContentPane			(contentPane);
		
		JButton btnNewButton 	= new JButton("Run on XOR");
		JButton btnRunOnRubiks 	= new JButton("Run on Rubik's Cube");
		JButton btnBack 		= new JButton("back");
		JButton btnCreateNn 	= new JButton("Run on a custom NN");
		
		btnNewButton.setFont	(new Font("Tahoma", Font.PLAIN, 25));
		btnNewButton.setBounds	(10, 11, 347, 56);
		btnRunOnRubiks.setFont	(new Font("Tahoma", Font.PLAIN, 25));
		btnRunOnRubiks.setBounds(10, 78, 347, 56);
		btnBack.setFont			(new Font("Tahoma", Font.PLAIN, 20));
		btnBack.setBounds		(127, 212, 105, 35);
		btnCreateNn.setFont		(new Font("Tahoma", Font.PLAIN, 25));
		btnCreateNn.setBounds	(10, 145, 347, 56);
		
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				GAXORWindow window = new GAXORWindow();
				window.enable();
				thisFrame.dispose();
			}
		});
		
		btnRunOnRubiks.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GARCNNWindow window = new GARCNNWindow();
				window.enable();
				thisFrame.dispose();
			}
		});
		
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ChoiceWindow window = new ChoiceWindow();
				window.enable();
				thisFrame.dispose();
			}
		});
		
		btnCreateNn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GenericGAWindow window = new GenericGAWindow();
				window.enable();
				thisFrame.dispose();
			}
		});

		contentPane.add(btnNewButton);
		contentPane.add(btnRunOnRubiks);
		contentPane.add(btnBack);
		contentPane.add(btnCreateNn);
	}
}
