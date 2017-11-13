package map;

import java.awt.Color;
import java.awt.Graphics;

public abstract class SuperLift extends SuperMap {
	protected boolean directionSwitch;
	protected int moveCount;//Amount of movement
	protected static final int WIDTH = 60, HEIGHT = 10;
	protected static final int MOVE_SPEED = 1;
	protected static final int MOVE_LIMIT = 120;
	
	public SuperLift() {
		
	}

	public SuperLift(int x, int y) {
		super(x, y);
		directionSwitch = false;
	}
	
	public void StateReport(){
		Move();
	}
	
	protected abstract void Move();
	/***************
	 * 	Accessor
	 ***************/
	@Override public int getXsize() {return x + WIDTH;}
	public int getXMove(){return directionSwitch ? MOVE_SPEED * -1 : MOVE_SPEED;}
	public int getYMove(){return directionSwitch ? MOVE_SPEED * -1 : MOVE_SPEED;}
	/***************
	 * 	Decision
	 ***************/
	@Override
	public boolean TopDec(int x, int y, int width) {
		//引数の対象の左下と右下の判定
		if(this.x + 1 <= x && this.x - 1 + WIDTH >= x || this.x + 1 <= x + width && this.x - 1 + WIDTH >= x + width){
			if(this.y <= y && this.y + HEIGHT / 4 >= y){
				return true;//ブロック上半分の範囲に入っている
			}
		}
		return false;
	}

	@Override
	public boolean LeftDec(int x, int y, int width, int height) {
		//引数の対象の右上と右下の判定
		if(this.y + 1 <= y && this.y - 1 + HEIGHT >= y || this.y + 1 <= y + height && this.y - 1 + HEIGHT >= y){
			if(this.x <= x + width && this.x + WIDTH / 4 >= x + width){
				return true;//ブロック左半分の範囲に入っている
			}
		}
		return false;
	}

	@Override
	public boolean RightDec(int x, int y, int width, int height) {
		//引数の対象の左上と左下の判定
		if(this.y + 1 <= y && this.y - 1 + HEIGHT >= y || this.y + 1 <= y + height && this.y - 1 + HEIGHT >= y){
			if(this.x + WIDTH / 4 <= x && this.x + WIDTH >= x){
				return true;//ブロック右半分の範囲に入っている
			}
		}return false;
	}

	@Override
	public boolean BottomDec(int x, int y, int width) {
		//引数の対象の右上と左上の判定
		if(this.x + 1 <= x && this.x - 1 + WIDTH >= x || this.x + 1 <= x + width && this.x - 1 + WIDTH >= x + width){
			if(this.y + HEIGHT >= y && this.y + HEIGHT / 4 <= y){
				return true;//ブロック下半分の範囲に入っている
			}
		}return false;
	}

	@Override
	public void Draw(Graphics g) {
		g.setColor(Color.DARK_GRAY);
		g.fillRect(x, y, WIDTH, HEIGHT);
	}
}
