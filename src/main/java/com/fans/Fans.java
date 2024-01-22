package com.fans;

import com.fans.init.*;
import net.fabricmc.api.ModInitializer;

import net.minecraft.enchantment.Enchantment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Fans implements ModInitializer {
	public static String MOD = "fans";

	@Override
	public void onInitialize() {
		ItemInit.init();
		EntityInit.init();
		EnchantmentInit.init();
		EffectInit.init();
		PotionInit.init();
		ItemGroupInit.init();
	}
}