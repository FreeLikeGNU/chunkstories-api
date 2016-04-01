package io.xol.chunkstories.api.events.core;

import io.xol.chunkstories.api.events.CancellableEvent;
import io.xol.chunkstories.api.events.EventListeners;
import io.xol.chunkstories.api.events.categories.ClientEvent;
import io.xol.chunkstories.api.input.Input;

//(c) 2015-2016 XolioWare Interactive
//http://chunkstories.xyz
//http://xol.io

public class ClientInputPressedEvent extends CancellableEvent implements ClientEvent
{
	// Every event class has to have this

	static EventListeners listeners = new EventListeners();

	@Override
	public EventListeners getListeners()
	{
		return listeners;
	}

	public static EventListeners getListenersStatic()
	{
		return listeners;
	}

	// Specific event code
	
	public ClientInputPressedEvent(Input input)
	{
		this.input = input;
	}
	
	Input input;
	
	public Input getInputPressed()
	{
		return input;
	}

}
