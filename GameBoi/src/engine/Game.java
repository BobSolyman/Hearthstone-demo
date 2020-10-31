package engine;
//All methods mentioned in the document should be public
//i changed clone() to a public method
import playerlol.Player;
import exceptions.*;
import model.cards.Card;
import model.cards.minions.Minion;
import model.heroes.Hero;
import model.heroes.HeroListener;

public class Game implements HeroListener, ActionValidator{
	private Hero firstHero;
	private Hero secondHero;
	private Hero currentHero;
	private Hero opponent;
	private GameListener listener;
	
	//Initially, the starting hero will have only one mana crystal.
	public Hero getCurrentHero() {
		return currentHero;
	}
	public Hero getOpponent() {
		return opponent;
	}
	
	public Game(Hero p1, Hero p2) throws FullHandException, CloneNotSupportedException {
		firstHero=p1;
		secondHero=p2;
		p1.gethImage().setId("hero1");
		p2.gethImage().setId("hero2");
		p1.getpImage().setId("power1");
		p2.getpImage().setId("power2");
		
		int x = (int)(Math.random()*2)+1;
		if(x==1) {
			currentHero=firstHero;
			opponent=secondHero;
		}
		else if(x==2) {
			currentHero=secondHero;
			opponent=firstHero;
		}
		currentHero.setTotalManaCrystals(1);
		currentHero.setCurrentManaCrystals(1);

		currentHero.setListener(this);
		opponent.setListener(this);
		currentHero.setValidator(this);
		opponent.setValidator(this);
		currentHero.drawCard();
		currentHero.drawCard();
		currentHero.drawCard();
		opponent.drawCard();
		opponent.drawCard();
		opponent.drawCard();
		opponent.drawCard();
	}

	public void setListener(GameListener listener) {
		this.listener = listener;
	}

	public void onHeroDeath(){
		listener.onGameOver();
	}

	public void damageOpponent(int amount){
		opponent.setCurrentHP(opponent.getCurrentHP() - amount);
	}

	public void endTurn() throws FullHandException, CloneNotSupportedException{
		if(currentHero==firstHero){
			currentHero=secondHero;
			opponent=firstHero;
		}
		else if(currentHero==secondHero){
			currentHero=firstHero;
			opponent=secondHero;
		}
		currentHero.setTotalManaCrystals(currentHero.getTotalManaCrystals()+1);
		currentHero.setCurrentManaCrystals(currentHero.getTotalManaCrystals());
		currentHero.setHeroPowerUsed(false);
		for (int i = 0 ; i<currentHero.getField().size();i++){
			currentHero.getField().get(i).setSleeping(false);
			currentHero.getField().get(i).setAttacked(false);
		}
		Card x = currentHero.drawCard();
	
			
	}
	
	public void validateTurn(Hero user) throws NotYourTurnException{
		if(user==opponent)
			throw new NotYourTurnException();
	}

	public void validateAttack(Minion attacker,Minion target) throws CannotAttackException, NotSummonedException, TauntBypassException,InvalidTargetException{
		if(attacker.getAttack()==0 || attacker.isSleeping() || attacker.isAttacked())
			throw new CannotAttackException();
		if(!currentHero.getField().contains(attacker))
			throw new NotSummonedException();
		if(currentHero.getField().contains(target))
			throw new InvalidTargetException();
		if(!opponent.getField().contains(target))
			throw new NotSummonedException();
		if(!target.isTaunt()){	
			for (int i = 0 ; i<opponent.getField().size();i++){
				if(opponent.getField().get(i).isTaunt() )
					throw new TauntBypassException();
			}
		}
	}

	public void validateAttack(Minion attacker,Hero target) throws CannotAttackException, NotSummonedException, TauntBypassException,InvalidTargetException{
		if(attacker.getAttack()==0 || attacker.isSleeping() || attacker.isAttacked())
			throw new CannotAttackException();

		if(!currentHero.getField().contains(attacker))
			throw new NotSummonedException();
		
		for (int i = 0 ; i<target.getField().size();i++){
			if(target.getField().get(i).isTaunt() )
				throw new TauntBypassException();
		}

		if(currentHero == target)
			throw new InvalidTargetException();	
	}

	public void validateManaCost(Card card) throws NotEnoughManaException{
		if(currentHero.getCurrentManaCrystals()<card.getManaCost())
			throw new NotEnoughManaException();
	}

	public void validatePlayingMinion(Minion minion) throws FullFieldException{
		if(currentHero.getField().size()==7)
			throw new FullFieldException();
	}

	public void validateUsingHeroPower(Hero hero) throws NotEnoughManaException, HeroPowerAlreadyUsedException{
		if(hero.isHeroPowerUsed())
			throw new HeroPowerAlreadyUsedException();
		if(hero.getCurrentManaCrystals()<2)
			throw new NotEnoughManaException();
	}

}
