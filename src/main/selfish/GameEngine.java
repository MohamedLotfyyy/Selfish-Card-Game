package selfish;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import selfish.deck.GameDeck;
import selfish.deck.Oxygen;
import selfish.deck.SpaceDeck;
import selfish.deck.Card;
import selfish.deck.Deck;

/**

GameEngine class represents a game of the selfish card game
* @author Mohamed Lotfy
* @version 1
*/
public class GameEngine implements Serializable{
    private Random random;
    private boolean hasStarted;
    private GameDeck gameDeck;
    private GameDeck gameDiscard;
    private SpaceDeck spaceDeck;
    private SpaceDeck spaceDiscard;
    private Collection<Astronaut> activePlayers;
    private List<Astronaut> corpses;
    private Astronaut currentPlayer;
    private static final long serialVersionUID = 1L;
    /**
    private Game Engine constructor  
    */
    private GameEngine(){
    
    }
    /**
    GameEngine public constuctor
    @param seed seed to use for the random object
    @param gameDeckPath path to the game deck file
    @param spaceDeckPath path to the space deck file
    @throws GameException if
    */
    public GameEngine(long seed, String gameDeckPath, String spaceDeckPath)throws GameException{
        random = new Random(seed);
        gameDeck = new GameDeck(gameDeckPath);
        
        gameDeck.shuffle(random);
        spaceDeck = new SpaceDeck(spaceDeckPath);
        
        spaceDeck.shuffle(random);
        gameDiscard = new GameDeck();
        spaceDiscard = new SpaceDeck();
        activePlayers = new ArrayList<Astronaut>();
        corpses = new ArrayList<Astronaut>();
    }
    /**
    returns random object
    @return random object
    */
    public Random getRandom() {
    return random;
    }
    /**
    returns the GameDeck 
    @return the GameDeck 
    */
    public GameDeck getGameDeck() {
    return gameDeck;
    }
    /**
    returns the SpaceDeck 
    @return the SpaceDeck
    */
    public SpaceDeck getSpaceDeck() {
    return spaceDeck;
    }
    /**
    return GameDiscard
    @return GameDiscard 
    */
    public GameDeck getGameDiscard() {
    return gameDiscard;
    }
    /**
    return SpaceDiscard
    @return SpaceDiscard 
    */
    public SpaceDeck getSpaceDiscard() {
    return spaceDiscard;
    }
    /**
    add new player to game
    @param player name of player
    @return number of players
    */
    public int addPlayer(String player){
        if (hasStarted) {
            throw new IllegalStateException("Cannot add player to a game that has already started.");
        }
        Astronaut newAstronaut = new Astronaut(player, this);
        if (activePlayers.size() < 5){
            activePlayers.add(newAstronaut);
        }
        else{
            throw new IllegalStateException("Cannot add player to a game that has already started.");
        }
        return activePlayers.size();
    }

    /**
 * end turn
 * @return number of players still alive in the game after the turn has ended
 */
public int endTurn() {
    if (currentPlayer.isAlive()){
        this.activePlayers.add(currentPlayer);
    }
    else{
        this.corpses.add(currentPlayer);
    }
    currentPlayer = null;
    int numPlayersAlive = 0;
        for (Astronaut player : activePlayers) {
            if (player.isAlive()) {
                numPlayersAlive++;
            }
        }
        
        return numPlayersAlive;
}

/**
 * start game by drawing cards to players
 */
public void startGame(){
    if (hasStarted) {
        throw new IllegalStateException("The game has already started.");
    }
    if (activePlayers.size() > 5) {
        throw new IllegalStateException("The game cannot be started with more than 5 players.");
    }
    if (activePlayers.size() == 1) {
        throw new IllegalStateException("The game cannot be started with more than 5 players.");
    }
    for (Astronaut player : activePlayers){
            Oxygen oxygen = gameDeck.drawOxygen(2);
            player.addToHand(oxygen);
            Oxygen oxygen1 = gameDeck.drawOxygen(1);
            player.addToHand(oxygen1);
            Oxygen oxygen2 = gameDeck.drawOxygen(1);
            player.addToHand(oxygen2);
            Oxygen oxygen3 = gameDeck.drawOxygen(1);
            player.addToHand(oxygen3);
            Oxygen oxygen4 = gameDeck.drawOxygen(1);
            player.addToHand(oxygen4);
        }
 
    for (int i = 0; i < 4; i++) {
        for (Astronaut player : activePlayers) {
            player.addToHand(gameDeck.draw());
            }
        }
    for (Astronaut player : activePlayers) {
       List<Card> cards = player.getHand();
       Collections.sort(cards);     
    }
    hasStarted = true;
}

/**
 * merge two decks together
 *
 * @param deck1 first deck
 * @param deck2 second deck
 */
public void mergeDecks(Deck deck1, Deck deck2) {
    if (deck1 == null || deck2 == null) {
        throw new IllegalArgumentException("One or both decks is null");
    }
    while (deck2.size() != 0) {
        deck1.add(deck2.draw());
    }
    deck1.shuffle(this.random);
}

/**
 * split a double oxygen into two singles
 *
 * @param dbl double oxygen card to split
 * @return the two new oxygen one cards after the split in an array
 */
public Oxygen[] splitOxygen(Oxygen dbl){
    
    if (dbl.getValue() !=  2){
        throw new IllegalArgumentException("Invalid Oxygen value passed. Expected value: 2.");
    }
    if(gameDiscard.size() + gameDeck.size() < 2){
        throw new IllegalStateException("Invalid Oxygen value passed. Expected value: 2.");
    }
    if(gameDiscard.size() == 0 && gameDeck.size() == 1){
        throw new IllegalStateException("Invalid Oxygen value passed. Expected value: 2.");
    }
    if (gameDeck.size() == 0){
        mergeDecks(gameDeck, gameDiscard);
    }
    else if(gameDeck.size() == 1){
        mergeDecks(gameDeck, gameDiscard);
    }
    Oxygen[] divided = gameDeck.splitOxygen(dbl);
    return divided;
}

/**
 * get winner if present
 *
 * @return the astronaut who one or null if none 
 */
public Astronaut getWinner() {
    for(Astronaut player: activePlayers){
        Collection<Card> track = player.getTrack();
        if(track.size() >= 6){
            return player;
        }
    }
    return null;
}

/**
 * return current player that is taking the turn
 * @return current player that is taking the turn
 */
public Astronaut getCurrentPlayer() {
    return currentPlayer;
}

/**
 * returns all players in the game
 *
 * @return list of all the players in the game.
 */
public List<Astronaut> getAllPlayers() {
    List<Astronaut> unionList = new ArrayList<Astronaut>(activePlayers);
    List<Astronaut> corpses2 = new ArrayList<Astronaut>(corpses);
    for(Astronaut element: corpses2){
            unionList.add(element);
    }

    if(currentPlayer != null && currentPlayer.isAlive()){
        unionList.add(currentPlayer);
    }
    
    return unionList;
}

/**
 * start turn and initialising current player
 */
public void startTurn() {
    if(!hasStarted){
        throw new IllegalStateException("Invalid Oxygen value passed. Expected value: 2.");
    }
    if(currentPlayer!= null){
        throw new IllegalStateException("Invalid Oxygen value passed. Expected value: 2.");
    }
    if(getWinner() != null || this.activePlayers.size() == 0 && this.corpses.size() > 1){
        throw new IllegalStateException("Invalid Oxygen value passed. Expected value: 2.");
    }
    if(!(activePlayers.isEmpty()) && getCurrentPlayer() == null){
        currentPlayer = ((List<Astronaut>) activePlayers).get(0);
        activePlayers.remove(currentPlayer); 
    }
    
}

/**
 * save current state of game
 * @param path where to save the game
 */
public void saveState(String path) {
    try {
        FileOutputStream file = new FileOutputStream(path);

        ObjectOutputStream output = new ObjectOutputStream(file);

        output.writeObject(this);

        file.close();
        output.close();
    } catch(IOException e) {
        e.printStackTrace();
    }
}

/**
 * allows astronaut to travel
 * @param traveller astronaut who is travelling
 * @return drawn card when astronaut traveled
 */
public Card travel(Astronaut traveller) {
    // if(traveller.oxygenRemaining() <= 1){
    //     throw new IllegalStateException("exception");
    // }

    // traveller.breathe();
    // traveller.breathe();
    // Card card = spaceDeck.draw();
    // if(!card.toString().equals("Gravitational anomaly")){
    //     traveller.addToTrack(card);
    // }

    // return card;
    if(hasStarted == false){throw new IllegalStateException("cannot travel in a game that has not yet started");}
        else if (!traveller.toString().equals(this.currentPlayer.toString())){ throw new IllegalArgumentException("cannot travel when it is not your turn");}
        // conditions are right to travel now
        if(traveller.hasCard("Oxygen(2)")>0){
            this.gameDiscard.add(traveller.hack("Oxygen(2)"));
        }else if (traveller.hasCard("Oxygen(1)")>1){
            this.gameDiscard.add(traveller.hack("Oxygen(1)"));  
            this.gameDiscard.add(traveller.hack("Oxygen(1)"));
        }else{ throw new IllegalStateException("cannot travel with 1 or no oxygens");
        }
        if(this.spaceDeck.size()==0){ mergeDecks(this.spaceDeck, this.spaceDiscard);}
        Card c = this.spaceDeck.draw();
        if (c.toString().equals(this.spaceDeck.GRAVITATIONAL_ANOMALY)){
            
        }else{
            // for(int i=0; i<traveller.getTrack().size()-1; i++){
            //     // System.out.print("card "+Integer.toString(i)+" : "+c.toString()+ " | ");
            // }
            // // System.out.println();
            this.currentPlayer.addToTrack(c);
            // for(int i=0; i<traveller.getTrack().size()-1; i++){
            //     // System.out.print("card "+Integer.toString(i)+" : "+c.toString()+ " | ");
            // }
            // System.out.println();
            
        }


        return c;
}

/**
 * kill the player
 *
 * @param corpse astronaut who died
 */
public void killPlayer(Astronaut corpse) {
    activePlayers.remove(corpse);
    corpses.add(corpse);
}

/**
 * returns the full player count
 *
 * @return the full player count
 */
public int getFullPlayerCount() {
    List<String> list = new ArrayList<>();
    for(Astronaut name: getAllPlayers()){
        String cardName = name.toString();
        if(!(list.contains(cardName))){
            list.add(cardName);
        }
    }
    System.out.println("list: " + list);
    return list.size();

}

/**
 * returns if game over or not
 *
 * @return if game over or not
 */
public boolean gameOver() {
    if(getWinner() != null || this.activePlayers.size() == 0 && this.corpses.size() > 1){
        return true;
    }
    return false;
}

/**
 * load a previously saved game
 *
 * @param path path that has the game we want to load
 * @return new GameEngine representing the loaded game
 * @throws GameException if can't load game
 */
public static GameEngine loadState(String path) throws GameException{
    try {
        ObjectInputStream input = new ObjectInputStream(new FileInputStream(path));
        GameEngine game = (GameEngine) input.readObject();
        input.close();

        return game;
    } catch (IOException e) {
        throw new GameException("can't load game: ", e);
    } catch (ClassNotFoundException e) {
        throw new GameException("can't load game: ", e);
    }
}
}
