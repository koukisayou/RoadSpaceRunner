package panel;

import Collision.CharacterCollision;
import character.BossSuper;
import character.CharacterSuper;
import character.Enemy;
import character.Player;
import character.S1Boss;
import map.SuperLift;
import map.SuperMap;
import map.UpDownLift;

public abstract class StagePanel extends SuperPanel{
	protected static boolean remaining;//true = 残機なし, false = 残機あり
	protected int edgeCount;//ステージ上xの現在地
	protected int warpx, warpy;//ワープポイント座標
	protected int defaultWarpx, defaultWarpy;
	protected boolean bossFloor;//ボスフロアに居るか
	protected SuperMap[] SM;
	protected CharacterSuper[] CS;
	protected CharacterCollision CC = new CharacterCollision();
	

	protected static final int PLAYER_NUMBER = 0;	//キャラクター配列でのプレイヤーのindex
	protected static final int BOSS_NUMBER = 1;			//キャラクター配列でのボスのindex
	
	static{
		remaining = false;
	}
	
	//Constructor
	public StagePanel() {
		super();
		edgeCount = 0;
		bossFloor = false;
	}

	@Override
	public void InitState(){
		remaining = false;
		for(int i = 0; i < SM.length; i++){
			SM[i].InitState();
		}
		for(int i = 0; i < CS.length; i++){
			CS[i].InitState();
		}
		edgeCount = 0;
		warpx = defaultWarpx;
		warpy = defaultWarpy;
	}
	public boolean getRemaining(){return remaining;}
	
	//マップをスクロールさせる
	protected void Scroll(){
		if(!(bossFloor)){
			switch(((Player)CS[PLAYER_NUMBER]).ScrollReport()){
			case 1:
				//右方向へスクロール
				for(int i = 0; i < SM.length; i++){
					SM[i].LeftMove();
				}
				for(int i = 0; i < CS.length; i++){
					if(!(CS[i] instanceof Player) && !(CS[i] instanceof BossSuper)){
						((Enemy)CS[i]).LeftScrollMove();
					}
				}
				warpx -= SCROLL_SPEED;
				edgeCount += SCROLL_SPEED;
				break;
			case 2:
				//左方向へスクロール
				for(int i = 0; i < SM.length; i++){
					SM[i].RightMove();
				}
				for(int i = 0; i < CS.length; i++){
					if(!(CS[i] instanceof Player) && !(CS[i] instanceof BossSuper)){
						((Enemy)CS[i]).RightScrollMove();
					}
				}
				
				warpx += SCROLL_SPEED;
				edgeCount -= SCROLL_SPEED;
				break;
			}
		}
	}
	//マップをスクロールさせなければならないか
	protected abstract void CheckScroll();
	/*************************
	 * 		Decision
	 *************************/
	//移動制御の判定をまとめて実行
	public void MapDecision(){
		for(int i = 0; i < CS.length; i++){
			CS[i].setLoopEnd(false);
			for(int j = 0; j < SM.length; j++){
				//落下
				if(SM[j].TopDec(CS[i].getX(), CS[i].getBottom(), CS[i].getWIDTH())){
					CS[i].setLoopEnd(true);
					CS[i].setGroundDecision(true);
					if(SM[j] instanceof SuperLift){
						if(SM[j] instanceof UpDownLift){
							CS[i].setYMove(((SuperLift)SM[j]).getYMove());
						}
					}
				}else CS[i].setGroundDecision(false);	
				if(CS[i].getLoopEnd())break;
			}
			if(CS[i].getLoopEnd())continue;
		}
		for(int i = 0; i < CS.length; i++){
			CS[i].setLoopEnd(false);
			for(int j = 0; j < SM.length; j++){
				//右側
				if(SM[j].LeftDec(CS[i].getX(), CS[i].getY(), CS[i].getWIDTH(), CS[i].getHEIGHT())){
					CS[i].setLoopEnd(true);
					CS[i].setRightCollision(false);
				}else CS[i].setRightCollision(true);
				if(CS[i].getLoopEnd())break;
			}
			if(CS[i].getLoopEnd())continue;
		}
		for(int i = 0; i < CS.length; i++){
			CS[i].setLoopEnd(false);
			for(int j = 0; j < SM.length; j++){
				//左側
				if(SM[j].RightDec(CS[i].getX(), CS[i].getY(), CS[i].getWIDTH(), CS[i].getHEIGHT())){
					CS[i].setLoopEnd(true);
					CS[i].setLeftCollision(false);
				}else CS[i].setLeftCollision(true);
				if(CS[i].getLoopEnd())break;
			}
			if(CS[i].getLoopEnd())continue;
		}
		for(int i = 0; i < CS.length; i++){
			CS[i].setLoopEnd(false);
			for(int j = 0; j < SM.length; j++){
				//ジャンプ
				if(SM[j].BottomDec(CS[i].getX(), CS[i].getY(), CS[i].getWIDTH())){
					CS[i].setLoopEnd(true);
					CS[i].setFly(false);
				}else CS[i].setFly(true);
				if(CS[i].getLoopEnd())break;
			}
			if(CS[i].getLoopEnd())continue;
		}
	}
	
	protected void PlayerAttackDecision(int i){
		if(i == BOSS_NUMBER){
			((S1Boss)CS[i]).PlayerAttackDecision(((Player)CS[PLAYER_NUMBER]).getXattack(),
					((Player)CS[PLAYER_NUMBER]).getYattack(),
					((Player)CS[PLAYER_NUMBER]).ATTACK_WIDTH,
					((Player)CS[PLAYER_NUMBER]).ATTACK_HEIGHT,
					((Player)CS[PLAYER_NUMBER]).getDirection(),
					((Player)CS[PLAYER_NUMBER]).getBallDirection());
		}else{
			((Enemy)CS[i]).PlayerAttackDecision(((Player)CS[PLAYER_NUMBER]).getXattack(),
				((Player)CS[PLAYER_NUMBER]).getYattack(),
				((Player)CS[PLAYER_NUMBER]).ATTACK_WIDTH,
				((Player)CS[PLAYER_NUMBER]).ATTACK_HEIGHT,
				((Player)CS[PLAYER_NUMBER]).getDirection(),
				((Player)CS[PLAYER_NUMBER]).getBallDirection());
		}
	}
	//ワープ当たり判定
	protected void WarpDecision(){
		if(Math.pow(CS[PLAYER_NUMBER].getXcenter() - warpx, 2) + Math.pow(CS[PLAYER_NUMBER].getYcenter() - warpy,  2) <= Math.pow(MAP_CHIP_SIZE / 2, 2)){
			BossFloorWarp();
			bossFloor = true;
		}
	}
	//全ブロックをワープ後の位置へ動かす
	protected abstract void BossFloorWarp();
	//プレイヤー死亡によるステージ終了判定
	protected void PlayerAlive(){
		if(!(CS[PLAYER_NUMBER].getAlive())){
			if(((Player)CS[PLAYER_NUMBER]).Death()){
				remaining = true;
				((Player)CS[PLAYER_NUMBER]).InitRemaining();
			}
			state = false;
		}
	}
		
}
