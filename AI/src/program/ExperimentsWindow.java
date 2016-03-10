package program;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import experimental_data.*;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;

/**
 * Window to run different experiments from the phases
 * 
 * @author braem
 * @version 1.0
 */
public class ExperimentsWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6452197459200260004L;
	private JFrame thisFrame = this;
	private JPanel contentPane;
	private JComboBox<ExperimentSize> sizeCB;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ExperimentsWindow frame = new ExperimentsWindow();
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
	public ExperimentsWindow() {
		setTitle("Run Experiments");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 676, 316);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JComboBox<String> fileExtensionComboBox = new JComboBox<String>();
		fileExtensionComboBox.setFont(new Font("Tahoma", Font.PLAIN, 25));
		fileExtensionComboBox.setModel(new DefaultComboBoxModel<String>(new String[] {".txt", ".csv"}));
		fileExtensionComboBox.setSelectedIndex(1);
		fileExtensionComboBox.setBounds(222, 11, 102, 43);
		contentPane.add(fileExtensionComboBox);
		
		JComboBox<Experiment> ExperimentSelectComboBox = new JComboBox<Experiment>();
		ExperimentSelectComboBox.setFont(new Font("Tahoma", Font.PLAIN, 25));
		ExperimentSelectComboBox.addItem(new Phase1Experiment());
		ExperimentSelectComboBox.addItem(new Phase2Experiment());
		ExperimentSelectComboBox.setSelectedIndex(0);
		ExperimentSelectComboBox.setBounds(10, 11, 202, 43);
		contentPane.add(ExperimentSelectComboBox);
		
		JButton btnRunExperiment = new JButton("Run Experiment");
		btnRunExperiment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Experiment exp = (Experiment)ExperimentSelectComboBox.getSelectedItem();
				exp.runExperiment((String)fileExtensionComboBox.getSelectedItem(),
						(ExperimentSize)sizeCB.getSelectedItem());
			}
		});
		btnRunExperiment.setFont(new Font("Tahoma", Font.PLAIN, 25));
		btnRunExperiment.setBounds(10, 65, 416, 63);
		contentPane.add(btnRunExperiment);
		
		JButton btnBack = new JButton("back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ChoiceWindow window = new ChoiceWindow();
				thisFrame.dispose();
				window.enable();
			}
		});
		btnBack.setFont(new Font("Tahoma", Font.PLAIN, 25));
		btnBack.setBounds(334, 11, 92, 43);
		contentPane.add(btnBack);
		
		sizeCB = new JComboBox<ExperimentSize>();
		sizeCB.setFont(new Font("Tahoma", Font.PLAIN, 25));
		sizeCB.setBounds(204, 144, 222, 43);
		contentPane.add(sizeCB);
		for(ExperimentSize s : ExperimentSize.values())
			sizeCB.addItem(s);
		
		JLabel lblExperimentSize = new JLabel("Experiment Size:");
		lblExperimentSize.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblExperimentSize.setBounds(10, 144, 202, 43);
		contentPane.add(lblExperimentSize);
	}
}
