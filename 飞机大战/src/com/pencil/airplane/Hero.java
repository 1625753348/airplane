package com.pencil.airplane;

import java.awt.image.BufferedImage;

public class Hero extends FlyingObject {

	private static BufferedImage[] images;
	static {
		images = new BufferedImage[2];
		images[0] = readImage("shoot/hero0.png");
		images[1] = readImage("shoot/hero1.png");
	}

	// Ӣ�ۻ�������ֵ
	private int life;

	// ����ֵ
	private int doubleFire;

	public Hero() {
		super(97, 139, 152, 410);
		life = 3;
		doubleFire = 0;
	}

	public void show() {
		super.show();
		System.out.println("����ֵ��" + life + "\n����ֵ��" + doubleFire);
	}

	// ��ʵ��
	public void step() {

	}

	int index = 0;

	public BufferedImage getImage() {
		BufferedImage img = null;
		// �κ���ֵȡ��2֮��ֻ���ܵ�0��1
		img = images[index % 2];
		// index++���´λ����һ��ͼ
		index++;
		return img;
	}

	// Ӣ�ۼ����ڷ���
	public Bullet[] shoot() {
		Bullet[] bs = null;
		// Ϊ�˷�������ʹ��Ӣ�ۻ���ȵ�1/4����һ������
		int len = this.width / 4;
		// Ӣ�ۻ����ڷֵ��ź�˫��
		if (doubleFire > 0) {
			// ˫��
			bs = new Bullet[3];
			bs[0] = new Bullet(this.x + len, this.y - 20);
		
			
		bs[1] = new Bullet(this.x + 3 * len, this.y - 20);
		bs[2] = new Bullet(this.x + len / 3, this.y - 20);
			doubleFire--;
		} else {
			// ����
			bs = new Bullet[1];
			bs[0] = new Bullet(this.x + len, this.y - 20);
		}
		return bs;
	}

	// Ӣ�ۻ��ƶ�����
	public void moveTo(int x, int y) {
		// Ӣ�ۻ�����x�ᵽ���x��
		this.x = x - this.width / 2;
		// Ӣ�ۻ�����y�ᵽ���y��
		this.y = y - this.height / 2;
	}

	// ���Ӣ�ۻ�������ֵ
	public int getLife() {
		return this.life;
	}

	// ����Ӣ�ۻ�������ֵ
	public void addLife() {
		this.life++;
	}

	// ����Ӣ�ۻ��Ļ���ֵ
	public void addDoubleFire() {
		this.doubleFire += 20;
	}

	// Ӣ�ۻ�����
	public void subLife() {
		this.life--;
	}

	// ��ջ���ֵ
	public void clearDoubleFire() {
		this.doubleFire = 0;
	}
}