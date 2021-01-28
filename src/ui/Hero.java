package ui;

import java.awt.image.BufferedImage;

public class Hero extends FlyObject {

    Hero(){
      img = ImageUtil.getImage("/image/hero.png");
      x=200;
      y=500;
        assert img != null;
        w=img.getWidth();
      h=img.getHeight();
    };
    public void moveToMouse(int mx,int my){
        x=mx-w/2;
        y=my-h/2;
    };

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
