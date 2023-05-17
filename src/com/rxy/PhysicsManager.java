package com.rxy;

/**
 * 第一种
 * 面向过程 重在方法
 * 模拟物理引擎的碰撞
 */
public class PhysicsManager {

    /**
     * 碰撞检测
     *
     * @param one
     * @param other
     * @return true 碰撞 ，false 没碰撞
     */
    public static boolean isHit(FlySprite one, FlySprite other) {
        //左侧
        if (one.getX() > other.getX() && one.getX() > other.getX() + other.getWidth()) {
            return false;
        }
        //右侧
        if (one.getX() < other.getX() && other.getX() > one.getX() + one.getWidth()) {
            return false;
        }
        //上方
        if(one.getY()>other.getY()&&one.getY()>other.getY()+other.getHeight()){
            return false;
        }
        //下方
        if(one.getY()<other.getY()&&other.getY()>one.getY()+one.getHeight()){
            return  false;
        }
        //默认返回 true
        return true;
    }
}
