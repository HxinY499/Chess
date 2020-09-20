/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.logging.*;

/**
 *
 * @author lenovo
 */
public class OnlineChess extends JPanel implements MouseListener, ActionListener, Runnable,FocusListener {

    int width = Toolkit.getDefaultToolkit().getScreenSize().width;
    int height = Toolkit.getDefaultToolkit().getScreenSize().height;
    int x;
    int y;
    int x2;
    int y2;
    String str,chat;
    int[][] chess = new int[20][20];
    boolean gameOver = true;
    boolean blackTurn = true;
    boolean control = true;
    InetAddress inetAddress;
    String othersIP;
    int clientPort;
    int serverPort;
    static Thread t;
    static JTextField text1,text2,text3,text4;JButton button1;JLabel label1,label2,label3;

    public OnlineChess() {
        JFrame this1 = new JFrame();
        this1.setTitle("五子棋");
        this1.setLayout(null);
        this1.setSize(1000, 900);
        this1.setLocation((width - 1000) / 2, (height - 900) / 2);
        this1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this1.addMouseListener(this);
        this.setBounds(0, -40, 800, 900);
        this.setBackground(Color.white);
        this1.add(this);
        button1 = new JButton("搜索用户");
        button1.setBounds(810, 250, 160, 30);
        button1.addActionListener(this);
        this1.add(button1);
        text1 = new JTextField("");
        text1.setBounds(870, 100, 100, 30);
        text2 = new JTextField("");
        text2.setBounds(870, 150, 100, 30);
        text3 = new JTextField("");
        text3.setBounds(870, 200, 100, 30);
        String hint1 = "请输入对方IP地址";
        String hint2 = "请输入己方端口号";
        String hint3 = "请输入对方端口号";
        text1.setText(hint1);
        text2.setText(hint2);
        text3.setText(hint3);
        text1.addFocusListener(new chess.MyFocusListener(hint1, text1));
        text2.addFocusListener(new chess.MyFocusListener(hint2, text2));
        text3.addFocusListener(new chess.MyFocusListener(hint3, text3));
        this1.add(text1);
        this1.add(text2);
        this1.add(text3);
        text4 = new JTextField("");
        text4.setBounds(815, 500, 150, 50);
        this1.add(text4);
        Font font=new Font("宋体",Font.BOLD,15);
        label1 = new JLabel ("IP地址");
        label1.setFont(font);
        label1.setBounds(810, 100, 50, 30);
        label2 = new JLabel("端口号");
        label2.setFont(font);
        label2.setBounds(810, 150, 50, 30);
        label3 = new JLabel("端口号");
        label3.setFont(font);
        label3.setBounds(810, 200, 50, 30);
        this1.add(label1);
        this1.add(label2);
        this1.add(label3);
        this1.setVisible(true);
        t = new Thread(this);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.black);
        g.setFont(new Font("华文行楷", Font.BOLD, 40));
        g.drawString("五子棋大战", 300, 80);
        g.setFont(new Font("宋体", 0, 30));
        for (int i = 0; i < 15; i++) {
            g.drawLine(50, 120 + 50 * i, 750, 120 + 50 * i);
            g.drawLine(50 + 50 * i, 120, 50 + 50 * i, 820);
        }

        g.fillOval(197, 267, 6, 6);
        g.fillOval(597, 267, 6, 6);
        g.fillOval(197, 667, 6, 6);
        g.fillOval(597, 667, 6, 6);
        g.fillOval(197, 267, 6, 6);
        g.fillOval(397, 467, 6, 6);

        Graphics2D g3 = (Graphics2D) g;
        Stroke stroke = new BasicStroke(3.0f);
        Stroke stroke1 = new BasicStroke(1.0f);
        g3.setStroke(stroke);
        g3.drawLine(50, 120, 750, 120);
        g3.drawLine(50, 820, 750, 820);
        g3.drawLine(50, 120, 50, 820);
        g3.drawLine(750, 120, 750, 820);

        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                if (chess[i][j] == 1) {
                    int x1 = i * 50 + 50;
                    int y1 = j * 50 + 120;
                    g.setColor(Color.black);
                    g.fillOval(x1 - 20, y1 - 20, 40, 40);
                }
                if (chess[i][j] == 2) {
                    int x1 = i * 50 + 50;
                    int y1 = j * 50 + 120;
                    g.setColor(Color.white);
                    g.fillOval(x1 - 20, y1 - 20, 40, 40);
                    g.setColor(Color.black);
                    g3.setStroke(stroke1);
                    g3.drawOval(x1 - 20, y1 - 20, 40, 40);
                }
            }
        }
    }

    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(clientPort);
            while (true) {
                Socket s = serverSocket.accept();
                InputStream inputs = s.getInputStream();
                byte[] b = new byte[1024];
                int len = inputs.read(b);
                String strReceive = new String(b, 0, len);
                System.out.println(strReceive);
                String[] strs = strReceive.split("-");
                x2 = Integer.parseInt(strs[0]);
                y2 = Integer.parseInt(strs[1]);
                getChess();
                judge();
                control = true;
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "连接失败，用户正忙或用户信息错误");
            System.exit(0);
        }
    }

    public void getChess() {
        x = (x2 - 25) / 50;
        y = (y2 - 95) / 50;
        if (chess[x][y] == 0) {
            if (blackTurn == true) {
                chess[x][y] = 1;
                blackTurn = false;
            } else {
                chess[x][y] = 2;
                blackTurn = true;
            }
        }
        this.repaint();
    }

    public void judge() {
        boolean win = false;
        int totalChess = 1;
        int currentChess = chess[x][y];
        totalChess = count(1, 0, currentChess);
        if (totalChess >= 5) {
            win = true;
        } else {
            totalChess = count(0, 1, currentChess);
            if (totalChess >= 5) {
                win = true;
            } else {
                totalChess = count(1, -1, currentChess);
                if (totalChess >= 5) {
                    win = true;
                } else {
                    totalChess = count(1, 1, currentChess);
                    if (totalChess >= 5) {
                        win = true;
                    }
                }
            }
        }
        if (win == true) {
            if (chess[x][y] == 1) {
                JOptionPane.showMessageDialog(this, "黑方获胜");
            }
            if (chess[x][y] == 2) {
                JOptionPane.showMessageDialog(this, "白方获胜");
            }
            gameOver = true;
        }
    }

    public int count(int x1, int y1, int currentChess) {
        int totalChess = 1;
        int tx = x1;
        int ty = y1;
        while ((x + x1) >= 0 && (y + y1) >= 0 && currentChess == chess[x + x1][y + y1]) {
            totalChess++;
            if (x1 != 0) {
                x1++;
            }
            if (y1 != 0) {
                if (y1 > 0) {
                    y1++;
                } else {
                    y1--;
                }
            }
        }
        x1 = tx;
        y1 = ty;
        while ((x - x1) >= 0 && (y - y1) >= 0 && currentChess == chess[x - x1][y - y1]) {
            totalChess++;
            if (x1 != 0) {
                x1++;
            }
            if (y1 != 0) {
                if (y1 > 0) {
                    y1++;
                } else {
                    y1--;
                }
            }
        }
        return totalChess;
    }

    public void sendPoint(int sx, int sy) {
        try {
            Socket socket = new Socket(inetAddress, serverPort);
            OutputStream outputs = socket.getOutputStream();
            str = sx + "-" + sy;
            outputs.write(str.getBytes());
            socket.shutdownOutput();
            OnlineChess.text4.setFont(new Font("宋体", Font.BOLD, 30));
            OnlineChess.text4.setText(str);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "连接失败，用户正忙或用户信息错误");
            System.exit(0);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("搜索用户")) {
            othersIP = text1.getText();
            String s1 = text2.getText();
            clientPort = Integer.parseInt(s1);
            String s2 = text3.getText();
            serverPort = Integer.parseInt(s2);
            try {
                inetAddress = InetAddress.getByName(othersIP);
            } catch (UnknownHostException ex) {
                Logger.getLogger(OnlineChess.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (inetAddress != null) {
                JOptionPane.showMessageDialog(null, "游戏开始");
                gameOver = false;
                t.start();
            }
        }
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (gameOver == false) {
            x2 = e.getX();
            y2 = e.getY();
            if (x2 >= 50 && x2 <= 750 && y2 >= 120 && y2 <= 820) {
                x = (x2 - 25) / 50;
                y = (y2 - 95) / 50;
            }
            if (chess[x][y] != 0) {
                JOptionPane.showMessageDialog(this, "此处已有棋子");
                return;
            }
            if (control == true) {
                getChess();
                sendPoint(x2, y2);
                judge();
            }
        }
        control = false;
    }

    public static void main(String[] args) {
        // TODO code application logic here
        OnlineChess on = new OnlineChess();

    }

    @Override
    public void mousePressed(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void focusGained(FocusEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void focusLost(FocusEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
