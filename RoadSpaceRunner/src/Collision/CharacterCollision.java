package Collision;

import character.CharacterSuper;
import character.Enemy;
import character.Player;
import character.Ball;

public class CharacterCollision extends SuperCollision {

	private CharacterSuper[] CS;
	private Ball[] ball;
	
	public CharacterCollision() {
	}

	//Enemyの攻撃がPlayerに当たっているか判定(攻撃 = 点, Player = 円)
	public boolean EPCollision(Enemy enemy, Player player){
		if(enemy.BallDecision(player.getXcenter(),player.getYcenter(),player.getRadius())){
			return true;
		}
		return false;
	}
}
