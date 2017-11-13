package panel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

import StaticFinalField.StaticFinalField;

public abstract class SuperPanel extends JPanel implements KeyListener, StaticFinalField{
	protected boolean state;//Panelの有効無効
	
	public SuperPanel() {
		setLayout(null);
		setBounds(10, 10, D_XSIZE, D_YSIZE);
		setBackground(Color.GREEN);
		state = true;
	}
	
	public abstract void StateReport();//状態の報告、更新
	public abstract void InitState();//状態の初期化
	
	/*************************
	 * 		Accessor
	 *************************/
	public boolean getState(){return state;}
	public void setState(boolean state){this.state = state;}

	/*************************
	 * 		KeyListener
	 *************************/
	public abstract void keyTyped(KeyEvent e);
	public abstract void keyPressed(KeyEvent e);
	public abstract void keyReleased(KeyEvent e);
	
	/*************************
	 * 		paintComponent
	 *************************/
	public abstract void paintComponent(Graphics g);
}
