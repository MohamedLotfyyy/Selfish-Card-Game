package selfish;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import selfish.deck.Card;
import selfish.deck.Oxygen;

/**
 * Astronaut class represents an astronaut in the game
 * @author Mohamed Lotfy
 * @version 1
 */
public class Astronaut implements Serializable{

    /**
     * the game that the astronaut is playing.
     */
    private GameEngine game;

    /**
     * name of the astronaut.
     */
    private String name;

    /**
     * list of the oxygen cards in the astronaut's hand
     */
    private List<Oxygen> oxygens;

    /**
     * list of the action cards in the astronaut's hand
     */
    private List<Card> actions;

    /**
     * collection of cards in the astronaut's track
     */
    private Collection<Card> track;

    /**
     * serial version uid
     */
    private static final long serialVersionUID = 1L;
    

    /**
     * Astronaut constructor with a name to define the astronaut and a game to define the game he's playing in
     * 
     * @param name name of the astronaut
     * @param game game that the astronaut is playing
     */
    public Astronaut(String name, GameEngine game){
        this.name = name;
        this.game = game;
        this.actions = new ArrayList<Card>();;
        this.oxygens = new ArrayList<Oxygen>();
        this.track = new ArrayList<Card>();
    }

    /**
     * returns string representation of astronaut depending if he's alive or dead
     * 
     * @return string representation of astronaut
     */
    public String toString(){
        if (!isAlive()) {
            return name +  " (is dead)";
        } else {
            return name;
        }
    }

    /**
     * method that adds a card to astronaut's track
     * @param card card to add to the track
     */
    public void addToTrack(Card card){
        this.track.add(card);
    }

    /**
     * method that adds oxygen card to astronaut's hand.
     * @param card the oxygen card to add to the hand
     */
    public void addToHand(Card card){
        if (card instanceof Oxygen) {
            oxygens.add((Oxygen) card);
        } else {
            actions.add(card);
        }
    } 

    /**
     * Returns the action cards in the astronaut's hand.
     * @return the action cards in the astronaut's hand
     */
    public List<Card> getActions(){
        Collections.sort(this.actions);
        return this.actions;
    }

    /**
     * returns the hand of the astronaut
     * 
     * @return the hand of the astronaut
     */
    public List<Card> getHand(){
        List<Card> hand = new ArrayList<Card>();
        List<Oxygen> sorted_o = this.oxygens;
        Collections.sort(sorted_o);
        for(Oxygen item: this.oxygens){
            hand.add((Card)item);
        }
        List<Card> h = getActions();
        for (Card c: h){
            hand.add(c);
        }
        Collections.sort(hand);
        return hand;
    }

    /**
     * check if astronaut is alive or dead.
     * 
     * @return false if astronaut is dead and true if he's alive
     */
    public boolean isAlive() {
        if(this.oxygens.isEmpty()){
            return false;
        }
        return true;
    }

    /**
     * returns distance of the astronaut to the ship.
     * 
     * @return distance of the astronaut to the ship
     */
    public int distanceFromShip(){
        if(getTrack().isEmpty()){
            return 6;
        }
        else if(getTrack().size() == 6){
            return 0;
        }
        else{
            return getTrack().size();
        }
    }

    /**
     * returns a string representation of the action cards
     * @param enumerated boolean value to specify if we should enumerate or not
     * @param excludeShields boolean value to specify if we should exclude shield card or not
     * @return string representation of the action cards
     */
    public String getActionsStr(boolean enumerated, boolean excludeShields){
        StringBuilder sb = new StringBuilder();
        List<Card> actions = getActions();
        if (enumerated && !excludeShields) {
            int index = 0;
            Set<String> cardNames = new HashSet<>();
            for (Card c : actions) {
                if (!(c instanceof Oxygen)) {
                    String cardName = c.getName();
                    if (!cardNames.contains(cardName)) {
                        if (sb.length() > 0) sb.append(", ");
                        sb.append(String.format("[%s] %s", (char)('A' + index), cardName));
                        index++;
                    }
                    cardNames.add(cardName);
                }
            }
        }
       else if (enumerated && excludeShields) {
            int index = 0;
            Set<String> cardNames2 = new HashSet<>();
            for (Card c : actions) {
                if (!(c instanceof Oxygen) && !c.getName().equals("Shield")) {
                    String cardName = c.getName();
                    if (!cardNames2.contains(cardName)) {
                        if (sb.length() > 0) sb.append(", ");
                        sb.append(String.format("[%s] %s", (char)('A' + index), cardName));
                        index++;
                    }
                    cardNames2.add(cardName);
                }
            }
        }
        else if(excludeShields && !enumerated){
            Map<String, Integer> count = new HashMap<>();
            for (Card element : actions) {
                if (count.containsKey(element.getName())) {
                    count.put(element.getName(), count.get(element.getName()) + 1);
                } else {
                    count.put(element.getName(), 1);
                }
            }

            if (count.size() == actions.size()) {
                for (String element : count.keySet()) {
                    if(!element.equals("Shield")){
                        sb.append(element);
                        sb.append(", ");
                    }
                }
                if (sb.length() > 0) {
                    sb.setLength(sb.length() - 2);
                }
        } else{
            for (Map.Entry<String, Integer> entry : count.entrySet()) {
                String element = entry.getKey();
                int count3 = entry.getValue();
                if(!element.equals("Shield")){
                    sb.append(count3);
                    sb.append("x ");
                    sb.append(element);
                    sb.append(", ");
                }
            }
            if (sb.length() > 0) {
                sb.setLength(sb.length() - 2);
            }
        }
    }
        else {
            Map<String, Integer> counts = new HashMap<>();
            for (Card element : actions) {
                if (counts.containsKey(element.getName())) {
                    counts.put(element.getName(), counts.get(element.getName()) + 1);
                } else {
                    counts.put(element.getName(), 1);
                }
            }
        
            if (counts.size() == actions.size()) {
                for (String element : counts.keySet()) {
                    sb.append(element);
                    sb.append(", ");
                }
                if (sb.length() > 0) {
                    sb.setLength(sb.length() - 2);
                }
            } else {
                for (Map.Entry<String, Integer> entry : counts.entrySet()) {
                    String element = entry.getKey();
                    int count2 = entry.getValue();
                    sb.append(count2);
                    sb.append("x ");
                    sb.append(element);
                    sb.append(", ");
                }
                if (sb.length() > 0) {
                    sb.setLength(sb.length() - 2);
                }
            }
        }
    return sb.toString();
    }

    /**
 * returns string representation of astronaut's hand
 * 
 * @return string representation of astronaut's hand
 */
public String getHandStr(){
    StringBuilder sb = new StringBuilder();
    List<Card> hand = getHand();
    Map<String, Integer> count = new HashMap<>();
    for (Card element : hand) {
        if (count.containsKey(element.toString())) {
            count.put(element.toString(), count.get(element.toString()) + 1);
        } else {
            count.put(element.toString(), 1);
        }
    } 

    Map<String, Integer> sortedMap = count.entrySet().stream()
        .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                (oldValue, newValue) -> oldValue, LinkedHashMap::new));

    boolean onlyOxygen = true;
    for(Card element: hand){
        if  (!(element instanceof Oxygen)){
            onlyOxygen = false;
        }
    }
                
    if (count.size() == hand.size()) {
        List<Oxygen> o2 = this.oxygens;
        Collections.sort(o2);
        Collections.reverse(o2);
        if(onlyOxygen){
            for(Oxygen element: o2){
                sb.append(element);
                sb.append(", ");
            }
            if (sb.length() > 0) {
                sb.setLength(sb.length() - 2);
            }
            return sb.toString();
        }
        for (Card card : hand) {
            sb.append(card).append("; ");
        }
        if (sb.length() > 6) {
            sb.delete(sb.length() - 2, sb.length());
        }
    }
    else{
        int counts = 0;
        for (Map.Entry<String, Integer> entry : sortedMap.entrySet()) {

            String element = entry.getKey();
            int count2 = entry.getValue();
            if (count2 > 1){
                sb.append(count2);
                sb.append("x ");
            }
            sb.append(element);
            counts++;
            if(counts >= 2){
                sb.append("; ");
            }
            else{
                sb.append(", ");
            }
        }
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 2);
        }
    }
        System.out.println(sb.toString());
        return sb.toString();
}

/**
 * returns the track of an astronaut
 * 
 * @return track of an astronau
 */
public Collection<Card> getTrack(){
    return track;
}

/**
 * sets track of an astronaut
 * 
 * @param track collection representing track of an astronaut
 */
public void setTrack(Collection<Card> track) {
    this.track.clear(); 
    this.track.addAll(track); 
}

/**
 * Astronaut breathe
 * 
 * @return oxygen remaining after breathe
 */
public int breathe(){
    if(!isAlive()){
        throw new IllegalStateException("Invalid Oxygen value passed. Expected value: 2.");
    }

    Oxygen oxygen = oxygens.remove(0);
    int value = oxygen.getValue();
    if (value > 1) {
        oxygens.add(new Oxygen(1));
    }
    int count = 0;
    for(Oxygen element: oxygens){
        if (element.getValue() == 2){
            count++;
            count++;
        }
        else{
            count++;
        }
    }
    if (count == 0) {
        this.game.killPlayer(this);
    }
    return count;
}

/**
 * hack card for Card param
 * @param card card to be hacked
 */
public void hack(Card card){
    List<Card> hand = getHand();
    Card cardToRemove = card;

    if(!hand.contains(cardToRemove)){
       throw new IllegalArgumentException("card not in hand");
    }
    if (cardToRemove != null){
        hand.remove(card);
    }
    else{
        throw new IllegalArgumentException("card not found");
    }
    if(hand.isEmpty()){
        this.game.killPlayer(this);
    }
    if (cardToRemove instanceof Oxygen){
        this.oxygens.remove(card);
        if(this.oxygens.isEmpty()){
            this.actions.clear();
            hand.clear();
            this.game.killPlayer(this);
        }
    }
    else{
        this.actions.remove(card);
    }
}

/**
 * hack card for string param
 * @param card name of card to be hacked
 * @return the hacked card
 */
public Card hack(String card){
    List<Card> hand = getHand();
    List<Card> actions = getActions();
    List<Oxygen> oxygens = this.oxygens;
    Card cardToRemove = null;

    for (Card cards : hand) {
        if (cards.toString().equals(card)) {
            cardToRemove = cards;
            break;
        }
    }
    
    if (cardToRemove != null) {
        hand.remove(cardToRemove);
    }
    else{
        throw new IllegalArgumentException("string not found");
    }
    if(cardToRemove instanceof Oxygen){
        oxygens.remove(cardToRemove);
        if (oxygens.isEmpty()){
            actions.clear();
            hand.clear();
            this.game.killPlayer(this);
        }
    }
    else{
        actions.remove(cardToRemove);
    }
    return cardToRemove;
}

/**
 * returns number of card in the hand for a specific card defined by name
 * @param card card name that we want to search for how many we have
 * @return int the count number of the card
 */
public int hasCard(String card){
    List<Card> hand = getHand();
    int count = 0;
    for (Card c : hand) {
        if (c.toString().equals(card)) {
            count++;
        }
    }
    return count;
}

/**
 * astronaut eyes melted or not
 * 
 * @return true if astronaut's eyes have melted, false if not
 */
public boolean hasMeltedEyeballs(){
    if (((List<Card>) this.getTrack()).get(getTrack().size() - 1).toString() == "Solar flare"){
        return true;
    }
    return false;
}

/**
 * returns if astronaut has won the game or not
 * 
 * @return true if the astronaut has won the game, false if not
 */
public boolean hasWon(){
    if (this.getTrack().size() == 6 && this.oxygens.size() >= 1){
        return true;
    }
    return false;
}

/**
 * laser blast
 * 
 * @return the card to remove
 */
public Card laserBlast(){
    if(this.track.size() == 0){
        throw new IllegalArgumentException("Astronaut in starting position");
    }
    List<Card> track = (List<Card>) getTrack();
    Card removeElement = track.remove(track.size() - 1);
    return removeElement;
}

/**
 * ammount of oxygen remaining 
 * 
 * @return ammout of oxygen remaining
 */
public int oxygenRemaining(){
    List<Oxygen> o = this.oxygens;
    int count = 0;
    for(Oxygen oxygen : o){
        if (oxygen.getValue() == 2){
            count += 2;
        }
        else{
            count += 1;
        }
    }
    return count;
}

/**
 * last card in track
 * 
 * @return last card in track
 */
public Card peekAtTrack(){
    if (getTrack().size() == 0){
        return null;
    }
    return ((List<Card>) getTrack()).get(getTrack().size() - 1);
}

/**
 * siphon
 * 
 * @return an oxygen card
 */
public Oxygen siphon() {
    List<Card> hand = getHand();
    Oxygen oxygenToRemove = null;
    for (Card card : hand) {
        if (card instanceof Oxygen) {
            Oxygen oxygen = (Oxygen) card;
            if (oxygen.getValue() == 1) {
                oxygenToRemove = oxygen;
                hand.remove(oxygenToRemove);
                this.oxygens.remove(oxygenToRemove);
                if(hand.isEmpty()){
                    this.game.killPlayer(this);
                }
                return oxygenToRemove;
            }
        }
    }

    if (oxygenToRemove == null) {
        for (Card card : hand) {
            if (card instanceof Oxygen) {
                Oxygen oxygen = (Oxygen) card;
                if (oxygen.getValue() == 2) {
                    hand.remove(oxygen);
                    this.oxygens.remove(oxygen);
                    hand.add(new Oxygen(1));
                    this.oxygens.add(new Oxygen(1));
                    if(hand.isEmpty()){
                        this.game.killPlayer(this);
                    }
                    return new Oxygen(1);
                }
            }
        }
    } 
    if(hand.isEmpty()){
        this.game.killPlayer(this);
    }
    return null;
}



/**
 * steal card from track
 * 
 * @return the stolen card
 */
public Card steal() {
    List<Card> hand = getHand();
    if (hand == null || hand.isEmpty()) {
        return null;
    }
    int randomIndex = new Random().nextInt(hand.size());
    Card removedCard = hand.remove(randomIndex);
    if(hand.isEmpty()){
        this.game.killPlayer(this);
    }
    if (removedCard instanceof Oxygen) {
        this.oxygens.remove(removedCard);
    } else {
        getActions().remove(removedCard);
    }
    return removedCard;
}


/**
 * swap astronaut track with another astronaut's track
 * 
 * @param swapee the astronaut to swap tracks with
 */
public void swapTrack(Astronaut swapee){
    List<Card> tempTrack = new ArrayList<>(this.getTrack());
    this.setTrack(new ArrayList<>(swapee.getTrack()));
    swapee.setTrack(tempTrack);
    }

}
