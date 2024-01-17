package model;

public abstract class Mob {
    private int[] DeltaXY = new int[2];
    private String name;
    private int health_points;
    private String display;
    private int damage_intensity;
    private String Url;
    private int colonnePosi;
    private int posiX;

    // private static final int damage;

    public Mob(String name) {
        this.name = name;
        this.health_points = 10;
        // this.Alive = true;
    }

    public Mob(String name, String display) {
        this.name = name;
        this.display = display;
        this.health_points = 10;
        // this.Alive = true;

    }

    // les methodes que les defenseur et Asaillant doivent avoir
    public abstract void attack(MapConfig map);

    /////////////////////////// ACCESSEURS ///////////////////////////
    public int[] getDeltaXY() {
        return this.DeltaXY;
    }

    public String getNom() {
        return this.name;
    }

    public int getHealth_points() {
        return this.health_points;
    }

    public void setHealth_points(int n) {
        this.health_points = n;
    }

    public boolean Alive() {
        return (this.health_points > 0);
    }

    public void setNom(String nom) {
        this.name = nom;
    }

    public void setDelatXY(int x, int y) {
        this.DeltaXY[0] = x;
        this.DeltaXY[1] = y;
    }

    public void set_display(String display) {
        this.display = display;
    }

    public String get_display() {
        return display;
    }

    public int getDamageIntensity() {
        return this.damage_intensity;
    }

    public void setDamageIntensity(int n) {
        this.damage_intensity = n;
    }

    public int getColonnePosi() {
        return this.colonnePosi;
    }

    public void setColonnePosi(int n) {
        this.colonnePosi = n;
    }

    public int getPosiX() {
        return this.posiX;
    }

    public void setPosiX(int n) {
        this.posiX = n;
    }

    // public int getCurrentFrameIndex() {
    // return this.currentFrameIndex;
    // }
    /////////////////////////// ACCESSEURS ///////////////////////////

    //// ------------////

    //// ACCESSEUR GUI////
    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }
    //// ACCESSEUR GUI////

}
