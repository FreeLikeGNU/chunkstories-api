//
// This file is a part of the Chunk Stories API codebase
// Check out README.md for more information
// Website: http://chunkstories.xyz
//

package io.xol.chunkstories.api.plugin.commands;

import io.xol.chunkstories.api.GameContext;

/** Describes a command installed by the game itself */
public class SystemCommand extends Command {

	final GameContext gameContext;
	
	public SystemCommand(GameContext gameContext, String name) {
		super(name);
		
		this.gameContext = gameContext;
	}

	public GameContext getGameContext() {
		return gameContext;
	}

}
