package model.heroes;
import java.util.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;



import javafx.scene.image.Image;
import exceptions.*;
import model.cards.Card;
import model.cards.Rarity;
import model.cards.minions.Minion;
import model.cards.spells.*;

public class Hunter extends Hero {

	public Hunter() throws IOException, CloneNotSupportedException {
		super("Rexxar");
		Image i = new Image (getClass().getResourceAsStream("rexxar.png"));
		Image i2 = new Image (getClass().getResourceAsStream("steady_shot.png"));
		this.gethImage().setImage(i);
		this.getpImage().setImage(i2);
		
		this.gethImage().setFitHeight(115);
		this.gethImage().setFitWidth(96);
		this.gethImage().setLayoutX(3);
		this.gethImage().setPickOnBounds(true);
		
		this.getpImage().setFitHeight(100);
		this.getpImage().setFitWidth(110);
		this.getpImage().setPreserveRatio(true);
		this.getpImage().setPickOnBounds(true);
      
	}

	@Override
	public void buildDeck() throws IOException, CloneNotSupportedException {
		ArrayList<Minion> m = getNeutralMinions(getAllNeutralMinions("neutral_minions.csv"), 15);
		ArrayList<Card> d = new ArrayList<Card>();
		
		d.addAll(m);
		
		Spell s1 = new KillCommand();
		Spell s2 = new KillCommand();
		Spell s3 = new MultiShot();
		Spell s4 = new MultiShot();
		d.add(s1);
		d.add(s2);
		d.add(s3);
		d.add(s4);
		
		Minion x = new Minion ("King Krush",9,Rarity.LEGENDARY,8,8,false,false,true);
		d.add(x);

		Collections.shuffle(d);
		getDeck().addAll(d);
		
		}

		public void useHeroPower() throws NotEnoughManaException,HeroPowerAlreadyUsedException, NotYourTurnException, FullHandException,FullFieldException, CloneNotSupportedException {
			super.useHeroPower();
			this.getListener().damageOpponent(2);
		}



 

}
