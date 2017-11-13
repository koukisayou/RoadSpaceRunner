package character;

import java.awt.Color;
import java.awt.Graphics;

public class StraightBall extends Ball {
	private static final double MOVE_SPEED = 2.0;

	public StraightBall(double x, double y, boolean dir) {
		super(x, y, dir);
	}
	
	/*public StraightBall(double x, double y, boolean dir, int ballDirection){
		super(x, y, dir, ballDirection);
	}*/
	
	public StraightBall(double x, double y, boolean dir, boolean evil) {
		super(x, y, dir, evil);
	}
	
	@Override
	public void LeftMove() {
		x -= MOVE_SPEED;
	}
	
	@Override
	public void RightMove() {
		x += MOVE_SPEED;
	}
	
	@Override
	public void Draw(Graphics g){
		if(visible){
			if(evil)g.setColor(Color.GREEN);
			else g.setColor(Color.MAGENTA);
			g.fillRect((int)x, (int)y, WIDTH, HEIGHT);
		}
	}
}