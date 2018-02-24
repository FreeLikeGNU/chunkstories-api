//
// This file is a part of the Chunk Stories API codebase
// Check out README.md for more information
// Website: http://chunkstories.xyz
//

package io.xol.chunkstories.api.rendering;

import io.xol.chunkstories.api.rendering.Primitive;
import io.xol.chunkstories.api.rendering.pipeline.AttributesConfiguration;
import io.xol.chunkstories.api.rendering.pipeline.PipelineConfiguration;
import io.xol.chunkstories.api.rendering.pipeline.ShaderInterface;
import io.xol.chunkstories.api.rendering.pipeline.TexturingConfiguration;
import io.xol.chunkstories.api.rendering.pipeline.UniformsConfiguration;

/**
 * Describes precisely everything needed to draw something on screen
 */
public interface RenderingCommand extends Renderable
{
	public Primitive getPrimitive();
	
	public ShaderInterface getShader();

	public TexturingConfiguration getBoundTextures();

	public AttributesConfiguration getAttributesConfiguration();

	public UniformsConfiguration getUniformsConfiguration();

	public PipelineConfiguration getPipelineConfiguration();
	
	/**
	 * Used to automatically instanciate similar rendering commands
	 */
	//Not actually used, renderer is too complex already, instead we'll encourage modders to optimize their DC.
	//public boolean canMerge(RenderingCommand renderingCommand);
}
