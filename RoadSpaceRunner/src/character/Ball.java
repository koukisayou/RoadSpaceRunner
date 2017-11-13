package character;

import java.awt.Graphics;

import StaticFinalField.StaticFinalField;

public abstract class Ball implements StaticFinalField{
	protected double x, y;
	protected double defaultX, defaultY;
	protected double moveX;
	protected boolean evil;		//true = playerの攻撃, false = Enemyの攻撃
	protected boolean direction;//true = 右, false = 左
	protected boolean moving;	//true = move, false = stop
	protected boolean visible;	//true = visible, false = invisible
	
	protected static final int WIDTH = 7, HEIGHT = 7;
	
	//DefaultConstructor
	Ball(double x, double y, boolean dir){
		this.x = x;
		this.y = y;
		moveX = x;
		defaultX = x;
		defaultY = y;
		this.evil = false;
		this.direction = dir;
		this.moving = false;
		this.visible = false;
	}
	
	/*//PlayerAttackConstructor
	Ball(double x, double y, boolean dir, int ballDirection){
		this.x = x;
		this.y = y;
		this.evil = true;
		this.direction = dir;
		this.moving = true;
		this.visible = true;
	}*/
	
	//AttackConstructor
	Ball(double x, double y, boolean dir, boolean evil){
		this.x = x;
		this.y = y;
		moveX = x;
		defaultX = x;
		defaultY = y;
		this.evil = evil;
		this.direction = dir;
		this.moving = true;
		this.visible = true;
	}
	
	/*********************
	 * 		Accessor
	 *********************/
	public void setX(int x){this.x = x;}
	public double getX(){return x;}
	public int getXcenter(){return (int)x + WIDTH / 2;}
	public void setY(int y){this.y = y;}
	public double getY(){return y;}
	public int getYcenter(){return (int)y + HEIGHT /2;}
	public void setMoving(boolean b){this.moving = b;}
	public boolean getEvil(){return evil;}
	public boolean isVisible(){return visible;}
	
	public void StateReport(){
		if(moving)Move();
		if(x < 0 || x > D_XSIZE || y > D_YSIZE){
			initDelete();//画面外に出れば消える
		}
	}
	
	//攻撃時の初期化
	public void initAttack(double x, double y, boolean evil, boolean dir){
		defaultX = x;
		defaultY = y;
		this.x = defaultX;
		this.y = defaultY;
		moveX = x;
		this.evil = evil;
		this.direction = dir;
		this.moving = true;
		this.visible = true;
	}
	
	//消滅時の初期化
	public void initDelete(){
		moving = false;
		visible = false;
	}
	
	//キャラクターへの当たり判定(点と円)
	public boolean Decision(double x, double y, double r){
		if(visible && Math.pow(this.x + WIDTH / 2 - x, 2.0) + Math.pow(this.y + HEIGHT / 2 - y, 2.0) <= Math.pow(r, 2.0)){
			return true;
		}else return false;
	}
	
	//プレイヤーの攻撃との当たり判定(矩形)
	public boolean RectangleDecision(int x, int y, int width ,int height, boolean dir){
		if(x < getXcenter() && x + width > getXcenter() && y < getYcenter() && y + height > getYcenter()){
			evil = true;
			direction = dir;
			return true;
		}
		return false;
	}
	
	public void Move(){
		if(direction){
			RightMove();
		}else LeftMove();
	}
	
	public void LeftScrollMove(){
		x -= SCROLL_SPEED;
	}
	
	public void RightScrollMove(){
		x += SCROLL_SPEED;
	}
	
	public abstract void LeftMove();
	public abstract void RightMove();
	public abstract void Draw(Graphics g);
	
}