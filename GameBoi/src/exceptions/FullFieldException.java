package exceptions;

public class FullFieldException extends HearthstoneException {
public FullFieldException(){
   super ("You cannot add a minion card to a full field.");
}
public FullFieldException(String s){
	super (s);
}

}
