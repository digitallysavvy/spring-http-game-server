package gameserver;


import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import pl.maciejwalkowiak.plist.*;



@RestController
public class GameServerController{

	private final AtomicLong connectionCounter = new AtomicLong();
	private final AtomicLong playerCounter = new AtomicLong();
	private final AtomicLong moveCounter = new AtomicLong();

	private boolean testing = true;

	private InetAddress serverInet;
	private String clientIP;
	private String playerMove;
	private HashMap<Integer, Player> clients = new HashMap<Integer, Player>();
	private ArrayList<GameMove> moves = new ArrayList<GameMove>();
	private String player1 = null;

	/*
	 *
	 * Clients can find their ip relative to the server
	 *
	 */
	@RequestMapping("/find")
	public ClientServerIdentifier findClientServer(HttpServletRequest request) {
        
    	try {

			serverInet = InetAddress.getLocalHost();
			clientIP = request.getRemoteAddr();

		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

        return new ClientServerIdentifier(connectionCounter.incrementAndGet(),
        							serverInet.getHostAddress(), 
        							clientIP);
    }

    /*
     *
	 * List players connected to the game server
	 *
	 */
    @RequestMapping("/players")
	public Map getPlayers(HttpServletRequest request) {
        return clients;
    }

    /*
     *
	 * Conenct players to the game server
	 *
	 */
    @RequestMapping("/connect")
	public Player connectPlayer(HttpServletRequest request) {
		
		Player player = null;
		
		if(clients.size() < 2){
			player = new Player(playerCounter.incrementAndGet(), request.getRemoteAddr());
			if(playerCounter.get() == 1){
	    		player.setColor("blue");
	    		player1 = player.getIp();
	    	}
	    	else{
	    		player.setColor("red");
	    		player.setOpponent(player1);
	    		
	    		Player blue = clients.get(player1.hashCode());
	    		blue.setOpponent(player.getIp());
	    	}

	    	clients.putIfAbsent(player.hashCode(), player);
	    	
	    	if(testing){
		    	System.out.println("Player_" + player.getId() + " connected as: " + player.hashCode());
				
				if(clients.size() == 2){
					System.out.println("Both players have connected, ready to begin game.");
				}	
	    	}
		}
		else {
			player = clients.get(request.getRemoteAddr().hashCode());
		}

        return clients.get(player.hashCode());
    }


    /*
     *
	 * Return the requested player details
	 *
	 */
    @RequestMapping(value="/player", method=RequestMethod.GET)
	public Player getPlayer(HttpServletRequest request) {

        return clients.get(request.getRemoteAddr().hashCode());

    }

    @RequestMapping(value="/player/{playerIP}", method=RequestMethod.GET)
	public Player getPlayerInfo(HttpServletRequest request, @PathVariable String playerIP) {

        return clients.get(playerIP.hashCode());

    }

    /*
     *
	 * Return the requsting player's opponent
	 *
	 */
    @RequestMapping(value="/opponent", method=RequestMethod.GET)
	public Player getOpponent(HttpServletRequest request) {

		Player player = clients.get(request.getRemoteAddr().hashCode());
		Player opponent = clients.get(player.getOpponent().hashCode());

        return opponent;

    }

    /*
     *
	 * Send the move through a GET request
	 *
	 */
    @RequestMapping(value="/sendmove/{playerMove}", method=RequestMethod.GET)
	public List<GameMove> sendMoveGET(HttpServletRequest request, @PathVariable String playerMove) {

		moves.add(new GameMove(moveCounter.incrementAndGet(), request.getRemoteAddr(), playerMove));

        return moves;

    }


    /*
     *
	 * Send the move through a POST request
	 *
	 */
    @RequestMapping(value="/sendmove", method=RequestMethod.POST)
	public List<GameMove> sendMovePOST(HttpServletRequest request, @PathVariable String playerMove) {
        
    	clientIP = request.getRemoteAddr();
		playerMove =  request.getParameter("playerMove");

        moves.add(new GameMove(moveCounter.incrementAndGet(), request.getRemoteAddr(), playerMove));

        return moves;
    }



}