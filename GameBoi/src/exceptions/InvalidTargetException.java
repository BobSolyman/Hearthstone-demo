package exceptions;

public class InvalidTargetException extends HearthstoneException{
	public InvalidTargetException()
	{
	super ("Invalid target.");
	}
public InvalidTargetException(String s){
	super(s);
	
}
}
