package org.sig.jobs;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;

public class BlockBreakHandler {
	@SubscribeEvent
	public void onBlockBreak(BreakEvent ev) {
		//ev.getPlayer().addChatComponentMessage(new ChatComponentText(EnumChatFormatting.GOLD+"You broke a block!"));
	}
}
