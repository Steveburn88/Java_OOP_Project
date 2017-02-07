package four_wins;

import java.io.Serializable;

/**
 * Created by stefan on 14.01.17.
 */
public class Player implements Serializable {
    String name;
    int coins;

    public Player(String name, int coins) {
        this.name = name;
        this.coins = coins;
    }

    public void setName(String name) {

        this.name = name;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public String getName() {

        return name;
    }

    public int getCoins() {
        return coins;
    }
}
