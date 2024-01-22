package com.fans.mixin;

import com.fans.effect.ChargeEffect;
import com.fans.effect.ShakeEffect;
import com.fans.init.EffectInit;
import net.minecraft.client.render.Camera;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Camera.class)
public abstract class ChargeCameraMixin {
    @Shadow protected abstract void setRotation(float yaw, float pitch);

    @Inject(method = "update",at = @At("RETURN"))
    public void shake(BlockView area, Entity focusedEntity, boolean thirdPerson, boolean inverseView, float tickDelta, CallbackInfo ci){
        if (focusedEntity instanceof LivingEntity livingEntity){
            livingEntity.getStatusEffects().forEach(statusEffectInstance -> {
                if (statusEffectInstance.getEffectType() instanceof ShakeEffect){
                    float shakeIntensity = 7f;
                    setRotation((float) (livingEntity.prevYaw + (Math.random() - 0.5) * shakeIntensity * tickDelta), (float) (livingEntity.prevPitch + (Math.random() - 0.5) * shakeIntensity * tickDelta));
                }
            });
        }
    }
}
