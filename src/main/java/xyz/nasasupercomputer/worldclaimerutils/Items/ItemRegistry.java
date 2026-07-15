package xyz.nasasupercomputer.worldclaimerutils.Items;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import xyz.nasasupercomputer.worldclaimerutils.MainRegistry;

public class ItemRegistry {

	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MainRegistry.MODID);
	
	public static final RegistryObject<Item> CONTRABAND_RADAR = ITEMS.register("contraband_radar", () -> new ContrabandRadar(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC).fireResistant()));
	
	public static void registerItems(IEventBus modEventBus) {
		ITEMS.register(modEventBus);
	}
}
