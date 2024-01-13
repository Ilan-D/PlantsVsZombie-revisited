
package view;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class card {
    private int canbuyornot;// 0 oui 1 non
    private int cardnumber;
    private String cardname;

    public card(int number, String name) {
        this.cardname = name;
        this.cardnumber = number;
        canbuyornot = 0;
    }

    public void showinshop_canbuy(Graphics g) {
        Image canbuy_card = (new ImageIcon("src/img/card/" + cardname + "0.png")).getImage();
        // if (this.cardname.equals("peashooter")) {
        // System.out.println("test");
        // }
        g.drawImage(canbuy_card, 95 + 54 * cardnumber, 8, null);
    }

    // public void showinshop_canbuy(Graphics g) {
    // try {
    // Image canbuy_card = (new ImageIcon("ressources/card/" + cardname +
    // "0.png")).getImage();
    // if (canbuy_card != null) {
    // g.drawImage(canbuy_card, 300, 300, 100, 100, null);
    // System.out.println(canbuy_card);
    // } else {
    // System.out.println("Erreur lors du chargement de l'image.");
    // }
    // } catch (Exception e) {
    // System.out.println("Exception lors du chargement de l'image : " +
    // e.getMessage());
    // }
    // }

    public void showinshop_cannotbuy(Graphics g) {
        Image canbuy_card = (new ImageIcon("src/img/card/" + cardname + "1.png")).getImage();

        g.drawImage(canbuy_card, 95 + 54 * cardnumber, 8, null);
    }

    public boolean if_pressed(int mbx, int mby) {
        if (new Rectangle(95 + 54 * cardnumber, 8, 50, 70).contains(mbx, mby)) {
            return true;
        }
        return false;
    }

}
