package model.defender;

public class PeaShooter extends Tower {

    public int damage_intensity = 2;
    private final int price = 80;

    public PeaShooter() {
        super("peashooter", "N", 2);
    }

    @Override
    public String get_display() {
        int health_points = this.getHealth_points();
        if (health_points <= 9) {
            return super.get_display() + super.get_display() + this.getHealth_points();
        }
        return super.get_display() + 10;
    }

    public int getPrice() {
        return price;
    }
}