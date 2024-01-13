package model.enemy;

import model.MapConfig;
import model.Mob;

public abstract class Enemy extends Mob {
    // private MapConfig mapConfig;
    // private Case[][] map = mapConfig.getMap();
    String file_display;
    

    public Enemy(String name, String display) {
        super(name, display);
    }

  

    @Override /// MOb => display
              // enemey extend mob => get_display()
              // x10 x9
    public String get_display() {
        int health_points = this.getHealth_points();
        if (health_points <= 9) {
            String a = super.get_display();

            char lettre = a.charAt(0);
            int health = super.getHealth_points();
            String healths = String.valueOf(health);
            String print = String.valueOf(lettre) + lettre;

            return print + healths;
        }
        String a = super.get_display();
        return a;
    }

    // public abstract void attack(MapConfig map, int i, int j); // j'envoie les coordonnées dans la signature de la
                                                              // fonction

}
