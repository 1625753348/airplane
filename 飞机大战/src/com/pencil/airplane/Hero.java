package com.pencil.airplane;

import java.awt.image.BufferedImage;

public class Hero extends FlyingObject {

	private static BufferedImage[] images;
	static {
		images = new BufferedImage[2];
		images[0] = readImage("shoot/hero0.png");
		images[1] = readImage("shoot/hero1.png");
	}

	// 英雄机的生命值
	private int life;

	// 火力值
	private int doubleFire;

	public Hero() {
		super(97, 139, 152, 410);
		life = 3;
		doubleFire = 0;
	}

	public void show() {
		super.show();
		System.out.println("生命值：" + life + "\n火力值：" + doubleFire);
	}

	// 空实现
	public void step() {

	}

	int index = 0;

	public BufferedImage getImage() {
		BufferedImage img = null;
		// 任何数值取余2之后只可能得0或1
		img = images[index % 2];
		// index++后下次获得另一张图
		index++;
		return img;
	}

	// 英雄级开炮方法
	public Bullet[] shoot() {
		Bullet[] bs = null;
		// 为了方便我们使用英雄机宽度的1/4定义一个变量
		int len = this.width / 4;
		// 英雄机开炮分单排和双排
		if (doubleFire > 0) {
			// 双排
			bs = new Bullet[3];
			bs[0] = new Bullet(this.x + len, this.y - 20);
		
			
		bs[1] = new Bullet(this.x + 3 * len, this.y - 20);
		bs[2] = new Bullet(this.x + len / 3, this.y - 20);
			doubleFire--;
		} else {
			// 单排
			bs = new Bullet[1];
			bs[0] = new Bullet(this.x + len, this.y - 20);
		}
		return bs;
	}

	// 英雄机移动方法
	public void moveTo(int x, int y) {
		// 英雄机中心x轴到鼠标x轴
		this.x = x - this.width / 2;
		// 英雄机中心y轴到鼠标y轴
		this.y = y - this.height / 2;
	}

	// 获得英雄机的生命值
	public int getLife() {
		return this.life;
	}

	// 增加英雄机的生命值
	public void addLife() {
		this.life++;
	}

	// 增加英雄机的火力值
	public void addDoubleFire() {
		this.doubleFire += 20;
	}

	// 英雄机减命
	public void subLife() {
		this.life--;
	}

	// 清空火力值
	public void clearDoubleFire() {
		this.doubleFire = 0;
	}
}