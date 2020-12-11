package application;

/**
 * @author Jeremy Zyduck
 */
public class Character {

  // Private variables
  private String mName;
  private int mCost; // How much the character will cost
  private String mImage; // Character images will reference a link to an image stored on the web server
  private String mImageAttack;
  private String mImageSkill;
  private int mHealth;
  private int mStrength;
  private int mDefense;
  private String mAttackName;
  private String mSkillName;
  private Skill mIdleSkill;
  private Skill mNormalSkill;
  private Skill mSpecialSkill;

  // setters
  public void setName(String name) {
    this.mName = name;
  }

  public void setCost(int cost) {
    this.mCost = cost;
  }

  public void setImage(String image) {
    this.mImage = image;
  }

  public void setImageAttack(String imageAttack) {
    this.mImageAttack = imageAttack;
  }

  public void setImageSkill(String imageSkill) {
    this.mImageSkill = imageSkill;
  }

  public void setHealth(int health) {
    this.mHealth = health;
  }

  public void setStrength(int strength) {
    this.mStrength = strength;
  }

  public void setDefense(int defense) {
    this.mHealth = defense;
  }

  public void setAttackName(String attackName) {
    this.mAttackName = attackName;
  }

  public void setSkillName(String skillName) {
    this.mSkillName = skillName;
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

  // getters
  public String getName() {
    return mName;
  }

  public int getCost() {
    return mCost;
  }

  public String getImage() {
    return mImage;
  }

  public String getImageAttack() {
    return mImageAttack;
  }

  public String getImageSkill() {
    return mImageSkill;
  }

  public int getHealth() {
    return mHealth;
  }

  public String getAttackName() {
    return mAttackName;
  }

  public String getSkillName() {
    return mSkillName;
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

  public void printAll() {
    System.out.println("Name: " + mName);
    System.out.println("Health: " + mHealth);
    System.out.println("Attack Name: " + mAttackName);
    System.out.println("Skill Name: " + mSkillName);
    System.out.println("Cost: " + mCost);
  }

}
