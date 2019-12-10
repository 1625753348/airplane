package com.pencil.airplane;

import java.awt.image.BufferedImage;

public class Bullet extends FlyingObject {
	public static void setImage(BufferedImage image) {
		Bullet.image = image;
	}

	private static BufferedImage image;
	static {
//		image = readImage("shoot/bullet.png");
		image = readImage("shoot/QQQ.png");
	}
 
	private int step;
 
	// �ӵ���ʼλ�ø���Ӣ�ۻ���������д����ʱ��֪��Ӣ�ۻ���λ�ã�
	// ����ֻ��ʹ�ò�������
	public Bullet(int x, int y) {
		super(8, 20, x, y);
		step = 20;
	}
 
	public void show() {
		super.show();
		System.out.println("�ٶȣ�" + step);
	}
 
	public void step() {
		// �ӵ������ƶ�
		y -= step;
	}
 
	public BufferedImage getImage() {
		BufferedImage img = null;
		if (isLife()) {
			img = image;
		} else if (isDead()) {
			// �ӵ����ᱬը������ֱ�ӱ�Ϊ��ʧ״̬
			state = REMOVE;
		}
		return img;
	}
 
	// �ӵ����ϳ�����жϣ���д����ķ���
	public boolean isOutOfBounds() {
		return y < -this.height;
	}
}
