package application;

/**
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
   * Constructs a character.
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
  public void setName(String name) {
    this.mName = name;
  }

  public void setCost(int cost) {
    this.mCost = cost;
  }

  public void setHealth(int health) {
    this.mHealth = health;
  }

  public void setStrength(int strength) {
    this.mStrength = strength;
  }

  public void setDefense(int defense) {
    this.mDefense = defense;
  }

  public void setIdleSkill(Skill skill) {
    this.mIdleSkill = skill;
  }

  public void setNormalSkill(Skill skill) {
    this.mNormalSkill = skill;
  }

  public void setSpecialSkill(Skill skill) {
    this.mSpecialSkill = skill;
  }

  public void setKey(int key) {
    this.mKey = key;
  }

  // getters
  public String getName() {
    return mName;
  }

  public int getCost() {
    return mCost;
  }

  public int getHealth() {
    return mHealth;
  }

  public int getStrength() {
    return mStrength;
  }

  public int getDefense() {
    return mDefense;
  }

  public Skill getIdleSkill() {
    return mIdleSkill;
  }

  public Skill getNormalSkill() {
    return mNormalSkill;
  }

  public Skill getSpecialSkill() {
    return mSpecialSkill;
  }

  public int getKey() {
    return mKey;
  }

  public void printAll() {
    System.out.println("Name: " + mName);
    System.out.println("Cost: " + mCost);
    System.out.println("Health: " + mHealth);
    System.out.println("Strength: " + mStrength);
    System.out.println("Defense: " + mDefense);
    System.out.println("Key:" + mKey);
  }

}
