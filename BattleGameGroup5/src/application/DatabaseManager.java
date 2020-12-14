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
 * Manages the connection to the database. Uploads, updates, and deletes data to
 * and from the database.
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
          existChar.setKey(characterID);
          mCharacters.put(characterID, existChar);
        }
        // Set the character's skill.
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
   * Uploads a character to the database.
   * 
   * @param character Character to be uploaded to the database.
   * @return boolean True if the character was added to the database or false if
   *         not.
   */
  public boolean uploadCharacter(Character character) {

    try {
      // Check if there are characters in the database. If there are, then increment
      // off the max.
      // Otherwise start at 1.
      Statement charAmCheckState = mConnection.createStatement();
      String charAmCheckQuery = "SELECT COUNT(*) " + "FROM characterInfo;";
      ResultSet rsCharAm = charAmCheckState.executeQuery(charAmCheckQuery);
      rsCharAm.next();
      // Get the amount.
      int charAm = rsCharAm.getInt("COUNT(*)");
      // Set the amount string.
      if (charAm > 0) {
        Statement charGetMaxState = mConnection.createStatement();
        String charGetMaxQuery = "SELECT MAX(c.characterID) FROM characterInfo c";
        ResultSet rsCharMax = charGetMaxState.executeQuery(charGetMaxQuery);
        rsCharMax.next();
        // Get the max.
        charAm = rsCharMax.getInt("MAX(c.characterID)");
      }

      // Check if there are attack images in the database. If there are, then
      // increment
      // off the max.
      // Otherwise start at 1.
      Statement imgAmCheckState = mConnection.createStatement();
      String imgAmCheckQuery = "SELECT COUNT(*) " + "FROM attackImage;";
      ResultSet rsImgAm = imgAmCheckState.executeQuery(imgAmCheckQuery);
      rsImgAm.next();
      // Get the amount.
      int imgAm = rsImgAm.getInt("COUNT(*)");
      // Set the amount string.
      if (imgAm > 0) {
        Statement imgGetMaxState = mConnection.createStatement();
        String imgGetMaxQuery = "SELECT MAX(i.imageID) FROM attackImage i";
        ResultSet rsImgMax = imgGetMaxState.executeQuery(imgGetMaxQuery);
        rsImgMax.next();
        // Get the max.
        imgAm = rsImgMax.getInt("MAX(i.imageID)");
      }

      // Create the query to add character info.
      PreparedStatement charInfoState = mConnection.prepareStatement("INSERT INTO characterInfo VALUES (?,?,?,?,?,?);");
      charInfoState.setInt(1, charAm + 1);
      charInfoState.setString(2, character.getName());
      charInfoState.setInt(3, character.getHealth());
      charInfoState.setInt(4, character.getStrength());
      charInfoState.setInt(5, character.getDefense());
      charInfoState.setInt(6, character.getCost());
      // Execute character info insert.
      charInfoState.execute();
      character.setKey(charAm + 1);

      ArrayList<Skill> skills = new ArrayList<Skill>(3);
      skills.add(character.getIdleSkill());
      skills.add(character.getNormalSkill());
      skills.add(character.getSpecialSkill());
      for (int i = 1; i <= skills.size(); ++i) {
        Skill skill = skills.get(i - 1);

        // Create the query to add attack image.
        PreparedStatement attackImgState = mConnection.prepareStatement("INSERT INTO attackImage VALUES (?,?,?);");
        attackImgState.setInt(1, imgAm + i);
        // Set blob image.
        BufferedImage bImg = SwingFXUtils.fromFXImage(skill.getImage(), null);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bImg, "png", baos);
        InputStream is = new ByteArrayInputStream(baos.toByteArray());
        // Set the blob.
        attackImgState.setBlob(2, is);
        attackImgState.setString(3, skill.getName());

        // Create the query to add character attack.
        PreparedStatement charAtkState = mConnection.prepareStatement("INSERT INTO characterAttack VALUES (?,?,?);");
        charAtkState.setInt(1, charAm + 1);
        charAtkState.setInt(2, imgAm + i);
        charAtkState.setString(3, skill.getType().toString().toLowerCase());

        // Execute attack image and character attack inserts.
        attackImgState.execute();
        charAtkState.execute();
      }

      // Put the new character into the HashMap.
      mCharacters.put(charAm + 1, character);
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }

    return true;
  }

  /**
   * Updates a character in the database.
   * 
   * @param character Character to be updated in the database.
   * @return boolean True if the character was added to the database or false if
   *         not.
   */
  public boolean updateCharacter(Character character) {
    try {
      // First update characterInfo table.
      Statement upCharState = mConnection.createStatement();
      String upCharQuery = "UPDATE characterInfo c " + "SET c.characterName = '" + character.getName() + "', "
          + "c.health = " + character.getHealth() + ", " + "c.strength = " + character.getStrength() + ", "
          + "c.defense = " + character.getDefense() + ", " + "c.cost = " + character.getCost() + " "
          + "WHERE c.characterID = " + character.getKey() + ";";
      upCharState.executeUpdate(upCharQuery);

      // Next update attackImage table.
      ArrayList<Skill> skills = new ArrayList<Skill>(3);
      skills.add(character.getIdleSkill());
      skills.add(character.getNormalSkill());
      skills.add(character.getSpecialSkill());
      for (Skill skill : skills) {
        String upImgQuery = "UPDATE attackImage i " + "SET i.attackName = ?, " + "i.imageBlob = ? "
            + "WHERE i.imageID IN " + "(SELECT a.imageID FROM characterAttack a " + "WHERE a.characterID = "
            + character.getKey() + " " + "AND a.attackTypeID = '" + skill.getType().toString().toLowerCase() + "');";
        PreparedStatement upImgState = mConnection.prepareStatement(upImgQuery);

        upImgState.setString(1, skill.getName());
        // Set blob image.
        BufferedImage bImg = SwingFXUtils.fromFXImage(skill.getImage(), null);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bImg, "png", baos);
        InputStream is = new ByteArrayInputStream(baos.toByteArray());
        upImgState.setBlob(2, is);

        // Execute the update.
        upImgState.execute();
      }

    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }

    return true;
  }

  /**
   * Deletes a character, its skills, and its links from the database.
   * 
   * @param character - Character to delete from the database.
   * @return boolean - If the character was successfully deleted or not.
   */
  public boolean deleteCharacter(Character character) {
    // character.printAll();
    // First remove the character from the HashMap.
    mCharacters.remove(character.getKey());
    // Then remove the character from the database.
    try {
      // First we need to remove the characters skills.
      // Get the primary keys of the skills we need to delete.
      Statement charAtkState = mConnection.createStatement();
      String charAtkQuery = "SELECT i.imageID FROM characterAttack i " + "WHERE i.characterID = " + character.getKey()
          + ";";
      ResultSet rsCharAtk = charAtkState.executeQuery(charAtkQuery);
      // Store the image keys to delete in a list.
      ArrayList<Integer> skillKeys = new ArrayList<Integer>();
      while (rsCharAtk.next()) {
        skillKeys.add(rsCharAtk.getInt("imageID"));
      }

      // Now we delete the links between the character and their skills.
      String linkDelQuery = "DELETE FROM characterAttack " + "WHERE characterAttack.characterID = " + character.getKey()
          + ";";
      PreparedStatement linkDelState = mConnection.prepareStatement(linkDelQuery);
      linkDelState.execute();

      // Delete the images after deleting their collections.
      for (Integer key : skillKeys) {
        String atkImgQuery = "DELETE FROM attackImage " + "WHERE attackImage.imageID = " + key + ";";
        PreparedStatement atkImgState = mConnection.prepareStatement(atkImgQuery);
        atkImgState.execute();
      }

      // Delete the character from the table.
      String charDelQuery = "DELETE FROM characterInfo " + "WHERE characterInfo.characterID = " + character.getKey()
          + ";";
      PreparedStatement charDelState = mConnection.prepareStatement(charDelQuery);
      charDelState.execute();

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
