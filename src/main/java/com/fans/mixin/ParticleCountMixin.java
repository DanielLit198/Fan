package com.fans.mixin;


import com.google.common.collect.EvictingQueue;
import com.google.common.collect.Lists;
import net.minecraft.client.particle.EmitterParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.particle.ParticleTextureSheet;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.*;


@Mixin(ParticleManager.class)
public abstract class ParticleCountMixin {
    /**
     * @author
     * @reason
     */
    @Overwrite
    public void tick() {
        this.particles.forEach((sheet, queue) -> {
            this.world.getProfiler().push(sheet.toString());
            this.tickParticles(queue);
            this.world.getProfiler().pop();
        });
        if (!this.newEmitterParticles.isEmpty()) {
            List<EmitterParticle> list = Lists.newArrayList();
            Iterator<EmitterParticle> var2 = this.newEmitterParticles.iterator();

            while(var2.hasNext()) {
                EmitterParticle emitterParticle = var2.next();
                emitterParticle.tick();
                if (!emitterParticle.isAlive()) {
                    list.add(emitterParticle);
                }
            }

            this.newEmitterParticles.removeAll(list);
        }

        Particle particle;
        if (!this.newParticles.isEmpty()) {
            while((particle = this.newParticles.poll()) != null)
                this.particles.computeIfAbsent(particle.getType(), (sheet) -> EvictingQueue.create(300000)).add(particle);
        }

    }
    @Shadow
    @Final private Map<ParticleTextureSheet, Queue<Particle>> particles;

    @Shadow protected ClientWorld world;

    @Shadow protected abstract void tickParticles(Collection<Particle> particles);

    @Shadow @Final private Queue<EmitterParticle> newEmitterParticles;

    @Shadow
    @Final
    private Queue<Particle> newParticles;
}
