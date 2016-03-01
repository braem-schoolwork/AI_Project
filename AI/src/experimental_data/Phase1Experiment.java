package experimental_data;

import search.*;
import training_data.RubiksCubeTrainingDataGenerator;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import rubiks.*;

/**
 * 
 * @author braem
 *
 * Runs given Experiment
 * 
 * 
 */

public class Phase1Experiment implements Experiment
{
	private final static Charset ENCODING = StandardCharsets.UTF_8;
	private static Search search = new AstarSearch();
	private static int cubeSize = 3;
	private static String fileName = System.getProperty("user.dir")+"\\Phase1Experiment_TrainingData";
	
	@Override
	public void runExperiment(String fileExtension) {
		fileName += fileExtension;
		List<String> content = new ArrayList<String>();
		for(int j=1; j<=8; j++) {
			for(int k=1; k<=5; k++) {
				RubiksCube rubiksCube = new RubiksCube(cubeSize);
				Perturber.perturb(j, rubiksCube);
				search.search(rubiksCube, new RubiksCube(rubiksCube.getSize()));
				List<Searchable> searchableObjs = search.getPath();
				List<RubiksCube> cubes = new ArrayList<RubiksCube>();
				for(Searchable obj : searchableObjs)
					cubes.add( (RubiksCube)obj );
				List<Move> moves = new ArrayList<Move>();
				for(Searchable obj : cubes)
					moves.add( ((RubiksCube)obj).getLastMoveApplied() );
				moves.remove(0);
				content.addAll(RubiksCubeTrainingDataGenerator.genTrainingData(cubes, moves));
			}
		}
		try {
			writeToFile(content, fileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void writeToFile(List<String> lines, String filename) throws IOException {
		Path path = Paths.get(filename);
		Files.write(path, lines, ENCODING);
	}
	
	@Override
	public String toString() {
		return "Phase1";
	}
}
