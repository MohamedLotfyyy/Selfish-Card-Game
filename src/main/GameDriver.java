import selfish.deck.SpaceDeck;

public class GameDriver {

    /**
     * A helper function to centre text in a longer String.
     * @param width The length of the return String.
     * @param s The text to centre.
     * @return A longer string with the specified text centred.
     */
    public static String centreString (int width, String s) {
        return String.format("%-" + width  + "s", String.format("%" + (s.length() + (width - s.length()) / 2) + "s", s));
    }

    public GameDriver() {
        
    }

    public static void main(String[] args) throws Exception  {
        // SpaceDeck spaceDeck = new SpaceDeck("/Users/mohamedlotfy/Documents/JavaSem2/selfishGame/comp16412-coursework-2__r64749ml/io/SpaceCards.txt");
        // GameDeck gameDeck = new GameDeck("/Users/mohamedlotfy/Documents/JavaSem2/selfishGame/comp16412-coursework-2__r64749ml/io/ActionCards.txt");
        // spaceDeck.getCards();
        // gameDeck.getCards();

        // Scanner scanner = new Scanner(System.in);

        // // Prompt the user to load the saved game state or start a new game
        // System.out.println("Would you like to (l)oad a saved game or (s)tart a new game?");
        // String choice = scanner.nextLine();
        // GameEngine game = new GameEngine(530886,  "io/ActionCards.txt", "io/SpaceCards.txt");
        // game.loadState(SAVE_FILE_PATH);
        // game.saveState(SAVE_FILE_PATH);
            

        // scanner.close();
    }
}