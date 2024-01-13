package model.enemy;

public class Enemy2 extends Mechant {
    // private static final String display = "=";
    // private final int damage = 3;
    private final String file_path = "src/img/redzombie/Frame";

    public Enemy2() {
        super("Enemy1", "=", 3);
        super.setUrl(file_path);
    }

}