package com.bainhero.ahjinmod.network;

import com.bainhero.ahjinmod.AhjinMod;
import com.bainhero.ahjinmod.entity.GateEntity;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EntityRegistry 
{
	// Deferred Registers
	public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, AhjinMod.MODID);
	
	//Entities
	 public static final RegistryObject<EntityType<GateEntity>> GATE = ENTITIES.register("gate", 
			 () -> EntityType.Builder
			 .<GateEntity>of(GateEntity::new, MobCategory.MISC)
			 .setTrackingRange(10)
			 .setUpdateInterval(20)
			 .sized(2f, 2f)
			 .setCustomClientFactory((packet, level) -> {
				 GateEntity ent = EntityRegistry.GATE.get().create(level);
				 return ent;
			 })
			 .build(new ResourceLocation(AhjinMod.MODID, "gate").toString()));
	 
	 public static void register(IEventBus eventBus) {
			ENTITIES.register(eventBus);
	 }
}
