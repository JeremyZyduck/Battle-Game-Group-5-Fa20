package application;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests for the Character class.
 * 
 * @author Jeremy Zyduck
 */
public class CharacterTest {  
  @Test
  public void testConstructor() {
    Character test = new Character();
    
    assertEquals(test.getName(), "");
    assertEquals(test.getCost(), 0);
    assertEquals(test.getHealth(), 0);
    assertEquals(test.getStrength(), 0);
    assertEquals(test.getDefense(), 0);
    assertNull(test.getIdleSkill());
    assertNull(test.getNormalSkill());
    assertNull(test.getSpecialSkill());
    assertEquals(test.getKey(), -1);
  }

  @Test
  /**
   * Tests the setName function from the character class
   */
  public void testSetName() {
    Character test = new Character();
    String tS = "Test Name";
    test.setName(tS);
    assertEquals(tS, test.getName());
  }

  @Test
  /**
   * Tests the setCost function from the character class.
   */
  public void testSetCost() {
    Character test = new Character();
    int tI = 1;
    test.setCost(tI);
    assertEquals(tI, test.getCost());
  }

  /*
  @Test
  public void testSetImage() {
    Character test = new Character();
    String tS = "Test Name";
    test.setImage(tS);
    assertEquals(tS, test.getImage());
  }

  @Test
  public void testSetImageAttack() {
    Character test = new Character();
    String tS = "Test Name";
    test.setImageAttack(tS);
    assertEquals(tS, test.getImageAttack());
  }

  @Test
  public void testSetImageSkill() {
    Character test = new Character();
    String tS = "Test Name";
    test.setImageSkill(tS);
    assertEquals(tS, test.getImageSkill());
  }
  */

  @Test
  /**
   * Tests the setHealth function from the character class.
   */
  public void testSetHealth() {
    Character test = new Character();
    int tI = 1;
    test.setHealth(tI);
    assertEquals(tI, test.getHealth());
  }
  
  @Test
  /**
   * Tests the setStrength function from the character class.
   */
  public void testSetStrength() {
    Character test = new Character();
    int tI = 1;
    test.setStrength(tI);
    assertEquals(tI, test.getStrength());
  }
  
  @Test
  /**
   * Tests the setDefense function from the character class.
   */
  public void testSetDefense() {
    Character test = new Character();
    int tI = 1;
    test.setDefense(tI);
    assertEquals(tI, test.getDefense());
  }
  
  @Test
  /**
   * Tests the setIdleSkill function from the character class.
   */
  public void testSetIdleSkill() {
    Character test = new Character();
    Skill tSkill = new Skill("Idle", SkillType.IDLE, 0, null);
    test.setIdleSkill(tSkill);
    assertEquals(tSkill, test.getIdleSkill());
  }
  
  @Test
  /**
   * Tests the setNormalSkill function from the character class.
   */
  public void testSetNormalSkill() {
    Character test = new Character();
    Skill tSkill = new Skill("Normal", SkillType.NORMAL, 0, null);
    test.setNormalSkill(tSkill);
    assertEquals(tSkill, test.getNormalSkill());
  }
  
  @Test
  /**
   * Tests the setSpecialSkill function from the character class.
   */
  public void testSetSpecialSkill() {
    Character test = new Character();
    Skill tSkill = new Skill("Special", SkillType.SPECIAL, 0, null);
    test.setSpecialSkill(tSkill);
    assertEquals(tSkill, test.getSpecialSkill());
  }
  
  @Test
  /**
   * Tests the setKey function from the character class.
   */
  public void testSetKey() {
    Character test = new Character();
    int tKey = 1;
    test.setKey(tKey);
    assertEquals(tKey, test.getKey());
  }

  /*
  @Test
  public void testSetAttackName() {
    Character test = new Character();
    String tS = "Test Name";
    test.setAttackName(tS);
    assertEquals(tS, test.getAttackName());
  }

  @Test
  public void testSetSkillName() {
    Character test = new Character();
    String tS = "Test Name";
    test.setSkillName(tS);
    assertEquals(tS, test.getSkillName());
  }
  */

  @Test
  /**
   * Tests the getName function from the character class.
   */
  public void testGetName() {
    Character test = new Character();
    test.setName("test");
    assertEquals("test", test.getName());
  }

  @Test
  /**
   * Tests the getCost function from the character class.
   */
  public void testGetCost() {
    Character test = new Character();
    test.setCost(1);
    assertEquals(1, test.getCost());
  }

  /*
  @Test
  public void testGetImage() {
    Character test = new Character();
    test.setImage("test");
    assertEquals("test", test.getImage());
  }

  @Test
  public void testGetImageAttack() {
    Character test = new Character();
    test.setImageAttack("test");
    assertEquals("test", test.getImageAttack());
  }

  @Test
  public void testGetImageSkill() {
    Character test = new Character();
    test.setImageSkill("test");
    assertEquals("test", test.getImageSkill());
  }
  */

  @Test
  /**
   * Tests the getHealth function from the character class.
   */
  public void testGetHealth() {
    Character test = new Character();
    test.setHealth(1);
    assertEquals(1, test.getHealth());
  }

  /*
  @Test
  public void testGetSkillName() {
    Character test = new Character();
    test.setSkillName("test");
    assertEquals("test", test.getSkillName());
  }

  @Test
  public void testGetAttackName() {
    Character test = new Character();
    test.setAttackName("test");
    assertEquals("test", test.getAttackName());
  }
  */
}
