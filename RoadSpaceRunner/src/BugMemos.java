/*発生したバグのメモ
 * 
 * 大ジャンプにならない時がある
 * 		他の入力にイベントを上書きされているため
 * 敵の攻撃を出現後すぐに打ち返すと、ボールが残り、それに触れるとダメージを受ける
 * 
 * 
 * 
 * 
 */

/*普通にメモ
 * 10/4
 * 		ボスクラスは内部クラスにEnemyClassを継承させたほうが良くね？
 * 		敵とプレイヤーの攻撃（ボール）の移動速度は敵の攻撃を遅く、プレイヤーの攻撃を早くしたい
 * 
 * 
 * 
 * 
 * 
 */
public class BugMemos {

}
