package ui;

import javax.swing.*;

public class MyFrame extends JFrame{
    MyFrame(){
        setTitle("飞机大战");
        setSize(512,768);
        setLocationRelativeTo(null);
        //设置不可调整窗口大小
        setResizable(false);
        //设置关闭窗体时杀死进程
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    };
    public static void main(String[] args) {
        MyFrame frame = new MyFrame();
        MyPanel panel = new MyPanel(frame);
        frame.add(panel);
        frame.setVisible(true);
        //开始游戏
        panel.action();
    }
}
