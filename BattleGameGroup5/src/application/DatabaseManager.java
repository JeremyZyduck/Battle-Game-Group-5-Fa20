package application;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Manages the connection to the database.
 * 
 * @author Wyatt
 *
 */
public class DatabaseManager {
  // Connection to the database.
  private Connection mConnection;
  // Current skills in the database.
  private ArrayList<Skill> mSkills;
  
  /**
   * Attempts to connect to the database.
   * Returns false if failed, true if successfully connected.
   * 
   * @param user User name to the database.
   * @param pass Password to the database.
   * @return boolean
   */
  public boolean connectToDatabase(String user, String pass) {
    try {
      mConnection = DriverManager.getConnection("jdbc:mysql://144.13.22.59:3306/G5AgileExperience", user, pass);
      System.out.println("Connected With the database successfully");
      
      // Pull some data from the database right away.
      pullSkillsFromDatabase();
      
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("Failed to connect to database");
      return false;
    }
  }
  
  /**
   * Pulls skills from the database.
   */
  private void pullSkillsFromDatabase() {
    // Initialize skills list.
    mSkills = new ArrayList<Skill>();
    
    try {
      // Query for attacks.
      Statement state = mConnection.createStatement();
      String query = "SELECT * FROM attack";
      ResultSet rs = state.executeQuery(query);
      
      // Iterate over all rows.
      while (rs.next()) {
        String id = rs.getNString("id");
        Blob image = rs.getBlob("image");
        // Create the skill with appropriate data.
        mSkills.add(new Skill(id, 0, 0));
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  /**
   * Returns the skills from the database.
   * 
   * @return List<Skill> Skills in the database.
   */
  public ArrayList<Skill> getSkills() {
    return mSkills;
  }
  
  /**
   * Gets the connection to the database.
   * 
   * @return Connnection.
   */
  public Connection getConnection() {
    return mConnection;
  }
}
