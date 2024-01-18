package model.enemy;

import model.MapConfig;
import model.Mob;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Enemy extends Mob {
    // private boolean die;
    // private boolean walk;
    // private boolean attack;
    private int posiX;
    public int currentFrameIndex;

    public Enemy(String name, String Display, int damage) {
        super(name, Display);
        super.setDamageIntensity(damage);
        this.timer();
    }

    /////////////////////////// ACCESSEURS ET MUTATEURS ///////////////////////////
    // public boolean isDie() {
    // return die;
    // }

    // public void setDie(boolean die) {
    // this.die = die;
    // }

    // public boolean isWalk() {
    // return walk;
    // }

    // public void setWalk(boolean walk) {
    // this.walk = walk;
    // }

    // public boolean isAttack() {
    // return attack;
    // }

    // public void setAttack(boolean attack) {
    // this.attack = attack;
    // }

    public int getPosiX() {
        return this.posiX;
    }

    public void setPosiX(int n) {
        this.posiX = n;
    }

    /////////////////////////// ACCESSEURS ET MUTATEURS ////////////////////////////

    public void attack(MapConfig battle) {
        int i = this.getDeltaXY()[0];
        int j = this.getDeltaXY()[1];
        battle.map[i][j].getMob()
                .setHealth_points(battle.map[i][j].getMob().getHealth_points() - super.getDamageIntensity());
        if (battle.map[i][j].getMob().getHealth_points() <= 0) {
            // il faut parcourir arraylist pour le supprimer de l'arraylist
            battle.suppAttack(battle.map[i][j].getMob());
            battle.map[i][j].setMob(null);
            battle.map[i][j].setPresent(false);
        }
    }

    public void attack(int x, int y, MapConfig battle) {
        int i = this.getDeltaXY()[0];
        int j = this.getDeltaXY()[1];
        battle.map[i][j - 1].getMob().setHealth_points(battle.map[i][j - 1].getMob().getHealth_points() - 2);
        if (battle.map[i][j - 1].getMob().getHealth_points() <= 0) {
            battle.getDEFENDER().remove(battle.map[i][j - 1].getMob());
            battle.map[i][j - 1].setMob(null);
            battle.map[i][j - 1].setPresent(false);
            battle.map[i][j - 1].Set_Enemy_present(false);
        }
    }

    @Override
    public String get_display() {
        int health_points = this.getHealth_points();
        if (health_points <= 9) {
            return super.get_display() + super.get_display() + health_points;
        }
        return super.get_display() + this.getHealth_points();
    }

    public void timer() {
        Timer timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                posiX -= 1;
                currentFrameIndex = (currentFrameIndex + 1) % 17;
                // if (isDie() && currentFrameIndex == 12) {
                // ((Timer) e.getSource()).stop();
                // } else if (isAttack() && currentFrameIndex == 12) {
                // currentFrameIndex = 0;
                // } else if (currentFrameIndex == 18) {
                // currentFrameIndex = 0;
                // }
            }
        });
        timer.start();
    }

}