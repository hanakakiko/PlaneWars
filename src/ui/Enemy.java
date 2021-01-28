package ui;

import java.util.Random;

public class Enemy extends FlyObject{
    int speed;
    Enemy(){
        Random random=new Random();
        //出现的敌机图片随机
        int index=random.nextInt(15)+1;//[1,16)
        img=ImageUtil.getImage("/image/ep"+(index<10?"0":"")+index+".png");
        speed=17-index;
        assert img != null;
        w=img.getWidth();
        h=img.getHeight();
        //出现时的横坐标随机
        x=random.nextInt(512-w);
        y=-h;//这样让飞机感觉是从外面飞进来
    };
    void move(){

        y+=speed;
    }

    public boolean isShot(Fire f) {
        return x<=f.x+f.w &&
                x+w>=f.x &&
                y<=f.y+f.h &&
                y+h>=f.y;
    }
}
