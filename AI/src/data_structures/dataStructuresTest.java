package data_structures;

import static org.junit.Assert.*;

import org.junit.Test;

import rubiks.*;
import search.*;

public class dataStructuresTest {

	@Test
	public void testPQ() {
		PriorityQueue<Searchable> pq = new PriorityQueue<Searchable>();
		RubiksCube cube = new RubiksCube(3);
		Searchable[] children = cube.genChildren();
		
		Move move1 = new Move(0, Axis.X, Direction.CW);
		move1.apply(cube);
		for(Searchable child : children) {
			pq.add(child);
		}
		for(Searchable child : children) {
			pq.remove(child);
		}
		for(Searchable child : children) {
			pq.add(child);
		}
		pq.remove(children[5]);
		assertTrue(!pq.contains(children[5]));
		assertTrue(pq.contains(cube));
		Searchable ref = pq.containsRef(cube);
		
		assertTrue(ref.equals(cube));
	}
	
	@Test
	public void testHS() {
		HashSet<Searchable> hs = new HashSet<Searchable>();
		RubiksCube cube = new RubiksCube(3);
		Searchable[] children = cube.genChildren();
		
		Move move1 = new Move(0, Axis.X, Direction.CW);
		move1.apply(cube);
		
		for(Searchable child : children) {
			hs.add(child);
		}
		for(Searchable child : children) {
			hs.remove(child);
		}
		for(Searchable child : children) {
			hs.add(child);
		}
		hs.remove(children[5]);
		assertTrue(!hs.contains(children[5]));
		assertTrue(hs.contains(cube));
		Searchable ref = hs.containsRef(cube);
		
		assertTrue(ref.equals(cube));
	}

}
