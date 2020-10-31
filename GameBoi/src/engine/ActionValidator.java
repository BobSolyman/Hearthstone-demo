package engine;

import exceptions.*;
import model.cards.Card;
import model.cards.minions.Minion;
import model.heroes.Hero;
// The Game should be able to validate the different actions any hero can make
public interface ActionValidator {
	public void validateTurn(Hero user) throws NotYourTurnException;
	
	public void validateAttack(Minion attacker,Minion target) throws CannotAttackException, NotSummonedException, TauntBypassException, InvalidTargetException;

	public void validateAttack(Minion attacker,Hero target) throws	CannotAttackException, NotSummonedException, TauntBypassException,	InvalidTargetException;
	
	public void validateManaCost(Card card) throws NotEnoughManaException;
	
	public void validatePlayingMinion(Minion minion) throws FullFieldException;
	
	public void validateUsingHeroPower(Hero hero) throws NotEnoughManaException, HeroPowerAlreadyUsedException;

}
//A minion can not attack a friendly target (either a hero or a minion).
// A minion can not attack if he has zero attack points.