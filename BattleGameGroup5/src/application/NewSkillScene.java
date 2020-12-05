package application;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
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
    mAnimChooser = new AnimationChooser(getStage()); 
    imageUploadButton.setOnAction(mAnimChooser);
    
    // Image name selected for the upload.
    Label imageNameLabel = new Label("");
    setLabelToDefault(imageNameLabel, position++);
    ImageView imageView = new ImageView();
    imageView.setFitHeight(ELEMENT_SPACE * 2);
    imageView.setFitWidth(ELEMENT_SPACE * 2);
    imageView.setPreserveRatio(true);
    position += 2;
    setFieldNextToLabel(imageView, imageNameLabel);
    // After a new file is chosen, update its info on the page.
    mAnimChooser.addOnSelectEvent(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        if (mAnimChooser.getFile() != null && mAnimChooser.getAnimation() != null) {
          imageNameLabel.setText(mAnimChooser.getFile().getName());
          imageView.setImage(mAnimChooser.getAnimation());
        }
      }
    });
    
    // Button to cancel
    Button cancelButt = new Button("Cancel");
    cancelButt.setLayoutY(ELEMENT_SPACE * position);
    cancelButt.setLayoutX(ELEMENT_SPACE);
    // When canceled, return to the creation scene.
    cancelButt.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        swapToLink(CreationScene.TITLE);
      }
    });
    
    // Button to submit
    Button submitButt = new Button("Submit");
    submitButt.setLayoutY(ELEMENT_SPACE * position++);
    submitButt.setLayoutX(cancelButt.getLayoutBounds().getMaxX() + ELEMENT_SPACE * 3);
    // When submit is pressed, try to upload the skill (return to creation if successful)
    submitButt.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        // TODO Upload skill to database.
        
      }
    });
    

    // Creates and adds elements to the group
    Group g = new Group(nameLabel, mNameField, typeLabel, mTypeBox, powerLabel, mPowerField, imageLabel,
        imageUploadButton, imageNameLabel, imageView, cancelButt, submitButt);

    return new Scene(g, 500, (int) (ELEMENT_SPACE * position));
  }

}
