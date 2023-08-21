package selfish.deck;
import java.util.ArrayList;
import java.util.List;

import selfish.GameException;
/**
 * The GameDeck class extends the Deck class and contains the list of cards specific to the game.
 * @author Mohamed Lotfy
 * @version 1
 */
public class SpaceDeck extends Deck {
    /**
    string initialising the card ASTEROID_FIELD
    */
    public static  final String ASTEROID_FIELD = "Asteroid field";
    /**
    string initialising the card BLANK_SPACE
    */
    public static  final String BLANK_SPACE = "Blank space";
    /**
    string initialising the card COSMIC_RADIATION
    */
    public static  final String COSMIC_RADIATION = "Cosmic radiation";
    /**
    string initialising the card GRAVITATIONAL_ANOMALY
    */
    public static  final String GRAVITATIONAL_ANOMALY = "Gravitational anomaly";
    /**
    string initialising the card HYPERSPACE
    */
    public static  final String HYPERSPACE = "Hyperspace";
    /**
    string initialising the card METEOROID
    */
    public static  final String METEOROID = "Meteoroid";
    /**
    string initialising the card MYSTERIOUS_NEBULA
    */
    public static final  String MYSTERIOUS_NEBULA = "Mysterious nebula";
    /**
    string initialising the card SOLAR_FLARE
    */
    public static  final String SOLAR_FLARE = "Solar flare";
    /**
    string initialising the card USEFUL_JUNK
    */
    public static  final String USEFUL_JUNK = "Useful junk";
    /**
    string initialising the card WORMHOLE
    */
    public static  final String WORMHOLE = "Wormhole";

     /**
    serial version uid
    */
    private static final long serialVersionUID = 1L;

    /**
    list of cards
    */
    private List<Card> cards;

    /**
    empty SpaceDeck constructor
    */
    public SpaceDeck(){
        super();
    }
    /**
    Load card from file into SpaceDeck.
    @param filename file that has the cards
     * @throws GameException if
    */
    public SpaceDeck(String filename) throws GameException{
        super();
        this.cards = new ArrayList<Card>();
        List<Card> actionCards;
        actionCards = loadCards(filename);
        super.add(actionCards); 
    }
}
