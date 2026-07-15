package xyz.nasasupercomputer.worldclaimerutils.Items;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import xyz.nasasupercomputer.worldclaimerutils.MainRegistry;
import xyz.nasasupercomputer.worldclaimerutils.Configs.ForgeConfigs;

public class ContrabandRadar extends Item {

	public ContrabandRadar(Properties pProperties) {
		super(pProperties);
	}
	
	public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pUsedHand);
        boolean hooliganFound = false; // if anyone has it or not.
        
        List<String> checkedNames = new ArrayList<>();
        List<String> hooliganNames = new ArrayList<>();
        
        if (pPlayer.getServer() == null) { 
        	return InteractionResultHolder.pass(pPlayer.getItemInHand(pUsedHand));
        }
        
        if (pPlayer.hasPermissions(4)) {
        	
        	
            for (ServerPlayer serverPlayer : pPlayer.getServer().getPlayerList().getPlayers()) {
            	String playerUsername = serverPlayer.getDisplayName().getString();
            	checkedNames.add(playerUsername);
            	
            	for (int i = 0; i < serverPlayer.getInventory().getContainerSize(); i++) {
            	    ItemStack itemStack = serverPlayer.getInventory().getItem(i);
            	    
            	    
            	    if (MainRegistry.isItemBanned(serverPlayer, itemStack.getItem())) {
            	    	hooliganNames.add(playerUsername);
            	    	hooliganFound = true;
            	    }
            	}
            }
            
            if (hooliganFound) {
            	// Header
            	pPlayer.playSound(SoundEvents.NETHER_WOOD_BUTTON_CLICK_ON, 1.0F, 0.5F);
            	pPlayer.sendSystemMessage(Component.literal("=-------------------------------------------------=").withStyle(ChatFormatting.GRAY));
            	pPlayer.sendSystemMessage(Component.translatable("item.worldclaimerutils.contraband_radar.found").withStyle(ChatFormatting.DARK_RED));
            	
            	// Spill the Secrets
            	for (String i : checkedNames) {
            		if (hooliganNames.contains(i)) {
            			pPlayer.sendSystemMessage(Component.literal(i + " -- " + countUsernameListEntries(hooliganNames, i) + " Has Contraband Stack(s) Found").withStyle(ChatFormatting.RED));
            		}
            		else {
            			pPlayer.sendSystemMessage(Component.literal(i + " -- Has 0 Contraband Stacks Found").withStyle(ChatFormatting.GREEN));
            		}
            	}
            	
            	pPlayer.sendSystemMessage(Component.literal("=-------------------------------------------------=").withStyle(ChatFormatting.GRAY));
            	
            }
            else {
            	pPlayer.playSound(SoundEvents.NETHER_WOOD_BUTTON_CLICK_ON, 1.0F, 2.0F);
            	pPlayer.sendSystemMessage(Component.translatable("item.worldclaimerutils.contraband_radar.nofound").withStyle(ChatFormatting.GREEN));
            }

        }
        else {
        	pPlayer.sendSystemMessage(Component.translatable("item.worldclaimerutils.contraband_radar.error").withStyle(ChatFormatting.RED));
        	pPlayer.playSound(SoundEvents.VILLAGER_NO, 1.0F, 0.8F);
        }


        return InteractionResultHolder.pass(pPlayer.getItemInHand(pUsedHand));
    }
    
    private static int countUsernameListEntries(List<String> usernameList, String username) {
    	int count = 0;
    	
    	for (String i : usernameList) {
    		if (i.equalsIgnoreCase(username)) { count ++; }
    	}
    	
    	return count;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
    	pTooltipComponents.add(Component.translatable("item.worldclaimers.contraband_radar.desc").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC));
    }
}
