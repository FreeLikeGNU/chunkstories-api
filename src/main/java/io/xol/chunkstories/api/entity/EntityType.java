package io.xol.chunkstories.api.entity;

import io.xol.chunkstories.api.Content.EntityTypes;
import io.xol.chunkstories.api.content.NamedWithProperties;
import io.xol.chunkstories.api.world.World;

//(c) 2015-2017 XolioWare Interactive
//http://chunkstories.xyz
//http://xol.io

public interface EntityType extends NamedWithProperties
{
	public String getName();
	
	public short getId();
	
	public Entity create(World world);
	
	public EntityTypes store();
}
