package io.xol.chunkstories.api.item.interfaces;

import io.xol.chunkstories.api.item.inventory.ItemPile;
import io.xol.chunkstories.api.rendering.RenderingInterface;

//(c) 2015-2017 XolioWare Interactive
//http://chunkstories.xyz
//http://xol.io

/**
 * An interface for items that draw on top of the 2d screen of the user ( but before actual GUI elements are)
 */
public interface ItemOverlay
{
	public void drawItemOverlay(RenderingInterface renderingInterface, ItemPile itemPile);
}
