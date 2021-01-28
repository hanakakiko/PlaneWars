package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.security.Key;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

public class MyPanel extends JPanel {
    //敌机集合
    List<Enemy> eps = new ArrayList<Enemy>();//创建敌机大本营
    List<Fire> fs = new ArrayList<Fire>();//创建弹药库（最好写在Hero里）
    BufferedImage bgIm;//背景图片
    Hero hero = new Hero();//战斗机
    Enemy enemy = new Enemy();//敌机
    BufferedImage fire;//战火（子弹）
    MyPanel(MyFrame frame){
        bgIm = ImageUtil.getImage("/image/bg1.jpg");
        fire = ImageUtil.getImage("/image/fire.png");
        //设置背景颜色，其实会被背景图片覆盖，也可不设(如果窗口可拉伸那就会露出底色)
        setBackground(Color.GRAY);
        //创建鼠标监听器
        MouseAdapter mouseAdapter = new MouseAdapter() {
            public void mouseMoved(MouseEvent e){
                int mx=e.getX();
                int my=e.getY();
                hero.moveToMouse(mx,my);
                repaint();
            };
        };
        addMouseListener(mouseAdapter);
        addMouseMotionListener(mouseAdapter);
        //创建键盘适配器
        KeyAdapter keyAdapter = new KeyAdapter() {
            //按下键盘时会触发的方法
            public void keyPressed(KeyEvent e){
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
                repaint();
            };
        };
        //加到框体的键盘监听器里
        frame.addKeyListener(keyAdapter);
    };

    @Override
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
        //画分数
        g.setFont(new Font("宋体",Font.PLAIN,18));
        g.setColor(Color.BLACK);
        g.drawString("分数：100", 20,20);
        //画战斗机
        g.drawImage(hero.img,hero.x,hero.y,hero.w,hero.h, null);
        //遍历画敌机

        for (Enemy enemy : eps) {
            g.drawImage(enemy.img, enemy.x, enemy.y, enemy.w, enemy.h, null);
        }
        //画子弹
        for(Fire f:fs){
            g.drawImage(f.img,f.x ,f.y,f.w,f.h,null);
        }
    }

    //开始游戏
    public void action(){
        new Thread(() -> {
            while(true){
                epEnter();
                epMove();
                shoot();
                shootMove();
                shot();//遍历敌机库和子弹库，检测是否有碰撞
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                repaint();
            }
        }).start();
    }

    private void shot() {
        Iterator<Enemy> itEp = eps.iterator();
        while(itEp.hasNext()){
            Iterator<Fire> itF=fs.iterator();
            while(itF.hasNext()){
                if(itEp.next().isShot(itF.next())){
                    itEp.remove();
                    itF.remove();
                }
            }
        }
    }

    private void shootMove() {
        for (Fire f:fs) {
            f.move();
        }
    }

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

    private void epMove() {
        for (Enemy ep : eps) {
            ep.move();
        }
    }

    int index = 0;//下面的函数执行20次才生成一个敌机
    private void epEnter() {
        index++;
        if(index>=20){
            Enemy enemy = new Enemy();
            eps.add(enemy);
            index = 0;//归零
        }
    }
}
