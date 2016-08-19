package experimental_data;

/**
 * Experiment interface
 * 
 * @author braem
 * @version 1.0
 */
public interface Experiment
{
	/**
	 * Runs the experiment
	 * 
	 * @param fileExtension			extension for file. IE .txt, .csv, etc
	 * @param size					size of experiment to run. Proportional to speed of run
	 */
	public void runExperiment(String fileExtension, ExperimentSize size);
}
