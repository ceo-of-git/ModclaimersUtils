package xyz.nasasupercomputer.modclaimersutil.EventHandlers;

import java.awt.Component;
import java.util.ArrayList;

import javax.json.stream.JsonParser.Event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.chat.LoggedChatMessage.Player;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.OutgoingChatMessage;
import net.minecraft.network.chat.PlayerChatMessage;
import net.minecraft.server.TickTask;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event.Result;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import xyz.nasasupercomputer.modclaimersutil.MainRegistry;
import xyz.nasasupercomputer.modclaimersutil.Configs.ForgeConfigs;

@Mod.EventBusSubscriber(modid = MainRegistry.MODID)
public class ServerEventHandler {

	// When an item is picked up
	@SubscribeEvent
	public static void onItemPickup(EntityItemPickupEvent event) {
		net.minecraft.world.entity.player.Player player = event.getEntity();
		ItemStack itemStack = event.getItem().getItem();
		
		if (player != null && ForgeConfigs.enablePickupBlocking) { 
			if (MainRegistry.isItemBanned(player, itemStack.getItem())) {
				event.setCanceled(true);
				event.setResult(Result.DENY);
				
				if (ForgeConfigs.instantlyKillPlayer) {
					player.kill(); // welp shouldn't have done that
				}
			}
		}
	}
	

	@SubscribeEvent
	public static void blockBreakEvent(BlockEvent.BreakEvent event) {
		net.minecraft.world.entity.player.Player player = event.getPlayer();
		Item item = event.getState().getBlock().asItem();
		
		if (player != null && ForgeConfigs.enableBlockBreakBlocking) { 
			if (MainRegistry.isItemBanned(player, item)) {
				event.setCanceled(true);
				event.setResult(Result.DENY);
				
				if (ForgeConfigs.instantlyKillPlayer) {
					player.kill(); // welp shouldn't have done that
				}
			}
		}
	}

	// Whenever the player right-clicks with an item
	@SubscribeEvent
	public static void onRightClickItem(PlayerInteractEvent.RightClickItem event) {
	    if (event.getLevel().isClientSide()) { return; }

	    ServerPlayer player = (ServerPlayer) event.getEntity();
	    ItemStack itemStack = event.getItemStack();

		if (player != null && ForgeConfigs.enableRightclickBlocking) { 
			if (MainRegistry.isItemBanned(player, itemStack.getItem())) {
				event.setCanceled(true);
				event.setResult(Result.DENY);
				
				if (ForgeConfigs.instantlyKillPlayer) {
					player.kill(); // welp shouldn't have done that
				}
			}
		}
	}
	
	// Whenever the player switches Armor
	@SubscribeEvent
	public static void onEquipmentChange(LivingEquipmentChangeEvent event) {
	    if (!(event.getEntity() instanceof ServerPlayer player)) { return; }
	    if (event.getSlot().getType() != EquipmentSlot.Type.ARMOR) { return; }

	    ItemStack itemStack = event.getTo();

		if (player != null && ForgeConfigs.enableArmorEquipBlocking) { 
			if (MainRegistry.isItemBanned(player, itemStack.getItem())) {
				// event.setCanceled(true); This crashes :(
				// event.setResult(Result.DENY);
				
				// Drop armor piece after 1 tick
				// Spent a while forum-dwelling for this one so it better run perfectly
				player.server.tell(new TickTask(
					    player.server.getTickCount() + 1,
					    () -> {
				            ItemStack currentArmor = player.getItemBySlot(event.getSlot());
			                player.setItemSlot(event.getSlot(), ItemStack.EMPTY);
			                player.spawnAtLocation(currentArmor.copy());
					    }
					));
				
				if (ForgeConfigs.instantlyKillPlayer) {
					player.kill(); // welp shouldn't have done that
				}
			}
		}
	}
	
	// Whenever the player right-clicks a block, air or item.
	@SubscribeEvent
	public static void onPlayerInteract(PlayerInteractEvent.RightClickBlock event) {
		net.minecraft.world.entity.player.Player player = event.getEntity();
		ItemStack itemStack = event.getItemStack();
		Block block = event.getLevel().getBlockState(event.getPos()).getBlock();
		
		if (player != null && ForgeConfigs.enableBlockInteractionBlocking) { 
			if (MainRegistry.isItemBanned(player, itemStack.getItem())) {
				// Dont interact
				event.setCanceled(true);
				event.setResult(Result.DENY);
				event.setUseBlock(Result.DENY);
				
				if (ForgeConfigs.instantlyKillPlayer) {
					player.kill(); // welp shouldn't have done that
				}
			}
			
			// Check for the clicked block
			else if (MainRegistry.isItemBanned(player, block.asItem())) {
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
