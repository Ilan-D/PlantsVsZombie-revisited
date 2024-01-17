package view;

import javax.swing.*;
import javax.swing.text.html.ImageView;

import model.Launcher;
import view.Start;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Start implements ActionListener {
    private JFrame frame;
    private JButton Terminal;
    private JButton levels;
    private JButton Marathon;
    private JButton Map;
    private JButton closeButton;
    private int choiceMap = 1;
    private JLabel mapImageLabel;

    public Start() {

        frame = new JFrame();
        frame.setTitle("START");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 800);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setUndecorated(true);
        frame.setBackground(Color.BLACK);

        // Initialisez choiceMap
        choiceMap = 1; // Utilisez le choix initial

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.BLACK);

        // Ajout de l'image au centre
        ImageIcon image = new ImageIcon("src/img/background/backgroundLevel.jpeg");
        JLabel label = new JLabel(image);
        panel.add(label, BorderLayout.CENTER);

        frame.add(panel);

        JPanel buttonPanel = new JPanel();
        String[] buttonLabels = {"TERMINAL", "CHOSE LEVELS", "MARATHON", "MAP", "CLOSE"};
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

    private void showMapPopup() {
        JDialog mapDialog = new JDialog(frame, "Map Popup", true);
        mapDialog.setSize(400, 380);
        mapDialog.setLayout(new BorderLayout());

        // Ajout d'une étiquette et d'éléments pour la fenêtre contextuelle (pop-up)
        Image mapImage = new ImageIcon("src/img/background/backgroundv1.jpeg").getImage();
        mapImage = mapImage.getScaledInstance(350, 300, Image.SCALE_SMOOTH); // Redimensionnez l'image
        mapImageLabel = new JLabel(new ImageIcon(mapImage));
        mapDialog.add(mapImageLabel, BorderLayout.NORTH);

        JButton prevButton = new JButton("Previous");
        System.out.println(this.choiceMap);
        prevButton.addActionListener(event -> changeMap(-1));

        JButton nextButton = new JButton("Next");
        nextButton.addActionListener(event -> changeMap(1));


        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(prevButton);
        buttonPanel.add(nextButton);
        mapDialog.add(buttonPanel, BorderLayout.CENTER);
        mapDialog.setLocationRelativeTo(frame);

        mapDialog.setVisible(true);
    }

    private void changeMap(int delta) {
        if ((delta == -1 && this.choiceMap > 1) || (delta == 1 && this.choiceMap < 4)) {
            this.choiceMap += delta;
        }

        // Mettez à jour l'image affichée avec le chemin relatif depuis le package
        Image mapImage = new ImageIcon("src/img/background/backgroundv" + choiceMap + ".jpeg").getImage();
        mapImage = mapImage.getScaledInstance(350, 300, Image.SCALE_SMOOTH); // Redimensionnez l'image
        mapImageLabel.setIcon(new ImageIcon(mapImage));
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
            Levels level = new Levels(choiceMap);
        } else if (e.getSource() == Marathon) {
            frame.dispose();
            PlantvsZombie pvz = new PlantvsZombie(4, choiceMap);
        } else if (e.getSource() == Map) {
            showMapPopup();
        } else if (e.getSource() == closeButton) {
            frame.dispose();
        }
    }

    public static void main(String[] args) {
        new Start();
    }
}
