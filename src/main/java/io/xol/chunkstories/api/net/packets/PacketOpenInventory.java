//
// This file is a part of the Chunk Stories API codebase
// Check out README.md for more information
// Website: http://chunkstories.xyz
//

package io.xol.chunkstories.api.net.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import io.xol.chunkstories.api.client.ClientInterface;
import io.xol.chunkstories.api.entity.Entity;
import io.xol.chunkstories.api.entity.interfaces.EntityWithInventory;
import io.xol.chunkstories.api.exceptions.PacketProcessingException;
import io.xol.chunkstories.api.item.inventory.Inventory;
import io.xol.chunkstories.api.item.inventory.InventoryTranslator;
import io.xol.chunkstories.api.net.PacketDestinator;
import io.xol.chunkstories.api.net.PacketReceptionContext;
import io.xol.chunkstories.api.net.PacketSender;
import io.xol.chunkstories.api.net.PacketSendingContext;
import io.xol.chunkstories.api.net.PacketWorld;
import io.xol.chunkstories.api.world.World;

public class PacketOpenInventory extends PacketWorld
{
	protected Inventory inventory;
	
	public PacketOpenInventory(World world) {
		super(world);
	}
	
	public PacketOpenInventory(World world, Inventory inventory) {
		super(world);
		this.inventory = inventory;
	}

	@Override
	public void send(PacketDestinator destinator, DataOutputStream out, PacketSendingContext context) throws IOException
	{
		InventoryTranslator.writeInventoryHandle(out, inventory);
	}

	@Override
	public void process(PacketSender sender, DataInputStream in, PacketReceptionContext processor) throws IOException, PacketProcessingException
	{
		inventory = InventoryTranslator.obtainInventoryHandle(in, processor);
		if(processor.getContext() instanceof ClientInterface) {
			ClientInterface client = (ClientInterface) processor.getContext();
			Entity currentControlledEntity = client.getPlayer().getControlledEntity();
			
			if(currentControlledEntity != null && currentControlledEntity instanceof EntityWithInventory)
				client.openInventories(((EntityWithInventory) currentControlledEntity).getInventory(), inventory);
			else
				client.openInventories(inventory);
		}
	}

}
