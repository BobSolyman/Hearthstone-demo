package exceptions;

public abstract class HearthstoneException extends Exception{
	
public HearthstoneException (){
	super ("Invalid action was performed.");
}
 public HearthstoneException(String s){
	 super (s);
 }

}
