package com.fans.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.FoodComponents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;

public class FlintAndSteelFoodItem extends Item {
    public FlintAndSteelFoodItem() {
        super(new Settings().maxCount(1).food(FoodComponents.DRIED_KELP));
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {

//        World world = context.getWorld();
//        PlayerEntity entity = context.getPlayer();
//
//        if (world instanceof ServerWorld serverWorld){
//            serverWorld.spawnParticles(ParticleInit.LONG_TIME_CLOUD_PARTICLE,entity.getX(),entity.getBoundingBox().getCenter().getY(),entity.getZ(),50,0.3,0.3,0.3,0.01);
//        }
//
//
//
//        Identifier texture = MinecraftClient.getInstance().getBlockRenderManager().getModel(world.getBlockState(context.getBlockPos())).getParticleSprite().getContents().getId();
//        BreakBlockEntity block = new BreakBlockEntity(world,"textures/"+texture.getPath()+".png");
//        block.refreshPositionAfterTeleport(context.getBlockPos().toCenterPos());
//        block.addVelocity(-entity.getRotationVector().getX() + new Random().nextDouble()-0.5,-entity.getRotationVector().getY()+ new Random().nextDouble()-0.5,-entity.getRotationVector().getZ()+ new Random().nextDouble()-0.5);
//
//        world.breakBlock(context.getBlockPos(),false);
//
//        world.spawnEntity(block);


        return super.useOnBlock(context);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        world.createExplosion(null,user.getX(),user.getY(),user.getZ(),2, World.ExplosionSourceType.MOB);
        return super.finishUsing(stack, world, user);
    }
}
