package org.sig.jobs.Players;

import net.minecraft.server.MinecraftServer;


/**
 * A buff structure contains a duration, and a buff ID, which determines what type of buff it is.
 * @author sigonasr2
 *
 */
public class Buff {
	int id;
	int lv; //Level/Potency of the buff.
	int timeoff; //The server tick time when the buff wears out.
	int duration; //Saves the duration of this buff.
	
	public Buff(int id, int level,int duration) {
		this.id=id;
		this.lv=level;
		this.timeoff=MinecraftServer.getServer().getTickCounter()+duration;
		this.duration=duration;
	}
	
	/**
	 * Returns the ID of this buff.
	 */
	public int getID() {
		return id;
	}
	
	/**
	 * Returns the level of this buff.
	 */
	public int getLevel() {
		return lv;
	}
	
	/**
	 * Returns the total duration of this buff.
	 * @return
	 */
	public int getDuration() {
		return duration;
	}

	/**
	 * Returns the time this buff has left (in ticks).
	 */
	public int getTimeRemaining() {
		return timeoff-MinecraftServer.getServer().getTickCounter();
	}
	
	/**
	 * Returns the server time when this buff wears off.
	 */
	public int getTimeOff() {
		return timeoff;
	}
}