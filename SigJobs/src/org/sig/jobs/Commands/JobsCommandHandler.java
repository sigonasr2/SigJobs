package org.sig.jobs.Commands;

import org.sig.jobs.Jobs;
import org.sig.jobs.main;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class JobsCommandHandler extends CommandBase {

	@Override
	public String getCommandName() {
		// TODO Auto-generated method stub
		return "jobs";
	}

	@Override
	public String getCommandUsage(ICommandSender p) {
		// TODO Auto-generated method stub
		return "/jobs help - Displays a list of job commands.";
	}
	
	@Override
	public boolean canCommandSenderUseCommand(ICommandSender p) {
		if (p instanceof EntityPlayer) {
			return true; //If we are a player, regardless of permissions, we can use this.
		}
		return false; //By default we should return false.
	}
	
	void UnknownCommand(EntityPlayer p) {
		p.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.RED+"Unknown command. Please type "+EnumChatFormatting.GREEN+"/jobs help"+EnumChatFormatting.RED+" for more information."));
	}

	@Override
	public void processCommand(ICommandSender p, String[] msg) {
		// TODO Auto-generated method stub
		if (p instanceof EntityPlayer) {
			EntityPlayer pl = (EntityPlayer)p;
			/*// /jobs test multiple args RETURNS 0:test 1:multiple 2:args
			 *
			 * for (int i=0;i<msg.length;i++) {
				pl.addChatComponentMessage(new ChatComponentText(i+"|"+msg[i]));
			}*/
			switch (msg.length) {
			
				case 0:{
					pl.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.BOLD+"List of Jobs:"));
					for (int i=0;i<main.JOBS.length;i+=2) {
						pl.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.getValueByName(main.JOBS[i])+"("+main.JOBLIMIT[i/2]+"/"+Jobs.getMaxJobMembers()+")"+main.JOBS[i+1]));
					}
				}break;
				case 1:{
					switch (msg[0]) {
						case "join": {
							//Display help message for joining.
							pl.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.GREEN+"/jobs join <job>"+EnumChatFormatting.RESET+" - Join a job."));
							pl.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.ITALIC+""+EnumChatFormatting.GRAY+"   See a list of jobs by typing "+EnumChatFormatting.GREEN+"/jobs"+EnumChatFormatting.GRAY+"."));
						}break;
						case "leave": {
							//Display help message for leaving.
							pl.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.GREEN+"/jobs leave <job>"+EnumChatFormatting.RESET+" - Leave a job."));
							pl.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.ITALIC+""+EnumChatFormatting.GRAY+"   See a list of jobs by typing "+EnumChatFormatting.GREEN+"/jobs"+EnumChatFormatting.GRAY+"."));
						}break;
						case "info":{
							//Display help message for job info.
							pl.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.GREEN+"/jobs info <job>"+EnumChatFormatting.RESET+" - Displays more information about a job."));
							pl.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.ITALIC+""+EnumChatFormatting.GRAY+"   See a list of jobs by typing "+EnumChatFormatting.GREEN+"/jobs"+EnumChatFormatting.GRAY+"."));
						}break;
						case "help": {
							//Displays the list of commands for jobs.
							pl.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.BOLD+"List of Commands:"));
							pl.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.GREEN+"/jobs "+EnumChatFormatting.RESET+"- Displays a list of jobs."));
							pl.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.GREEN+"/jobs help "+EnumChatFormatting.RESET+"- Displays this list of commands."));
							pl.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.GREEN+"/jobs info <job> "+EnumChatFormatting.RESET+"- Displays more information about a job."));
							pl.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.GREEN+"/jobs list "+EnumChatFormatting.RESET+"- Displays a list of the jobs you are in."));
							pl.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.GREEN+"/jobs join <job> "+EnumChatFormatting.RESET+"- Join a certain job."));
							pl.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.GREEN+"/jobs leave <job> "+EnumChatFormatting.RESET+"- Leave a certain job."));
						}break;
						case "list": {
							//Lists the jobs you are currently in.
							pl.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.BOLD+"Current Jobs:"));
							for (int i=0;i<main.MAX_JOBS;i++) {
								if (!pl.getEntityData().getString("job"+(i+1)).equalsIgnoreCase("")) {
									//This is a valid job. Display data for it.
									pl.addChatComponentMessage(new ChatComponentText("Lv"+pl.getEntityData().getInteger("joblv"+(i+1))+" "+Jobs.getJobColor(pl.getEntityData().getString("job"+(i+1)))+Jobs.getJobName(pl.getEntityData().getString("job"+(i+1)))+EnumChatFormatting.RESET+"  "+pl.getEntityData().getInteger("jobexp"+(i+1))+"/"+Jobs.getExpRequirement(pl.getEntityData().getInteger("joblv"+(i+1)))+"xp"));
								}
							}
						}break;
						default: {
							UnknownCommand(pl);
						}
					}
				}break;
				case 2:{
					switch (msg[0]) {
						case "join": {
							//Attempt to join this job.
							Jobs.addJob(msg[1], pl);
						}break;
						case "leave": {
							//Attempt to leave this job.
							Jobs.removeJob(msg[1], pl);
						}break;
						case "info": {
							//Display more info about this job.
							
						}break;
						default: {
							
						}
					}
				}break;
				default: {
					UnknownCommand(pl);
				}
			}
		}
	}
}
