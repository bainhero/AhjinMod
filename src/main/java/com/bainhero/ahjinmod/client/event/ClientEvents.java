package com.bainhero.ahjinmod.client.event;

import com.bainhero.ahjinmod.AhjinMod;
import com.bainhero.ahjinmod.client.render.GateRenderer;
import com.bainhero.ahjinmod.network.EntityRegistry;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent.RegisterRenderers;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(bus = Bus.MOD, value = Dist.CLIENT, modid = AhjinMod.MODID)
public class ClientEvents 
{
	@SubscribeEvent
	public static void setup(FMLClientSetupEvent e) {
		
	}
	
	@SubscribeEvent
	public static void eRenders(RegisterRenderers e) {
		e.registerEntityRenderer(EntityRegistry.GATE.get(), GateRenderer::new);
	}
}
