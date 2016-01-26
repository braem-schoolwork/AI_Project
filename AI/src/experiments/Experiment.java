package experiments;

import search.*;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import rubiks.*;

/**
 * 
 * @author braem
 *
 * Runs given Experiment
 * 
 * 
 */

public class Experiment
{
	private Search search;
	private int cubeSize;
	private int experimentNum;
	private String hFriendlyFileName;
	private String mFriendlyFileName;
	private PrintWriter humanFriendlyWriter;
	private PrintWriter machineFriendlyWriter;
	
	public Experiment(int experimentNum, Search search, int cubeSize) {
		this.experimentNum = experimentNum;
		this.search = search;
		this.cubeSize = cubeSize;
	}
	
	public void runExperiment(String fileExtension) {
		setupFiles(fileExtension);
		humanFriendlyWriter.println("Experiment Number, Number of Perturbations, Rubik's Cube, Move Applied, Runtime");
		for(int j=1; j<=8; j++) {
			for(int k=1; k<=5; k++) {
				RubiksCube rubiksCube = new RubiksCube(cubeSize);
				rubiksCube.perturb(j);
				double startTime = System.nanoTime();
				RubiksCube searchResult = (RubiksCube)search.search(rubiksCube, new RubiksCube(rubiksCube.getSize()));
				double endTime = System.nanoTime();
				double duration = (endTime - startTime)/1000000000;
				ArrayList<Searchable> cubes = search.getPath();
				ArrayList<Move> moves = searchResult.traceMoves();
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
	
	private void writeResults(ArrayList<Searchable> cubes, ArrayList<Move> moves, int perturbations, double duration) {
		for(int i=0; i<cubes.size(); i++) {
			//human readable entry
			String hEntry = experimentNum + "," + perturbations + "," + cubes.get(i).toString().replaceAll(",", "");
			
			//training data entry
			String mEntry = "";
			if(i!=cubes.size()-1)
				mEntry = ((RubiksCube) cubes.get(i)).toTrainingData()+"|";
			try {
				hEntry += "," + moves.get(i).toString().replaceAll(",", "");
				mEntry += moves.get(i).toTrainingData();
			} catch(Exception e) {
				hEntry += ",," + duration;
			}
			humanFriendlyWriter.println(hEntry);
			machineFriendlyWriter.print(mEntry+'\t');
		}
	}

	private void setupFiles(String fileExtension) {
		this.hFriendlyFileName = System.getProperty("user.dir") + "\\Experiment"+experimentNum+fileExtension;
		try {
			this.humanFriendlyWriter = new PrintWriter(hFriendlyFileName, "UTF-8");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		System.out.println("Human Readable File written to: ");
		System.out.println(hFriendlyFileName);
		System.out.println();
		
		this.mFriendlyFileName = System.getProperty("user.dir")+"\\Experiment"+experimentNum+"TrainingData"+fileExtension;
		try {
			this.machineFriendlyWriter = new PrintWriter(mFriendlyFileName, "UTF-8");
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
		return "Experiment "+experimentNum;
	}
}
