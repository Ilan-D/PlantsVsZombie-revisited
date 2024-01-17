package model.enemy;

public class Zombie extends Enemy {
    private static final String display = "x";
    private final static String file_path = "src/img/zombie/Frame";

    public Zombie() {
        super("zombie", display, 2);
        super.setUrl(file_path);
        super.setHealth_points(10);
    }

}