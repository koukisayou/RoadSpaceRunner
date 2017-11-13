package map;

import java.awt.Color;
import java.awt.Graphics;

public class UpDownLift extends SuperLift {
	private static final int WIDTH = 60, HEIGHT = 10;
	private static final int MOVE_SPEED = 1;
	private static final int MOVE_LIMIT = 120;

	public UpDownLift() {
		// TODO Auto-generated constructor stub
	}

	public UpDownLift(int x, int y) {
		super(x, y);
	}

	
	/***************
	 * 	Move
	 ***************/
	@Override
	protected void Move(){
		moveCount++;
		if(directionSwitch)y -= MOVE_SPEED;//上昇
		else y += MOVE_SPEED;//下降
		//動く方向の切り替え
		if(moveCount > MOVE_LIMIT){
			if(directionSwitch)directionSwitch = false;
			else directionSwitch = true;
			moveCount = 0;
		}
	}
	

}
