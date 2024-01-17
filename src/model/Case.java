package model;

public class Case {
    private String display;
    private Mob mob;
    private boolean present;
    private boolean Enemy_present = false;

    public Case(Mob mob) {
        this.present = true;
        this.display = "---";
        this.mob = mob;
    }

    public Case() {
        this(null);
        this.present = false;
    }

    //// ACCESSEUR////
    public Mob getMob() {
        return this.mob;
    }

    public void setMob(Mob mob) {
        this.mob = mob;
    }

    // pour dire si un mob est present
    public boolean getPresent() {
        return this.present;
    }

    public void setPresent(boolean present) {
        this.present = present;
    }

    public String getDisplay() {
        return this.display;
    }

    public void setDisplay(String newDisplay) {
        this.display = newDisplay;
    }

    // pour dire si un ennemi est présent
    public boolean Get_Enemy_present() {
        return this.Enemy_present;
    }

    public void Set_Enemy_present(boolean set) {
        this.Enemy_present = set;
    }
    //// ACCESSEUR////
}
