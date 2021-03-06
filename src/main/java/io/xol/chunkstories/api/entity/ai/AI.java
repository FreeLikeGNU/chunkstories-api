//
// This file is a part of the Chunk Stories API codebase
// Check out README.md for more information
// Website: http://chunkstories.xyz
//

package io.xol.chunkstories.api.entity.ai;

import io.xol.chunkstories.api.entity.Entity;

public abstract class AI<T extends Entity>
{
	protected T entity;
	protected AiTask currentTask;
	
	public abstract class AiTask {

		public abstract void execute();
	}
	
	public AI(T entity)
	{
		this.entity = entity;
	}
	
	public void tick()
	{
		if(currentTask != null)
			currentTask.execute();
	}
	
	public void setAiTask(AiTask nextTask)
	{
		this.currentTask = nextTask;
	}
	
	public AiTask currentTask()
	{
		return currentTask;
	}
}
