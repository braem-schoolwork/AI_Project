package rubiks;

import java.util.Random;

/**
 * Static class that holds the perturb function.
 * 
 * @author Braemen Stoltz
 * @version 1.0
 */
public class Perturber
{
	/**
	 * Takes a cube and applies k moves to it. Steps are taken
	 * to preserve the integrity of a set of moves
	 * 
	 * @param depth		how many moves to be applied
	 * @param cube		rubik's cube to be perturbed
	 */
	public static void perturb(int depth, RubiksCube cube) {
		Move[] 	moveSet 			= cube.getMoveSet();
		Move[] 	moveList 			= moveSet;
		int 	consecutiveMoves 	= 0;
		Random 	rand 				= new Random();
		
		if(depth <= 0 || depth > moveSet.length)
			throw new IllegalDepthException();
		
		int 	randomMove 		= rand.nextInt(moveSet.length);
		Move 	moveToApply 	= moveList[randomMove];
		Move 	lastMoveApplied = moveToApply;
		
		moveToApply.apply(cube);
		for(int i=1; i<depth; i++) {
			randomMove 	= rand.nextInt(moveSet.length);
			moveToApply = moveList[randomMove];
			if( lastMoveApplied.getMoveParams().getAxis().equals(moveToApply.getMoveParams().getAxis()) &&
					lastMoveApplied.getMoveParams().getSliceNum() == moveToApply.getMoveParams().getSliceNum() )
				
				if(!lastMoveApplied.getMoveParams().getDirection().equals(lastMoveApplied.getMoveParams().getDirection())) {
					if(randomMove == moveList.length-1)
						moveToApply 	= moveList[0];
					else
						moveToApply 	= moveList[randomMove+1];
					consecutiveMoves 	= 0;
				}
				else {
					if(consecutiveMoves == 2) {
						if(randomMove == moveList.length-1)
							moveToApply 	= moveList[0];
						else
							moveToApply 	= moveList[randomMove+1];
						consecutiveMoves 	= 0;
					}
					else
						consecutiveMoves++;
				}
			else
				consecutiveMoves = 0;
			moveToApply.apply(cube);
			lastMoveApplied = moveToApply;
		}
	}
}
