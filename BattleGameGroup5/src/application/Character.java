package application;

/**
 * Stores variables pertinent to battle game characters
 * 
 * @author Jeremy Zyduck
 */
public class Character {

  // Private variables
  private String mName;
  private int mCost; // How much the character will cost
  private int mHealth;
  private int mStrength;
  private int mDefense;
  // Character skills.
  private Skill mIdleSkill;
  private Skill mNormalSkill;
  private Skill mSpecialSkill;
  // Primary key of the character on the database.
  private int mKey;

  /**
   * Constructor for Character class
   */
  public Character() {
    mName = "";
    mCost = 0;
    mHealth = 0;
    mStrength = 0;
    mDefense = 0;
    mIdleSkill = null;
    mNormalSkill = null;
    mSpecialSkill = null;
    mKey = -1;
  }

  // setters
  /**
   * setter for mName
   * 
   * @param name - the value that mName is set to
   */
  public void setName(String name) {
    this.mName = name;
  }

  /**
   * setter for mCost
   * 
   * @param cost - the value that mCost is set to
   */
  public void setCost(int cost) {
    this.mCost = cost;
  }

  /**
   * setter for mHealth
   * 
   * @param health - the value that mHealth is set to
   */
  public void setHealth(int health) {
    this.mHealth = health;
  }

  /**
   * setter for mStrength
   * 
   * @param strength - the value that mStrength is set to
   */
  public void setStrength(int strength) {
    this.mStrength = strength;
  }

  /**
   * setter for mDefense
   * 
   * @param defense - the value that mDefense is set to
   */
  public void setDefense(int defense) {
    this.mDefense = defense;
  }

  /**
   * setter for mIdleSkill
   * 
   * @param skill - the value that mIdleSkill is set to
   */
  public void setIdleSkill(Skill skill) {
    this.mIdleSkill = skill;
  }

  /**
   * setter for mName
   * 
   * @param skill - the value that mName is set to
   */
  public void setNormalSkill(Skill skill) {
    this.mNormalSkill = skill;
  }

  /**
   * setter for mSpecialSkill
   * 
   * @param skill - the value that mSpecialSkill is set to
   */
  public void setSpecialSkill(Skill skill) {
    this.mSpecialSkill = skill;
  }

  /**
   * setter for mKey
   * 
   * @param key - the value that mKey is set to
   */
  public void setKey(int key) {
    this.mKey = key;
  }

  // getters
  /**
   * getter for mName
   */
  public String getName() {
    return mName;
  }

  /**
   * getter for mCost
   */
  public int getCost() {
    return mCost;
  }

  /**
   * getter for mHealth
   */
  public int getHealth() {
    return mHealth;
  }

  /**
   * getter for mStrength
   */
  public int getStrength() {
    return mStrength;
  }

  /**
   * getter for mDefense
   */
  public int getDefense() {
    return mDefense;
  }

  /**
   * getter for mIdleSkill
   */
  public Skill getIdleSkill() {
    return mIdleSkill;
  }

  /**
   * getter for mNormalSkill
   */
  public Skill getNormalSkill() {
    return mNormalSkill;
  }

  /**
   * getter for mSpecialSkill
   */
  public Skill getSpecialSkill() {
    return mSpecialSkill;
  }

  /**
   * getter for mKey
   */
  public int getKey() {
    return mKey;
  }

  /**
   * Prints all variables stored in the character class
   */
  public void printAll() {
    System.out.println("Name: " + mName);
    System.out.println("Cost: " + mCost);
    System.out.println("Health: " + mHealth);
    System.out.println("Strength: " + mStrength);
    System.out.println("Defense: " + mDefense);
    System.out.println("Key:" + mKey);
  }
}