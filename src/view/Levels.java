package view;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;

public class Levels extends JFrame implements ActionListener {

    private JButton Facile;
    private JButton Moyen;
    private JButton Difficile;
    private JButton closeButton;
    private Image background;
    private int choiceMap;

    public Levels(int choiceMap) {
        this.choiceMap = choiceMap;

        this.setLayout(new BorderLayout());

        this.setSize(900, 800);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setUndecorated(true);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.BLACK);
        ImageIcon image = new ImageIcon("src/img/background/backgroundLevel.jpeg");
        JLabel label = new JLabel(image);
        panel.add(label, BorderLayout.CENTER);
        this.add(panel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();

        // buttonPanel.setBackground(new Color(0, 0, 0)); MODIFIER POUR CHANGER LA
        // COULEUR

        String[] buttonLabels = { "FACILE", "MOYEN", "DIFFICILE", "CLOSE" };
        JButton[] buttons = new JButton[buttonLabels.length];

        for (int i = 0; i < buttonLabels.length; i++) {
            buttons[i] = createStyledButton(buttonLabels[i]);
            buttons[i].addActionListener(this);
            buttonPanel.add(buttons[i]);
        }

        this.add(buttonPanel, BorderLayout.SOUTH);

        Facile = buttons[0];
        Moyen = buttons[1];
        Difficile = buttons[2];
        closeButton = buttons[3];

        this.setVisible(true);
    }

    private JButton createStyledButton(String buttonText) {
        JButton button = new JButton(buttonText) {
            {
                setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
                setBackground(new Color(0, 200, 0));
                setOpaque(true);
                setFocusPainted(false);
                setBorderPainted(false);

                addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseEntered(java.awt.event.MouseEvent evt) {
                        setBackground(new Color(220, 220, 220));
                    }

                    public void mouseExited(java.awt.event.MouseEvent evt) {
                        setBackground(new Color(0, 200, 0));
                    }
                });
            }
        };
        return button;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        g.drawImage(background, 0, 0, 900, 800, null);
    }

    public void initializeBackground() {
        try {
            File f = new File("src/img/background/backgroundLevel.jpeg");
            this.background = ImageIO.read(f);
        } catch (Exception e) {
            String errorMessage = "Problème lors du chargement de l'image : " + e.getMessage();
            JOptionPane.showMessageDialog(this, errorMessage, "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == Facile) {
            PlantvsZombie pvz = new PlantvsZombie(1, choiceMap);
            this.dispose();
        } else if (e.getSource() == Moyen) {
            PlantvsZombie pvz = new PlantvsZombie(2, choiceMap);
            this.dispose();
        } else if (e.getSource() == Difficile) {
            PlantvsZombie pvz = new PlantvsZombie(3, choiceMap);
            this.dispose();
        } else if (e.getSource() == closeButton) {
            this.dispose();
        }
    }
}
