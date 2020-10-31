package model.cards.spells;

import java.util.ArrayList;
import java.util.Random;

import model.cards.Rarity;
import model.cards.minions.Minion;

public class MultiShot extends Spell implements AOESpell{
	public MultiShot() {
		super("Multi-Shot", 4, Rarity.BASIC);
	}

	@Override
	public void performAction(ArrayList<Minion> oppField,ArrayList<Minion> curField) {
		Random r = new Random () ;
		Random t = new Random () ;
		int x = oppField.size();
		
		if (oppField.size()>2){
			int i = r.nextInt(oppField.size());
			int j = t.nextInt(oppField.size());
			if (i != j){
				if (!oppField.get(i).isDivine()){
				oppField.get(i).setCurrentHP(oppField.get(i).getCurrentHP()- 3);
					if(x != oppField.size()){
						if(j>i)
							j--;
					}
																			}
				else
					oppField.get(i).setDivine(false);
				if (!oppField.get(j).isDivine())
				oppField.get(j).setCurrentHP(oppField.get(j).getCurrentHP()- 3);	
				else
					oppField.get(j).setDivine(false);
				}
			else
					performAction(oppField,curField);
		}
		else if (oppField.size()==2){
			if (!oppField.get(1).isDivine())
			oppField.get(1).setCurrentHP(oppField.get(0).getCurrentHP()- 3);
			else
				oppField.get(1).setDivine(false);
			if (!oppField.get(0).isDivine())
			oppField.get(0).setCurrentHP(oppField.get(1).getCurrentHP()- 3);
			else
				oppField.get(0).setDivine(false);
		}
		else if (oppField.size()==1){
			if (!oppField.get(0).isDivine())
				oppField.get(0).setCurrentHP(oppField.get(0).getCurrentHP()- 3);
			else
				oppField.get(0).setDivine(false);	
		}
		else {}
	}
	
	



}
