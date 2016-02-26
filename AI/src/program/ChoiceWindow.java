package program;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * 
 * @author braem
 *
 * Window to choose whether you want to run the experiments or
 * manually manipulate a Rubik's Cube
 *
 */


public class ChoiceWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -606638665140433493L;
	private JFrame thisFrame = this;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChoiceWindow frame = new ChoiceWindow();
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
	public ChoiceWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 523, 317);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnRunExperiments = new JButton("Run Experiments");
		btnRunExperiments.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ExperimentsWindow window = new ExperimentsWindow();
				thisFrame.dispose();
				window.enable();
			}
		});
		btnRunExperiments.setFont(new Font("Tahoma", Font.PLAIN, 25));
		btnRunExperiments.setBounds(103, 11, 264, 71);
		contentPane.add(btnRunExperiments);
		
		JButton btnCreateManipulate = new JButton("Create & Manipulate a Rubik's Cube");
		btnCreateManipulate.setFont(new Font("Tahoma", Font.PLAIN, 25));
		btnCreateManipulate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CreateCubeWindow window = new CreateCubeWindow();
				thisFrame.dispose();
				window.enable();
			}
		});
		btnCreateManipulate.setBounds(10, 93, 464, 71);
		contentPane.add(btnCreateManipulate);
		
		JButton btnCreateNn = new JButton("Create NN & Run SBP on XOR");
		btnCreateNn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				NNSBPXORWindow window = new NNSBPXORWindow();
				thisFrame.dispose();
				window.enable();
			}
		});
		btnCreateNn.setFont(new Font("Tahoma", Font.PLAIN, 25));
		btnCreateNn.setBounds(35, 175, 410, 71);
		contentPane.add(btnCreateNn);
	}

}
