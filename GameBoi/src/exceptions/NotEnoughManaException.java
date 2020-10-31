package exceptions;

public class NotEnoughManaException  extends HearthstoneException{
	public NotEnoughManaException(){
		super("You don't have enough Mana-crystals.");
	}
	public NotEnoughManaException(String s){
		super (s);
	}

}
