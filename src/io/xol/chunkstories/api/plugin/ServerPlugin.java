package io.xol.chunkstories.api.plugin;

import io.xol.chunkstories.api.server.ServerInterface;

//(c) 2015-2016 XolioWare Interactive
//http://chunkstories.xyz
//http://xol.io

/** A type of plugin that exclusivly runs on the client */
public abstract class ServerPlugin extends ChunkStoriesPlugin
{
	private final ServerInterface serverInterface;

	public ServerPlugin(PluginInformation pluginInformation, ServerInterface clientInterface)
	{
		super(pluginInformation, clientInterface);
		this.serverInterface = clientInterface;
	}

	public ServerInterface getServer()
	{
		return serverInterface;
	}
}
