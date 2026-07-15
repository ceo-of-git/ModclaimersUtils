package xyz.nasasupercomputer.worldclaimerutils.Configs;

import java.util.HashMap;
import java.util.List;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.registries.ForgeRegistries;
import xyz.nasasupercomputer.worldclaimerutils.MainRegistry;

@Mod.EventBusSubscriber(modid = MainRegistry.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ForgeConfigs
{
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    
	public static boolean enableMod;
    private static final ForgeConfigSpec.BooleanValue ENABLED_MOD = BUILDER
            .comment("Whether or not to actually apply the changes of the mod.")
            .define("modEnabled", true);
    
	public static boolean instantlyKillPlayer;
    private static final ForgeConfigSpec.BooleanValue KILL_PLAYER = BUILDER
            .comment("Instantly kill a player that tries to interact with a banned mod (Joke Option) (Untested) (Will probably crash your game)")
            .define("instantlyKillPlayer", false);
    
	public static boolean enableDisallowedTooltip;
    private static final ForgeConfigSpec.BooleanValue ENABLED_DISALLOWED_TOOLTIP_DISPLAY = BUILDER
            .comment("Whether or not there will be text below an item indicating that it is banned")
            .define("enableDisallowedTooltip", true);

    public static List<? extends String> modBans;
    private static final ForgeConfigSpec.ConfigValue<List<? extends String>> MOD_BANS = BUILDER
                    .comment("A List of who should be banned from using What mod. Format: username,mod1,mod2,mod3")
                    .defineList(
                            "modBans",
                            List.of(
                                    "Steve,mekanism,hbm,birmingham",
                                    "FireMan307,create,waystones",
                                    "Dev,minecraft"
                            ),
                            o -> o instanceof String
                    );
    
	public static boolean enablePickupBlocking;
    private static final ForgeConfigSpec.BooleanValue ENABLED_ITEM_PICKUP_BLOCKING = BUILDER
            .comment("If a player is NOT allowed to pick up items from banned mods")
            .define("enablePickupBlocking", true);
    
	public static boolean enableRightclickBlocking;
    private static final ForgeConfigSpec.BooleanValue ENABLED_RIGHT_CLICK_BLOCKING = BUILDER
            .comment("If a player is NOT allowed to right-click an item from a banned mod")
            .define("enableRightclickBlocking", true);
    
	public static boolean enableArmorEquipBlocking;
    private static final ForgeConfigSpec.BooleanValue ENABLED_ARMOR_EQUIP_BLOCKING = BUILDER
            .comment("If a player is NOT allowed to wear armor from a banned mod")
            .define("enableArmorEquipBlocking", true);
    
	public static boolean enableBlockBreakBlocking;
    private static final ForgeConfigSpec.BooleanValue ENABLED_BLOCK_BREAK_BLOCKING = BUILDER
            .comment("If a player is NOT allowed to break blocks from a banned mod")
            .define("enableBlockBreakBlocking", false);
    
	public static boolean enableBlockInteractionBlocking;
    private static final ForgeConfigSpec.BooleanValue ENABLED_BLOCK_INTERACT_BLOCKING = BUILDER
            .comment("If a player is NOT allowed to interact (right-click) blocks from a banned mod")
            .define("enableBlockInteractionBlocking", true);
    
    // This must be last because this finalizes the Config.
    public static final ForgeConfigSpec SPEC = BUILDER.build();

    
	private static boolean validateItemName(final Object obj)
    {
        return obj instanceof final String itemName && ForgeRegistries.ITEMS.containsKey(new ResourceLocation(itemName));
    }

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event)
    {
    	enableMod = ENABLED_MOD.get();
    	modBans = MOD_BANS.get();
    	instantlyKillPlayer = KILL_PLAYER.get();
    	
    	enableDisallowedTooltip = ENABLED_DISALLOWED_TOOLTIP_DISPLAY.get();
    	enablePickupBlocking = ENABLED_ITEM_PICKUP_BLOCKING.get();
    	enableRightclickBlocking = ENABLED_RIGHT_CLICK_BLOCKING.get();
    	enableArmorEquipBlocking = ENABLED_ARMOR_EQUIP_BLOCKING.get();
    	enableBlockBreakBlocking = ENABLED_BLOCK_BREAK_BLOCKING.get();
    	enableBlockInteractionBlocking = ENABLED_BLOCK_INTERACT_BLOCKING.get();
    	
//        magicNumber = MAGIC_NUMBER.get();
//        magicNumberIntroduction = MAGIC_NUMBER_INTRODUCTION.get();
//
//        // convert the list of strings into a set of items
//        items = ITEM_STRINGS.get().stream()
//                .map(itemName -> ForgeRegistries.ITEMS.getValue(new ResourceLocation(itemName)))
//                .collect(Collectors.toSet());
    }
}
