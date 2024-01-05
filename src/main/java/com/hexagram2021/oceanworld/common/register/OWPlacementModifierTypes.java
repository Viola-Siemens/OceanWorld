package com.hexagram2021.oceanworld.common.register;

import com.hexagram2021.oceanworld.common.world.placement_modifiers.SeaLevelBiomeFilter;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import static com.hexagram2021.oceanworld.OceanWorld.MODID;

public class OWPlacementModifierTypes {
	public static final DeferredRegister<PlacementModifierType<?>> REGISTER = DeferredRegister.create(Registries.PLACEMENT_MODIFIER_TYPE, MODID);

	public static final RegistryObject<PlacementModifierType<SeaLevelBiomeFilter>> SEA_LEVEL_BIOME_FILTER = REGISTER.register(
			"sea_level_biome", () -> () -> SeaLevelBiomeFilter.CODEC
	);

	public static void init(IEventBus bus) {
		REGISTER.register(bus);
	}
}
