package com.hexagram2021.oceanworld.common;

import net.minecraft.util.Tuple;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.InterModComms;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Triple;

import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;

public class OWFoods {
	public static final FoodProperties SEA_CUCUMBER =
			new FoodProperties.Builder().nutrition(2).saturationMod(2.4F)
					.effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 1200, 1), 1.0F)
					.effect(() -> new MobEffectInstance(MobEffects.NIGHT_VISION, 1200, 0), 1.0F)
					.alwaysEat().build();
	public static final FoodProperties OYSTER_MEAT =
			new FoodProperties.Builder().nutrition(2).saturationMod(0.6F)
					.effect(() -> new MobEffectInstance(MobEffects.HUNGER, 600, 0), 0.1F).build();
	public static final FoodProperties COOKED_OYSTER_MEAT =
			new FoodProperties.Builder().nutrition(4).saturationMod(2.4F).build();

	public static void compatDiet(Item foodItem, FoodProperties food) {
		InterModComms.sendTo("diet", "item",
				() -> new Tuple<Item, BiFunction<Player, ItemStack, Triple<List<ItemStack>, Integer, Float>>>(
						foodItem,
						(player, stack) -> new ImmutableTriple<>(Collections.singletonList(stack), food.getNutrition(), food.getSaturationModifier())
				)
		);
	}
}
