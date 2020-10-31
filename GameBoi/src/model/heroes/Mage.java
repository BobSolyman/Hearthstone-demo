package model.heroes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import exceptions.*;
import model.cards.Card;
import model.cards.Rarity;
import model.cards.minions.Minion;
import model.cards.spells.*;

public class Mage extends Hero {

	public Mage() throws IOException, CloneNotSupportedException {
		super("Jaina Proudmoore");
		Image i = new Image (getClass().getResourceAsStream("jaina.png"));
		Image i2 = new Image(getClass().getResourceAsStream("fireblast.png"));
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
		
		Spell s1 = new Polymorph();
		Spell s2 = new Polymorph();
		Spell s3 = new Flamestrike();
		Spell s4 = new Flamestrike();
		Spell s5 = new Pyroblast();
		Spell s6 = new Pyroblast();
		d.add(s1);
		d.add(s2);
		d.add(s3);
		d.add(s4);
		d.add(s5);
		d.add(s6);
		
		Minion x = new Minion ("Kalycgos",10,Rarity.LEGENDARY,4,12,false,false,false);
		d.add(x);
		
		Collections.shuffle(d);
		getDeck().addAll(d);
	}
	
	public void useHeroPower(Hero target) throws NotEnoughManaException,HeroPowerAlreadyUsedException, NotYourTurnException, FullHandException,FullFieldException, CloneNotSupportedException {
		super.useHeroPower();
		target.setCurrentHP(target.getCurrentHP() - 1);
	}
	public void useHeroPower(Minion target) throws NotEnoughManaException,HeroPowerAlreadyUsedException, NotYourTurnException, FullHandException,FullFieldException, CloneNotSupportedException {
		super.useHeroPower();
		if(target.isDivine())
			target.setDivine(false);
		else
			target.setCurrentHP(target.getCurrentHP() - 1);
	}
	@Override
	public void castSpell(FieldSpell s) throws NotYourTurnException, NotEnoughManaException{
		boolean flag = false;
		for(int i=0; i<this.getField().size();i++){
			if(this.getField().get(i).getName().equals("Kalycgos")){
				flag = true;
				break;
			}
		}
		if(flag) {
			Card c = (Card)s;
			c.setManaCost(c.getManaCost() - 4);
			super.castSpell((FieldSpell) c);
		}
		else
			super.castSpell(s);
	}
	@Override
	public void castSpell(AOESpell s, ArrayList<Minion >oppField) throws NotYourTurnException, NotEnoughManaException{
		boolean flag = false;
		for(int i=0; i<this.getField().size();i++){
			if(this.getField().get(i).getName().equals("Kalycgos")){
				flag = true;
				break;
			}
		}
		if(flag) {
			Card c = (Card)s;
			c.setManaCost(c.getManaCost() - 4);
			super.castSpell((AOESpell) c, oppField);
		}
		else
			super.castSpell(s, oppField);
	}
	@Override
	public void castSpell(MinionTargetSpell s, Minion m) throws NotYourTurnException, NotEnoughManaException, InvalidTargetException{
		boolean flag = false;
		for(int i=0; i<this.getField().size();i++){
			if(this.getField().get(i).getName().equals("Kalycgos")){
				flag = true;
				break;
			}
		}
		if(flag) {
			Card c = (Card)s;
			c.setManaCost(c.getManaCost() - 4);
			super.castSpell((MinionTargetSpell) c,m);
		}
		else
			super.castSpell(s, m);
	}
	@Override
	public void castSpell(HeroTargetSpell s, Hero h) throws NotYourTurnException, NotEnoughManaException{
		boolean flag = false;
		for(int i=0; i<this.getField().size();i++){
			if(this.getField().get(i).getName().equals("Kalycgos")){
				flag = true;
				break;
			}
		}
		if(flag) {
			Card c = (Card)s;
			c.setManaCost(c.getManaCost() - 4);
			super.castSpell((HeroTargetSpell) c, h);
		}
		else
			super.castSpell(s, h);
	}
	@Override
	public void castSpell(LeechingSpell s, Minion m) throws NotYourTurnException, NotEnoughManaException{
		boolean flag = false;
		for(int i=0; i<this.getField().size();i++){
			if(this.getField().get(i).getName().equals("Kalycgos")){
				flag = true;
				break;
			}
		}
		if(flag) {
			Card c = (Card)s;
			c.setManaCost(c.getManaCost() - 4);
			super.castSpell((LeechingSpell) c, m);
		}
		else
			super.castSpell(s, m);
	}

}
