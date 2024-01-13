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

import view.zooz;

public class zooz extends JPanel {

    private Image[] dieZombImages;
    private int currentFrameIndex2 = 0;
    private boolean t = true;

    public zooz() {
        this.initialiseDieShow();
        this.setPreferredSize(new Dimension(900, 800));
    }

    @Override
    public void paintComponent(Graphics g) {
        this.getGraphics().drawImage(dieZombImages[currentFrameIndex2], 0, 0, null);
    }

    public void initialiseDieShow() {
        try {
            dieZombImages = new Image[12];
            for (int pages = 0; pages < 12; pages++) {
                dieZombImages[pages] = ImageIO.read(new File("src/img/zombiedie/Frame" + pages + ".png"));
            }

            // Créer le mouvement des ennemis
            Timer timer = new Timer(1000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    currentFrameIndex2 = (currentFrameIndex2 + 1) % dieZombImages.length;
                    repaint();
                }
            });
            timer.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("Game Window");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                zooz game = new zooz();
                frame.add(game);

                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setResizable(false);
                frame.setVisible(true);
            }
        });
    }
}
