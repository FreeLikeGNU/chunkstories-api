package io.xol.chunkstories.api.entity.components;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Iterator;

import io.xol.chunkstories.api.csf.StreamSource;
import io.xol.chunkstories.api.csf.StreamTarget;
import io.xol.chunkstories.api.entity.Controller;
import io.xol.chunkstories.api.entity.Entity;
import io.xol.chunkstories.api.entity.interfaces.EntityControllable;
import io.xol.chunkstories.entity.EntityComponents;
import io.xol.chunkstories.net.packets.PacketEntity;

//(c) 2015-2016 XolioWare Interactive
//http://chunkstories.xyz
//http://xol.io

/**
 * To make a new entity component, just extend this class and mention your class a .components file in the entity/ directory so it gets an unique id
 */
public abstract class EntityComponent
{
	protected Entity entity;
	EntityComponent next;
	
	public EntityComponent(Entity entity)
	{
		this(entity, entity.getComponents().getLastComponent());
	}
	
	public EntityComponent(Entity entity, EntityComponent previous)
	{
		this.entity = entity;
		if(previous != null)
			previous.setNext(this);
	}
	
	public void setNext(EntityComponent next)
	{
		this.next = next;
	}
	
	public EntityComponent getLastComponent()
	{
		if(next != null)
			return next.getLastComponent();
		else return this;
	}
	
	/**
	 * Push will tell all subscribers of the entity about a change of this component only
	 */
	public void pushComponentEveryone()
	{
		Iterator<Subscriber> iterator = entity.getAllSubscribers();
		while(iterator.hasNext())
		{
			Subscriber subscriber = iterator.next();
			
			PacketEntity packet = new PacketEntity(subscriber.getUUID() == -1);
			packet.entityToUpdate = entity;
			
			packet.updateOneComponent = this;
			subscriber.pushPacket(packet);
		}
	}
	
	/**
	 * Push the component to the controller, if such one exists
	 */
	public void pushComponentController()
	{
		if(this.entity instanceof EntityControllable)
		{
			Controller controller = ((EntityControllable) this.entity).getControllerComponent().getController();
			if(controller != null)
			{
				pushComponent(controller);
			}
		}
	}
	
	/**
	 * Push the component to everyone but the controller, if such one exists
	 */
	public void pushComponentEveryoneButController()
	{
		//System.out.println("pushing2all");
		Iterator<Subscriber> iterator = entity.getAllSubscribers();
		
		Controller controller = null;
		if(entity instanceof EntityControllable)
		{
			controller = ((EntityControllable) entity).getControllerComponent().getController();
		}
		while(iterator.hasNext())
		{
			Subscriber subscriber = iterator.next();
			
			//Don't push the update to the controller.
			if(controller != null && subscriber.equals(controller))
				continue;
			
			PacketEntity packet = new PacketEntity(subscriber.getUUID() == -1);
			packet.entityToUpdate = entity;
			
			packet.updateOneComponent = this;
			subscriber.pushPacket(packet);
		}
	}
	
	public void pushComponent(Subscriber subscriber)
	{
		//You may check that subscriber has subscribed to said entity ?
		//Re : nope because we send the EntityExistence (hint: false) component to [just] unsubscribed guys so it wouldn't work
		
		PacketEntity packet = new PacketEntity(subscriber.getUUID() == -1);
		packet.entityToUpdate = entity;
		
		//Set the packet to "just update that component" mode
		packet.updateOneComponent = this;
		subscriber.pushPacket(packet);
	}
	
	public void pushComponentInStream(StreamTarget to, DataOutputStream dos) throws IOException
	{
		dos.writeInt(getEntityComponentId());
		push(to, dos);
	}
	
	public void pushAllComponentsInStream(StreamTarget to, DataOutputStream dos) throws IOException
	{
		pushComponentInStream(to, dos);
		if(next != null)
			next.pushAllComponentsInStream(to, dos);
	}

	public void pushAllComponents(Subscriber subscriber)
	{
		PacketEntity packet = new PacketEntity(subscriber.getUUID() == -1);
		packet.entityToUpdate = entity;
		
		//Set the packet to "update everything in the chained list" mode
		packet.updateManyComponents = this;
		subscriber.pushPacket(packet);
	}
	
	public boolean tryPullComponentInStream(int componentId, StreamSource from, DataInputStream dis) throws IOException
	{
		//Does the Id match ?
		if(this.getEntityComponentId() == componentId)
		{
			pull(from, dis);
			return true;
		}
		//Chain next component
		if(next != null)
			return next.tryPullComponentInStream(componentId, from, dis);
		return false;
	}

	protected abstract void push(StreamTarget destinator, DataOutputStream dos) throws IOException;
	
	protected abstract void pull(StreamSource from, DataInputStream dis) throws IOException;

	public int getEntityComponentId(){
		//System.out.println("debug : "+this.getClass().getName()+" id = "+EntityComponents.getIdForClass(this.getClass().getName()));
		return EntityComponents.getIdForClass(this.getClass().getName());
	}
}
