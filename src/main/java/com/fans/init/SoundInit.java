package com.fans.init;

import com.fans.Fans;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class SoundInit {
    public static final SoundEvent WOW = initSound("wow");
    public static final SoundEvent CHARGE = initSound("charge");
    public static final SoundEvent AGITATED = initSound("agitated");
    public static final SoundEvent ROAR = initSound("roar");
    public static final SoundEvent AMBIENT = initSound("ambient");
    public static final SoundEvent LISTENING = initSound("listening");
    public static final SoundEvent ANGRY = initSound("angry");
    private static SoundEvent initSound(String name){
        Identifier id = new Identifier(Fans.MOD,name);
        return Registry.register(Registries.SOUND_EVENT,id,SoundEvent.of(id));
    }

    public static void init(){

    }
}
