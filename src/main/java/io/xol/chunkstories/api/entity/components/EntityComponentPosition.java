package io.xol.chunkstories.api.entity.components;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.joml.Vector3dc;

import io.xol.chunkstories.api.Location;
import io.xol.chunkstories.api.entity.Entity;
import io.xol.chunkstories.api.serialization.StreamSource;
import io.xol.chunkstories.api.serialization.StreamTarget;
import io.xol.chunkstories.api.world.World;
import io.xol.chunkstories.api.world.WorldMaster;
import io.xol.chunkstories.api.world.chunk.Region;

//(c) 2015-2017 XolioWare Interactive
//http://chunkstories.xyz
//http://xol.io

public class EntityComponentPosition extends EntityComponent
{
	public EntityComponentPosition(Entity entity, EntityComponent next)
	{
		super(entity, next);
	}

	//private SafeWriteLock safetyLock = new SafeWriteLock();
	private Location pos = new Location(entity.getWorld(), 0, 0, 0);
	private Region regionWithin;
	
	public void setLocation(Location location)
	{	
		assert location != null;
		
		this.pos = location;

		checkPositionAndUpdateHolder();
		
		//Push updates to everyone subscribed to this
		//In client mode it means that the controlled entity has the server subscribed so it will update it's status to the server
		
		//In server/master mode any drastic location change is told to everyone, as setLocation() is not called when the server receives updates
		//from the controller but only when an external event changes the location.
		this.pushComponentEveryone();
	}
	
	public void setPosition(Vector3dc position)
	{
		this.pos.x = (position.x());
		this.pos.y = (position.y());
		this.pos.z = (position.z());
		
		checkPositionAndUpdateHolder();

		//Same logic as above, refactoring should be done for clarity tho
		this.pushComponentEveryone();
	}
	
	public void setPosition(double x, double y, double z)
	{
		this.pos.x = (x);
		this.pos.y = (y);
		this.pos.z = (z);
		
		checkPositionAndUpdateHolder();

		//Same logic as above, refactoring should be done for clarity tho
		this.pushComponentEveryone();
	}
	
	public void setWorld(World world)
	{
		this.pos.setWorld(world);

		checkPositionAndUpdateHolder();
	}

	public Location getLocation()
	{
		//safetyLock.beginRead();
		Location pos = this.pos;
		//safetyLock.endRead();
		return new Location(pos.getWorld(), pos);
	}
	
	public Region getRegionWithin()
	{
		return regionWithin;
	}

	@Override
	public void push(StreamTarget to, DataOutputStream dos) throws IOException
	{
		dos.writeDouble(pos.x());
		dos.writeDouble(pos.y());
		dos.writeDouble(pos.z());
	}

	@Override
	public void pull(StreamSource from, DataInputStream dis) throws IOException
	{
		if(pos == null)
			pos = new Location(this.entity.getWorld(), 0, 0, 0);
		
		pos.x = (dis.readDouble());
		pos.y = (dis.readDouble());
		pos.z = (dis.readDouble());
		
		checkPositionAndUpdateHolder();
		
		//Position updates received by the server should be told to everyone but the controller
		if(entity.getWorld() instanceof WorldMaster)
			pushComponentEveryoneButController();
	}
	
	public void trySnappingToRegion()
	{
		checkPositionAndUpdateHolder();
	}
	
	/**
	 * Prevents entities from going outside the world area and updates the parentHolder reference
	 */
	protected final boolean checkPositionAndUpdateHolder()
	{
		pos.x = (pos.x() % entity.getWorld().getWorldSize());
		pos.z = (pos.z() % entity.getWorld().getWorldSize());
		if (pos.x() < 0)
			pos.x = (pos.x() + entity.getWorld().getWorldSize());
		if (pos.z() < 0)
			pos.z = (pos.z() + entity.getWorld().getWorldSize());
		int regionX = (int) (pos.x() / (32 * 8));
		int regionY = (int) (pos.y() / (32 * 8));
		if (regionY < 0)
			regionY = 0;
		if (regionY > entity.getWorld().getMaxHeight() / (32 * 8))
			regionY = entity.getWorld().getMaxHeight() / (32 * 8);
		int regionZ = (int) (pos.z() / (32 * 8));
		
		//Don't touch updates once the entity was removed
		if(!entity.exists())
			return false;
		
		//Entities not in the world should never be added to it
		if(!entity.hasSpawned())
			return false;
		
		if (regionWithin != null && regionWithin.getRegionX() == regionX && regionWithin.getRegionY() == regionY && regionWithin.getRegionZ() == regionZ)
		{
			return false; // Nothing to do !
		}
		else
		{
			if(regionWithin != null)
				regionWithin.removeEntityFromRegion(entity);
		
			regionWithin = entity.getWorld().getRegionChunkCoordinates(regionX * 8, regionY * 8, regionZ * 8);
			//When the region is loaded, add this entity to it.
			if(regionWithin != null)// && regionWithin.isDiskDataLoaded())
				regionWithin.addEntityToRegion(entity);
			
			return true;
		}
	}
}
