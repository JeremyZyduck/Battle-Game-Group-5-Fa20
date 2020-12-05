package application;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NewSkillScene extends SceneManager {
  // Constants.
  // Name of the scene.
  public final static String TITLE = "Battle Game - Character Creation - New Skill";

  // Text fields for skill name and power
  private TextField mNameField;
  private TextField mPowerField;
  // Combo box for skill type.
  private ComboBox<SkillType> mTypeBox;
  
  // For uploading the image.
  private AnimationChooser mAnimChooser;

  /**
   * Constructs NewSkillScene.
   * 
   * @param stage   Stage the scene will be on.
   * @param dataMan Reference to the database manager.
   */
  public NewSkillScene(Stage stage, DatabaseManager dataMan) {
    super(stage, TITLE, dataMan);
    mAnimChooser = new AnimationChooser(stage); 
  }

  /**
   * Builds the new skill scene and returns it.
   */
  @Override
  protected Scene createScene() {
    int position = 0;

    // Label and text field for entering name.
    Label nameLabel = new Label("Skill name: ");
    setLabelToDefault(nameLabel, position++);
    mNameField = new TextField();
    setFieldNextToLabel(mNameField, nameLabel);
    setTextFieldListeners(mNameField);

    // Label and combo box for entering type.
    Label typeLabel = new Label("Skill type: ");
    setLabelToDefault(typeLabel, position++);
    mTypeBox = new ComboBox<SkillType>();
    for (SkillType val : SkillType.values()) {
      mTypeBox.getItems().add(val);
    }
    setFieldNextToLabel(mTypeBox, typeLabel);
    // Set listener to only show power text field when it is not an idle skill.
    Label powerLabel = new Label("Power: ");
    mTypeBox.valueProperty().addListener(new ChangeListener<SkillType>() {
      @Override
      public void changed(ObservableValue<? extends SkillType> observable, SkillType oldValue, SkillType newValue) {
        // Set visible when swapping off an idle skill.
        if (newValue != SkillType.IDLE && oldValue == SkillType.IDLE) {
          powerLabel.setVisible(true);
          mPowerField.setVisible(true);
        }
        // Set invisible when swapping to an idle skill.
        else if (newValue == SkillType.IDLE && oldValue != SkillType.IDLE) {
          powerLabel.setVisible(false);
          mPowerField.setText("0");
          mPowerField.setVisible(false);
        }
      }
    });

    // Label and text field for entering power.
    setLabelToDefault(powerLabel, position++);
    mPowerField = new TextField("0");
    setFieldNextToLabel(mPowerField, powerLabel);
    setTextFieldListeners(mPowerField);
    setTextFieldOnlyNumbers(mPowerField);
    powerLabel.setVisible(false);
    mPowerField.setVisible(false);
    
    // Prompt for uploading an image to add to the database and boxes that are to select images. 
    Label imageLabel = new Label("Upload skill idle: ");
    setLabelToDefault(imageLabel, position++); 
    Button imageUploadButton = new Button("Upload");
    setFieldNextToLabel(imageUploadButton, imageLabel); 
    // When the button is pressed open file explorer to select images.
    imageUploadButton.setOnAction(mAnimChooser);

    // Creates and adds elements to the group
    Group g = new Group(nameLabel, mNameField, typeLabel, mTypeBox, powerLabel, mPowerField, imageLabel,
        imageUploadButton);

    return new Scene(g, 500, (int) (ELEMENT_SPACE * position));
  }

}
