package ui;

//子弹类
public class Fire extends FlyObject {
    int dir;//用于散弹：指示子弹的方向（0：左前，1：前，2：右前）
    public Fire(int hx,int hy,int dir){//传入的是英雄机的位置和散弹方向
        img=ImageUtil.getImage("/image/fire.png");
        //缩小子弹到合适大小
        w=img.getWidth()/4;
        h=img.getHeight()/4;
        //从英雄机位置发出
        x=hx;
        y=hy;
        //确定子弹方向
        this.dir=dir;
    };

    //子弹移动（根据dir方向）
    public void move() {
        y-=10;
        if(dir==0){
            x-=2;
        }else if(dir==2){
            x+=2;
        }
    }
}
