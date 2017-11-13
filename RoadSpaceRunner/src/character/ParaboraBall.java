package character;

import java.awt.Color;
import java.awt.Graphics;

public class ParaboraBall extends Ball {
	private static final double X_VERTEX = 100.0, Y_VERTEX = 100.0;//頂点の座標
	private static final double a = Y_VERTEX / Math.pow(X_VERTEX, 2.0);//二次関数aの値
	private static final double X_VARIATE = 1.0;//xの毎時増減量
	
	public ParaboraBall(double x, double y, boolean dir) {
		super(x, y, dir);
	}
	
	/*public ParaboraBall(double x, double y, boolean dir, int ballDirection){
		super(x, y, dir, ballDirection);
	}*/
	
	public ParaboraBall(double x, double y, boolean dir, boolean evil) {
		super(x, y, dir, evil);
	}

	@Override
	public void LeftMove() {
		x -= X_VARIATE;
		moveX -= X_VARIATE;
		y = a * Math.pow(moveX - (defaultX - X_VERTEX), 2.0) + (defaultY - Y_VERTEX);
	}

	@Override
	public void RightMove() {
		x += X_VARIATE;
		moveX += X_VARIATE;
		y = a * Math.pow(moveX - (defaultX + X_VERTEX), 2.0) + (defaultY - Y_VERTEX);
	}

	@Override
	public void Draw(Graphics g) {
		if(visible){
			if(evil)g.setColor(Color.GREEN);
			else g.setColor(Color.MAGENTA);
			g.fillRect((int)x, (int)y, WIDTH, HEIGHT);
		}
	}

}
