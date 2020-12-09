package application;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

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
  // ArrayList of characters
  private ArrayList<Character> mCharacters;

  /**
   * Attempts to connect to the database. Returns false if failed, true if
   * successfully connected.
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
      pullCharactersFromDatabase();

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
        Image idleImage = null;
        Image normalImage = null;
        Image specialImage = null;
        if (idleBlob != null) {
          idleImage = new Image(idleBlob.getBinaryStream());
        }
        if (normalBlob != null) {
          normalImage = new Image(normalBlob.getBinaryStream());
        }
        if (specialBlob != null) {
          specialImage = new Image(specialBlob.getBinaryStream());
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
   * Pull characters from database.
   */
  private void pullCharactersFromDatabase() {
    // Initialize the characters list.
    mCharacters = new ArrayList<Character>();

    try {
      // Query for characters.
      Statement state = mConnection.createStatement();
      String query = "SELECT * FROM characterinfo";
      ResultSet rs = state.executeQuery(query);

      // Iterate over all rows.
      while (rs.next()) {
        // Get data.
        int characterID = rs.getInt("characterID");
        String characterName = rs.getNString("characterName");
        String idleImageID = rs.getNString("idleImageID");
        String normalAttackID = rs.getNString("idleImageID");
        String specialAttackID = rs.getNString("specialAttackID");

        // Create the character with appropriate data.
        Character newChar = new Character();
        newChar.setName(characterName);
        newChar.setImage(idleImageID);
        newChar.setImageAttack(normalAttackID);
        newChar.setImageSkill(specialAttackID);
        mCharacters.add(newChar);
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Uploads a character to the database.
   * 
   * @param character Character to be uploaded to the database.
   * @return boolean True if the character was added to the database or false if not.
   */
  public boolean uploadCharacter(Character character) {

    try {
      // Check if there are characters in the database. If there are, then increment off the max.
      // Otherwise start at 1.
      Statement charAmCheckState = mConnection.createStatement();
      String charAmCheckQuery = "SELECT COUNT(*) "
          + "FROM characterinfo c;";
      ResultSet rs = charAmCheckState.executeQuery(charAmCheckQuery);
      rs.next();
      // Get the amount.
      int charAm = rs.getInt("COUNT(*)");
      // Set the amount string.
      String idStr = "1";
      if (charAm > 0) {
        idStr = "(SELECT MAX(c.characterID) "
            + "FROM characterinfo c) + 1";
      } 

      // Create the query to add characters.
      Statement state = mConnection.createStatement();
      String query = "INSERT INTO characterinfo "
          + "VALUES (" + idStr + ", "
          + "'" + character.getName() + "', "
          + "'" + character.getImage() + "', "
          + "'" + character.getImageAttack() + "', "
          + "'" + character.getImageSkill() + "');";
      state.executeUpdate(query);

    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }

    mCharacters.add(character);
    return true;
  }
 
  /**
   * Uploads a skill/attack to the database.
   * 
   * @param skill - Skill to upload to the database.
   * @return boolean True if the character was added to the database or false if not.
   */
  public boolean uploadSkill(Skill skill) {
    
    try {
      // Create the query to add characters.
      PreparedStatement state = mConnection.prepareStatement("INSERT INTO attack VALUES (?,?,?,?,?,?)");
      state.setString(1, skill.getName());
      state.setString(2, skill.getName());
      state.setString(3, skill.getName());
      
      BufferedImage bImg = SwingFXUtils.fromFXImage(skill.getImage(), null);
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      ImageIO.write(bImg, "png", baos);
      InputStream is = new ByteArrayInputStream(baos.toByteArray());
      
      state.setBlob(4, is);
      state.setBlob(5, is);
      state.setBlob(6, is);
      
      state.execute();
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
    
    mIdleSkills.add(skill);
    mNormalSkills.add(skill);
    mSpecialSkills.add(skill);
    return true;
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
   * Returns the characters from the database.
   * 
   * @return List<Character> Characters in the database.
   */
  public ArrayList<Character> getCharacters() {
    return mCharacters;
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
