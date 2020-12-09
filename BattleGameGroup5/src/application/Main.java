package application;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
  // Database manager
  private DatabaseManager mDatabaseManager;
  
  // Scene to log in to the database.
  private DatabaseLoginScene mDatabaseLoginScene;
  // Main scene for creating the characters.
  private CreationScene mCreationScene;
  // Scene for creating a new skill.
  private NewSkillScene mSkillScene;
  // Scene for viewing all the created characters.
  private CharacterListScene mCharacterListScene;

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
    establishLinks();
    mDatabaseLoginScene.swapToScene();
  }

  public static void main(String[] args) {
    Application.launch(args);
  }
  
  /**
   * Creates all the scenes.
   * Helper function for start.
   * 
   * @param stage - Stage the scenes will load onto.
   */
  private void createScenes(Stage stage) {
    mDatabaseLoginScene = new DatabaseLoginScene(stage, mDatabaseManager);
    mCreationScene = new CreationScene(stage, mDatabaseManager);
    mSkillScene = new NewSkillScene(stage, mDatabaseManager);
    mCharacterListScene = new CharacterListScene(stage, mDatabaseManager);
  }
  
  /**
   * Establishes links between the scenes.
   */
  private void establishLinks() {
    // DatabaseLoginScene links.
    mDatabaseLoginScene.addLink(mCharacterListScene);
    // CreationScene links.
    mCreationScene.addLink(mSkillScene);
    mCreationScene.addLink(mCharacterListScene);
    // SkillScene links.
    mSkillScene.addLink(mCreationScene);
    // Character list links.
    mCharacterListScene.addLink(mCreationScene);
  }
}
