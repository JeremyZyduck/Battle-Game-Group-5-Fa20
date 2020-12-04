package application;

import java.io.File;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Meant to be added to buttons with the setOnAction function.
 * Pulls open a file chooser for the user to select the desired action.
 * 
 * @authors Wyatt
 *
 */
public class AnimationChooser implements EventHandler<ActionEvent> {
  // Stage to open file chooser on.
  private Stage mStage;
  // Reference to the database manager.
  private DatabaseManager mDatabaseManager;
  // File that was selected.
  private File mAnimation;
  
  /**
   * Constructs an AnimationChooser.
   * 
   * @param stage Stage to open file chooser on.
   */
  public AnimationChooser(Stage stage, DatabaseManager manager) {
    mStage = stage;
    mDatabaseManager = manager;
  }
  
  /**
   * Override to run on button press.
   * Opens a file chooser and saves the selected file as a member to be accessed later.
   * 
   * @param event ActionEvent
   */
  @Override
  public void handle(ActionEvent event) {
    // Open file chooser.
    FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png", "*.gif");
    FileChooser fileChooser = new FileChooser();
    fileChooser.getExtensionFilters().add(imageFilter);
    fileChooser.setTitle("Upload Animation");
    // Get selected file.
    mAnimation = fileChooser.showOpenDialog(mStage);
  }
  
  /**
   * Returns the most recent animation gotten by the file chooser.
   * 
   * @return File animation that was selected.
   */
  public File getAnimation() {
    return mAnimation;
  }
}
