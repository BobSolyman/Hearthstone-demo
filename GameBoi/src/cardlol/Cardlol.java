package cardlol;

import model.cards.Card;
import model.cards.Rarity;
import model.cards.minions.Minion;
import model.cards.spells.Spell;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class Cardlol extends AnchorPane {
		private Card cardx ;
		private Minion m ;
		private Spell s ;
		private ImageView cardView , backView ,dv , sleep , minionView ;
		
		public ImageView getMinionView() {
			return minionView;
		}

		private Label mana , aa , hp ;
		private boolean played ;
	
    public boolean isPlayed() {
			return played;
		}

		public void setPlayed(boolean played) {
			this.played = played;
			this.getChildren().clear();
			this.getChildren().add(minionView);
			aa.setFont(new Font ("Rockwell Bold", 15));
			aa.setPrefHeight(18);
    		aa.setPrefWidth(20);
			hp.setFont(new Font ("Rockwell Bold", 15));
			hp.setPrefHeight(18);
    		hp.setPrefWidth(20);
    		aa.setLayoutY(76);
			hp.setLayoutY(76);

			this.setPrefHeight(84);
	    	this.setPrefWidth(76);
	    	if (this.getM().getRarity()==Rarity.LEGENDARY){
	    		minionView.setFitWidth(97);
	    		minionView.setFitHeight(100);
	    		minionView.setLayoutY(5);
	    		aa.setLayoutX(6);
	    		hp.setLayoutX(53);
	    		dv.setFitHeight(89);
	      		dv.setFitWidth(70);
	      		dv.setLayoutX(4);	
	      		dv.setLayoutY(14);

	
	    		if (this.getM().isTaunt()){
	    			minionView.setFitWidth(98);
		    		minionView.setFitHeight(112);
		    		minionView.setLayoutY(5);
	    			aa.setLayoutX(8);
		    		hp.setLayoutX(54);
		    		dv.setFitHeight(91);
		      		dv.setFitWidth(74);
		    		dv.setLayoutX(4);
		      		dv.setLayoutY(14);
	    		}
	    		
	    	}
	   	else	if (this.getM().isTaunt()&& this.getM().getRarity()!= Rarity.LEGENDARY){
	    		minionView.setFitWidth(87);
	    		minionView.setFitHeight(112);
	    		minionView.setLayoutY(7);
	    		aa.setLayoutX(7);
	    		hp.setLayoutX(56);
	    		dv.setFitHeight(95);
	      		dv.setFitWidth(75);
	      		dv.setLayoutX(5);
	      		dv.setLayoutY(12);
	    		

	      		
	    	}
	   	else {		minionView.setFitWidth(78);
	   				minionView.setFitHeight(97);
	   				minionView.setLayoutY(8);
	   				aa.setLayoutX(6);
					hp.setLayoutX(54);
					dv.setFitHeight(91);
		      		dv.setFitWidth(73);
		      		dv.setLayoutX(3);
		      		dv.setLayoutY(10);

	   				
	   								}
	    	this.getChildren().add(aa);
	    	this.getChildren().add(hp);
	    	
			if (this.getM().isDivine())
				this.getChildren().add(dv);
			if (this.getM().isSleeping())
				this.getChildren().add(sleep);
		
		}

	public Cardlol( Card c  ) {
    	super();
    	cardx = c ;
    	this.setPrefHeight(157);
    	this.setPrefWidth(125);
    	mana = new Label();
    	Image iii = new Image (getClass().getResourceAsStream("back of card.png"));
    	backView = new ImageView(iii);
		backView.setFitHeight(120);
		backView.setFitWidth(100);
		backView.setPickOnBounds(true);
    	
    	if (c instanceof Minion){
    		m = (Minion) c;
    		String n = m.getName();
    		Image i ;
    		Image im ;
    		if (n.equals("Goldshire Footman")){
    			i = new Image (getClass().getResourceAsStream("Goldshire Footman.png"));
    			im = new Image (getClass().getResourceAsStream("Goldshire Footman minion.png")); }
    		
    		else if (n.equals("Stonetusk Boar")){
    			i = new Image (getClass().getResourceAsStream("Stonetusk Boar.png"));
    			im = new Image (getClass().getResourceAsStream("Stonetusk Boar minion.png")); }
    		
       		else if (n.equals("Bloodfen Raptor")){
    			i = new Image (getClass().getResourceAsStream("Bloodfen Raptor.png"));
    			im = new Image (getClass().getResourceAsStream("Bloodfen Raptor minion.png")); }
    		
       		else if (n.equals("Frostwolf Grunt")){
    			i = new Image (getClass().getResourceAsStream("Frostwolf Grunt.png"));
    			im = new Image (getClass().getResourceAsStream("Frostwolf Grunt minion.png")); }
    		
       		else if (n.equals("Wolfrider")){
    			i = new Image (getClass().getResourceAsStream("Wolfrider.png"));
    			im = new Image (getClass().getResourceAsStream("Wolfrider minion.png"));}
    		
       		else if (n.equals("Chilwind Yeti")){
    			i = new Image (getClass().getResourceAsStream("Chillwind Yeti.png"));
    			im = new Image (getClass().getResourceAsStream("Chillwind Yeti minion.png")); }
    		
       		else if (n.equals("Boulderfist Ogre")){
    			i = new Image (getClass().getResourceAsStream("Boulderfist Ogre.png"));
    			im = new Image (getClass().getResourceAsStream("Boulderfist Ogre minion.png")); }
    		
       		else if (n.equals("Core Hound")){
    			i = new Image (getClass().getResourceAsStream("Core Hound.png"));
    			im = new Image (getClass().getResourceAsStream("Core Hound minion.png")); }
    		
       		else if (n.equals("Argent Commander")){
    			i = new Image (getClass().getResourceAsStream("Argent Commander.png"));
    			im = new Image (getClass().getResourceAsStream("Argent Commander minion.png")); }
    		
       		else if (n.equals("Sunwalker")){
    			i = new Image (getClass().getResourceAsStream("Sunwalker.png"));
    			im = new Image (getClass().getResourceAsStream("Sunwalker minion.png")); }
    		
       		else if (n.equals("Chromaggus")){
    			i = new Image (getClass().getResourceAsStream("Chromaggus.png"));
    			im = new Image (getClass().getResourceAsStream("Chromaggus minion.png")); }
    		
       		else if (n.equals("The LichKing")){
    			i = new Image (getClass().getResourceAsStream("The LichKing.png"));
    			im = new Image (getClass().getResourceAsStream("The LichKing minion.png")); }
    		
       		else if (n.equals("Icehowl")){
    			i = new Image (getClass().getResourceAsStream("Icehowl.png"));
    			im = new Image (getClass().getResourceAsStream("Icehowl minion.png")); }
    		
       		else if (n.equals("Colossus of the Moon")){
    			i = new Image (getClass().getResourceAsStream("Colossus of the Moon.png"));
    			im = new Image (getClass().getResourceAsStream("Colossus of the Moon minion.png")); }
    		
       		else if (n.equals("King Krush")){
    			i = new Image (getClass().getResourceAsStream("King Krush.png"));
    			im = new Image (getClass().getResourceAsStream("King Krush minion.png")); }
    		
       		else if (n.equals("Kalycgos")){
    			i = new Image (getClass().getResourceAsStream("Kalycgos.png"));
    			im = new Image (getClass().getResourceAsStream("Kalycgos minion.png")); }
    		
       		else if (n.equals("Tirion Fordring")){
    			i = new Image (getClass().getResourceAsStream("Tirion Fordring.png"));
    			im = new Image (getClass().getResourceAsStream("Tirion Fordring minion.png"));}
       		else if (n.equals("Prophet Velen")){
    			i = new Image (getClass().getResourceAsStream("Prophet Velen.png"));
    			im = new Image (getClass().getResourceAsStream("Prophet Velen minion.png")); }
    		
       		else if (n.equals("Sheep")){
    			i = new Image (getClass().getResourceAsStream("Sheep.png"));
    			im = new Image (getClass().getResourceAsStream("Sheep minion.png")); }
    		
       		else if (n.equals("Silver Hand Recruit")){
    			i = new Image (getClass().getResourceAsStream("Silver Hand Recruit.png"));
    			im = new Image (getClass().getResourceAsStream("Silver Hand Recruit minion.png")); }
    		
       		else  {
    			i = new Image (getClass().getResourceAsStream("Wilfred Fizzlebang.png"));
    			im = new Image (getClass().getResourceAsStream("Wilfred Fizzlebang minion.png")); }
    		
    		//init cardView
    		cardView = new ImageView(i);
    		cardView.setFitHeight(157);
    		cardView.setFitWidth(125);
    		cardView.setPickOnBounds(true);
    		this.getChildren().add(cardView);
    		
    		DropShadow dp = new DropShadow();
    		dp.setSpread(0.57);
    		//init mana
    		mana.setAlignment(Pos.CENTER);
    		mana.setFont(new Font ("Rockwell Bold", 20));
    		mana.setTextFill(Color.WHITE);
    		mana.setTextAlignment(TextAlignment.CENTER);
    		mana.setEffect(dp);
    		mana.setPrefHeight(34);
    		mana.setPrefWidth(34);
    		mana.textProperty().bind(c.getManaCosttoBind().asString());
    		mana.setLayoutX(3);
    		this.getChildren().add(mana);
    		
    		//init aa
    		aa = new Label();
    		aa.setAlignment(Pos.CENTER);
    		aa.setFont(new Font ("Rockwell Bold", 20));
    		aa.setTextFill(Color.WHITE);
    		aa.setTextAlignment(TextAlignment.CENTER);
    		aa.setEffect(dp);
    		aa.setPrefHeight(34);
    		aa.setPrefWidth(34);
    		aa.textProperty().bind(m.getAa().asString());
    		aa.setLayoutX(4);
    		aa.setLayoutY(123);
    		this.getChildren().add(aa);
    		
    		//init hp
    		hp = new Label();
    		hp.setAlignment(Pos.CENTER);
    		hp.setFont(new Font ("Rockwell Bold", 20));
    		hp.setTextFill(Color.WHITE);
    		hp.setTextAlignment(TextAlignment.CENTER);
    		hp.setEffect(dp);
    		hp.setPrefHeight(34);
    		hp.setPrefWidth(34);
    		hp.textProperty().bind(m.getHp().asString());
    		hp.setLayoutX(94);
    		hp.setLayoutY(123);
    		this.getChildren().add(hp);
    		
    		if (c.getRarity()==Rarity.LEGENDARY && !c.getName().equals("Sheep")){

    			mana.setLayoutY(6);
		
    		}
 
    
    		 Image iiiii = new Image(getClass().getResourceAsStream("divine shield.png"));
    		  dv = new ImageView(iiiii);
     		dv.setPickOnBounds(true);
    			
    		
    		 
    			 
    			 Image ioi = new Image(getClass().getResourceAsStream("effect sleep.png"));
    			 sleep = new ImageView (ioi);
    			 sleep.setFitHeight(40);
    			 sleep.setFitWidth(40);
    			 sleep.setLayoutX(50);
   
    			 
    		 	 
    		 minionView = new ImageView(im);
    		 	
    		
    		
    		
    	}
    	
    	
    	
    	else if (c instanceof Spell){
    		s = (Spell) c ;
    		String name = c.getName();
    		Image i;
    		if (name.equals("Curse of Weakness"))
    			i = new Image (getClass().getResourceAsStream("Curse of Weakness.png"));
    		else if (name.equals("Flamestrike"))
    			i = new Image (getClass().getResourceAsStream("Flamestrike.png"));
    		else if (name.equals("Polymorph"))
    			i = new Image (getClass().getResourceAsStream("Polymorph.png"));
    		else if (name.equals("Pyroblast"))
    			i = new Image (getClass().getResourceAsStream("Pyroblast.png"));
    		else if (name.equals("Divine Spirit"))
    			i = new Image (getClass().getResourceAsStream("Divine Spirit.png"));
    		else if (name.equals("Holy Nova"))
    			i = new Image (getClass().getResourceAsStream("Holy Nova.png"));
    		else if (name.equals("Kill Command"))
    			i = new Image (getClass().getResourceAsStream("Kill Command.png"));
    		else if (name.equals("Level Up!"))
    			i = new Image (getClass().getResourceAsStream("Level Up!.png"));
    		else if (name.equals("Multi-Shot"))
    			i = new Image (getClass().getResourceAsStream("MultiShot.png"));
    		else if (name.equals("Seal of Champions"))
    			i = new Image (getClass().getResourceAsStream("Seal of Champions.png"));
    		else if (name.equals("Shadow Word: Death"))
    			i = new Image (getClass().getResourceAsStream("Shadow Word Death.png"));
    		else if (name.equals("Siphon Soul"))
    			i = new Image (getClass().getResourceAsStream("Siphon Soul.png"));
    		else
    			i = new Image (getClass().getResourceAsStream("Twisting Nether.png"));
    		
    		//init cardView
    		cardView = new ImageView(i);
    		cardView.setFitHeight(157);
    		cardView.setFitWidth(125);
    		cardView.setPickOnBounds(true);
    		this.getChildren().add(cardView);
    		
    		DropShadow dp = new DropShadow();
    		dp.setSpread(0.57);
    		//init mana
    		mana.setAlignment(Pos.CENTER);
    		mana.setFont(new Font ("Rockwell Bold", 20));
    		mana.setTextFill(Color.WHITE);
    		mana.setTextAlignment(TextAlignment.CENTER);
    		mana.setEffect(dp);
    		mana.setPrefHeight(34);
    		mana.setPrefWidth(34);
    		mana.textProperty().bind(c.getManaCosttoBind().asString());
    		mana.setLayoutX(3);
    		this.getChildren().add(mana);
    		
 		
    		
    	}
    	
	
    }// end of constructor
    
	public ImageView getSleep() {
		return sleep;
	}

	public void setSleep(ImageView sleep) {
		this.sleep = sleep;
	}

	public ImageView getDv() {
		return dv;
	}

	public Label getMana() {
		return mana;
	}

	public Label getAa() {
		return aa;
	}

	public Label getHp() {
		return hp;
	}

	public Card getCardx() {
		return cardx;
	}

	public ImageView getBackView() {
		return backView;
	}

	public ImageView getCardView() {
		return cardView;
	}

	public Minion getM() {
		return m;
	}

	public Spell getS() {
		return s;
	}
    
    
    
} /// end of class