package com.fans.entity.BreakBlockEntity;

import com.fans.entity.DoorEntity.DoorEntity;
import com.fans.entity.DoorEntity.DoorEntityModel;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class BreakBlockEntityRenderer extends GeoEntityRenderer<BreakBlockEntity> {

    public BreakBlockEntityRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new BreakBlockEntityModel());
    }

}
