package panel;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import Collision.CharacterCollision;
import character.BossSuper;
import character.Cannon;
import character.CharacterSuper;
import character.Enemy;
import character.Pitcher;
import character.Player;
import character.S1Boss;
import map.Block;
import map.SuperLift;
import map.SuperMap;
import map.UpDownLift;

public final class S1Panel extends StagePanel{
	
	private static final int BOSS_FLOOR = 30 * 56;		//ボスフロア移動時のX座標減算値
	private static final int UNIQUE_CHARACTER = 2;		//ユニークキャラクターの数
	private static final int SCREEN_EDGE_FRONT = 0;		//ステージ端手前
	private static final int SCREEN_EDGE_BACK = 1000;	//ステージ端奥
	
	
	//コンストラクタ
	public S1Panel(){
		super();
		int blockCount = 0;
		int characterCount = 0;
		int[][] map = {
			//0 = null, 1 = Block, 2 = Player, 3 = Pitcher, 4 = Cannon, 5 = Boss, 6 = UpDownLift, 7 = WarpPoint
			//D_YSIZE = 480 / MapChipSize = 30 で調整中
		//   0  1  2  3  4  5  6  7  8  9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31 32 33 34 35 36 37 38 39 40 41 42 43 44 45 46 47 48 49 50 51 52 53 54 55 56 57 58 59 60 61 62 63 64 65 66 67 68 69 70 71 72 73 74 75 76 77
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,},//[0][]
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 1, 1, 1, 1, 1, 1, 1, 1, 1,},//[1][]
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1,},//[2][]
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1,},//[3][]
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 1, 1,},//[4][]
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 6, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1,},//[5][]
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 1, 1, 1, 1,},//[6][]
			{0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 7, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1,},//[7][]
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,},//[8][]
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 0, 6, 0, 0, 0, 0, 0, 6, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,},//[9][]
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,},//[10][]
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,},//[11][]
			{0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1,},//[12][]
			{0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1,},//[13][]
			{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,},//[14][]
		};
		
		//クラスの参照型を作る為、マップチップの合計値をチェック
		for(int i = 0; i < map.length; i++){
			for(int j = 0; j < map[i].length; j++){
				switch(map[i][j]){
				case 1:
				case 6:
					blockCount++;
					break;
				case 2:
				case 3:
				case 4:
				case 5:
					characterCount++;
					break;
				case 7:
					warpx = (j * 30) + (MAP_CHIP_SIZE / 2);
					warpy = (i * 30) + (MAP_CHIP_SIZE / 2);
					defaultWarpx = warpx;
					defaultWarpy = warpy;
					break;
				}
			}
		}
		
		//参照型に配列の要素数を代入
		SM = new SuperMap[blockCount];
		blockCount = 0;
		CS = new CharacterSuper[characterCount];
		characterCount = 0;
		
		//instance
		for(int i = 0; i < map.length; i++){
			for(int j = 0; j < map[i].length; j++){
				switch(map[i][j]){
				case 1:
					SM[blockCount] = new Block(j * 30, i * 30);
					blockCount++;
					break;
				case 2:
					CS[PLAYER_NUMBER] = new Player(j * 30, i * 30);
					break;
				case 3:
					CS[characterCount + UNIQUE_CHARACTER] = new Pitcher(j * 30, i * 30);
					characterCount++;
					break;
				case 4:
					CS[characterCount + UNIQUE_CHARACTER] = new Cannon(j * 30, i * 30);
					characterCount++;
					break;
				case 5:
					CS[BOSS_NUMBER] = new S1Boss(j * 30, i * 30);
					break;
				case 6:
					SM[blockCount] = new UpDownLift(j * 30, i * 30);
					blockCount++;
					break;
				}
			}
		}
	}
	@Override
	public void StateReport(){
		PlayerAlive();
		CheckScroll();
		
		//リフトを動かす
		for(int i = 0; i < SM.length; i++){
			if(SM[i] instanceof SuperLift)((SuperLift)SM[i]).StateReport();
		}
		
		//キャラクターのStateReportの実行
		for(int i = 0; i < CS.length; i++){
			if(CS[i] instanceof BossSuper){
				if(((BossSuper)CS[i]).StateReport(true)){
					state = false;
				}
			}else CS[i].StateReport();
			
			if(!(i == PLAYER_NUMBER) && !(i == BOSS_NUMBER) && CS[i] instanceof Enemy){
				//敵のボールがプレイヤーに当たっているかの判定
				if(CC.EPCollision((Enemy)CS[i], (Player)CS[PLAYER_NUMBER])){
					((Player)CS[PLAYER_NUMBER]).Damege();
				}
				//敵のボールとプレイヤーの攻撃の当たり判定へ
				if(((Player)CS[PLAYER_NUMBER]).getAttackState() == 1){
					PlayerAttackDecision(i);
				}
				//プレイヤーの返したボールと敵の当たり判定
				for(int j = 0; j < CS.length; j++){
					//j のボールは i に当たっているか
					if(!(j == PLAYER_NUMBER) && !(j == BOSS_NUMBER) && CS[j] instanceof Enemy){
						if(((Enemy)CS[j]).isBallDecision() && ((Enemy)CS[j]).EnemyBallDecision(CS[i].getXcenter(),CS[i].getYcenter(),CS[i].getRadius())){
							CS[i].Damege();
						}
					}else if(j == BOSS_NUMBER){
						if(((S1Boss)CS[j]).isBallDecision() && ((S1Boss)CS[j]).EnemyBallDecision(CS[i].getXcenter(),CS[i].getYcenter(),CS[i].getRadius())){
							CS[i].Damege();
						}
					}
				}
			}else if(i == BOSS_NUMBER){
				//Bossのボールがプレイヤーに当たっているかの判定
				if(((BossSuper)CS[i]).BallDecision(((Player)CS[PLAYER_NUMBER]).getXcenter(),((Player)CS[PLAYER_NUMBER]).getYcenter(),((Player)CS[PLAYER_NUMBER]).getRadius())){
					((Player)CS[PLAYER_NUMBER]).Damege();
				}
				
				//敵のボールとプレイヤーの攻撃の当たり判定へ
				if(((Player)CS[PLAYER_NUMBER]).getAttackState() == 1){
					PlayerAttackDecision(i);
				}
				
				//プレイヤーの返したボールと敵の当たり判定
				for(int j = 0; j < CS.length; j++){
					if(!(j == PLAYER_NUMBER) && !(j == BOSS_NUMBER) && CS[j] instanceof Enemy){
						if(((Enemy)CS[j]).isBallDecision()){
							((BossSuper)CS[i]).EnemyBallDecision((Enemy)CS[j]);
						}
					}else if(j == BOSS_NUMBER){
						if(((BossSuper)CS[j]).isBallDecision()){
							((BossSuper)CS[i]).EnemyBallDecision();
						}
					}
				}
			}
		}
		
		//マップのスクロール
		if(((Player)CS[PLAYER_NUMBER]).isPositionCenter() && !(bossFloor)){
			Scroll();
		}
		
		MapDecision();
		WarpDecision();
	}
	
	//マップをスクロールさせなければならないか
	@Override protected void CheckScroll(){	
		if(edgeCount < SCREEN_EDGE_FRONT || edgeCount > SCREEN_EDGE_BACK || bossFloor){
				((Player)CS[PLAYER_NUMBER]).setPositionCenter(false);
			}
	}
	//全ブロックをワープ後の位置へ動かす
	@Override protected void BossFloorWarp(){
		for(int i = 0; i < CS.length; i++){
			if(i == PLAYER_NUMBER){
				CS[i].InitState();
			}else{
				CS[i].WarpX(BOSS_FLOOR);
			}
		}
		for(int i = 0; i < SM.length; i++){
			SM[i].WarpX(BOSS_FLOOR);
		}
		warpx = defaultWarpx;
		warpy = defaultWarpy;
	}
	/*************************
	 * 		KeyListener
	 *************************/
	@Override public void keyTyped(KeyEvent e) {}

	@Override public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()){
			case KeyEvent.VK_X:
				((Player)CS[PLAYER_NUMBER]).setAttack(true);
				break;
			case KeyEvent.VK_ENTER:
				state = false;
				break;
			case KeyEvent.VK_SPACE:
				((Player)CS[PLAYER_NUMBER]).Jump();
				break;
			case KeyEvent.VK_RIGHT:
				((Player)CS[PLAYER_NUMBER]).RightMoveOn();
				break;
			case KeyEvent.VK_LEFT:
				((Player)CS[PLAYER_NUMBER]).LeftMoveOn();
				break;
			case KeyEvent.VK_UP:
				((Player)CS[PLAYER_NUMBER]).setBallDirection(1);
				break;
			case KeyEvent.VK_DOWN:
				((Player)CS[PLAYER_NUMBER]).setBallDirection(2);
				break;
		}
	}

	@Override public void keyReleased(KeyEvent e) {
		switch(e.getKeyCode()){
		case KeyEvent.VK_SPACE:
			//p.JumpReset();//コメントアウトを解くと任意のタイミングでジャンプを止めることができる
			break;
		case KeyEvent.VK_RIGHT:
			((Player)CS[PLAYER_NUMBER]).RightMoveOff();
			break;
		case KeyEvent.VK_LEFT:
			((Player)CS[PLAYER_NUMBER]).LeftMoveOff();
			break;
		case KeyEvent.VK_UP:
		case KeyEvent.VK_DOWN:
			((Player)CS[PLAYER_NUMBER]).setBallDirection(0);
			break;
		}
	}

	/*************************
	 * 		paintComponent
	 *************************/
	
	@Override public void paintComponent(Graphics g){
		Graphics2D g2 = (Graphics2D)g;
		g2.drawString("Stage1", D_XSIZE / 2, D_YSIZE / 3);
		
		//マップチップの描画
		for(int i = 0; i < SM.length; i++){
			SM[i].Draw(g2);
		}
		
		//キャラクターの描画
		for(int i = 0; i < CS.length; i++){
			CS[i].Draw(g2);
		}
	}
}
