package application;

import java.util.HashMap;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public abstract class SceneManager {
  // Constants
  // Determines the vertical space between sets of relevant elements
  protected static final int ELEMENT_SPACE = 40;

  // Scene to show.
  private Scene mScene;
  // Stage the scene will be on.
  private Stage mStage;
  // Title of the scene.
  private String mTitle;
  // Database manager reference.
  protected DatabaseManager mDatabaseManager;
  
  // References to other scenes that can be swapped to.
  private HashMap<String, SceneManager> links;

  /**
   * Constructs a scene manager. Calls the createScene helper function.
   * 
   * @param title String title of the scene.
   */
  public SceneManager(Stage stage, String title, DatabaseManager dataMan) {
    mStage = stage;
    mDatabaseManager = dataMan;
    mTitle = title;
    links = new HashMap<String, SceneManager>();
    mScene = createScene();
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
   * Adds a link to this scene.
   * 
   * @param scene Scene where the link goes.
   */
  public void addLink(SceneManager scene) {
    links.put(scene.getTitle(), scene);
  }
  
  /**
   * Swaps the scene to the given link.
   * 
   * @param linkKey Key to the link.
   * @return boolean - returns false if the link key was invalid and the scene was not swapped.
   */
  protected boolean swapToLink(String linkKey) {
    SceneManager scene = links.get(linkKey);
    if (scene != null) {
      scene.swapToScene();
      return true;
    }
    
    return false;
  }

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
    field.setLayoutX(label.getPrefWidth());
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
   * Returns the title of the scene.
   * 
   * @return String - title of the scene.
   */
  public String getTitle() {
    return mTitle;
  }
}