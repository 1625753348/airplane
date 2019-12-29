package com.pencil.airplane;

import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JPanel;




//�̳�JPanel��ʾWorld����һ������
public class World extends JPanel {
	
	// �����������������ڿ�͸�
	public static final int WIDTH = 400;
	public static final int HEIGHT = 700;
	// ������Ϸ�ķ���
	public static int score;
	// ������Ϸ״̬����
	public static final int START = 0;// ��ʼ
	public static final int RUNNING = 1;// ����
	public static final int PAUSE = 2;// ��ͣ
	public static final int GAME_OVER = 3;// ����
	// ���õ�ǰ��Ϸ״̬
	private int state = START;// Ĭ�Ͽ�ʼ״̬
	// ��������״̬��Ӧ������ͼƬ
	private static BufferedImage startImg;
	private static BufferedImage pauseImg;
	private static BufferedImage gameOverImg;

	static {
	
		startImg = FlyingObject.readImage("shoot/start.png");
		pauseImg = FlyingObject.readImage("shoot/pause.png");
		gameOverImg = FlyingObject.readImage("shoot/gameover.png");
	}
	// �����ɻ���ս�г��ֵĶ���
	// ���齨����Ϊ��Ա����
	// ���з������ܷ���
	Hero hero = new Hero();
	Sky sky = new Sky();
	FlyingObject[] enemy = {};
	Bullet[] bullets = {};

	public void start() {
		// ��д���ļ�����
		MouseAdapter l = new MouseAdapter() {
			public void mouseMoved(MouseEvent e) {
				if (state == RUNNING)
					hero.moveTo(e.getX(), e.getY());
			}

			// �����ʱ��״̬�л�
			public void mouseClicked(MouseEvent e) {
				switch (state) {
				case START:// ����ǿ�ʼ״̬
					state = RUNNING;// �����������״̬
					break;
				case GAME_OVER:// ����ǽ���״̬
					state = START;// ������뿪ʼ״̬
			
					// ������Ϸ
					score = 0;
					hero = new Hero();
					sky = new Sky();
					bullets = new Bullet[0];
					enemy = new FlyingObject[0];
					break;
				}
			}

			// �������ʱ״̬�л�
			public void mouseEntered(MouseEvent e) {
				if (state == PAUSE)// �������ͣ
					state = RUNNING;// ��������л�Ϊ����
			}

			// ����Ƴ�
			public void mouseExited(MouseEvent e) {
				if (state == RUNNING)// ���������
					state = PAUSE;// ����Ƴ��л�Ϊ��ͣ
			}
		};
		// �������ƶ��ͻ����¼�
		this.addMouseListener(l);
		this.addMouseMotionListener(l);

		// �����ʱ��ʱ����
		int interval = 20;
		// ������ʱ��
		Timer timer = new Timer();
		// ������ʱ������
		TimerTask task = new TimerTask() {
			// �����ڲ���
			public void run() {
				if (state == RUNNING) {
					// �������е�����
					// ���÷������ƶ�����
					moveAction();
					// ���õл���������
					enterAction();
					shootAction();
					outOfBoundsAction();
					bulletHitAction();
					heroHitAction();
					gameOverAction();
					// System.out.println(enemy.length);
					// System.out.println(bullets.length);
					// �ػ����ж���
				}
				repaint();
			}
		};
		// ������ʱ����������
		timer.schedule(task, interval, interval);
	}

	// �ж�Ӣ�ۻ��͵л���ײ�ķ���
	public void heroHitAction() {
		// �������ел�
		for (int i = 0; i < enemy.length; i++) {
			FlyingObject f = enemy[i];
			// �ж��Ƿ�͵�ǰ�л���ײ
			if (f.isLife() && hero.hit(f)) {
				// ײ���л�
				f.goDead();

				// Ӣ�ۻ������������
				hero.subLife();
				hero.clearDoubleFire();
			}
		}
	}

	// �ж���Ϸ�����ķ���
	public void gameOverAction() {
		// �������ֵС�ڵ���0����Ϸ����
		if (hero.getLife() <= 0) {
			state = GAME_OVER;
		}
	}

	// �ж��ӵ��͵л���ײ�ķ���
	public void bulletHitAction() {
		// ���������ӵ�
		for (int i = 0; i < bullets.length; i++) {
			Bullet b = bullets[i];// ��ȡ�ӵ�
			// �������ел�
			for (int j = 0; j < enemy.length; j++) {
				FlyingObject f = enemy[j];// ��ȡ�л�
				// �ж��ӵ��Ƿ��ел�
				if (b.isLife() && f.isLife() && b.hit(f)) {
					b.goDead();// �ӵ���
					f.goDead();// �л���
					
					
					
					
					// �жϵл��ǲ��ǵ÷ֵ�
					
					if (f instanceof Score) {
						Score s = (Score) f;
						score += s.getScore();
					}
					// �жϵл��ǲ����н���
					if (f instanceof Award) {
						Award a = (Award) f;
						// �����ܽ������Ľ�����ֵ
						int num = a.awardType();
						switch (num) {
//						case Award.LIFE:// �����0
//							hero.addLife();// ����
//							break;
						case Award.DOUBLE_FIRE:// �����1
							hero.addDoubleFire();// �ӻ���ֵ
							break;
						}
					}
				}

			}
		}
	}

	// ������ķ���
	public void outOfBoundsAction() {
		// û�г����Ԫ���±꣬ͬʱ����û����Ԫ�ص�����
		int index = 0;
		// ���屣��δ����Ԫ�ص�����
		FlyingObject[] fs = new FlyingObject[enemy.length];
		// �������ел�������Ƿ����
		for (int i = 0; i < enemy.length; i++) {
			// ��ȡ��ǰԪ��
			FlyingObject f = enemy[i];
			// �ж��Ƿ�δ���粢��û�Ƴ�
			if (!f.isOutOfBounds() && !f.isRemove()) {
				fs[index] = f;// ����������
				index++;// �±��1
			}
		}
		// ��������
		enemy = Arrays.copyOf(fs, index);
		index = 0;
		Bullet[] bs = new Bullet[bullets.length];
		for (int i = 0; i < bullets.length; i++) {
			Bullet b = bullets[i];
			if (!b.isOutOfBounds() && !b.isRemove()) {
				bs[index] = b;
				index++;
			}
		}
		bullets = Arrays.copyOf(bs, index);
	}

	// �ӵ���������
	int shootIndex = 1;

	public void shootAction() {
		if (shootIndex % 5 == 0) {
			// ����Ӣ�ۻ��Ŀ��ڷ���
			Bullet[] bs = hero.shoot();
			// ��bullets�����������
			bullets = Arrays.copyOf(bullets, bullets.length + bs.length);
			// ��Ӣ�ۻ���������ڵ��ŵ����ݺ���������λ��
			System.arraycopy(bs, 0, bullets, bullets.length - bs.length, bs.length);

		}
		shootIndex++;
	}

	// �л���������
	int enterIndex = 1;

	public void enterAction() {
		if (enterIndex % 20 == 0) {
			// ����һ�ܵл�
			FlyingObject fo = nextEnemy();
			// ���ݵ�ǰ�л�����
			enemy = Arrays.copyOf(enemy, enemy.length + 1);
			// �����ɵĵл��������ݺ���������
			enemy[enemy.length - 1] = fo;
		}
		enterIndex++;
	}

	// �������ƶ��ķ���
	public void moveAction() {
		sky.step();// ����ƶ�
		// �л��ƶ�
		for (int i = 0; i < enemy.length; i++) {
			enemy[i].step();
		}
		// �ӵ��ƶ�
		for (int i = 0; i < bullets.length; i++) {
			bullets[i].step();
		}
	}

	// ��������л��ķ���
	public FlyingObject nextEnemy() {
		Random ran = new Random();
		FlyingObject fo = null;
		int num = ran.nextInt(100);
		if (num < 40) {// 40%����С�л�
			fo = new Airplane();
		} else if (num < 80) {// 40%���ʴ�л�
			fo = new BigAirplane();
		} else {// 20%���ʽ�����
			fo = new Bee();
		}
		return fo;

	}

	// ��д�˸���JPanel�е�paint���������������ܴ�
	public void paint(Graphics g) {
		// �Ȼ��������ٻ�������
		sky.paintObject(g);
		hero.paintObject(g);
		for (int i = 0; i < enemy.length; i++) {
			enemy[i].paintObject(g);
		}
		for (int i = 0; i < bullets.length; i++) {
			bullets[i].paintObject(g);
		}

		// ��ʾ����������ֵ
		g.drawString("SCORE:" + score, 20, 20);
		g.drawString("LIFE:" + hero.getLife(), 20, 45);
		// ������Ϸ״̬����״̬ͼƬ
		switch (state) {
		case START:
			g.drawImage(startImg, 0, 0, null);
			break;
		case PAUSE:
			g.drawImage(pauseImg, 0, 0, null);
			break;
		case GAME_OVER:
		
			
			g.drawImage(gameOverImg, 0, 0, null);
				
		}
	
		
		

	}

	public static void main(String[] args) {
		World w = new World();
		// ʵ����һ������
		JFrame f = new JFrame("�ɻ���ս");
		// ��World�����õ�������
		f.add(w);
		// �������ô��ڵĿ�͸�
		f.setSize(400, 700);
		// ���ô��ڹر�ʱ�������
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// ���ô��ھ���
		f.setLocationRelativeTo(null);
		// ��ʾ���ڣ��Զ����������paint
		f.setVisible(true);

		w.start();

	}

}
