package com.hexagram2021.oceanworld.common;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class OWFoods {
	public static final FoodProperties SEA_CUCUMBER =
			(new FoodProperties.Builder()).nutrition(2).saturationMod(2.4F)
					.effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 1200, 1), 1.0F)
					.effect(() -> new MobEffectInstance(MobEffects.NIGHT_VISION, 1200, 0), 1.0F)
					.alwaysEat().build();
}
