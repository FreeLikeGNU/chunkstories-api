package io.xol.chunkstories.api.events;

//(c) 2015-2016 XolioWare Interactive
//http://chunkstories.xyz
//http://xol.io

public interface EventExecutor
{
	public void fireEvent(Event event) throws Exception;
}
