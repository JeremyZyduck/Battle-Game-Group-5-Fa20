package battlegame;

import static org.junit.Assert.*;

import org.junit.Test;
/**
 * @author Jeremy Zyduck
 */
public class CharacterTest {
	
	@Test
	public void testSetName() {
		Character test = new Character();
		String tS = "Test Name";
		test.setName(tS);
		assertEquals (tS,test.getName());
	}

	@Test
	public void testSetCost() {
		Character test = new Character();
		int tI = 1;
		test.setCost(tI);
		assertEquals (tI,test.getCost());
	}

	@Test
	public void testSetImage() {
		Character test = new Character();
		String tS = "Test Name";
		test.setImage(tS);
		assertEquals (tS,test.getImage());
	}

	@Test
	public void testSetDamage() {
		Character test = new Character();
		int tI = 1;
		test.setDamage(tI);
		assertEquals (tI,test.getDamage());
	}

	@Test
	public void testSetHealth() {
		Character test = new Character();
		int tI = 1;
		test.setHealth(tI);
		assertEquals (tI,test.getHealth());
	}

	@Test
	public void testSetSkillName() {
		Character test = new Character();
		String tS = "Test Name";
		test.setSkillName(tS);
		assertEquals (tS,test.getSkillName());
	}

	@Test
	public void testGetName() {
		Character test = new Character();
		test.setName("test");
		assertEquals ("test",test.getName());
	}

	@Test
	public void testGetCost() {
		Character test = new Character();
		test.setCost(1);
		assertEquals (1,test.getCost());
	}

	@Test
	public void testGetImage() {
		Character test = new Character();
		test.setImage("test");
		assertEquals ("test",test.getImage());
	}

	@Test
	public void testGetDamage() {
		Character test = new Character();
		test.setDamage(1);
		assertEquals (1,test.getDamage());
	}

	@Test
	public void testGetHealth() {
		Character test = new Character();
		test.setHealth(1);
		assertEquals (1,test.getHealth());
	}

	@Test
	public void testGetSkillName() {
		Character test = new Character();
		test.setSkillName("test");
		assertEquals ("test",test.getSkillName());
	}

}
