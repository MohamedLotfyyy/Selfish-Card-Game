package selfish.deck;

import java.io.Serializable;

/**
 * oxygen class represents an oxygen card
 * it inherits the Card class and also defines the oxygen by an additional para
 * the value of the oxygen
 *  @author Mohamed Lotfy
 * @version 1
 */
public class Oxygen extends Card implements Serializable, Comparable<Card>{

    /**
     * integer value of oxygen card.
     */
    private int value;
    
    /**
     * serial version uid
     */
    private static final long serialVersionUID = 1L;


    /**
     * Oxygen constructor that inherits the name and description from Card class and that has a value param
     *
     * @param value oxygen value of the card
     */
    public Oxygen(int value) {
        super("Oxygen", "Oxygen level of a player");
        this.value = value;
    }

    /**
     * returns oxygen value of the card.
     *
     * @return oxygen value of the card
     */
    public int getValue() {
        return value;
    }

    /**
     * returns string representation of the oxygen card.
     *
     * @return a string representation of the oxygen card
     */
    public String toString() {
        return "Oxygen(" + Integer.toString(this.value)+ ")";
    }

    /**
     * compares two cards with each other, if oxygen cards, them compare the value, else compare the name
     *@param o oxygen card
     * @return int value to specify wether the cards are equal or not
     */
    public int compareTo(Card o) {
        if(!(o instanceof Oxygen)){
            return this.toString().compareTo(o.getName());
        }
        else{
            Oxygen o2 = (Oxygen)o;
            return Integer.compare(this.value, o2.value);  
        }
    }
}

