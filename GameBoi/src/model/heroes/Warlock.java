package model.heroes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import javafx.scene.image.Image;
import exceptions.*;
import model.cards.Card;
import model.cards.Rarity;
import model.cards.minions.Minion;
import model.cards.spells.CurseOfWeakness;
import model.cards.spells.SiphonSoul;
import model.cards.spells.Spell;
import model.cards.spells.TwistingNether;

public class Warlock extends Hero {

	public Warlock() throws IOException, CloneNotSupportedException {
		super("Gul'dan");
		Image i = new Image (getClass().getResourceAsStream("guldan.png"));		
		Image i2 = new Image(getClass().getResourceAsStream ("life_tap.png"));
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
		
		ArrayList<Minion> m = getNeutralMinions(getAllNeutralMinions("neutral_minions.csv"), 13);
		ArrayList<Card> d = new ArrayList<Card>();
		
		d.addAll(m);
		
		Spell s1 = new CurseOfWeakness();
		Spell s2 = new CurseOfWeakness();
		Spell s3 = new SiphonSoul();
		Spell s4 = new SiphonSoul();
		Spell s5 = new TwistingNether();
		Spell s6 = new TwistingNether();
		d.add(s1);
		d.add(s2);
		d.add(s3);
		d.add(s4);
		d.add(s5);
		d.add(s6);
		
		Minion x = new Minion ("Wilfred Fizzlebang",6,Rarity.LEGENDARY,4,4,false,false,false);
		d.add(x);

		Collections.shuffle(d);
		getDeck().addAll(d);
	}
	public void useHeroPower() throws NotEnoughManaException,HeroPowerAlreadyUsedException, NotYourTurnException, FullHandException,FullFieldException, CloneNotSupportedException {
		super.useHeroPower();
		this.setCurrentHP(this.getCurrentHP() - 2);
		int s = this.getHand().size();
		Card x =super.drawCard();
		boolean foundWilfred_Fizzlebang = false ;
		for(int i=0; i<this.getField().size();i++)
			if(this.getField().get(i).getName().equals("Wilfred Fizzlebang"))
				foundWilfred_Fizzlebang = true;	
		if(x instanceof Minion && foundWilfred_Fizzlebang && s+1== this.getHand().size())
			this.getHand().get(getHand().size()-1).setManaCost(0);	
		if(x instanceof Minion && foundWilfred_Fizzlebang && s+2== this.getHand().size()){
			this.getHand().get(getHand().size()-1).setManaCost(0);
			this.getHand().get(getHand().size()-2).setManaCost(0);
			}
	}

	

	
	

	


}
