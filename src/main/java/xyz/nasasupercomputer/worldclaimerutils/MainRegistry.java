package xyz.nasasupercomputer.worldclaimerutils;

import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.TickTask;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import xyz.nasasupercomputer.worldclaimerutils.Configs.ForgeConfigs;
import xyz.nasasupercomputer.worldclaimerutils.ItemGroups.ItemGroupRegistry;
import xyz.nasasupercomputer.worldclaimerutils.Items.ItemRegistry;

import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(MainRegistry.MODID)
public class MainRegistry
{
    public static final String MODID = "worldclaimerutils";
    private static final Logger LOGGER = LogUtils.getLogger();
    
    

    public MainRegistry(FMLJavaModLoadingContext context)
    {
        IEventBus modEventBus = context.getModEventBus();

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register Items / Blocks / Creative Tabs
        ItemRegistry.registerItems(modEventBus);
        ItemGroupRegistry.registerItemGroups(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);
        modEventBus.addListener(ItemGroupRegistry::AddItemToTab);

        context.registerConfig(ModConfig.Type.COMMON, ForgeConfigs.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
//        // Some common setup code
//        LOGGER.info("HELLO FROM COMMON SETUP");
//
//        if (Config.logDirtBlock)
//            LOGGER.info("DIRT BLOCK >> {}", ForgeRegistries.BLOCKS.getKey(Blocks.DIRT));
//
//        LOGGER.info(Config.magicNumberIntroduction + Config.magicNumber);
//
//        Config.items.forEach((item) -> LOGGER.info("ITEM >> {}", item.toString()));
    }
    
	public static boolean isItemBanned(Player player, Item item) {
		if (ForgeConfigs.enableMod) { 
			if (item == Items.AIR) { return false; }
			
			boolean isOpped = player.hasPermissions(4);

			for (String index : ForgeConfigs.modBans) {
				String[] fullList = index.split(",");
				String bannedUsername = fullList[0];
				
				// If the players name is mentioned
				if (player.getName().getString().equalsIgnoreCase(bannedUsername) || (bannedUsername.equalsIgnoreCase("all") && isOpped)) {

					// Loop through all the modids and see if they match the 
					for (int i = 0; i < fullList.length - 1; i++) { // -1 to account for the first element being the players username
						
						if (ForgeRegistries.ITEMS.getKey(item).getNamespace().equalsIgnoreCase(fullList[i + 1])) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

}
