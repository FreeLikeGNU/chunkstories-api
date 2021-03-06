//
// This file is a part of the Chunk Stories API codebase
// Check out README.md for more information
// Website: http://chunkstories.xyz
//

package io.xol.chunkstories.api.plugin.commands;

public interface CommandHandler
{
	public boolean handleCommand(CommandEmitter emitter, Command command, String[] arguments);
}
