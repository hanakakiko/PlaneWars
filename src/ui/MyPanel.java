package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.security.Key;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

public class MyPanel extends JPanel {
    boolean gameOver;//默认为false
    List<Enemy> eps = new ArrayList<Enemy>();//创建敌机大本营
    List<Fire> fs = new ArrayList<Fire>();//创建弹药库（最好写在Hero里）
    BufferedImage bgIm;//背景图片
    Hero hero = new Hero();//己方战斗机
    int score;//分数
    public MyPanel(MyFrame frame){
        bgIm = ImageUtil.getImage("/image/bg1.jpg");
        //设置背景颜色，其实会被背景图片覆盖，也可不设(如果窗口可拉伸那就会露出底色)
        setBackground(Color.GRAY);
        //创建鼠标适配器
        MouseAdapter mouseAdapter = new MouseAdapter() {
            public void mouseMoved(MouseEvent e){
                if(!gameOver){//要是结束了英雄机就不能随鼠标动了
                    int mx=e.getX();
                    int my=e.getY();
                    hero.moveToMouse(mx,my);
                    repaint();
                }
            };
        };
        //加入到鼠标监听器
        addMouseListener(mouseAdapter);
        addMouseMotionListener(mouseAdapter);

        //创建键盘适配器，对键盘的上、下、左、右键作出反应
        KeyAdapter keyAdapter = new KeyAdapter() {
            //按下键盘时会触发的方法
            public void keyPressed(KeyEvent e){
                if(!gameOver){//游戏结束键盘操纵失效
                    int keyCode = e.getKeyCode();
                    if(keyCode == KeyEvent.VK_UP){
                        hero.moveUp();
                    }
                    else if(keyCode == KeyEvent.VK_DOWN){
                        hero.moveDown();
                    }
                    else if(keyCode == KeyEvent.VK_LEFT){
                        hero.moveLeft();
                    }
                    else if(keyCode == KeyEvent.VK_RIGHT){
                        hero.moveRight();
                    };
                    //更改战斗机位置后要重画
                    repaint();
                }
            };
        };
        //加到框体的键盘监听器
        frame.addKeyListener(keyAdapter);
    };

    @Override
    //所有的画面显示都在这里
    public void paint(Graphics g) {
        super.paint(g);
        //画背景
        g.drawImage(bgIm,0,0,null);
        /*画出血条
        g.setColor(Color.WHITE);
        g.fillRect(50,10,100,10);
        g.setColor(Color.BLACK);
        g.drawRect(50,10,100,10);
         */
        //画出剩余生命条数图标
        for (int i = 0; i < hero.hp; i++) {
            g.drawImage(hero.img,400+30*i,20,30,30,null);
        }
        //画分数
        g.setFont(new Font("宋体",Font.PLAIN,18));
        g.setColor(Color.BLACK);
        g.drawString("分数："+score, 20,20);
        //画战斗机
        g.drawImage(hero.img,hero.x,hero.y,hero.w,hero.h, null);
        //遍历画敌机
        for (int i = 0; i < eps.size(); i++) {
            Enemy enemy = eps.get(i);
            g.drawImage(enemy.img, enemy.x, enemy.y, enemy.w, enemy.h, null);
        }
        /*
        for (Enemy enemy : eps) {
            g.drawImage(enemy.img, enemy.x, enemy.y, enemy.w, enemy.h, null);
        }*/

        //画子弹
        for (int i = 0; i < fs.size(); i++) {
            Fire f = fs.get(i);
            g.drawImage(f.img,f.x ,f.y,f.w,f.h,null);
        }
        /*for(Fire f:fs){
            g.drawImage(f.img,f.x ,f.y,f.w,f.h,null);
        }*/

        //游戏结束显示文字提示
        if(gameOver){
            g.setFont(new Font("黑体",Font.PLAIN,26));
            g.setColor(Color.RED);
            g.drawString("游戏结束！", 200,200);
        }
    }

    //开始游戏
    public void action(){
        new Thread(() -> {
            while(true){
                if(!gameOver){//游戏结束停止一切运动
                    epEnter();//敌机加入战场
                    epMove();//敌机移动
                    shoot();//发射子弹
                    shootMove();//子弹移动
                    shot();//遍历敌机库和子弹库，检测是否有碰撞
                    hit();//遍历敌机库，检测是否与英雄机碰撞
                }
                try {
                    Thread.sleep(50);//线程短暂休息
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //刷新画面
                repaint();
            }
        }).start();
    }

    private void hit() {
        Iterator<Enemy> itEp = eps.iterator();
        while(itEp.hasNext()){
            Enemy ep = itEp.next();
            if(ep.isHit(hero)){
                itEp.remove();//这里一定要用迭代器删除，不能用eps.remove(ep)，否则检测到修改数不一致会报错
                hero.hp--;//与敌机碰撞，减少一次生命值
                repaint();//画面上的生命值图标减少一个
                if(hero.hp<=0){//三条生命全部耗尽，游戏结束
                    gameOver = true;
                }
            }
        }
    }

    //检测敌机与子弹碰撞，有的话删除敌机和子弹，加分
    private void shot() {
        Iterator<Enemy> itEp = eps.iterator();
        while (itEp.hasNext()) {
            Enemy ep = itEp.next();
            Iterator<Fire> itF = fs.iterator();
            boolean shot = false;
            while (itF.hasNext()) {
                Fire f = itF.next();
                if (ep.isShot(f)) {
                    shot=true;
                    itF.remove();
                }
            }
            if(shot){
                ep.hp-=10;//子弹打中生命-10
                if(ep.hp<=0) {//敌机死亡
                    itEp.remove();
                    score += ep.sc;//打中加分
                }
            }
        }
    }


    //生成敌机
    int index = 0;//下面的函数执行20次才生成一个敌机
    private void epEnter() {
        index++;
        if(index>=20){
            Enemy enemy = new Enemy();
            eps.add(enemy);
            index = 0;//归零
        }
    }

    //敌机移动
    private void epMove() {
        for (Enemy ep : eps) {
            ep.move();
        }
    }


    //生成子弹（三排霰弹）
    int findex=0;
    private void shoot() {
        findex++;
        if(findex>=20){
            int hx=hero.x;
            int hy=hero.y;
            Fire fire1=new Fire(hx+15,hy,0);
            fs.add(fire1);
            Fire fire2=new Fire(hx+45,hy-20,1);
            fs.add(fire2);
            Fire fire3=new Fire(hx+75,hy,2);
            fs.add(fire3);
            findex=0;
        }
    }

    //子弹移动
    private void shootMove() {
        for (Fire f:fs) {
            f.move();
        }
    }
}
