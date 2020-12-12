package application;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * Meant to be a base class when creating different scenes.
 * 
 * When you want to create a scene, extend this and place the
 * code for the elements of the scene in the createScene override.
 * 
 * If you need to use data from the database, use the database manager to
 * get that data in an override of onLoad.
 * 
 * @author Wyatt
 *
 */
public abstract class SceneManager {
  // Constants
  // Determines the vertical space between sets of relevant elements
  protected static final int ELEMENT_SPACE = 40;

  // Scene to show.
  private Scene mScene;
  // Stage the scene will be on.
  protected Stage mStage;
  // Title of the scene.
  private String mTitle;
  // Database manager reference.
  protected DatabaseManager mDatabaseManager;

  /**
   * Constructs a scene manager. Calls the createScene helper function.
   * 
   * @param title String title of the scene.
   */
  public SceneManager(String title) {
    mTitle = title;
    mScene = createScene();
  }
  
  /**
   * Sets the scene and database manager.
   * 
   * @param stage   - Stage the scene will be on.
   * @param dataMan - DatabaseManager reference.
   */
  public void initialize(Stage stage, DatabaseManager dataMan) {
    mStage = stage;
    mDatabaseManager = dataMan;
  }

  /**
   * Creates the scene. Meant to be overridden in sub classes.
   * 
   * @return Scene - created scene.
   */
  protected abstract Scene createScene();

  /**
   * Swaps to this scene.
   */
  public void swapToScene() {
    mStage.setTitle(mTitle);
    onLoad();
    mStage.setScene(mScene);
    mStage.show();
  }
  
  /**
   * Does things on loading into the new scene.
   * Meant to be overridden by children.
   */
  protected void onLoad() { }

  /**
   * Sets the given label's font, width, and layout to the determined defaults.
   * Distance down on page is determined by position.
   * 
   * @param label    Label to set to defaults.
   * @param position Position from the top the label will be placed.
   */
  protected void setLabelToDefault(Label label, int position) {
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
  protected void setFieldNextToLabel(Node field, Label label) {
    field.setLayoutY(label.getLayoutY());
    field.setLayoutX(label.getPrefWidth() + label.getLayoutX());
  }

  /**
   * Sets the given field to change to the default background when it is changed.
   * 
   * @param field Field who the listener will be added to.
   */
  protected void setTextFieldListeners(TextField field) {
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
  protected void setTextFieldOnlyNumbers(TextField field) {
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
  protected void setTextFieldBackgroundRed(TextField field) {
    setTextFieldBackground(field, Paint.valueOf("AA7777").toString().substring(2));
  }

  /**
   * Sets the background of the given field to normal.
   * 
   * @param field TextField whose background to set normal.
   */
  protected void setTextFieldBackgroundDefault(TextField field) {
    setTextFieldBackground(field, Paint.valueOf("FFFFFF").toString().substring(2));
  }

  /**
   * Sets the background of the given field to the given color.
   * 
   * @param field TextField whose background to set.
   * @param color Color to set the background to.
   */
  protected void setTextFieldBackground(TextField field, String color) {
    field.setStyle("-fx-control-inner-background: #" + color);
  }
  
  /**
   * Returns the stage the scene is on.
   * 
   * @return Stage - stage the scene is on.
   */
  public Stage getStage() {
    return mStage;
  }
  /**
   * Returns the title of the scene.
   * 
   * @return String - title of the scene.
   */
  public String getTitle() {
    return mTitle;
  }
}