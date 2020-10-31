package model.heroes;
import model.cards.minions.Minion.*;
import model.cards.spells.AOESpell;
import model.cards.spells.FieldSpell;
import model.cards.spells.Flamestrike;
import model.cards.spells.HeroTargetSpell;
import model.cards.spells.LeechingSpell;
import model.cards.spells.MinionTargetSpell;

import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.ImageView;
import engine.ActionValidator;
import exceptions.*;
import model.cards.Card;
import model.cards.Rarity;
import model.cards.minions.*;


public abstract class Hero implements MinionListener{
	private String name; // Read Only
	private int currentHP;
	private SimpleIntegerProperty hhpp ;
	private boolean heroPowerUsed;
	private int totalManaCrystals;
	private SimpleIntegerProperty totalmana ;
	private int currentManaCrystals;
	private SimpleIntegerProperty currmana ;
	private ArrayList<Card> deck; // Read Only
	private ArrayList<Minion> field; // Read Only
	private ArrayList<Card> hand; // Read Only
	private int fatigueDamage; // neither nor
	private HeroListener listener;
	private ActionValidator validator;
	private ImageView hImage ;
	private ImageView pImage;

	
	//--------------Getters & Setters-------------//
	
	public String getName() {
		return name;
	}

	public SimpleIntegerProperty getHhpp() {
		return hhpp;
	}

	public int getCurrentHP() {
		return currentHP;
	}

	public void setCurrentHP(int currentHP) {
		if (currentHP >= 30)
			this.currentHP = 30;
		else
			this.currentHP = currentHP;
		if(this.currentHP<=0)
			listener.onHeroDeath();
		hhpp.set(this.currentHP);
	
	}

	public boolean isHeroPowerUsed() {
		return heroPowerUsed;
	}

	public void setHeroPowerUsed(boolean heroPowerUsed) {
		this.heroPowerUsed = heroPowerUsed;
	}

	public int getTotalManaCrystals() {
		return totalManaCrystals;
	}

	public void setTotalManaCrystals(int totalManaCrystals) {
		if (totalManaCrystals >= 10)
			this.totalManaCrystals = 10;
		else
			this.totalManaCrystals = totalManaCrystals;
		totalmana.set(this.totalManaCrystals);
	}

	public int getCurrentManaCrystals() {
		return currentManaCrystals;
	}
		

	public void setCurrentManaCrystals(int currentManaCrystals) {
		if (currentManaCrystals >= 10)
			this.currentManaCrystals = 10;
		else
			this.currentManaCrystals = currentManaCrystals;
		currmana.set(this.currentManaCrystals);
	}

	public SimpleIntegerProperty getTotalmana() {
		return totalmana;
	}

	public SimpleIntegerProperty getCurrmana() {
		return currmana;
	}

	public ArrayList<Card> getDeck() {
		return deck;
	}

	public ArrayList<Minion> getField() {
		return field;
	}

	public ArrayList<Card> getHand() {
		return hand;
	}

	
	//..Constructor
	
	public Hero(String name) throws IOException {

		this.name = name;
		this.currentHP = 30;
		this.hhpp=new SimpleIntegerProperty(currentHP);
		this.heroPowerUsed = false;
		this.totalManaCrystals = 0;
		this.totalmana =new SimpleIntegerProperty(totalManaCrystals);
		this.currentManaCrystals = this.totalManaCrystals;
		this.currmana = new SimpleIntegerProperty(currentManaCrystals);
		this.fatigueDamage = 0;
		this.deck = new ArrayList<Card>() ;
		this.field = new ArrayList<Minion>();
		this.hand = new ArrayList<Card>() ;
		this.hImage = new ImageView();
		this.pImage = new ImageView();
		try{
		buildDeck();}
				catch(Exception e){}
		for (int i = 0 ; i <deck.size();i++){
		if (deck.get(i) instanceof Minion){
			Minion x =(Minion)deck.get(i);
			x.setListener(this);
			deck.set(i,(Card)x);
			}
		}
	}
	
	//..Methods
	
	public ImageView gethImage() {
		return hImage;
	}

	public ImageView getpImage() {
		return pImage;
	}

	public final static ArrayList<Minion> getAllNeutralMinions(String filePath)throws IOException {
		String currentLine = "";
		FileReader fileReader = new FileReader(filePath);
		BufferedReader br = new BufferedReader(fileReader);

		ArrayList<Minion> res = new ArrayList<Minion>();

		while ((currentLine = br.readLine()) != null) {
			String[] x = currentLine.split(",");
			String n = x[0];
			int mana = Integer.parseInt(x[1]);
			Rarity r = null;
			switch (x[2].charAt(0)) {
			case 'b':
				r = Rarity.BASIC;
				break;
			case 'c':
				r = Rarity.COMMON;
				break;
			case 'r':
				r = Rarity.RARE;
				break;
			case 'e':
				r = Rarity.EPIC;
				break;
			case 'l':
				r = Rarity.LEGENDARY;
				break;
			}
			int aa = Integer.parseInt(x[3]);
			int hp = Integer.parseInt(x[4]);
			boolean t = Boolean.parseBoolean(x[5]);
			boolean d = Boolean.parseBoolean(x[6]);
			boolean c = Boolean.parseBoolean(x[7]);
		

			if(n.equals("Icehowl")){
				Minion m = new Icehowl();
			    res.add(m);}
			else{
			Minion m = new Minion(n, mana, r, aa, hp, t, d, c);
			res.add(m);}
		}
	    br.close();
		return res;
	
	}

	public final static ArrayList<Minion> getNeutralMinions(ArrayList<Minion> minions, int count) throws CloneNotSupportedException {
		ArrayList<Minion> res = new ArrayList<Minion>();
	
		ArrayList<Integer> z = new ArrayList<Integer>();
		for (int j =0;j<minions.size();j++){
			z.add(0);
		}
		
		int i = 0;
		while(i < count && minions.size()>0&& z.size()>0) 
		{
			int x = (int)(Math.random()*minions.size());
			Minion m = 	minions.get(x).clone();
			
	/*	//	 To get the data of a minion and Create a new one :)
			String name_m = minions.get(x).getName();
			int mana_m = minions.get(x).getManaCost();
			Rarity r_m = minions.get(x).getRarity();
			int aa_m = minions.get(x).getAttack();
			int hp_m = minions.get(x).getMaxHP();
			boolean t_m = minions.get(x).isTaunt();
			boolean d_m = minions.get(x).isDivine();
			boolean c_m = !minions.get(x).isSleeping(); 
			
			Minion m = new Minion(name_m, mana_m, r_m, aa_m, hp_m, t_m, d_m, c_m);  */
			
			
          if ( m.getRarity() == Rarity.LEGENDARY)
          {
        	  if (minions.get(x).getName().equals("Icehowl"))
        		  res.add(minions.get(x));
        	  else
        	 res.add(m);
        	  
        	 minions.remove(x);
        	 z.remove(x);
        	 
        	 i++;
        	  }

          if ( m.getRarity()!=Rarity.LEGENDARY)
          {
        	 res.add(m);
        	 z.set(x,z.get(x)+1);
        	 
        	 	if(z.get(x)==2)
        	 	{
        	 		minions.remove(x);
           	  		z.remove(x);
        	 	}
        	 	
        	 i++;
          	  }
			
		}
		return res ;
	}

	public abstract void buildDeck() throws IOException, CloneNotSupportedException;

	public HeroListener getListener() {
		return listener;
	}

	public void setListener(HeroListener listener) {
		this.listener = listener;
	}

	public void setValidator(ActionValidator validator) {
		this.validator = validator;
	}
	public void onMinionDeath(Minion m){
			this.getField().remove(m);
	}
		
	
	//Carefully consider all the special cases when implementing this method.
	public void useHeroPower() throws NotEnoughManaException,HeroPowerAlreadyUsedException, NotYourTurnException, FullHandException,FullFieldException, CloneNotSupportedException{
		validator.validateTurn(this);
		validator.validateUsingHeroPower(this);
		this.setCurrentManaCrystals(this.getCurrentManaCrystals() - 2);
		this.setHeroPowerUsed(true);
	 
	}

	public void playMinion(Minion m) throws NotYourTurnException,NotEnoughManaException, FullFieldException{
		validator.validateTurn(this);
		validator.validateManaCost((Card)m);
		validator.validatePlayingMinion(m);
		m.setListener(this);
		this.getField().add(m);
		this.getHand().remove((Card)m);
		this.setCurrentManaCrystals(this.getCurrentManaCrystals() - m.getManaCost());
	}

	public void attackWithMinion(Minion attacker, Minion target) throws CannotAttackException, NotYourTurnException, TauntBypassException,InvalidTargetException, NotSummonedException{
		validator.validateTurn(this);
		validator.validateAttack(attacker,target);
		attacker.attack(target);
	}

	public void attackWithMinion(Minion attacker, Hero target) throws CannotAttackException, NotYourTurnException, TauntBypassException,InvalidTargetException, NotSummonedException{
		validator.validateTurn(this);
		validator.validateAttack(attacker,target);
		attacker.attack(target);
	}

	public void castSpell(FieldSpell s) throws NotYourTurnException, NotEnoughManaException{
		validator.validateTurn(this);
		validator.validateManaCost((Card)s);
		this.getHand().remove((Card)s);
		for (Minion x : this.getField())   //added for fixing 
			x.setListener(this);			//
		s.performAction(this.getField());
		Card c = (Card) s;
		this.setCurrentManaCrystals(this.getCurrentManaCrystals() - c.getManaCost());
	}

	public void castSpell(AOESpell s, ArrayList<Minion >oppField) throws NotYourTurnException, NotEnoughManaException{
		validator.validateTurn(this);
		validator.validateManaCost((Card)s);
		this.getHand().remove((Card)s);
		for (Minion x : this.getField())    //added for fixing 
			x.setListener(this);			//
		s.performAction(oppField, this.getField());
		Card c = (Card) s;
		this.setCurrentManaCrystals(this.getCurrentManaCrystals() - c.getManaCost());
	}
	
	public void castSpell(MinionTargetSpell s, Minion m) throws NotYourTurnException, NotEnoughManaException, InvalidTargetException{
		validator.validateTurn(this);
		validator.validateManaCost((Card)s);
		this.getHand().remove((Card)s);
		s.performAction(m);
		Card c = (Card) s;
		this.setCurrentManaCrystals(this.getCurrentManaCrystals() - c.getManaCost());
	}

	public void castSpell(HeroTargetSpell s, Hero h) throws NotYourTurnException, NotEnoughManaException{
		validator.validateTurn(this);
		validator.validateManaCost((Card)s);
		this.getHand().remove((Card)s);
		s.performAction(h);
		Card c = (Card) s;
		this.setCurrentManaCrystals(this.getCurrentManaCrystals() - c.getManaCost());
	}

	public void castSpell(LeechingSpell s, Minion m) throws NotYourTurnException, NotEnoughManaException{
		validator.validateTurn(this);
		validator.validateManaCost((Card)s);
		this.getHand().remove((Card)s);
		int j = s.performAction(m);
		this.setCurrentHP(this.getCurrentHP()+j);
		Card c = (Card) s;
		this.setCurrentManaCrystals(this.getCurrentManaCrystals() - c.getManaCost());
	} 

	public void endTurn() throws FullHandException, CloneNotSupportedException{
		listener.endTurn();
	}

	public Card drawCard() throws FullHandException, CloneNotSupportedException{
		if(this.getDeck().size()!=0){
			Card x = this.getDeck().get(0) ;
			if (x instanceof Minion){
				((Minion) x).setListener(this);
			}
			this.getDeck().remove(0);	
			boolean f = false ;
			boolean foundChromaggus = false ;
			if(this.getDeck().size()==0)
				this.fatigueDamage=1;
			if (this.getHand().size()==10)
				throw new FullHandException(x);
			else{
				for (int i = 0 ; i <this.getField().size(); i++ ){
					if (this.getField().get(i).getName().equals(x.getName()))
						f = true;
					}	
				if (f){
					
					this.getHand().add(x.clone());
				}
				else
					this.getHand().add(x);
			}	
				for(int i=0; i<this.getField().size();i++)
					if(this.getField().get(i).getName().equals("Chromaggus"))
						foundChromaggus = true;	
				if(this.getHand().size()!=10&&foundChromaggus)
					this.getHand().add(x.clone());
				if (f)
					return x.clone();
				else
					return x ;
			
		}
		else{
			this.setCurrentHP(this.getCurrentHP() - (this.fatigueDamage++));	
			return null;
		}
	}

	@Override
	public String toString() {
		return "Hero [listener=" + this.getName() + "]";
	}
	
	


}
