package model.defender;

import model.*;

public class Defender2 extends Tower {

    public int damage_intensity = 2;
    private final int price = 80;

    public Defender2() {
        super("Bombe", "B", 10);
    }

    @Override
    public String get_display() {
        int health_points = this.getHealth_points();
        if (health_points <= 9) {
            return super.get_display() + super.get_display() + this.getHealth_points();
        }
        return super.get_display() + 10;
    }

    @Override
    public void attack(MapConfig battle) {
   
        int posi_de_defender_line = this.getDeltaXY()[0];
        int posi_de_defender_line_coulum = this.getDeltaXY()[1];

        if (battle.map[posi_de_defender_line][posi_de_defender_line_coulum + 1].Get_Enemy_present()) {
            battle.map[posi_de_defender_line][posi_de_defender_line_coulum + 1].Set_Enemy_present(false);
            battle.map[posi_de_defender_line][posi_de_defender_line_coulum + 1].setPresent(false);
            battle.map[posi_de_defender_line][posi_de_defender_line_coulum + 1].setMob(null);
            battle.map[posi_de_defender_line][posi_de_defender_line_coulum].setPresent(false);
            battle.map[posi_de_defender_line][posi_de_defender_line_coulum].setMob(null);
            battle.getDEFENDER().remove(battle.map[posi_de_defender_line][posi_de_defender_line_coulum].getMob());
        }
    }

    public int getPrice() {
        return price;
    }
}