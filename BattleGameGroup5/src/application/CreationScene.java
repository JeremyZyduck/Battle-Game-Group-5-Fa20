package application;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class CreationScene extends SceneManager {
  // Constants.
  // Name of the scene.
  public final static String TITLE = "Battle Game - Character Creation";

  // Text fields to hold name, health, strength, defense, and cost of character.
  private TextField mNameField;
  private TextField mHealthField;
  private TextField mStrengthField;
  private TextField mDefenseField;
  private TextField mCostField;
  // Combo boxes to select appearance, main attack, and special attack.
  private ComboBox<String> mAppearanceBox;
  private ComboBox<String> mMainAttackBox;
  private ComboBox<String> mSpecialAttackBox;

  /**
   * Constructs the creation scene.
   */
  public CreationScene(Stage stage, DatabaseManager dataMan) {
    super(stage, TITLE, dataMan);
  }

  /**
   * Builds the creation scene and returns it.
   */
  @Override
  protected Scene createScene() {
    // Keeps track of the position.
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

    // prompt for character strength
    Label strengthLabel = new Label("Input character strength value:");
    setLabelToDefault(strengthLabel, position++);
    mStrengthField = new TextField();
    setFieldNextToLabel(mStrengthField, strengthLabel);
    setTextFieldListeners(mStrengthField);
    setTextFieldOnlyNumbers(mStrengthField);

    // prompt for character defense
    Label defenseLabel = new Label("Input character defense value:");
    setLabelToDefault(defenseLabel, position++);
    mDefenseField = new TextField();
    setFieldNextToLabel(mDefenseField, defenseLabel);
    setTextFieldListeners(mDefenseField);
    setTextFieldOnlyNumbers(mDefenseField);

    // prompt for character appearance
    Label appearanceLabel = new Label("Select character idle:");
    setLabelToDefault(appearanceLabel, position++);
    mAppearanceBox = new ComboBox<String>();
    setFieldNextToLabel(mAppearanceBox, appearanceLabel);

    // prompt for primary attack skill
    Label mainAttackLabel = new Label("Select primary attack skill:");
    setLabelToDefault(mainAttackLabel, position++);
    mMainAttackBox = new ComboBox<String>();
    setFieldNextToLabel(mMainAttackBox, mainAttackLabel);

    // prompt for secondary attack skill
    Label specialAttackLabel = new Label("Select secondary attack skill:");
    setLabelToDefault(specialAttackLabel, position++);
    mSpecialAttackBox = new ComboBox<String>();
    setFieldNextToLabel(mSpecialAttackBox, specialAttackLabel);

    // Prompt to create a new skill
    Label newSkillLabel = new Label("Create new skill/idle: ");
    setLabelToDefault(newSkillLabel, position++);
    Button newSkillButton = new Button("New Skill");
    setFieldNextToLabel(newSkillButton, newSkillLabel);
    // When the button is pressed, open a skill creation menu.
    newSkillButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent arg0) {
        swapToLink(NewSkillScene.TITLE);
      }
    });

    // Prompt for character cost
    Label costLabel = new Label("Input character cost:");
    setLabelToDefault(costLabel, position++);
    mCostField = new TextField();
    setFieldNextToLabel(mCostField, costLabel);
    setTextFieldListeners(mCostField);
    setTextFieldOnlyNumbers(mCostField);

    // submit and clear buttons.
    Button clearButton = new Button("Clear");
    clearButton.setStyle("-fx-font-size:15");
    clearButton.setFont(new Font(40));
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
    submitButton.setStyle("-fx-font-size:15");
    submitButton.setFont(new Font(40));
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
    Group g = new Group(nameLabel, mNameField, healthLabel, mHealthField, strengthLabel, mStrengthField, defenseLabel,
        mDefenseField, appearanceLabel, mAppearanceBox, newSkillLabel, newSkillButton, mainAttackLabel, mMainAttackBox,
        specialAttackLabel, mSpecialAttackBox, costLabel, mCostField, submitButton, clearButton);

    return new Scene(g, 500, (int) (ELEMENT_SPACE * position));
  }
  
  /**
   * When the scene loads, pulls from the database to populate selection fields.
   */
  @Override
  protected void onLoad() {
    // Combo boxes.
    // Clear the lists.
    mAppearanceBox.getItems().clear();
    mMainAttackBox.getItems().clear();
    mSpecialAttackBox.getItems().clear();
    // Populate from database.
    for (Skill i : mDatabaseManager.getIdleSkills()) {
      mAppearanceBox.getItems().add(i.getName());
    }
    for (Skill i : mDatabaseManager.getNormalSkills()) {
      mMainAttackBox.getItems().add(i.getName());
    }
    for (Skill i : mDatabaseManager.getSpecialSkills()) {
      mSpecialAttackBox.getItems().add(i.getName());
    }
  }

  /**
   * Clears the info in the text fields and combo boxes.
   */
  private void clearInfo() {
    // Reset info inside.
    mNameField.setText("");
    mHealthField.setText("");
    mStrengthField.setText("");
    mDefenseField.setText("");
    mCostField.setText("");
    mAppearanceBox.getSelectionModel().clearSelection();
    mMainAttackBox.getSelectionModel().clearSelection();
    mSpecialAttackBox.getSelectionModel().clearSelection();

    // Reset background colors.
    setTextFieldBackgroundDefault(mNameField);
    setTextFieldBackgroundDefault(mHealthField);
    setTextFieldBackgroundDefault(mStrengthField);
    setTextFieldBackgroundDefault(mDefenseField);
    setTextFieldBackgroundDefault(mCostField);
  }

  /**
   * Tries to submit the character's info. If some of the entries are invalid,
   * does not create a new character.
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
    // Test if the character name is unique
    for (Character i : mDatabaseManager.getCharacters()) {
      if (i.getName().equals(charaName)) {
        validData = false;
        setTextFieldBackgroundRed(mNameField);
      }
    }
    // Test if the entered health is an integer.
    String healthStr = mHealthField.getText();
    if (!validateNumberString(healthStr)) {
      validData = false;
      setTextFieldBackgroundRed(mHealthField);
    }
    // Test if the entered strength is an integer.
    String strengthStr = mStrengthField.getText();
    if (!validateNumberString(strengthStr)) {
      validData = false;
      setTextFieldBackgroundRed(mStrengthField);
    }
    // Test if the entered defense is an integer.
    String defenseStr = mDefenseField.getText();
    if (!validateNumberString(defenseStr)) {
      validData = false;
      setTextFieldBackgroundRed(mDefenseField);
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
      // Adds character to mCharacters arrayList
      Character character = new Character();
      character.setName(charaName);
      character.setHealth(Integer.parseInt(healthStr));

      character.setAttackName(mainAttackStr);
      character.setSkillName(specialAttackStr);
      character.setCost(Integer.parseInt(costStr));
      // TODO Character Image
      
      // TEMP TODO
      character.setImage(appearanceStr);
      character.setImageAttack(mainAttackStr);
      character.setImageSkill(specialAttackStr);
      // Upload character.
      mDatabaseManager.uploadCharacter(character);

      // Clears all fields
      mNameField.clear();
      mHealthField.clear();
      mDefenseField.clear();
      mStrengthField.clear();
      mCostField.clear();
      mMainAttackBox.getSelectionModel().clearSelection();
      mSpecialAttackBox.getSelectionModel().clearSelection();
      mAppearanceBox.getSelectionModel().clearSelection();

      // Outputs information for all characters stored in the mCharacters arrayList
      for (Character i : mDatabaseManager.getCharacters()) {
        i.printAll();
      }
    }
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
}
