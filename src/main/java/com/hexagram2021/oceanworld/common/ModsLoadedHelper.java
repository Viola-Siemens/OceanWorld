package com.hexagram2021.oceanworld.common;

import com.hexagram2021.oceanworld.common.items.CuriosItemFactory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraftforge.fml.ModList;

import java.util.function.Consumer;

public class ModsLoadedHelper {
	public static boolean CURIOS_API = false;

	public static void compatModLoaded() {
		ModList modList = ModList.get();
		if(modList.isLoaded("curios")) {
			CURIOS_API = true;
		}
	}

	public static Item makeCuriosItem(Consumer<LivingEntity> effect) {
		if(CURIOS_API) {
			return CuriosItemFactory.make(new Item.Properties().stacksTo(1), effect);
		}
		return new Item(new Item.Properties().stacksTo(1));
	}
}
