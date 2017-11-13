package character;

import java.awt.Graphics;

public abstract class BossSuper extends Enemy {

	public BossSuper() {
		// TODO Auto-generated constructor stub
	}

	public BossSuper(int x, int y) {
		super(x, y);
		// TODO Auto-generated constructor stub
	}

	@Override public abstract void StateReport();
	public abstract boolean StateReport(boolean foo);
	public abstract void EnemyBallDecision();
	public abstract void EnemyBallDecision(Enemy enemy);
}
