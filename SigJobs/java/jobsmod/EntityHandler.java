package com.Alex.jobsmod;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class EntityHandler {
	@SubscribeEvent
	public void onPlayerJoin(EntityJoinWorldEvent ev) {
		if (ev.entity instanceof EntityPlayer) {
			EntityPlayer p = (EntityPlayer)ev.entity;
			//Initialize money amount if this player does not have money.
			if (!p.getEntityData().hasKey("money")) {
				p.getEntityData().setDouble("money", Jobsmain.STARTMONEY); //Start off with $100.
			}
			//Initialize jobs.
			for (int i=0;i<Jobsmain.MAX_JOBS;i++) {
				if (!p.getEntityData().hasKey("job"+(i+1))) {
					System.out.println("Player "+p.getUniqueID()+" does not have data. Writing new data.");
					p.getEntityData().setString("job"+(i+1), "");
					p.getEntityData().setInteger("joblv"+(i+1), 1);
					p.getEntityData().setInteger("jobexp"+(i+1), 0);
				}
			}
		}
	}
}
