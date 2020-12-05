package application;

import java.awt.image.BufferedImage;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.imageio.ImageIO;

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
  private ArrayList<Skill> mIdleSkills;
  // Current normal skills in the database.
  private ArrayList<Skill> mNormalSkills;
  // Current special skills in the database.
  private ArrayList<Skill> mSpecialSkills;
  
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
      System.out.println("Connected with the database successfully");
      
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
    mIdleSkills = new ArrayList<Skill>();
    mNormalSkills = new ArrayList<Skill>();
    mSpecialSkills = new ArrayList<Skill>();
    
    try {
      // Query for attacks.
      Statement state = mConnection.createStatement();
      String query = "SELECT * FROM attack";
      ResultSet rs = state.executeQuery(query);
      
      // Iterate over all rows.
      while (rs.next()) {
        // Get data.
        String idleID = rs.getNString("idleImageID");
        String normalID = rs.getNString("normalAttackID");
        String specialID = rs.getNString("specialAttackID");
        Blob idleBlob = rs.getBlob("idleImage");
        Blob normalBlob = rs.getBlob("normalImage");
        Blob specialBlob = rs.getBlob("specialImage");
        // Convert blobs to buffered images.
        BufferedImage idleImage = null;
        BufferedImage normalImage = null;
        BufferedImage specialImage = null;
        if (idleBlob != null) {
          idleImage = ImageIO.read(idleBlob.getBinaryStream());
        }
        if (normalBlob != null) {
          normalImage = ImageIO.read(normalBlob.getBinaryStream());
        }
        if (specialBlob != null) {
          specialImage = ImageIO.read(specialBlob.getBinaryStream());
        }
        // Create the skill with appropriate data.
        mIdleSkills.add(new Skill(idleID, SkillType.IDLE, 0, idleImage));
        mNormalSkills.add(new Skill(normalID, SkillType.NORMAL, 1, normalImage));
        mSpecialSkills.add(new Skill(specialID, SkillType.SPECIAL, 1, specialImage));
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  /**
   * Returns the idle skills from the database.
   * 
   * @return List<Skill> Idle skills in the database.
   */
  public ArrayList<Skill> getIdleSkills() {
    return mIdleSkills;
  }
  /**
   * Returns the normal skills from the database.
   * 
   * @return List<Skill> Normal skills in the database.
   */
  public ArrayList<Skill> getNormalSkills() {
    return mNormalSkills;
  }
  /**
   * Returns the special skills from the database.
   * 
   * @return List<Skill> Special skills in the database.
   */
  public ArrayList<Skill> getSpecialSkills() {
    return mSpecialSkills;
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
