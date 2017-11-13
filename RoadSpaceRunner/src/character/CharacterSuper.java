package character;

import java.awt.Graphics;

import StaticFinalField.StaticFinalField;

public abstract class CharacterSuper implements StaticFinalField{
	protected int x, y;
	protected int defaultX, defaultY;
	protected int life;
	protected boolean loopend;//当たり判定のループ時に使用
	protected boolean alive;//true = alive, false = dead
	protected boolean fly;//壁にぶつかって飛べない時にfalse
	protected boolean groundDec;//接地判定。trueで接地状態
	protected boolean direction;//体の向き true = 右, false = 左
	
	public CharacterSuper(){
		
	}
	
	public CharacterSuper(int x, int y){
		defaultX = x;
		defaultY = y;
		this.x = x;
		this.y = y;
	}
	
	public abstract void StateReport();
	public abstract void InitState();
	public abstract void Draw(Graphics g);//描画
	public void WarpX(int x){this.x = defaultX - x;}
	
	/*********************
	 * 		Accessor
	 *********************/
	protected void setAlive(boolean b){alive = b;}
	public boolean getAlive(){return alive;}
	public void setLoopEnd(boolean b){loopend = b;}
	public boolean getLoopEnd(){return loopend;}
	public void setX(int x){this.x = x;}
	public void setXMove(int x){this.x += x;}
	public void setYMove(int y){this.y += y;}
	public int getX(){return x;}
	public int getY(){return y;}
	public abstract double getXcenter();
	public abstract double getYcenter();
	public abstract double getRadius();
	public abstract int getWIDTH();
	public abstract int getHEIGHT();
	public abstract int getBottom();
	public abstract void setGroundDecision(boolean b);
	public abstract void setRightCollision(boolean b);
	public abstract void setLeftCollision(boolean b);
	public void setFly(boolean b){fly = b;}
	
	public abstract void Damege();
	protected abstract void Fall();
}
