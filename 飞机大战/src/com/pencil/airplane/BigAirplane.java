package com.pencil.airplane;

import java.awt.image.BufferedImage;

public class BigAirplane extends FlyingObject implements Score{
 
	private static BufferedImage[] images;
	static {
		images = new BufferedImage[5];
		images[0] = readImage("shoot/bigairplane0.png");
		for (int i = 1; i < images.length; i++) {
			images[i] = readImage("shoot/bom" + i + ".png");
		}
	}
 
	// 速度
	private int step;
 
	public BigAirplane() {
		super(66, 89);
		step = 5;
	}
 
	public void show() {
		super.show();
		System.out.println("速度：" + step);
	}
 
	public void step() {
		y += step;
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
 
	public int getScore() {
		//击中大敌机获得3分
		return 3;
	}
}
