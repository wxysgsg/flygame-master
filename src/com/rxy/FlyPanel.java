package com.rxy;

import com.rxy.utils.ImageLoaderUtil;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * FlyPanel 面板，画板
 */
public class FlyPanel extends JPanel {

    //把硬盘上的一张图片读取到Java内存当中。
    //把硬盘上的一张图片映射成Java对象，java类
    //背景图片
    BufferedImage background = null;
    //开始图片
    BufferedImage start = null;
    //暂停图片
    BufferedImage pause = null;
    //游戏结束图片
    BufferedImage gameover = null;
    //画图的起始位置
    int x = 0;
    int y = -1100;
    //图片的宽高
    int bgWidth = 0;
    int bgHeight = 0;
    //定时器每隔多少秒执行一次
    Timer timer = new Timer();
    BgRoll bgRoll = null;
    ShootTask shootTask;
    EnemyTask enemyTask;
    HitTask hitTask;

    public FlyPanel() {
        background = ImageLoaderUtil.load("background.png");
        start = ImageLoaderUtil.load("start.png");
        pause = ImageLoaderUtil.load("pause.png");
        gameover = ImageLoaderUtil.load("gameover.png");
        bgWidth = background.getWidth();
        bgHeight = background.getHeight();
        //让当前面板获得焦点
        super.setFocusable(true);

        //添加事件，改变游戏状态
        super.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                switch (gameState) {
                    //当游戏状态是START 点击 变成RUNNING
                    case START:
                    case PAUSE:
                        gameState = GameState.RUNNING;
                        startGameTask();
                        break;
                    case RUNNING:
                        gameState = GameState.PAUSE;
                        pauseGameTask();
                        repaint();
                        break;
                    case GAME_OVER:
                        gameState = GameState.START;
                        repaint();
                        break;
                }
            }
        });
        super.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int code = e.getKeyCode();
                hero.move(code);
            }
        });
    }
    SoundPool bgSoundPool;
    SoundPool shootSoundPool;
    private void startGameTask() {
        bgRoll = new BgRoll();
        shootTask = new ShootTask();
        enemyTask = new EnemyTask();
        hitTask = new HitTask();
        timer.schedule(bgRoll, 0, 50);
        timer.schedule(shootTask, 0, 200);
        timer.schedule(enemyTask, 0, 1000);
        timer.schedule(hitTask, 0, 50);
        bgSoundPool = new SoundPool(SoundPool.BG_MUSIC);
        bgSoundPool.start();
        shootSoundPool = new SoundPool(SoundPool.SHOOT);
        shootSoundPool.start();
    }

    private void pauseGameTask() {
        bgRoll.cancel();
        shootTask.cancel();
        enemyTask.cancel();
        hitTask.cancel();
        bgSoundPool.stopAc();
        shootSoundPool.stopAc();
    }


    //背景滚动内部类
    class BgRoll extends TimerTask {
        @Override
        public void run() {
            //修改y轴的值（制造背景滚动效果）
            y = y + 5;
            if (y > 0) {
                y = -1100;
            }
            //重绘
            repaint();
        }
    }

    //子弹的定时任务
    class ShootTask extends TimerTask {
        @Override
        public void run() {
            hero.shoot();
            //重绘
            repaint();
        }
    }

    //敌机的定时任务
    class EnemyTask extends TimerTask {

        @Override
        public void run() {
            Random random = new Random();
            int type = random.nextInt(10);
            if (type == 0) {
                //奖励
                BuffAward buffAward = new BuffAward();
                enemys.add(buffAward);
            } else {
                //敌机
                EnemyAirplane enemyAirplane = new EnemyAirplane();
                enemys.add(enemyAirplane);
            }

            //重绘
            repaint();
        }
    }

    //实时检测是否碰撞任务类
    class HitTask extends TimerTask {

        @Override
        public void run() {
            //敌机与英雄机是否碰撞
            for (FlySprite enemy : enemys) {
                //判断是否碰撞
                //if(PhysicsManager.isHit(hero,enemy)){
                //敌机死亡
                if (enemy.isDead()) {
                    continue;
                }
                //是BUFF奖励
                if (enemy instanceof BuffAward) {
                    continue;
                }
                if (hero.isHit(enemy)) {
                    enemy.setDead(true);
                    if (hero.getLife() == 0) {
                        //游戏结束
                        hero.destroy();
                    } else {
                        hero.subtractLife();
                        hero.setDead(true);
                        break;
                    }
                }
            }
            //子弹与敌机是否碰撞
            for (Bullet bullet : bulletList) {
                //enemys 敌机 buff
                for (FlySprite enemy : enemys) {
                    //if(PhysicsManager.isHit(bullet,enemy)){
                    if (bullet.isHit(enemy)) {
                        bullet.destroy();
                        //是否是敌机
                        if (enemy instanceof EnemyAirplane) {
                            EnemyAirplane enemyAirplane = (EnemyAirplane)enemy;
                            enemyAirplane.setBlood(enemyAirplane.getBlood()-1);
                            if(enemyAirplane.getBlood()==0){
                                enemy.setDead(true);
                                new SoundPool(SoundPool.BOOM).start();
                                //加分
                                hero.setScore(hero.getScore()+enemyAirplane.getScore());
                            }

                        }
                        //是否是Buff
                        if (enemy instanceof BuffAward) {
                            BuffAward buffAward = (BuffAward)enemy;
                            if(buffAward.getAwardType()==0){
                                //双发加火力
                                hero.addDoubleFile();
                            }else {
                                //加生命值
                                hero.addLife();
                            }
                            enemy.destroy();
                        }
                    }
                }
            }
        }
    }

    //当前游戏状态
   public static GameState gameState = GameState.START;

    /**
     * 根据当前的游戏状态进行绘制图片
     * @param g
     */
    private void paintState(Graphics g) {
        switch (gameState) {
            case START:
                g.drawImage(start, 0, 0, null);
                break;
            case PAUSE:
                g.drawImage(pause, 0, 0, null);
                break;
            case GAME_OVER:
                //清理现场 游戏内容的重置
                hero = new Hero();
                enemys.clear();
                bulletList.clear();
                pauseGameTask();
                g.drawImage(gameover, 0, 0, null);
                //gameState = GameState.START;
                break;
        }
    }

    //创建英雄机
    public static Hero hero = new Hero();
    //子弹复数  == 存储  == 数组 集合
    //Bullet bullet = new Bullet(hero.x+hero.width/2,hero.y-20);
    //子弹集合 谁往集合中加子弹呢？当英雄飞机
    public static CopyOnWriteArrayList<Bullet> bulletList = new CopyOnWriteArrayList<>();
    //敌机的集合,Buff对象，  泛型 EnemyAirplane  多态
    public static CopyOnWriteArrayList<FlySprite> enemys = new CopyOnWriteArrayList<>();

    /**
     * 画出生命值和分数
     * @param g
     */
    private void paintLifeAndScore(Graphics g){
        g.setColor(new Color(0xFF00000));
        g.setFont(new Font(Font.SANS_SERIF,Font.BOLD,20));
        g.drawString("LIFE:"+hero.getLife(),20,45);
        g.drawString("SCORE:"+hero.getScore(),20,25);
    }

    //只要页面发送变动，就重绘的方法
    @Override
    public void paint(Graphics g) {
        g.drawImage(background, x, y, bgWidth, bgHeight, null);
        paintState(g);
        hero.draw(g);
        for (Bullet bullet : bulletList) {
            bullet.draw(g);
            bullet.up();
        }
        for (FlySprite flySprite : enemys) {
            flySprite.draw(g);
            ActionSprite actionSprite = (ActionSprite) flySprite;
            actionSprite.down();
        }
        paintLifeAndScore(g);
    }

}
