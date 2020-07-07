//Group 4
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


public class UserInterface extends Application
{
   public void start(Stage primaryStage)
   {
      //create a DrawPane object. See DrawPane.java for details.
      UserPane gui = new UserPane();
   
      //put gui on top of the rootPane
      StackPane rootPane = new StackPane();
      rootPane.getChildren().add(gui);
   
      // Create a scene and place rootPane in the stage
      Scene scene = new Scene(rootPane, 1000, 800);
      primaryStage.setTitle("Stats GUI"); 
      primaryStage.setScene(scene); // Place the scene in the stage
      primaryStage.show(); // Display the stage
      
   }
   public static void main(String[] args)
   {
      Application.launch(args);
   }
}
