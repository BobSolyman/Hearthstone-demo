package model.cards.minions;
import exceptions.*;
import model.cards.Rarity;

public class Icehowl extends Minion   {

	public Icehowl () {
		super("Icehowl", 9, Rarity.LEGENDARY, 10, 10, false, false, true);
	}//"This minion can only attack other minions and can not attack heroes" not handled.

}
