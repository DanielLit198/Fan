package com.fans.mixin;

import com.fans.init.SoundInit;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.SonicBoomTask;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.WardenEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Unit;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.Objects;
import java.util.Optional;

@Mixin(Entity.class)
public abstract class WowMixin {
    @Shadow public abstract boolean isSilent();

    @Shadow public abstract World getWorld();

    @Shadow public abstract double getX();

    @Shadow public abstract double getY();

    @Shadow public abstract double getZ();

    @Shadow public abstract SoundCategory getSoundCategory();

    /**
     * @author
     * @reason
     */
    @Overwrite
    public void playSound(SoundEvent sound, float volume, float pitch) {

        if (!this.isSilent()) {
            if (sound == SoundEvents.ENTITY_WARDEN_SONIC_BOOM){
                this.getWorld().playSound((PlayerEntity)null, this.getX(), this.getY(), this.getZ(), SoundInit.WOW, this.getSoundCategory(), volume, pitch);
            }else this.getWorld().playSound((PlayerEntity)null, this.getX(), this.getY(), this.getZ(), sound, this.getSoundCategory(), volume, pitch);

        }

    }
}
