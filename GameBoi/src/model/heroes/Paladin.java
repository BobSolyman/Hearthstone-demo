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
import model.cards.spells.LevelUp;
import model.cards.spells.SealOfChampions;
import model.cards.spells.Spell;

public class Paladin extends Hero {

	public Paladin() throws IOException, CloneNotSupportedException {
		super("Uther Lightbringer");
		Image i = new Image (getClass().getResourceAsStream("uther.png"));
		Image i2 = new Image (getClass().getResourceAsStream("reinforce.png"));
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
		
		Spell s1 = new SealOfChampions();
		Spell s2 = new SealOfChampions();
		Spell s3 = new LevelUp();
		Spell s4 = new LevelUp();
		d.add(s1);
		d.add(s2);
		d.add(s3);
		d.add(s4);
		
		Minion x = new Minion ("Tirion Fordring",4,Rarity.LEGENDARY,6,6,true,true,false);
		d.add(x);
		
		Collections.shuffle(d);
		getDeck().addAll(d);
	}

	public void useHeroPower() throws NotEnoughManaException,HeroPowerAlreadyUsedException, NotYourTurnException, FullHandException,FullFieldException, CloneNotSupportedException {
		super.useHeroPower();
		Minion m = new Minion("Silver Hand Recruit", 1, Rarity.BASIC, 1, 1, false, false, false);
		m.setListener(this);
		if (this.getField().size()==7)
			throw new FullFieldException();
		else
			this.getField().add(m);
	}
	
}
