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




//继承JPanel表示World类是一个窗口
public class World extends JPanel {
	
	// 声明两个常量，窗口宽和高
	public static final int WIDTH = 400;
	public static final int HEIGHT = 700;
	// 定义游戏的分数
	public static int score;
	// 定义游戏状态常量
	public static final int START = 0;// 开始
	public static final int RUNNING = 1;// 运行
	public static final int PAUSE = 2;// 暂停
	public static final int GAME_OVER = 3;// 结束
	// 设置当前游戏状态
	private int state = START;// 默认开始状态
	// 声明三个状态对应得三个图片
	private static BufferedImage startImg;
	private static BufferedImage pauseImg;
	private static BufferedImage gameOverImg;

	static {
	
		startImg = FlyingObject.readImage("shoot/start.png");
		pauseImg = FlyingObject.readImage("shoot/pause.png");
		gameOverImg = FlyingObject.readImage("shoot/gameover.png");
	}
	// 声明飞机大战中出现的对象
	// 将组建声明为成员变量
	// 所有方法均能访问
	Hero hero = new Hero();
	Sky sky = new Sky();
	FlyingObject[] enemy = {};
	Bullet[] bullets = {};

	public void start() {
		// 编写鼠标的监听器
		MouseAdapter l = new MouseAdapter() {
			public void mouseMoved(MouseEvent e) {
				if (state == RUNNING)
					hero.moveTo(e.getX(), e.getY());
			}

			// 鼠标点击时的状态切换
			public void mouseClicked(MouseEvent e) {
				switch (state) {
				case START:// 如果是开始状态
					state = RUNNING;// 点击进入运行状态
					break;
				case GAME_OVER:// 如果是结束状态
					state = START;// 点击进入开始状态
			
					// 重置游戏
					score = 0;
					hero = new Hero();
					sky = new Sky();
					bullets = new Bullet[0];
					enemy = new FlyingObject[0];
					break;
				}
			}

			// 鼠标移入时状态切换
			public void mouseEntered(MouseEvent e) {
				if (state == PAUSE)// 如果是暂停
					state = RUNNING;// 鼠标移入切换为运行
			}

			// 鼠标移出
			public void mouseExited(MouseEvent e) {
				if (state == RUNNING)// 如果是运行
					state = PAUSE;// 鼠标移出切换为暂停
			}
		};
		// 绑定鼠标的移动和滑动事件
		this.addMouseListener(l);
		this.addMouseMotionListener(l);

		// 定义计时器时间间隔
		int interval = 20;
		// 声明计时器
		Timer timer = new Timer();
		// 声明计时器任务
		TimerTask task = new TimerTask() {
			// 匿名内部类
			public void run() {
				if (state == RUNNING) {
					// 周期运行的内容
					// 调用飞行物移动方法
					moveAction();
					// 调用敌机进场方法
					enterAction();
					shootAction();
					outOfBoundsAction();
					bulletHitAction();
					heroHitAction();
					gameOverAction();
					// System.out.println(enemy.length);
					// System.out.println(bullets.length);
					// 重绘所有对象
				}
				repaint();
			}
		};
		// 启动计时器周期运行
		timer.schedule(task, interval, interval);
	}

	// 判断英雄机和敌机碰撞的方法
	public void heroHitAction() {
		// 遍历所有敌机
		for (int i = 0; i < enemy.length; i++) {
			FlyingObject f = enemy[i];
			// 判断是否和当前敌机相撞
			if (f.isLife() && hero.hit(f)) {
				// 撞死敌机
				f.goDead();

				// 英雄机减命，清活力
				hero.subLife();
				hero.clearDoubleFire();
			}
		}
	}

	// 判断游戏结束的方法
	public void gameOverAction() {
		// 如果生命值小于等于0，游戏结束
		if (hero.getLife() <= 0) {
			state = GAME_OVER;
		}
	}

	// 判断子弹和敌机碰撞的方法
	public void bulletHitAction() {
		// 遍历所有子弹
		for (int i = 0; i < bullets.length; i++) {
			Bullet b = bullets[i];// 提取子弹
			// 遍历所有敌机
			for (int j = 0; j < enemy.length; j++) {
				FlyingObject f = enemy[j];// 提取敌机
				// 判断子弹是否集中敌机
				if (b.isLife() && f.isLife() && b.hit(f)) {
					b.goDead();// 子弹死
					f.goDead();// 敌机死
					
					
					
					
					// 判断敌机是不是得分的
					
					if (f instanceof Score) {
						Score s = (Score) f;
						score += s.getScore();
					}
					// 判断敌机是不是有奖励
					if (f instanceof Award) {
						Award a = (Award) f;
						// 获得这架奖励机的奖励数值
						int num = a.awardType();
						switch (num) {
//						case Award.LIFE:// 如果是0
//							hero.addLife();// 加命
//							break;
						case Award.DOUBLE_FIRE:// 如果是1
							hero.addDoubleFire();// 加火力值
							break;
						}
					}
				}

			}
		}
	}

	// 检测出界的方法
	public void outOfBoundsAction() {
		// 没有出界的元素下标，同时保存没出界元素的数量
		int index = 0;
		// 定义保存未出界元素的数组
		FlyingObject[] fs = new FlyingObject[enemy.length];
		// 遍历所有敌机，检测是否出界
		for (int i = 0; i < enemy.length; i++) {
			// 提取当前元素
			FlyingObject f = enemy[i];
			// 判断是否未出界并且没移除
			if (!f.isOutOfBounds() && !f.isRemove()) {
				fs[index] = f;// 放入新数组
				index++;// 下标加1
			}
		}
		// 数组缩容
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

	// 子弹进场方法
	int shootIndex = 1;

	public void shootAction() {
		if (shootIndex % 5 == 0) {
			// 调用英雄机的开炮方法
			Bullet[] bs = hero.shoot();
			// 对bullets数组进行扩容
			bullets = Arrays.copyOf(bullets, bullets.length + bs.length);
			// 将英雄机发射的新炮弹放到扩容后的数组最后位置
			System.arraycopy(bs, 0, bullets, bullets.length - bs.length, bs.length);

		}
		shootIndex++;
	}

	// 敌机进场方法
	int enterIndex = 1;

	public void enterAction() {
		if (enterIndex % 20 == 0) {
			// 生成一架敌机
			FlyingObject fo = nextEnemy();
			// 扩容当前敌机数组
			enemy = Arrays.copyOf(enemy, enemy.length + 1);
			// 将生成的敌机放入扩容后数组的最后
			enemy[enemy.length - 1] = fo;
		}
		enterIndex++;
	}

	// 飞行物移动的方法
	public void moveAction() {
		sky.step();// 天空移动
		// 敌机移动
		for (int i = 0; i < enemy.length; i++) {
			enemy[i].step();
		}
		// 子弹移动
		for (int i = 0; i < bullets.length; i++) {
			bullets[i].step();
		}
	}

	// 随机产生敌机的方法
	public FlyingObject nextEnemy() {
		Random ran = new Random();
		FlyingObject fo = null;
		int num = ran.nextInt(100);
		if (num < 40) {// 40%几率小敌机
			fo = new Airplane();
		} else if (num < 80) {// 40%几率大敌机
			fo = new BigAirplane();
		} else {// 20%几率奖励机
			fo = new Bee();
		}
		return fo;

	}

	// 重写了父类JPanel中的paint方法，方法名不能错
	public void paint(Graphics g) {
		// 先画背景，再画其他的
		sky.paintObject(g);
		hero.paintObject(g);
		for (int i = 0; i < enemy.length; i++) {
			enemy[i].paintObject(g);
		}
		for (int i = 0; i < bullets.length; i++) {
			bullets[i].paintObject(g);
		}

		// 显示分数和生命值
		g.drawString("SCORE:" + score, 20, 20);
		g.drawString("LIFE:" + hero.getLife(), 20, 45);
		// 根据游戏状态绘制状态图片
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
		// 实例化一个窗口
		JFrame f = new JFrame("飞机大战");
		// 将World类设置到窗口中
		f.add(w);
		// 首先设置窗口的宽和高
		f.setSize(400, 700);
		// 设置窗口关闭时程序结束
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// 设置窗口居中
		f.setLocationRelativeTo(null);
		// 显示窗口，自动调用上面的paint
		f.setVisible(true);

		w.start();

	}

}
