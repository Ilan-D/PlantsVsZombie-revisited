package view;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Launcher;
import view.Start;

import javax.swing.ImageIcon;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;

public class Start implements ActionListener {
    private JFrame frame;
    private JButton Terminal;
    private JButton levels;
    private JButton Marathon;
    private JButton Map;
    private JButton closeButton;
    public Start() {

        frame = new JFrame();
        frame.setTitle("START");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 800);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setUndecorated(true);
        frame.setBackground(Color.BLACK);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.BLACK);

        // Ajout de l'image au centre
        ImageIcon image = new ImageIcon("src/img/background/backgroundLevel.jpeg");
        JLabel label = new JLabel(image);
        panel.add(label, BorderLayout.CENTER);

        frame.add(panel);

        JPanel buttonPanel = new JPanel();
        String[] buttonLabels = { "TERMINAL", "CHOSE LEVELS", "MARATHON","MAP", "CLOSE"};
        JButton[] buttons = new JButton[buttonLabels.length];

        for (int i = 0; i < buttonLabels.length; i++) {
            buttons[i] = createStyledButton(buttonLabels[i]);
            buttons[i].addActionListener(this);
            buttonPanel.add(buttons[i]);
        }

        frame.add(buttonPanel, BorderLayout.SOUTH);

        Terminal = buttons[0];
        levels = buttons[1];
        Marathon = buttons[2];
        Map = buttons[3];
        closeButton = buttons[4];

        frame.setVisible(true);
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
                        setBackground(new Color(255, 255, 255));
                    }

                    public void mouseExited(java.awt.event.MouseEvent evt) {
                        setBackground(new Color(0, 200, 0));
                    }
                });
            }
        };
        return button;
    }

    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == Terminal) {
            frame.dispose();
            Launcher a = new Launcher();
        } else if (e.getSource() == levels) {
            frame.dispose();
            Levels level = new Levels();
        } else if (e.getSource() == Marathon) {
            frame.dispose();
            PlantvsZombie pvz = new PlantvsZombie(4);
        }else if(e.getSource() == Map){
            System.out.println("bientôt disponible");
        } else if (e.getSource() == closeButton) {
            frame.dispose();
        }
    }
    public static void main(String[] args) {
        new Start();
    }
}
