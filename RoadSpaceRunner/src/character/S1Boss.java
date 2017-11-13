package character;

import java.awt.Color;
import java.awt.Graphics;

public class S1Boss extends BossSuper {
	private static final int WIDTH = 30, HEIGHT = 30;
	private static final int BOSS_CANNON = 6;//攻撃発射場所の数
	private static final int WEEK_POINT = 6;//弱点の数
	private static final int BALL_ELEMENT_COUNT = 5;//Ball配列の要素数
	private static final int ATTACK_WAIT = 120;//攻撃までの待機時間
	private int ballOrder;
	
	WeakPoint[] WP;
	BossCannon[] BC;
	
	public S1Boss() {/*Not use*/}

	public S1Boss(int x, int y) {
		super(x, y);

		ballOrder = 0;
		WP = new WeakPoint[WEEK_POINT];
		BC = new BossCannon[BOSS_CANNON];
		
		WP[0] = new WeakPoint(x + 30, y + 30, 0);
		WP[1] = new WeakPoint(x + 180, y + 60, 1);
		WP[2] = new WeakPoint(x + 30, y + 120, 1);
		WP[3] = new WeakPoint(x + 120, y + 150, 1);
		WP[4] = new WeakPoint(x + -30, y + 240, 1);
		WP[5] = new WeakPoint(x + 60, y + 330, 1);
		
		BC[0] = new BossCannon(x, y);
		BC[1] = new BossCannon(x + 240, y + 60);
		BC[2] = new BossCannon(x + 30, y + 90);
		BC[3] = new BossCannon(x + 240, y + 150);
		BC[4] = new BossCannon(x + 60, y + 240);
		BC[5] = new BossCannon(x + 60, y + 360);
	}
	
	@Override public void StateReport(){}
	
	public boolean StateReport(boolean foo){
		for(int i = 0; i < WP.length; i++){
			int j = 0;
			if(!(WP[i].StateReport())){
				j++;
				if(j == WP.length)return true;
			}
		}
		for(int i = 0; i < BC.length; i++){
			BC[i].StateReport();
		}
		return false;
	}

	
	
	@Override
	public void InitState() {
		this.x = defaultX;
		this.y = defaultY;
		ballOrder = 0;
		for(int i = 0; i < WP.length; i++){
			WP[i].InitState();
		}
		for(int i = 0; i < BC.length; i++){
			BC[i].InitState();
		}
	}
	
	@Override public double getXcenter(){return x + WIDTH / 2.0;}
	@Override public double getYcenter(){return y + HEIGHT / 2.0;}
	@Override public int getWIDTH(){return WIDTH;}
	@Override public int getHEIGHT(){return HEIGHT;}
	@Override public int getBottom(){return y + HEIGHT;}
	@Override public double getRadius(){return WIDTH > HEIGHT ? WIDTH / 2 : HEIGHT / 2;}

	@Override public void WarpX(int x){
		for(int i = 0; i < WP.length; i++){
			WP[i].WarpX(x);
		}
		for(int i = 0; i < BC.length; i++){
			BC[i].WarpX(x);
		}
	}
	
	//プレイヤーに当たっているか
	@Override public boolean BallDecision(double x, double y, double r){
		for(int i = 0; i < BC.length; i++){
			if(BC[i].BallDecision(x, y, r)){
				return true;
			}
		}
		return false;
	}
	
	//プレイヤーの攻撃にボールは当たっているか
	@Override public void PlayerAttackDecision(int x, int y, int width ,int height, boolean dir, int ballDirection){
		for(int i = 0; i < BC.length; i++){
			BC[i].PlayerAttackDecision(x, y, width, height, dir, ballDirection);
		}
	}
	
	//プレイヤーの攻撃になっているボールがあるのかチェック。あればtrue
	@Override public boolean isBallDecision(){
		for(int i = 0; i < BC.length; i++){
			if(BC[i].isBallDecision()){
				return true;
			}
		}
		return false;
	}
	
	//プレイヤーの攻撃にEnemyは当たっているか
	@Override public boolean EnemyBallDecision(double x, double y, double r){
		for(int i = 0; i < BC.length; i++){
			if(BC[i].EnemyBallDecision(x, y, r)){
				return true;
			}
		}
		return false;
	}
	
	@Override public void EnemyBallDecision(Enemy enemy){
		for(int i = 0; i < WP.length; i++){
			switch(WP[i].place){
			case 0://上半分
				if(EnemyBallDecision(WP[i].getXcenter(), WP[i].getYcenter(), WP[i].getRadius())){
					WP[i].Damege();
				}
				break;
			case 1://右半分
				if(EnemyBallDecision(WP[i].getXcenter(), WP[i].getYcenter(), WP[i].getRadius())){
					WP[i].Damege();
				}
				break;
			}
		}
	}
	
	@Override public void EnemyBallDecision(){
		for(int i = 0; i < WP.length; i++){
			switch(WP[i].place){
			case 0://上半分
				if(EnemyBallDecision(WP[i].getXcenter(), WP[i].getYcenter(), WP[i].getRadius())){
					WP[i].Damege();
				}
				break;
			case 1://右半分
				if(EnemyBallDecision(WP[i].x + WIDTH / 2, WP[i].y + HEIGHT / 2, WIDTH > HEIGHT ? WIDTH : HEIGHT)){
					WP[i].Damege();
				}
				break;
			}
		}
	}
	
	@Override
	public void Draw(Graphics g) {
		for(int i = 0; i < WP.length; i++){
			WP[i].Draw(g);
		}
		for(int i = 0; i < BC.length; i++){
			BC[i].Draw(g);
		}
	}

	public void Damege(int enemy) {
		WP[enemy].Damege();
	}

	/*******************
	 * WeakPoint Class
	 *******************/
	class WeakPoint{
		private int life = 2;
		private int x, y;
		private int defaultX, defaultY;
		private int place;//表示位置 0 = 上半分, 1 = 右半分
		
		WeakPoint(int x, int y, int place){
			this.x = x;
			this.y = y;
			defaultX = x;
			defaultY = y;
			this.place = place;
			alive = true;
		}
		
		public boolean StateReport(){
			return alive;
		}
		
		protected void InitState(){
			x = defaultX;
			y = defaultY;
			alive = true;
		}
		
		public double getXcenter(){return this.x + WIDTH / 2.0;}
		public double getYcenter(){return this.y + HEIGHT / 2.0;}
		public double getRadius(){return WIDTH > HEIGHT ? WIDTH / 2 : HEIGHT / 2;}
		
		
		public void WarpX(int x){this.x = defaultX - x;}
		
		//ダメージ
		public void Damege(){
			life--;
			if(life <= 0){
				alive = false;
			}
		}
		
		public void Draw(Graphics g){
			if(life > 0){
				//色を設定
				switch(life){
				case 1:
					g.setColor(Color.RED);
					break;
				case 2:
					g.setColor(Color.BLUE);
					break;
				}
				//描画位置を設定
				switch(place){
				case 0:
					g.fillRect(x, y, WIDTH, HEIGHT / 2);
					break;
				case 1:
					g.fillRect(x + WIDTH / 2, y, WIDTH / 2, HEIGHT);
					break;
				}
			}
		}
	}
	/*******************
	 * BossCannon Class
	 *******************/
	class BossCannon{
		private int x, y;
		private int defaultX, defaultY;
		public Ball[] ball;
		
		BossCannon(int x, int y){
			this.x = x;
			this.y = y;
			defaultX = x;
			defaultY = y;
			direction = false;
			
			ball = new Ball[BALL_ELEMENT_COUNT];
			for(int i = 0; i < BALL_ELEMENT_COUNT; i++){
				ball[i] = new StraightBall(x, y, direction);
			}
		}
		
		public void StateReport(){
			attackTime++;
			//一定時間後に攻撃をする
			if(attackTime > ATTACK_WAIT){
				Attack();
				attackTime = 0;//アタックタイマーの初期化
			}
			for(int i = 0; i < ball.length; i++){
				ball[i].StateReport();
			}
		}
		
		protected void InitState(){
			x = defaultX;
			y = defaultY;
			alive = true;
		}
		
		//プレイヤーに当たっているか
		public boolean BallDecision(double x, double y, double r){
			for(int i = 0; i < ball.length; i++){
				if(ball[i].Decision(x, y, r)){
					ball[i].initDelete();
					return true;
				}
			}
			return false;
		}
		
		//プレイヤーの攻撃にボールは当たっているか
		public void PlayerAttackDecision(int x, int y, int width ,int height, boolean dir, int ballDirection){
			for(int i = 0; i < ball.length; i++){
				//xの座標が当該するか判断
				if(x < ball[i].getXcenter() && x + width > ball[i].getXcenter()){
					if(ball[i].RectangleDecision(x, y, width, height, dir)){
						switch(ballDirection){
						case 0://Straight
							ball[i] = new StraightBall(ball[i].getX(), ball[i].getY(), dir, true);
							break;
						case 1://Parabora
							ball[i] = new ParaboraBall(ball[i].getX(), ball[i].getY(), dir, true);
							break;
						case 2://Bound
							break;
						}
					}
				}
			}
		}
		
		//プレイヤーの攻撃になっているボールがあるのかチェック。あればtrue
		public boolean isBallDecision(){
			for(int i = 0; i < ball.length; i++){
				if(ball[i].getEvil()){
					return true;
				}
			}
			return false;
		}
		
		//プレイヤーの攻撃にEnemyは当たっているか
		public boolean EnemyBallDecision(double x, double y, double r){
			for(int i = 0; i < ball.length; i++){
				if(ball[i].Decision(x, y, r)){
					if(ball[i].getEvil()){
						ball[i].initDelete();
						return true;
					}
				}
			}
			return false;
		}
		
		private void Attack(){
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
		
		public void WarpX(int x){this.x = defaultX - x;}
		public void Draw(Graphics g){
			g.setColor(Color.GREEN);
			g.fillRect(x + WIDTH / 2, y + HEIGHT / 3, WIDTH / 2, HEIGHT / 3);
			//ボール描画
			for(int i = 0; i < ball.length; i++){
				ball[i].Draw(g);
			}
		}
	}
	@Override
	protected void Move() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void Attack() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void Damege() {
		// TODO Auto-generated method stub
		
	}

}
