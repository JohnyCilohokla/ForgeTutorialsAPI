package com.forgetutorials.lib.utilities;

import io.netty.buffer.ByteBuf;

public interface ICustomDataReceiver {
	public void onDataReceived(ByteBuf data);
}
