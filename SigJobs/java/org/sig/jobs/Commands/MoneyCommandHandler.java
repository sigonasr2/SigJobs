package org.sig.jobs.Commands;

import java.text.NumberFormat;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class MoneyCommandHandler extends CommandBase {

	@Override
	public String getCommandName() {
		// TODO Auto-generated method stub
		return "money";
	}

	@Override
	public String getCommandUsage(ICommandSender p) {
		// TODO Auto-generated method stub
		return "/money - Returns how much money the Player is holding.";
	}

	@Override
	public void processCommand(ICommandSender p, String[] msg) {
		// TODO Auto-generated method stub
		
		if (p instanceof EntityPlayer) {
			EntityPlayer pl = (EntityPlayer) p;
			
			NumberFormat money_format = NumberFormat.getInstance();
			
			pl.addChatComponentMessage(new ChatComponentText("Currently Holding: "+EnumChatFormatting.GREEN+"$"+money_format.format(pl.getEntityData().getFloat("money"))));
		}
	}
	
	@Override
	public boolean canCommandSenderUseCommand(ICommandSender p) {
		if (p instanceof EntityPlayer) {
			return true; //Allow all players to use this command.
		}
		return false;
	}

}
