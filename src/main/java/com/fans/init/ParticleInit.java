package com.fans.init;

import com.fans.Fans;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ParticleInit {
    public static final DefaultParticleType LONG_TIME_CLOUD_PARTICLE = FabricParticleTypes.simple(true);
    public static final DefaultParticleType LONG_TIME_RED_CLOUD_PARTICLE = FabricParticleTypes.simple(true);
    public static final DefaultParticleType LONG_TIME_YELLOW_CLOUD_PARTICLE = FabricParticleTypes.simple(true);
    public static final DefaultParticleType LONG_TIME_ORANGE_CLOUD_PARTICLE = FabricParticleTypes.simple(true);

    public static void init(){
        Registry.register(Registries.PARTICLE_TYPE,new Identifier(Fans.MOD,"long_time_cloud"),LONG_TIME_CLOUD_PARTICLE);
        Registry.register(Registries.PARTICLE_TYPE,new Identifier(Fans.MOD,"long_time_red_cloud"),LONG_TIME_RED_CLOUD_PARTICLE);
        Registry.register(Registries.PARTICLE_TYPE,new Identifier(Fans.MOD,"long_time_yellow_cloud"),LONG_TIME_YELLOW_CLOUD_PARTICLE);
        Registry.register(Registries.PARTICLE_TYPE,new Identifier(Fans.MOD,"long_time_orange_cloud"),LONG_TIME_ORANGE_CLOUD_PARTICLE);
    }
}
