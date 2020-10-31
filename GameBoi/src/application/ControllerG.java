package application;

import javafx.*;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import cardlol.Cardlol;
import playerlol.Player;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import engine.Game;
import engine.GameListener;
import exceptions.CannotAttackException;
import exceptions.FullFieldException;
import exceptions.FullHandException;
import exceptions.HeroPowerAlreadyUsedException;
import exceptions.InvalidTargetException;
import exceptions.NotEnoughManaException;
import exceptions.NotSummonedException;
import exceptions.NotYourTurnException;
import exceptions.TauntBypassException;
import model.cards.Card;
import model.cards.Rarity;
import model.cards.minions.Icehowl;
import model.cards.minions.Minion;
import model.cards.spells.AOESpell;
import model.cards.spells.FieldSpell;
import model.cards.spells.HeroTargetSpell;
import model.cards.spells.LeechingSpell;
import model.cards.spells.MinionTargetSpell;
import model.heroes.*;

public class ControllerG implements  GameListener {

	private static Game model ;
	private static Player p1 ;
	private static Player p2 ;
	private Hero h1,h2 ;  // linked to currentHero & opponent
	
	@FXML
	private HBox currField , oppField , currHand , oppHand ;
	@FXML
	private ImageView hero1 , power1 , hero2 , power2 , winner;
	@FXML
	private Pane  hero1pos , power1pos , hero2pos, power2pos , cardburnedpos , carddisplay;

	@FXML
	private Label hp1 , currmana1 ,maxmana1 ;
	
	@FXML
	private Label hp2 , currmana2 ,maxmana2 ;
	
	@FXML
	private Label burnedlabel , exceptionLabel , currCardsleft , oppCardsleft , player1Label ,player2Label , winboi;
	
	@FXML
	private Button endTurn , CloseGame ;
	
	private DropShadow ds = new DropShadow( 20, Color.AQUA );
	private DropShadow selectedeff = new DropShadow(20,Color.YELLOW );
	private DropShadow selectedattacer = new DropShadow(20, Color.RED);
	
	
	
	
	private boolean actionhappened ;
	private Node selected ;
	private ArrayList<Node> kobaya = new ArrayList<Node>() ;
	private Circle ccc ;

				
	
		private EventHandler<MouseEvent> onSelect1 = new EventHandler<MouseEvent>() {
		
		@Override
		public void handle(MouseEvent event) {
			Cardlol c = (Cardlol)event.getSource();
			if (selected != event.getSource())
			c.getCardView().setEffect(ds);
			
			Cardlol cc = new Cardlol(c.getCardx());
			cc.getCardView().setFitHeight(200);
			cc.getCardView().setFitWidth(170);
			cc.getMana().setLayoutX(10);
			cc.getMana().setLayoutY(3);
			if (cc.getM()!= null){
			cc.getAa().setLayoutX(11);
			cc.getAa().setLayoutY(160);
			cc.getHp().setLayoutX(133);
			cc.getHp().setLayoutY(160);	
			if (cc.getDv()!=null){
			cc.getDv().setFitHeight(134);
			cc.getDv().setFitWidth(128);
	     	cc.getDv().setLayoutX(24);}							
			
			
					}
			if (  cc.getM()!= null && cc.getM().getRarity()==Rarity.LEGENDARY){
			cc.getHp().setLayoutY(161);
			cc.getMana().setLayoutY(10);
			if (cc.getDv()!=null)
			cc.getDv().setLayoutX(21);
			}
			if (cc.getSleep()!=null)
				cc.getSleep().setImage(null);
			
			carddisplay.getChildren().add(cc); 				
		}
	} ; 
		private EventHandler<MouseEvent> onSelect2 = new EventHandler<MouseEvent>() {
		
		@Override
		public void handle(MouseEvent event) {
			
			if (selected != event.getSource() && event.getSource() instanceof Cardlol){
				Cardlol c = (Cardlol)event.getSource();
				c.getCardView().setEffect(null);
				if (c.getMinionView()!=null)
				c.getMinionView().setEffect(null);
			 }
			
			
			else if (event.getSource() instanceof ImageView && selected != event.getSource()){
				((ImageView)event.getSource()).setEffect(null);}
				

			carddisplay.getChildren().clear();
				
		}
	} ; 
	
	private EventHandler<MouseEvent> usepowertarget = new EventHandler<MouseEvent>() {

		@Override
		public void handle(MouseEvent event) {
			if (selected!=null && selected !=event.getSource() &&selected instanceof ImageView){
				if (selected == power1){
					if (h1 instanceof Mage && event.getSource() instanceof Cardlol ){
						Cardlol target = (Cardlol)event.getSource();
						if (target.getM()!= null ){
							try {
								((Mage)h1).useHeroPower(target.getM());
							} catch (NotEnoughManaException e) { exceptionLabel.setText(e.getMessage());
							} catch (HeroPowerAlreadyUsedException e) { exceptionLabel.setText(e.getMessage());
							} catch (NotYourTurnException e) { exceptionLabel.setText(e.getMessage());
							} catch (FullHandException e) { exceptionLabel.setText(e.getMessage());
								Cardlol burned = new Cardlol(e.getBurned());
								cardburnedpos.getChildren().add(burned);
								burnedlabel.setText("Burned");
							} catch (FullFieldException e) { exceptionLabel.setText(e.getMessage()); 
							} catch (CloneNotSupportedException e) { exceptionLabel.setText(e.getMessage()); 
							}	finally{
								selected=null;
								actionhappened = true;
							}
						}	
					}// end of using mage power on a minion	
					else if (h1 instanceof Mage && event.getSource() instanceof ImageView && ( hero2 == event.getSource()|| hero1 == event.getSource())){
						if (hero1 == event.getSource()){
							try {
								((Mage)h1).useHeroPower(h1);
							} catch (NotEnoughManaException e) { exceptionLabel.setText(e.getMessage());
							} catch (HeroPowerAlreadyUsedException e) { exceptionLabel.setText(e.getMessage());
							} catch (NotYourTurnException e) { exceptionLabel.setText(e.getMessage());
							} catch (FullHandException e) { exceptionLabel.setText(e.getMessage());
								Cardlol burned = new Cardlol(e.getBurned());
								cardburnedpos.getChildren().add(burned);
								burnedlabel.setText("Burned");
							} catch (FullFieldException e) { exceptionLabel.setText(e.getMessage());
							} catch (CloneNotSupportedException e) { exceptionLabel.setText(e.getMessage());
							} finally {
								selected = null ;
								actionhappened = true ;
							}
							
						}// end of attacking current hero with mage power
						else if (hero2 == event.getSource()){
							try {
								((Mage)h1).useHeroPower(h2);
							} catch (NotEnoughManaException e) { exceptionLabel.setText(e.getMessage());
							} catch (HeroPowerAlreadyUsedException e) { exceptionLabel.setText(e.getMessage());
							} catch (NotYourTurnException e) { exceptionLabel.setText(e.getMessage());
							} catch (FullHandException e) { exceptionLabel.setText(e.getMessage());
								Cardlol burned = new Cardlol(e.getBurned());
								cardburnedpos.getChildren().add(burned);
								burnedlabel.setText("Burned");
							} catch (FullFieldException e) { exceptionLabel.setText(e.getMessage());
							} catch (CloneNotSupportedException e) { exceptionLabel.setText(e.getMessage());
							} finally {
								selected = null ;
								actionhappened = true ;
							}
							
						}// end of attacking oppenent with mage power
						
					}//end of using mage power on a hero
					else if (h1 instanceof Priest && event.getSource() instanceof Cardlol){
						Cardlol target = (Cardlol)event.getSource();
						if (target.getM()!= null ){
							try {
								((Priest)h1).useHeroPower(target.getM());
							} catch (NotEnoughManaException e) { exceptionLabel.setText(e.getMessage());
							} catch (HeroPowerAlreadyUsedException e) { exceptionLabel.setText(e.getMessage());
							} catch (NotYourTurnException e) { exceptionLabel.setText(e.getMessage());
							} catch (FullHandException e) { exceptionLabel.setText(e.getMessage());
								Cardlol burned = new Cardlol(e.getBurned());
								cardburnedpos.getChildren().add(burned);
								burnedlabel.setText("Burned");
							} catch (FullFieldException e) { exceptionLabel.setText(e.getMessage());
							} catch (CloneNotSupportedException e) { exceptionLabel.setText(e.getMessage());
							} finally {
								selected = null ;
								actionhappened = true; 
							}
						}
		
					}//end of using priest power on minion
					else if (h1 instanceof Priest && event.getSource() instanceof ImageView && (hero1 == event.getSource()|| hero2 == event.getSource())){
							if (hero1 == event.getSource()){
								try {
									((Priest)h1).useHeroPower(h1);
								} catch (NotEnoughManaException e) { exceptionLabel.setText(e.getMessage());
								} catch (HeroPowerAlreadyUsedException e) { exceptionLabel.setText(e.getMessage());
								} catch (NotYourTurnException e) { exceptionLabel.setText(e.getMessage());
								} catch (FullHandException e) { exceptionLabel.setText(e.getMessage());
									Cardlol burned = new Cardlol(e.getBurned());
									cardburnedpos.getChildren().add(burned);
									burnedlabel.setText("Burned");
								} catch (FullFieldException e) { exceptionLabel.setText(e.getMessage());
								} catch (CloneNotSupportedException e) { exceptionLabel.setText(e.getMessage());
								} finally {
									selected = null ;
									actionhappened = true ;
								}
							} // end of healing urself 
							else if (hero2 == event.getSource()){
								try {
									((Priest)h1).useHeroPower(h2);
								} catch (NotEnoughManaException e) { exceptionLabel.setText(e.getMessage());
								} catch (HeroPowerAlreadyUsedException e) { exceptionLabel.setText(e.getMessage());
								} catch (NotYourTurnException e) { exceptionLabel.setText(e.getMessage());
								} catch (FullHandException e) { exceptionLabel.setText(e.getMessage());
									Cardlol burned = new Cardlol(e.getBurned());
									cardburnedpos.getChildren().add(burned);
									burnedlabel.setText("Burned");
								} catch (FullFieldException e) { exceptionLabel.setText(e.getMessage());
								} catch (CloneNotSupportedException e) { exceptionLabel.setText(e.getMessage());
								} finally {
									selected = null ;
									actionhappened = true ;
								}
							} // end of healing opponenet 
						
						
					}//end of using priest power on heroes
					
				}//endf of using power1
				else if (selected == power2){
					
					if (h2 instanceof Mage && event.getSource() instanceof Cardlol ){
						Cardlol target = (Cardlol)event.getSource();
						if (target.getM()!= null ){
							try {
								((Mage)h2).useHeroPower(target.getM());
							} catch (NotEnoughManaException e) { exceptionLabel.setText(e.getMessage());
							} catch (HeroPowerAlreadyUsedException e) { exceptionLabel.setText(e.getMessage());
							} catch (NotYourTurnException e) { exceptionLabel.setText(e.getMessage());
							} catch (FullHandException e) { exceptionLabel.setText(e.getMessage());
								Cardlol burned = new Cardlol(e.getBurned());
								cardburnedpos.getChildren().add(burned);
								burnedlabel.setText("Burned");
							} catch (FullFieldException e) { exceptionLabel.setText(e.getMessage());
							} catch (CloneNotSupportedException e) { exceptionLabel.setText(e.getMessage());
							}	finally{
								selected=null;
								actionhappened = true;
							}
						}	
					}// end of using mage power on a minion	
					else if (h2 instanceof Mage && event.getSource() instanceof ImageView &&  hero2 == event.getSource()){
						if (hero1 == event.getSource()){
							try {
								((Mage)h2).useHeroPower(h1);
							} catch (NotEnoughManaException e) { exceptionLabel.setText(e.getMessage());
							} catch (HeroPowerAlreadyUsedException e) { exceptionLabel.setText(e.getMessage());
							} catch (NotYourTurnException e) { exceptionLabel.setText(e.getMessage());
							} catch (FullHandException e) { exceptionLabel.setText(e.getMessage());
								Cardlol burned = new Cardlol(e.getBurned());
								cardburnedpos.getChildren().add(burned);
								burnedlabel.setText("Burned");
							} catch (FullFieldException e) { exceptionLabel.setText(e.getMessage());
							} catch (CloneNotSupportedException e) { exceptionLabel.setText(e.getMessage());
							} finally {
								selected = null ;
								actionhappened = true ;
							}
							
						}// enf of attacking current hero with mage power
						else if (hero2 == event.getSource()){
							try {
								((Mage)h2).useHeroPower(h2);
							} catch (NotEnoughManaException e) { exceptionLabel.setText(e.getMessage());
							} catch (HeroPowerAlreadyUsedException e) { exceptionLabel.setText(e.getMessage());
							} catch (NotYourTurnException e) { exceptionLabel.setText(e.getMessage());
							} catch (FullHandException e) { exceptionLabel.setText(e.getMessage());
								Cardlol burned = new Cardlol(e.getBurned());
								cardburnedpos.getChildren().add(burned);
								burnedlabel.setText("Burned");
							} catch (FullFieldException e) { exceptionLabel.setText(e.getMessage());
							} catch (CloneNotSupportedException e) { exceptionLabel.setText(e.getMessage());
							} finally {
								selected = null ;
								actionhappened = true ;
							}
							
						}// end of attacking oppenent with mage power
						
					}//end of using mage power on a hero
					else if (h2 instanceof Priest && event.getSource() instanceof Cardlol){
						Cardlol target = (Cardlol)event.getSource();
						if (target.getM()!= null ){
							try {
								((Priest)h2).useHeroPower(target.getM());
							} catch (NotEnoughManaException e) { exceptionLabel.setText(e.getMessage());
							} catch (HeroPowerAlreadyUsedException e) { exceptionLabel.setText(e.getMessage());
							} catch (NotYourTurnException e) { exceptionLabel.setText(e.getMessage());
							} catch (FullHandException e) { exceptionLabel.setText(e.getMessage());
								Cardlol burned = new Cardlol(e.getBurned());
								cardburnedpos.getChildren().add(burned);
								burnedlabel.setText("Burned");
							} catch (FullFieldException e) { exceptionLabel.setText(e.getMessage());
							} catch (CloneNotSupportedException e) { exceptionLabel.setText(e.getMessage());
							} finally {
								selected = null ;
								actionhappened = true; 
							}
						}
		
					}//end of using priest power on minion
					else if (h2 instanceof Priest && event.getSource() instanceof ImageView && (hero1 == event.getSource()|| hero2 == event.getSource())){
							if (hero1 == event.getSource()){
								try {
									((Priest)h2).useHeroPower(h1);
								} catch (NotEnoughManaException e) { exceptionLabel.setText(e.getMessage());
								} catch (HeroPowerAlreadyUsedException e) { exceptionLabel.setText(e.getMessage());
								} catch (NotYourTurnException e) { exceptionLabel.setText(e.getMessage());
								} catch (FullHandException e) { exceptionLabel.setText(e.getMessage());
									Cardlol burned = new Cardlol(e.getBurned());
									cardburnedpos.getChildren().add(burned);
									burnedlabel.setText("Burned");
								} catch (FullFieldException e) { exceptionLabel.setText(e.getMessage());
								} catch (CloneNotSupportedException e) { exceptionLabel.setText(e.getMessage());
								} finally {
									selected = null ;
									actionhappened = true ;
								}
							} // end of healing urself 
							else if (hero2 == event.getSource()){
								try {
									((Priest)h2).useHeroPower(h2);
								} catch (NotEnoughManaException e) { exceptionLabel.setText(e.getMessage());
								} catch (HeroPowerAlreadyUsedException e) { exceptionLabel.setText(e.getMessage());
								} catch (NotYourTurnException e) { exceptionLabel.setText(e.getMessage());
								} catch (FullHandException e) { exceptionLabel.setText(e.getMessage());
									Cardlol burned = new Cardlol(e.getBurned());
									cardburnedpos.getChildren().add(burned);
									burnedlabel.setText("Burned");
								} catch (FullFieldException e) { exceptionLabel.setText(e.getMessage());
								} catch (CloneNotSupportedException e) { exceptionLabel.setText(e.getMessage());
								} finally {
									selected = null ;
									actionhappened = true ;
								}
							} // end of healing opponenet 
						
						
					}//end of using priest power on heroes
					
				} //end of using power 2 
				
			}// end of checking if the selected is a hero power && we choose a target
	
	}};
	
	
	
	private EventHandler<MouseEvent> doubleClickers = new EventHandler<MouseEvent>() {
		
		@Override
		public void handle(MouseEvent event) {
			
			if(selected == event.getSource() && event.getSource() instanceof Cardlol) {
				Cardlol c = (Cardlol)event.getSource();
				if (c.getM()!=null && h1.getHand().contains((c.getM()))) {
					try {
						h1.playMinion(c.getM());
						c.setPlayed(true);
						currField.getChildren().add(c);
						actionhappened=true;
					} catch (NotYourTurnException e) { exceptionLabel.setText(e.getMessage());
					} catch (NotEnoughManaException e) { exceptionLabel.setText(e.getMessage());
					} catch (FullFieldException e) { exceptionLabel.setText(e.getMessage());
					}
					finally {
						c.getCardView().setEffect(null);
						selected = null ;
					}
				}
				else if (c.getS()!= null){
					if (c.getS() instanceof FieldSpell){
						try {
							h1.castSpell((FieldSpell)c.getS());
							currHand.getChildren().remove(c);
							actionhappened=true;
						} catch (NotYourTurnException e) { exceptionLabel.setText(e.getMessage());
						} catch (NotEnoughManaException e) { exceptionLabel.setText(e.getMessage());
						} finally {
							c.getCardView().setEffect(null);
							selected = null ;
						}
					}
					else if (c.getS() instanceof AOESpell){
						try {
							for (Minion lolo : h2.getField())
								lolo.setListener(h2);
							h1.castSpell((AOESpell)c.getS(), h2.getField());
							currHand.getChildren().remove(c);
							actionhappened=true;
						} catch (NotYourTurnException e) { exceptionLabel.setText(e.getMessage());
						} catch (NotEnoughManaException e) { exceptionLabel.setText(e.getMessage());
						} finally {
							c.getCardView().setEffect(null);
							selected = null ;
						}		
					}
				}
			}// end of playing cards
			else if (selected == event.getSource() && event.getSource() instanceof ImageView){
				if (power1 == event.getSource()){
					if (h1.getName().equals("Gul'dan")||h1.getName().equals("Uther Lightbringer")||h1.getName().equals("Rexxar")){
						if (h1 instanceof Warlock){
							try {
								((Warlock)h1).useHeroPower();
							} catch (NotEnoughManaException e) { exceptionLabel.setText(e.getMessage());
							} catch (HeroPowerAlreadyUsedException e) { exceptionLabel.setText(e.getMessage());
							} catch (NotYourTurnException e) { exceptionLabel.setText(e.getMessage());
							} catch (FullHandException e) { exceptionLabel.setText(e.getMessage());
								Cardlol burned = new Cardlol(e.getBurned());
								cardburnedpos.getChildren().add(burned);
								burnedlabel.setText("Burned");
							} catch (FullFieldException e) { exceptionLabel.setText(e.getMessage());
							} catch (CloneNotSupportedException e) { exceptionLabel.setText(e.getMessage());
							} finally {
								selected = null ;
								actionhappened= true ;
							}
						} // end if h1 is warlock 
						else if (h1 instanceof Hunter){
							try {
								((Hunter)h1).useHeroPower();
							} catch (NotEnoughManaException e) { exceptionLabel.setText(e.getMessage());
							} catch (HeroPowerAlreadyUsedException e) { exceptionLabel.setText(e.getMessage());
							} catch (NotYourTurnException e) { exceptionLabel.setText(e.getMessage());
							} catch (FullHandException e) { exceptionLabel.setText(e.getMessage());
								Cardlol burned = new Cardlol(e.getBurned());
								cardburnedpos.getChildren().add(burned);
								burnedlabel.setText("Burned");
							} catch (FullFieldException e) { exceptionLabel.setText(e.getMessage());
							} catch (CloneNotSupportedException e) { exceptionLabel.setText(e.getMessage());
							} finally {
								selected = null ;
								actionhappened= true ;
							}
							
						}// end if h1 is hunter
						else if (h1 instanceof Paladin){
							try {
								((Paladin)h1).useHeroPower();
							} catch (NotEnoughManaException e) { exceptionLabel.setText(e.getMessage());
							} catch (HeroPowerAlreadyUsedException e) { exceptionLabel.setText(e.getMessage());
							} catch (NotYourTurnException e) { exceptionLabel.setText(e.getMessage());
							} catch (FullHandException e) { exceptionLabel.setText(e.getMessage());
								Cardlol burned = new Cardlol(e.getBurned());
								cardburnedpos.getChildren().add(burned);
								burnedlabel.setText("Burned");
							} catch (FullFieldException e) { exceptionLabel.setText(e.getMessage());
							} catch (CloneNotSupportedException e) { exceptionLabel.setText(e.getMessage());
							} finally {
								selected = null ;
								actionhappened= true ;
							}
						}// end if h1 is paladin
					
					}//end checking h1 power is no argument need power
					
				}	// end of using current hero power
				else if (power2 == event.getSource()){
					if (h2.getName().equals("Gul'dan")||h2.getName().equals("Uther Lightbringer")||h2.getName().equals("Rexxar")){
						if (h2 instanceof Warlock){
							try {
								((Warlock)h2).useHeroPower();
							} catch (NotEnoughManaException e) { exceptionLabel.setText(e.getMessage());
							} catch (HeroPowerAlreadyUsedException e) { exceptionLabel.setText(e.getMessage());
							} catch (NotYourTurnException e) { exceptionLabel.setText(e.getMessage());
							} catch (FullHandException e) { exceptionLabel.setText(e.getMessage());
								Cardlol burned = new Cardlol(e.getBurned());
								cardburnedpos.getChildren().add(burned);
								burnedlabel.setText("Burned");
							} catch (FullFieldException e) { exceptionLabel.setText(e.getMessage());
							} catch (CloneNotSupportedException e) { exceptionLabel.setText(e.getMessage());
							} finally {
								selected = null ;
								actionhappened= true ;
							}
						} // end if h2 is warlock 
						else if (h2 instanceof Hunter){
							try {
								((Hunter)h2).useHeroPower();
							} catch (NotEnoughManaException e) { exceptionLabel.setText(e.getMessage());
							} catch (HeroPowerAlreadyUsedException e) { exceptionLabel.setText(e.getMessage());
							} catch (NotYourTurnException e) { exceptionLabel.setText(e.getMessage());
							} catch (FullHandException e) { exceptionLabel.setText(e.getMessage());
								Cardlol burned = new Cardlol(e.getBurned());
								cardburnedpos.getChildren().add(burned);
								burnedlabel.setText("Burned");
							} catch (FullFieldException e) { exceptionLabel.setText(e.getMessage());
							} catch (CloneNotSupportedException e) { exceptionLabel.setText(e.getMessage());
							} finally {
								selected = null ;
								actionhappened= true ;
							}
							
						}// end if h2 is hunter
						else if (h2 instanceof Paladin){
							try {
								((Paladin)h2).useHeroPower();
							} catch (NotEnoughManaException e) { exceptionLabel.setText(e.getMessage());
							} catch (HeroPowerAlreadyUsedException e) { exceptionLabel.setText(e.getMessage());
							} catch (NotYourTurnException e) { exceptionLabel.setText(e.getMessage());
							} catch (FullHandException e) { exceptionLabel.setText(e.getMessage());
								Cardlol burned = new Cardlol(e.getBurned());
								cardburnedpos.getChildren().add(burned);
								burnedlabel.setText("Burned");
							} catch (FullFieldException e) { exceptionLabel.setText(e.getMessage());
							} catch (CloneNotSupportedException e) { exceptionLabel.setText(e.getMessage());
							} finally {
								selected = null ;
								actionhappened= true ;
							}
						}// end if h2 is paladin
						
					}//end checking h2 power is no argument need power
					
				}//end of using opponent power
				
			}// end of using hero powers
		}
	};

	private EventHandler<MouseEvent> spellattacks = new EventHandler<MouseEvent>() {
		
		@Override
		public void handle(MouseEvent event) {
			if(selected != event.getSource() && selected!=null && !currHand.getChildren().contains(event.getSource())&& selected instanceof Cardlol) {
				Cardlol cc = (Cardlol) selected;
				if(cc.getS()!=null && cc.getM()==null ) {
					if (cc.getS() instanceof HeroTargetSpell && (event.getSource()==hero1 || event.getSource()==hero2 )){
						if(event.getSource()==hero1){
							try {
								h1.castSpell((HeroTargetSpell)cc.getS(), h1);
								currHand.getChildren().remove(cc);
								actionhappened=true;
							} catch (NotYourTurnException e) { exceptionLabel.setText(e.getMessage());
							} catch (NotEnoughManaException e) { exceptionLabel.setText(e.getMessage());
							} finally{
								cc.getCardView().setEffect(null);
								selected = null;
							}
						}
						else if(event.getSource()==hero2){
							try {
								h1.castSpell((HeroTargetSpell)cc.getS(), h2);
								currHand.getChildren().remove(cc);
								actionhappened=true;
							} catch (NotYourTurnException e) { exceptionLabel.setText(e.getMessage());
							} catch (NotEnoughManaException e) { exceptionLabel.setText(e.getMessage());
							} finally{
								cc.getCardView().setEffect(null);
								selected = null;
							}
						}
					}
					else if (cc.getS() instanceof MinionTargetSpell){
						if (event.getSource() instanceof Cardlol){
							Cardlol minion = (Cardlol)event.getSource();
							if (minion.getM()!=null) {
								try {
									h1.castSpell((MinionTargetSpell)cc.getS(), minion.getM() );
									currHand.getChildren().remove(cc);
									actionhappened=true;
								} catch (NotYourTurnException e) { exceptionLabel.setText(e.getMessage());
								} catch (NotEnoughManaException e) { exceptionLabel.setText(e.getMessage());
								} catch (InvalidTargetException e) { exceptionLabel.setText(e.getMessage());	
								} finally{
									minion.getCardView().setEffect(null);
									selected = null;
								}
							}		
						}
					}
					else if (cc.getS() instanceof LeechingSpell){
						if (event.getSource() instanceof Cardlol){
							Cardlol minion = (Cardlol)event.getSource();
							if (minion.getM()!=null) {
								try {
									h1.castSpell((LeechingSpell)cc.getS(), minion.getM());
									currHand.getChildren().remove(cc);
									actionhappened=true;
								} catch (NotYourTurnException e) { exceptionLabel.setText(e.getMessage()); 
								} catch (NotEnoughManaException e) { exceptionLabel.setText(e.getMessage());
								} finally{
									minion.getCardView().setEffect(null);
									selected = null;
								}
							}
						}	
					}
				}//end of checking for spell
			}//end of checking two different actions
		}
	};
	
	private EventHandler<MouseEvent> attackM = new EventHandler<MouseEvent>() {
		
		@Override
		public void handle(MouseEvent event) {
			if (selected!=event.getSource()&& selected!=null){
				if (selected instanceof Cardlol){
					Cardlol attacker = (Cardlol)selected ;
					if (attacker.getM()!= null) {
						if (hero1 == event.getSource()){
							try {
								h1.attackWithMinion(attacker.getM(), h1);
								actionhappened=true;
							} catch (CannotAttackException e) { exceptionLabel.setText(e.getMessage());
							} catch (NotYourTurnException e) { exceptionLabel.setText(e.getMessage());
							} catch (TauntBypassException e) { exceptionLabel.setText(e.getMessage());
							} catch (InvalidTargetException e) { exceptionLabel.setText(e.getMessage());
							} catch (NotSummonedException e)  { exceptionLabel.setText(e.getMessage());
							} finally {
								attacker.getCardView().setEffect(null);
								selected = null ;
							}
						}
						else if (hero2 == event.getSource()){
							try {
								h1.attackWithMinion(attacker.getM(), h2);
								actionhappened=true;
							} catch (CannotAttackException e) { exceptionLabel.setText(e.getMessage());
							} catch (NotYourTurnException e) { exceptionLabel.setText(e.getMessage());
							} catch (TauntBypassException e) { exceptionLabel.setText(e.getMessage());
							} catch (InvalidTargetException e) { exceptionLabel.setText(e.getMessage());
							} catch (NotSummonedException e)  { exceptionLabel.setText(e.getMessage());
							} finally {
								attacker.getCardView().setEffect(null);
								selected = null ;
							}
						}
						else if (event.getSource() instanceof Cardlol) {
							Cardlol target = (Cardlol)event.getSource();
							if (target.getM() != null){
								try {
									h1.attackWithMinion(attacker.getM(), target.getM());
									actionhappened=true;
								} catch (CannotAttackException e) { exceptionLabel.setText(e.getMessage());
								} catch (NotYourTurnException e) { exceptionLabel.setText(e.getMessage());
								} catch (TauntBypassException e) { exceptionLabel.setText(e.getMessage());
								} catch (InvalidTargetException e) { exceptionLabel.setText(e.getMessage());
								} catch (NotSummonedException e) { exceptionLabel.setText(e.getMessage());
								} finally {
									attacker.getCardView().setEffect(null);
									selected = null ;
								}
							}
						}
					}	
				}	
			}
		}
	};
		
	private EventHandler<MouseEvent> playcard = new EventHandler<MouseEvent>() {
		
		@Override
		public void handle(MouseEvent event) {
			if (selected == null){	
				if(event.getSource() instanceof Cardlol){
					selected = (Node) event.getSource() ;
					Cardlol mac = (Cardlol)selected;
					if(h1.getHand().contains(mac.getCardx()))
						mac.getCardView().setEffect(selectedeff);
					else if (mac.getCardView()!=null)
						mac.getCardView().setEffect(selectedattacer);
					 if (mac.getMinionView()!=null)
						mac.getMinionView().setEffect(selectedattacer);
				}
				else if (event.getSource() instanceof ImageView && (power1 == event.getSource()|| power2 == event.getSource())){
					selected = (Node)event.getSource();
					ImageView pwr = (ImageView)selected ;
					pwr.setEffect(selectedattacer);
				}
			}
			else if(selected != null) { 
				doubleClickers.handle(event);
				attackM.handle(event);
				spellattacks.handle(event);
				usepowertarget.handle(event);
				selected = null;

			}
			
			//updating fields   
		
			if (actionhappened){
				selected = null ;	
				currField.getChildren().clear();
				oppField.getChildren().clear();
				currHand.getChildren().clear();
				for (Minion c : h1.getField()){
					Cardlol x1 = new Cardlol((Card)c);
					x1.setPlayed(true);
					x1.addEventHandler(MouseEvent.MOUSE_ENTERED, onSelect1);
					x1.addEventHandler(MouseEvent.MOUSE_EXITED, onSelect2);
					x1.addEventHandler(MouseEvent.MOUSE_CLICKED, playcard);
					currField.getChildren().add(x1);
				} 
				for (Minion c : h2.getField()){
					Cardlol x1 = new Cardlol((Card)c);
					x1.setPlayed(true);
					x1.addEventHandler(MouseEvent.MOUSE_ENTERED, onSelect1);
					x1.addEventHandler(MouseEvent.MOUSE_EXITED, onSelect2);
					x1.addEventHandler(MouseEvent.MOUSE_CLICKED, playcard);
					oppField.getChildren().add(x1);
				} 
				 // updating hands
				for (Card c : h1.getHand()){
					Cardlol x1 = new Cardlol(c);
					x1.addEventHandler(MouseEvent.MOUSE_ENTERED, onSelect1);
					x1.addEventHandler(MouseEvent.MOUSE_EXITED, onSelect2);
					x1.addEventHandler(MouseEvent.MOUSE_CLICKED, playcard);
					currHand.getChildren().add(x1); }
				
				actionhappened = false ;				
			}
		} 
	};	
	

	public void p1select (MouseEvent e) throws IOException, CloneNotSupportedException {
		ImageView x = (ImageView)e.getSource();
			p1 = new Player (x.getId());
			AnchorPane root1 = FXMLLoader.load(getClass().getResource("/scenes/menu2.fxml"));
			Scene scene2 = new Scene(root1);
			Stage s = (Stage)((Node)x).getScene().getWindow();
			s.setScene(scene2);	

			
	}
	
	public void p2select (MouseEvent e) throws IOException, CloneNotSupportedException {
		ImageView x = (ImageView)e.getSource();
			p2 = new Player (x.getId());
			AnchorPane root1 = FXMLLoader.load(getClass().getResource("/scenes/startG.fxml"));
			Scene scene2 = new Scene(root1);
			Stage s = (Stage)((Node)x).getScene().getWindow();
			s.setScene(scene2);	

			
	}
	
	public void noice (ActionEvent e ) throws FullHandException, CloneNotSupportedException, IOException{
		Button x = (Button)e.getSource();
		model = new Game (p1.getH(),p2.getH());
		model.setListener(this);
		h1= model.getCurrentHero(); h2 =model.getOpponent();
		AnchorPane root = FXMLLoader.load(getClass().getResource("/scenes/Game.fxml"));
		Scene scene2 = new Scene(root);
		Stage s = (Stage)((Node)x).getScene().getWindow();
		s.setScene(scene2);
		
	
	
		oppField = (HBox)root.getChildren().get(0);
		currField = (HBox)root.getChildren().get(1);
		currHand =(HBox)root.getChildren().get(2);
		oppHand =(HBox)root.getChildren().get(3);
		endTurn = (Button)root.getChildren().get(16);
		endTurn.setOnAction(event -> endTurn(event));
		cardburnedpos = (Pane)root.getChildren().get(17);
		burnedlabel = (Label)root.getChildren().get(18);
		carddisplay = (Pane)root.getChildren().get(19);
		exceptionLabel = (Label)root.getChildren().get(20);
		currCardsleft = (Label)root.getChildren().get(21);
        oppCardsleft = (Label)root.getChildren().get(22);
        player1Label = (Label)root.getChildren().get(23);
        player2Label = (Label)root.getChildren().get(24);
        winner = (ImageView) root.getChildren().get(25);
        winboi = (Label) root.getChildren().get(26);
        CloseGame = (Button) root.getChildren().get(27);
        CloseGame.setOnAction(event -> CloseGame(event));
        if (h1==p1.getH()){
            player1Label.setText("Player1 " + h1.getName());
            player2Label.setText("Player2 " + h2.getName());    }
        else{
            player2Label.setText("Player1 " + h2.getName());
            player1Label.setText("Player2 " + h1.getName()); }

		
		// hero1 setting
		hero1pos =(Pane)root.getChildren().get(4);
		hero1 = h1.gethImage();
		hero1.addEventHandler(MouseEvent.MOUSE_CLICKED, playcard);
		hero1pos.getChildren().add(hero1);
		power1pos =(Pane)root.getChildren().get(5);
		power1=h1.getpImage();
		power1.addEventHandler(MouseEvent.MOUSE_CLICKED, playcard);
		power1.addEventHandler(MouseEvent.MOUSE_EXITED, onSelect2);
		power1pos.getChildren().add(power1);
		hp1 = (Label)root.getChildren().get(9);
		hp1.textProperty().bind(model.getCurrentHero().getHhpp().asString());
		currmana1 = (Label)root.getChildren().get(12);
		currmana1.textProperty().bind(model.getCurrentHero().getCurrmana().asString());
		maxmana1 = (Label)root.getChildren().get(13);
		maxmana1.textProperty().bind(model.getCurrentHero().getTotalmana().asString());
		for (Card c : h1.getHand()){
			Cardlol x1 = new Cardlol(c);
			x1.addEventHandler(MouseEvent.MOUSE_ENTERED, onSelect1);
			x1.addEventHandler(MouseEvent.MOUSE_EXITED, onSelect2);
			x1.addEventHandler(MouseEvent.MOUSE_CLICKED, playcard);
			currHand.getChildren().add(x1);
		} 
	
		
		
		//hero2 setting
		hero2pos =(Pane)root.getChildren().get(6);
		hero2=h2.gethImage();
		hero2.addEventHandler(MouseEvent.MOUSE_CLICKED, playcard);
		hero2pos.getChildren().add(hero2);
		power2pos =(Pane)root.getChildren().get(7);
		power2=h2.getpImage();
		power2.addEventHandler(MouseEvent.MOUSE_CLICKED, playcard);
		power2.addEventHandler(MouseEvent.MOUSE_EXITED, onSelect2);
		power2pos.getChildren().add(power2);
		hp2 = (Label)root.getChildren().get(11);
		hp2.textProperty().bind(model.getOpponent().getHhpp().asString());
		currmana2 = (Label)root.getChildren().get(14);
		currmana2.textProperty().bind(model.getOpponent().getCurrmana().asString());
		maxmana2 = (Label)root.getChildren().get(15);
		maxmana2.textProperty().bind(model.getOpponent().getTotalmana().asString());
			for (Card c : h2.getHand()){
			Cardlol x2 = new Cardlol(c);
			oppHand.getChildren().add(x2.getBackView());
		}   
		
		currCardsleft.setText(h1.getDeck().size()+"");
        oppCardsleft.setText(h2.getDeck().size()+"");
		
	
		
	}
	

	public void endTurn (ActionEvent e 	)  {
		
		cardburnedpos.getChildren().clear();
		burnedlabel.setText("");
		exceptionLabel.setText("");

		try {
			model.endTurn();
			
		} catch (FullHandException e1) {
			exceptionLabel.setText(e1.getMessage());
			Cardlol burned = new Cardlol(e1.getBurned());
			cardburnedpos.getChildren().add(burned);
			burnedlabel.setText("Burned");
			
		} catch (CloneNotSupportedException e1) {
			exceptionLabel.setText(e1.getMessage());
		}
		 finally {
			 h1= model.getCurrentHero(); h2 =model.getOpponent(); // updataing h1 and h2 refrences
				// changing images
				hero1 = h1.gethImage();
				hero1pos.getChildren().add(hero1);
				power1=h1.getpImage();
				power1pos.getChildren().add(power1);
				hero2=h2.gethImage();
				hero2pos.getChildren().add(hero2);
				power2=h2.getpImage();
				power2pos.getChildren().add(power2);
				// changing data of labels
				hp1.textProperty().bind(model.getCurrentHero().getHhpp().asString());
				currmana1.textProperty().bind(model.getCurrentHero().getCurrmana().asString());
				maxmana1.textProperty().bind(model.getCurrentHero().getTotalmana().asString());
				hp2.textProperty().bind(model.getOpponent().getHhpp().asString());
				currmana2.textProperty().bind(model.getOpponent().getCurrmana().asString());
				maxmana2.textProperty().bind(model.getOpponent().getTotalmana().asString());
				currCardsleft.setText(h1.getDeck().size()+"");
	            oppCardsleft.setText(h2.getDeck().size()+"");
				//switching hands
				currHand.getChildren().clear();
				oppHand.getChildren().clear();
				for (Card c : h1.getHand()){
					Cardlol x1 = new Cardlol(c);			
					x1.addEventHandler(MouseEvent.MOUSE_ENTERED, onSelect1);
					x1.addEventHandler(MouseEvent.MOUSE_EXITED, onSelect2);
					x1.addEventHandler(MouseEvent.MOUSE_CLICKED, playcard);
					currHand.getChildren().add(x1);
				} 
				
				for (Card c : h2.getHand()){
					Cardlol x2 = new Cardlol(c);
					oppHand.getChildren().add(x2.getBackView());
				}   
				//switching Fields 
					currField.getChildren().clear();
					oppField.getChildren().clear();
					for (Minion c : h1.getField()){
						Cardlol x1 = new Cardlol((Card)c);
						x1.setPlayed(true);
						x1.addEventHandler(MouseEvent.MOUSE_ENTERED, onSelect1);
						x1.addEventHandler(MouseEvent.MOUSE_EXITED, onSelect2);
						x1.addEventHandler(MouseEvent.MOUSE_CLICKED, playcard);
						currField.getChildren().add(x1);
					} 
					for (Minion c : h2.getField()){
						Cardlol x1 = new Cardlol((Card)c);
						x1.setPlayed(true);
						x1.addEventHandler(MouseEvent.MOUSE_ENTERED, onSelect1);
						x1.addEventHandler(MouseEvent.MOUSE_EXITED, onSelect2);
						x1.addEventHandler(MouseEvent.MOUSE_CLICKED,playcard);
						oppField.getChildren().add(x1);
					} 
				
			 selected = null ;
			 if (h1==p1.getH()){
		            player1Label.setText("Player1 " + h1.getName());
		            player2Label.setText("Player2 " + h2.getName());    }
		        else{
		            player2Label.setText("Player1 " + h2.getName());
		            player1Label.setText("Player2 " + h1.getName()); }
				
		 }// end of finally
	
		
	}// end of method 


	public void CloseGame (ActionEvent e) {
		Platform.exit();
	}

	@Override
	public void onGameOver() {
		if(p1.getH().getCurrentHP()<=0) {
			winboi.setText("Player 2 wins!!");
		}
		else if(p2.getH().getCurrentHP()<=0) {
			winboi.setText("Player 1 wins!!");
		}
		winboi.setVisible(true);
		winner.setVisible(true);
		CloseGame.setDisable(false);
		CloseGame.setVisible(true);
	}

	
	public static void main(String[] args) throws Exception {
		
	
		
	}


	
}		// end of class
