package com.rxy;

import com.rxy.utils.ImageLoaderUtil;

import java.util.Random;

/**
 * 奖励类
 */
public class BuffAward extends FlySprite implements ActionSprite{
    //x 步数
    private int xSpeed = 2;
    //y 步数
    private int ySpeed = 2;
    //奖励类型 0 双发 1 加命
    private int awardType;

    public int getAwardType() {
        return awardType;
    }

    public void setAwardType(int awardType) {
        this.awardType = awardType;
    }

    public BuffAward(){
        this.init();
    }
    @Override
    public void init() {
        Random random = new Random();
        //产生的随机数0,1就是奖励类型
        this.awardType = random.nextInt(2);
        super.image = ImageLoaderUtil.load("prop_type_"+awardType+".png");
        super.width = super.image.getWidth();
        super.height = super.image.getHeight();
        //初始 x 位置 y 位置
        y=-super.height;
        x=random.nextInt(FlyFrame.WIDTH-super.width);
    }

    @Override
    public void destroy() {
        FlyPanel.enemys.remove(this);
    }

    @Override
    public void down() {
        //向下运行 走S行 xy 都变
        if(super.x>=FlyFrame.WIDTH-super.width){
            xSpeed = -2;
        }
        if(super.x<=0){
            xSpeed = 2;
        }
        super.x+=xSpeed;
        super.y+=ySpeed;
        if(super.y>FlyFrame.HEIGHT){
            this.destroy();
        }
    }
}
