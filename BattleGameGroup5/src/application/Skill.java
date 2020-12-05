package application;

import java.awt.image.BufferedImage;

public class Skill
{
	//name of the skill
	private String mName;
	//determines the skill's type. 0 <- idle, 1 <- normal attack, 2 <- special attack
	private SkillType mType;
	//damage dealt or prevented by skill
	private double mPower;
	
	// Image associate with the skill
	private BufferedImage mImage;
	
	
	/**
	 * Constructor for the Skill class. Sets member variables equal to corresponding parameters
	 * 
	 * @param name - the skill's name
	 * @param type - the skill's type
	 * @param power - the skill's power
	 * @param image - the skill's image
	 */
	public Skill(String name, SkillType type, double power, BufferedImage image){
		this.mName = name;
		this.mType = type;
		this.mPower = power;
		this.mImage = image;
	}
	
	/**
	 * Getter for the skill's name
	 * 
	 * @return mName
	 */
	public String getName(){
		return this.mName;
	}
	
	/**
	 * Getter for the skill's type
	 * 
	 * @return mName
	 */
	public SkillType getType(){
		return this.mType;
	}
	
	/**
	 * Getter for the skill's power
	 * 
	 * @return mName
	 */
	public double getPower(){
		return this.mPower;
	}
	
	/**
	 * Getter for the skill's image
	 * 
	 * @return mImage
	 */
	public BufferedImage getImage() {
	  return this.mImage;
	}
	
	/**
	 * Setter for the skill's name
	 * 
	 * @param name - the skill's name
	 */
	public void setName(String name){
		this.mName = name;
	}
	
	/**
	 * Setter for the skill's type
	 * 
	 * @param type - the skill's type
	 */
	public void setType(SkillType type){
		this.mType = type;
	}
	
	/**
	 * Setter for the skill's power
	 * 
	 * @param power - the skill's power
	 */
	public void  setPower(double power){
		this.mPower = power;
	}
	
	/**
	 * Setter for the skill's image.
	 * 
	 * @param image - the skill's image
	 */
	public void setImage(BufferedImage image) {
	  this.mImage = image;
	}
	
}
