//
// This file is a part of the Chunk Stories API codebase
// Check out README.md for more information
// Website: http://chunkstories.xyz
//

package io.xol.chunkstories.api.rendering;

import io.xol.chunkstories.api.rendering.world.WorldRenderer;

public interface RenderingPipeline {
	public RenderingInterface getRenderingInterface();
	
	public WorldRenderer getWorldRenderer();
	
	public void registerRenderPass(RenderPass pass);

	public RenderPass getRenderPass(String name);
	
	public RenderPass getCurrentPass();
}