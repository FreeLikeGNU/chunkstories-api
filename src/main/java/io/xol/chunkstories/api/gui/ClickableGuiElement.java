//
// This file is a part of the Chunk Stories API codebase
// Check out README.md for more information
// Website: http://chunkstories.xyz
//

package io.xol.chunkstories.api.gui;

import io.xol.chunkstories.api.input.Mouse.MouseButton;

public interface ClickableGuiElement {
	public boolean handleClick(MouseButton mouseButton);
}
