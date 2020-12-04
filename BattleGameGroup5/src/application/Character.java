package application;
/**
 * @author Jeremy Zyduck
 */
public class Character {
	
	//Private variables
	private String name; 
	private int cost; //How much the character will cost
	private String image; //Character images will reference a link to an image stored on the web server
	private String imageAttack;
	private String imageSkill;
	private int strength; 
	private int defense; 
	private int health;  
	private String attackName;
	private int attackOption;
	private String skillName;
	private int skillOption;
	
	//setters
	public void setName(String name) {
		this.name = name;
	}
	public void setCost(int cost) {
		this.cost = cost;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public void setImageAttack(String imageAttack) {
		this.imageAttack = imageAttack;
	}
	public void setImageSkill(String imageSkill) {
		this.imageSkill = imageSkill;
	}
	public void setStrength(int strength) {
		this.strength = strength;
	}
	public void setDefense(int defense) {
		this.defense = defense;
	}
	public void setHealth(int health) {
		this.health = health;
	}
	public void setAttackName(String attackName) {
		this.attackName = attackName;
	}
	public void setAttackOption(int attackOption) {
		this.attackOption = attackOption;
	}
	public void setSkillName(String skillName) {
		this.skillName = skillName;
	}
	public void setSkillOption(int skillOption) {
		this.skillOption = skillOption;
	}
	
	//getters
	public String getName() {
		return name;
	}
	public int getCost() {
		return cost;
	}
	public String getImage() {
		return image;
	}
	public String getImageAttack() {
		return imageAttack;
	}
	public String getImageSkill() {
		return imageSkill;
	}
	public int getStrength() {
		return strength;
	}
	public int getDefense() {
		return defense;
	}
	public int getHealth() {
		return health;
	}
	public String getAttackName() {
		return attackName;
	}
	public int getAttackOption() {
		return attackOption;
	}
	public String getSkillName() {
		return skillName;
	}
	public int getSkillOption() {
		return skillOption;
	}
	public void printAll() {
		System.out.println("Name: " + name);
		System.out.println("Health: " + health);
		System.out.println("Defense: " + defense);
		System.out.println("Strength: " + strength);
		System.out.println("Attack Name: " + attackName);
		System.out.println("Attack Option: " + attackOption);
		System.out.println("Skill Name: " + skillName);
		System.out.println("Skill Option: " + skillOption);
		System.out.println("Cost: " + cost);
		//TODO image stuff
	}
	
	
	

}
