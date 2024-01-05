package com.hexagram2021.oceanworld.common.world.features.configuration;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public record OceanStoneConfiguration(HolderSet<Block> replaceBlock, Block targetBlock, int depth, int invPossibility) implements FeatureConfiguration {
	@SuppressWarnings("deprecation")
	public static final Codec<OceanStoneConfiguration> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			RegistryCodecs.homogeneousList(Registries.BLOCK).fieldOf("target_block").forGetter(conf -> conf.replaceBlock),
			BuiltInRegistries.BLOCK.byNameCodec().fieldOf("replace_block").forGetter(conf -> conf.targetBlock),
			Codec.intRange(1, 16).fieldOf("depth").orElse(10).forGetter(conf -> conf.depth),
			Codec.intRange(1, 16).fieldOf("inv_possibility").orElse(3).forGetter(conf -> conf.invPossibility)
	).apply(instance, OceanStoneConfiguration::new));
}
