package com.fans.init;

import com.fans.Fans;
import com.fans.item.FlintAndSteelFoodItem;
import com.fans.item.FlintAndSteelItem;
import net.minecraft.block.TntBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterials;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ItemInit {
    public static Item RAVAGER_FOOT_SKIN = Registry.register(Registries.ITEM,new Identifier(Fans.MOD,"ravager_foot_skin"),new Item(new Item.Settings()));
    public static Item FLINT_AND_STEEL = Registry.register(Registries.ITEM,new Identifier(Fans.MOD,"flint_and_steel"),new FlintAndSteelItem());
    public static Item FLINT_AND_STEEL_FOOD = Registry.register(Registries.ITEM,new Identifier(Fans.MOD,"flint_and_steel_food"),new FlintAndSteelFoodItem());


    public static void init(){

    }
}
