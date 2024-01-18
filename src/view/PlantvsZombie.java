package view;

import model.*;
import model.defender.*;
import model.enemy.*;

import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.Timer;

import controller.Mouse;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import javax.imageio.ImageIO;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

import java.util.ArrayList;

public class PlantvsZombie extends JPanel {

    private MapConfig battle;
    private Case[][] map;
    private Image background;
    private int currentFrameIndex = 0;
    private int gifX = 0;
    private int gifXTMP = 0;
    private int posiX = 0;
    private int posiY = 0;
    private ArrayList<Mob> enemies = new ArrayList<>();
    private int[][] position;
    private JLabel money = new JLabel();
    // --------->> for shop <<---------//
    private Image backgroundShop;
    private card peashooter = new card(0, "peashooter");
    private card sun = new card(1, "sunflower");
    private card nut = new card(2, "nut");
    private boolean is_image_clicked_peashooter = false;
    private ImageIcon imageIconPea;
    private boolean is_image_clicked_nut = false;

    private ImageIcon imageIconNut;
    // --------->> for shop <<---------//
    private int cellWidth, cellHeight;
    private Timer attackTimer;
    private JFrame frame;
    private boolean finish = false;
    private boolean end_of_death = false;
    private int currentFrameIndexDeath;
    private boolean hasDisplayedWinDialog = false; // pour la boite de dialogue
    private int choiceMap;

    public PlantvsZombie(int a, int choiceMap) {

        if (a == 1) {
            this.battle = new MapConfig(a);
        }
        if (a == 2) {
            this.battle = new MapConfig(a);
        }
        if (a == 3) {
            this.battle = new MapConfig(a);
        }
        if (a == 3) {
            this.battle = new MapConfig(a);
        }
        if (a == 4) {
            this.battle = new MapConfig(a);
        }
        this.choiceMap = choiceMap;
        this.map = battle.getMap();

        this.setPreferredSize(new Dimension(1200, 800));
        this.cellHeight = (this.getHeight() - 100) / this.map.length;
        // this.cellHeight = 700 / 5; // a voir ce probleme
        this.cellWidth = 1200 / 11;

        this.position = new int[battle.getCompteurEnemy()][2];

        this.initialiseEnemies();
        this.initializeBackground();
        this.timer();
        this.initializeShopBackground();

        this.money.setBounds(300, 70, 500, 500);
        this.money.setBackground(new Color(0, 100, 100, 0));
        this.add(money);

        Mouse mouseListener = new Mouse(this);
        this.addMouseListener(mouseListener);
        this.addMouseMotionListener(mouseListener);

        this.frame = new JFrame("Game Window");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.pack();
        frame.setSize(new Dimension(1200, 800));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.add(this);

        startPage = new Start();
        startPage.setVisible(false);
    }

    private Start startPage;

    /////////////////////////// DISPLAY GUI ///////////////////////////

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        String money = this.battle.getPlayer().getMoney() + "";

        // Draw background and details //
        g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
        g.drawImage(backgroundShop, 15, 3, null);

        // // affichage des cartes du shop //
        if (this.battle.getPlayer().getMoney() >= 100) {
            sun.showinshop_canbuy(g);
            peashooter.showinshop_canbuy(g);
            nut.showinshop_canbuy(g);
        } else if (this.battle.getPlayer().getMoney() >= 50) {
            peashooter.showinshop_cannotbuy(g);
            sun.showinshop_canbuy(g);
            nut.showinshop_canbuy(g);
        } else if (this.battle.getPlayer().getMoney() >= 25) {
            peashooter.showinshop_cannotbuy(g);
            sun.showinshop_canbuy(g);
            nut.showinshop_cannotbuy(g);
        } else {
            sun.showinshop_cannotbuy(g);
            peashooter.showinshop_cannotbuy(g);
            nut.showinshop_cannotbuy(g);
        }

        g.drawString(money, 35, 80);
        this.drawCase(g, cellHeight, cellWidth);

        this.cellHeight = (this.getHeight() - 100) / this.map.length; // a retirer
        // car deja dans le constrcuteur !!
        // this.cellWidth = 1200 / 11;

        if (is_image_clicked_peashooter) {
            drawImage(g, "src/img/peashooter.gif", this.imageIconPea);
        }
        if (is_image_clicked_nut) {
            drawImage(g, "src/img/nut.gif", this.imageIconNut);
        }

        drawMob(g);

        if (this.battle.getCompteurEnemy() == 0) {
            if (!hasDisplayedWinDialog) {
                JButton returnToStartButton = createStyledButton("VOUS AVEZ GAGNÉ ! ACCUEIL");
                returnToStartButton.setBounds(300, 400, 300, 50);
                returnToStartButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        showStartPage();
                    }
                });
                add(returnToStartButton);
                hasDisplayedWinDialog = true;
            }
        }
    }

    private void showStartPage() {
        this.frame.dispose();
        startPage.setVisible(true);
    }

    private JButton createStyledButton(String buttonText) {
        JButton button = new JButton(buttonText) {
            {
                setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
                setBackground(new Color(200, 100, 100));
                setOpaque(true);
                setFocusPainted(false);
                setBorderPainted(false);

                addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseEntered(java.awt.event.MouseEvent evt) {
                        setBackground(new Color(220, 220, 220));
                    }

                    public void mouseExited(java.awt.event.MouseEvent evt) {
                        setBackground(new Color(200, 100, 100));
                    }
                });
            }
        };
        return button;
    }

    public void drawMob(Graphics g) {
        boolean attack = true;
        int counter_X = 0;
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {

                Case currentCase = map[i][j];

                // this.posiX =; this.posiY =; // penser a factoriser le plus possible le code
                Mob currentMob = map[i][j].getMob();
                if (currentMob != null) {

                    if (currentCase.Get_Enemy_present() && currentMob.getClass() == Zombie.class) {

                        posiX = ((currentMob.getColonnePosi() * cellWidth) - cellWidth) +
                                currentMob.getPosiX();
                        // gifXTMP = ((j * cellWidth) / cellWidth);

                        posiY = (i * cellHeight) + 100;
                        position[counter_X][0] = posiY / cellHeight;
                        position[counter_X][1] = (posiX + cellWidth + cellWidth / 4) / cellWidth;

                        if (j != 1 && j != 0 && map[i][j - 1].getPresent() && !map[i][j -
                                1].Get_Enemy_present()) {
                            currentMob.setAttack(true);
                            g.drawImage(
                                    new ImageIcon(
                                            "src/img/zombieeat/Frame" + currentMob.getCurrentFrameIndex() + ".png")
                                            .getImage(),
                                    ((j) * cellWidth) - 20, posiY, 100, 100, null);
                            currentMob.setPosiX(currentMob.getPosiX() + 1);
                            attackDefenseWithDelay(g, i, j);

                            if (j != 1 && map[i][j - 1].getMob().getHealth_points() <= 0) {
                                clean(i, j - 1);
                                currentMob.setAttack(false); // il faut remettre donc a false car il est mort donc plus
                                // personne !
                            }
                            // if (currentMob.getHealth_points() <= 0) {
                            // map[i][j].setMob(null);
                            // map[i][j].setPresent(false);
                            // map[i][j].Set_Enemy_present(false);
                            // this.battle.setCompteurEnemy(this.battle.getCompteurEnemy() - 1);
                            // }

                        } else if (currentMob.getHealth_points() <= 0) {

                            if (!currentMob.isDie()) {
                                currentMob.setCurrentFrameIndex(0);
                            }

                            currentMob.setPosiX(currentMob.getPosiX() + 1);
                            currentMob.setDie(true);

                            drawDeath(g, "src/imgzombiedie/Frame", ".png", posiX, posiY, currentMob);

                            if (currentMob.getCurrentFrameIndex() == 12) {
                                clean(i, j);
                            }

                        } else {
                            g.drawImage(new ImageIcon(
                                    "src/img/zombie/Frame" + currentMob.getCurrentFrameIndex() + ".png").getImage(),
                                    posiX,
                                    posiY, 100, 100, null);
                            if (position[counter_X][1] != j) {
                                move(i, j);
                                // gifXTMP = ((j * cellWidth)) / cellWidth;
                                battle.afficher();
                                if (attack) {
                                    battle.attack2();
                                    attack = false;
                                }
                            }
                        }
                        if (posiX + cellWidth == 0) { // pour la fin s'il gagne à changer
                            end(g);
                        }

                        counter_X += 1;

                    } else if (currentCase.getPresent() && currentMob.getClass() == PeaShooter.class) {
                        g.drawImage(new ImageIcon("src/img/peashooter.gif").getImage(), (j *
                                cellWidth) + 10,
                                (i * cellHeight) + cellHeight - 30, 80, 80, null);

                        attackEnemyWithDelay(g, i, j);
                    } else if (currentCase.getPresent() && currentMob.getClass() == DefenderNut.class) {
                        g.drawImage(new ImageIcon("src/img/nut.gif").getImage(), (j * cellWidth) +
                                25,
                                (i * cellHeight) + cellHeight, 50, 50, null);
                    }
                }
            }
        }

    }

    public void drawDeath(Graphics g, String path, String extension, int posiX, int posiY, Mob currentMob) {
        // System.out.println(currentMob.getCurrentFrameIndex());
        g.drawImage(new ImageIcon("src/img/zombiedie/Frame" +
                currentMob.getCurrentFrameIndex() + ".png")
                .getImage(), posiX, posiY, 100, 100, null);

        g.drawImage(
                new ImageIcon("src/img/zombiehead/Frame" + currentMob.getCurrentFrameIndex()
                        + ".png")
                        .getImage(),
                posiX, posiY, 100, 100, null);

    }

    public void attackDefenseWithDelay(Graphics g, int i, int j) { // mauvais timer ne marche que pour une instance !
        if (attackTimer == null || !attackTimer.isRunning()) {
            attackTimer = new Timer(1000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (map[i][j - 1].getMob() != null) {
                        map[i][j - 1].getMob().setHealth_points(map[i][j - 1].getMob().getHealth_points() - 2);
                        // Mechant currentMechant = (Mechant) map[i][j].getMob();
                        // currentMechant.attack(i, j, battle);
                        g.drawImage(new ImageIcon("src/img/zombieeat/Frame" + currentFrameIndex + ".png").getImage(),
                                (posiX - gifX) - 20, posiY, 100, 100, null);
                        attackTimer.stop();
                    }
                }
            });
            attackTimer.start();
        }
    }

    private Timer attackTimer2;

    public void attackEnemyWithDelay(Graphics g, int i, int j) {
        if (attackTimer2 == null || !attackTimer2.isRunning()) {
            attackTimer2 = new Timer(2000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (map[i][j].getMob() != null) {
                        Tower tower = (Tower) map[i][j].getMob();
                        tower.attack2(battle);
                        attackTimer2.stop();
                    }
                }
            });
            attackTimer2.start();
        }
    }

    ////////////////////////////// TIMER //////////////////////////////
    private void timer() {
        Timer timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                repaint();
            }
        });
        timer.start();
    }
    ////////////////////////////// TIMER //////////////////////////////

    ///////////////////////// HELPER FUNCTION /////////////////////////
    public void drawCase(Graphics g, int cellHeight, int cellWidth) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                g.setColor(Color.BLACK);
                g.drawRect(j * cellWidth, (i * cellHeight) + 100, cellWidth, cellHeight);
            }
        }
    }

    public void drawImage(Graphics g, String path, ImageIcon currentImage) {
        try {
            g.drawImage(new ImageIcon(path).getImage(), getMousePosition().x - (currentImage.getIconWidth() / 2),
                    getMousePosition().y - (currentImage.getIconHeight() / 2), 50, 50, null);
        } catch (NullPointerException e) {

        }
    }

    public void move(int i, int j) {
        map[i][j].setPresent(false);
        map[i][j - 1].setPresent(true);
        map[i][j].Set_Enemy_present(false);
        map[i][j - 1].Set_Enemy_present(true);
        Mob tmp = map[i][j].getMob();
        map[i][j].setMob(null);
        map[i][j - 1].setMob(tmp);
    }

    public void add(int line, int column, Mob mob) {
        this.map[line][column].setMob(mob);
        this.map[line][column].setPresent(true);
        this.map[line][column].getMob().setDelatXY(line, column);
    }

    public void clean(int i, int j) {
        if (map[i][j].getMob() instanceof Tower) {
            this.battle.getDEFENDER().remove(map[i][j].getMob());
        }
        map[i][j].setMob(null);
        map[i][j].setPresent(false);
        map[i][j].Set_Enemy_present(false);
    }

    public void initialiseEnemies() {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j].Get_Enemy_present()) {
                    enemies.add(map[i][j].getMob());
                }
            }
        }
    }

    private void initializeBackground() {
        try {
            background = ImageIO.read(new File("src/img/background/backgroundv" + this.choiceMap + ".jpeg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initializeShopBackground() {
        try {
            backgroundShop = ImageIO.read(new File("src/img/background/shopping.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void end(Graphics g) {
        if (!hasDisplayedWinDialog) {
            JButton returnToStartButton = createStyledButton("VOUS AVEZ PERDU ! ACCUEIL");
            returnToStartButton.setBounds(300, 400, 300, 50);
            returnToStartButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    showStartPage();
                }
            });

            add(returnToStartButton);
            hasDisplayedWinDialog = true;
        }

    }

    public void printPosition() {
        for (int i = 0; i < position.length; i++) {
            for (int j = 0; j < position[i].length; j++) {
                if (j == 0) {
                    System.out.print(" i : [" + position[i][j] + "] ");
                } else {
                    System.out.print(" j : [" + position[i][j] + "] ");
                }
            }
            System.out.println();
        }
    }

    ///////////////////////// HELPER FUNCTION /////////////////////////

    /////////////////////////// ACCESSEURS ///////////////////////////
    public Case[][] getMap() {
        return this.map;
    }

    public int getCellWidth() {
        return this.cellWidth;
    }

    public int getCellHeight() {
        return this.cellHeight;
    }

    public boolean getImageClickedPeashooter() {
        return this.is_image_clicked_peashooter;
    }

    public void setImageClickedPeashooter(boolean imagecliked) {
        this.is_image_clicked_peashooter = imagecliked;
    }

    public boolean getImageClickedNut() {
        return this.is_image_clicked_nut;
    }

    public void setImageClickedNut(boolean imagecliked) {
        this.is_image_clicked_nut = imagecliked;
    }

    public MapConfig getBattle() {
        return this.battle;
    }

    public ImageIcon getImageIconPea() {
        return this.imageIconPea;
    }

    public void setImageIconPea(ImageIcon image) {
        this.imageIconPea = image;
    }

    public ImageIcon getImageIconNut() {
        return this.imageIconNut;
    }

    public void setImageIconNut(ImageIcon imageIconNut) {
        this.imageIconNut = imageIconNut;
    }

    /////////////////////////// ACCESSEURS ///////////////////////////S
}
