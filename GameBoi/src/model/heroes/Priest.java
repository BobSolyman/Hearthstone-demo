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
import model.cards.minions.MinionListener;
import model.cards.spells.DivineSpirit;
import model.cards.spells.HolyNova;
import model.cards.spells.ShadowWordDeath;
import model.cards.spells.Spell;

public class Priest extends Hero {

	public Priest() throws IOException, CloneNotSupportedException {
		super("Anduin Wrynn");
		Image i = new Image (getClass().getResourceAsStream("anduin.png"));
		Image i2 = new Image (getClass().getResourceAsStream("lesser_heal.png"));
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
		
		Spell s1 = new DivineSpirit();
		Spell s2 = new DivineSpirit();
		Spell s3 = new HolyNova();
		Spell s4 = new HolyNova();
		Spell s5 = new ShadowWordDeath();
		Spell s6 = new ShadowWordDeath();
		d.add(s1);
		d.add(s2);
		d.add(s3);
		d.add(s4);
		d.add(s5);
		d.add(s6);
		
		Minion x = new Minion ("Prophet Velen",7,Rarity.LEGENDARY,7,7,false,false,false);
		d.add(x);
	
		Collections.shuffle(d);
		getDeck().addAll(d);
	}

	public void useHeroPower(Hero target) throws NotEnoughManaException,HeroPowerAlreadyUsedException, NotYourTurnException, FullHandException,FullFieldException, CloneNotSupportedException {
		super.useHeroPower();
		boolean flag = false;
		for(int i=0; i<this.getField().size();i++){
			if(this.getField().get(i).getName().equals("Prophet Velen")){
				flag = true;
				break;
			}
		}
		if(flag)
			target.setCurrentHP(target.getCurrentHP() + 8);
		else	
			target.setCurrentHP(target.getCurrentHP() + 2);
	}
	public void useHeroPower(Minion target) throws NotEnoughManaException,HeroPowerAlreadyUsedException, NotYourTurnException, FullHandException,FullFieldException, CloneNotSupportedException {
		super.useHeroPower();
		boolean flag = false;
		for(int i=0; i<this.getField().size();i++){
			if(this.getField().get(i).getName().equals("Prophet Velen")){
				flag = true;
				break;
			}
		}
		if(flag)
			target.setCurrentHP(target.getCurrentHP() + 8);
		else	
			target.setCurrentHP(target.getCurrentHP() + 2);
	}
}
