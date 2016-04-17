package gameserver;


public class ClientServerIdentifier{

	private final long id;
	private final String server;
	private final String client;

	public ClientServerIdentifier(long id, String server, String client){
		this.id = id;
		this.server = server;
		this.client = client;

	}


	public long getId(){
		return id;
	}


	public String getserver(){
		return server;
	}

	public String getClient(){
		return client;
	}

}