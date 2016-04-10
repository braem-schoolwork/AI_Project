package experimental_data;

public class Phase4Experiment implements Experiment
{
	private static String fileNameETI = System.getProperty("user.dir")+"\\ETI";
	
	private static int genStart = 10;
	private static int genIncrease = 10;
	private static int genEnd = 100;
	
	private static int popStart = 20;
	private static int popIncrease = 20;
	private static int popEnd = 150;
	
	private static float pEliteStart = 10;
	private static float pEliteIncrease = 10;
	private static float pEliteEnd = 90;
	
	private static float pMutationStart = 10;
	private static float pMutationIncrease = 10;
	private static float pMutationEnd = 90;
	
	private static float pCrossoverStart = 10;
	private static float pCrossoverIncrease = 10;
	private static float pCrossoverEnd = 90;
	
	private static int defaultGen = 100;
	private static int defaultPopSize = 100;
	private static float defaultElite = 20;
	private static float defaultMutation = 75;
	private static float defaultCrossover = 5;
	
	@Override
	public void runExperiment(String fileExtension, ExperimentSize size) {
		// TODO Auto-generated method stub
		
	}

}
