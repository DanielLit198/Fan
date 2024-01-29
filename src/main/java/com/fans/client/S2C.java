package com.fans.client;

import com.fans.Fans;
import com.fans.entity.DoorEntity.DoorEntity;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

public class S2C {


    public static void init(){
        //更新门的类型材质
        ClientPlayNetworking.registerGlobalReceiver(new Identifier(Fans.MOD, "door_type"), (client, handler, buf, responseSender) -> {
            String type = buf.readString();
            int id = buf.readInt();
            client.world.getEntityById(id);
            Entity entity = client.world.getEntityById(id);

            client.execute(() -> {
                if (entity instanceof DoorEntity door) {
                    door.type = type;
                }
            });
        });
        //冲撞效果对于玩家的服务器客户端的同步
        ClientPlayNetworking.registerGlobalReceiver(new Identifier(Fans.MOD, "charge"), (client, handler, buf, responseSender) -> {
            int id = buf.readInt();
            double speed = buf.readDouble();
            client.world.getEntityById(id);
            Entity entity = client.world.getEntityById(id);

            client.execute(() -> {
                Vec3d velocity = entity.getRotationVector();
                entity.addVelocity(velocity.getX()* speed,0,velocity.getZ()* speed);
            });
        });
        //冲撞快速撞击地面产生的粒子
        ClientPlayNetworking.registerGlobalReceiver(new Identifier(Fans.MOD, "impact_ground_particle"), (client, handler, buf, responseSender) -> {
            int id = buf.readInt();
            LivingEntity entity = (LivingEntity) client.world.getEntityById(id);

            client.execute(() -> {
                double a = 0;
                double b = 0;

                double r = 2;
                double fr = 3;

                for (int i = 0; i < 3; i++) {
                    b = b + 0.1;
                    r = r + b;
                    fr = fr + b;
                    for (int j = 0; j < 200; j++) {
                        a = a + 0.01 * Math.PI;
                        double x = entity.getX() + r * Math.cos(a);
                        double z = entity.getZ() + r * Math.sin(a);

                        double fx = entity.getX() + fr * Math.cos(a);
                        double fz = entity.getZ() + fr * Math.sin(a);
                        entity.getWorld().addParticle(ParticleTypes.CLOUD,true, x, entity.getBoundingBox().getCenter().getY() + 2, z, fx - x, 0, fz - z);
                    }
                }
            });
        });

        ClientPlayNetworking.registerGlobalReceiver(new Identifier(Fans.MOD, "circle_particle"), (client, handler, buf, responseSender) -> {
            double a = buf.readDouble();
            double b = buf.readDouble();
            double c = buf.readDouble();
            client.execute(() -> {
                client.world.addParticle(ParticleTypes.CLOUD, true,a,b,c, 0, 0, 0);
            });
        });
    }}
