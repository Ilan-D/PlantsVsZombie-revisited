
package model;

import model.defender.*;
import model.enemy.*;

import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;

public class MapConfig {

    private int frontiere = 10;
    private int width, height;
    public Case[][] map;
    private int compteurEnemy = 0;
    private static String[] defense = { "N", "C", "B" };
    Player player1;
    public ArrayList<Tower> DEFENDER = new ArrayList<>();
    private static int Vague = 0;
    public int game_mode;
    public int VagueM = 0;

    public MapConfig(int config) {
        basic();
        if (config == 1) {
            game_mode = 1;
        } else if (config == 2) {
            game_mode = 2;

        } else if (config == 3) {
            game_mode = 3;
        } else if (config == 4) {
            game_mode = 4;
        }
        spawn();
    }

    public void basic() {
        this.player1 = new Player();
        this.height = 5;
        this.width = 16;
        this.map = new Case[height][width];
        initialise();
    }

    public void pour_facile() {
        this.game_mode = 1;
        basic();
        System.out.println(this.map.length);
        spawn();
        loop();
    }

    public void pour_Moyen() {
        this.game_mode = 2;
        basic();
        spawn();
        loop1();
    }

    public void pour_dur() {
        game_mode = 3;
        basic();
        loop2();
        spawn();
    }

    public void loop2() {
        this.afficher();
        while (!lose() && (Vague <= 2)) {
            if (win() && (Vague == 2)) {
                this.afficher();
                System.out.println("vous avez gagner");
                return;
            }
            this.player1.setMoney(this.player1.getMoney() + 10);
            if (this.player1.getMoney() >= 100) {
                call();
            } else {
                call_no_money();
            }
            this.move_Left();
            this.attack();
            if (win() && (Vague == 0)) {
                this.afficher();
                spawn2();
                Vague++;
            }
            if (win() && (Vague == 1)) {
                this.afficher();
                spawn3();
                Vague++;
            }
        }
        System.out.println("La partie est terminé, vous avez perdu\n");
    }

    public void Pour_Marathon() {
        game_mode = 4;
        basic();
        loopM();
        spawn();
    }

    public void loopM() {
        this.afficher();
        while (!lose()) {
            if (win() && (VagueM == 2)) {
                spawn_till_death();

                this.afficher();

            }
            this.player1.setMoney(this.player1.getMoney() + 10);
            if (this.player1.getMoney() >= 100) {
                call();
            } else {
                call_no_money();
            }
            this.move_Left();
            this.attack();
            if (win() && (VagueM == 0)) {
                this.afficher();
                spawn2_M();
                VagueM++;
            }

            if (win() && (VagueM == 1)) {
                this.afficher();
                spawn3_M();
                VagueM++;
            }

        }
        System.out.println("La partie est terminé, vous avez perdu\n");
    }

    public void spawn(int numberOfEnemiesToSpawn, int number) {
        Random random = new Random();

        while (numberOfEnemiesToSpawn > 0) {
            int line = random.nextInt(5);
            int column = random.nextInt(6) + 10;

            if (!present(line, column)) {
                if (line < map.length && column < map[0].length) {
                    int random_Type_ennemie = random.nextInt(2);

                    if (random_Type_ennemie == 0) {
                        map[line][column].setMob(new Zombie());
                        map[line][column].Set_Enemy_present(true);
                        map[line][column].setPresent(true);
                    }
                    numberOfEnemiesToSpawn--;
                }
            }
        }
    }

    // spawn2 to spawn 6 enemies
    public void spawn2_M() {
        spawn(1, 1);
    }

    // spawn3 to spawn 3 enemies
    public void spawn3_M() {
        spawn(1, 1);
    }

    public void spawn_till_death() {
        Random random = new Random();

        int numberOfEnemiesToSpawn = find_number_of_free_cases();

        int loopCounter = 0;

        while (numberOfEnemiesToSpawn > 0) {
            loopCounter++;

            if (loopCounter > 1000) {
                System.out.println("pour sortir si il y un problème");
                break;
            }

            int line = random.nextInt(5);
            int column = random.nextInt(6) + 10;

            if (!present(line, column)) {
                if (line < map.length && column < map[0].length) {
                    int random_Type_ennemie = random.nextInt(2);

                    if (random_Type_ennemie == 0) {
                        map[line][column].setMob(new Zombie());
                    }

                    map[line][column].Set_Enemy_present(true);
                    map[line][column].setPresent(true);

                    numberOfEnemiesToSpawn--;

                }
            }
        }

    }

    public int find_number_of_free_cases() {
        int count_free_case = 0;
        for (int i = 0; i < this.map.length; i++) {
            for (int j = 10; j < this.map[i].length; j++) {
                if (!map[i][j].getPresent()) {
                    count_free_case++;
                }
            }
        }
        return count_free_case;
    }

    //// ACCESSEUR////
    public Case[][] getMap() {
        return this.map;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public Player getPlayer() {
        return this.player1;
    }

    public int getCompteurEnemy() {
        return this.compteurEnemy;
    }

    public void setCompteurEnemy(int n) {
        this.compteurEnemy = n;
    }

    public ArrayList<Tower> getDEFENDER() {
        return this.DEFENDER;
    }
    //// ACCESSEUR////

    public void initialise() {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                map[i][j] = new Case();
                if (j == 0) {
                    map[i][j].setDisplay("HHH");
                    map[i][j].setPresent(true);
                }
            }
        }
    }

    public void spawn() {
        Random random = new Random();
        int numberOfEnemies = 4;

        if (game_mode == 3) {
            numberOfEnemies = 10;
        }

        int randomNumber_ennemie = 0;
        compteurEnemy = compteurEnemy + numberOfEnemies;

        while (numberOfEnemies != 0) {
            int enemyType = random.nextInt(2);
            int line = random.nextInt(map.length);
            int column = random.nextInt(10, map[0].length);
            if (!present(line, column)) {
                if (randomNumber_ennemie == 0) {
                    map[line][column].setMob(new Zombie());
                    map[line][column].Set_Enemy_present(true);
                    map[line][column].setPresent(true);
                    map[line][column].getMob().setColonnePosi(column);
                    numberOfEnemies--;
                }
            }
        }
    }

    public boolean present(int x, int y) {
        return map[x][y].getPresent();
    }

    public void print_list_defense() {
        System.out.println(((this.player1.getMoney() <= 1) ? "PIECE" : "PIECES") + " : " + this.player1.getMoney());
        System.out.println((compteurEnemy == 0 ? "ENNEMI : " : "ENNEMIS : ") + compteurEnemy);
        System.out.print("\n--------choix possible : ");
        for (int i = 0; i < defense.length - 1; i++) {
            System.out.print(defense[i] + "/");
        }
        System.out.println(defense[defense.length - 1] + "--------\n");
    }

    public void afficher() {
        set_boolean_Enemy_line();
        this.print_list_defense();
        System.out.print("     ");
        for (int i = 0; i < 11; i++) {
            System.out.print(i + "   ");
        }
        System.out.println("\n");
        for (int i = 0; i < map.length; i++) {
            System.out.print(i + "   ");
            for (int j = 0; j < 11; j++) {
                if (map[i][j].getMob() != null) {
                    System.out.print(map[i][j].getMob().get_display() + " ");
                } else {
                    System.out.print(map[i][j].getDisplay() + " ");
                }
            }
            System.out.println("\n");

        }
        System.out.println();
    }

    public void add_Scanner(Scanner sc1, String piece) {
        String coup = sc1.next();
        if (coup.length() > 2) {
            System.out.println(
                    "vous ne pouvez pas choisir ce nombre. vous ne pouvez pas dépasser le column 10. c'est la limite");
            return;
        }
        int x = coup.charAt(0) - '0';
        int y = coup.charAt(1) - '0';

        if (present(x, y)) {
            System.out.println("il y a deja quelquen ");
        }

        if (x > 10 || y >= this.width) {
            System.out.println("Vous êtes en dehors du tableau, veuillez ressayer");
            this.add_Scanner(new Scanner(System.in), piece);
        } else if (y == 0) {
            System.out.print("Vous n'avez pas le droit de jouer sur votre base, jouez autre part : ");
            add_Scanner(new Scanner(System.in), piece);
        } else if (map[x][y].getPresent() == false) {
            if (piece.equals("N")) {
                PeaShooter defender1 = new PeaShooter();
                map[x][y].setMob(defender1);
                map[x][y].getMob().setDelatXY(x, y);
                map[x][y].setPresent(true);
                DEFENDER.add(defender1);
                this.player1.setMoney(this.player1.getMoney() - defender1.getPrice());
            }
            if (piece.equals("B")) {
                Bombe defender2 = new Bombe();
                map[x][y].setMob(defender2);
                map[x][y].getMob().setDelatXY(x, y);
                map[x][y].setPresent(true);
                DEFENDER.add(defender2);
                this.player1.setMoney(this.player1.getMoney() - defender2.getPrice());
            }

        } else {
            System.out.println("ressayer, case déjà utilisée");
            this.add_Scanner(sc1, piece);
        }
    }

    public boolean check_si(String a) {
        for (int i = 0; i < defense.length; i++) {
            if (a.equals(defense[i])) {
                return true;
            }
        }
        return false;
    }

    public void clear() {
        for (int i = 0; i < this.map.length; i++) {
            for (int j = 0; j <= this.frontiere; j++) {
                map[i][j].Set_Enemy_present(false);

            }
        }
    }

    public void set_boolean_Enemy_line() {
        for (int i = 0; i < this.map.length; i++) {
            for (int j = 0; j <= this.frontiere; j++) {
                if (map[i][j].getMob() != null) {
                    if (map[i][j].getMob().getClass() == Zombie.class) {
                        map[i][j].Set_Enemy_present(true);
                    }
                }
            }
        }
    }

    public void attack() {
        for (int i = 0; i < DEFENDER.size(); i++) {
            DEFENDER.get(i).attack(this);
        }
    }

    public void attack2() {
        for (int i = 0; i < DEFENDER.size(); i++) {
            DEFENDER.get(i).attack2(this);
        }
    }

    public void suppAttack(Mob m) {
        for (int i = 0; i < DEFENDER.size(); i++) {
            if (m == DEFENDER.get(i)) {
                DEFENDER.remove(i);
                break;
            }
        }
    }

    public void printListDefense2() {
        System.out.print("(");
        for (int i = 0; i < defense.length - 1; i++) {
            System.out.print(defense[i] + ",");
        }
        System.out.print(defense[defense.length - 1] + ") : ");
    }

    public void call() {
        System.out.print("Voulez-vous ajouter une pièce ? (o/n) : ");
        Scanner scan = new Scanner(System.in);
        String rescan = scan.next();

        if (rescan.equals("n")) {
            System.out.println("L'ennemi avance\n");
        } else if (rescan.equals("o")) {
            System.out.print("quelle pièce voulez-vous jouer ? : ");
            Scanner piece = new Scanner(System.in);
            String repiece = "" + piece.next().charAt(0);
            while (!this.check_si(repiece)) {
                System.out.print("Vous avez le droit seulement à ");
                printListDefense2();
                piece = new Scanner(System.in);
                repiece = "" + piece.next().charAt(0);
            }
            System.out.print("quel coup voulez-vous jouer ? : ");
            Scanner scan2 = new Scanner(System.in);
            try {
                this.add_Scanner(new Scanner(System.in), repiece);
            } catch (Exception e) {
                System.out.println("Mauvaise entrée, veuillez ressayer : ");
                call();
                return;
            }

        } else {
            System.out.println("\nVous n'avez pas bien répondu (o/n)\n");
            call();
            return;
        }
    }

    public void call_no_money() {
        System.out.println("Appuyer sur entrée vous n'avez pas d'argent");
        Scanner scan = new Scanner(System.in);
        scan.nextLine();
    }

    public void move_Left() {
        for (int i = 0; i < map.length; i++) {
            for (int j = 1; j < map[i].length; j++) {
                if ((this.map[i][j].Get_Enemy_present())
                        && (!this.map[i][j - 1].Get_Enemy_present())) {

                    if (this.map[i][j - 1].getMob() == null) {
                        Mob enemy = this.map[i][j].getMob();
                        this.map[i][j - 1].setMob(enemy);
                        this.map[i][j - 1].Set_Enemy_present(true);
                        this.map[i][j - 1].setPresent(true);

                        this.map[i][j].setMob(null);
                        this.map[i][j].Set_Enemy_present(false);
                        this.map[i][j].setPresent(false);

                    } else if (this.map[i][j - 1].getMob() != null
                            && (this.map[i][j - 1].getMob().getClass() == PeaShooter.class)) {

                        Enemy enemy = (Enemy) this.map[i][j].getMob();
                        this.map[i][j].getMob().setDelatXY(i, j - 1);
                        enemy.attack(this);
                    }
                }
            }
        }
        this.afficher();
    }

    public void move_Left1(int i, int j) {

        if ((this.map[i][j].Get_Enemy_present())
                && (!this.map[i][j - 1].Get_Enemy_present())) {

            if (this.map[i][j - 1].getMob() == null) {
                Mob enemy = this.map[i][j].getMob();
                this.map[i][j - 1].setMob(enemy);
                this.map[i][j - 1].Set_Enemy_present(true);
                this.map[i][j - 1].setPresent(true);

                this.map[i][j].setMob(null);
                this.map[i][j].Set_Enemy_present(false);
                this.map[i][j].setPresent(false);

            } else if (this.map[i][j - 1].getMob() != null
                    && (this.map[i][j - 1].getMob().getClass() == PeaShooter.class)) {

                Enemy enemy = (Enemy) this.map[i][j].getMob();
                this.map[i][j].getMob().setDelatXY(i, j - 1);
                enemy.attack(this);
            }
        }
    }

    public boolean lose() {
        for (int i = 0; i < map.length; i++) {
            if (map[i][0].Get_Enemy_present()) {
                return true;
            }
        }
        return false;
    }

    public boolean win() {
        all_check_Enemy_Down();
        return compteurEnemy == 0 ? true : false;
    }

    public void all_check_Enemy_Down() {
        int compteur = 0;
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j].getMob() != null && (map[i][j].getMob().getClass() == Zombie.class)) {
                    compteur++;
                }
            }
        }
        int res = compteurEnemy - compteur;
        this.player1.setMoney((this.player1.getMoney() + (30 * res)));
        compteurEnemy = compteur;
    }

    public void loop() {
        int round = 1;
        System.out.println(
                "------------------------------------------------------------------------->>> " + "TOUR : " + round
                        + " <<<-------------------------------------------------------------------------");
        this.afficher();
        while (!lose()) {
            this.player1.setMoney(this.player1.getMoney() + 10);
            if (this.player1.getMoney() >= 100) {
                call();
            } else {
                call_no_money();
            }
            round += 1;
            System.out.println(
                    "-------------------------------------------------------------------------->>>" + " TOUR : " + round
                            + " <<<-------------------------------------------------------------------------");
            this.move_Left();
            if (win()) {
                System.out.println("VOUS AVEZ GAGNE(E) !!!\n");
                return;
            }
            this.attack();
        }
        System.out.println("La partie est terminé, vous avez perdu\n");
    }

    public void loop1() {
        this.afficher();
        while (!lose() && (Vague <= 2)) {
            if (win() && (Vague == 2)) {
                this.afficher();
                System.out.println("vous avez gagner");
                return;
            }
            this.player1.setMoney(this.player1.getMoney() + 10);
            if (this.player1.getMoney() >= 100) {
                call();
            } else {
                call_no_money();
            }
            this.move_Left();
            this.attack();
            if (win() && (Vague == 0)) {
                this.afficher();
                spawn2();
                Vague++;
            }
            if (win() && (Vague == 1)) {
                this.afficher();
                spawn3();
                Vague++;
            }
        }
        System.out.println("La partie est terminé, vous avez perdu\n");
    }

    public void spawn(int numberOfEnemiesToSpawn) {
        Random random = new Random();
        while (numberOfEnemiesToSpawn > 0) {
            int line = random.nextInt(5);
            int column = random.nextInt(6) + 10;
            if (!present(line, column)) {
                if (line < map.length && column < map[0].length) {
                    int random_Type_ennemie = random.nextInt(2);
                    if (random_Type_ennemie == 0) {
                        map[line][column].setMob(new Zombie());
                        map[line][column].Set_Enemy_present(true);
                        map[line][column].setPresent(true);
                    }
                    numberOfEnemiesToSpawn--;
                }
            }
        }
    }

    public void spawn2() {
        spawn(6);
    }

    public void spawn3() {
        spawn(3);
    }

    public static void main(String[] args) {
    }
}