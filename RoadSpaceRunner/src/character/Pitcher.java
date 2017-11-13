package character;

import java.awt.Color;
import java.awt.Graphics;

import StaticFinalField.StaticFinalField;

public class Pitcher extends Enemy implements StaticFinalField {
	private int jumpTime;//ジャンプまでの待機時間のカウント
	private int jumpHeight;//ジャンプで上昇した距離をカウント
	private boolean jumpSwitch;//ジャンプスイッチ
	private boolean jumpState;//ジャンプ中の時true
	
	private static final int WIDTH = 20, HEIGHT = 40;
	private static final double MOVE_SPEED = 1.00;
	private static final int JUMP_LATENCY = 60;//ジャンプまでの待機時間
	private static final int JUMP_TIME = 30;//ジャンプ時間
	private static final int ATTACK_WAIT = 120;//攻撃までの待機時間
	private static final int JUMP_SPEED = 2;//ジャンプスピード
	private static final int BALL_ELEMENT_COUNT = 3;//Ball配列の要素数
	private static final int DEFAULT_LIFE = 1;
	private static final int DEFAULT_DAMEGE = 1;
	
	public Pitcher(){
	}
	
	public Pitcher(int x, int y){
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
		jumpTime = 0;
		attackTime = 0;
		jumpSwitch = false;
		jumpState = false;
		groundDec = false;
		fly = true;
		leftCollision = true;
		rightCollision = true;
		ballOrder = 0;
		life = DEFAULT_LIFE;
		alive = true;
		direction = false;
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
	protected void Move(){
		//体の向きが一致してなければ変更
		if(!(leftCollision)){
			direction = true;
		}else if(!(rightCollision)){
			direction = false;
		}
		//右方向に進む
		if(direction)x += MOVE_SPEED;
		//左方向に進む	
		else x -= MOVE_SPEED;
		
		//タイマーの更新
		jumpTime++;
		attackTime++;
		
		//一定時間後にジャンプする
		if(groundDec && jumpTime > JUMP_LATENCY){
			jumpSwitch = true;//ジャンプさせる
			jumpTime = 0;//ジャンプタイマーの初期化
		}else if(!(groundDec) && jumpTime > JUMP_LATENCY){
			//ジャンプするタイミングで接地していなければ
			jumpTime = 0;//ジャンプタイマーの初期化、見送り
		}else jumpSwitch = false;
		if(jumpSwitch){jumpState = true;}//スイッチが押され、ジャンプ状態に以降
		if(jumpState){
			Jump();//Y値が下がり、上昇動作をする
		}else if(!(groundDec) && !(jumpState)){
			Fall();//ジャンプ状態でなく、接地してなければ落下
		}
		
		//一定時間後に攻撃をする
		if(attackTime > ATTACK_WAIT){
			Attack();
			attackTime = 0;//アタックタイマーの初期化
		}
	}
	
	private void Jump(){
		if(jumpHeight < JUMP_TIME){
			y -= JUMP_SPEED;
			jumpHeight++;
		}else JumpReset();
	}
	
	private void JumpReset(){
		jumpHeight = 0;//高度の初期化
		fly = true;//ジャンプ可能にリセット
		jumpState = false;//非ジャンプ状態にリセット
	}
	
	//ダメージ
	@Override
	public void Damege(){
		life -= DEFAULT_DAMEGE;
		if(life <= 0){
			alive = false;
		}
	}
	
	@Override
	public void Draw(Graphics g){
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

	@Override
	protected void Attack(){
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
}