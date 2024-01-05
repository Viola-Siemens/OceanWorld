package com.hexagram2021.oceanworld.common.register;

import com.google.common.collect.ImmutableList;
import com.hexagram2021.oceanworld.common.world.OceanWorldBiomeBuilder;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.MultiNoiseBiomeSource;

import static com.hexagram2021.oceanworld.OceanWorld.MODID;

public final class OWMultiNoiseBiomeSourcePresets {
	public static final MultiNoiseBiomeSource.Preset OCEANWORLD = new MultiNoiseBiomeSource.Preset(
			new ResourceLocation(MODID, "oceanworld"),
			biomes -> {
				ImmutableList.Builder<Pair<Climate.ParameterPoint, Holder<Biome>>> builder = ImmutableList.builder();
				OceanWorldBiomeBuilder oceanWorldBiomeBuilder = new OceanWorldBiomeBuilder();
				oceanWorldBiomeBuilder.addBiomes(pair -> builder.add(pair.mapSecond(biomes::getOrThrow)));
				return new Climate.ParameterList<>(builder.build());
			}
	);

	private OWMultiNoiseBiomeSourcePresets() {
	}

	public static void init() {
	}
}
