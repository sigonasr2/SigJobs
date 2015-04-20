package com.Alex.jobsmod;

import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class BlockBreakHandler {
	@SubscribeEvent
	public void onBlockBreak(BreakEvent ev) {
		//ev.getPlayer().addChatComponentMessage(new ChatComponentText(EnumChatFormatting.GOLD+"You broke a block!"));
	}

}
