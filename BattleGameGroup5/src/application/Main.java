package application;
	
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application  {

  @Override
  public void start(Stage primaryStage) 
    throws Exception {
	if(1 == 1){
		displayCreationMenu(primaryStage);
	}
  }

  public static void main(String[] args) {
    Application.launch(args);
  }
  
  /**
   * ALGORYTHM displayCreationMenu(Stage stage)
   * Displays the character creation menu onto the stage passed into the method.
   * 
   * @param stage - the stage that will display the character creation menu
   */
  public void displayCreationMenu(Stage stage)
  {
	  //determines the vertical space between sets of relevant elements
	  int elementSpace = 40;
	  
	  //sets the window's title
	  stage.setTitle("Battle Game - Character Creation");

	  //prompt for character name
	  Label nameLabel = new Label("Input character name:");
	  nameLabel.setFont(new Font("Arial", 20));
	  nameLabel.setPrefWidth(280);
	  nameLabel.setLayoutX(10);
	  TextField nameField = new TextField ();
	  nameField.setLayoutX(nameLabel.getPrefWidth());
	  
	  //prompt for character health
	  Label healthLabel = new Label("Input character health value:");
	  healthLabel.setFont(new Font("Arial", 20));
	  healthLabel.setPrefWidth(280);
	  healthLabel.setLayoutX(nameLabel.getLayoutX());
	  healthLabel.setLayoutY(elementSpace * 1);
	  TextField healthField = new TextField ();
	  healthField.setLayoutY(elementSpace);
	  healthField.setLayoutX(healthLabel.getPrefWidth());
	  
	  //prompt for character appearance
	  Label appearanceLabel = new Label("Select character appearance:");
	  appearanceLabel.setFont(new Font("Arial", 20));
	  appearanceLabel.setPrefWidth(280);
	  appearanceLabel.setLayoutX(nameLabel.getLayoutX());
	  appearanceLabel.setLayoutY(elementSpace * 2);
	  ComboBox<String> appearanceBox = new ComboBox<String> ();
	  //TEMP
	  	appearanceBox.getItems().add("Appearance 1");
	  	appearanceBox.getItems().add("Appearance 2");
	  	appearanceBox.getItems().add("Appearance 3");
	  //END TEMP
	  appearanceBox.setLayoutY(elementSpace * 2);
	  appearanceBox.setLayoutX(healthLabel.getPrefWidth());
	  
	  //prompt for primary attack skill
	  Label mainAttackLabel = new Label("Select primary attack skill:");
	  mainAttackLabel.setFont(new Font("Arial", 20));
	  mainAttackLabel.setPrefWidth(280);
	  mainAttackLabel.setLayoutX(nameLabel.getLayoutX());
	  mainAttackLabel.setLayoutY(elementSpace * 3);
	  ComboBox<String> mainAttackBox = new ComboBox<String> ();
	  //TEMP
	  	mainAttackBox.getItems().add("Main Attack 1");
	  	mainAttackBox.getItems().add("Main Attack 2");
	  	mainAttackBox.getItems().add("Main Attack 3");
	  //END TEMP
	  mainAttackBox.setLayoutY(elementSpace * 3);
	  mainAttackBox.setLayoutX(healthLabel.getPrefWidth());
	  
	//prompt for secondary attack skill
	  Label specialAttackLabel = new Label("Select secondary attack skill:");
	  specialAttackLabel.setFont(new Font("Arial", 20));
	  specialAttackLabel.setPrefWidth(280);
	  specialAttackLabel.setLayoutX(nameLabel.getLayoutX());
	  specialAttackLabel.setLayoutY(elementSpace * 4);
	  ComboBox<String> specialAttackBox = new ComboBox<String> ();
	  //TEMP
	  	specialAttackBox.getItems().add("Special Attack 1");
	  	specialAttackBox.getItems().add("Special Attack 2");
	  	specialAttackBox.getItems().add("Special Attack 3");
	  //END TEMP
	  specialAttackBox.setLayoutY(elementSpace * 4);
	  specialAttackBox.setLayoutX(healthLabel.getPrefWidth());
	  
	  //submit and clear buttons.
	  Button clearButton = new Button("Clear");
	  clearButton.setPrefHeight(30);
	  clearButton.setPrefWidth(100);
	  clearButton.setLayoutX(10);
	  clearButton.setLayoutY(200);
	  Button submitButton = new Button("Submit");
	  submitButton.setPrefHeight(clearButton.getPrefHeight());
	  submitButton.setPrefWidth(clearButton.getPrefWidth());
	  submitButton.setLayoutX(clearButton.getLayoutX() * 2 + submitButton.getPrefWidth());
	  submitButton.setLayoutY(clearButton.getLayoutY());
	
	  //creates and adds elements to the group
	  Group g = new Group(nameLabel,
			  			  nameField,
			  			  healthLabel,
			  			  healthField,
			  			  appearanceLabel,
			  			  appearanceBox,
			  			  mainAttackLabel,
			  			  mainAttackBox,
			  			  specialAttackLabel,
			  			  specialAttackBox,
			  			  submitButton,
			  			  clearButton);
	
	  //creates and displays the scene
	  Scene characterCreationScene = new Scene(g, 500, 500);
	  stage.setScene(characterCreationScene);
	  stage.show();
  }
}
