package com.fans;

import com.fans.client.S2C;
import com.fans.init.ParticleInit;
import com.fans.init.RendererInit;
import com.fans.init.SoundInit;
import com.fans.particle.LongTimeCloudParticle;
import com.fans.particle.LongTimeOrangeCloudParticle;
import com.fans.particle.LongTimeRedCloudParticle;
import com.fans.particle.LongTimeYellowCloudParticle;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;

public class FansClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        RendererInit.init();
        S2C.init();
        SoundInit.init();

        ParticleFactoryRegistry.getInstance().register(ParticleInit.LONG_TIME_CLOUD_PARTICLE, LongTimeCloudParticle.LongTimeCloudFactory::new);
        ParticleFactoryRegistry.getInstance().register(ParticleInit.LONG_TIME_RED_CLOUD_PARTICLE, LongTimeRedCloudParticle.LongTimeCloudFactory::new);
        ParticleFactoryRegistry.getInstance().register(ParticleInit.LONG_TIME_YELLOW_CLOUD_PARTICLE, LongTimeYellowCloudParticle.LongTimeCloudFactory::new);
        ParticleFactoryRegistry.getInstance().register(ParticleInit.LONG_TIME_ORANGE_CLOUD_PARTICLE, LongTimeOrangeCloudParticle.LongTimeCloudFactory::new);
    }
}
