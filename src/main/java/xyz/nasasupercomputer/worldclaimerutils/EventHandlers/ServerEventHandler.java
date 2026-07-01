package xyz.nasasupercomputer.worldclaimerutils.EventHandlers;

import java.awt.Component;
import java.util.ArrayList;

import javax.json.stream.JsonParser.Event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.chat.LoggedChatMessage.Player;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.OutgoingChatMessage;
import net.minecraft.network.chat.PlayerChatMessage;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event.Result;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import xyz.nasasupercomputer.worldclaimerutils.MainRegistry;
import xyz.nasasupercomputer.worldclaimerutils.Configs.ForgeConfigs;

@Mod.EventBusSubscriber(modid = MainRegistry.MODID)
public class ServerEventHandler {

	// Likely not the most efficient way of implementing this,
	// might redo this later.
	@SubscribeEvent
	public static void onItemPickup(EntityItemPickupEvent event) {
		
		if (ForgeConfigs.enableMod) {
			net.minecraft.world.entity.player.Player player = event.getEntity();
			ItemStack item = event.getItem().getItem();

//			// For each player
//			player.createCommandSourceStack().sendChatMessage(new OutgoingChatMessage.Player(testMSG), false, ChatType.bind(ChatType.CHAT, player));
//			
//			if (player.getName().getString().equalsIgnoreCase("dev")) {
//				event.setCanceled(true);
//				event.setResult(Result.DENY);
//			}	

			for (String index : ForgeConfigs.modBans) {
				String[] fullList = index.split(",");
				String bannedUsername = fullList[0];
				
				// If the players name is mentioned
				if (player.getName().getString().equalsIgnoreCase(bannedUsername)) {

					// Loop through all the modids and see if they match the 
					for (int i = 0; i < fullList.length - 1; i++) { // -1 to account for the first element being the players username
						
						if (ForgeRegistries.ITEMS.getKey(item.getItem()).getNamespace().equalsIgnoreCase(fullList[i + 1])) {
							// Dont pickup
							event.setCanceled(true);
							event.setResult(Result.DENY);
							
							if (ForgeConfigs.instantlyKillPlayer) {
								player.kill(); // welp shouldn't have done that
							}
						}
					}
				}
			}
		}
	}
	
	// Whenever the player right-clicks a block, air or item.
	@SubscribeEvent
	public static void onPlayerInteract(PlayerInteractEvent.RightClickBlock event) {
		net.minecraft.world.entity.player.Player player = event.getEntity();
		ItemStack item = event.getItemStack();
		Block block = event.getLevel().getBlockState(event.getPos()).getBlock();
		
		if (ForgeConfigs.enableMod) { 
			for (String index : ForgeConfigs.modBans) {
				String[] fullList = index.split(",");
				String bannedUsername = fullList[0];
				
				// If the players name is mentioned
				if (player.getName().getString().equalsIgnoreCase(bannedUsername)) {
	
					// Loop through all the modids and see if they match the 
					for (int i = 0; i < fullList.length - 1; i++) { // -1 to account for the first element being the players username
						
						// Check for the held item
						if (ForgeRegistries.ITEMS.getKey(item.getItem()).getNamespace().equalsIgnoreCase(fullList[i + 1])) {
							// Dont interact
							event.setCanceled(true);
							event.setResult(Result.DENY);
							event.setUseBlock(Result.DENY);
							
							if (ForgeConfigs.instantlyKillPlayer) {
								player.kill(); // welp shouldn't have done that
							}
						}
						
						// Check for the clicked block
						else if (ForgeRegistries.ITEMS.getKey(block.asItem()).getNamespace().equalsIgnoreCase(fullList[i + 1])) {
							// Dont interact
							event.setCanceled(true);
							event.setResult(Result.DENY);
							event.setUseBlock(Result.DENY);
							
							if (ForgeConfigs.instantlyKillPlayer) {
								player.kill(); // welp shouldn't have done that
							}
						}
					}
				}
			}
		}
	}
}
