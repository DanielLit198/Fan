package com.fans.mixin;

import com.fans.Fans;
import com.fans.entity.DoorEntity.DoorEntity;
import com.fans.init.EntityInit;
import com.fans.init.SoundInit;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.*;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.time.Year;

@Mixin(DoorBlock.class)
public abstract class KickDoorMixin {
    @Shadow public abstract BlockSetType getBlockSetType();

    /**
     * @author
     * @reason
     */
    @Overwrite
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        
        if (state.getBlock() instanceof DoorBlock block) {
            DoubleBlockHalf half = state.get(DoorBlock.HALF);
            BlockPos otherHalfPos = (half == DoubleBlockHalf.LOWER) ? pos.up() : pos.down();
            world.removeBlock(pos, false);
            world.removeBlock(otherHalfPos, false);
        }

        player.playSound(SoundEvents.ENTITY_ZOMBIE_ATTACK_WOODEN_DOOR,1,1);

        DoorEntity doorEntity = new DoorEntity(world,getBlockSetType().name());
        doorEntity.setOwner(player);
        double x = state.get(DoorBlock.FACING).getOffsetX();
        double y = state.get(DoorBlock.FACING).getOffsetY();
        double z = state.get(DoorBlock.FACING).getOffsetZ();

        double pitch = Math.atan2(-y, Math.sqrt(x * x + z * z)) * (180 / Math.PI);
        double yaw = Math.atan2(-x, z) * (180 / Math.PI);

        doorEntity.refreshPositionAfterTeleport(pos.getX()+0.5,pos.getY(),pos.getZ()+0.5);
        doorEntity.setPitch((float) pitch);
        doorEntity.setYaw((float) yaw);
        doorEntity.setVelocity(player.getRotationVector().multiply(2f));

        world.spawnEntity(doorEntity);

        doorEntity.type = getBlockSetType().name();

        return ActionResult.success(world.isClient);
    }

}