package model;

public class Player {
    private int money;
    private int score;
    private int number_of_kills;

    public Player() {
        money = 10000;
        score = 0;
        number_of_kills = 0;
    }

    //// ACCESSEUR////
    public int getMoney() {
        return money;
    }

    public void setMoney(int n) {
        money = n;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int n) {
        score = n;
    }

    public int number_of_kills() {
        return number_of_kills;
    }

    public void set_number_of_kills(int n) {
        number_of_kills = n;
    }
    //// ACCESSEUR////

}