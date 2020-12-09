package application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class DatabaseLoginScene extends SceneManager {
  // Constants.
  // Name of the scene.
  public final static String TITLE = "Battle Game - Character Creation - Database Login";

  // Text fields for database credentials scene.
  private TextField mDataUserField;
  private PasswordField mDataPassField;

  /**
   * Constructs a DatabaseLoginScene.
   * 
   * @param stage   Stage the scene will be on.
   * @param dataMan Reference to the database manager.
   */
  public DatabaseLoginScene(Stage stage, DatabaseManager dataMan) {
    super(stage, TITLE, dataMan);
  }

  /**
   * Creates the database login scene.
   */
  @Override
  protected Scene createScene() {
    int position = 0;

    Label invalidInfoLabel = new Label("INVALID CREDENTIALS");
    invalidInfoLabel.setTextFill(Color.web("#BB6666"));
    invalidInfoLabel.setVisible(false);
    setLabelToDefault(invalidInfoLabel, position++);

    // Prompt for user name
    Label userLabel = new Label("Database Username:");
    setLabelToDefault(userLabel, position++);
    mDataUserField = new TextField();
    setFieldNextToLabel(mDataUserField, userLabel);
    setTextFieldListeners(mDataUserField);

    // Prompt for password
    Label passLabel = new Label("Database Password:");
    setLabelToDefault(passLabel, position++);
    mDataPassField = new PasswordField();
    setFieldNextToLabel(mDataPassField, passLabel);
    setTextFieldListeners(mDataPassField);

    // Login button.
    Button submitButton = new Button("Login");
    submitButton.setStyle("-fx-font-size:15");
    submitButton.setFont(new Font(40));
    submitButton.setPrefHeight(30);
    submitButton.setPrefWidth(100);
    submitButton.setLayoutY(ELEMENT_SPACE * position++);
    // When the button is pressed, submit the character info.
    submitButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        if (mDatabaseManager.connectToDatabase(mDataUserField.getText(), mDataPassField.getText())) {
          invalidInfoLabel.setVisible(false);
          swapToLink(CharacterListScene.TITLE);
        } else {
          invalidInfoLabel.setVisible(true);
          checkValidDatabseAccountEntry();
        }
      }
    });

    // Creates and adds elements to the group
    Group g = new Group(invalidInfoLabel, userLabel, mDataUserField, passLabel, mDataPassField, submitButton);

    return new Scene(g, 500, (int) (ELEMENT_SPACE * position));
  }

  /**
   * Sets the database account info fields red if nothing was typed in them.
   */
  private void checkValidDatabseAccountEntry() {
    if (mDataUserField.getText().equals("")) {
      setTextFieldBackgroundRed(mDataUserField);
    }
    if (mDataPassField.getText().equals("")) {
      setTextFieldBackgroundRed(mDataPassField);
    }
  }
}
