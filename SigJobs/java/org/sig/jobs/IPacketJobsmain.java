package com.Alex.jobsmod;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.player.EntityPlayerMP;

public interface IPacketJobsmain {

	public void executeClient(EntityClientPlayerMP player);

	public void executeServer(EntityPlayerMP player);

	public void writeBytes(ByteBuf Bytes);

	public void readBytes(ByteBuf Bytes);

}
