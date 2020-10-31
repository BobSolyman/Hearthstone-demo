package exceptions;

public class TauntBypassException extends HearthstoneException {
	public TauntBypassException(){
		super ("The opponent has a taunt minion in the field.");
	}
	public TauntBypassException(String s){
		super (s);
		
	}

}
