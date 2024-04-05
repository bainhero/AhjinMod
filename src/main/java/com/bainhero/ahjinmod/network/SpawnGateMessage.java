package com.bainhero.ahjinmod.network;

import java.util.function.Supplier;

import javax.annotation.Nullable;

import com.bainhero.ahjinmod.entity.GateEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

public class SpawnGateMessage 
{
	private final BlockPos pos;
	public SpawnGateMessage(BlockPos pos) {
		this.pos = pos;
	}
	
	public static void encode(SpawnGateMessage msg, FriendlyByteBuf packetBuffer) {
		packetBuffer.writeLong(msg.pos.asLong());
	}
	
	public static SpawnGateMessage decode(FriendlyByteBuf packetBuffer) {
		return new SpawnGateMessage(BlockPos.of(packetBuffer.readLong()));
	}
	
	static void onMessage(SpawnGateMessage msg, Supplier<NetworkEvent.Context> contextSupplier) {
		NetworkEvent.Context context = contextSupplier.get();
		context.enqueueWork(() -> handleMessage(msg, context.getSender()));
		context.setPacketHandled(true);
	}
	
	private static void handleMessage(SpawnGateMessage msg, @Nullable ServerPlayer sender) {
		if (sender == null) {
			return;
		}
		GateEntity gate = new GateEntity(EntityRegistry.GATE.get(), sender.level);
		gate.setPos(msg.pos.getX(), msg.pos.getY(), msg.pos.getZ());
		sender.level.addFreshEntity(gate);
	}
}
