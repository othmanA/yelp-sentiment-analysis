


public class Review{
   	private String business_id;
   	private String date;
   	private String review_id;
   	private Number stars;
   	private String text;
   	private String type;
   	private String user_id;
   	private Votes votes;

 	public String getBusiness_id(){
		return this.business_id;
	}
	public void setBusiness_id(String business_id){
		this.business_id = business_id;
	}
 	public String getDate(){
		return this.date;
	}
	public void setDate(String date){
		this.date = date;
	}
 	public String getReview_id(){
		return this.review_id;
	}
	public void setReview_id(String review_id){
		this.review_id = review_id;
	}
 	public Number getStars(){
		return this.stars;
	}
	public void setStars(Number stars){
		this.stars = stars;
	}
 	public String getText(){
		return this.text;
	}
	public void setText(String text){
		this.text = text;
	}
 	public String getType(){
		return this.type;
	}
	public void setType(String type){
		this.type = type;
	}
 	public String getUser_id(){
		return this.user_id;
	}
	public void setUser_id(String user_id){
		this.user_id = user_id;
	}
 	public Votes getVotes(){
		return this.votes;
	}
	public void setVotes(Votes votes){
		this.votes = votes;
	}
}