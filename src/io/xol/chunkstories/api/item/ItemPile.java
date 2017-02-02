package io.xol.chunkstories.api.item;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import io.xol.chunkstories.api.Content;
import io.xol.chunkstories.api.entity.Inventory;
import io.xol.chunkstories.api.exceptions.NullItemException;
import io.xol.chunkstories.api.exceptions.UndefinedItemTypeException;
import io.xol.chunkstories.core.entity.components.EntityComponentInventory;

//(c) 2015-2017 XolioWare Interactive
// http://chunkstories.xyz
// http://xol.io

public class ItemPile
{
	private final Item item;
	
	private int amount = 1;

	private Inventory inventory;
	private int x;
	private int y;
	
	public ItemPile(Item item)
	{
		this.item = item;
	}

	public ItemPile(Item item, int amount)
	{
		this(item);
		this.amount = amount;
	}

	public ItemPile(ItemType type)
	{
		this(type.newItem());
	}

	public static ItemPile obtainItemPileFromStream(Content.ItemsTypes context, DataInputStream stream) throws IOException, UndefinedItemTypeException, NullItemException
	{
		int itemId = stream.readInt();
		if(itemId == 0)
			throw new NullItemException(stream);
		
		ItemType itemType = context.getItemTypeById(itemId);
		if(itemType == null)
			throw new UndefinedItemTypeException(itemId);
		
		ItemPile itemPile = new ItemPile(itemType);
		itemPile.loadInternalItemData(stream);
		
		return itemPile;
	}

	public String getTextureName()
	{
		return item.getTextureName(this);
	}

	public Item getItem()
	{
		return item;
	}

	private final void loadInternalItemData(DataInputStream stream) throws IOException
	{
		this.amount = stream.readInt();
		item.load(stream);
	}

	public final void saveItemIntoStream(DataOutputStream stream) throws IOException
	{
		stream.writeInt(item.getID());
		
		stream.writeInt(amount);
		item.save(stream);
	}

	/**
	 * Try to move an item to another slot
	 * 
	 * @param destinationInventory
	 *            new slot's inventory
	 * @param destinationX
	 * @param destinationY
	 * @return null if successfull, this if not.
	 */
	//@SuppressWarnings("unchecked")
	public boolean moveItemPileTo(Inventory destinationInventory, int destinationX, int destinationY, int amountToTransfer)
	{
		Inventory currentInventory = this.inventory;
		
		//If moving to itself
		if(destinationInventory != null && currentInventory != null && destinationInventory.equals(currentInventory))
		{
			ItemPile alreadyHere = null;
			int i = 0;
			int w = this.getItem().getSlotsWidth();
			int h = this.getItem().getSlotsHeight();
			//Tryhard to find out if it touches itself
			do
			{
				if(alreadyHere != null && alreadyHere.equals(this))
				{
					//Remove temporarily
					destinationInventory.setItemPileAt(x, getY(), null);
					
					//Check if can be placed now
					if(destinationInventory.canPlaceItemAt(destinationX, destinationY, this))
					{
						destinationInventory.setItemPileAt(destinationX, destinationY, this);
						return true;
					}
	
					//Add back if it couldn't
					destinationInventory.setItemPileAt(x, getY(), this);
					return false;
				}
				
				alreadyHere = destinationInventory.getItemPileAt(destinationX + i % w, destinationY + i / w);
				i++;
			}
			while(i < w * h);
		}
		
		//We duplicate the pile and limit it's amount
		ItemPile pileToSend = this.duplicate();
		pileToSend.setAmount(amountToTransfer);

		//The amount we're not trying to transfer
		int leftAmountBeforeTransaction = this.getAmount() - amountToTransfer;

		ItemPile leftFromTransaction = null;
		//Moving an item to a null inventory would destroy it so leftFromTransaction stays nulls in that case
		if (destinationInventory != null)
			leftFromTransaction = destinationInventory.placeItemPileAt(destinationX, destinationY, pileToSend);

		//If something was left from the transaction ( incomplete )
		if (leftFromTransaction != null)
			this.setAmount(leftAmountBeforeTransaction + leftFromTransaction.getAmount());

		//If nothing was left but we only moved part of the stuff
		else if (leftAmountBeforeTransaction > 0)
			this.setAmount(leftAmountBeforeTransaction);

		//If everything was moved we destroy this pile ... if it ever existed ( /dev/null inventories, creative mode etc )
		else if (currentInventory != null)
			currentInventory.setItemPileAt(this.x, this.getY(), null);

		//Success conditions : either we transfered all or we transfered at least one
		return leftFromTransaction == null || leftFromTransaction.getAmount() < amountToTransfer;
	}

	public ItemPile setAmount(int amount)
	{
		this.amount = amount;

		if(amount == 0 && inventory != null)
		{
			inventory.setItemPileAt(x, y, null);
			this.inventory.refreshItemSlot(x, y);
			return null;
		}
		
		if (inventory != null)
			this.inventory.refreshItemSlot(x, y);

		return this;
	}

	public int getAmount()
	{
		return amount;
	}

	public boolean canMergeWith(ItemPile itemPile)
	{
		return this.getItem().canMergeWith(itemPile.getItem());
	}

	/**
	 * Returns an exact copy of this pile ( serializes and unserializes )
	 */
	public ItemPile duplicate()
	{
		ItemPile pile = new ItemPile(this.item, this.amount);
		ByteArrayOutputStream data = new ByteArrayOutputStream();
		try
		{
			this.saveItemIntoStream(new DataOutputStream(data));
			ByteArrayInputStream stream = new ByteArrayInputStream(data.toByteArray());
			DataInputStream dis = new DataInputStream(stream);
			dis.readInt();
			pile.loadInternalItemData(dis);
		}
		catch (IOException e)
		{
		}
		return pile;
	}
	
	public String toString()
	{
		return "[ItemPile t:"+getItem()+" a:"+amount+" i:"+inventory+" x:"+x+" y:"+getY()+" ]";
	}

	public Inventory getInventory()
	{
		return inventory;
	}

	public void setInventory(EntityComponentInventory inventory)
	{
		this.inventory = inventory;
	}

	public int getX()
	{
		return x;
	}

	public void setX(int x)
	{
		this.x = x;
	}

	public int getY()
	{
		return y;
	}

	public void setY(int y)
	{
		this.y = y;
	}
}
