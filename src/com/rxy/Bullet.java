package com.rxy;

import com.rxy.utils.ImageLoaderUtil;

public class Bullet extends FlySprite implements ActionSprite {

    public Bullet(int x,int y){
        this.init();
        super.x = x;
        super.y = y;
    }


    @Override
    public void init() {
        super.image = ImageLoaderUtil.load("bullet2.png");
        super.width = image.getWidth()/2;
        super.height = image.getHeight()/2;
    }

    @Override
    public void destroy() {
        //移除集合当中越界子弹对象
        FlyPanel.bulletList.remove(this);
    }

    int SPEED = 5;
    /**
     * 子弹向上运行
     */
    @Override
    public void up() {
        //y 垂直方向 移动， 减 向上
        super.y=y-SPEED;
        if(super.y<0){
            this.destroy();
        }
    }
}
