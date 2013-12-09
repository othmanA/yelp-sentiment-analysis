/**
 * 
 * This class is not going to be used in the project. 
 * I created only of the Json Parser, because it's required to map each variable in the json array to a property in a class.
 * @author Othman Abahossain
 *
 */

public class Votes{
   	private Number cool;
   	private Number funny;
   	private Number useful;

 	public Number getCool(){
		return this.cool;
	}
	public void setCool(Number cool){
		this.cool = cool;
	}
 	public Number getFunny(){
		return this.funny;
	}
	public void setFunny(Number funny){
		this.funny = funny;
	}
 	public Number getUseful(){
		return this.useful;
	}
	public void setUseful(Number useful){
		this.useful = useful;
	}
}
