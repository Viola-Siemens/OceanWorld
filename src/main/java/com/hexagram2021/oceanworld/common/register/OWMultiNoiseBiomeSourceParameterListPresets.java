package com.hexagram2021.oceanworld.common.register;

import com.google.common.collect.ImmutableList;
import com.hexagram2021.oceanworld.common.world.OceanWorldBiomeBuilder;
import com.mojang.datafixers.util.Pair;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.MultiNoiseBiomeSourceParameterList;

import java.util.function.Function;

import static com.hexagram2021.oceanworld.OceanWorld.MODID;

public final class OWMultiNoiseBiomeSourceParameterListPresets {
	public static final MultiNoiseBiomeSourceParameterList.Preset OCEANWORLD = new MultiNoiseBiomeSourceParameterList.Preset(
			new ResourceLocation(MODID, "oceanworld"),
			OWMultiNoiseBiomeSourceParameterListPresets::generateBiomes
	);

	private static <T> Climate.ParameterList<T> generateBiomes(Function<ResourceKey<Biome>, T> biomeRegistry) {
		ImmutableList.Builder<Pair<Climate.ParameterPoint, T>> builder = ImmutableList.builder();
		OceanWorldBiomeBuilder oceanWorldBiomeBuilder = new OceanWorldBiomeBuilder();
		oceanWorldBiomeBuilder.addBiomes(pair -> builder.add(pair.mapSecond(biomeRegistry)));
		return new Climate.ParameterList<>(builder.build());
	}

	private OWMultiNoiseBiomeSourceParameterListPresets() {
	}

	public static void init() {
		MultiNoiseBiomeSourceParameterList.Preset.BY_NAME.put(OCEANWORLD.id(), OCEANWORLD);
	}
}
