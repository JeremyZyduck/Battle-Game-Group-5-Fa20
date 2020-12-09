package application;

import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class CharacterListScene extends SceneManager {
  // Constants.
  // Name of the scene.
  public final static String TITLE = "Battle Game - Character List";
  // The width of the scene.
  private final static int SCENE_WIDTH = ELEMENT_SPACE * 15;
  // The default height of the scene.
  private final static int SCENE_HEIGHT = ELEMENT_SPACE * 10;
  
  // Scroll bar.
  private ScrollPane mScrollPane;
  // VBox of elements in the scene.
  private VBox mVBox;
  // Position in the scene of the last element.
  private int mPosition;
  
  /**
   * Constructs the CharacterListScene.
   * 
   * @param stage - Stage the scene will be on.
   * @param dataMan - Database manager.
   */
  public CharacterListScene(Stage stage, DatabaseManager dataMan) {
    super(stage, TITLE, dataMan);
  }

  /**
   * Builds the scene and returns it.
   */
  @Override
  protected Scene createScene() {
    // Keeps track of the position.
    mPosition = 0;
    
    // Headers for name and appearance.
    Label nameHeaderLabel = new Label("Character Name");
    setLabelToDefault(nameHeaderLabel, mPosition++);
    Label appearHeaderLabel = new Label("Appearance");
    setLabelToDefault(appearHeaderLabel, 0);
    setFieldNextToLabel(appearHeaderLabel, nameHeaderLabel);
    
    // Scroll box
    mVBox = new VBox();
    mVBox.setMaxWidth(SCENE_WIDTH);
    mScrollPane = new ScrollPane(mVBox);
    mScrollPane.setFitToWidth(true);
    
    // Create a border pan for the scene that contains the scroll pane.
    BorderPane borderPane = new BorderPane(mScrollPane);
    borderPane.setPadding(new Insets(15));
    
    // Set the headers at the top
    borderPane.setTop(new HBox(nameHeaderLabel, appearHeaderLabel));
    
    // A button for creating a new character.
    Button newButton = new Button("New Character");
    newButton.setOnAction(ev -> this.swapToLink(CreationScene.TITLE));
    borderPane.setBottom(newButton);

    return new Scene(borderPane, SCENE_WIDTH, SCENE_HEIGHT);
  }
  
  /**
   * Does things on loading into the new scene.
   * Adds additional fields to display characters from the database.
   */
  @Override
  protected void onLoad() { 
    // Pull characters and idle skills from the database.
    ArrayList<Character> characters = mDatabaseManager.getCharacters();
    ArrayList<Skill> idleSkills = mDatabaseManager.getIdleSkills();
    ArrayList<HBox> hBoxes = new ArrayList<HBox>(characters.size());
    for (Character c : characters) {
      // Name for the character.
      Label nameLabel = new Label(c.getName());
      setLabelToDefault(nameLabel, mPosition);
      
      // TODO This is rather inefficient. Should probably have references to skills in
      // character class that are set during the query.
      // Get the idle image for the character.
      Image appearance = null;
      for (Skill s : idleSkills) {
        if (s.getName().equals(c.getImage())) {
          appearance = s.getImage();
        }
      }
      // Image for the character
      ImageView imageView = new ImageView();
      imageView.setFitHeight(ELEMENT_SPACE * 2);
      imageView.setFitWidth(ELEMENT_SPACE * 2);
      imageView.setPreserveRatio(true);
      imageView.setImage(appearance);
      setFieldNextToLabel(imageView, nameLabel);
      
      mPosition+=2;
      
      // Edit button for character.
      Button editButt = new Button("Edit");
      editButt.setOnAction(ev -> System.out.println("EDIT not yet implemented"));
      setFieldNextToLabel(editButt, nameLabel);
      editButt.setLayoutX(imageView.getLayoutX() + ELEMENT_SPACE * 3);
      // Delete button for character.
      Button delButt = new Button("Delete");
      delButt.setOnAction(ev -> System.out.println("DELETE not yet implemented"));
      setFieldNextToLabel(delButt, nameLabel);
      delButt.setLayoutX(imageView.getLayoutX() + ELEMENT_SPACE * 5);
      
      // Add all the elements of each row to an hBox.
      hBoxes.add(new HBox(nameLabel, imageView, editButt, delButt));
    }
    mPosition++;
    
    // Clear the VBox.
    mVBox.getChildren().clear();
    // Add all the hBoxes to the scene.
    for (HBox box : hBoxes) {
      mVBox.getChildren().add(box);
    }
  }
}
