package ui;

import javax.swing.*;

public class MyFrame extends JFrame{
    public MyFrame(){
        setTitle("飞机大战");
        setSize(512,768);
        //设置框体居中（默认相对左上角居中）
        setLocationRelativeTo(null);
        //设置不可调整窗口大小
        setResizable(false);
        //设置关闭窗体时杀死进程
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    };
    public static void main(String[] args) {
        MyFrame frame = new MyFrame();
        MyPanel panel = new MyPanel(frame);//传入frame是为了加入键盘监听器
        frame.add(panel);//将面板放入框体
        //显示框体
        frame.setVisible(true);
        //开始游戏
        panel.action();
    }
}
