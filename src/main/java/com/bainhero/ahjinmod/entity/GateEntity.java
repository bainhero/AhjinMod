package com.bainhero.ahjinmod.entity;

import com.bainhero.ahjinmod.Rank;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.entity.IEntityAdditionalSpawnData;
import net.minecraftforge.network.NetworkHooks;

public class GateEntity extends Entity implements IEntityAdditionalSpawnData
{
	public static final EntityDataAccessor<Integer> TICKS_ACTIVE = SynchedEntityData.defineId(GateEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> MANA = SynchedEntityData.defineId(GateEntity.class, EntityDataSerializers.INT);
	
	private Rank rank;
	public GateEntity(EntityType<?> type, Level level, Rank rank) {
		super(type, level);
		this.rank = rank;
		this.entityData.set(MANA,  rank.getMana());
	}

	public GateEntity(EntityType<?> type, Level level) {
		super(type, level);
		this.rank = new Rank();
		this.entityData.set(MANA,  rank.getMana());
	}

	@Override
	public void writeSpawnData(FriendlyByteBuf buffer) {
		
	}

	@Override
	public void readSpawnData(FriendlyByteBuf additionalData) {
		
	}

	@Override
	protected void defineSynchedData() {
		this.entityData.define(MANA, 0);
		this.entityData.define(TICKS_ACTIVE, 0);
	}

	@Override
	protected void readAdditionalSaveData(CompoundTag tag) {
		this.entityData.set(TICKS_ACTIVE, tag.getInt("ticks_active"));
		this.entityData.set(MANA, tag.getInt("mana"));
		this.rank = new Rank(getMana());
	}

	@Override
	protected void addAdditionalSaveData(CompoundTag tag) {
		tag.putInt("mana", getMana());
		tag.putInt("ticks_active", getTicksActive());
	}

	@Override
	public Packet<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
	@Override
	public void tick() {
		super.tick();
		AABB eAABB = this.getBoundingBox();
		Level level = this.level;
		Player player = level.getNearestPlayer(this, 1);
		if(!level.isClientSide && player != null) {
			if(eAABB.intersects(player.getBoundingBox())) {
				player.teleportTo(player.getX()+10d, player.getY()+10d, player.getZ()+10d);
			}
			
			//this.entityData.set(TICKS_ACTIVE, this.getTicksActive() + 1);
		}
	}
	
	@Override
	public boolean isPickable() {
		return true;
	}
	
	public String getRank() {
		return this.rank.getRank();
	}
	
	public int getMana() {
		return this.entityData.get(MANA);
	}
	
	public int getTicksActive() {
		return this.entityData.get(TICKS_ACTIVE);
	}
}
