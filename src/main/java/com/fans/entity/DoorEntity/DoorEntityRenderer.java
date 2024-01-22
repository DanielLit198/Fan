package com.fans.entity.DoorEntity;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class DoorEntityRenderer extends GeoEntityRenderer<DoorEntity> {

    public DoorEntityRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new DoorEntityModel());
    }

    @Override
    public void render(DoorEntity entity, float entityYaw, float partialTick, MatrixStack poseStack, VertexConsumerProvider bufferSource, int packedLight) {
        poseStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(MathHelper.lerp(partialTick, entity.prevYaw, entity.getYaw()) - 90.0F));
        poseStack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(MathHelper.lerp(partialTick, entity.prevPitch, entity.getPitch())));
        poseStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(0));


        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
