package exceptions;

public class HeroPowerAlreadyUsedException extends HearthstoneException {
 public HeroPowerAlreadyUsedException(){
	super("You already used the hero power.");
 }
 public HeroPowerAlreadyUsedException(String s){
	 super (s);
 }
}
