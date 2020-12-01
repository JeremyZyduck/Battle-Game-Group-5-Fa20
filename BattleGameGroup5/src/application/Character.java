package battlegame;
/**
 * @author Jeremy Zyduck
 */
public class Character {
	
	//Private variables
	private String name; 
	private int cost; 
	private String image; //Character image will reference a link to an image stored on the web server
	private int damage; 
	private int health; 
	private String skillName; //Unsure of how skills will work. Sticking with just adding the name for now.
	
	//Setters
	public void setName(String name) {
		this.name = name;
	}
	public void setCost(int cost) {
		this.cost = cost;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public void setDamage(int damage) {
		this.damage = damage;
	}
	public void setHealth(int health) {
		this.health = health;
	}
	public void setSkillName(String skillName) {
		this.skillName = skillName;
	}
	
	//Getters
	public String getName() {
		return name;
	}
	public int getCost() {
		return cost;
	}
	public String getImage() {
		return image;
	}
	public int getDamage() {
		return damage;
	}
	public int getHealth() {
		return health;
	}
	public String getSkillName() {
		return skillName;
	}

}
