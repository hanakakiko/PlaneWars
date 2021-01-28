package ui;

public class Fire extends FlyObject {
    int dir;//散弹：指示子弹的方向
    public Fire(int hx,int hy,int dir){//传入的是英雄机的位置
        img=ImageUtil.getImage("/image/fire.png");
        w=img.getWidth()/4;
        h=img.getHeight()/4;
        x=hx;
        y=hy;
        this.dir=dir;
    };

    public void move() {
        y-=10;
        if(dir==0){
            x-=2;
        }else if(dir==2){
            x+=2;
        }
    }
}
