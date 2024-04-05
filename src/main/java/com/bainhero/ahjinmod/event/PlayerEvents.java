package com.bainhero.ahjinmod.event;

import com.bainhero.ahjinmod.AhjinMod;
import com.bainhero.ahjinmod.entity.GateEntity;
import com.bainhero.ahjinmod.item.ManaMeter;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid=AhjinMod.MODID, bus=Bus.FORGE)
public class PlayerEvents 
{
	private static final int DURATION = 60;
	private static boolean display = false;
	private static String text = "";
	private static long start = 0;
	
	@SuppressWarnings("resource")
	@SubscribeEvent
	public static void playerInteract(PlayerInteractEvent.EntityInteract e) {
		if (e.getLevel().isClientSide) {
            return;
        }
        if (!(e.getTarget() instanceof GateEntity)) {
            return;
        }

		ItemStack item = e.getItemStack();
		Entity entity = e.getTarget();
		if(item.getItem() instanceof ManaMeter) {
			if (!e.getEntity().getCooldowns().isOnCooldown(item.getItem())) {
				GateEntity gEntity = (GateEntity)entity;
				ServerPlayer player = (ServerPlayer)e.getEntity();
				e.getEntity().getCooldowns().addCooldown(item.getItem(), DURATION + 1);
				display = true;
				text = "Mana: " + gEntity.getMana() + ", Rank: " + gEntity.getRank();
				start = player.level.getGameTime();
			}
		}
	}
	
	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public static void renderGui(RenderGuiOverlayEvent.Post event) {
		Minecraft mc = Minecraft.getInstance();
		long end = mc.level.getGameTime();
		if(display && (end - start) < DURATION) {
			mc.font.draw(event.getPoseStack(),
					Component.literal(text),
					mc.getWindow().getGuiScaledWidth()/2 - mc.font.width(text)/2,
					mc.getWindow().getGuiScaledHeight()/2 + 10,
					0xFFFFFF);
		} else {
			text = "";
			display = false;
		}
	}
}
