package com.hexagram2021.oceanworld.common.register;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import static com.hexagram2021.oceanworld.OceanWorld.MODID;

@SuppressWarnings("unused")
public class OWPlacedFeatureKeys {
	public static final ResourceKey<PlacedFeature> PERIDOTITE_FEATURE = register("peridotite_feature");
	public static final ResourceKey<PlacedFeature> SERPENTINE_FEATURE = register("serpentine_feature");
	public static final ResourceKey<PlacedFeature> RED_CLAY_FEATURE = register("red_clay_feature");

	public static final ResourceKey<PlacedFeature> CONGLOMERATE_FEATURE = register("conglomerate_feature");

	public static void init() {

	}

	private static ResourceKey<PlacedFeature> register(String name) {
		return ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(MODID, name));
	}
}
