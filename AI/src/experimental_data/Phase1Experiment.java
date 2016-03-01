package experimental_data;

import search.*;
import training_data.RubiksCubeTrainingDataGenerator;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
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
	private static Search search = new AstarSearch();
	private static int cubeSize = 3;
	private static String hFriendlyFileName;
	private static String mFriendlyFileName;
	private static PrintWriter humanFriendlyWriter;
	private static PrintWriter machineFriendlyWriter;
	
	@Override
	public void runExperiment(String fileExtension) {
		setupFiles(fileExtension);
		humanFriendlyWriter.println("Experiment Number, Number of Perturbations, Rubik's Cube, Move Applied, Runtime");
		for(int j=1; j<=8; j++) {
			for(int k=1; k<=5; k++) {
				RubiksCube rubiksCube = new RubiksCube(cubeSize);
				Perturber.perturb(j, rubiksCube);
				double startTime = System.nanoTime();
				search.search(rubiksCube, new RubiksCube(rubiksCube.getSize()));
				double endTime = System.nanoTime();
				double duration = (endTime - startTime)/1000000000;
				List<Searchable> cubes = search.getPath();
				List<Move> moves = new ArrayList<Move>();
				for(Searchable obj : cubes)
					moves.add( ((RubiksCube)obj).getLastMoveApplied() );
				moves.remove(0);
				writeResults(cubes, moves, j, duration);
				if(cubes != null)
					cubes.clear();
				if(moves != null)
					moves.clear();
				
			}
			humanFriendlyWriter.println();
		}
		humanFriendlyWriter.close();
		machineFriendlyWriter.close();
	}
	
	private static void writeResults(List<Searchable> cubes, List<Move> moves, int perturbations, double duration) {
		for(int i=0; i<cubes.size(); i++) {
			//human readable entry
			String hEntry = "Phase 1" + "," + perturbations + "," + cubes.get(i).toString().replaceAll(",", "");
			
			//training data entry
			String mEntry = "";
			if(i!=cubes.size()-1)
				mEntry = RubiksCubeTrainingDataGenerator.genTrainingData(((RubiksCube) cubes.get(i)))+"|";
			try {
				hEntry += "," + moves.get(i).toString().replaceAll(",", "");
				mEntry += ((Move) moves.get(i)).toTrainingData();
			} catch(Exception e) {
				hEntry += ",," + duration;
			}
			humanFriendlyWriter.println(hEntry);
			machineFriendlyWriter.print(mEntry+'\t');
		}
	}

	private static void setupFiles(String fileExtension) {
		hFriendlyFileName = System.getProperty("user.dir") + "\\Phase1Experiment"+fileExtension;
		try {
			humanFriendlyWriter = new PrintWriter(hFriendlyFileName, "UTF-8");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		System.out.println("Human Readable File written to: ");
		System.out.println(hFriendlyFileName);
		System.out.println();
		
		mFriendlyFileName = System.getProperty("user.dir")+"\\Phase1Experiment_TrainingData"+fileExtension;
		try {
			machineFriendlyWriter = new PrintWriter(mFriendlyFileName, "UTF-8");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		System.out.println("Training Data File written to: ");
		System.out.println(mFriendlyFileName);
		System.out.println();
		System.out.println();
	}
	
	@Override
	public String toString() {
		return "Phase1";
	}
}
