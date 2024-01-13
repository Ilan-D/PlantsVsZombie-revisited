package model.defender;

public class Defender extends Tower {

    private final int price = 100;

    public Defender() {
        // super("Defender", "C");
        super("Defender", "C", 2);

    }

    public int getPrice() {
        return this.price;
    }

    @Override
    public String get_display() {
        int health_points = this.getHealth_points();
        if (health_points <= 9) {
            return super.get_display() + super.get_display() + this.getHealth_points();

        }
        return super.get_display() + 10;
    }
}