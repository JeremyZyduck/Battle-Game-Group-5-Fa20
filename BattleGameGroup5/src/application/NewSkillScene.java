package application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class NewSkillScene extends SceneManager {
  // Constants.
  // Name of the scene.
  public final static String TITLE = "Battle Game - Character Creation - New Skill";
  
  // Singleton
  private static NewSkillScene instance = null;

  // Labels for power and uploaded image.
  private Label mPowerLabel;
  private Label mImageNameLabel;
  // Text fields for skill name and power
  private TextField mNameField;
  private TextField mPowerField;
  // ImageView for image
  private ImageView mImageView;
  
  // For uploading the image.
  private AnimationChooser mAnimChooser;
  
  // For what skill type is being made.
  private SkillType mType;

  /**
   * Constructs the NewSkillScene.
   * 
   * @param stage   - Stage the scene will be on.
   * @param dataMan - Database manager.
   */
  private NewSkillScene() {
    super(TITLE);
  }

  /**
   * Gets the singleton instance.
   * 
   * @return NewSkillScene singleton instance.
   */
  public static NewSkillScene getInstance() {
    if (instance == null) {
      instance = new NewSkillScene();
    }
    return instance;
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

    // Label and text field for entering power.
    mPowerLabel = new Label("Power: ");
    setLabelToDefault(mPowerLabel, position++);
    mPowerField = new TextField("0");
    setFieldNextToLabel(mPowerField, mPowerLabel);
    setTextFieldListeners(mPowerField);
    setTextFieldOnlyNumbers(mPowerField);
    mPowerLabel.setVisible(false);
    mPowerField.setVisible(false);

    // Prompt for uploading an image to add to the database and boxes that are to
    // select images.
    Label imageLabel = new Label("Upload skill image: ");
    setLabelToDefault(imageLabel, position++);
    Button imageUploadButton = new Button("Upload");
    setFieldNextToLabel(imageUploadButton, imageLabel);
    // When the button is pressed open file explorer to select images.
    mAnimChooser = new AnimationChooser(getStage());
    imageUploadButton.setOnAction(mAnimChooser);

    // Image name selected for the upload.
    mImageNameLabel = new Label("");
    setLabelToDefault(mImageNameLabel, position++);
    mImageView = new ImageView();
    mImageView.setFitHeight(ELEMENT_SPACE * 2);
    mImageView.setFitWidth(ELEMENT_SPACE * 2);
    mImageView.setPreserveRatio(true);
    position += 2;
    setFieldNextToLabel(mImageView, mImageNameLabel);
    // After a new file is chosen, update its info on the page.
    mAnimChooser.addOnSelectEvent(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        if (mAnimChooser.getFile() != null && mAnimChooser.getAnimation() != null) {
          mImageNameLabel.setText(mAnimChooser.getFile().getName());
          mImageView.setImage(mAnimChooser.getAnimation());
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
        CreationScene.getInstance().swapToScene();
      }
    });

    // Button to submit
    Button submitButt = new Button("Submit");
    submitButt.setLayoutY(ELEMENT_SPACE * position++);
    submitButt.setLayoutX(cancelButt.getLayoutBounds().getMaxX() + ELEMENT_SPACE * 3);
    // When submit is pressed, try to upload the skill (return to creation if
    // successful)
    submitButt.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        saveSkill();
      }
    });

    // Creates and adds elements to the group
    Group g = new Group(nameLabel, mNameField, mPowerLabel, mPowerField, imageLabel,
        imageUploadButton, mImageNameLabel, mImageView, cancelButt, submitButt);

    return new Scene(g, 500, (int) (ELEMENT_SPACE * position));
  }
  
  /**
   * Does things on loading into the new scene.
   * Meant to be overridden by children.
   */
  @Override
  protected void onLoad() { 
    // Set visible for a non idle skill.
    if (mType != SkillType.IDLE) {
      mPowerLabel.setVisible(true);
      mPowerField.setVisible(true);
    }
    // Set invisible for an idle skill.
    else {
      mPowerLabel.setVisible(false);
      mPowerField.setText("0");
      mPowerField.setVisible(false);
    }
    
    
    // Clear some fields.
    mNameField.setText("");
    mPowerField.setText("0");
    mImageNameLabel.setText("");
    mImageView.setImage(null);
  }
  
  /**
   * Sets the type of skill that will be entered.
   * Must be done before swapping to the scene.
   * 
   * @param type - SkillType being created.
   */
  public void setSkillType(SkillType type) {
    mType = type;
  }

  private void saveSkill() {
    boolean isValid = true;
    // Check if name was entered.
    String name = mNameField.getText();
    if (name == null || name.equals("")) {
      setTextFieldBackgroundRed(mNameField);
      isValid = false;
    }
    // Check if power was entered (where needed).
    int power = 0;
    if (mType != SkillType.IDLE && (mPowerField.getText() == null || mPowerField.getText().equals(""))) {
      setTextFieldBackgroundRed(mPowerField);
      isValid = false;
    }
    else if (mType != SkillType.IDLE) {
      power = Integer.parseInt(mPowerField.getText());
    }
    // Check if image was selected.
    Image img = mAnimChooser.getAnimation();
    if (img == null) {
      isValid = false;
    }
    
    // If the entries are all valid create the new skill.
    if (isValid) {
      Character curChar = CreationScene.getInstance().getCharacter();
      Skill newSkill = new Skill(name, mType, power, img);
      // Save the skill in the correct spot.
      switch (mType) {
      case IDLE:
        curChar.setIdleSkill(newSkill);
        break;
      case NORMAL:
        curChar.setNormalSkill(newSkill);
        break;
      case SPECIAL:
        curChar.setSpecialSkill(newSkill);
        break;
      default:
        System.out.println("Unrecognized SkillType [" + mType + "] in NewSkillScene.saveSkill()");
        break;
      }
      
      // Swap back to creation after finished.
      CreationScene.getInstance().swapToScene();
    }
  }
}
