package com.rxy;

import com.rxy.utils.ImageLoaderUtil;

import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 敌机
 */
public class EnemyAirplane extends FlySprite implements ActionSprite {

    //敌机类型
    private int type;
    //飞机下落的速度
    private int speed;
    //飞机的血量
    private int blood;
    //分值
    private int score;

    public int getBlood() {
        return blood;
    }

    public void setBlood(int blood) {
        this.blood = blood;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public EnemyAirplane() {
        this.init();
    }

    @Override
    public void init() {
        //创建随机对象
        Random random = new Random();
        //1-10；
        int i = random.nextInt(10) + 1;
        //1:3:6:生成不同的飞机
        if (i % 10 == 0) {
            type = 2;
        } else {
            //369
            if (i % 3 == 0) {
                type = 1;
            } else {
                type = 0;
            }
        }
        //根据飞机类型不通，速度也不同
        speed = 5 - type;
        if (type == 2) {
            score = blood = 10;
        } else {
            if (type == 1) {
                score = blood = 5;
            } else {
                score = blood = 1;
            }
        }
        //根据飞机的类型，加载相应的图片
        super.image = ImageLoaderUtil.load("enemy"+type+".png");
        super.width = super.image.getWidth()/2;
        super.height = super.image.getHeight()/2;
        //x,y
        //敌机从屏幕外进入，因此y轴设定为敌机的负高度
        super.y = -super.height;
        // 随机敌机的X轴
        super.x =random.nextInt(FlyFrame.WIDTH-super.width);
        //启动死亡动画
        Timer timer = new Timer();
        timer.schedule(new EnemyAirplaneAnimation(),0, 200);
    }

    @Override
    public void destroy() {
        FlyPanel.enemys.remove(this);
    }

    @Override
    public void down() {
        super.y = super.y+speed;
        if(super.y>FlyFrame.HEIGHT){
            this.destroy();
        }
    }

    class EnemyAirplaneAnimation extends TimerTask{
        int num;
        @Override
        public void run() {
            //如果死亡，就播放动画
            if(isDead()){
                num++;
                BufferedImage image =  ImageLoaderUtil.load("enemy"+type+"_down"+num+".png");
                //EnemyAirplane.super.image = image;
                if((type!=4&&num==4)||(type==2&&num==6)){
                    num = 0;
                    destroy();
                }
                setImage(image);
            }

        }
    }

}
