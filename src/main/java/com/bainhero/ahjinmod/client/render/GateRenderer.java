package com.bainhero.ahjinmod.client.render;

import com.bainhero.ahjinmod.AhjinMod;
import com.bainhero.ahjinmod.entity.GateEntity;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public class GateRenderer extends EntityRenderer<GateEntity>
{

	public static final ResourceLocation TEXTURE = new ResourceLocation(AhjinMod.MODID, "textures/entity/gate.png");
	
	public GateRenderer(Context ctx) {
		super(ctx);
	}

	@Override
	public ResourceLocation getTextureLocation(GateEntity entity) {
		return TEXTURE;
	}
	
	@Override
	public void render(GateEntity gate, float yaw, float partialTicks, PoseStack matrix, MultiBufferSource buf, int packedLight) {
		matrix.pushPose();
		@SuppressWarnings("resource")
		Player player = Minecraft.getInstance().player;
		Vec3 playerV = player.getEyePosition(partialTicks);
		Vec3 portal = gate.position();

		float scale = 1.0f;
		double yOffset = 1d;

		// Rotate to face the player
		matrix.translate(0, yOffset, 0);
		matrix.mulPose(new Quaternion(new Vector3f(0, 1, 0), 90, true));
		matrix.mulPose(new Quaternion(new Vector3f(0, 1, 0), 180F - (float) angleOf(portal, playerV), true));
		matrix.scale(2, 1, 1);
		
		matrix.scale(scale, scale, 1);

		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		RenderSystem.setShaderTexture(0, this.getTextureLocation(gate));
		VertexConsumer builder = buf.getBuffer(RenderType.entityCutout(this.getTextureLocation(gate)));
		int color = 3717631;
		int r = color >> 16 & 255, g = color >> 8 & 255, b = color & 255;
		float frameHeight = 1 / 9F;
		int frame = gate.tickCount % 9;
		builder.vertex(matrix.last().pose(), -1, -1, 0).color(r, g, b, 255).uv(1, 1 - frame * frameHeight).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLight).normal(matrix.last().normal(), 0, 1, 0).endVertex();
		builder.vertex(matrix.last().pose(), -1, 1, 0).color(r, g, b, 255).uv(1, 8F / 9 - frame * frameHeight).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLight).normal(matrix.last().normal(), 0, 1, 0).endVertex();
		builder.vertex(matrix.last().pose(), 1, 1, 0).color(r, g, b, 255).uv(0, 8F / 9 - frame * frameHeight).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLight).normal(matrix.last().normal(), 0, 1, 0).endVertex();
		builder.vertex(matrix.last().pose(), 1, -1, 0).color(r, g, b, 255).uv(0, 1 - frame * frameHeight).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLight).normal(matrix.last().normal(), 0, 1, 0).endVertex();

		matrix.popPose();
	}
	
	public static double angleOf(Vec3 p1, Vec3 p2) {
        final double deltaY = p2.z - p1.z;
        final double deltaX = p2.x - p1.x;
        final double result = Math.toDegrees(Math.atan2(deltaY, deltaX));
        return result < 0 ? 360d + result : result;
    }
}
