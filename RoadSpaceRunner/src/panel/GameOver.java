package panel;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

public class GameOver extends SuperPanel{
	private boolean remaining;
	private int stateCount;
	private static final int NEXT_PANEL = 60;//次のパネルへ行くまでの時間
	
	public GameOver() {
		super();
		InitState();
		remaining = false;
	}

	public void setRemaining(boolean b){remaining = b;}
	public boolean getRemaining(){return remaining;}

	public void StateReport(){
		stateCount++;
		if(stateCount > NEXT_PANEL){
			state = false;
		}
	}
	
	public void InitState(){
		stateCount = 0;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		g.drawString(remaining ? "GAME OVER" : "Miss", D_XSIZE / 2, D_YSIZE / 3);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()){
		case KeyEvent.VK_ENTER:
		case KeyEvent.VK_SPACE:
			state = false;
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
