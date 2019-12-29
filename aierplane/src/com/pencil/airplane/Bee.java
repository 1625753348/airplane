package com.pencil.airplane;

import java.awt.image.BufferedImage;
import java.util.Random;

public class Bee extends FlyingObject implements Award{
	 
	private static BufferedImage[] images;
	static {
		images = new BufferedImage[5];
		images[0] = readImage("shoot/bee0.png");
		for (int i = 1; i < images.length; i++) {
			images[i] = readImage("shoot/bom" + i + ".png");
		}
	}
	// 移动分方向
	private int xStep;// 左右移动
	private int yStep;// 上下移动
 
	// 奖励类型
	private int awardType;
 
	public Bee() {
		super(60, 51);
		xStep = 2;
		yStep = 2;
		Random ran = new Random();
		// 随机生成0和1存入奖励类型，用于击中敌机时获得不同奖励
		awardType = ran.nextInt(2);
	}
 
	public void show() {
		super.show();
		System.out.println("x速度:" + xStep + "\ny速度:" + yStep);
		System.out.println("奖励类型：" + awardType);
	}
 
	public void step() {
		x += xStep;
		y += yStep;
		// 如果奖励机碰撞了左右两侧的边界
		if (x <= 0 || x >= World.WIDTH - this.width) {
			// 修改它的移动方向
			xStep *= -1;
		}
	}
	int index = 1;
 
	public BufferedImage getImage() {
		BufferedImage img = null;
		if (isLife()) {
			img = images[0];
		} else if (isDead()) {
			img = images[index];
			index++;
			if (index == images.length) {
				state = REMOVE;
			}
		}
		return img;
	}
 
	public int awardType() {
		//返回当前奖励机的奖励类型值
		return this.awardType;
	}
}
