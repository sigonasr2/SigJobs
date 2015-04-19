package org.sig.jobs.Commands;

import java.text.NumberFormat;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class TimeCommandHandler extends CommandBase {

	@Override
	public String getCommandName() {
		// TODO Auto-generated method stub
		return "servertime";
	}

	@Override
	public String getCommandUsage(ICommandSender p) {
		// TODO Auto-generated method stub
		return "/servertime - Returns the current server time.";
	}

	@Override
	public void processCommand(ICommandSender p, String[] msg) {
		// TODO Auto-generated method stub
		AdminCommandHandler.sendMessage(p, "Current Server Time: "+EnumChatFormatting.GRAY+MinecraftServer.getServer().getTickCounter());
	}
	
	@Override
	public boolean canCommandSenderUseCommand(ICommandSender p) {
		return true;
	}

}
