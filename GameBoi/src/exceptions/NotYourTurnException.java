package exceptions;

public class NotYourTurnException extends HearthstoneException{
	public NotYourTurnException(){
		super ("Not your turn yet.");
	}
	public NotYourTurnException(String s){
		super (s);
	}

}
