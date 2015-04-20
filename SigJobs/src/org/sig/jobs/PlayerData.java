package org.sig.jobs;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.sig.jobs.Players.Buff;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;

/**
 * A structure that defines data  for each entering player.
 * @author sigonasr2
 *
 */
public class PlayerData {
	UUID id; //Stores the ID this PlayerData refers to.
	String name; //The player's name.
	List<Buff> buffs; //A list of buffs this PlayerData contains. 
	

	/**
	 * Returns the player instance tied to this PlayerData.
	 * @return Will return null if it cannot find the player!
	 */
	EntityPlayer getPlayer() {
		//return MinecraftServer.getServer().getEntityWorld().getPlayerEntityByName(name);
		return getPlayer(id);
	}
	
	//@Deprecated
	/**
	 * Returns a player instance given a UUID.
	 * @param id The UUID.
	 * @return Will return null if it cannot find the player!
	 */
	public static EntityPlayer getPlayer(UUID id) {
		MinecraftServer serv = MinecraftServer.getServer();
		List<EntityPlayer> playerEntities = serv.getEntityWorld().playerEntities;
		for (EntityPlayer p : playerEntities) {
			//System.out.println("Comparing "+p.getUniqueID()+" to "+id+".");
			if (p.getUniqueID().equals(id)) {
				return p;
			}
		}
		/*
		for (int i=0;i<playerEntities.size();i++) {
			if (playerEntities.get(i) instanceof EntityPlayer) {
				EntityPlayer p = (EntityPlayer)playerEntities.get(i);
				if (p.getUniqueID().equals(id)) {
					return p;
				}
			}
		}*/
		return null;
	}
	
	/**
	 * Returns a player instance given a name.
	 * @param name The name of the player.
	 * @return 
	 */
	public static EntityPlayer getPlayer(String name) {

		MinecraftServer serv = MinecraftServer.getServer();
		List<EntityPlayer> playerEntities = serv.getEntityWorld().playerEntities;
		for (EntityPlayer p : playerEntities) {
			//System.out.println("Comparing "+p.getUniqueID()+" to "+id+".");
			if (p.getDisplayName().equalsIgnoreCase(name)) {
				return p;
			}
		}
		/*
		for (int i=0;i<playerEntities.size();i++) {
			if (playerEntities.get(i) instanceof EntityPlayer) {
				EntityPlayer p = (EntityPlayer)playerEntities.get(i);
				if (p.getUniqueID().equals(id)) {
					return p;
				}
			}
		}*/
		return null;
		//return MinecraftServer.getServer().getEntityWorld().getPlayerEntityByName(name);
	}
	
	/**
	 * 
	 * @param name
	 */
	public PlayerData(UUID id) {
		this.id=id;
		//Get the player's name.
		//this.name = getPlayer(id).getDisplayName();
		buffs = new ArrayList<Buff>();
	}
	
	@Deprecated
	public PlayerData(String name) {
		this.id=MinecraftServer.getServer().getEntityWorld().getPlayerEntityByName(name).getUniqueID();
		//Get the player's name.
		//this.name = MinecraftServer.getServer().getEntityWorld().getPlayerEntityByName(name).getDisplayName();
		buffs = new ArrayList<Buff>();
	}
	
	/**
	 * Returns the UUID of the player tied to this PlayerData.
	 * @return
	 */
	public UUID getID() {
		return id;
	}
	
	/**
	 * Returns the buff array of this player.
	 * @return
	 */
	public List<Buff> getBuffs() {
		return buffs;
	}
	
	/**
	 * Returns true if this player has a certain buff. False otherwise.
	 * @param id The ID of the buff we are searching for. See main.BUFF_*
	 * @return
	 */
	public boolean hasBuff(int id) {
		for (int i=0;i<buffs.size();i++) {
			if (buffs.get(i).getID()==id) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Returns the Buff object for a particular buff on the player.
	 * @param id The ID of the buff we are searching for. See main.BUFF_*
	 * @return Returns null if it cannot find it.
	 */
	public Buff getBuff(int id) {
		for (int i=0;i<buffs.size();i++) {
			if (buffs.get(i).getID()==id) {
				return buffs.get(i);
			}
		}
		return null;
	}
	
	/**
	 * Applies a buff to this PlayerData object. If the buff already exists, it will
	 * attempt to override it if the lv of this buff is >= the current buff. You can
	 * forceOverride this by setting the appropriate argument to true.  Returns whether it worked or not.
	 * @param id The ID of the buff. See main.BUFF_*
	 * @param lv The Level/Power of the buff.
	 * @param duration The duration (in ticks) of this buff.
	 */
	public boolean applyBuff(int id, int lv, int duration) {
		return applyBuff(id,lv,duration,false);
	}
	
	/**
	 * Applies a buff to this PlayerData object. If the buff already exists, it will
	 * attempt to override it if the lv of this buff is >= the current buff. You can
	 * forceOverride this by setting the appropriate argument to true.  Returns whether it worked or not.
	 * @param id The ID of the buff. See main.BUFF_*
	 * @param lv The Level/Power of the buff.
	 * @param duration The duration (in ticks) of this buff.
	 * @param forceOverride Whether or not to forcefully apply the buff, regardless of current buff level.
	 */
	public boolean applyBuff(int id, int lv, int duration, boolean forceOverride) {
		if (hasBuff(id)) {
			System.out.println("Found this buff.");
			Buff buff = getBuff(id);
			if (buff!=null && (forceOverride || buff.getLevel()<=lv)) {
				//We're allowed to override it.
				buffs.remove(buff);
				buffs.add(new Buff(id,lv,duration));
				return true;
			} else {
				return false;
			}
		} else {
			buffs.add(new Buff(id,lv,duration));
			return true;
		}
	}
	
	/**
	 * Attempts to remove a buff on this PlayerData object. Returns whether it worked or not.
	 * @param id The ID of the buff. See main.BUFF_*
	 * @return
	 */
	public boolean removeBuff(int id) {
		if (hasBuff(id)) {
			Buff buff = getBuff(id);
			buffs.remove(buff);
			return true;
		} else {
			return false;
		}
	}
}
