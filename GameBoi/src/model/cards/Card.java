package model.cards;

import javafx.beans.property.SimpleIntegerProperty;

public abstract class Card implements Cloneable{
	private String name;
	private int manaCost;
	private Rarity rarity;
	private SimpleIntegerProperty manaCosttoBind ;
	public Card(String name, int manaCost, Rarity rarity) {
		this.name = name;
		this.rarity = rarity;
		if(manaCost<0)
			this.manaCost = 0;
		else if(manaCost>10)
			this.manaCost = 10;
		else
			this.manaCost=manaCost;
		manaCosttoBind = new SimpleIntegerProperty(this.manaCost);
		
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getManaCost() {
		return manaCost;
	}
	public void setManaCost(int manaCost) {
		if(manaCost<0)
			this.manaCost = 0;
		else if(manaCost>10)
			this.manaCost = 10;
		else
			this.manaCost=manaCost;
		manaCosttoBind.set(this.manaCost);
	}
	public Rarity getRarity() {
		return rarity;
	}
	
	public SimpleIntegerProperty getManaCosttoBind() {
		return manaCosttoBind;
	}
	@Override
	public Card clone() throws CloneNotSupportedException {
		
		return (Card) super.clone();
	}
	

	
}
