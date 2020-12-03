package application;

public class Skill
{
	//name of the skill
	private String mName;
	//determines the skill's type. 0 <- basic attack, 1 <- special attack, 2 <- block
	private int mType;
	//damage dealt or prevented by skill
	private double mPower;
	
	
	/**
	 * Constructor for the Skill class. Sets member variables equal to corresponding parameters
	 * 
	 * @param name - the skill's name
	 * @param type - the skill's type
	 * @param power - the skill's power
	 */
	public Skill(String name, int type, double power){
		this.mName = name;
		this.mType = type;
		this.mPower = power;
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
	public int getType(){
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
	public void setType(int type){
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
	
}
