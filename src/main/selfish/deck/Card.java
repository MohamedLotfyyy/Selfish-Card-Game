package selfish.deck;

import java.io.Serializable;
import java.lang.Comparable;

/**
 * Card class representing a card in the game
 * defined by nam eand description
 * @author Mohamed Lotfy
 * @version 1
 */
public class Card  implements Serializable, Comparable<Card>{

    /**
     * name of card
     */
    private String name;

    /**
     * description of card
     */
    private String description;
    /**
     * serial version uid
     */
    private static final long serialVersionUID = 1L;

    /**
     * Card constructor with a name and description to define the card
     *
     * @param name name of card
     * @param description description of card
     */
    public Card(String name, String description) {
        this.name = name;
        this.description = description;
    }

    /**
     * returns description of the card.
     *
     * @return description of the card
     */
    public String getDescription() {
        return this.description;
    }
    /**
     * returns name of the card.
     *
     * @return name of the card
     */
    public String getName(){
        return this.name;
    }

    /**
     * returns string representation of the card.
     *
     * @return string representation of the card
     */
    public String toString() {
        return this.name;
    }

    /**
     * compare two cards to each other
     * @param c the card to compare
     * @return int stating that the cards are equal or not
     */
    public int compareTo(Card c){
        return this.toString().compareTo(c.name);
    }
}
