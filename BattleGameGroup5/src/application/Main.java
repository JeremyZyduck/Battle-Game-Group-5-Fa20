package application;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Main extends Application {
  // Constants
  //determines the vertical space between sets of relevant elements
  private static final int ELEMENT_SPACE = 40;
  
  // Text fields to hold name, health, and cost of character.
  private TextField mNameField;
  private TextField mHealthField;
  private TextField mCostField;
  // Combo boxes to select appearance, main attack, and special attack.
  private ComboBox<String> mAppearanceBox;
  private ComboBox<String> mMainAttackBox;
  private ComboBox<String> mSpecialAttackBox;

  @Override
  public void start(Stage primaryStage) throws Exception {
    if (1 == 1) {
      connectToDatabase();
      displayCreationMenu(primaryStage);
    }
  }

  public static void main(String[] args) {
    Application.launch(args);
  }

  /**
   * ALGORYTHM displayCreationMenu(Stage stage) Displays the character creation
   * menu onto the stage passed into the method.
   * 
   * @param stage - the stage that will display the character creation menu
   */
  public void displayCreationMenu(Stage stage) {
    // sets the window's title
    stage.setTitle("Battle Game - Character Creation");

    int position = 0;
    
    // prompt for character name
    Label nameLabel = new Label("Input character name:");
    setLabelToDefault(nameLabel, position++);
    mNameField = new TextField();
    setFieldNextToLabel(mNameField, nameLabel);
    setTextFieldListeners(mNameField);

    // prompt for character health
    Label healthLabel = new Label("Input character health value:");
    setLabelToDefault(healthLabel, position++);
    mHealthField = new TextField();
    setFieldNextToLabel(mHealthField, healthLabel);
    setTextFieldListeners(mHealthField);
    setTextFieldOnlyNumbers(mHealthField);

    // prompt for character appearance
    Label appearanceLabel = new Label("Select character appearance:");
    setLabelToDefault(appearanceLabel, position++);
    mAppearanceBox = new ComboBox<String>();
    // TEMP
    mAppearanceBox.getItems().add("Appearance 1");
    mAppearanceBox.getItems().add("Appearance 2");
    mAppearanceBox.getItems().add("Appearance 3");
    // END TEMP
    setFieldNextToLabel(mAppearanceBox, appearanceLabel);
    
    // Prompt for uploading an image to add to the data and appearance box.
    Label imageLabel = new Label("Add new appearance: ");
    setLabelToDefault(imageLabel, position++);
    Button imageUploadButton = new Button("Upload");
    setFieldNextToLabel(imageUploadButton, imageLabel);
    // When the button is pressed open file explorer to select images.
    imageUploadButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent arg0) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Upload Image");
        List<File> list = fileChooser.showOpenMultipleDialog(stage);
        if (list != null) {
          for (File file : list) {
            // TODO Upload file to database.
            System.out.println(file.getName());
          }
        }
      }
    });

    // prompt for primary attack skill
    Label mainAttackLabel = new Label("Select primary attack skill:");
    setLabelToDefault(mainAttackLabel, position++);
    mMainAttackBox = new ComboBox<String>();
    // TEMP
    mMainAttackBox.getItems().add("Main Attack 1");
    mMainAttackBox.getItems().add("Main Attack 2");
    mMainAttackBox.getItems().add("Main Attack 3");
    // END TEMP
    setFieldNextToLabel(mMainAttackBox, mainAttackLabel);

    // prompt for secondary attack skill
    Label specialAttackLabel = new Label("Select secondary attack skill:");
    setLabelToDefault(specialAttackLabel, position++);
    mSpecialAttackBox = new ComboBox<String>();
    // TEMP
    mSpecialAttackBox.getItems().add("Special Attack 1");
    mSpecialAttackBox.getItems().add("Special Attack 2");
    mSpecialAttackBox.getItems().add("Special Attack 3");
    // END TEMP
    setFieldNextToLabel(mSpecialAttackBox, specialAttackLabel);

    // Prompt for character cost
    Label costLabel = new Label("Input character cost:");
    setLabelToDefault(costLabel, position++);
    mCostField = new TextField();
    setFieldNextToLabel(mCostField, costLabel);
    setTextFieldListeners(mCostField);
    setTextFieldOnlyNumbers(mCostField);

    // submit and clear buttons.
    Button clearButton = new Button("Clear");
    clearButton.setPrefHeight(30);
    clearButton.setPrefWidth(100);
    clearButton.setLayoutX(10);
    clearButton.setLayoutY(ELEMENT_SPACE * position++);
    // When the button is pressed, clear the info.
    clearButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent arg0) {
        clearInfo();
      }
    });
    Button submitButton = new Button("Submit");
    submitButton.setPrefHeight(clearButton.getPrefHeight());
    submitButton.setPrefWidth(clearButton.getPrefWidth());
    submitButton.setLayoutX(clearButton.getLayoutX() * 2 + submitButton.getPrefWidth());
    submitButton.setLayoutY(clearButton.getLayoutY());
    // When the button is pressed, submit the character info.
    submitButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent arg0) {
        submitCharacterInfo();
      }
    });

    // creates and adds elements to the group
    Group g = new Group(nameLabel, mNameField, healthLabel, mHealthField, appearanceLabel, mAppearanceBox,
        imageLabel, imageUploadButton,
        mainAttackLabel, mMainAttackBox, specialAttackLabel, mSpecialAttackBox, costLabel, mCostField,
        submitButton, clearButton);

    // creates and displays the scene
    Scene characterCreationScene = new Scene(g, 500, 500);
    stage.setScene(characterCreationScene);
    stage.show();
  }
  
  /**
   * Sets the given label's font, width, and layout to the determined defaults.
   * Distance down on page is determined by position.
   * 
   * @param label Label to set to defaults.
   * @param position Position from the top the label will be placed.
   */
  private void setLabelToDefault(Label label, int position) {
    label.setFont(new Font("Arial", 20));
    label.setPrefWidth(280);
    label.setLayoutX(10);
    label.setLayoutY(ELEMENT_SPACE * position);
  }
  /**
   * Places the given field to the right of the label.
   * 
   * @param field Field to be placed right of the field.
   * @param label Label field will be placed right of.
   */
  private void setFieldNextToLabel(Node field, Label label) {
    field.setLayoutY(label.getLayoutY());
    field.setLayoutX(label.getPrefWidth());
  }

  /**
   * Clears the info in the text fields and combo boxes.
   */
  private void clearInfo() {
    // Reset info inside.
    mNameField.setText("");
    mHealthField.setText("");
    mCostField.setText("");
    mAppearanceBox.getSelectionModel().clearSelection();
    mMainAttackBox.getSelectionModel().clearSelection();
    mSpecialAttackBox.getSelectionModel().clearSelection();
    
    // Reset background colors.
    setTextFieldBackgroundDefault(mNameField);
    setTextFieldBackgroundDefault(mHealthField);
    setTextFieldBackgroundDefault(mCostField);
  }

  /**
   * Tries to submit the character's info.
   * If some of the entries are invalid, does not create a new character.
   */
  private void submitCharacterInfo() {
    // If the entered data is valid.
    boolean validData = true;

    // Test if the character name is valid.
    String charaName = mNameField.getText();
    if (charaName.equals("")) {
      validData = false;
      setTextFieldBackgroundRed(mNameField);
    }
    // Test if the entered health is an integer.
    String healthStr = mHealthField.getText();
    if (!validateNumberString(healthStr)) {
      validData = false;
      setTextFieldBackgroundRed(mHealthField);
    }
    // Test if the entered cost is an integer.
    String costStr = mCostField.getText();
    if (!validateNumberString(costStr)) {
      validData = false;
      setTextFieldBackgroundRed(mCostField);
    }

    // Check if an appearance was selected.
    String appearanceStr = mAppearanceBox.getSelectionModel().getSelectedItem();
    if (appearanceStr == null) {
      validData = false;
    }
    // Check if a main attack was selected.
    String mainAttackStr = mMainAttackBox.getSelectionModel().getSelectedItem();
    if (mainAttackStr == null) {
      validData = false;
    }
    // Check if a special attack was selected.
    String specialAttackStr = mSpecialAttackBox.getSelectionModel().getSelectedItem();
    if (specialAttackStr == null) {
      validData = false;
    }

    if (validData) {
      // TEMP
      System.out.println("CHARACTER CREATION UNIMPLEMENTED");
    }
  }

  /**
   * Sets the given field to change to the default background when it is changed.
   * 
   * @param field Field who the listener will be added to.
   */
  private void setTextFieldListeners(TextField field) {
    field.textProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        setTextFieldBackgroundDefault(field);
      }
    });
  }
  
  /**
   * Sets the given field to remove any non numeric values entered into it.
   * 
   * @param field Field who the listener will be added to.
   */
  private void setTextFieldOnlyNumbers(TextField field) {
    field.textProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        if (!newValue.matches("\\d*")) {
          field.setText(newValue.replaceAll("[^\\d]", ""));
        }
      }
    });
  }

  /**
   * Sets the background of the given field to red.
   * 
   * @param field TextField whose background to set red.
   */
  private void setTextFieldBackgroundRed(TextField field) {
    setTextFieldBackground(field, Paint.valueOf("AA7777").toString().substring(2));
  }
  /**
   * Sets the background of the given field to normal.
   * 
   * @param field TextField whose background to set normal.
   */
  private void setTextFieldBackgroundDefault(TextField field) {
    setTextFieldBackground(field, Paint.valueOf("FFFFFF").toString().substring(2));
  }
  /**
   * Sets the background of the given field to the given color.
   * 
   * @param field TextField whose background to set.
   * @param color Color to set the background to.
   */
  private void setTextFieldBackground(TextField field, String color) {
    field.setStyle("-fx-control-inner-background: #" + color);
  }


  /**
   * Returns true when the given string contains only an int.
   * 
   * @param numStr String to test if it contains an int.
   * @return boolean.
   */
  private boolean validateNumberString(String numStr) {
    try {
      Integer.parseInt(numStr);
    } catch (Exception e) {
      return false;
    }

    return true;
  }
  
  private void connectToDatabase() {
    /*
    try {
      Connection connect = DriverManager.getConnection(
          "jdbc:mysql://144.13.22.59:3306/feedback?user=[redacted]&password=[redacted]");
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("Failed");
    }
    */
  }
}
