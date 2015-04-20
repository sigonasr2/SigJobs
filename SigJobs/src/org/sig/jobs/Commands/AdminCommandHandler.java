package org.sig.jobs.Commands;

import java.text.NumberFormat;
import java.util.UUID;

import org.sig.jobs.PlayerData;
import org.sig.jobs.main;
import org.sig.jobs.Players.Buff;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class AdminCommandHandler extends CommandBase {

	@Override
	public String getCommandName() {
		// TODO Auto-generated method stub
		return "admin";
	}

	@Override
	public String getCommandUsage(ICommandSender p) {
		// TODO Auto-generated method stub
		return "/admin - Admin commands.";
	}
	
	boolean hasPermission(ICommandSender p) {
		boolean approved=false;
		if (p instanceof EntityPlayer) {
			if (main.AdminList.contains(((EntityPlayer) p).getDisplayName())) {
				approved=true;
			}
		} else {
			//Has to come from console.
			approved=true;
		}
		return approved;
	}
	
	public static void sendMessage(ICommandSender p, String message) {
		if (p instanceof EntityPlayer) {
			EntityPlayer pl = (EntityPlayer)p;
			pl.addChatComponentMessage(new ChatComponentText(message));
		} else {
			System.out.println(message);
		}
	}

	@Override
	public void processCommand(ICommandSender p, String[] msg) {
		// TODO Auto-generated method stub
		if (hasPermission(p)) {
			switch(msg.length) {
				case 1:{
					switch (msg[0]) {
						case "addAdmin":{
							sendMessage(p,"addAdmin <name>");
						}break;
						case "removeAdmin":{
							sendMessage(p,"removeAdmin <name>");
						}break;
						case "addBuff":{
							sendMessage(p,"addBuff <id> <lv> <duration>");
						}break;
						case "removeBuff":{
							sendMessage(p,"removeBuff <id>");
						}break;
						case "displayBuffs":{
							if (p instanceof EntityPlayer) {
								EntityPlayer pl = (EntityPlayer) p;
								PlayerData pd = main.getPlayerData(pl.getDisplayName());
								int i=0;
								for (Buff buff : pd.getBuffs()) {
									sendMessage(p,"Buff "+i+": ID:"+buff.getID()+",Level:"+buff.getLevel()+",Duration:"+buff.getDuration());
									i++;
								}
							}
						}break;
						default:{
							sendMessage(p,"Unknown Command.");
						}
					}
				}break;
				case 2:{
					switch (msg[0]) {
						case "addAdmin":{
							boolean found=false;
							boolean found2=false;
							for (String s2 : main.AdminList) {
								if (msg[1].equalsIgnoreCase(s2)) {
									//main.AdminList.remove(s2);
									//sendMessage(p,"Removed player "+msg[1]+" from list of Admins.");
									found2=true;
									break;
								}
							}
							if (!found2) {
								EntityPlayer pl = PlayerData.getPlayer(msg[1]);
								if (pl!=null && pl.getDisplayName().equalsIgnoreCase(msg[1])) {
									//sendMessage(p,"Comparing "+msg[1]+" to "+s);
									main.AdminList.add(PlayerData.getPlayer(msg[1]).getDisplayName());
									sendMessage(p,"Added player "+msg[1]+" to list of Admins.");
									found=true;
									break;
								}
							} else {
								sendMessage(p,"Player "+msg[1]+" already added to admin list.");
							}
							if (!found && !found2) {sendMessage(p,"Could not find player "+msg[1]+".");}
						}break;
						case "removeAdmin":{
							boolean found=false;
							for (String s : main.AdminList) {
								if (msg[1].equalsIgnoreCase(s)) {
									main.AdminList.remove(s);
									sendMessage(p,"Removed player "+msg[1]+" from list of Admins.");
									found=true;
									break;
								}
							}
							if (!found) {sendMessage(p,"Could not find player "+msg[1]+".");}
						}break;
						case "removeBuff":{
							if (p instanceof EntityPlayer) {
								sendMessage(p,"removeBuff "+msg[1]);
								EntityPlayer pl = (EntityPlayer)p;
								PlayerData pd = main.getPlayerData(pl.getDisplayName());
								if (pd!=null) {
									pd.removeBuff(Integer.valueOf(msg[1]));
								} else {
									sendMessage(p,"Could not remove buff from Player "+pl.getDisplayName());
								}
							}
						}break;
						default:{
							sendMessage(p,"Unknown Command.");
						}
					}
				}break;
				case 4:{
					switch (msg[0]) {
						case "addBuff":{
							if (p instanceof EntityPlayer) {
								sendMessage(p,"addBuff "+msg[1]+" "+msg[2]+" "+msg[3]);
								EntityPlayer pl = (EntityPlayer)p;
								PlayerData pd = main.getPlayerData(pl.getDisplayName());
								if (pd!=null) {
									pd.applyBuff(Integer.valueOf(msg[1]), Integer.valueOf(msg[2]), Integer.valueOf(msg[3]));
								} else {
									sendMessage(p,"Could not add buff to Player "+pl.getDisplayName());
								}
							} else {
								sendMessage(p,"Can only be done to a player.");
							}
						}break;
						default:{
							sendMessage(p,"Unknown Command.");
						}
					}
				}break;
				default:{
					sendMessage(p,"Unknown Command.");
				}
			}
		} else {
			sendMessage(p,EnumChatFormatting.RED+"You do not have permissions to do this.");
		}
	}
	
	@Override
	public boolean canCommandSenderUseCommand(ICommandSender p) {
		if (hasPermission(p)) {
			return true;
		} else {
			return false;
		}
	}

}
