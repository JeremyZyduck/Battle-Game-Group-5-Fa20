package application;

import java.io.File;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Meant to be added to buttons with the setOnAction function. Pulls open a file
 * chooser for the user to select the desired action.
 * 
 * @authors Wyatt
 *
 */
public class AnimationChooser implements EventHandler<ActionEvent> {
  // Stage to open file chooser on.
  private Stage mStage;
  // File that was selected.
  private File mFile;

  // List of things that happen when the file is selected.
  private ArrayList<EventHandler<ActionEvent>> mOnSelectEvents;

  /**
   * Constructs an AnimationChooser.
   * 
   * @param stage Stage to open file chooser on.
   */
  public AnimationChooser(Stage stage) {
    mStage = stage;
    mOnSelectEvents = new ArrayList<EventHandler<ActionEvent>>();
  }  

  /**
   * Override to run on button press. Opens a file chooser and saves the selected
   * file as a member to be accessed later.
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
    mFile = fileChooser.showOpenDialog(mStage);

    // Call on select events.
    for (EventHandler<ActionEvent> eventHandle : mOnSelectEvents) {
      eventHandle.handle(null);
    }
  }

  /**
   * Adds an EventHandler to do things after the animation is chosen.
   * 
   * @param event Event to execute handle() of when the animation is chosen.
   */
  public void addOnSelectEvent(EventHandler<ActionEvent> event) {
    mOnSelectEvents.add(event);
  }

  /**
   * Returns the most recent file that was gotten by the file chooser.
   * 
   * @return File file that was selected.
   */
  public File getFile() {
    return mFile;
  }

  /**
   * Returns the most recent animation gotten by the file chooser.
   * 
   * @return Image animation that was selected.
   */
  public Image getAnimation() {
    try {
      if (mFile != null) {
        return new Image(mFile.toURI().toURL().toExternalForm());
      }
      return null;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }
}
