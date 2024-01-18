package controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import model.Player;
import model.defender.*;
import view.PlantvsZombie;

import javax.swing.ImageIcon;

public class Mouse implements MouseListener, MouseMotionListener {

    PlantvsZombie game;

    public Mouse(PlantvsZombie game) {
        this.game = game;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        int column = e.getX() / this.game.getCellWidth();
        int line = (e.getY() - 100) / this.game.getCellHeight();
        Player currentPlayer = this.game.getBattle().getPlayer();

        if (this.game.getImageClickedPeashooter() && currentPlayer.getMoney() >= 100 && column != 0
                && !(this.game.getMap()[line][column].getPresent())) {
            currentPlayer.setMoney(currentPlayer.getMoney() - 100);

            PeaShooter def = new PeaShooter();

            this.game.getBattle().getDEFENDER().add(def);

            this.game.add(line, column, def);

            this.game.setImageClickedPeashooter(false);

            this.game.repaint();

        } else if (this.game.getImageClickedNut() && currentPlayer.getMoney() >= 50 && column != 0
                && !(this.game.getMap()[line][column].getPresent())) {
            currentPlayer.setMoney(currentPlayer.getMoney() - 50);

            DefenderNut def = new DefenderNut();

            this.game.getBattle().getDEFENDER().add(def);

            this.game.add(line, column, def);

            this.game.setImageClickedNut(false);

            this.game.repaint();

        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

        // System.out.println(e.getX() + " , " + e.getY());
        if (((e.getX() >= 98 && e.getX() <= 144)) && (e.getY() >= 17 && e.getY() <= 80) && this.game.getBattle()
                .getPlayer().getMoney() >= 100) {
            this.game.setImageClickedPeashooter(true);
        }
        if (((e.getX() >= 206 && e.getX() <= 253)) && (e.getY() >= 11 && e.getY() <= 80) && this.game.getBattle()
                .getPlayer().getMoney() >= 50) {
            this.game.setImageClickedNut(true);
        }

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (this.game.getImageClickedPeashooter()) {
            this.game.setImageIconPea(new ImageIcon("src/img/plantput/pea.png"));
            this.game.repaint();
        }
        if (this.game.getImageClickedNut()) {
            this.game.setImageIconNut(new ImageIcon("src/img/plantput/nut.png"));
            this.game.repaint();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

}
