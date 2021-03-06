//
// This file is a part of the Chunk Stories API codebase
// Check out README.md for more information
// Website: http://chunkstories.xyz
//

package io.xol.chunkstories.api.net.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import io.xol.chunkstories.api.net.Packet;
import io.xol.chunkstories.api.net.PacketDestinator;
import io.xol.chunkstories.api.net.PacketSender;
import io.xol.chunkstories.api.net.PacketSendingContext;
import io.xol.chunkstories.api.net.PacketReceptionContext;

public class PacketText extends Packet
{
	public String text;

	@Override
	public void send(PacketDestinator destinator, DataOutputStream out, PacketSendingContext context) throws IOException
	{
		out.writeUTF(text);
	}

	public void process(PacketSender sender, DataInputStream in, PacketReceptionContext context) throws IOException
	{
		text = in.readUTF();
		
		//Actual handling is left to internal code defined in proprietary subclasses that expose the inner workings
		//( see clientClass and serverClass within .packets files )
	}
}
