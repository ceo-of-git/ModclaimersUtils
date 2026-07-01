package xyz.nasasupercomputer.worldclaimerutils.EventHandlers;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import xyz.nasasupercomputer.worldclaimerutils.MainRegistry;

@Mod.EventBusSubscriber(modid = MainRegistry.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ClientEventHandler {

	
	// TODO Make items that aren't allowed say so in the tooltip.
	@SubscribeEvent
	public static void drawTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        List<Component> tooltipList = event.getToolTip();
        List<String> tooltipString = new ArrayList<String>();
        
        // event.setCanceled(true);
	}
	
}
