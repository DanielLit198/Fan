package com.fans.item;

import com.fans.init.EnchantmentInit;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.TntEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class FlintAndSteelItem extends net.minecraft.item.FlintAndSteelItem{
    public FlintAndSteelItem() {
        super(new Settings().maxCount(1).maxDamage(64));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        ItemStack main = user.getStackInHand(Hand.MAIN_HAND);
        ItemStack off = user.getStackInHand(Hand.OFF_HAND);

        if (hand == Hand.MAIN_HAND) {
            if (!user.getStackInHand(Hand.OFF_HAND).isEmpty() && EnchantmentHelper.getLevel(EnchantmentInit.BURN, off) <= 0) {
                brun(off,user);
            }else return TypedActionResult.pass(stack);
        } else {
            if (!user.getStackInHand(Hand.MAIN_HAND).isEmpty() && EnchantmentHelper.getLevel(EnchantmentInit.BURN, main) <= 0) {
                brun(main,user);
            }else return TypedActionResult.pass(stack);
        }

        Vec3d rotation = user.getRotationVector();
        if (world instanceof ServerWorld serverWorld){
            serverWorld.spawnParticles(ParticleTypes.FLAME,user.getX()+rotation.getX(), user.getEyeY()+rotation.getY(), user.getZ()+rotation.getZ(),
                    1,0.1,0.1,0.1,0.01);
        }

        user.playSound(SoundEvents.ITEM_FLINTANDSTEEL_USE,1,1);
        user.playSound(SoundEvents.ITEM_FIRECHARGE_USE,1,1);

        stack.damage(1,user, (p) -> {
            p.sendToolBreakStatus(Hand.MAIN_HAND);
        });

        return TypedActionResult.success(stack);

    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        return super.finishUsing(stack, world, user);
    }

    public void brun(ItemStack item,PlayerEntity player){
        if (item.getItem() == Items.WOODEN_SWORD){
            item.addEnchantment(EnchantmentInit.BURN,1);
            item.addEnchantment(Enchantments.FIRE_ASPECT,1);
        }else if (item.getItem() == Items.BOW){
            item.addEnchantment(EnchantmentInit.BURN,1);
            item.addEnchantment(Enchantments.FLAME,1);
        }else if (item.getItem() == Items.TNT){
            item.decrement(1);
            TntEntity tnt = EntityType.TNT.create(player.getWorld());
            tnt.refreshPositionAfterTeleport(player.getX(),player.getEyeY(),player.getZ());
            player.getWorld().spawnEntity(tnt);
        }else if (item.getItem() == Items.STICK){
            item.decrement(1);
            player.giveItemStack(new ItemStack(Items.BLAZE_ROD));
        }
    }
}
