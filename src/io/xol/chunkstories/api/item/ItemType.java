package io.xol.chunkstories.api.item;

//(c) 2015-2016 XolioWare Interactive
//http://chunkstories.xyz
//http://xol.io

/**
 * Immutable, describes an item type and is a common reference in all items of that type
 * It gets loaded from the .items file
 */
public interface ItemType
{
	/**
	 * @return Returns the associated ID in the .items files
	 */
	public int getID();
	
	/**
	 * @return The name this item is declared by
	 */
	public String getInternalName();

	public int getSlotsWidth();

	public int getSlotsHeight();

	public int getMaxStackSize();
	
	/**
	 * @param propertyName Name of the property to look for
	 * @param defaultValue The value to return if the above isn't present
	 * @return Arbitrary properties defined in .items files
	 */
	public String getProperty(String propertyName, String defaultValue);

	public Item newItem();
}