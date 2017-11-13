package map;

import java.awt.Color;
import java.awt.Graphics;

import StaticFinalField.StaticFinalField;

public class Block extends SuperMap implements StaticFinalField{
	private static final int WIDTH = MAP_CHIP_SIZE, HEIGHT = MAP_CHIP_SIZE;
	
	public Block(){
	
	}
	public Block(int x, int y){
		super(x, y);
	}

	/***************
	 * 	Accessor
	 ***************/
	public int getX(){return this.x;}
	public int getXsize(){return x + WIDTH;}
	
	/***************
	 * 	Move
	 ***************/
	public void LeftMove(){
		x -= SCROLL_SPEED;
	}
	public void RightMove(){
		x += SCROLL_SPEED;
	}
	
	/***************
	 * 	Decision
	 ***************/
	//上部当たり判定
	public boolean TopDec(int x, int y, int width){
		//引数の対象の左下と右下の判定
		if(this.x + 1 <= x && this.x - 1 + WIDTH >= x || this.x + 1 <= x + width && this.x - 1 + WIDTH >= x + width){
			if(this.y <= y && this.y + HEIGHT / 4 >= y){
				return true;//ブロック上半分の範囲に入っている
			}
		}
		return false;
	}
	
	//左部当たり判定
	public boolean LeftDec(int x, int y, int width, int height){
		//引数の対象の右上と右下の判定
		if(this.y + 1 <= y && this.y - 1 + HEIGHT >= y || this.y + 1 <= y + height && this.y - 1 + HEIGHT >= y){
			if(this.x <= x + width && this.x + WIDTH / 4 >= x + width){
				return true;//ブロック左半分の範囲に入っている
			}
		}
		return false;
	}
	
	//右部当たり判定
	public boolean RightDec(int x, int y, int width, int height){
		//引数の対象の左上と左下の判定
		if(this.y + 1 <= y && this.y - 1 + HEIGHT >= y || this.y + 1 <= y + height && this.y - 1 + HEIGHT >= y){
			if(this.x + WIDTH / 4 <= x && this.x + WIDTH >= x){
				return true;//ブロック右半分の範囲に入っている
			}
		}return false;
	}
	
	//下部当たり判定
	public boolean BottomDec(int x, int y, int width){
		//引数の対象の右上と左上の判定
		if(this.x + 1 <= x && this.x - 1 + WIDTH >= x || this.x + 1 <= x + width && this.x - 1 + WIDTH >= x + width){
			if(this.y + HEIGHT >= y && this.y + HEIGHT / 4 <= y){
				return true;//ブロック下半分の範囲に入っている
			}
		}return false;
	}
	
	@Override
	public void Draw(Graphics g){
		g.setColor(Color.DARK_GRAY);
		g.fillRect(x, y, WIDTH, HEIGHT);
	}
}
