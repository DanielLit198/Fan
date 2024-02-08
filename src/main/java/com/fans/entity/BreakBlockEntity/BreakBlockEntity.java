package com.fans.entity.BreakBlockEntity;

import com.fans.Fans;
import com.fans.init.EntityInit;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.world.World;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

public class BreakBlockEntity extends PersistentProjectileEntity implements GeoAnimatable {
    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);
    public String texture;

    public BreakBlockEntity(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
        super(entityType, world);
    }
    public BreakBlockEntity(World world,String texture) {
        super(EntityInit.BREAK_BLOCK, world);
        this.texture = texture;
    }
    @Override
    protected SoundEvent getHitSound() {
        return SoundEvents.BLOCK_STONE_BREAK;
    }
    @Override
    public void tick() {
        if (!this.getWorld().isClient()) {
            PacketByteBuf buf = PacketByteBufs.create();
            buf.writeString(texture);
            buf.writeInt(this.getId());
            this.getWorld().getPlayers().forEach(playerEntity -> {
                if (playerEntity instanceof ServerPlayerEntity serverPlayer)
                    ServerPlayNetworking.send(serverPlayer, new Identifier(Fans.MOD, "block_texture"), buf);
            });
        }

        super.tick();
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putString("texture",texture);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        texture = nbt.getString("texture");
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        discard();
        super.onBlockHit(blockHitResult);
    }

    @Override
    protected ItemStack asItemStack() {
        return null;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return geoCache;
    }

    @Override
    public double getTick(Object object) {
        return 0;
    }
}
