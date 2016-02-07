package io.xol.chunkstories.api.plugin.server;

import io.xol.chunkstories.api.Location;
import io.xol.chunkstories.entity.Entity;

//(c) 2015-2016 XolioWare Interactive
//http://chunkstories.xyz
//http://xol.io

public interface Player
{
	/**
	 * Returns the username of the player
	 * @return
	 */
	public String getName();
	
	/**
	 * Returns the entity this player is controlling
	 * @return
	 */
	public Entity getControlledEntity();
	
	/**
	 * Sends a text message to this player chat
	 * @param msg
	 */
	public void sendTextMessage(String msg);
	
	/**
	 * Gets the location of the user
	 * @return a {@link Location} object
	 */
	public Location getPosition();
	
	/**
	 * Sets the location of the user
	 * @param l a {@link Location} object
	 */
	public void setPosition(Location l);
	
	/**
	 * Kicks the player
	 * @param reason
	 */
	public void kickPlayer(String reason);
	
	public boolean isConnected();
}
