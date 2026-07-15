package xyz.nasasupercomputer.worldclaimerutils.ItemGroups;

import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.Registries;
import xyz.nasasupercomputer.worldclaimerutils.Items.ItemRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import xyz.nasasupercomputer.worldclaimerutils.MainRegistry;

public class ItemGroupRegistry {

	public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MainRegistry.MODID);
	
    public static final RegistryObject<CreativeModeTab> WORLDCLAIMERS_TAB = CREATIVE_MODE_TABS.register("worldclaimers_tab", () -> CreativeModeTab.builder()
            .icon(() -> ItemRegistry.TAB_ICON.get().getDefaultInstance())
            .title(Component.translatable("worldclaimers.creativetab"))
            .build());

    
    // Adding Items to the groups.
    public static void AddItemToTab(BuildCreativeModeTabContentsEvent event)
    {
        if (event.getTabKey() == WORLDCLAIMERS_TAB.getKey()) {
        	event.accept(Blocks.DIRT);
        }
    }
    
    
    public static void registerItemGroups(IEventBus modEventBus) {
    	CREATIVE_MODE_TABS.register(modEventBus);
    }

}
