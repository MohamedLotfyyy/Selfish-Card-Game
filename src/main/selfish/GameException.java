package selfish;

/**
 * game exception class
 * @author Mohamed Lotfy
 * @version 1
 * 
 */
public class GameException extends Exception{
/*
game exception constructor
@param msg message
@param e cause of game exception
*/
public GameException(String msg, Throwable e){
    super(msg, e);
    }

}
