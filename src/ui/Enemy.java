package ui;

import java.util.Random;

//敌方战斗机类
public class Enemy extends FlyObject{
    int type;//敌机种类（有一些特性）
    int sc;//击败敌机的加分
    int hp;//敌机的血量
    int speed;//速度
    public Enemy(){
        Random random=new Random();
        //出现的敌机图片随机
        int index=random.nextInt(15)+1;//[1,16)
        img=ImageUtil.getImage("/image/ep"+(index<10?"0":"")+index+".png");
        type = index;//种类就等于index
        sc=index;//击败敌机的加分和难度有关，敌机越大血量越高加分越多
        hp=index*3;//敌机的血量和机型大小有关
        if(type==15){
            speed=20;//导弹速度非常快
        }else{
            speed=17-index;//速度和图片索引有关
        }
        w=img.getWidth();
        h=img.getHeight();
        //出现时的横坐标随机
        x=random.nextInt(512-w);
        y=-h;//这样让飞机感觉是从外面飞进来
    };

    //移动
    void move(){
        if(type==5){
            y+=5;
            x-=2;//斜左飞
        }
        else if(type==6){
            y+=5;
            x+=2;//斜右飞
        }
        else{
            y+=speed;//不同战斗机移动速度不同
        }
    }

    //判断战斗机是否被子弹击中（战斗机图片是否与子弹图片部分重合）
    public boolean isShot(Fire f) {
        return x<=f.x+f.w &&
                x+w>=f.x &&
                y<=f.y+f.h &&
                y+h>=f.y;
    }

    public boolean isHit(Hero hr) {
        return x<=hr.x+hr.w &&
                x+w>=hr.x &&
                y<=hr.y+hr.h &&
                y+h>=hr.y;
    }
}
