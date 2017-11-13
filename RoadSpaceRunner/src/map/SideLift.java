package map;

import java.awt.Graphics;

public class SideLift extends SuperLift {

	
	
	public SideLift() {
		// TODO Auto-generated constructor stub
	}

	public SideLift(int x, int y) {
		super(x, y);
		// TODO Auto-generated constructor stub
	}

	/***************
	 * 	Move
	 ***************/
	@Override
	protected void Move() {
		moveCount++;
		if(directionSwitch)x -= MOVE_SPEED;//左へ
		else x += MOVE_SPEED;//右へ
		if(moveCount > MOVE_LIMIT){
			if(directionSwitch)directionSwitch = false;
			else directionSwitch = true;
			moveCount = 0;
		}
	}

}
