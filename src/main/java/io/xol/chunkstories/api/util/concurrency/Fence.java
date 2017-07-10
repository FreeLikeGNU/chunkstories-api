package io.xol.chunkstories.api.util.concurrency;

//(c) 2015-2017 XolioWare Interactive
//http://chunkstories.xyz
//http://xol.io

/**
 * A fence can stop the program's execution until a condition is met.
 */
public interface Fence
{
	public void traverse();
}
