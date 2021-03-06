//
// This file is a part of the Chunk Stories API codebase
// Check out README.md for more information
// Website: http://chunkstories.xyz
//

package io.xol.chunkstories.api.exceptions.rendering;

/**
 * Rendering functions should only be called inside the main rendering thread.
 * To check if you are in the proper thread, please use GameWindowOpenGL.isMainGLWindow()
 */
public class IllegalRenderingThreadException extends RuntimeException
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8543094072345220060L;

}
