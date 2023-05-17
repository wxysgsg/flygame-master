package com.rxy;

import com.rxy.utils.ImageLoaderUtil;

import javax.swing.*;
import java.awt.image.BufferedImage;

/**
 * FlyFrame 画框
 * Java 继承
 * Swing编程
 */
public class FlyFrame extends JFrame {
    public static final   int WIDTH = 400;
    public static final   int HEIGHT = 600;
    public FlyFrame(){
        //swing 编程
        //通过父类的方法去设置窗体的样式属性
        //设置窗体的大小
        super.setSize(WIDTH,HEIGHT);
        //设置一直在最上面
        super.setAlwaysOnTop(true);
        //窗体居中显示
        super.setLocationRelativeTo(null);
        //禁止最大化
        super.setResizable(false);
        //设置默认退出的处理方式
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        FlyPanel flyPanel = new FlyPanel();
        super.add(flyPanel);
        //设置图标
        BufferedImage image = ImageLoaderUtil.load("icon72x72.png");
        super.setIconImage(image);
        //设置窗口的隐藏属性
        super.setVisible(true);
    }
}
