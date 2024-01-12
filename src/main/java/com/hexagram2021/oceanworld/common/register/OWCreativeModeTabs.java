package com.hexagram2021.oceanworld.common.register;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

import static com.hexagram2021.oceanworld.OceanWorld.MODID;

public class OWCreativeModeTabs {
	public static final DeferredRegister<CreativeModeTab> REGISTER = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);
	public static final RegistryObject<CreativeModeTab> OCEANWORLD = register(
			"oceanworld",
			Component.translatable("itemGroup.oceanworld"),
			() -> new ItemStack(OWBlocks.TO_STAIRS.get(new ResourceLocation(MODID, "polished_serpentine"))),
			(flags, output) -> OWItems.ItemEntry.ALL_ITEMS.forEach(output::accept)
	);

	@SuppressWarnings("SameParameterValue")
	private static RegistryObject<CreativeModeTab> register(String name, Component title, Supplier<ItemStack> icon, CreativeModeTab.DisplayItemsGenerator generator) {
		return REGISTER.register(name, () -> CreativeModeTab.builder().withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
				.title(title).icon(icon).displayItems(generator).build());
	}

	public static void init(IEventBus bus) {
		REGISTER.register(bus);
	}
}
