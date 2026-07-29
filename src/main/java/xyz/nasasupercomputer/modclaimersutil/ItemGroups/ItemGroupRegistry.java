package xyz.nasasupercomputer.modclaimersutil.ItemGroups;

import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.Registries;
import xyz.nasasupercomputer.modclaimersutil.MainRegistry;
import xyz.nasasupercomputer.modclaimersutil.Items.ItemRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ItemGroupRegistry {

	public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MainRegistry.MODID);
	
    // Adding Contraband Radar to the OP Tab
    public static void AddItemToTab(BuildCreativeModeTabContentsEvent event)
    {
        if (event.getTabKey() == CreativeModeTabs.OP_BLOCKS) {
        	event.accept(ItemRegistry.CONTRABAND_RADAR);
        }
    }
    
    public static void registerItemGroups(IEventBus modEventBus) {
    	CREATIVE_MODE_TABS.register(modEventBus);
    }

}
