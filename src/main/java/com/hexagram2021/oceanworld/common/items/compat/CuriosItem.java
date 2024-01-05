package com.hexagram2021.oceanworld.common.items.compat;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public abstract class CuriosItem extends Item implements ICurioItem {
	public CuriosItem(Properties properties) {
		super(properties);
	}

	@Override
	public void curioTick(SlotContext slotContext, ItemStack stack) {
		this.equipmentTick(slotContext.entity());
	}

	protected abstract void equipmentTick(LivingEntity livingEntity);
}
