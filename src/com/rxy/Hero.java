package com.rxy;

import com.rxy.utils.ImageLoaderUtil;

import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 英雄机，玩家类
 */
public class Hero extends FlySprite implements ActionSprite {

    //生命值
    private int life;
    //双发子弹
    private int doubleFire;
    //得分
    private int score;

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void addDoubleFile(){
        doubleFire+=40;
    }

    public void setDoubleFire(int doubleFire) {
        this.doubleFire = doubleFire;
    }

    //获得生命值
    public int getLife() {
        return life;
    }
    //加一条命
    public void addLife(){
        life++;
    }
    //减一条命
    public void subtractLife(){
        life--;
    }

    public Hero() {
        this.life = 3;
        this.init();
    }

    @Override
    public void init() {
        super.image = ImageLoaderUtil.load("hero1.png");
        super.width = image.getWidth() / 2;
        super.height = image.getHeight() / 2;
        //窗体的宽度-图片的宽度
        super.x = (FlyFrame.WIDTH - super.width) / 2;
        super.y = FlyFrame.HEIGHT - super.height - 50;

        Timer timer = new Timer();
        timer.schedule(new HeroAnimation(), 0, 200);
    }

    int flag = 1;
    int num = 0;
    class HeroAnimation extends TimerTask {
        @Override
        public void run() {
            if(isDead()){
                //死亡动画
                num++;
                Hero.super.image = ImageLoaderUtil.load("hero_blowup_n"+num+".png");
                //setImage(ImageLoaderUtil.load("hero_blowup_n"+num+".png"));
                if(num==4){
                    //复活
                    setDead(false);
                    num=0;
                }
            }else {
                //正常飞行
                if (flag == 1) {
                    flag = 2;
                } else {
                    flag = 1;
                }
                Hero.this.image = ImageLoaderUtil.load("hero" + flag + ".png");
            }

        }
    }

    public static final int SPEED = 5;

    //alt+ins
    @Override
    public void move(int code) {
        switch (code) {
            case KeyEvent.VK_UP:
                //上下移动 垂直方向Y
                int y = super.y - SPEED;
                if (y < 0) {
                    y = 0;
                }
                this.y = y;
                break;
            case KeyEvent.VK_DOWN:
                //上下移动 垂直方向Y
                y = super.y + SPEED;
                if (y > (FlyFrame.HEIGHT - super.height - 30)) {
                    y = FlyFrame.HEIGHT - super.height - 30;
                }
                this.y = y;
                break;
            case KeyEvent.VK_LEFT:
                //左右移动 水平方向X
                int x = this.x - SPEED;
                if (x < 0) {
                    x = 0;
                }
                this.x = x;
                break;
            case KeyEvent.VK_RIGHT:
                //左右移动 水平方向X
                x = this.x + SPEED;
                if (x > FlyFrame.WIDTH - this.width - 16) {
                    x = FlyFrame.WIDTH - this.width - 16;
                }
                this.x = x;
                break;
        }
    }

    //发送子弹的方法
    /**
     * 创建子弹对象，并加到集合当中
     */
    public void shoot() {
        int xStep = this.width / 4;//后续 双发
        if(doubleFire>0){
            //双发
            Bullet bullet0 = new Bullet(super.x + xStep * 1, super.y - 20);
            Bullet bullet1 = new Bullet(super.x + xStep * 3, super.y - 20);
            FlyPanel.bulletList.add(bullet0);
            FlyPanel.bulletList.add(bullet1);
            //BUFF 持续时间
            //发射双倍火力，每次减2，实际就是2倍火力的持续时间
            doubleFire-=2;
        }else {
            //Bullet bullet = new Bullet(hero.x+hero.width/2,hero.y-20);
            Bullet bullet = new Bullet(super.x + xStep * 2, super.y - 20);
            FlyPanel.bulletList.add(bullet);
        }

    }

    @Override
    public void destroy() {
        //表示游戏结束
        FlyPanel.gameState = GameState.GAME_OVER;
    }
}
