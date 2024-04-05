package com.bainhero.ahjinmod.client.gui;

import com.bainhero.ahjinmod.AhjinMod;
import com.bainhero.ahjinmod.network.AhjinPacketHandler;
import com.bainhero.ahjinmod.network.SpawnGateMessage;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class GateSummonScreen extends Screen 
{
	private final ResourceLocation backgroundTexture = new ResourceLocation(AhjinMod.MODID, "textures/screens/ahjin_background.png");
	private BlockPos pos;
	
	public GateSummonScreen(String title, BlockPos pos) {
		super(Component.literal(title).withStyle(ChatFormatting.AQUA));
		this.pos = pos;
	}
	
	@Override
	protected void init() {
		addRenderableWidget(new Button(this.width/2 - Button.DEFAULT_WIDTH/2, this.height/2 - Button.DEFAULT_HEIGHT/2, Button.DEFAULT_WIDTH, Button.DEFAULT_HEIGHT, Component.literal("Spawn"),
				(pos) -> {
					this.onSpawn();
					this.onClose();
					this.removed();
				}));
	}
	
	@Override
	public void render(PoseStack pose, int mouseX, int mouseY, float partialTick) {
		
		this.renderBackground(pose);
		
		RenderSystem.setShaderTexture(0, this.backgroundTexture);
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		blit(pose, this.width / 2 - (420 / 2), this.height / 2 - (256 / 2), 0, 0, 420, 256, 420, 256);
		drawCenteredString(pose, this.font, this.title, this.width / 2, 20, 16777215);
		super.render(pose, mouseX, mouseY, partialTick);
	}
	
	private void onSpawn() {
		AhjinPacketHandler.INSTANCE.sendToServer(new SpawnGateMessage(this.getBlockPos()));
	}
	
	private BlockPos getBlockPos() {
		return this.pos;
	}
	
	@Override
	public boolean isPauseScreen() {
		return false;
	}
	
}
