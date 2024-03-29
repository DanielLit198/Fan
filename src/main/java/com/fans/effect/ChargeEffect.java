package com.fans.effect;

import com.fans.Fans;
import com.fans.entity.BreakBlockEntity.BreakBlockEntity;
import com.fans.entity.DoorEntity.DoorEntity;
import com.fans.init.EffectInit;
import com.fans.init.ParticleInit;
import com.fans.init.SoundInit;
import com.google.common.collect.Sets;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.fluid.FluidState;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.explosion.EntityExplosionBehavior;
import net.minecraft.world.explosion.ExplosionBehavior;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Random;

public class ChargeEffect extends StatusEffect {
    public double speed;
    public double maxSpeed;
    public int particleTick = 0;


    public ChargeEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }



    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        double v = entity.getVelocity().length();
        System.out.println(maxSpeed);
        Vec3d velocity = entity.getRotationVector();

        //移动
        if (speed <= maxSpeed + (amplifier + 1) * 0.8) {
            speed = speed + 0.1 + (amplifier * 0.1);
        }
        double vy = velocity.getY();
        if ((vy + entity.getEyeY()) > entity.getEyeY()) vy = 0;
        entity.setVelocity(velocity.getX() * speed, vy * speed, velocity.getZ() * speed);
        if (!entity.getWorld().isClient()) {
            PacketByteBuf buf = PacketByteBufs.create();
            buf.writeInt(entity.getId());
            buf.writeDouble(speed);
            entity.getWorld().getPlayers().forEach(playerEntity -> {
                if (playerEntity instanceof ServerPlayerEntity serverPlayer) {
                    ServerPlayNetworking.send(serverPlayer, new Identifier(Fans.MOD, "charge"), buf);
                }
            });
        }


        if (v > 2) {
            //快速
            //方块的获取以及破坏
            BlockPos fastBreakBlockPos;
            HitResult fastRayTraceResult = entity.raycast(v * 3, 0.0F, false);
            if (fastRayTraceResult instanceof BlockHitResult blockHitResult) {
                fastBreakBlockPos = new BlockPos(blockHitResult.getBlockPos().getX(), blockHitResult.getBlockPos().getY(), blockHitResult.getBlockPos().getZ());

                if (entity.getWorld().getBlockState(fastBreakBlockPos).isFullCube(entity.getWorld(), entity.getBlockPos()) &&
                        entity.getWorld().getBlockState(fastBreakBlockPos).isFullCube(entity.getWorld(), entity.getBlockPos().add(0, -1, 0))) {
                    entity.setStatusEffect(new StatusEffectInstance(EffectInit.SHAKE, 40, 1), entity);
                }

                if (!entity.getWorld().getBlockState(fastBreakBlockPos).isOf(Blocks.AIR) || !entity.getWorld().getBlockState(fastBreakBlockPos.add(0, -1, 0)).isOf(Blocks.AIR)) {
                    for (int i = 0; i < 3; i++) {
                        breakBlock(entity.getWorld(),entity,fastBreakBlockPos, (int) ((v + 1)*5));
                    }
                    entity.playSound(SoundInit.CHARGE,20,-10);
                    entity.playSound(SoundInit.CHARGE,20,10);
                    entity.playSound(SoundInit.CHARGE,20,-5);
                    entity.playSound(SoundInit.CHARGE,30,-20);
                    impact_ground_particle(entity,fastBreakBlockPos);
                    entity.setVelocity(0,0,0);
                }
            }
            if (entity.getWorld() instanceof ServerWorld serverWorld){
                serverWorld.spawnParticles(ParticleInit.LONG_TIME_RED_CLOUD_PARTICLE,entity.getX(),entity.getBoundingBox().getCenter().getY(),entity.getZ(),80,1,1,1,0.05);
                serverWorld.spawnParticles(ParticleInit.LONG_TIME_CLOUD_PARTICLE,entity.getX(),entity.getBoundingBox().getCenter().getY(),entity.getZ(),80,1,1,1,0.05);
                serverWorld.spawnParticles(ParticleInit.LONG_TIME_YELLOW_CLOUD_PARTICLE,entity.getX(),entity.getBoundingBox().getCenter().getY(),entity.getZ(),80,1,1,1,0.05);
                serverWorld.spawnParticles(ParticleInit.LONG_TIME_ORANGE_CLOUD_PARTICLE,entity.getX(),entity.getBoundingBox().getCenter().getY(),entity.getZ(),80,1,1,1,0.05);
            }
            circleParticle(entity);
        } else {
            //慢速
            //方块的获取以及破坏
            BlockPos breakBlockPos;
            HitResult rayTraceResult = entity.raycast(1.0D, 0.0F, false);
            if (rayTraceResult instanceof BlockHitResult blockHitResult) {
                breakBlockPos = new BlockPos(blockHitResult.getBlockPos().getX(), entity.getBlockY(), blockHitResult.getBlockPos().getZ());

                BlockState state = entity.getWorld().getBlockState(breakBlockPos);
                //防止玩家的视角超过玩家的身高会破坏玩家身高以上的方块
                if (entity.getWorld().getBlockState(breakBlockPos).getBlock() instanceof DoorBlock block) {
                    //门的击飞
                    DoubleBlockHalf half = state.get(DoorBlock.HALF);
                    BlockPos otherHalfPos = (half == DoubleBlockHalf.LOWER) ? breakBlockPos.up() : breakBlockPos.down();
                    entity.getWorld().removeBlock(breakBlockPos, false);
                    entity.getWorld().removeBlock(otherHalfPos, false);

                    DoorEntity doorEntity = new DoorEntity(entity.getWorld(), block.getBlockSetType().name());
                    doorEntity.setOwner(entity);
                    double x = state.get(DoorBlock.FACING).getOffsetX();
                    double y = state.get(DoorBlock.FACING).getOffsetY();
                    double z = state.get(DoorBlock.FACING).getOffsetZ();

                    double pitch = Math.atan2(-y, Math.sqrt(x * x + z * z)) * (180 / Math.PI);
                    double yaw = Math.atan2(-x, z) * (180 / Math.PI);

                    doorEntity.refreshPositionAfterTeleport(breakBlockPos.getX() + 0.5, breakBlockPos.getY(), breakBlockPos.getZ() + 0.5);
                    doorEntity.setPitch((float) pitch);
                    doorEntity.setYaw((float) yaw);
                    doorEntity.setVelocity(entity.getRotationVector().add(0, 0.1, 0).multiply(3f));
                    entity.getWorld().spawnEntity(doorEntity);

                    doorEntity.type = block.getBlockSetType().name();
                } else if (!entity.getWorld().getBlockState(breakBlockPos).isOf(Blocks.AIR) || !entity.getWorld().getBlockState(breakBlockPos.add(0, 1, 0)).isOf(Blocks.AIR)) {
                    if (entity.getWorld().getBlockState(breakBlockPos).isFullCube(entity.getWorld(), entity.getBlockPos()) &&
                            entity.getWorld().getBlockState(breakBlockPos).isFullCube(entity.getWorld(), entity.getBlockPos().add(0, 1, 0))) {
                        entity.setStatusEffect(new StatusEffectInstance(EffectInit.SHAKE, 10, 1), entity);
                        entity.playSound(SoundInit.CHARGE,20,10);
                    }
                    if (!entity.getWorld().isClient()) {
                        for (int xOffset = -amplifier; xOffset <= amplifier; xOffset++) {
                            for (int zOffset = -amplifier; zOffset <= amplifier; zOffset++) {
                                BlockPos blockPos = breakBlockPos.add(xOffset, 0, zOffset);
                                entity.getWorld().breakBlock(blockPos, true);
                                entity.getWorld().breakBlock(blockPos.add(0, 1, 0), true);
                                entity.getWorld().breakBlock(blockPos.add(0, amplifier + 1, 0), true);
                                entity.getWorld().breakBlock(blockPos.add(0, amplifier, 0), true);
                            }
                        }
                    }

                }
            }
        }
        entity.getWorld().getEntitiesByClass(LivingEntity.class,new Box(entity.getBlockPos()).expand(amplifier+1),i->true).forEach(livingEntity -> {
            if (livingEntity != entity) {
                livingEntity.damage(entity.getDamageSources().mobAttackNoAggro(entity), 10);
                livingEntity.takeKnockback(2.5, -velocity.getX(), -velocity.getZ());
            }
        });

        if (entity.getWorld() instanceof ServerWorld serverWorld){
            serverWorld.spawnParticles(ParticleTypes.CLOUD,entity.getX(),entity.getBoundingBox().getCenter().getY(),entity.getZ(),10,0.02,0.1,0.02,0);
        }

        super.applyUpdateEffect(entity, amplifier);
    }

    public void breakBlock(World world,LivingEntity entity, BlockPos breakPos, int power) {
        ExplosionBehavior behavior = new EntityExplosionBehavior(entity);
        int l;
        int k;
        HashSet<BlockPos> set = Sets.newHashSet();
        int i = 16;
        for (int j = 0; j < 32; ++j) {
            for (k = 0; k < 32; ++k) {
                block2: for (l = 0; l < 32; ++l) {
                    if (j != 0 && j != 15 && k != 0 && k != 15 && l != 0 && l != 15) continue;
                    double d = (float)j / 15.0f * 2.0f - 1.0f;
                    double e = (float)k / 15.0f * 2.0f - 1.0f;
                    double f = (float)l / 15.0f * 2.0f - 1.0f;
                    double g = Math.sqrt(d * d + e * e + f * f);
                    d /= g;
                    e /= g;
                    f /= g;
                    double m = breakPos.getX();
                    double n = breakPos.getY();
                    double o = breakPos.getZ();
                    float p = 0.3f;
                    for (float h = power * (0.7f + world.random.nextFloat() * 0.6f); h > 0.0f; h -= 0.22500001f) {
                        BlockPos blockPos = BlockPos.ofFloored(m, n, o);
                        BlockState blockState = world.getBlockState(blockPos);
                        FluidState fluidState = world.getFluidState(blockPos);
                        if (!world.isInBuildLimit(blockPos)) continue block2;
                        Optional<Float> optional = behavior.getBlastResistance(null, world, blockPos, Blocks.SAND.getDefaultState(), fluidState);
                        if (optional.isPresent()) {
                            h -= (optional.get().floatValue() + 0.3f) * 0.3f;
                        }
                        if (h > 0.0f) {
                            set.add(blockPos);
                        }
                        m += d * (double)0.3f;
                        n += e * (double)0.3f;
                        o += f * (double)0.3f;
                    }
                }
            }
        }
        ObjectArrayList<BlockPos> affectedBlocks = new ObjectArrayList<>();
        affectedBlocks.addAll((Collection<BlockPos>)set);
        affectedBlocks.forEach(blockPos -> {
            if (world.getBlockState(blockPos).isFullCube(world,blockPos) && new Random().nextDouble()>0.7) {
                Identifier texture = MinecraftClient.getInstance().getBlockRenderManager().getModel(world.getBlockState(blockPos)).getParticleSprite().getContents().getId();
                BreakBlockEntity block = new BreakBlockEntity(world, "textures/" + texture.getPath() + ".png");
                block.refreshPositionAfterTeleport(blockPos.toCenterPos());
                double j = 1.3;
                block.addVelocity((-entity.getRotationVector().getX() + new Random().nextDouble()-0.5)*j,
                        (-entity.getRotationVector().getY()+ new Random().nextDouble()-0.5)*j,
                        (-entity.getRotationVector().getZ()+ new Random().nextDouble()-0.5)*j);
                world.spawnEntity(block);
            }

            if (!world.isClient()) {
                world.removeBlock(blockPos, false);
            }
        });

        entity.removeStatusEffect(this);

    }

    public void impact_ground_particle(LivingEntity entity,BlockPos pos){
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeInt(entity.getId());
        buf.writeDouble(pos.getY());
        entity.getWorld().getPlayers().forEach(playerEntity -> {
            if (playerEntity instanceof ServerPlayerEntity serverPlayer) {
                ServerPlayNetworking.send(serverPlayer, new Identifier(Fans.MOD,"impact_ground_particle"), buf);
            }
        });
    }
    public void circleParticle(Entity user){
        particleTick = particleTick + 1;
        if (particleTick >= 18) {
            World world = user.getWorld();
            if (world.isClient()) return;
            for (int t = 0; t < 300; t++) {
                double[] first = circleParticlePoint(user, 1.5, 4, t);
                double[] last = circleParticlePoint(user, 2.5, 4, t);
                PacketByteBuf bf = PacketByteBufs.create();
                bf.writeDouble(first[0]);
                bf.writeDouble(first[1]);
                bf.writeDouble(first[2]);
                bf.writeDouble(last[0]);
                bf.writeDouble(last[1]);
                bf.writeDouble(last[2]);
                // 迭代世界上所有追踪位置的玩家，并将数据包发送给每个玩家
                for (ServerPlayerEntity player : user.getServer().getPlayerManager().getPlayerList()) {
                    ServerPlayNetworking.send(player, new Identifier(Fans.MOD, "circle_particle"), bf);
                }
            }
            particleTick = 0;
        }
    }

    public double[] circleParticlePoint(Entity user,double h,double k,double t){
        //h为半径,k:距离,t:循环
        double a = user.getX() + user.getRotationVector().getX() * k;
        double b = user.getEyeY() + user.getRotationVector().getY() * k;
        double c = user.getZ() + user.getRotationVector().getZ() * k;
        double r = h;
        double vx = user.getRotationVector().getX();
        double vy = user.getRotationVector().getY();
        double vz = user.getRotationVector().getZ();
        double[] n = {vx,vy,vz};
        double[] u = {n[1],-n[0],0};
        double[] v = {(n[1] * u[2])-(n[2] * u[1]),
                (n[2] * u[0])-(n[0] * u[2]),
                (n[0] * u[1])-(n[1] * u[0]),
        };
        //单位化向量:
        double us = Math.sqrt((u[0]*u[0])+(u[1]*u[1])+(u[2]*u[2]));
        double vs = Math.sqrt((v[0]*v[0])+(v[1]*v[1])+(v[2]*v[2]));
        double[] uu = {u[0]/us,u[1]/us,0};
        double[] vv = {v[0]/vs,v[1]/vs,v[2]/vs};
        /*
        x=x_0+r(cos t)(d/|u|)+r(sin t)(b f-c e)/|v|
        y=y_0+r(cos t)(e/|u|)+r(sin t)(c d-a f)/|v|
        z=z_0+r(cos t)(f/|u|)+r(sin t)(a e-b d)/|v|
         */
        double x = a + r * Math.cos(t) * uu[0] + r * Math.sin(t) * vv[0];
        double y = b + r * Math.cos(t) * uu[1] + r * Math.sin(t) * vv[1];
        double z = c + r * Math.cos(t) * uu[2] + r * Math.sin(t) * vv[2];

        return new double[]{x, y, z};
    }

    @Override
    public void onRemoved(AttributeContainer attributeContainer) {
        speed = 0.0f;
        particleTick = 0;
        super.onRemoved(attributeContainer);
    }

    @Override
    public void applyInstantEffect(@Nullable Entity source, @Nullable Entity attacker, LivingEntity target, int amplifier, double proximity) {
        maxSpeed = target.getVelocity().length();
        speed = target.getVelocity().length();
        target.addStatusEffect(new StatusEffectInstance(EffectInit.CHARGE,80,amplifier));
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    @Override
    public boolean isInstant() {
        return true;
    }

}
