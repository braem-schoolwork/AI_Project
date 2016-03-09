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
 * Window to pick from different neural network training options
 * 
 * @author braemen
 * @version 1.0
 */
public class NNWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2486932921763055237L;
	private JPanel contentPane;
	private JFrame thisFrame = this;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NNWindow frame = new NNWindow();
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
	public NNWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 673, 328);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnRunOn = new JButton("Run SBP on 2-2-1 w/ bias network for XOR");
		btnRunOn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				NNSBPXORWindow window = new NNSBPXORWindow();
				thisFrame.dispose();
				window.enable();
			}
		});
		btnRunOn.setFont(new Font("Tahoma", Font.PLAIN, 24));
		btnRunOn.setBounds(15, 16, 602, 61);
		contentPane.add(btnRunOn);
		
		JButton btnCreateTrain = new JButton("Create & train network with SBP");
		btnCreateTrain.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CreateNetworkWindow window = new CreateNetworkWindow();
				thisFrame.dispose();
				window.enable();
			}
		});
		btnCreateTrain.setFont(new Font("Tahoma", Font.PLAIN, 24));
		btnCreateTrain.setBounds(15, 93, 602, 61);
		contentPane.add(btnCreateTrain);
		
		JButton btnTrainNetworkFor = new JButton("Train network for Rubik's Cube");
		btnTrainNetworkFor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RCNNWindow window = new RCNNWindow();
				thisFrame.dispose();
				window.enable();
			}
		});
		btnTrainNetworkFor.setFont(new Font("Tahoma", Font.PLAIN, 24));
		btnTrainNetworkFor.setBounds(15, 170, 602, 61);
		contentPane.add(btnTrainNetworkFor);
	}
}
