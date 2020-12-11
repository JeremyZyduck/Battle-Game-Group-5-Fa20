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
import java.util.HashMap;

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
  // ArrayList of characters
  private HashMap<Integer, Character> mCharacters;

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
      pullCharacterInfoFromDatabase();

      return true;
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("Failed to connect to database");
      return false;
    }
  }

  /**
   * Pulls character info and theirs skill from the database.
   */
  private void pullCharacterInfoFromDatabase() {
    // Initialize the characters list.
    mCharacters = new HashMap<Integer, Character>();

    try {
      // Query for attacks.
      Statement state = mConnection.createStatement();
      String query = "SELECT ch.characterID, ch.characterName, ch.health, ch.strength, ch.defense, ch.cost, j.attackTypeID, j.attackName, j.imageBlob "
          + "FROM characterInfo ch "
          + "JOIN (SELECT atk.characterID, atk.imageID, atk.attackTypeID,  img.attackName, img.imageBlob "
          + "FROM characterAttack atk " + "JOIN attackImage img " + "WHERE img.imageID = atk.imageID) j "
          + "WHERE ch.characterID = j.characterID;";
      ResultSet rs = state.executeQuery(query);

      // Iterate over all rows.
      while (rs.next()) {
        int characterID = rs.getInt("characterID");
        String characterName = rs.getNString("characterName");
        int health = rs.getInt("health");
        int strength = rs.getInt("strength");
        int defense = rs.getInt("defense");
        int cost = rs.getInt("cost");
        String attackTypeID = rs.getNString("attackTypeID");
        String attackName = rs.getNString("attackName");
        Blob imageBlob = rs.getBlob("imageBlob");

        // Convert blob to image.
        Image attackImage = null;
        if (imageBlob != null) {
          attackImage = new Image(imageBlob.getBinaryStream());
        }

        // Convert the attackTypeID to skillType.
        SkillType skillType = null;
        switch (attackTypeID) {
        case "idle":
          skillType = SkillType.IDLE;
          break;
        case "normal":
          skillType = SkillType.NORMAL;
          break;
        case "special":
          skillType = SkillType.SPECIAL;
          break;
        default:
          System.out.println(
              "Unrecognized attackTypeID [" + attackTypeID + "] in DatabaseManager.pullCharacterInfoFromDatabase()");
          break;
        }
        // Create the new skill.
        Skill newSkill = null;
        if (skillType != null) {
          newSkill = new Skill(attackName, skillType, 0, attackImage);
        }

        // Add the skill to the existing character.
        Character existChar = mCharacters.get(characterID);
        // If the character doesn't exist yet, make them exist.
        if (existChar == null) {
          existChar = new Character();
          existChar.setName(characterName);
          existChar.setHealth(health);
          existChar.setStrength(strength);
          existChar.setDefense(defense);
          existChar.setCost(cost);
          mCharacters.put(characterID, existChar);
        }
        switch (skillType) {
        case IDLE:
          existChar.setIdleSkill(newSkill);
          break;
        case NORMAL:
          existChar.setNormalSkill(newSkill);
          break;
        case SPECIAL:
          existChar.setSpecialSkill(newSkill);
          break;
        default:
          if (skillType != null) {
            System.out.println(
                "Unrecognized skillType [" + skillType + "] in DatabaseManager.pullCharacterInfoFromDatabase()");
          }
          break;
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Uploads a character to the database. TODO FIX
   * 
   * @param character Character to be uploaded to the database.
   * @return boolean True if the character was added to the database or false if
   *         not.
   */
  public boolean uploadCharacter(Character character) {
    /*
     * try { // Check if there are characters in the database. If there are, then
     * increment // off the max. // Otherwise start at 1. Statement charAmCheckState
     * = mConnection.createStatement(); String charAmCheckQuery = "SELECT COUNT(*) "
     * + "FROM characterinfo c;"; ResultSet rs =
     * charAmCheckState.executeQuery(charAmCheckQuery); rs.next(); // Get the
     * amount. int charAm = rs.getInt("COUNT(*)"); // Set the amount string. String
     * idStr = "1"; if (charAm > 0) { idStr = "(SELECT MAX(c.characterID) " +
     * "FROM characterinfo c) + 1"; }
     * 
     * // Create the query to add characters. Statement state =
     * mConnection.createStatement(); String query = "INSERT INTO characterinfo " +
     * "VALUES (" + idStr + ", " + "'" + character.getName() + "', " + "'" +
     * character.getImage() + "', " + "'" + character.getImageAttack() + "', " + "'"
     * + character.getImageSkill() + "');"; state.executeUpdate(query);
     * 
     * } catch (Exception e) { e.printStackTrace(); return false; }
     * 
     * mCharacters.add(character);
     */
    return true;
  }

  /**
   * Uploads a skill/attack to the database. TODO FIX/REMOVE
   * 
   * @param skill - Skill to upload to the database.
   * @return boolean True if the character was added to the database or false if
   *         not.
   */
  public boolean uploadSkill(Skill skill) {

    try {
      // Check if there are skills in the database. If there are, then increment
      // off the max.
      // Otherwise start at 1.
      Statement charAmCheckState = mConnection.createStatement();
      String charAmCheckQuery = "SELECT COUNT(*) " + "FROM attackImage i;";
      ResultSet rs = charAmCheckState.executeQuery(charAmCheckQuery);
      rs.next();
      // Get the amount.
      int charAm = rs.getInt("COUNT(*)");
      // Set the amount string.
      String idStr = "1";
      if (charAm > 0) {
        idStr = "(SELECT MAX(i.imageID) " + "FROM attackImage i) + 1";
      }

      // Create the query to add characters.
      PreparedStatement state = mConnection.prepareStatement("INSERT INTO attackImage VALUES (?,?,?)");

      // Set primary key.
      state.setInt(1, 10);
      // Set blob image.
      BufferedImage bImg = SwingFXUtils.fromFXImage(skill.getImage(), null);
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      ImageIO.write(bImg, "png", baos);
      InputStream is = new ByteArrayInputStream(baos.toByteArray());
      state.setBlob(2, is);
      // Set skill name.
      state.setString(3, skill.getName());

      state.execute();
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }

    return true;
  }

  /**
   * Returns the characters from the database.
   * 
   * @return List<Character> Characters in the database.
   */
  public ArrayList<Character> getCharacters() {
    ArrayList<Character> list = new ArrayList<Character>(mCharacters.size());
    for (Integer i : mCharacters.keySet()) {
      list.add(mCharacters.get(i));
    }

    return list;
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
