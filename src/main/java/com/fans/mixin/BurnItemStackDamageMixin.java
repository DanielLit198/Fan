package com.fans.mixin;

import com.fans.init.EnchantmentInit;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Item.class)
public abstract class BurnItemStackDamageMixin {


    @Inject(method = "inventoryTick",at = @At("HEAD"))
    public void tick(ItemStack stack, World world, Entity entity, int slot, boolean selected, CallbackInfo ci){
        if (EnchantmentHelper.getLevel(EnchantmentInit.BURN,stack) > 0){
            stack.getNbt().putInt("tick",stack.getNbt().getInt("tick") + 1);
            if (stack.getNbt().getInt("tick") >= 20) {
                stack.damage(1, (LivingEntity) entity, (p) -> {
                    p.sendToolBreakStatus(Hand.MAIN_HAND);
                });
                stack.getNbt().putInt("tick",0);
            }
        }
    }
}
