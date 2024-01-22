package com.fans.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.FoodComponents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class FlintAndSteelFoodItem extends Item {
    public FlintAndSteelFoodItem() {
        super(new Settings().maxCount(1).food(FoodComponents.DRIED_KELP));
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        world.createExplosion(null,user.getX(),user.getY(),user.getZ(),2, World.ExplosionSourceType.MOB);
        return super.finishUsing(stack, world, user);
    }
}
