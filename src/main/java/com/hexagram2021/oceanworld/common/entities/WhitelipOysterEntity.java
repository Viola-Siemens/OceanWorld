package com.hexagram2021.oceanworld.common.entities;

import com.hexagram2021.oceanworld.common.register.OWItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class WhitelipOysterEntity extends OysterEntity {
	public WhitelipOysterEntity(EntityType<? extends Animal> type, Level level) {
		super(type, level);
	}

	@Override
	protected Item dropPearlItem() {
		return this.random.nextInt(256) == 0 ? OWItems.GOLDEN_PEARL.get() : OWItems.WHITE_PEARL.get();
	}

	@Override
	public ItemStack getBucketItemStack() {
		return new ItemStack(OWItems.WHITELIP_OYSTER_BUCKET.get());
	}
}
