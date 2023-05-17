package com.rxy;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * 精灵类:玩家机，敌机，子弹，道具机等元素都属于精灵对象
 * FlySprite 所有元素的父类
 */
public abstract class FlySprite {
    //游戏引擎
    //cocos  移动端，手机端，微信小程序  javaScript ==》TypeScript
    //unity  pc 手机   C#
    //UN 虚幻 3A       C++

    //图片
    protected   BufferedImage image;
    protected  int x;
    protected  int y;
    protected  int width;
    protected  int height;
    //是否死亡
    protected  boolean dead;

    //alt+ins 生成get set 方法
    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    /**
     * 初始化精灵信息
     */
    public abstract void init();

    /**
     * 绘制精灵
     * @param g
     */
    public void draw(Graphics g){
        g.drawImage(image,x,y,width,height,null);
    }

    /**
     * 第二种面向对象思想
     * 碰撞检测
     * @param other
     * @return
     */
    public  boolean isHit(FlySprite other) {
        //当前类
        //当前类的方法当中，
        //左侧
        if(this.x > other.x && this.x > other.x   + other.width){
            return false;
        }
        //右侧
        if(this.x < other.x   && other.x  > this.x + this.width){
            return false;
        }
        //上方
        if(this.y > other.y && this.y > other.y + other.height){
            return false;
        }
        //下方
        if(this.y < other.y && other.y>this.y + this.height){
            return false;
        }
        //默认返回 true
        return true;
    }

    /**
     * 销毁精灵信息
     */
    public abstract void destroy();

}
