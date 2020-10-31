package model.cards.spells;

import java.util.ArrayList;

import model.cards.Rarity;
import model.cards.minions.Minion;

public class Flamestrike extends Spell implements AOESpell{
	public Flamestrike() {
		super("Flamestrike", 7, Rarity.BASIC);
	}
	public void performAction(ArrayList<Minion> oppField,ArrayList<Minion> curField){
	
		for(int i = oppField.size()-1 ; i > -1 ; i-- ){
			if(oppField.get(i).isDivine())
				oppField.get(i).setDivine(false);
			else
				oppField.get(i).setCurrentHP(oppField.get(i).getCurrentHP() - 4);	
			}
		}
	}
