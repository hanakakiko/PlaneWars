package ui;

import java.util.Random;

//敌方战斗机类
public class Enemy extends FlyObject{
    int speed;//速度
    public Enemy(){
        Random random=new Random();
        //出现的敌机图片随机
        int index=random.nextInt(15)+1;//[1,16)
        img=ImageUtil.getImage("/image/ep"+(index<10?"0":"")+index+".png");
        speed=17-index;//速度和图片索引有关
        w=img.getWidth();
        h=img.getHeight();
        //出现时的横坐标随机
        x=random.nextInt(512-w);
        y=-h;//这样让飞机感觉是从外面飞进来
    };

    //移动
    void move(){
        y+=speed;//不同战斗机移动速度不同
    }

    //判断战斗机是否被子弹击中（战斗机图片是否与子弹图片部分重合）
    public boolean isShot(Fire f) {
        return x<=f.x+f.w &&
                x+w>=f.x &&
                y<=f.y+f.h &&
                y+h>=f.y;
    }
}
