package experimental_data;

import search.*;
import training_data.RubiksCubeTrainingDataGenerator;

import java.util.ArrayList;

import rubiks.*;

/**
 * Runs Experiment from Phase 1
 * 
 * @author braem
 * @version 1.0
 */
public class Phase1Experiment implements Experiment
{
	private static Search search = new AstarSearch();
	private static int cubeSize = 3;
	private static String fileName = System.getProperty("user.dir")+"\\training_data";
	private static int rcSizeLim = 8;
	private static int runTimesLim = 5;
	
	@Override
	public void runExperiment(String fileExtension, ExperimentSize size) {
		setupParams(size);
		fileName += fileExtension;
		ArrayList<String> content = new ArrayList<String>();
		for(int j=1; j<=rcSizeLim; j++) {
			for(int k=1; k<=runTimesLim; k++) {
				RubiksCube rubiksCube = new RubiksCube(cubeSize);
				Perturber.perturb(j, rubiksCube);
				search.search(rubiksCube, new RubiksCube(rubiksCube.getSize()));
				ArrayList<Searchable> searchableObjs = search.getPath();
				ArrayList<RubiksCube> cubes = new ArrayList<RubiksCube>();
				for(Searchable obj : searchableObjs) {
					RubiksCube cube = (RubiksCube)obj;
					cubes.add(cube);
				}
				ArrayList<Move> moves = new ArrayList<Move>();
				for(RubiksCube obj : cubes)
					moves.add( obj.getLastMoveApplied() );
				moves.remove(0);
				cubes.remove(cubes.size()-1);
				content.addAll(RubiksCubeTrainingDataGenerator.genFileTrainingData(cubes, moves));
			}
		}
		ExperimentIO.writeToFile(content, fileName);
	}
	
	private static void setupParams(ExperimentSize size) {
		switch(size) {
		case SMALL:
			rcSizeLim = 6;
			runTimesLim = 4;
			break;
		case MEDIUM:
			rcSizeLim = 7;
			runTimesLim = 4;
			break;
		case LARGE:
			rcSizeLim = 8;
			runTimesLim = 5;
			break;
		}
	}
	
	@Override
	public String toString() {
		return "Phase1";
	}
}
