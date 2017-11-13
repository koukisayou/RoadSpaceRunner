package character;

import java.awt.Color;
import java.awt.Graphics;

import StaticFinalField.StaticFinalField;

public class Player extends CharacterSuper implements StaticFinalField{
	private int xcenter;							//x軸中点
	private int jumpHeight = 0;						//ジャンプで上昇した距離をカウント
	private int attackCount = 0;					//攻撃の後の待ち時間の保存
	private int attackState = 0;					//攻撃の状態保存。0 = 待機, 1 = 攻撃中, 2 = 攻撃不可
	private int damegeCount = 0;					//被ダメージ後の無敵時間カウント
	private int ballDirection;						//ボールの打ち分け方向 0 = Straight, 1 = Parabora, 2 = Bound
	private static int remaining;					//プレイヤー残機
	private boolean jumpState;						//ジャンプの状態判定
	private boolean jumpScale;						//ジャンプの大きさ判定。trueで大ジャンプ、falseで小ジャンプ
	private boolean leftmove, rightmove;			//Move switch
	private boolean direction;						//体の向き true = 右, false = 左
	private boolean leftCollision,rightCollision;	//壁衝突判定
	private boolean attack;							//攻撃スイッチ
	private boolean damege;							//被ダメージスイッチ
	private boolean flashing;						//被ダメージ無敵時間中の点滅スイッチ
	private boolean positionCenter;					//画面中央にいるかいないか
	
	private static final int WIDTH = 25, HEIGHT = 30;				//座標の横幅、縦幅
	private static final int MOVE_SPEED = 3;						//移動速度
	private static final int ATTACK_X = 10, ATTACK_Y = 10;			//攻撃時当たり判定x,y座標
	public  static final int ATTACK_WIDTH = 30, ATTACK_HEIGHT = 10;	//攻撃時当たり判定横幅、縦幅
	private static final int ATTACK_TIME = 20, ATTACK_LATENCY = 30;	//攻撃後の当たり判定時間と攻撃不可時間
	private static final int JUMP_SPEED = 5;						//ジャンプ時の上昇スピード
	private static final int JUMP_TIME_SMALL = 15;					//小ジャンプする時間
	private static final int JUMP_TIME_BIG = 30;					//大ジャンプする時間
	private static final int DAMEGE_STAY = 30;						//被ダメージ後の無敵時間
	private static final int DEFAULT_DAMEGE = 1;					//基本被ダメージ
	private static final int DEFAULT_LIFE = 3;						//初期体力
	private static final int DEATH_LIFE = 0;						//死亡判定のHP
	private static final int DEFAULT_REMAINING = 2;					//初期残機
	
	static{
		remaining = DEFAULT_REMAINING;//残機数の設定
	}
	
	//コンストラクタ
	public Player(){
		
	}
	
	//座標の初期化を行えるコンストラクタ
	public Player(int x, int y){
		super(x,y);
		InitState();
	}

	//ステージ読み込み時、座標の初期化をまとめて行う
	@Override public void InitState(){
		this.x = defaultX;
		this.y = defaultY;
		loopend = false;
		alive = true;
		xcenter = x + WIDTH / 2;
		leftmove = false;
		rightmove = false;
		jumpState = false;
		jumpScale = false;
		groundDec = false;
		leftCollision = true;
		rightCollision = true;
		direction = true;
		attack = false;
		life = DEFAULT_LIFE;
		flashing = true;
		positionCenter = false;
		damege = false;
		ballDirection = 0;
	}
	
	//状態の更新、報告
	public void StateReport(){
		if(alive){
			if(!(positionCenter))Xmove();
			Ymove();
			setXcenter();
			Attack();
			Invincible();
			CheckCenter();
		}
	}
	
	//画面のスクロールが必要かを報告
	public int ScrollReport(){
		if(positionCenter && rightmove && rightCollision){
			if(!(direction))direction = true;//体の向きを変更
			return 1;//右方向へスクロール
		}else if(positionCenter && leftmove && leftCollision){
			if(direction)direction = false;//体の向きを変更
			return 2;//左方向へスクロール
		}else return 0;//スクロールしない
	}
	
	/*********************
	 * 		Accessor
	 *********************/
	public void setAttack(boolean b){attack = b;}
	protected void setXcenter(){xcenter = x + WIDTH / 2;}
	public int getXsize(){return x + WIDTH;}
	public int getY(){return y;}
	public double getXcenter(){return x + WIDTH / 2;}
	public double getYcenter(){return y + HEIGHT / 2;}
	public double getRadius(){return WIDTH > HEIGHT ? WIDTH / 2 : HEIGHT / 2;}
	public int getAttackState(){return attackState;}
	public int getXattack(){
		if(direction)return xcenter + ATTACK_X;
		else return xcenter - ATTACK_X - ATTACK_WIDTH;
	}
	public int getYattack(){return y + ATTACK_Y;}
	public boolean getDirection(){return direction;}
	public void setPositionCenter(boolean b){positionCenter = b;}
	public boolean isPositionCenter(){return positionCenter;}
	public void setBallDirection(int i){ballDirection = i;}
	public int getBallDirection(){return ballDirection;}
	@Override public int getBottom(){return y + HEIGHT;}
	@Override public int getWIDTH(){return WIDTH;}
	@Override public int getHEIGHT(){return HEIGHT;}
	@Override public void setGroundDecision(boolean b){groundDec = b;}
	@Override public void setLeftCollision(boolean b){leftCollision = b;}
	@Override public void setRightCollision(boolean b){rightCollision = b;}
	
	/*********************
	 * 		Move
	 *********************/
	//X軸移動のまとめ
	protected void Xmove(){
		//左右の移動,X軸移動に関する条件式
		if(leftCollision && leftmove){
			x -= MOVE_SPEED;
			if(direction)direction = false;//体の向きを変更
		}
		if(rightCollision && rightmove){
			x += MOVE_SPEED;
			if(!(direction))direction = true;//体の向きを変更
		}
	}
	
	public void LeftMoveOn(){leftmove = true;}
	public void LeftMoveOff(){leftmove = false;}
	public void RightMoveOn(){rightmove = true;}
	public void RightMoveOff(){rightmove = false;}
	
	//Y軸移動のまとめ
	protected void Ymove(){
		//ジャンプや落下など、Y軸移動に関する条件式
		if(jumpState && !(jumpScale)){
			//まず小ジャンプ
			if(jumpHeight < JUMP_TIME_SMALL && fly){
				y -= JUMP_SPEED;
				jumpHeight++;
			}else{
				JumpReset();
			}
		}else if(jumpState && jumpScale){
			//続いて大ジャンプ
			if(jumpHeight < JUMP_TIME_BIG && fly){
				y -= JUMP_SPEED;
				jumpHeight++;
			}else{
				JumpReset();
			}
		}else if(!(groundDec) && !(jumpState)){
			//ジャンプしてなく接地してなければ当然落ちる
			Fall();
		}
	}

	//ジャンプ
	public void Jump(){
		if(groundDec && !(jumpState)){
			//接地状態からジャンプ状態へ
			jumpState = true;
			groundDec = false;
		}else if(!(groundDec) && jumpState){
			//2度目以降の呼び出しで大ジャンプに切り替え
			jumpScale = true;
		}
	}
	
	//ジャンプ周りの変数の初期化
	public void JumpReset(){
		jumpScale = false;//小ジャンプにリセット
		jumpState = false;//非ジャンプ状態にリセット
		fly = true;//ジャンプ可能にリセット
		jumpHeight = 0;//高度の初期化
	}
	
	//落下
	@Override
	protected void Fall(){
		if(!(groundDec)){
			y += FALL_SPEED;
			//画面下に落下すると即死
			if(y > D_YSIZE){
				life = 0;
				alive = false;
			}
		}
	}
	
	//プレイヤーが画面中央付近にいるかチェック
	private void CheckCenter(){
		if(D_XSIZE / 2 - 2 < xcenter && xcenter < D_XSIZE / 2 + 2){
			setPositionCenter(true);
		}
	}
	
	/*********************
	 * 	Attack&Damege
	 *********************/
	//攻撃
	public void Attack(){
		if(attack){
			attackCount++;
			if(attackCount < ATTACK_TIME){
				//AttackDecision();
				attackState = 1;
			}else if(attackCount >= ATTACK_TIME && attackCount < ATTACK_LATENCY){
				attackState = 2;
			}else if(attackCount > ATTACK_LATENCY)attack = false;
		}else{
			attackCount = 0;
			attackState = 0;
		}
	}

	//ダメージ
	@Override
	public void Damege(){
		if(!(damege)){
			damege = true;
			life -= DEFAULT_DAMEGE;
			if(life < DEATH_LIFE){
				alive = false;
			}
		}
	}
	
	//ダメージ(引数あり)
	public void Damege(int dam){
		if(!(damege)){
			damege = true;
			life -= dam;
		}
	}
	
	//被ダメージ後の無敵時間
	private void Invincible(){
		if(damege && damegeCount < DAMEGE_STAY){
			//無敵中。キャラ絵点滅。
			damegeCount++;
			if(flashing) flashing = false;
			else flashing = true;
		}else if(damege && damegeCount >= DAMEGE_STAY){
			//無敵終了
			damege = false;
			damegeCount = 0;
			flashing = true;
		}
	}
	
	//死亡時の残機減少
	public boolean Death(){
		remaining--;
		if(remaining <= 0){
			return true;//残機が無くなった
		}else return false;//残機がまだある
	}
	
	//Remaining Reset
	public void InitRemaining(){
		remaining = DEFAULT_REMAINING;
	}
	
	//描画
	public void Draw(Graphics g){
		if(jumpScale)g.drawString("Big", 300, 40);//テスト用。大ジャンプ時表示
		
		//体の向きの確認のため、色を変える
		if(direction)g.setColor(Color.RED);
		else g.setColor(Color.BLUE);
		if(flashing){
			//被ダメージ時の点滅の為のif文
			g.fillRect(x, y, WIDTH, HEIGHT);
		}
		
		//テスト用。攻撃の描画
		if(attackState != 0){
			if(attackState == 1)g.setColor(Color.GREEN);
			else if(attackState == 2)g.setColor(Color.PINK);
			if(direction)g.fillRect(xcenter + ATTACK_X, y + ATTACK_Y, ATTACK_WIDTH, ATTACK_HEIGHT);
			else g.fillRect(xcenter - ATTACK_X - ATTACK_WIDTH, y + ATTACK_Y, ATTACK_WIDTH, ATTACK_HEIGHT);
		}
	}
}