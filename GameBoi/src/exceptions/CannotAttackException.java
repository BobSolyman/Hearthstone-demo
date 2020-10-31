package exceptions;

public class CannotAttackException extends HearthstoneException{
	public CannotAttackException(){
		super ("Cannot attack.");
	}
	public CannotAttackException(String s){
		super (s);
	}
	

}
