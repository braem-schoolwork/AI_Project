package program;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GAChoiceWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2716082525654016415L;
	private JPanel contentPane;
	private JFrame thisFrame = this;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GAChoiceWindow frame = new GAChoiceWindow();
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
	public GAChoiceWindow() {
		setTitle("Genetic Algorithms");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 416, 211);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("Run on XOR");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				GAXORWindow window = new GAXORWindow();
				window.enable();
				thisFrame.dispose();
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 25));
		btnNewButton.setBounds(10, 11, 347, 56);
		contentPane.add(btnNewButton);
		
		JButton btnRunOnRubiks = new JButton("Run on Rubik's Cube");
		btnRunOnRubiks.setFont(new Font("Tahoma", Font.PLAIN, 25));
		btnRunOnRubiks.setBounds(10, 78, 347, 56);
		contentPane.add(btnRunOnRubiks);
	}

}
