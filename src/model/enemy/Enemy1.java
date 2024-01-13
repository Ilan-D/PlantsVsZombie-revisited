package model.enemy;

public class Enemy1 extends Mechant {
    private static final String display = "x";
    private boolean Here = true;
    private final int damage = 2;
    private final static String file_path = "src/img/zombie/Frame";

    public Enemy1() {
        // super("Enemy1", display);
        super("Plant", display, 2);
        super.setUrl(file_path);
        super.setHealth_points(10);
    }

}