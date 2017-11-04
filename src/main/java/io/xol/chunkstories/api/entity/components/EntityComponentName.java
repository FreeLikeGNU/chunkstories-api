package io.xol.chunkstories.api.entity.components;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import io.xol.chunkstories.api.entity.Entity;
import io.xol.chunkstories.api.world.serialization.StreamSource;
import io.xol.chunkstories.api.world.serialization.StreamTarget;

//(c) 2015-2017 XolioWare Interactive
//http://chunkstories.xyz
//http://xol.io

public class EntityComponentName extends EntityComponent
{
	public EntityComponentName(Entity entity, EntityComponent previous)
	{
		super(entity, previous);
	}
	String name = "";
	
	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	@Override
	protected void push(StreamTarget destinator, DataOutputStream dos) throws IOException
	{
		dos.writeUTF(name);
	}

	@Override
	protected void pull(StreamSource from, DataInputStream dis) throws IOException
	{
		name = dis.readUTF();
	}

}
