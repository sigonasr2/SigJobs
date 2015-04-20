package org.sig.jobs;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class EntityHandler {
	@SubscribeEvent
	public void onPlayerJoin(EntityJoinWorldEvent ev) {
		if (ev.entity instanceof EntityPlayer) {
			EntityPlayer p = (EntityPlayer)ev.entity;
			//Initialize money amount if this player does not have money.
			if (!p.getEntityData().hasKey("money")) {
				p.getEntityData().setDouble("money", main.STARTMONEY); //Start off with $100.
			}
			//Initialize jobs.
			for (int i=0;i<main.MAX_JOBS;i++) {
				if (!p.getEntityData().hasKey("job"+(i+1))) {
					System.out.println("Player "+p.getPersistentID()+" does not have data. Writing new data.");
					p.getEntityData().setString("job"+(i+1), "");
					p.getEntityData().setInteger("joblv"+(i+1), 1);
					p.getEntityData().setInteger("jobexp"+(i+1), 0);
				}
			}
			boolean alreadyExists=false;
			
			for (PlayerData pd : main.PlayerList) {
				if (pd.getID().equals(p.getPersistentID())) {
					alreadyExists=true;
					break;
				}
			}
			if (!alreadyExists) {
				main.PlayerList.add(new PlayerData(p.getPersistentID()));
			}
		}
	}
	
	@SubscribeEvent
	public void onPlayerDamage(LivingHurtEvent ev) {
		if (ev.entityLiving instanceof EntityPlayer) {
			//This is a player that has been hit.
			EntityPlayer p = (EntityPlayer) ev.entityLiving;
			float dmg = ev.ammount;
			
			//Check for any damage reduction buffs.
		}
	}
}
