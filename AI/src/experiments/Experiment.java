package experiments;

import search.*;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import rubiks.*;

public class Experiment
{
	private Search search;
	private int cubeSize;
	private int experimentNum;
	private String fileName;
	private PrintWriter writer;
	
	public Experiment(int experimentNum, Search search, int cubeSize, String fileExtension) {
		this.experimentNum = experimentNum;
		this.search = search;
		this.cubeSize = cubeSize;
		this.fileName = System.getProperty("user.dir")+"\\Experiment"+experimentNum+fileExtension;
		System.out.println(fileName);
		try {
			this.writer = new PrintWriter(fileName, "UTF-8");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	public void runExperiment() {
		writer.println("Experiment Number, Number of Perturbations, Rubik's Cube, Move Applied/Runtime");
		for(int j=1; j<=5; j++) {
			for(int k=1; k<=5; k++) {
				
				RubiksCube rubiksCube = new RubiksCube(cubeSize);
				rubiksCube.perturb(j);
				double startTime = System.nanoTime();
				RubiksCube searchResult = (RubiksCube) search.search(rubiksCube, RubiksCube.createSolvedRubiksCube(rubiksCube.getSize()));
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

	
	@Override
	public String toString() {
		return "Experiment "+experimentNum;
	}
}
