package selfish.deck;
import java.util.List;
import selfish.GameException;
/**

The GameDeck class extends the Deck class and contains the list of cards specific to the game.
@author Mohamed Lotfy
@version 1
*/
public class GameDeck extends Deck{
    /**
    string
    */
    public static final String HACK_SUIT = "Hack suit";
    /**
    string initialising the HOLE_IN_SUIT card
    */
    public static final String HOLE_IN_SUIT = "Hole in suit";
    /**
    string initialising the HOLE_IN_SUIT card
    */
    public static final String LASER_BLAST = "Laser blast";
    /**
    string initialising the HOLE_IN_SUIT card
    */
    public static final String OXYGEN = "Oxygen";
    /**
    string initialising the HOLE_IN_SUIT card
    */
    public static final String OXYGEN_1 = "Oxygen(1)";
    /**
    string initialising the HOLE_IN_SUIT card
    */
    public static final String OXYGEN_2 = "Oxygen(2)";
    /**
    string initialising the HOLE_IN_SUIT card
    */
    public static final String OXYGEN_SIPHON = "Oxygen siphon";
    /**
    string initialising the HOLE_IN_SUIT card
    */
    public static final String ROCKET_BOOSTER = "Rocket booster";
    /**
    string initialising the HOLE_IN_SUIT card
    */
    public static final String SHIELD = "Shield";
    /**
    string initialising the HOLE_IN_SUIT card
    */
    public static final String TETHER = "Tether";
    /**
    string initialising the HOLE_IN_SUIT card
    */
    public static final String TRACTOR_BEAM = "Tractor beam";

    /**
    serial version uid
    */
    private static final long serialVersionUID = 1L;


    /**
     * empty deck constructor
     */
    public GameDeck(){
        super();
    }
    
    /**
     * Deck constructor with a filename param
     *
     * @param filename the name of the file to load cards from
     * @throws GameException if
     */
    public GameDeck(String filename) throws GameException{
        super();


        List<Card> actionCards;
        actionCards = loadCards(filename);
        super.add(actionCards);

        for (int i = 0; i < 10; i++) {
            super.add(new Oxygen(2));
        }

        for (int i = 0; i < 38; i++) {
            super.add(new Oxygen(1));
        }
    }
    
    /**
     * draw oxygen card with a specified value from the deck 
     *
     * @param value value of oxygen card
     * @return the card that we drawed from the deck
     */
    public Oxygen drawOxygen(int value){
        if(this.getCards().isEmpty()){
            throw new IllegalStateException("deck empty");
        }
        for (int i = this.getCards().size() - 1; i >= 0; i--){
            Card card = this.getCards().get(i);
            if (card instanceof Oxygen && ((Oxygen) card).getValue() == value) {
            this.getCards().remove(card);
            return (Oxygen) card;
            }
        }
            throw new IllegalStateException("no oxygen found");
        }
    
    /**
     * Splits an oxygen with value 2 into two oxygen with value one
     *
     * @param dbl the oxygen(2) we want to split
     * @return arrau containing the two oxygen that we got after splitting the dbl oxygen(2)
     */
    public Oxygen[] splitOxygen(Oxygen dbl) {
        if (!dbl.toString().equals(OXYGEN_2)){
            throw new IllegalArgumentException("Invalid Oxygen value passed. Expected value: 2.");
        }
        
        this.getCards().add(dbl);
        Oxygen[] divided = new Oxygen[2];
        int count = 0;
        for (int i = 0; i < this.getCards().size() && count < 2; i++) {
            Card card = this.getCards().get(i);
            if (card instanceof Oxygen && ((Oxygen) card).getValue() == 1) {
                divided[count] = (Oxygen) card;
                count++;
            }
        }
        if(count != 2){
            throw new IllegalStateException("error");
        }
        for(Card element: divided){
            this.getCards().remove(element);
        }
        return divided;
    }
    


}