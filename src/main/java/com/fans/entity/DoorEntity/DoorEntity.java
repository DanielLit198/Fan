package com.fans.entity.DoorEntity;

import com.fans.Fans;
import com.fans.init.EntityInit;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.EndGatewayBlockEntity;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageSources;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

public class DoorEntity extends PersistentProjectileEntity implements GeoAnimatable {

    public String type;

    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);
    public DoorEntity(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
        super(entityType, world);
    }
    public DoorEntity(World world,String type) {
        super(EntityInit.DOOR_ENTITY, world);
        this.type = type;
    }

    public String getDoorType() {
        return type;
    }

    public void handleStatus(byte status) {
        if (status == 3) {
            for(int i = 0; i < 30; ++i) {
                this.getWorld().addParticle(new ItemStackParticleEffect(ParticleTypes.ITEM, Items.OAK_DOOR.getDefaultStack()), this.getX(), this.getY(), this.getZ(), ((double)this.random.nextFloat() - 0.5) * 1, ((double)this.random.nextFloat() - 0.5) * 1, ((double)this.random.nextFloat() - 0.5) * 1);
                this.getWorld().addParticle(new ItemStackParticleEffect(ParticleTypes.ITEM, Items.OAK_DOOR.getDefaultStack()), this.getX(), this.getY()+1, this.getZ(), ((double)this.random.nextFloat() - 0.5) * 1, ((double)this.random.nextFloat() - 0.5) * 1, ((double)this.random.nextFloat() - 0.5) * 1);
            }
        }

    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        this.playSound(SoundEvents.ENTITY_ZOMBIE_BREAK_WOODEN_DOOR,3,1);
        getWorld().sendEntityStatus((Entity) this, (byte) 3);
        discard();
        super.onBlockHit(blockHitResult);
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        entityHitResult.getEntity().damage(entityHitResult.getEntity().getDamageSources().playerAttack((PlayerEntity) this.getOwner()),10);
        ((LivingEntity)entityHitResult.getEntity()).takeKnockback(2,-this.getRotationVector().getX(),-this.getRotationVector().getZ());
        this.playSound(SoundEvents.ENTITY_ZOMBIE_BREAK_WOODEN_DOOR,3,1);
        getWorld().sendEntityStatus((Entity) this, (byte) 3);
        discard();
        super.onEntityHit(entityHitResult);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.type=nbt.getString("type");
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putString("type",type);
    }

    @Override
    protected ItemStack asItemStack() {
        return null;
    }

    @Override
    public void tick() {
        setPitch(getPitch()-10);
        if (!this.getWorld().isClient()) {
            PacketByteBuf buf = PacketByteBufs.create();
            buf.writeString(type);
            buf.writeInt(this.getId());
            this.getWorld().getPlayers().forEach(playerEntity -> {
                if (playerEntity instanceof ServerPlayerEntity serverPlayer)
                    ServerPlayNetworking.send(serverPlayer, new Identifier(Fans.MOD, "door_type"), buf);
            });
        }
        super.tick();
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {}

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return geoCache;
    }

    @Override
    public double getTick(Object o) {
        return 0;
    }


}
