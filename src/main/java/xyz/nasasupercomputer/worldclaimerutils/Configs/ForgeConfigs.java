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
            .comment("Instantly kill a player that tries to interact with a banned mod (Joke Option)")
            .define("instantlyKillPlayer", false);

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
    	
//        magicNumber = MAGIC_NUMBER.get();
//        magicNumberIntroduction = MAGIC_NUMBER_INTRODUCTION.get();
//
//        // convert the list of strings into a set of items
//        items = ITEM_STRINGS.get().stream()
//                .map(itemName -> ForgeRegistries.ITEMS.getValue(new ResourceLocation(itemName)))
//                .collect(Collectors.toSet());
    }
}
