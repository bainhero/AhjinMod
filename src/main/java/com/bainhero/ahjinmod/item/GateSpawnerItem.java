package com.bainhero.ahjinmod.item;

import com.bainhero.ahjinmod.client.gui.GateSummonScreen;
import com.bainhero.ahjinmod.entity.GateEntity;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public class GateSpawnerItem extends Item
{

	public GateSpawnerItem(Properties prop) {
		super(prop);
	}
	
	@Override
	public InteractionResult useOn(UseOnContext ctx) {
		Level level = ctx.getLevel();
		if(level.isClientSide) {
			Minecraft.getInstance().setScreen(new GateSummonScreen("", new BlockPos(ctx.getClickLocation().x, ctx.getClickLocation().y, ctx.getClickLocation().z)));
		}
		return InteractionResult.SUCCESS;
	}
	
	@Override
	public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity) {
		if(!player.level.isClientSide) {
			if(entity instanceof GateEntity) {
				entity.remove(RemovalReason.KILLED);
			}
		}
		return super.onLeftClickEntity(stack, player, entity);
	}
}
