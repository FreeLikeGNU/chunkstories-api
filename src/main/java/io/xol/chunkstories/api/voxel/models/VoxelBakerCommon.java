package io.xol.chunkstories.api.voxel.models;

import io.xol.chunkstories.api.voxel.VoxelSides.Corners;
import io.xol.chunkstories.api.voxel.models.ChunkRenderer.ChunkRenderContext.VoxelLighter;

//(c) 2015-2017 XolioWare Interactive
//http://chunkstories.xyz
//http://xol.io

import io.xol.chunkstories.api.voxel.textures.VoxelTexture;

/**
 * Abstract.
 * Used to bake static voxel mesh into, each family of function must be called in order ( vert, tex, col, norm ) 3 times to form triangles
 */
public interface VoxelBakerCommon
{
	/*public void addTexCoordInt(int i0, int i1);

	public void addColors(float[] t);
	
	public void addColors(byte sunLight, byte blockLight, byte ao);

	public void addColorsSpecial(float[] t, int extended);

	public void addColors(float f0, float f1, float f2);

	public void addColorsSpecial(float f0, float f1, float f2, int extended);

	public void addNormalsInt(int i0, int i1, int i2, byte extra);*/
	
	public void setVoxelLight(byte sunLight, byte blockLight, byte ao);

	public void setVoxelLightAuto(VoxelLighter voxelLighter, Corners corner);
	
	public void usingTexture(VoxelTexture voxelTexture);
	
	public void setTextureCoordinates(float s, float t);
	
	public void setNormal(float x, float y, float z);
	
	public void setWavyFlag(boolean wavy);
	
	public abstract void endVertex();
}
