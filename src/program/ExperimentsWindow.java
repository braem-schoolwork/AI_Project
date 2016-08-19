package program;

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
 * Window to run different experiments from the phases.
 * 
 * @author Braemen Stoltz
 * @version 1.0
 */
public class ExperimentsWindow extends JFrame
{
	private static final long 			serialVersionUID 	= 6452197459200260004L;
	private JFrame 						thisFrame 			= this;
	private JPanel 						contentPane 		= new JPanel();
	private JComboBox<ExperimentSize> 	sizeCB 				= new JComboBox<ExperimentSize>();

	public void enable() { this.setVisible(true); }

	public ExperimentsWindow() {
		setTitle				("Run Experiments");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds				(100, 100, 676, 316);
		contentPane.setBorder	(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout	(null);
		setContentPane			(contentPane);
		
		JComboBox<String> 		fileExtensionComboBox 		= new JComboBox<String>();
		JComboBox<Experiment> 	ExperimentSelectComboBox 	= new JComboBox<Experiment>();
		JButton 				btnRunExperiment 			= new JButton("Run Experiment");
		JButton 				btnBack 					= new JButton("back");
		JLabel 					lblExperimentSize 			= new JLabel("Experiment Size:");
		
		lblExperimentSize.setFont					(new Font("Tahoma", Font.PLAIN, 25));
		lblExperimentSize.setBounds					(10, 144, 202, 43);
		fileExtensionComboBox.setFont				(new Font("Tahoma", Font.PLAIN, 25));
		fileExtensionComboBox.setModel				(new DefaultComboBoxModel<String>(new String[] {".txt", ".csv"}));
		fileExtensionComboBox.setSelectedIndex		(1);
		fileExtensionComboBox.setBounds				(222, 11, 102, 43);
		ExperimentSelectComboBox.setFont			(new Font("Tahoma", Font.PLAIN, 25));
		ExperimentSelectComboBox.setSelectedIndex	(0);
		ExperimentSelectComboBox.setBounds			(10, 11, 202, 43);
		
		ExperimentSelectComboBox.addItem( new Phase1Experiment() );
		ExperimentSelectComboBox.addItem( new Phase2Experiment() );
		ExperimentSelectComboBox.addItem( new Phase3Experiment() );
		ExperimentSelectComboBox.addItem( new Phase4Experiment() );
		
		btnRunExperiment.setFont	(new Font("Tahoma", Font.PLAIN, 25));
		btnRunExperiment.setBounds	(10, 65, 416, 63);
		btnBack.setFont				(new Font("Tahoma", Font.PLAIN, 25));
		btnBack.setBounds			(334, 11, 92, 43);
		sizeCB.setFont				(new Font("Tahoma", Font.PLAIN, 25));
		sizeCB.setBounds			(204, 144, 222, 43);
		
		for(ExperimentSize s : ExperimentSize.values())
			sizeCB.addItem(s);
		
		btnRunExperiment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Experiment exp = (Experiment)ExperimentSelectComboBox.getSelectedItem();
				exp.runExperiment( (String)fileExtensionComboBox.getSelectedItem(),
						(ExperimentSize)sizeCB.getSelectedItem() );
			}
		});
		
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ChoiceWindow window = new ChoiceWindow();
				thisFrame.dispose();
				window.enable();
			}
		});
		
		contentPane.add(sizeCB);
		contentPane.add(fileExtensionComboBox);
		contentPane.add(ExperimentSelectComboBox);
		contentPane.add(btnRunExperiment);
		contentPane.add(btnBack);
		contentPane.add(lblExperimentSize);
	}
}
