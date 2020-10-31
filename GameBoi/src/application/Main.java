package application;
	
import java.net.URL;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;




public class Main extends Application  {



	
	
	
	
	Stage window ;
	Scene scene1 ;
	MediaPlayer a ;
	
	
	
	@Override
	public void start(Stage primaryStage) {
		try {
			window = primaryStage;
			
			AnchorPane root1 = FXMLLoader.load(getClass().getResource("/scenes/menu.fxml"));
			scene1 = new Scene(root1);
			window.setTitle("HearthStone");
			window.setScene(scene1);	
			
			URL resource = getClass().getResource("Hearthstone Soundtrack - Main Title.mp3");
	        a =new MediaPlayer(new Media(resource.toString()));
	        a.setOnReady(new Runnable() {
	            @Override
	            public void run() {
	            	a.setCycleCount(MediaPlayer.INDEFINITE);
	            	a.setAutoPlay(true);
	                a.autoPlayProperty();
	                
	            }
	        });
			
			
			
			window.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	

	
	
	public static void main(String[] args) {
		launch(args);
	}
	
	
}
