package com.hexagram2021.oceanworld.common.items;

import com.hexagram2021.oceanworld.common.items.compat.CuriosItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;

import java.util.function.Consumer;

public final class CuriosItemFactory {
	public static Item make(Item.Properties props, Consumer<LivingEntity> effect) {
		return new CuriosItem(props) {
				@Override
				protected void equipmentTick(LivingEntity livingEntity) {
					effect.accept(livingEntity);
				}
		};
	}
}
