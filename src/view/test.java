package view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

import view.test;

public class test extends JPanel {
    private int currentFrameIndex2 = 0;
    private boolean t = true;

    public test() {
        this.initialiseDieShow();
        this.setPreferredSize(new Dimension(900, 800));
    }

    @Override
    public void paintComponent(Graphics g) {
        g.clearRect(0, 0, getWidth(), getHeight()); // pour nettoyer l'ancienne image
        g.drawImage(new ImageIcon("src/img/zombiedie/Frame" + currentFrameIndex2 + ".png").getImage(), 0, 0, null);
    }

    public void initialiseDieShow() {
        Timer timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentFrameIndex2 += 1;
                if (currentFrameIndex2 == 12) {
                    ((Timer) e.getSource()).stop();
                }
                repaint();
            }
        });
        timer.start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("Game Window");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                test game = new test();
                frame.add(game);
                frame.setSize(new Dimension(700, 800));

//                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setResizable(false);
                frame.setVisible(true);
            }
        });
    }
}
