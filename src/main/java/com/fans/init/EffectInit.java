package com.fans.init;

import com.fans.Fans;
import com.fans.effect.ChargeEffect;
import com.fans.effect.ShakeEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class EffectInit {

    public static StatusEffect CHARGE;
    public static StatusEffect SHAKE;

    public static StatusEffect register(String name, StatusEffect effect){
        return Registry.register(Registries.STATUS_EFFECT,new Identifier(Fans.MOD,name), effect);
    }

    public static void init(){
        CHARGE = register("charge",new ChargeEffect(StatusEffectCategory.NEUTRAL,159753));
        SHAKE = register("shake",new ShakeEffect(StatusEffectCategory.NEUTRAL,101457));
    }
}
