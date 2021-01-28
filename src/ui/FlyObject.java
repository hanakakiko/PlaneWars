package ui;

import java.awt.image.BufferedImage;

//所有飞行物体的公共父类（战斗机、敌机、子弹等）
public class FlyObject {//包含形象、位置坐标和长宽信息
    BufferedImage img;
    int x;
    int y;
    int w;
    int h;
}
