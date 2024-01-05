package com.hexagram2021.oceanworld.common.register;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

import static com.hexagram2021.oceanworld.OceanWorld.MODID;

@SuppressWarnings("unused")
public class OWConfiguredFeatureKeys {
	public static final ResourceKey<ConfiguredFeature<?, ?>> PERIDOTITE_FEATURE = register("peridotite_feature");
	public static final ResourceKey<ConfiguredFeature<?, ?>> SERPENTINE_FEATURE = register("serpentine_feature");
	public static final ResourceKey<ConfiguredFeature<?, ?>> RED_CLAY_FEATURE = register("red_clay_feature");

	public static final ResourceKey<ConfiguredFeature<?, ?>> CONGLOMERATE_FEATURE = register("conglomerate_feature");

	public static void init() {
	}

	private static ResourceKey<ConfiguredFeature<?, ?>> register(String name) {
		return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(MODID, name));
	}
}
