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
	private int health;  
	private String attackName;
	private String skillName;
	
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
	
	public void setHealth(int health) {
		this.health = health;
	}
	
	public void setAttackName(String attackName) {
		this.attackName = attackName;
	}
	
	public void setSkillName(String skillName) {
		this.skillName = skillName;
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

	public int getHealth() {
		return health;
	}
	
	public String getAttackName() {
		return attackName;
	}

	public String getSkillName() {
		return skillName;
	}
	
	public void printAll() {
		System.out.println("Name: " + name);
		System.out.println("Health: " + health);
		System.out.println("Attack Name: " + attackName);
		System.out.println("Skill Name: " + skillName);
		System.out.println("Cost: " + cost);
	}
	
	
	

}
