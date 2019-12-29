package com.pencil.airplane;

import java.awt.image.BufferedImage;


public class Bullet extends FlyingObject {
	public static void setImage(BufferedImage image) {
		Bullet.image = image;
	}

	private static BufferedImage image;
	static {
		
		image = readImage("shoot/QQQ.png");
	}
 
	private int step;
	//music m1 = new music("music\\《指尖骑士》音乐素材-sound-ATK 导弹子弹(ATK__爱给网_aigei_com.wav");
 
	// 子弹初始位置根据英雄机决定，编写代码时不知道英雄机的位置，
	// 所以只能使用参数代替
	public Bullet(int x, int y) {
		super(8, 20, x, y);
		step = 50;
		//m1.唱();
	}
 
	public void show() {
		super.show();
		

		System.out.println("速度：" + step);
	}
 
	public void step() {
		// 子弹向上移动
		y -= step;
	}
 
	public BufferedImage getImage() {
		BufferedImage img = null;
		if (isLife()) {
			img = image;
		} else if (isDead()) {
			// 子弹不会爆炸，死了直接变为消失状态
			state = REMOVE;
		}
		return img;
	}
 
	// 子弹向上出界的判断，重写父类的方法
	public boolean isOutOfBounds() {
		return y < -this.height;
	}
}
