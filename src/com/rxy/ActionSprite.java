package com.rxy;

/**
 * 动作精灵 接口
 *
 */
public interface ActionSprite {

    //jdk 1.8特性

    /**
     * 向下移动
     */
    default void down(){
        System.out.println("向下运动");
    }
    /**
     * 向上移动
     */
    default void up(){
        System.out.println("向下运动");
    }
    /**
     * 英雄机控制移动方法
     */
    default void move(int code){

    }
}
