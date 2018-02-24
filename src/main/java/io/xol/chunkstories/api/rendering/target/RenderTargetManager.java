//
// This file is a part of the Chunk Stories API codebase
// Check out README.md for more information
// Website: http://chunkstories.xyz
//

package io.xol.chunkstories.api.rendering.target;

import org.joml.Vector4fc;

public interface RenderTargetManager
{
	/**
	 * Get the current FBO bound
	 */
	public RenderTargetAttachementsConfiguration getCurrentConfiguration();
	
	/**
	 * Binds a new FBO to render to
	 */
	public void setConfiguration(RenderTargetAttachementsConfiguration fbo);
	
	/** Creates a new configuration, depth might be = null and you are prompted to provide an arbitrary number of colour buffers */
	public RenderTargetAttachementsConfiguration newConfiguration(RenderTarget depth, RenderTarget... colors);
	
	/**
	 * Clears both types of bound rendertargets
	 * Equivalent to calling clearBoundRenderTargetZ(1.0f); and clearBoundRenderTargetColor(null);
	 */
	public void clearBoundRenderTargetAll();
	
	/**
	 * Clears the depth render target (if bound) to the level specified
	 */
	public void clearBoundRenderTargetZ(float z);
	
	/**
	 * Clears the color render target (if bound) to the color specified
	 * Giving a null Vector4fm is assummed to mean Vector4fm(0, 0, 0, 0);
	 */
	public void clearBoundRenderTargetColor(Vector4fc color);
	
	/**
	 * Enables or disable the depth mask ( wether or not depth information is written )
	 */
	public void setDepthMask(boolean on);
	
	/**
	 * @return Wether the depth mask is active or not
	 */
	public boolean getDepthMask();
}
