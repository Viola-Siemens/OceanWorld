package com.hexagram2021.oceanworld.common.world.placement_modifiers;

import com.hexagram2021.oceanworld.common.register.OWPlacementModifierTypes;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementFilter;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;

public class SeaLevelBiomeFilter extends PlacementFilter {
	private static final SeaLevelBiomeFilter INSTANCE = new SeaLevelBiomeFilter();
	public static Codec<SeaLevelBiomeFilter> CODEC = Codec.unit(() -> INSTANCE);

	private SeaLevelBiomeFilter() {
	}

	public static SeaLevelBiomeFilter biome() {
		return INSTANCE;
	}

	@SuppressWarnings("deprecation")
	@Override
	protected boolean shouldPlace(PlacementContext context, RandomSource random, BlockPos blockPos) {
		PlacedFeature placedfeature = context.topFeature().orElseThrow(() -> new IllegalStateException("Tried to biome check an unregistered feature, or a feature that should not restrict the biome"));
		Holder<Biome> holder = context.getLevel().getBiome(blockPos.atY(context.getLevel().getSeaLevel()));
		return context.generator().getBiomeGenerationSettings(holder).hasFeature(placedfeature);
	}

	@Override
	public PlacementModifierType<?> type() {
		return OWPlacementModifierTypes.SEA_LEVEL_BIOME_FILTER.get();
	}
}
