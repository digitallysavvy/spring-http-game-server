package gameserver;


public class Player {

	private long id;
	private String ip;
	private String color;
	private String opponent = null;


	public Player(long id, String ip){
		this.id = id;
		this.ip = ip;
	}
	
	public Player(long id, String ip, String color){
		this.id = id;
		this.ip = ip;
		this.color = color;
	}

	public long getId(){
		return id;
	}

	public String getIp(){
		return ip;
	}

	public String getColor(){
		return color;
	}

	public void setColor(String color){
		this.color = color;
	}

	public String getOpponent(){
		return opponent;
	}

	public void setOpponent(String opponent){
		this.opponent = opponent;
	}

	public int hashCode(){
		return ip.hashCode();
	}
}