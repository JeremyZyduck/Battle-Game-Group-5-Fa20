package application;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
  // Database manager
  private DatabaseManager mDatabaseManager;

  /**
   * Starts the application.
   * 
   * @param primaryStage - stage for the application.
   */
  @Override
  public void start(Stage primaryStage) throws Exception {
    mDatabaseManager = new DatabaseManager();

    // Create the scenes, give them links to one another, and open the first scene.
    createScenes(primaryStage);
    DatabaseLoginScene.getInstance().swapToScene();
  }

  public static void main(String[] args) {
    Application.launch(args);
  }

  /**
   * Creates all the scenes. Helper function for start.
   * 
   * @param stage - Stage the scenes will load onto.
   */
  private void createScenes(Stage stage) {
    DatabaseLoginScene.getInstance().initialize(stage, mDatabaseManager);
    CreationScene.getInstance().initialize(stage, mDatabaseManager);
    NewSkillScene.getInstance().initialize(stage, mDatabaseManager);
    CharacterListScene.getInstance().initialize(stage, mDatabaseManager);
  }
}
