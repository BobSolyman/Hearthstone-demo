package model.cards.minions;
import javafx.beans.property.SimpleIntegerProperty;
import exceptions.InvalidTargetException;
import model.cards.Card;
import model.cards.Rarity;
import model.heroes.Hero;

public class Minion extends Card implements Cloneable{
	private int attack;
	private int maxHP;
	private int currentHP;
	private boolean taunt;
	private boolean divine;
	private boolean sleeping;
	private boolean attacked;
	private MinionListener listener;
	private SimpleIntegerProperty aa ;
	public SimpleIntegerProperty getAa() {
		return aa;
	}

	public SimpleIntegerProperty getHp() {
		return hp;
	}

	private SimpleIntegerProperty hp ;
	
	

	@Override
	public String toString() {
		return "Minion [getName()=" + getName() + ", getRarity()="
				+ getRarity() + "]";
	}

	public Minion(String name, int manaCost, Rarity rarity, int attack, int maxHP, boolean taunt, boolean divine, boolean charge)  {
		super(name, manaCost, rarity);
		
		if(attack<0)
			this.attack=0;
		else
		this.attack = attack;
		aa = new SimpleIntegerProperty(attack);
		this.maxHP = maxHP;
		this.taunt = taunt;
		this.divine = divine;
		this.sleeping = !(charge);
		this.currentHP = maxHP;
		hp = new SimpleIntegerProperty(currentHP);
		this.attacked = false;
	}

	public int getAttack() {
		return attack;
	}

	public void setAttack(int attack) {
		if(attack<0)
			this.attack=0;
		else
		this.attack = attack;
		aa.set(this.attack);
	}

	public int getMaxHP() {
		return maxHP;
	}

	public void setMaxHP(int maxHP) {
		this.maxHP = maxHP;
	}

	public int getCurrentHP() {
		return currentHP;
	}

	public void setCurrentHP(int currentHP) {

	if (currentHP>this.maxHP)
		this.currentHP=this.maxHP;
	else
		this.currentHP = currentHP;
	if(this.currentHP<=0){
		listener.onMinionDeath(this); }
	hp.set(this.currentHP);
	}

	public MinionListener getListener() {
		return listener;
	}

	public boolean isTaunt() {
		return taunt;
	}

	public void setTaunt(boolean taunt) {
		this.taunt = taunt;
	}

	public boolean isDivine() {
		return divine;
	}

	public void setDivine(boolean divine) {
		this.divine = divine;
	}

	public boolean isSleeping() {
		return sleeping;
	}

	public void setSleeping(boolean sleeping) {
		this.sleeping = sleeping;
	}

	public boolean isAttacked() {
		return attacked;
	}

	public void setAttacked(boolean attacked) {
		this.attacked = attacked;
	}

	public void setListener(MinionListener listener) {
		this.listener = listener;
	}//Think about where and to whom this variable will be set.
	//make sure that each hero will listen to the minions in his deck.
	
	//taunt elmfrod le effect 3la these 2 methods below?
	public void attack(Minion target) {
		if(!this.isSleeping() && !this.isAttacked() && this.getAttack()!=0) {
			if(this.isDivine() && target.isDivine()){
				if(target.getAttack()!=0)
					this.setDivine(false);
				target.setDivine(false);
			}
			else if(this.isDivine() && !target.isDivine()){
				target.setCurrentHP(target.getCurrentHP() - this.getAttack());
				if(target.getAttack()!=0)
					this.setDivine(false);						
			}
			else if(!this.isDivine() && target.isDivine()){
				this.setCurrentHP(this.getCurrentHP() - target.getAttack());	
				target.setDivine(false);
			}
			else{
				target.setCurrentHP(target.getCurrentHP() - this.getAttack());
				this.setCurrentHP(this.getCurrentHP() - target.getAttack());
			}
			this.setAttacked(true);
			this.setSleeping(true);
		}
	}
	
	public void attack(Hero target) throws InvalidTargetException{//recheck
		if(this.getName().equals("Icehowl"))
			throw new InvalidTargetException("Icehowl can't attack heroes");
		if(!this.isSleeping() && !this.isAttacked()) {
			target.setCurrentHP(target.getCurrentHP() - this.getAttack());
			this.setAttacked(true);
			this.setSleeping(true);
		}
	}

	@Override
	public Minion clone() throws CloneNotSupportedException {
		String name_m = this.getName();
		int mana_m = this.getManaCost();
		Rarity r_m = this.getRarity();
		int aa_m = this.getAttack();
		int hp_m = this.getMaxHP();
		boolean t_m = this.isTaunt();
		boolean d_m = this.isDivine();
		boolean c_m = !this.isSleeping(); 
		
		Minion m = new Minion(name_m, mana_m, r_m, aa_m, hp_m, t_m, d_m, c_m); 
		return m ;
	}//Make sure that while building the deck of any hero, whenever the same minion is to
	//be included again, a clone of the minion should be added to the deck not the original
	//instance with the same memory reference. which i think 7oda boi already handled it.
}
