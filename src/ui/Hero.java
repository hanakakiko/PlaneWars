package ui;

import java.awt.image.BufferedImage;

//乙方战斗机类
public class Hero extends FlyObject {
    int hp;
    int firePower=1;//设置火力（子弹排数）
    public Hero(){
      img = ImageUtil.getImage("/image/hero.png");
      x=200;
      y=500;
      w=img.getWidth();
      h=img.getHeight();
      hp=3;
    };

    //鼠标移动时的移动（鼠标始终位于图片中心）
    public void moveToMouse(int mx,int my){
        x=mx-w/2;
        y=my-h/2;
    };

    //键盘操作时的移动
    public void moveUp() {
        y-=10;
    }
    public void moveDown() {
        y+=10;
    }
    public void moveLeft() {
        x-=10;
    }
    public void moveRight() {
        x+=10;
    }
}
