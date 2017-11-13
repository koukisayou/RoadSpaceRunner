package panel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

import StaticFinalField.StaticFinalField;

public class TitlePanel extends SuperPanel{
	//Constructor
	public TitlePanel(){
		super();
	}
	
	/*************************
	 * 		KeyListener
	 *************************/
	
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
	
	/*************************
	 * 		paintComponent
	 *************************/
	
	public void paintComponent(Graphics g){
		Graphics2D g2 = (Graphics2D)g;
		g2.drawString("ロードスペランナー", D_XSIZE / 2, D_YSIZE / 3);
	}

	@Override
	public void StateReport() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void InitState() {
		// TODO Auto-generated method stub
		
	}
}
