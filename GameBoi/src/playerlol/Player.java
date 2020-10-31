package playerlol;

import java.io.IOException;



import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.heroes.*;
import javafx.beans.property.*;

public class Player {
	private	Hero H ;

		
	
	public Player (String s ) throws IOException, CloneNotSupportedException{
		if (s.equals("mage")){
			H = new Mage();

			
		}
		
		else if (s.equals("warlock")){
			H = new Warlock();

			
		}else if (s.equals("priest")){
			H = new Priest ();

			
		}else if (s.equals("hunter")){
			H = new Hunter();

			
		}else if (s.equals("paladin")){
			H = new Paladin() ;

		}
		 

		
		
	}

	public Hero getH() {
		return H;
	}


	
	public static void main(String[] args) throws IOException, CloneNotSupportedException {
		new Player ("mage");
	}
	
	
	
	
	
	
	
	
	
	
	
}
