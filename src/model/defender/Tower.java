package model.defender;

import model.*;

public class Tower extends Mob {

    public Tower(String name, String display, int damage) {
        super(name, display);
        super.setDamageIntensity(damage);
    }

    @Override
    public void attack(MapConfig battle) {
        int posi = this.getEnemyPresent(battle, this.getDeltaXY()[0]);
        if (posi > 0) {
            Case currentCase = battle.map[this.getDeltaXY()[0]][posi];
            Mob enemy = currentCase.getMob();
            if (enemy != null) {
                int healthPoints = enemy.getHealth_points();
                if (healthPoints <= 0) {
                    // || (enemy.getHealth_points() == super.getDamageIntensity())) {
                    currentCase.Set_Enemy_present(false);
                    currentCase.setPresent(false);
                    currentCase.setMob(null);
                    battle.setCompteurEnemy(battle.getCompteurEnemy() - 1);
                } else {
                    enemy.setHealth_points(healthPoints - super.getDamageIntensity());
                }
            }
        }
    }

    public void attack2(MapConfig battle) {
        int posi = this.getEnemyPresent(battle, this.getDeltaXY()[0]);
        if (posi > 0) {
            Case currentCase = battle.map[this.getDeltaXY()[0]][posi];
            Mob enemy = currentCase.getMob();
            if (enemy != null) {
                int healthPoints = enemy.getHealth_points();

                enemy.setHealth_points(healthPoints - super.getDamageIntensity());
            }
        }
    }

    public int getEnemyPresent(MapConfig battle, int line) {
        for (int j = 0; j < battle.map[0].length - 4; j++) {
            if (battle.map[line][j].Get_Enemy_present()) {
                return j;
            }
        }
        return -1;
    }

    public boolean getEnemyPresent_column(MapConfig plate, int line, int column) {
        System.out.println(line + "    ,          " + column);
        if (plate.map[line][column].Get_Enemy_present()) {
            return true;
        }
        return false;
    }
}