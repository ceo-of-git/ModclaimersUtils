package xyz.nasasupercomputer.worldclaimerutils.Blocks;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import xyz.nasasupercomputer.worldclaimerutils.MainRegistry;

public class BlockRegistry {
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MainRegistry.MODID);
	public static final DeferredRegister<Item> BLOCK_ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MainRegistry.MODID);

	public static void registerBlocks(IEventBus modEventBus) {
		BLOCKS.register(modEventBus);
		BLOCK_ITEMS.register(modEventBus);
	}
}
