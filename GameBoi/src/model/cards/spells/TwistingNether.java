package model.cards.spells;

import java.util.ArrayList;

import model.cards.Rarity;
import model.cards.minions.Minion;

public class TwistingNether extends Spell implements AOESpell{
	public TwistingNether() {
		super("Twisting Nether", 8, Rarity.EPIC);
	}
	
	public void performAction(ArrayList<Minion> oppField,ArrayList<Minion> curField){

		for(int i = oppField.size()-1 ; i > -1 ; i-- ){
			oppField.get(i).setCurrentHP(0);
			}	
		
		for(int j = curField.size()-1 ; j > -1 ; j-- ){
			curField.get(j).setCurrentHP(0);
			}	
		}

	}

