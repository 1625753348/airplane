package com.pencil.airplane;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

//飞机大战的父类飞行物
public abstract class FlyingObject {
	// 定义三种状态常量：活着、死了、消失
	public static final int LIFE = 0;
	public static final int DEAD = 1;
	public static final int REMOVE = 2;
	// 定义当前对象的状态属性
	protected int state = LIFE;// 初始默认活着

//所有子类共有的属性和方法、
	protected int width;
	protected int height;
	protected int x;
	protected int y;

	// 小敌机，大敌机，奖励机使用的构造
	public FlyingObject(int width, int height) {
		this.width = width;
		this.height = height;
		// 所有敌级的y轴都是负高度，因为出现在屏幕上方
		y = -height;
		Random ran = new Random();
		// x轴是从左侧开始到屏幕的宽度减去低级的宽度的随机数
		x = ran.nextInt(400 - this.width);
	}

	// 子弹，天空，英雄机的构造

	public FlyingObject(int width, int height, int x, int y) {
		this.width = width;
		this.height = height;
		this.x = x;
		this.y = y;
	}

	public void show() {
		System.out.println("宽：" + width + ",高" + height);
		System.out.println("x：" + x + ",y：" + y);
	}

	// 读取图片，将项目中保存的图片，转换成java内存的图片
	public static BufferedImage readImage(String fileName) {
		try {
			// 根据文件名将图片获取并赋值给img
			BufferedImage img = ImageIO.read(new File(fileName));
			return img;
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}

	}



	// 定义一个单位移动的抽象方法
	public abstract void step();

	// 判断状态的方法
	// 判断是否活着
	public boolean isLife() {
		return state == LIFE;
	}

	// 判断是否死了
	public boolean isDead() {
		return state == DEAD;
	}

	// 判断是否消失
	public boolean isRemove() {
		return state == REMOVE;
	}

	// 获得图片的抽象方法
	public abstract BufferedImage getImage();

	// 绘制图片到窗体
	public void paintObject(Graphics g) {
		g.drawImage(getImage(), x, y, null);
	}

	// 判断出界的方法
	public boolean isOutOfBounds() {

		return y > World.HEIGHT;
	}

	// 判断碰撞的方法
	// 判断的是this（当前对象）是否和参数中的飞行物碰撞了
	public boolean hit(FlyingObject f) {
		// this.想象为子弹，f想象为一架敌机
		int x1 = f.x - this.width;// 左侧点
		int x2 = f.x + f.width;// 右侧点

		int y1 = f.y - this.height;// 上方点
		int y2 = f.y + f.height;// 下方点

		return this.x > x1 && this.x < x2 && this.y > y1 && this.y < y2;

	}

	// 改变飞行物状态为DEAD的方法
	public void goDead() {
		state = DEAD;
	}

}
