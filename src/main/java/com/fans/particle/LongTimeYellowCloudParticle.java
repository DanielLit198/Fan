package com.fans.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.math.MathHelper;

public class LongTimeYellowCloudParticle extends SpriteBillboardParticle {
    private final SpriteProvider spriteProvider;

    protected LongTimeYellowCloudParticle(ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, SpriteProvider spriteProvider) {
        super(world, x, y, z, 0.0, 0.0, 0.0);
        float g;
        this.velocityMultiplier = 0.96f;
        this.spriteProvider = spriteProvider;
        float f = 2.5f;
        this.velocityX *= (double)0.1f;
        this.velocityY *= (double)0.1f;
        this.velocityZ *= (double)0.1f;
        this.velocityX += velocityX;
        this.velocityY += velocityY;
        this.velocityZ += velocityZ;
        this.red = 1.0f;
        this.green = 1;
        this.blue = 123;
        this.scale *= 1.875f;
        int i = (int)(1 / (Math.random() * 0.8 + 0.3));
        this.maxAge = (int)Math.max((float)i * 2.5f, 1.0f);
        this.collidesWithWorld = false;
        this.setSpriteForAge(spriteProvider);
    }

    /*
    白色255 255 255
    黄色255 254 132
    橙色250 170 48
    红色246 89 53
     */

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Override
    public float getSize(float tickDelta) {
        return this.scale * MathHelper.clamp(((float)this.age + tickDelta) / (float)this.maxAge * 32.0f, 0.0f, 1.0f);
    }

    @Override
    public void tick() {

        super.tick();
    }


    @Environment(value=EnvType.CLIENT)
    public static class LongTimeCloudFactory implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider spriteProvider;

        public LongTimeCloudFactory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            return new LongTimeYellowCloudParticle(clientWorld, d, e, f, g, h, i, this.spriteProvider);
        }
    }
}
