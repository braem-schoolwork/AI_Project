package program;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;

import genetic_algorithm.ComparatorTest;
import genetic_algorithm.ConversionTest;
import genetic_algorithm.GATest;
import neural_network.NNTest;
import rubiks.MoveTest;
import rubiks.RubiksCubeTest;
import search.AstarSearchTest;
import search.BFSearchTest;
import training_algorithms.SBPTest;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * 
 * 
 * @author braemen
 * @version 1.0
 */
public class RunJUnitTestsWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3673237110135893130L;
	private JPanel contentPane;
	private JFrame thisFrame = this;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RunJUnitTestsWindow frame = new RunJUnitTestsWindow();
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

	private void printTestResults(Result r) {
		if(r.getFailureCount() == 0)
			System.out.println("Tests Passed");
		else
			System.out.println("Tests Failed");
	}
	private void printTestResults(Result r1, Result r2) {
		if(r1.getFailureCount() == 0 && r2.getFailureCount() == 0)
			System.out.println("Tests Passed");
		else
			System.out.println("Tests Failed");
	}
	private void printTestResults(Result r1, Result r2, Result r3) {
		if(r1.getFailureCount() == 0 && r2.getFailureCount() == 0 && r3.getFailureCount() == 0)
			System.out.println("Tests Passed");
		else
			System.out.println("Tests Failed");
	}
	
	/**
	 * Create the frame.
	 */
	public RunJUnitTestsWindow() {
		setTitle("Run JUnit Tests");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 675, 290);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		JUnitCore junit = new JUnitCore();
		
		JButton btnRunSbpTests = new JButton("Run SBP Tests");
		btnRunSbpTests.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				printTestResults(junit.run(SBPTest.class));
			}
		});
		btnRunSbpTests.setFont(new Font("Tahoma", Font.PLAIN, 24));
		btnRunSbpTests.setBounds(10, 11, 295, 62);
		contentPane.add(btnRunSbpTests);
		
		JButton btnRunNnTests = new JButton("Run NN Tests");
		btnRunNnTests.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				printTestResults(junit.run(NNTest.class));
			}
		});
		btnRunNnTests.setFont(new Font("Tahoma", Font.PLAIN, 24));
		btnRunNnTests.setBounds(315, 11, 295, 62);
		contentPane.add(btnRunNnTests);
		
		JButton btnRunRubiksTests = new JButton("Run Rubik's Tests");
		btnRunRubiksTests.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				printTestResults(junit.run(RubiksCubeTest.class), junit.run(MoveTest.class));
			}
		});
		btnRunRubiksTests.setFont(new Font("Tahoma", Font.PLAIN, 24));
		btnRunRubiksTests.setBounds(10, 84, 295, 62);
		contentPane.add(btnRunRubiksTests);
		
		JButton btnRunSearchTests = new JButton("Run Search Tests");
		btnRunSearchTests.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				printTestResults(junit.run(AstarSearchTest.class), junit.run(BFSearchTest.class));
			}
		});
		btnRunSearchTests.setFont(new Font("Tahoma", Font.PLAIN, 24));
		btnRunSearchTests.setBounds(315, 84, 295, 62);
		contentPane.add(btnRunSearchTests);
		
		JButton btnBack = new JButton("back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ChoiceWindow window = new ChoiceWindow();
				thisFrame.dispose();
				window.enable();
			}
		});
		btnBack.setFont(new Font("Tahoma", Font.PLAIN, 24));
		btnBack.setBounds(315, 157, 295, 62);
		contentPane.add(btnBack);
		
		JButton btnRunGATests = new JButton("Run GA Tests");
		btnRunGATests.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				printTestResults(junit.run(ComparatorTest.class), junit.run(GATest.class), junit.run(ConversionTest.class));
			}
		});
		btnRunGATests.setFont(new Font("Tahoma", Font.PLAIN, 24));
		btnRunGATests.setBounds(10, 157, 295, 62);
		contentPane.add(btnRunGATests);
	}
}
