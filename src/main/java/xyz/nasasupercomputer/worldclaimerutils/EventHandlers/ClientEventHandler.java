package xyz.nasasupercomputer.worldclaimerutils.EventHandlers;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event.Result;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import xyz.nasasupercomputer.worldclaimerutils.MainRegistry;
import xyz.nasasupercomputer.worldclaimerutils.Configs.ForgeConfigs;

@Mod.EventBusSubscriber(modid = MainRegistry.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ClientEventHandler {
	
	// Hold on... this is... optimized?
	@SubscribeEvent
	public static void drawTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        List<Component> tooltipList = event.getToolTip();
        List<String> tooltipString = new ArrayList<String>();
        
        if (ForgeConfigs.enableMod && ForgeConfigs.enableDisallowedTooltip) {
			net.minecraft.world.entity.player.Player player = event.getEntity();
			ItemStack item = event.getItemStack();
			
			if (player == null) { return; }
			
			for (String index : ForgeConfigs.modBans) {
				String[] fullList = index.split(",");
				String bannedUsername = fullList[0];
				
				// If the players name is mentioned
				if (player.getName().getString().equalsIgnoreCase(bannedUsername) || (bannedUsername.equalsIgnoreCase("all"))) {

					// Loop through all the modids and see if they match the 
					for (int i = 0; i < fullList.length - 1; i++) { // -1 to account for the first element being the players username
						
						if (ForgeRegistries.ITEMS.getKey(item.getItem()).getNamespace().equalsIgnoreCase(fullList[i + 1])) {
							// Render
							tooltipList.add(Component.translatable("worldclaimers.disallowed").withStyle(ChatFormatting.RED).withStyle(ChatFormatting.ITALIC));
						}
					}
				}
			}
        }
	}
}
