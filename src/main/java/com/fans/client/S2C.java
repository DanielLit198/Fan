package com.fans.client;

import com.fans.Fans;
import com.fans.entity.DoorEntity.DoorEntity;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.entity.Entity;
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
    }}
