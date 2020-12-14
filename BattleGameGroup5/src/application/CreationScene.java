package application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;

/**
 * Represents the scene used to create new characters.
 * Singleton class.
 * 
 * @author Wyatt
 *
 */
public class CreationScene extends SceneManager {
  // Constants.
  // Name of the scene.
  private final static String TITLE = "Battle Game - Character Creation";

  // Singleton
  private static CreationScene instance = null;

  // Text fields to hold name, health, strength, defense, and cost of character.
  private TextField mNameField;
  private TextField mHealthField;
  private TextField mStrengthField;
  private TextField mDefenseField;
  private TextField mCostField;
  // Labels to see the name of the idle, normal, and special skills.
  private Label mIdleLabel;
  private Label mNormalLabel;
  private Label mSpecialLabel;

  // Character being created.
  private Character mCharacter;

  /**
   * Constructs the CreationScene.
   */
  private CreationScene() {
    super(TITLE);
    mCharacter = new Character();
  }

  /**
   * Gets the singleton instance.
   * 
   * @return CreationScene singleton instance.
   */
  public static CreationScene getInstance() {
    if (instance == null) {
      instance = new CreationScene();
    }
    return instance;
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
    setLabelToDefault(appearanceLabel, position);
    mIdleLabel = new Label("None");
    setLabelToDefault(mIdleLabel, position++);
    mIdleLabel.setPrefWidth(140);
    setFieldNextToLabel(mIdleLabel, appearanceLabel);
    Button editIdleButton = new Button("Edit");
    setFieldNextToLabel(editIdleButton, mIdleLabel);
    editIdleButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        NewSkillScene.getInstance().setSkillType(SkillType.IDLE);
        NewSkillScene.getInstance().swapToScene();
        if (mCharacter.getIdleSkill() != null) {
          NewSkillScene.getInstance().setSkillData(mCharacter.getIdleSkill());
        }
      }
    });

    // prompt for primary attack skill
    Label mainAttackLabel = new Label("Select primary attack skill:");
    setLabelToDefault(mainAttackLabel, position);
    mNormalLabel = new Label("None");
    setLabelToDefault(mNormalLabel, position++);
    mNormalLabel.setPrefWidth(140);
    setFieldNextToLabel(mNormalLabel, mainAttackLabel);
    Button editAttackButton = new Button("Edit");
    setFieldNextToLabel(editAttackButton, mNormalLabel);
    editAttackButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        NewSkillScene.getInstance().setSkillType(SkillType.NORMAL);
        NewSkillScene.getInstance().swapToScene();
        if (mCharacter.getNormalSkill() != null) {
          NewSkillScene.getInstance().setSkillData(mCharacter.getNormalSkill());
        }
      }
    });

    // prompt for secondary attack skill
    Label specialAttackLabel = new Label("Select secondary attack skill:");
    setLabelToDefault(specialAttackLabel, position);
    mSpecialLabel = new Label("None");
    setLabelToDefault(mSpecialLabel, position++);
    mSpecialLabel.setPrefWidth(140);
    setFieldNextToLabel(mSpecialLabel, specialAttackLabel);
    Button editSpecialButton = new Button("Edit");
    setFieldNextToLabel(editSpecialButton, mSpecialLabel);
    editSpecialButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        NewSkillScene.getInstance().setSkillType(SkillType.SPECIAL);
        NewSkillScene.getInstance().swapToScene();
        if (mCharacter.getSpecialSkill() != null) {
          NewSkillScene.getInstance().setSkillData(mCharacter.getSpecialSkill());
        }
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
    clearButton.setOnAction(e -> clearInfo());
    
    Button submitButton = new Button("Submit");
    submitButton.setStyle("-fx-font-size:15");
    submitButton.setFont(new Font(40));
    submitButton.setPrefHeight(clearButton.getPrefHeight());
    submitButton.setPrefWidth(clearButton.getPrefWidth());
    submitButton.setLayoutX(clearButton.getLayoutX() * 2 + submitButton.getPrefWidth());
    submitButton.setLayoutY(clearButton.getLayoutY());
    // When the button is pressed, submit the character info.
    submitButton.setOnAction(e -> submitCharacterInfo());

    Button backButton = new Button("Back");
    backButton.setStyle("-fx-font-size:15");
    backButton.setFont(new Font(40));
    backButton.setPrefHeight(clearButton.getPrefHeight());
    backButton.setPrefWidth(clearButton.getPrefWidth());
    backButton.setLayoutX(submitButton.getLayoutX() + backButton.getPrefWidth() * 2);
    backButton.setLayoutY(clearButton.getLayoutY());
    // When the button is pressed, return to the character list.
    backButton.setOnAction(e -> CharacterListScene.getInstance().swapToScene());

    // creates and adds elements to the group
    Group g = new Group(nameLabel, mNameField, healthLabel, mHealthField, strengthLabel, mStrengthField, defenseLabel,
        mDefenseField, appearanceLabel, mIdleLabel, mainAttackLabel, mNormalLabel, specialAttackLabel, mSpecialLabel,
        costLabel, mCostField, submitButton, clearButton, backButton, editIdleButton, editAttackButton,
        editSpecialButton);

    return new Scene(g, 500, (int) (ELEMENT_SPACE * position));
  }

  /**
   * Does things on loading into the new scene.
   * Update the skills labels to be the character's skills.
   */
  @Override
  protected void onLoad() {
    // Idle skill
    String idleName = "None";
    if (mCharacter.getIdleSkill() != null) {
      idleName = mCharacter.getIdleSkill().getName();
      if (idleName.equals("")) {
        idleName = "None";
      }
    }
    mIdleLabel.setText(idleName);

    // Normal skill
    String normalName = "None";
    if (mCharacter.getNormalSkill() != null) {
      normalName = mCharacter.getNormalSkill().getName();
      if (normalName.equals("")) {
        normalName = "None";
      }
    }
    mNormalLabel.setText(normalName);

    // Special skill
    String specialName = "None";
    if (mCharacter.getSpecialSkill() != null) {
      specialName = mCharacter.getSpecialSkill().getName();
      if (specialName.equals("")) {
        specialName = "None";
      }
    }
    mSpecialLabel.setText(specialName);
  }

  /**
   * Returns the current character being created.
   * 
   * @return Character.
   */
  public Character getCharacter() {
    return mCharacter;
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

    // Check if skills were created.
    if (mCharacter.getIdleSkill() == null || mCharacter.getNormalSkill() == null
        || mCharacter.getSpecialSkill() == null) {
      validData = false;
    }

    
    if (validData) {
      // Adds character to mCharacters arrayList
      mCharacter.setName(charaName);
      mCharacter.setHealth(Integer.parseInt(healthStr));
      mCharacter.setStrength(Integer.parseInt(strengthStr));
      mCharacter.setDefense(Integer.parseInt(defenseStr));
      mCharacter.setCost(Integer.parseInt(costStr));

      boolean wasSuccess = true;
      // If the character is a new character.
      if (mCharacter.getKey() == -1) {
        wasSuccess = submitNewCharacterInfo();
      }
      // If the character is an existing character.
      else {
        submitExistingCharacterInfo();
      }

      // If the character was successfully uploaded, reset things.
      if (wasSuccess) {
        // Reset the character.
        mCharacter = new Character();
        // Call onLoad to get rid of the skills.
        onLoad();

        // Clears all fields
        mNameField.clear();
        mHealthField.clear();
        mDefenseField.clear();
        mStrengthField.clear();
        mCostField.clear();

        // Go back to the character list scene.
        CharacterListScene.getInstance().swapToScene();

        // Outputs information for all characters stored in the mCharacters arrayList
        // for (Character i : mDatabaseManager.getCharacters()) {
        // i.printAll();
        // }
      }
    }
  }

  /**
   * Submit new character info to the database.
   * Helper function for submitCharacterInfo.
   */
  private boolean submitNewCharacterInfo() {
    boolean isValid = true;

    String charaName = mNameField.getText();
    // Test if the character name is unique
    for (Character i : mDatabaseManager.getCharacters()) {
      if (i.getName().equals(charaName)) {
        isValid = false;
        setTextFieldBackgroundRed(mNameField);
      }
    }

    if (isValid) {
      // Upload character.
      mDatabaseManager.uploadCharacter(mCharacter);
    }

    return isValid;
  }

  /**
   * Submit info for existing character for update.
   * Helper function for submitCharacterInfo.
   */
  private void submitExistingCharacterInfo() {
    // Update character.
    mDatabaseManager.updateCharacter(mCharacter);
  }

  /**
   * Sets the current character to the given character. Updates fields to hold the
   * character's info. Used when editing an existing character. Must be called
   * before the scene is loaded.
   * 
   * @param character - Character being edited.
   */
  public void setCharacterInfo(Character character) {
    mCharacter = character;

    mNameField.setText(mCharacter.getName());
    mHealthField.setText(String.valueOf(mCharacter.getHealth()));
    mStrengthField.setText(String.valueOf(mCharacter.getStrength()));
    mDefenseField.setText(String.valueOf(mCharacter.getDefense()));
    mCostField.setText(String.valueOf(mCharacter.getCost()));
  }

  /**
   * Creates a new character and sets it as the current character. Used when
   * creating a new character. Must be called before the scene is loaded.
   */
  public void setNewCharacter() {
    mCharacter = new Character();

    clearInfo();
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
