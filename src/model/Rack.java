package model;

import java.util.ArrayList;

/**
 * Represents a metal frame called rack and used to store
 * the servers. It can only exist inside a MiniRoom object.
 */
public class Rack{
	/**
	 * Collection of servers.
	 */
	private ArrayList<Server> servers;

	/**
	 * Constructor of the class.
	 */
	public Rack(Server[] preServers){
		this.servers = new ArrayList<Server>();
		for (int i=0; i<preServers.length; i++){
			servers.add(preServers[i]);
		}
	}

	/**
	 * Returns the sum of the ram memory of all servers.
	 * @return count int.
	 */
	public int getTotalRamMemory(){
		int count = 0;
		for (int i=0; i<servers.size(); i++){
			count += servers.get(i).getRamMemory();
		}
		return count;
	}

	/**
	 * Returns the sum of the disk capacity of all servers.
	 * @return count double.
	 */
	public double getTotalDiskCapacity(){
		double count = 0;
		for (int i=0; i<servers.size(); i++){
			count += servers.get(i).getDiskCapacity();
		}
		return count;
	}

	// Getters and Setters

	public ArrayList<Server> getServers() {
		return this.servers;
	}

	public void setServers(ArrayList<Server> servers) {
		this.servers = servers;
	}
}