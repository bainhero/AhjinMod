package com.bainhero.ahjinmod.network;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

import com.bainhero.ahjinmod.AhjinMod;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class AhjinPacketHandler
{
	public static final AhjinPacketHandler INSTANCE = new AhjinPacketHandler(AhjinMod.MODID);
	private static final String PROTOCOL = "1";
	
	private final SimpleChannel networkWrapper;
	private int idx = 0;

	public AhjinPacketHandler(String modId) {
		networkWrapper = NetworkRegistry.newSimpleChannel(new ResourceLocation(modId, "channel"),
				() -> PROTOCOL, PROTOCOL::equals, PROTOCOL::equals);
	}
	
	public void init() {
		
		registerMessage(SpawnGateMessage.class, SpawnGateMessage::encode, SpawnGateMessage::decode, SpawnGateMessage::onMessage);
	}
	
	public <M> void registerMessage(Class<M> messageType, BiConsumer<M, FriendlyByteBuf> encoder, Function<FriendlyByteBuf, M> decoder, BiConsumer<M, Supplier<NetworkEvent.Context>> messageConsumer) {
		networkWrapper.registerMessage(idx++, messageType, encoder, decoder, messageConsumer);
	}

	public <M> void sendToServer(M message) {
		networkWrapper.sendToServer(message);
	}

	public <M> void sendToClient(ServerPlayer player, M message) {
		networkWrapper.sendTo(message, player.connection.getConnection(), NetworkDirection.PLAY_TO_CLIENT);
	}

	public <M> void sendToAllNear(ServerLevel world, ResourceKey<Level> dimension, Vec3 position, int range, M message) {
		world.players().forEach(player -> {
			if (player.level.dimension() == dimension && player.distanceToSqr(position) <= range * range) {
				sendToClient(player, message);
			}
		});
	}
}
