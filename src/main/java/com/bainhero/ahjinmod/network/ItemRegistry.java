package com.bainhero.ahjinmod.network;

import com.bainhero.ahjinmod.AhjinMod;
import com.bainhero.ahjinmod.item.AhjinModCreativeModeTab;
import com.bainhero.ahjinmod.item.GateSpawnerItem;
import com.bainhero.ahjinmod.item.ManaMeter;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemRegistry
{
	// Deferred Registers
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, AhjinMod.MODID);
	
	// Items
	
	public static final RegistryObject<Item> MAGIC_CORE_E = ITEMS.register("magic_core_e", 
			() -> new Item(new Item.Properties().tab(AhjinModCreativeModeTab.AHJIN_TAB)));
	
	public static final RegistryObject<GateSpawnerItem> GATE_SPAWNER = ITEMS.register("gate_spawner",
			() -> new GateSpawnerItem(new Item.Properties().tab(AhjinModCreativeModeTab.AHJIN_TAB)));
	
	public static final RegistryObject<ManaMeter> MANA_METER = ITEMS.register("mana_meter", 
			() -> new ManaMeter(new Item.Properties().tab(AhjinModCreativeModeTab.AHJIN_TAB)));
	
	
	public static void register(IEventBus eventBus) {
		ITEMS.register(eventBus);
	}
}
