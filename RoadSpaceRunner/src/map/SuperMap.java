package map;

import java.awt.Graphics;

import StaticFinalField.StaticFinalField;

public abstract class SuperMap implements StaticFinalField{
	protected int x, y;
	protected int defaultX, defaultY;
	
	public SuperMap() {
	}
	
	public SuperMap(int x, int y){
		defaultX = x;
		defaultY = y;
		this.x = x;
		this.y = y;
	}
	
	public void InitState(){
		x = defaultX;
		y = defaultY;
	}
	
	public void WarpX(int x){this.x = defaultX - x;}
	/***************
	 * 	Accessor
	 ***************/
	public int getX(){return this.x;}
	public abstract int getXsize();
	/***************
	 * 	Move
	 ***************/
	public void LeftMove(){x -= SCROLL_SPEED;}
	public void RightMove(){x += SCROLL_SPEED;}
	/***************
	 * 	Decision
	 ***************/
	//上部当たり判定
	public abstract boolean TopDec(int x, int y, int width);
	//左部当たり判定
	public abstract boolean LeftDec(int x, int y, int width, int height);
	//右部当たり判定
	public abstract boolean RightDec(int x, int y, int width, int height);
	//下部当たり判定
	public abstract boolean BottomDec(int x, int y, int width);
	public abstract void Draw(Graphics g);

}
