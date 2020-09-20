/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 *
 * @author lenovo
 */
public class OfflineChess extends JPanel implements MouseListener, ActionListener {

    int width = Toolkit.getDefaultToolkit().getScreenSize().width;
    int height = Toolkit.getDefaultToolkit().getScreenSize().height;
    int x = 0;
    int y = 0;
    int[][] chess = new int[20][20];
    boolean blackTurn = true;
    boolean gameOver = false;
    JButton button1,button2;

    public OfflineChess() {
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
        button1 = new JButton("重新开始");
        button1.setBounds(815, 100, 150, 40);
        this1.add(button1);
        button1.addActionListener(this);
        button2 = new JButton("结束游戏");
        button2.setBounds(815, 200, 150, 40);
        this1.add(button2);
        button2.addActionListener(this);
        this1.setVisible(true);
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

        Graphics2D g2 = (Graphics2D) g;
        Stroke stroke = new BasicStroke(3.0f);
        Stroke stroke1 = new BasicStroke(1.0f);
        g2.setStroke(stroke);
        g2.drawLine(50, 120, 750, 120);
        g2.drawLine(50, 820, 750, 820);
        g2.drawLine(50, 120, 50, 820);
        g2.drawLine(750, 120, 750, 820);

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
                    g2.setStroke(stroke1);
                    g2.drawOval(x1 - 20, y1 - 20, 40, 40);
                }
            }
        }
    }

    public void getChess() {
        if (chess[x][y] == 0) {
            if (blackTurn == true) {
                chess[x][y] = 1;
                blackTurn = false;
            } else {
                chess[x][y] = 2;
                blackTurn = true;
            }
        } else {
            JOptionPane.showMessageDialog(null, "此处已有棋子");
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
                JOptionPane.showMessageDialog(null, "黑方获胜");
            }
            if (chess[x][y] == 2) {
                JOptionPane.showMessageDialog(null, "白方获胜");
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

    @Override
    public void mouseClicked(MouseEvent e) {
        if (gameOver == false) {
            x = e.getX();
            y = e.getY();
            if (x >= 50 && x <= 750 && y >= 120 && y <= 820) {
                x = (x - 25) / 50;
                y = (y - 95) / 50;
                getChess();
                judge();
            }
        }
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
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("重新开始")) {
            int respones = JOptionPane.showConfirmDialog(null, "确认重新开始？", "确认", JOptionPane.YES_NO_OPTION);
            if (respones == JOptionPane.YES_OPTION) {
                for (int i = 0; i < 16; i++) {
                    for (int j = 0; j < 16; j++) {
                        chess[i][j] = 0;
                    }
                }
                gameOver = false;
                blackTurn = true;
                repaint();
            }
        }
        if (e.getActionCommand().equals("结束游戏")) {
            int respones = JOptionPane.showConfirmDialog(null, "确认结束游戏？", "确认", JOptionPane.YES_NO_OPTION);
            if (respones == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        }
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static void main(String[] args) {
        // TODO code application logic here
        OfflineChess off = new OfflineChess();
    }
}
