package com.Alex.jobsmod;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

/* Jobs class.
 * 
 * Contains all helper functions related to jobs. Adding/removal/rewarding/checking of Jobs.
 */
public class Jobs {

	/**
	 * Returns whether or not the string provided is a valid job.
	 * @param job The name of the job. (Case-insensitive)
	 * @return
	 */
	public static boolean isValidJob(String job) {
		for (int i=0;i<Jobsmain.JOBS.length;i+=2) {
			//System.out.println("Comparing "+job.toLowerCase()+"|"+main.JOBS[i+1].toLowerCase()+"|");
			if (job.equalsIgnoreCase(Jobsmain.JOBS[i+1])) {
				//System.console().printf("Comparing %s|%s|",job.toLowerCase(),main.JOBS[i+1].toLowerCase());
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Gets the proper name of this job. (With correct capitalization.)
	 * @param job The name of the job.
	 * @return
	 */
	public static String getJobName(String job) {
		for (int i=0;i<Jobsmain.JOBS.length;i+=2) {
			//System.out.println("Comparing "+job.toLowerCase()+"|"+main.JOBS[i+1].toLowerCase()+"|");
			if (job.equalsIgnoreCase(Jobsmain.JOBS[i+1])) {
				//System.console().printf("Comparing %s|%s|",job.toLowerCase(),main.JOBS[i+1].toLowerCase());
				return Jobsmain.JOBS[i+1];
			}
		}
		return "";
	}
	
	/**
	 * Gets the proper name of this job. (With correct capitalization.)
	 * @param id The ID number of the job.
	 * @return
	 */
	public static String getJobName(int id) {
		return Jobsmain.JOBS[id+1];
	}
	
	/**
	 * Returns the color of the specified job.
	 * @param job The name of the job. (Case insensitive)
	 * @return
	 */
	public static EnumChatFormatting getJobColor(String job) {
		for (int i=0;i<Jobsmain.JOBS.length;i+=2) {
			//System.out.println("Comparing "+job.toLowerCase()+"|"+main.JOBS[i+1].toLowerCase()+"|");
			if (job.equalsIgnoreCase(Jobsmain.JOBS[i+1])) {
				//System.console().printf("Comparing %s|%s|",job.toLowerCase(),main.JOBS[i+1].toLowerCase());
				return EnumChatFormatting.getValueByName(Jobsmain.JOBS[i]);
			}
		}
		return EnumChatFormatting.WHITE;
	}
	
	/**
	 * Returns the color of the specified job.
	 * @param id The id number of the job.
	 * @return
	 */
	public static EnumChatFormatting getJobColor(int id) {
		return EnumChatFormatting.getValueByName(Jobsmain.JOBS[id]);
	}
	
	/**
	 * Returns the numerical value of a job.
	 * @param job The name of the job. (Case-insensitive)
	 * @return
	 */
	public static int getJobID(String job) {
		if (isValidJob(job)) {
			for (int i=0;i<Jobsmain.JOBS.length;i+=2) {
				if (job.equalsIgnoreCase(Jobsmain.JOBS[i+1])) {
					return i/2;
				}
			}
		}
		return -1; //We should never return this.
	}

	/**
	 * Get maximum amount of members allowed in a job.
	 * @return
	 */
	public static int getMaxJobMembers() {
		//Returns the number of players that may occupy one job. Based on total players registered on the server.
		String[] players = MinecraftServer.getServer().getConfigurationManager().getAvailablePlayerDat(); //Gets the total number of players on the server.
		
		return (int) (Math.floor(players.length/10)+1); //Offer some wiggle room as we do not necessarily need everyone to occupy all slots.
	}
	
	/**
	 * Checks if the job requested is already filled up.
	 * @param job The name of the job. (Case-insensitive)
	 * @return
	 */
	public static boolean isJobFull(String job) {
		if (Jobsmain.JOBLIMIT[getJobID(job)]<getMaxJobMembers()) {
			return false;
		}
		return true;
	}
	
	/**
	 * Returns if the player is in this job.
	 * @param job The name of the job. (Case-insensitive)
	 * @param p The player entity.
	 * @return
	 */
	public static boolean PlayerInJob(String job, EntityPlayer p) {
		for (int i=0;i<Jobsmain.MAX_JOBS;i++) {
			if (p.getEntityData().hasKey("job"+(i+1))) {
				if (p.getEntityData().getString("job"+(i+1)).equalsIgnoreCase(job)) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Returns true if the player is not filled up in jobs.
	 * @param p The player entity.
	 * @return
	 */
	public static boolean hasEmptyJobSlot(EntityPlayer p) {
		for (int i=0;i<Jobsmain.MAX_JOBS;i++) {
			if (p.getEntityData().getString("job"+(i+1)).equalsIgnoreCase("")) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Calculates the exp for a specified level based on config values.
	 * @param level The level to calculate exp for.
	 * @return
	 */
	public static int getExpRequirement(int level) {
		int totalxp=0;
		for (int i=0;i<level;i++) {
			totalxp+=Jobsmain.startexp+(Jobsmain.baseexp*(level*Jobsmain.expmult));
		}
		return totalxp; 
	}
	
	/**
	 * Attempts to add the player to a job. Returns true if succeeded, or false on failure.
	 * Also notifies the player the reason that they could not be added to the job.
	 * @param job The name of the job. (Case-insensitive)
	 * @param p The player entity.
	 * @return
	 */
	public static boolean addJob(String job, EntityPlayer p) {
		if (isValidJob(job)) {
			//Check if job is full.
			if (!isJobFull(job)) {
				if (!PlayerInJob(job,p)) {
					if (hasEmptyJobSlot(p)) {
						//Then we can add this player.
						//Find first empty slot.
						for (int i=0;i<Jobsmain.MAX_JOBS;i++) {
							if (p.getEntityData().getString("job"+(i+1)).equalsIgnoreCase("")) {
								//Found a slot. Add the job.
								p.getEntityData().setString("job"+(i+1), job);
								p.getEntityData().setInteger("joblv"+(i+1), 1);
								p.getEntityData().setInteger("jobexp"+(i+1), 0);
								Jobsmain.JOBLIMIT[getJobID(job)]++;
								List onlinePlayers = MinecraftServer.getServer().getEntityWorld().playerEntities;
								for (int j=0;j<onlinePlayers.size();j++) {
									EntityPlayer onlinep = (EntityPlayer)onlinePlayers.get(j);
									onlinep.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.GOLD+p.getDisplayName()+""+EnumChatFormatting.WHITE+" has just joined the "+getJobColor(job)+getJobName(job)+EnumChatFormatting.RESET+" job!"));
								}
								return true;
							}
						}
					} else {
						p.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.RED+"You are already in too many jobs! (Limit: "+Jobsmain.MAX_JOBS+") Please leave one first."));
						return false;
					}
				} else {
					p.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.RED+"You are already in this job!"));
					return false;
				}
			} else {
				p.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.RED+"The job you are trying to join is full!"));
				return false;
			}
		} else {
			p.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.RED+"The job you typed is not valid! Please try again."));
			return false;
		}
		p.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.DARK_RED+"[FATAL ERROR]"+EnumChatFormatting.RED+" Failed for undocumented reason. Please report this to sigonasr2 ASAP."));
		return false;
	}
	
	/**
	 * Attempts to remove a player from a job. Returns true if succeeded, false otherwise. The player will be told
	 * why it failed to remove them.
	 * @param job The name of the job. (Case-insensitive)
	 * @param p The player entity.
	 * @return
	 */
	public static boolean removeJob(String job, EntityPlayer p) {
		if (isValidJob(job)) {
			if (PlayerInJob(job, p)) {
				//Find the job slot with the job.
				for (int i=0;i<Jobsmain.MAX_JOBS;i++) {
					if (p.getEntityData().getString("job"+(i+1)).equalsIgnoreCase(job)) {
						//Found a slot. Add the job.
						p.getEntityData().setString("job"+(i+1), "");
						p.getEntityData().setInteger("joblv"+(i+1), 1);
						p.getEntityData().setInteger("jobexp"+(i+1), 0);
						Jobsmain.JOBLIMIT[getJobID(job)]--;
						List onlinePlayers = MinecraftServer.getServer().getEntityWorld().playerEntities;
						for (int j=0;j<onlinePlayers.size();j++) {
							EntityPlayer onlinep = (EntityPlayer)onlinePlayers.get(j);
							onlinep.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.GOLD+p.getDisplayName()+""+EnumChatFormatting.WHITE+" has left the "+getJobColor(job)+getJobName(job)+EnumChatFormatting.RESET+" job."));
						}
						return true;
					}
				}
			} else {
				p.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.RED+"You are not in this job!"));
				return false;
			}
		} else {
			p.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.RED+"The job you typed is not valid! Please try again."));
			return false;
		}
		p.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.DARK_RED+"[FATAL ERROR]"+EnumChatFormatting.RED+" Failed for undocumented reason. Please report this to sigonasr2 ASAP."));
		return false;
	}
}