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
	private String fileName;
	private PrintWriter writer;
	
	public Experiment(int experimentNum, Search search, int cubeSize) {
		this.experimentNum = experimentNum;
		this.search = search;
		this.cubeSize = cubeSize;
	}
	
	public void runExperiment(String fileExtension) {
		setupFile(fileExtension);
		writer.println("Experiment Number, Number of Perturbations, Rubik's Cube, Move Applied, Runtime");
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
			writer.println();
			System.out.println("hello?");
		}
		writer.close();
	}
	
	private void writeResults(ArrayList<Searchable> cubes, ArrayList<Move> moves, int perturbations, double duration) {
		for(int i=0; i<cubes.size(); i++) {
			String entry = experimentNum + "," + perturbations + "," + cubes.get(i).toString().replaceAll(",", "");
			try {
				entry += "," + moves.get(i).toString().replaceAll(",", "");
			} catch(Exception e) {
				entry += ",," + duration;
			}
			
			writer.println(entry);
		}
	}

	private void setupFile(String fileExtension) {
		this.fileName = System.getProperty("user.dir")+"\\Experiment"+experimentNum+fileExtension;
		try {
			this.writer = new PrintWriter(fileName, "UTF-8");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		System.out.println(fileName);
	}
	
	@Override
	public String toString() {
		return "Experiment "+experimentNum;
	}
}
