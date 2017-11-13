package character;

import java.awt.Color;
import java.awt.Graphics;

public class Cannon extends Enemy {
	private static final int WIDTH = 25, HEIGHT = 30;
	private static final int ATTACK_WAIT = 120;//攻撃までの待機時間
	private static final int BALL_ELEMENT_COUNT = 3;//Ball配列の要素数
	
	public Cannon() {
	}

	public Cannon(int x, int y) {
		super(x, y);
		ball = new Ball[BALL_ELEMENT_COUNT];
		for(int i = 0; i < BALL_ELEMENT_COUNT; i++){
			ball[i] = new ParaboraBall(x, y, direction);
		}
	}
	
	@Override
	public void InitState() {
		this.x = defaultX;
		this.y = defaultY;
		groundDec = false;
		fly = true;
		leftCollision = true;
		rightCollision = true;
		alive = true;
		direction = false;
	}

	@Override
	protected void Move() {
		//一定時間後に攻撃をする
		attackTime++;
		if(attackTime > ATTACK_WAIT){
			Attack();
			attackTime = 0;//アタックタイマーの初期化
		}
		
		if(!(groundDec)){
			Fall();//接地してなければ落下
		}
	}

	@Override
	public void Attack(){
		if(ballOrder < BALL_ELEMENT_COUNT){
			if(direction){
				ball[ballOrder] = new ParaboraBall(x + WIDTH, y + HEIGHT / 3, direction, false);
			}else{
				ball[ballOrder] = new ParaboraBall(x, y + HEIGHT / 3, direction, false);
			}
			ballOrder++;
		}else if(ballOrder >= BALL_ELEMENT_COUNT){
			ballOrder = 0;
			if(direction){
				ball[ballOrder] = new ParaboraBall(x + WIDTH, y + HEIGHT / 3, direction, false);
			}else{
				ball[ballOrder] = new ParaboraBall(x, y + HEIGHT / 3, direction, false);
			}
		}	
	}

	/*********************
	 * 		Accessor
	 *********************/
	@Override public double getXcenter(){return x + WIDTH / 2.0;}
	@Override public double getYcenter(){return y + HEIGHT / 2.0;}
	@Override public int getWIDTH(){return WIDTH;}
	@Override public int getHEIGHT(){return HEIGHT;}
	@Override public int getBottom(){return y + HEIGHT;}
	@Override public double getRadius(){return WIDTH > HEIGHT ? WIDTH / 2 : HEIGHT / 2;}

	@Override
	public void Damege() {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void Draw(Graphics g) {
		if(direction){
			g.setColor(Color.PINK);
		}else{
			g.setColor(Color.BLUE);
		}
		if(alive)g.fillRect(x, y, WIDTH, HEIGHT);
		//ボール描画
		for(int i = 0; i < ball.length; i++){
			ball[i].Draw(g);
		}
	}

}
