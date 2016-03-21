package rubiks;

import java.util.Random;

/**
 * Static class that holds the perturb function
 * 
 * @author braemen
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
		Move[] moveSet = cube.getMoveSet();
		//check
		if(depth <= 0 || depth > moveSet.length) throw new IllegalDepthException();
		//variables
		Move[] moveList = moveSet;
		int consecutiveMoves = 0; //counts consecutive same moves
		Random rand = new Random();
		int randomMove; //index for a move in the list
		
		//apply a randomMove
		randomMove = rand.nextInt(moveSet.length);
		Move moveToApply = moveList[randomMove];
		//copy this instead
		Move lastMoveApplied = moveToApply;
		moveToApply.apply(cube);
		//attempt to preserve depth integrity
		for(int i=1; i<depth; i++) {
			randomMove = rand.nextInt(moveSet.length);
			moveToApply = moveList[randomMove];
			//same axis && same sliceNum
			if( lastMoveApplied.getMoveParams().getAxis().equals(moveToApply.getMoveParams().getAxis()) &&
					lastMoveApplied.getMoveParams().getSliceNum()==moveToApply.getMoveParams().getSliceNum() )
				if(!lastMoveApplied.getMoveParams().getDirection().equals(lastMoveApplied.getMoveParams().getDirection())) {//not same direction (undos previous move)
					if(randomMove == moveList.length-1) //apply different move
						moveToApply = moveList[0];
					else
						moveToApply = moveList[randomMove+1];
					consecutiveMoves = 0;
				}
				else { //same move as previous
					if(consecutiveMoves == 2) { //2 of the same moves applied already
						if(randomMove == moveList.length-1) //apply different move
							moveToApply = moveList[0];
						else
							moveToApply = moveList[randomMove+1];
						consecutiveMoves = 0;
					}
					else //count consecutiveMoves
						consecutiveMoves++;
				}
			else
				consecutiveMoves = 0;
			moveToApply.apply(cube);
			lastMoveApplied = moveToApply;
		}
	}
}
