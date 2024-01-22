package com.fans.init;

import com.fans.Fans;
import com.fans.entity.DoorEntity.DoorEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class EntityInit {
    public static final EntityType<DoorEntity> DOOR_ENTITY = Registry.register(
            Registries.ENTITY_TYPE,new Identifier(Fans.MOD,"door"),
            FabricEntityTypeBuilder.<DoorEntity>create(SpawnGroup.MISC,DoorEntity::new)
                    .dimensions(EntityDimensions.fixed(0.5f,1.8f)).trackRangeBlocks(128).trackedUpdateRate(10).build());

    public static void init(){

    }
}
