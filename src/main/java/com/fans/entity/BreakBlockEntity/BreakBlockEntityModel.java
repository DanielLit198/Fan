package com.fans.entity.BreakBlockEntity;

import com.fans.Fans;
import com.fans.entity.DoorEntity.DoorEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class BreakBlockEntityModel extends GeoModel<BreakBlockEntity> {
    @Override
    public Identifier getModelResource(BreakBlockEntity animatable) {
        return new Identifier(Fans.MOD,"geo/entity/break_block.geo.json");
    }

    @Override
    public Identifier getTextureResource(BreakBlockEntity animatable) {

        if (animatable.texture != null) {
            return new Identifier("minecraft",animatable.texture);

        }else return new Identifier("minecraft","textures/block/stone.png");
    }

    @Override
    public Identifier getAnimationResource(BreakBlockEntity animatable) {
        return null;
    }
}
