package character;

public abstract class Enemy extends CharacterSuper{
	protected int life;
	protected int ballOrder;//使われるボールの順番
	protected boolean alive;
	protected boolean active;
	protected boolean groundDec;//接地判定。trueで接地
	protected boolean rightCollision, leftCollision;
	protected int attackTime;//攻撃までの待機時間のカウント
	Ball ball[];
	
	public Enemy(){}
	
	public Enemy(int x, int y){
		super(x, y);
	}
	
	public void StateReport(){
		if(x > 0 && x < D_XSIZE){
			if(alive)Move();
		}
		for(int i = 0; i < ball.length; i++){
			ball[i].StateReport();
		}
	}
	
	/*********************
	 * 		Decision
	 *********************/
	//プレイヤーの攻撃になっているボールがあるのかチェック。あればtrue
	public boolean isBallDecision(){
		for(int i = 0; i < ball.length; i++){
			if(ball[i].getEvil()){
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

	/*********************
	 * 	  Move & Scroll
	 *********************/
	@Override
	protected void Fall(){
		y += FALL_SPEED;
	}
	
	public void LeftScrollMove(){
		x -= SCROLL_SPEED;
		for(int i = 0; i < ball.length; i++){
			ball[i].LeftScrollMove();
		}
	}
	
	public void RightScrollMove(){
		x += SCROLL_SPEED;
		for(int i = 0; i < ball.length; i++){
			ball[i].RightScrollMove();
		}
	}

	/*********************
	 * 		Accessor
	 *********************/
	public void setGroundDecision(boolean b){groundDec = b;}
	public void setRightCollision(boolean b){rightCollision = b;}
	public void setLeftCollision(boolean b){leftCollision = b;}
	
	/*********************
	 * 		Abstract
	 *********************/
	protected abstract void Move();//動き
	protected abstract void Attack();//攻撃
}