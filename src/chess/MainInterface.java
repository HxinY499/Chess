/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 *
 * @author lenovo
 */

public class MainInterface  extends JFrame implements ActionListener{
    int width = Toolkit.getDefaultToolkit().getScreenSize().width;
    int height = Toolkit.getDefaultToolkit().getScreenSize().height;
    OfflineChess off;
    OnlineChess on;
    static Thread t;

    /**
     *
     */
    public MainInterface() {
        this.setLayout(null);
        this.setSize(600, 500);
        this.setLocation((width - 600) / 2, (height - 500) / 2);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        JButton b1 = new JButton("单机模式");
        b1.setBounds(200, 200, 200, 50);
        this.add(b1);
        JButton b2 = new JButton("联机模式");
        b2.setBounds(200, 290, 200, 50);
        this.add(b2);
        b1.addActionListener(this);
        b2.addActionListener(this);
        this.getContentPane().setBackground(Color.white);
        setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        BufferedImage image1 = null;
        try {
            image1 = ImageIO.read(new File("C:/Users/lenovo/Desktop/1.jpg"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        g.drawImage(image1, 0, 0, 500, 400, rootPane);
        g.setFont(new Font("华文行楷", Font.BOLD, 40));
        g.drawString("欢迎来到五子棋大战", 120, 100);
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        MainInterface m = new MainInterface();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("单机模式")) {
            off = new OfflineChess();
            dispose();

        }
        if (e.getActionCommand().equals("联机模式")) {
            on = new OnlineChess();
            dispose();
        }
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
