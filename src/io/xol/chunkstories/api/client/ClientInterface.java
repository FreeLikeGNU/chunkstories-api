package io.xol.chunkstories.api.client;

import io.xol.chunkstories.api.entity.ClientSideController;
import io.xol.chunkstories.api.entity.Inventory;
import io.xol.chunkstories.api.input.InputsManager;
import io.xol.chunkstories.api.particles.ParticlesManager;
import io.xol.chunkstories.api.rendering.effects.DecalsManager;
import io.xol.chunkstories.api.sound.SoundManager;
import io.xol.chunkstories.api.world.WorldClient;
import io.xol.chunkstories.content.PluginsManager;

import io.xol.chunkstories.world.WorldClientCommon;

//(c) 2015-2016 XolioWare Interactive
//http://chunkstories.xyz
//http://xol.io

public interface ClientInterface
{
	public ClientSideController getClientSideController();
	
	public SoundManager getSoundManager();
	
	public PluginsManager getPluginsManager();

	public InputsManager getInputsManager();

	/** Prints some text into the client chat */
	public void printChat(String textToPrint);
	
	/** Returns the currently played world, if such exist or null */
	public WorldClient getWorld();
	
	/** Changes the game to a new world */
	public void changeWorld(WorldClientCommon world);
	
	/** Closes current world and exits to main menu */
	public void exitToMainMenu();
	
	/** Closes current world and exits to main menu with an error message*/
	public void exitToMainMenu(String errorMessage);
	
	/**
	 * Opens the inventory GUI with the controlled entity's inventory, if applicable
	 * @param otherInventory If not null, opens this other inventory as well
	 */
	public void openInventory(Inventory otherInventory);

	/**
	 * @return Is the game GUI in focus or obstructed by other things ?
	 */
	public boolean hasFocus();
	
	/**
	 * Reloads all assets, shaders, sounds and whatnot from the mods and the main game.
	 */
	public void reloadAssets();

	public ParticlesManager getParticlesManager();

	public DecalsManager getDecalsManager();
}
