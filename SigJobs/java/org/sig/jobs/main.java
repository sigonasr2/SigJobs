package org.sig.jobs;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.sig.jobs.Commands.AdminCommandHandler;
import org.sig.jobs.Commands.JobsCommandHandler;
import org.sig.jobs.Commands.MoneyCommandHandler;
import org.sig.jobs.Commands.TimeCommandHandler;

import net.minecraft.command.ICommandManager;
import net.minecraft.command.ServerCommandManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;

@Mod(modid = main.MODID, version = main.VERSION)
public class main {

	public static int MAX_JOBS = 3; //Maximum number of jobs players may join.
	
	public static final String[] JOBS = { 
		"GOLD", "Blacksmith",
		"DARK_GREEN", "Breeder",
		"LIGHT_PURPLE", "Brewer",
		"WHITE", "Builder",
		"YELLOW", "Cook",
		"GOLD", "Digger",
		"DARK_BLUE", "Enchanter",
		"WHITE", "Explorer",
		"AQUA", "Fisherman",
		"RED", "Hunter",
		"GRAY", "Miner",
		"DARK_RED", "Support",
		"DARK_PURPLE", "Weaponsmith",
		"GREEN", "Woodcutter"
	};
	
	public static final int BUFF_DAMAGEREDUCTION = 0;
	public static final int BUFF_DAMAGEINCREASE = 0;
	
	public static double STARTMONEY = 100;

	public static final String MODID = "SigJobs";
	public static final String VERSION = "0.0.1";
	
	public static List<PlayerData> PlayerList;
	public static List<String> AdminList;
	
	public static int JOBMAXPLAYERS = 0; //The maximum amount of players that can occupy one job.
				/*This is a factor of the total number of players that have played on the server.*/
	
	public static int[] JOBLIMIT = { //Contains the number of members of each job.
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0
	};
	
	public static double expmult = 1.1f; //The exp growth rate per level.
	public static int baseexp = 100; //The base exp growth rate per level.
	public static int startexp = 100; //The starting exp requirement for a job.
	
	public static List<String> JOB_INFO1;
	public static List<String> JOB_STATINFO1;
	public static List<String> JOB_INFO2;
	public static List<String> JOB_STATINFO2;
	public static List<String> JOB_INFO3;
	public static List<String> JOB_STATINFO3;
	public static List<String> JOB_INFO4;
	public static List<String> JOB_STATINFO4;
	public static List<String> JOB_INFO5;
	public static List<String> JOB_STATINFO5;
	public static List<String> JOB_INFO6;
	public static List<String> JOB_STATINFO6;
	public static List<String> JOB_INFO7;
	public static List<String> JOB_STATINFO7;
	public static List<String> JOB_INFO8;
	public static List<String> JOB_STATINFO8;
	public static List<String> JOB_INFO9;
	public static List<String> JOB_STATINFO9;
	public static List<String> JOB_INFO10;
	public static List<String> JOB_STATINFO10;
	public static List<String> JOB_INFO11;
	public static List<String> JOB_STATINFO11;
	public static List<String> JOB_INFO12;
	public static List<String> JOB_STATINFO12;
	public static List<String> JOB_INFO13;
	public static List<String> JOB_STATINFO13;
	public static List<String> JOB_INFO14;
	public static List<String> JOB_STATINFO14;
	
	/**
	 * Returns the PlayerData instance of a player.
	 * @param id The UUID of the player to search for.
	 * @return Returns null if this fails.
	 */
	public PlayerData getPlayerData(UUID id) {
		for (PlayerData i : PlayerList) {
			if (i.getID().equals(id)) {
				return i;
			}
		}
		/*
		for (int i=0;i<PlayerList.size();i++) {
			if (PlayerList.get(i).getID()==id) {
				return PlayerList.get(i);
			}
		}*/
		return null;
	}
	/**
	 * Returns the PlayerData instance of a player.
	 * @param name The name of the player. (Case-insensitive)
	 * @return Returns null if this fails.
	 */
	public static PlayerData getPlayerData(String name) {
		for (PlayerData i : PlayerList) {
			if (i.getPlayer().getDisplayName().equalsIgnoreCase(name)) {
				return i;
			}
		}
		/*
		for (int i=0;i<PlayerList.size();i++) {
			if (PlayerList.get(i).getPlayer().getDisplayName().equalsIgnoreCase(name)) {
				return PlayerList.get(i);
			}
		}*/
		return null;
	}
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent ev) {
		
		JOB_INFO1 = new ArrayList<String>();
		JOB_INFO1.add(EnumChatFormatting.GOLD+"Blacksmith");
		JOB_INFO1.add("");
		JOB_INFO1.add("A Blacksmith's job entails creating strong pieces of equipment for themselves and their teammates to venture out with. Blacksmiths embue their souls into the armor they create, adding bonuses to them others would not be able to obtain.");
		JOB_INFO1.add("");
		JOB_INFO1.add("CRAFT:");
		JOB_INFO1.add("Leather Armor: +1xp per mat");
		JOB_INFO1.add("Dyed Leather Armor: +1xp per mat");
		JOB_INFO1.add("Iron Armor: +2xp per mat");
		JOB_INFO1.add("Steel Armor: +3xp per mat");
		JOB_INFO1.add("Diamond Armor: +4xp per mat");
		JOB_INFO1.add("Enforced Leather Armor: +2xp per mat");
		JOB_INFO1.add("Enforced Iron Armor: +10xp per mat");
		JOB_INFO1.add("Enforced Diamond Armor: +20xp per mat");
		JOB_INFO1.add("EnderDragon Armor: +100xp per mat");
		JOB_INFO1.add("Wither Armor: +200xp per mat");
		JOB_INFO1.add("REPAIR:");
		JOB_INFO1.add("Repairing armor gives exp based on how worn it is.");
		JOB_STATINFO1 = new ArrayList<String>();
		JOB_STATINFO1.add("A Blacksmith gets cheaper repairs, cheaper crafting, and stronger armor.");
		JOB_STATINFO1.add("");
		JOB_STATINFO1.add("Lv3: -5% anvil cost");
		JOB_STATINFO1.add("Lv5: Provides 'Protection II' on all crafted armor.");
		JOB_STATINFO1.add("Lv8: -10% anvil cost");
		JOB_STATINFO1.add("Lv10: Provides 'Regen I' to all crafted armor. (Improves health regeneration when worn.)");
		JOB_STATINFO1.add("Lv13: -15% anvil cost");
		JOB_STATINFO1.add("Lv15: Materials used for crafting have a 10% chance of being recycled.");
		JOB_STATINFO1.add("Lv18: -20% anvil cost");
		JOB_STATINFO1.add("Lv20: Provides 'Durability I' to all crafted armor. (Armor pieces last twice as long before breaking.)");
		JOB_STATINFO1.add("Lv23: -25% anvil cost");
		JOB_STATINFO1.add("Lv25: Enchantments from armor can be stored onto books for recombining.");
		JOB_STATINFO1.add("Lv28: -30% anvil cost");
		JOB_STATINFO1.add("Lv30: Provides 'Last Resort' to all crafted armor. (When armor breaks, it provides a temporary 40 HP shield.)");
		JOB_STATINFO1.add("Lv33: -40% anvil cost");
		JOB_STATINFO1.add("Lv35: Gain the ability to build 'enforced' armor, made with tanned leather;iron,gold,diamond blocks for extra damage reduction and 10x normal durability.");
		JOB_STATINFO1.add("Lv38: -50% anvil cost");
		JOB_STATINFO1.add("Lv40: Provides 'Protection VI', 'Regen II', 'Durability II' as upgrades on all crafted armor.");
		

		JOB_INFO2 = new ArrayList<String>();
		JOB_INFO2.add(EnumChatFormatting.DARK_GREEN+"Breeder");
		JOB_INFO2.add("");
		JOB_INFO2.add("A Breeder populates the world with many species of animals, ensuring their successful growth and preventing overpopulation.");
		JOB_INFO2.add("");
		JOB_INFO2.add("BREED:");
		JOB_INFO2.add("Chicken: +2xp");
		JOB_INFO2.add("Sheep: +2xp");
		JOB_INFO2.add("Pig: +3xp");
		JOB_INFO2.add("Cow: +3xp");
		JOB_INFO2.add("Ocelote: +5xp");
		JOB_INFO2.add("Wolf: +5xp");
		JOB_INFO2.add("CONTROL:");
		JOB_INFO2.add("A Breeder will get exp bonuses when multiplying animals in less dense areas, or unnatural areas. Example: An area with lots of pigs will score high breeding cows there.");
		JOB_INFO2.add("");
		JOB_INFO2.add("On the flip side, breeding too many of one type of animal in a space will reward less xp as the animals overcrowd one area of the world.");
		JOB_STATINFO2 = new ArrayList<String>();
		JOB_STATINFO2.add("A Breeder can absorb energy from the animals around them and get more benefits from them.");
		JOB_STATINFO2.add("");
		JOB_STATINFO2.add("Lv3: -1% damage taken per nearby animal");
		JOB_STATINFO2.add("Lv5: Animals will sometimes produce twins when breeding.");
		JOB_STATINFO2.add("Lv8: -3% damage taken per nearby animal");
		JOB_STATINFO2.add("Lv10: Animals provide 5x the normal amount of drops.");
		JOB_STATINFO2.add("Lv13: +1 HP per nearby animal");
		JOB_STATINFO2.add("Lv15: Breeded animals will now have 4x their normal health.");
		JOB_STATINFO2.add("Lv18: +1% damage dealt per nearby animal");
		JOB_STATINFO2.add("Lv20: Right-clicking with a stick will command all nearby animals to move to where you are. (Hold wheat/carrots/seeds/fish/bones to have finer control over this behavior)");
		JOB_STATINFO2.add("Lv23: +2 HP per nearby animal");
		JOB_STATINFO2.add("Lv25: When nearby animals get hurt, they will heal much faster.");
		JOB_STATINFO2.add("Lv28: -5% damage per nearby animal");
		JOB_STATINFO2.add("Lv30: Breeding animals gives 5x more experience.");
		JOB_STATINFO2.add("Lv33: +3 HP per nearby animal");
		JOB_STATINFO2.add("Lv35: Setting down chests near animals will automatically consume the items inside and feed them.");
		JOB_STATINFO2.add("Lv38: +3% damage dealt per nearby animal");
		JOB_STATINFO2.add("Lv40: Allows you to turn animals into egg form for respawning by right-clicking them (with no item). Animals resummoned by Breeders have x10 normal health, 'Strength II', and 'Speed II'.");

		JOB_INFO3 = new ArrayList<String>();
		JOB_INFO3.add(EnumChatFormatting.DARK_GREEN+"Brewer");
		JOB_INFO3.add("");
		JOB_INFO3.add("A Brewer creates stronger and longer lasting potions, while also enabling them to sometimes savor potions for multiple uses.");
		JOB_INFO3.add("");
		JOB_INFO3.add("BREW:");
		JOB_INFO3.add("All potions brewed give 1xp per potion. Reaching the redstone/glowstone stage will provide 5xp per potion.");
		JOB_INFO3.add("BOILING:");
		JOB_INFO3.add("By cooking potions in between brews, you can increase their power and duration greatly.");
		JOB_INFO3.add("");
		JOB_STATINFO3 = new ArrayList<String>();
		JOB_STATINFO3.add("A Brewer gets increased combat stats when consuming potions.");
		JOB_STATINFO3.add("");
		JOB_STATINFO3.add("Lv3: +2% damage reduction per potion consumption (Lasts 5 min)");
		JOB_STATINFO3.add("Lv5: Brewing potions in stands is twice as quick.");
		JOB_STATINFO3.add("Lv8: +2 HP shield per potion consumption (Lasts 5 min)");
		JOB_STATINFO3.add("Lv10: Potions will have 2 uses per potion before being discarded.");
		JOB_STATINFO3.add("Lv13: +2% damage dealt per potion consumption (Lasts 5 min)");
		JOB_STATINFO3.add("Lv15: When Mundane Potions are cooked, they turn into Splash Potions of Teleportation");
		JOB_STATINFO3.add("Lv18: +4% damage reduction per potion consumption (Lasts 10 min)");
		JOB_STATINFO3.add("Lv20: Potions brewed have twice the normal duration.");
		JOB_STATINFO3.add("Lv23: +4 HP shield per potion consumption (Lasts 5 min)");
		JOB_STATINFO3.add("Lv25: Potions will have 4 uses per potion before being discarded.");
		JOB_STATINFO3.add("Lv28: +4% damage dealt per potion consumption (Lasts 5 min)");
		JOB_STATINFO3.add("Lv30: When Thick Potions are cooked, they turn into Splash Potions of Shielding (+20 HP)");
		JOB_STATINFO3.add("Lv33: +8% damage reduction per potion consumption (Lasts 5 min)");
		JOB_STATINFO3.add("Lv35: Brewing stands instantly brew potions.");
		JOB_STATINFO3.add("Lv38: +8 HP shield per potion consmption (Lasts 5 min)");
		JOB_STATINFO3.add("Lv40: Potions brewed have 30 minute durations.");

		JOB_INFO4 = new ArrayList<String>();
		JOB_INFO4.add(EnumChatFormatting.WHITE+"Builder");
		JOB_INFO4.add("");
		JOB_INFO4.add("A Builder creates and designs structures with a variety of materials. Using richer materials nets morth building experience.");
		JOB_INFO4.add("");
		JOB_INFO4.add("BUILD:");
		JOB_INFO4.add("Wood Types - 1xp");
		JOB_INFO4.add("Stone Types - 1xp");
		JOB_INFO4.add("Carpenter's - 2xp");
		JOB_INFO4.add("Furnishings - 2xp");
		JOB_INFO4.add("Microblocks - 2xp");
		JOB_INFO4.add("Carpets - 2xp");
		JOB_INFO4.add("Chiseled - 2xp");
		JOB_INFO4.add("Redstone - 2xp");
		JOB_INFO4.add("Lighting - 2xp");
		JOB_INFO4.add("Metal Types - 4xp");
		JOB_INFO4.add("");
		JOB_INFO4.add("There are so many useful and interesting ways to build that listing and defining them all would be difficult. Just know that more unique materials will be rewarded greatly.");
		JOB_STATINFO4 = new ArrayList<String>();
		JOB_STATINFO4.add("A Builder is less susceptible to falls and can actually empower themselves with structures.");
		JOB_STATINFO4.add("");
		JOB_STATINFO4.add("Lv3: -10% Fall Damage");
		JOB_STATINFO4.add("Lv5: When right-clicking enemies with blocks, uses them to deal damage. Damage varies based on material type.");
		JOB_STATINFO4.add("Lv8: +4 HP shield when placing blocks (Lasts 10 seconds)");
		JOB_STATINFO4.add("Lv10: Damage taken is reduced by 30% while building.");
		JOB_STATINFO4.add("Lv13: -20% Fall Damage");
		JOB_STATINFO4.add("Lv15: Gain 'Jump Boost IV' while building to build around easier.");
		JOB_STATINFO4.add("Lv18: +8 HP shield when placing blocks (Lasts 10 seconds)");
		JOB_STATINFO4.add("Lv20: When shift-right-clicking two of the same block, will attempt to fill them in a line for quick building.");
		JOB_STATINFO4.add("Lv23: -30% Fall Damage");
		JOB_STATINFO4.add("Lv25: Damage taken is reduced by 50% while building.");
		JOB_STATINFO4.add("Lv28: +12 HP shield when placing blocks (Lasts 10 seconds)");
		JOB_STATINFO4.add("Lv30: When shift-jump-right-clicking two of the same block, will attempt to rectangle fill them for quick building.");
		JOB_STATINFO4.add("Lv33: -50% Fall Damage");
		JOB_STATINFO4.add("Lv35: Placing a rectangular structure (up to 9x9) of material blocks  nearby will give party members different effects. Iron: x2 Attack, Redstone: +30 HP, Lapis Lazuli: +50% Damage Reduction, Emerald Blocks: No Durability Loss, Diamond: No Knockback, All Debuffs get Removed Constantly");
		JOB_STATINFO4.add("Lv38: +20 HP shield when placing blocks (Lasts 10 seconds)");
		JOB_STATINFO4.add("Lv40: Gain the ability to fly temporarily when building. During flight you taken 75% less damage than normal.");

		JOB_INFO5 = new ArrayList<String>();
		JOB_INFO5.add(EnumChatFormatting.YELLOW+"Cook");
		JOB_INFO5.add("");
		JOB_INFO5.add("A Cook can create delicacies that have interesting effects beyond just keeping yourself full.");
		JOB_INFO5.add("");
		JOB_INFO5.add("COOK:");
		JOB_INFO5.add("There are a large abundance of foods to create. Ones that have more steps and ingredients give much more than simpler recipes.");
		JOB_INFO5.add("");
		JOB_STATINFO5 = new ArrayList<String>();
		JOB_STATINFO5.add("A cook can eat foods to influence party members with auras. Cooks are stronger with furnaces and food around them.");
		JOB_STATINFO5.add("");
		JOB_STATINFO5.add("Lv3: +1 damage dealt per nearby burning furnace.");
		JOB_STATINFO5.add("Lv5: All foods cooks eat can be consumed instantly. Eating fish will increase maximum HP of nearby players by 20%");
		JOB_STATINFO5.add("Lv8: +1% damage reduction for each stack of food held in your inventory");
		JOB_STATINFO5.add("Lv10: Cooking foods will add 'Sweetness I', giving the eater a bonus 10 HP shield for 2 minutes.");
		JOB_STATINFO5.add("Lv13: +2 damage dealt per nearby burning furnace.");
		JOB_STATINFO5.add("Lv15: Fuel in furnaces last twice as long. Eating Chili Con Carne will decrease damage taken of nearby players by 20%");
		JOB_STATINFO5.add("Lv18: +2% damage reduction for each stack of food held in your inventory");
		JOB_STATINFO5.add("Lv20: Colored candies have various perks - Black: Movement Speed+10%, Red: Lifesteal+10%, Green: Poison Touch(2min), Brown: Attack Damage+10%, Purple: Jump Boost II, Cyan: Water Breathing, Light Gray: +10% Item Drops, Gray: Debuff Resistance (2min), Pink: +4 HP Heal, Lime: Take 2 HP to deal double damage next hit, Yellow: Saturation (2min), Purple: Regeneration I (5sec), Magenta, Orange: Absorption II (2min), White: Cuts HP in half to gain Strength II (30 sec)");
		JOB_STATINFO5.add("Lv23: +3 damage dealt per nearby burning furnace.");
		JOB_STATINFO5.add("Lv25: Throwing eggs at mobs deals 10 damage.");
		JOB_STATINFO5.add("Lv28: +3% damage reduction for each stack of food held in your inventory");
		JOB_STATINFO5.add("Lv30: Waters in a Bottle created by cooks will be blessed and provide healing (6 HP per bottle)");
		JOB_STATINFO5.add("Lv33: +5 damage dealt per nearby burning furnace.");
		JOB_STATINFO5.add("Lv35: Cooking foods will add bonus random effects (see candies) to all foods cooked.");
		JOB_STATINFO5.add("Lv38: +5% damage reduction for each stack of food held in your inventory");
		JOB_STATINFO5.add("Lv40: All foods cooked will provide full health heals when eaten and include a temporary 10 HP shield (30 sec).");

		JOB_INFO6 = new ArrayList<String>();
		JOB_INFO6.add(EnumChatFormatting.GOLD+"Digger");
		JOB_INFO6.add("");
		JOB_INFO6.add("A Digger");
		JOB_INFO6.add("");
		JOB_INFO6.add("COOK:");
		JOB_INFO6.add("There are a large abundance of foods to create. Ones that have more steps and ingredients give much more than simpler recipes.");
		JOB_INFO6.add("");
		JOB_STATINFO6 = new ArrayList<String>();
		JOB_STATINFO6.add("A cook can eat foods to influence party members with auras. Cooks are stronger with furnaces and food around them.");
		JOB_STATINFO6.add("");
		JOB_STATINFO6.add("Lv3: +1 damage dealt per nearby burning furnace.");
		JOB_STATINFO6.add("Lv5: All foods cooks eat can be consumed instantly. Eating fish will increase maximum HP of nearby players by 20%");
		JOB_STATINFO6.add("Lv8: +1% damage reduction for each stack of food held in your inventory");
		JOB_STATINFO6.add("Lv10: Cooking foods will add 'Sweetness I', giving the eater a bonus 10 HP shield for 2 minutes.");
		JOB_STATINFO6.add("Lv13: +2 damage dealt per nearby burning furnace.");
		JOB_STATINFO6.add("Lv15: Fuel in furnaces last twice as long. Eating Chili Con Carne will decrease damage taken of nearby players by 20%");
		JOB_STATINFO6.add("Lv18: +2% damage reduction for each stack of food held in your inventory");
		JOB_STATINFO6.add("Lv20: Colored candies have various perks - Black: Movement Speed+10%, Red: Lifesteal+10%, Green: Poison Touch(2min), Brown: Attack Damage+10%, Purple: Jump Boost II, Cyan: Water Breathing, Light Gray: +10% Item Drops, Gray: Debuff Resistance (2min), Pink: +4 HP Heal, Lime: Take 2 HP to deal double damage next hit, Yellow: Saturation (2min), Purple: Regeneration I (5sec), Magenta, Orange: Absorption II (2min), White: Cuts HP in half to gain Strength II (30 sec)");
		JOB_STATINFO6.add("Lv23: +3 damage dealt per nearby burning furnace.");
		JOB_STATINFO6.add("Lv25: Throwing eggs at mobs deals 10 damage.");
		JOB_STATINFO6.add("Lv28: +3% damage reduction for each stack of food held in your inventory");
		JOB_STATINFO6.add("Lv30: Waters in a Bottle created by cooks will be blessed and provide healing (6 HP per bottle)");
		JOB_STATINFO6.add("Lv33: +5 damage dealt per nearby burning furnace.");
		JOB_STATINFO6.add("Lv35: Cooking foods will add bonus random effects (see candies) to all foods cooked.");
		JOB_STATINFO6.add("Lv38: +5% damage reduction for each stack of food held in your inventory");
		JOB_STATINFO6.add("Lv40: All foods cooked will provide full health heals when eaten and include a temporary 10 HP shield (30 sec).");
		

		JOB_INFO14 = new ArrayList<String>();
		JOB_INFO14.add(EnumChatFormatting.GREEN+"Woodcutter");
		JOB_INFO14.add("");
		JOB_INFO14.add("A Woodcutter's job includes cutting down trees, replacing them with saplings, and building items with the wood materials collected.");
		JOB_INFO14.add("");
		JOB_INFO14.add("CHOP:");
		JOB_INFO14.add("-Logs +1xp");
		JOB_INFO14.add("-Logs (w/axes) +3xp");
		JOB_INFO14.add("PLACE:");
		JOB_INFO14.add("-Planks +1xp");
		JOB_INFO14.add("-Wooden Items +2xp");
		JOB_INFO14.add("REPLANT:");
		JOB_INFO14.add("-Saplings +1xp");
		JOB_INFO14.add("-Bonemeal Trees +5xp");
		JOB_INFO14.add("");
		JOB_STATINFO14 = new ArrayList<String>();
		JOB_STATINFO14.add("A Woodcutter gets increased defenses, higher jumps, and takes less damage on falls.");
		JOB_STATINFO14.add("");
		JOB_STATINFO14.add("Lv3: +5% damage reduction");
		JOB_STATINFO14.add("Lv5: Gains 'Haste II' when cutting down trees.");
		JOB_STATINFO14.add("Lv8: +30% jump height");
		JOB_STATINFO14.add("Lv10: Chopping down trees sometimes yields golden apples.");
		JOB_STATINFO14.add("Lv13: -50% fall damage");
		JOB_STATINFO14.add("Lv15: Saplings placed down will only require one bonemeal to regrow.");
		JOB_STATINFO14.add("Lv18: +10% damage reduction");
		JOB_STATINFO14.add("Lv20: While cutting down logs, you can jump very high.");
		JOB_STATINFO14.add("Lv23: +50% jump height");
		JOB_STATINFO14.add("Lv25: Cutting down logs will yield 2-4 extra wooden planks.");
		JOB_STATINFO14.add("Lv28: -80% fall damage");
		JOB_STATINFO14.add("Lv30: Crafting recipes that consume logs or planks now recycle items 50% of the time.");
		JOB_STATINFO14.add("Lv33: -15% damage reduction");
		JOB_STATINFO14.add("Lv35: Cooking logs yields 1 coal block per log.");
		JOB_STATINFO14.add("Lv38: -30% damage reduction");
		JOB_STATINFO14.add("Lv40: Trees can be cut down in one click by right-clicking with an axe.");
		
		PlayerList = new ArrayList<PlayerData>();
		AdminList = new ArrayList<String>();
		
		Configuration config = new Configuration(new File("SigJobs.cfg"));
		config.load(); //Create/load our config.
		for (int i=0;i<JOBLIMIT.length;i++) {
			JOBLIMIT[i]=config.get("SIGJOBS", "JOB"+i, 0).getInt();
		}
		STARTMONEY = (float) config.get("SIGJOBS", "STARTMONEY", STARTMONEY).getDouble();
		MAX_JOBS = config.get("SIGJOBS","MAXJOBS",3).getInt();
		expmult = config.get("SIGJOBS", "XPMULT", expmult).getDouble();
		baseexp = config.get("SIGJOBS", "BASEXP", baseexp).getInt();
		startexp = config.get("SIGJOBS", "STARTXP", startexp).getInt();
		String[] emptyset = {};
		String[] adminslist = config.get("SIGJOBS", "ADMINS", emptyset).getStringList();
		for (int i=0;i<adminslist.length;i++) {
			AdminList.add(adminslist[i]);
		}
		
		
		config.save(); //Save our config file.
	}
	
	@EventHandler
	public void init(FMLInitializationEvent ev) {
		MinecraftForge.EVENT_BUS.register(new BlockBreakHandler());
		MinecraftForge.EVENT_BUS.register(new EntityHandler());
		//MinecraftForge.EVENT_BUS.register(new Jobs());
	}
	
	@EventHandler
	public void serverStart(FMLServerStartingEvent ev) {
		MinecraftServer server= ev.getServer();
		ICommandManager commander = server.getCommandManager();
		ServerCommandManager servmanager = (ServerCommandManager)commander;
		servmanager.registerCommand(new JobsCommandHandler());
		servmanager.registerCommand(new MoneyCommandHandler());
		servmanager.registerCommand(new TimeCommandHandler());
		servmanager.registerCommand(new AdminCommandHandler());
	}
	
	@EventHandler
	public void serverClose(FMLServerStoppingEvent ev) {
		//Server is shutting down. Save the configuration.
		Configuration config = new Configuration(new File("SigJobs.cfg"));
		for (int i=0;i<JOBLIMIT.length;i++) {
			config.get("SIGJOBS", "JOB"+i, 0).set(JOBLIMIT[i]);
		}
		String[] emptyset = {};
		String[] adminlist = AdminList.toArray(new String[AdminList.size()]);
		config.get("SIGJOBS", "ADMINS", emptyset).set(adminlist);
		config.save();
	}
}
