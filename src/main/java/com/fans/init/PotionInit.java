package com.fans.init;

import com.fans.Fans;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static com.fans.mixin.BrewingRecipeRegistryMixin.invokeRegisterPotionRecipe;

public class PotionInit {
    public static final Potion CHARGE = registerPotion("charge",EffectInit.CHARGE,20,0);
    public static final Potion CHARGE_STRONG = registerPotion("charge_strong",EffectInit.CHARGE,40,1);
    public static final Potion CHARGE_LONG = registerPotion("charge_long",EffectInit.CHARGE,60,0);

    public static Potion registerPotion(String name, StatusEffect effect, int time, int level){
        return Registry.register(Registries.POTION,new Identifier(Fans.MOD,name),
                new net.minecraft.potion.Potion(new StatusEffectInstance(effect,time,level)));
    }
    public static void init(){
//        registerPotionRecipes();
    }
    public static void registerPotionRecipes(){
        invokeRegisterPotionRecipe(Potions.AWKWARD,ItemInit.RAVAGER_FOOT_SKIN,PotionInit.CHARGE);
        invokeRegisterPotionRecipe(PotionInit.CHARGE, Items.GLOWSTONE_DUST,PotionInit.CHARGE_STRONG);
        invokeRegisterPotionRecipe(PotionInit.CHARGE, Items.REDSTONE,PotionInit.CHARGE_LONG);
    }
}
