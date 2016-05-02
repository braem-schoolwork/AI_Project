package program;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Window to decide on the different main components of this software.
 * 
 * @author Braemen Stoltz
 * @version 1.0
 */

public class ChoiceWindow extends JFrame
{
	private static final long 	serialVersionUID 	= -606638665140433493L;
	private JFrame 				thisFrame 			= this;
	private JPanel 				contentPane 		= new JPanel();

	public void enable() { this.setVisible(true); }
	
	public ChoiceWindow() {
		setTitle				("Main Window");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds				(100, 100, 500, 464);
		contentPane.setBorder	(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout	(null);
		setContentPane			(contentPane);
		
		JButton btnRunExperiments 		= new JButton("Run Experiments");
		JButton btnCreateManipulate 	= new JButton("Create & Manipulate a Rubik's Cube");
		JButton btnCreateNn 			= new JButton("Create & Train Neural Networks");
		JButton btnRunJunitTests 		= new JButton("Run JUnit Tests");
		JButton btnRunGeneticAlgorithm 	= new JButton("Run Genetic Algorithm");
		
		btnRunExperiments.setFont		(new Font("Tahoma", Font.PLAIN, 25));
		btnRunExperiments.setBounds		(103, 11, 264, 71);
		btnCreateManipulate.setFont		(new Font("Tahoma", Font.PLAIN, 25));
		btnCreateManipulate.setBounds	(10, 93, 464, 71);
		btnCreateNn.setFont				(new Font("Tahoma", Font.PLAIN, 25));
		btnCreateNn.setBounds			(35, 175, 410, 71);
		btnRunJunitTests.setFont		(new Font("Tahoma", Font.PLAIN, 25));
		btnRunJunitTests.setBounds		(103, 339, 264, 71);
		btnRunGeneticAlgorithm.setFont	(new Font("Tahoma", Font.PLAIN, 25));
		btnRunGeneticAlgorithm.setBounds(77, 257, 309, 71);
		
		btnRunExperiments.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ExperimentsWindow window = new ExperimentsWindow();
				thisFrame.dispose();
				window.enable();
			}
		});
		btnCreateManipulate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CreateCubeWindow window = new CreateCubeWindow();
				thisFrame.dispose();
				window.enable();
			}
		});
		btnCreateNn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				NNWindow window = new NNWindow();
				thisFrame.dispose();
				window.enable();
			}
		});
		btnRunJunitTests.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RunJUnitTestsWindow window = new RunJUnitTestsWindow();
				thisFrame.dispose();
				window.enable();
			}
		});
		btnRunGeneticAlgorithm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				GAChoiceWindow window = new GAChoiceWindow();
				window.enable();
				thisFrame.dispose();
			}
		});
		
		contentPane.add(btnRunExperiments);
		contentPane.add(btnCreateManipulate);
		contentPane.add(btnCreateNn);
		contentPane.add(btnRunJunitTests);
		contentPane.add(btnRunGeneticAlgorithm);
	}
}
