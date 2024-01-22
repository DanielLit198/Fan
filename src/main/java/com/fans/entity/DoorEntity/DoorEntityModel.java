package com.fans.entity.DoorEntity;

import com.fans.Fans;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class DoorEntityModel extends GeoModel<DoorEntity> {
    @Override
    public Identifier getModelResource(DoorEntity animatable) {
        return new Identifier(Fans.MOD,"geo/entity/door.geo.json");
    }

    @Override
    public Identifier getTextureResource(DoorEntity animatable) {
        if (animatable.getDoorType() != null) {
            return switch (animatable.getDoorType()) {
                case "acacia" -> new Identifier(Fans.MOD, "textures/entity/acacia_door.png");
                case "birch" -> new Identifier(Fans.MOD, "textures/entity/birch_door.png");
                case "dark_oak" -> new Identifier(Fans.MOD, "textures/entity/dark_oak_door.png");
                case "jungle" -> new Identifier(Fans.MOD, "textures/entity/jungle_door.png");
                case "spruce" -> new Identifier(Fans.MOD, "textures/entity/spruce_door.png");
                case "warped" -> new Identifier(Fans.MOD, "textures/entity/warped_door.png");
                case "crimson" -> new Identifier(Fans.MOD, "textures/entity/crimson_door.png");
                default -> new Identifier(Fans.MOD, "textures/entity/door.png");
            };
        }else return new Identifier(Fans.MOD, "textures/entity/door.png");
    }

    @Override
    public Identifier getAnimationResource(DoorEntity animatable) {
        return null;
    }
}
