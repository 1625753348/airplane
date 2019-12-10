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
	// �ƶ��ַ���
	private int xStep;// �����ƶ�
	private int yStep;// �����ƶ�
 
	// ��������
	private int awardType;
 
	public Bee() {
		super(60, 51);
		xStep = 2;
		yStep = 2;
		Random ran = new Random();
		// �������0��1���뽱�����ͣ����ڻ��ел�ʱ��ò�ͬ����
		awardType = ran.nextInt(2);
	}
 
	public void show() {
		super.show();
		System.out.println("x�ٶ�:" + xStep + "\ny�ٶ�:" + yStep);
		System.out.println("�������ͣ�" + awardType);
	}
 
	public void step() {
		x += xStep;
		y += yStep;
		// �����������ײ����������ı߽�
		if (x <= 0 || x >= World.WIDTH - this.width) {
			// �޸������ƶ�����
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
		//���ص�ǰ�������Ľ�������ֵ
		return this.awardType;
	}
}
