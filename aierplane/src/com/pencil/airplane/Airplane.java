package com.pencil.airplane;

import java.awt.image.BufferedImage;

public class Airplane extends FlyingObject implements Score{
	// ����С�л����ͼƬ������
	private static BufferedImage[] images;
	static {
		// ��ʼ��ͼƬ����
		images = new BufferedImage[5];
		// ��һ��ͼ��С�л�ͼ
		images[0] = readImage("shoot/airplane0.png");
		// ѭ������4�ű�ըͼ
		for (int i = 1; i < images.length; i++) {
			images[i] = readImage("shoot/bom" + i + ".png");
		}
	}
 
	// �ٶ�
	private int step;
 
	public Airplane() {
		super(48, 50);
		step = 5;
	}
 
	public void show() {
		super.show();
		System.out.println("�ٶȣ�" + step);
	}
 
	public void step() {
		// С�л������ƶ�
		y += step;
	}
 
	int index = 1;
 
	public BufferedImage getImage() {
		BufferedImage img = null;
		// ������ŷ��ص�һ��ͼ
		if (isLife()) {
			img = images[0];
		} else if (isDead()) {
			// ������˻�ñ�ըͼƬ
			img = images[index];
			index++;
			//�����ը��ϣ���С�ɻ�����Ϊ��ʧ
			if(index==images.length) {
				state=REMOVE;
			}
		}
		return img;
	}
 
	public int getScore() {
		//����С�л���1��
		return 1;
	}
}
