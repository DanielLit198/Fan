package com.fans.init;

import com.fans.entity.BreakBlockEntity.BreakBlockEntityRenderer;
import com.fans.entity.DoorEntity.DoorEntityRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class RendererInit {
    public static void init(){
        EntityRendererRegistry.register(EntityInit.DOOR_ENTITY, DoorEntityRenderer::new);
        EntityRendererRegistry.register(EntityInit.BREAK_BLOCK, BreakBlockEntityRenderer::new);
    }
}
