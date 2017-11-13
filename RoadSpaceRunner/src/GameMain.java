import java.awt.Container;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import StaticFinalField.StaticFinalField;
import panel.GameClear;
import panel.GameOver;
import panel.StagePanel;
import panel.S1Panel;
import panel.S2Panel;
import panel.S3Panel;
import panel.SuperPanel;
import panel.TitlePanel;

public final class GameMain extends JFrame implements Runnable, StaticFinalField{
	private static boolean initialize;
	private static int beforePanel;
	
	//GameState
	private static final int 
		GAME_SCENE_TOTAL = 6,
		GAME_TITLE = 0,
		GAME_S1 = 1,
		GAME_S2 = 2,
		GAME_S3 = 3,
		GAME_CLEAR = 4,
		GAME_OVER = 5;
	
	private int gameState;//パネルの切り替え
	
	Thread t;
	Container cp;
	SuperPanel[] sp;//Panel
	
	static{
		initialize = true;
		beforePanel = -1;
	}
	
	GameMain(String title){
		//Frame initialize.
		setTitle(title);
		setSize(D_XSIZE,D_YSIZE);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		cp = getContentPane();
		
		//Instance the required panel.
		sp = new SuperPanel[GAME_SCENE_TOTAL];
		for(int i = 0; i < sp.length; i++){
			switch(i){
			case GAME_TITLE:
				sp[i] = new TitlePanel();
				break;
			case GAME_S1:
				sp[i] = new S1Panel();
				break;
			/*case GAME_S2:
				sp[i] = new S2Panel();
				break;*/
			case GAME_S3:
				sp[i] = new S3Panel();
				break;
			case GAME_CLEAR:
				sp[i] = new GameClear();
				break;
			case GAME_OVER:
				sp[i] = new GameOver();
				break;
			}
		}
		
		cp.add(sp[GAME_TITLE]);//Title画面をセット
		addKeyListener(sp[GAME_TITLE]);
		gameState = GAME_TITLE;//Title画面からスタート
		
		//Thread start
		t = new Thread(this);
		t.start();
	}
	
	/*************************
	 * 			run
	 *************************/
	@Override
	public void run() {
		while(true){
			try{
				t.sleep(GAME_TIME);//GAME_TIMEms毎に実施
			}catch(InterruptedException e){}
			
			switch(gameState){
			case GAME_TITLE:
				if(sp[GAME_TITLE].getState()){
					//タイトル画面表示だけのため何もしない
				}else {
					NextPanel(sp[GAME_TITLE], sp[GAME_S1], GAME_S1);
					beforePanel = GAME_TITLE;
				}
				break;
			case GAME_S1:
				if(sp[GAME_S1].getState()){
					if(initialize){
						sp[GAME_S1].InitState();
						initialize = false;
					}
					sp[GAME_S1].StateReport();//Stage1の状態をまとめて更新
				}else {
					if(((StagePanel)sp[GAME_S1]).getRemaining()){
						//GameOver
						NextPanel(sp[GAME_S1], sp[GAME_OVER], GAME_OVER);
						beforePanel = GAME_S1;
					}else{
						//GameClear
						NextPanel(sp[GAME_S1], sp[GAME_CLEAR], GAME_CLEAR);
						beforePanel = GAME_S1;
					}
					
				}
				break;
			case GAME_S2:
				if(sp[GAME_S2].getState()){
					if(initialize){
						sp[GAME_S2].InitState();
						initialize = false;
					}
					sp[GAME_S2].StateReport();//Stage1の状態をまとめて更新
				}else {
					if(((StagePanel)sp[GAME_S2]).getRemaining()){
						//GameOver
						NextPanel(sp[GAME_S2], sp[GAME_OVER], GAME_OVER);
						beforePanel = GAME_S2;
					}else{
						//GameClear
						NextPanel(sp[GAME_S2], sp[GAME_CLEAR], GAME_CLEAR);
						beforePanel = GAME_S2;
					}
					
				}
				break;
			case GAME_CLEAR:
				if(sp[GAME_CLEAR].getState()){
					if(initialize){
						sp[GAME_CLEAR].InitState();
						initialize = false;
					}
					sp[GAME_CLEAR].StateReport();
				}else {
					switch(beforePanel){
					case GAME_S1:
						NextPanel(sp[GAME_CLEAR], sp[GAME_S2], GAME_S2);
						break;
					case GAME_S2:
						NextPanel(sp[GAME_CLEAR], sp[GAME_S3], GAME_S3);
						break;
					case GAME_S3:
						NextPanel(sp[GAME_CLEAR], sp[GAME_TITLE], GAME_TITLE);
					}
					beforePanel = GAME_CLEAR;
				}
				break;
			case GAME_OVER:
				if(sp[GAME_OVER].getState()){
					if(initialize){
						sp[GAME_OVER].InitState();
						((GameOver)sp[GAME_OVER]).setRemaining(((StagePanel)sp[beforePanel]).getRemaining());
						initialize = false;
					}
					sp[GAME_OVER].StateReport();
				}else {
					if(((GameOver)sp[GAME_OVER]).getRemaining()){
						//残機なし
						NextPanel(sp[GAME_OVER], sp[GAME_TITLE], GAME_TITLE);
						beforePanel = GAME_OVER;
					}else{
						//残機あり
						NextPanel(sp[GAME_OVER], sp[beforePanel], beforePanel);
						beforePanel = GAME_OVER;
					}
				}
				break;
			default:
				//不意のバグ対策
				gameState = GAME_TITLE;
				break;
			}
			repaint();
		}
	}
	
	//runメソッドにadd...だのremove...だのを書くのが面倒くさいのでメソッドにしちゃった
	void RemAdd(JPanel remp, JPanel addp){	
		cp.remove(remp);
		removeKeyListener((KeyListener) remp);//キャスト先輩流石っす！
		cp.add(addp);
		addKeyListener((KeyListener)addp);//そこにシビれる!あこがれるゥ!
	}
	
	//次のパネルへの処理まとめ
	void NextPanel(SuperPanel remp, SuperPanel addp, int nextState){
		RemAdd(remp, addp);
		remp.setState(true);
		gameState = nextState;
		initialize = true;
	}
	
	/*************************
	 * 			main
	 *************************/
	public static void main(String[] args) {
		GameMain frame = new GameMain("RordSpaceRunner");
		frame.setVisible(true);
	}
}
