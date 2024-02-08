package com.fans.mixin;

import com.fans.init.SoundInit;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

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
                this.getWorld().playSound((PlayerEntity) null, this.getX(), this.getY(), this.getZ(), SoundInit.WOW, this.getSoundCategory(), volume, pitch);
            }else if (sound == SoundEvents.ENTITY_WARDEN_AGITATED){
                this.getWorld().playSound((PlayerEntity) null, this.getX(), this.getY(), this.getZ(), SoundInit.AGITATED, this.getSoundCategory(), volume, pitch);
            }else if (sound == SoundEvents.ENTITY_WARDEN_ROAR){
                this.getWorld().playSound((PlayerEntity) null, this.getX(), this.getY(), this.getZ(), SoundInit.ROAR, this.getSoundCategory(), volume, pitch);
            }else if (sound == SoundEvents.ENTITY_WARDEN_AMBIENT){
                this.getWorld().playSound((PlayerEntity) null, this.getX(), this.getY(), this.getZ(), SoundInit.AMBIENT, this.getSoundCategory(), volume, pitch);
            }else if (sound == SoundEvents.ENTITY_WARDEN_LISTENING){
                this.getWorld().playSound((PlayerEntity) null, this.getX(), this.getY(), this.getZ(), SoundInit.LISTENING, this.getSoundCategory(), volume, pitch);
            }else if (sound == SoundEvents.ENTITY_WARDEN_LISTENING_ANGRY){
                this.getWorld().playSound((PlayerEntity) null, this.getX(), this.getY(), this.getZ(), SoundInit.LISTENING, this.getSoundCategory(), volume, pitch);
            }else if (sound == SoundEvents.ENTITY_WARDEN_ANGRY){
                this.getWorld().playSound((PlayerEntity) null, this.getX(), this.getY(), this.getZ(), SoundInit.ANGRY, this.getSoundCategory(), volume, pitch);
            }else this.getWorld().playSound((PlayerEntity) null, this.getX(), this.getY(), this.getZ(), sound, this.getSoundCategory(), volume, pitch);
        }

    }
}
