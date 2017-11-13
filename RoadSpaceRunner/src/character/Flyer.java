package character;

import java.awt.Color;
import java.awt.Graphics;

public class Flyer extends Enemy {
	private boolean moveSwitch;
	private int moveCount;//Amount of movement
	private static final int WIDTH = 20, HEIGHT = 40;
	private static final double MOVE_SPEED = 1.0;
	private static final int ATTACK_WAIT = 120;//攻撃までの待機時間
	private static final int BALL_ELEMENT_COUNT = 3;//Ball配列の要素数
	private static final int DEFAULT_LIFE = 1;
	private static final int DEFAULT_DAMEGE = 1;
	private static final int MOVE_LIMIT = 120;
	
	public Flyer() {
		// TODO Auto-generated constructor stub
	}

	public Flyer(int x, int y) {
		super(x, y);
		InitState();
		//Instance of ball to use
		ball = new Ball[BALL_ELEMENT_COUNT];
		for(int i = 0; i < BALL_ELEMENT_COUNT; i++){
			ball[i] = new StraightBall(x, y, direction);
		}
	}
	
	@Override
	public void InitState() {
		this.x = defaultX;
		this.y = defaultY;
		attackTime = 0;
		groundDec = true;
		fly = true;
		leftCollision = true;
		rightCollision = true;
		ballOrder = 0;
		life = DEFAULT_LIFE;
		alive = true;
		direction = false;
		moveSwitch = true;
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
	protected void Move() {
		//体の向きが一致してなければ変更
		if(x < 240){
			direction = true;
		}else{
			direction = false;
		}
		//下へ動く
		if(moveSwitch)y += MOVE_SPEED;
		else y -= MOVE_SPEED;
				
		//動く方向の切り替え
		if(moveCount++ > MOVE_LIMIT){
			if(moveSwitch)moveSwitch = false;
			else moveSwitch = true;
			moveCount = 0;
		}
		attackTime++;
		
		//一定時間後に攻撃をする
		if(attackTime > ATTACK_WAIT){
			Attack();
			attackTime = 0;//アタックタイマーの初期化
		}
	}

	@Override
	protected void Attack() {
		if(ballOrder < BALL_ELEMENT_COUNT){
			if(direction){
				ball[ballOrder] = new StraightBall(x + WIDTH, y + HEIGHT / 3, direction, false);
			}else{
				ball[ballOrder] = new StraightBall(x, y + HEIGHT / 3, direction, false);
			}
			ballOrder++;
		}else if(ballOrder >= BALL_ELEMENT_COUNT){
			ballOrder = 0;
			if(direction){
				ball[ballOrder] = new StraightBall(x + WIDTH, y + HEIGHT / 3, direction, false);
			}else{
				ball[ballOrder] = new StraightBall(x, y + HEIGHT / 3, direction, false);
			}
		}	
	}
	@Override
	public void Damege() {
		life -= DEFAULT_DAMEGE;
		if(life <= 0){
			alive = false;
		}
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
