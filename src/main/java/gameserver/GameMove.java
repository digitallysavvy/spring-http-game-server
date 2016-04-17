package gameserver;


public class GameMove {

	long id;
	String player;
	String move;


	public GameMove(long id, String player, String move){
		this.id = id;
		this.player = player;
		this.move = move;
	}


	public long getId(){
		return id;
	}

	public String getPlayer(){
		return player;
	}

	public String getMove(){
		return move;
	}

}