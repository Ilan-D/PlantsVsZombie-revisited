package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.imageio.ImageIO;

public class Mode extends JFrame implements ActionListener {

    private JButton Marathon;
    private JButton Normal;
    private JButton Libre;
    private int posiX, posiY;
    private Image background;

    public Mode() {

        this.initializeBackground();
        this.setLayout(null);
        this.setSize(900, 800);
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.posiX = 80;
        this.posiY = 600;

        String[] buttonLabels = { "MARATHON", "NORMAL", "LIBRE" };
        JButton[] buttons = new JButton[buttonLabels.length];

        for (int i = 0; i < buttonLabels.length; i++) {
            buttons[i] = createStyledButton(buttonLabels[i]);
            buttons[i].setBounds(posiX, posiY, 200, 50);
            buttons[i].addActionListener(this);
            this.add(buttons[i]);
            posiX += 250;
        }

        Marathon = buttons[0];
        Normal = buttons[1];
        Libre = buttons[2];
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
        if (e.getSource() == Marathon) {
            System.out.println("Marathon");
            // PlantvsZombie pvz = new PlantvsZombie();
            // this.dispose();
        } else if (e.getSource() == Normal) {
            createDifficultyDialog();
            // this.dispose();
        } else if (e.getSource() == Libre) {
            System.out.println("Libre");
            // this.dispose();
        }
    }

    private void createDifficultyDialog() {
        String[] options = { "Facile", "Moyen", "Difficile" };
        int choice = JOptionPane.showOptionDialog(this, "Choisir la difficulté", "Difficulté",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

        switch (choice) {
            case 0:
                System.out.println("Facile");
                // Ici, vous pouvez ajouter le code pour le mode "Facile"
                break;
            case 1:
                System.out.println("Moyen");
                // Ici, vous pouvez ajouter le code pour le mode "Moyen"
                break;
            case 2:
                System.out.println("Difficile");
                // Ici, vous pouvez ajouter le code pour le mode "Difficile"
                break;
            default:
                // Annulation ou fermeture de la boîte de dialogue
                break;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Mode());
    }
}
