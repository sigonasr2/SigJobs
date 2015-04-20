package com.Alex.jobsmod;

import java.util.Random;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.INetHandler;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import cpw.mods.fml.common.gameevent.InputEvent.KeyInputEvent;
import cpw.mods.fml.common.network.FMLIndexedMessageToMessageCodec;
import cpw.mods.fml.common.network.NetworkRegistry;


public class KeyHandler
{
	/** Key index for easy handling */
	public static final int CUSTOM_INV = 0;
	/** Key descriptions; use a language file to localize the description later */
	private static final String[] desc = {"key.tut_inventory.desc"};
	/** Default key values */
	private static final int[] keyValues = {Keyboard.KEY_J};
	public static KeyBinding[] keys;
	public Jobsmain instance;
	
	public KeyHandler() {
		keys = new KeyBinding[desc.length];
		for (int i = 0; i < desc.length; ++i) {
		keys[i] = new KeyBinding(desc[i], keyValues[i], "key.tutorial.category");
		ClientRegistry.registerKeyBinding(keys[i]);
		}
		}

	/**
	* KeyInputEvent is in the FML package, so we must register to the FML event
	* bus
	*/
	@SubscribeEvent
	public void onKeyInput(KeyInputEvent event) {
		if (!FMLClientHandler.instance().isGUIOpen(GuiChat.class)) {
				if (keys[CUSTOM_INV].isPressed()) {
				EntityPlayer entity = Minecraft.getMinecraft().thePlayer;
				int i = (int)entity.posX;
				int j = (int)entity.posY;
				int k = (int)entity.posZ;
				MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
				World world = server.worldServers[0];
				if(true){
				Minecraft.getMinecraft().thePlayer.openGui(instance, jobsgui.GUIID, world, i, j, k);
				}
			}
			}
			}

	public void load() {
		FMLCommonHandler.instance().bus().register(new KeyHandler());
	}

	public void serverLoad(FMLServerStartingEvent event) {}

	public void preInit(FMLPreInitializationEvent ev) {}

public void generateNether(World world, Random random, int chunkX, int chunkZ){}
public void generateSurface(World world, Random random, int chunkX, int chunkZ){}
public void registerRenderers(){}
public int addFuel(ItemStack fuel){
	return 0;
}


}
