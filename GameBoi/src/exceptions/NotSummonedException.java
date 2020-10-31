package exceptions;

public class NotSummonedException  extends HearthstoneException {
	public NotSummonedException(){
		super("This minion is not yet summoned to the field.");
	
	}
	public NotSummonedException(String s){
		super (s);
	}

}
