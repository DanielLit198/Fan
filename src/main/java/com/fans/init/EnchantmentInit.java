package com.fans.init;

import com.fans.Fans;
import com.fans.enchantment.Burn;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class EnchantmentInit {
    public static Enchantment BURN = Registry.register(Registries.ENCHANTMENT, new Identifier(Fans.MOD,"burn"),
            new Burn(Enchantment.Rarity.COMMON, EnchantmentTarget.VANISHABLE, EquipmentSlot.MAINHAND));

    public static void init(){

    }
}
