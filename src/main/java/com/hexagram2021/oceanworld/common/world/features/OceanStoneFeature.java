package com.hexagram2021.oceanworld.common.world.features;

import com.hexagram2021.oceanworld.common.world.features.configuration.OceanStoneConfiguration;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderSet;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

public class OceanStoneFeature extends Feature<OceanStoneConfiguration> {

	public OceanStoneFeature(Codec<OceanStoneConfiguration> codec) {
		super(codec);
	}

	private boolean tryPlace(HolderSet<Block> replaceBlock, BlockState targetBlock, int maxDepth, int invPossibility,
							 WorldGenLevel level, Set<BlockPos> visited, BlockPos curPosition, RandomSource random,
							 Predicate<BlockState> predicate, int depth, boolean toPlace) {
		for(int b = 1; b <= 6; ++b) {
			int s = ((b & 4) >> 2);
			int b1 = (b & 3) + s;
			int y = (b1 & 1) ^ ((b1 & 2) >> 1) ^ 1;
			int x = (b1 & 1) ^ y;
			int z = ((b1 & 2) >> 1) ^ y;

			x *= 1 - 2 * s;
			y *= 1 - 2 * s;
			z *= 1 - 2 * s;

			BlockPos nextPos = curPosition.offset(x, y, z);
			BlockState blockstate = level.getBlockState(nextPos);
			if(blockstate.is(Blocks.WATER) || blockstate.is(Blocks.AIR) || blockstate.is(Blocks.CAVE_AIR)) {
				return depth > 0;
			}
			if(depth >= maxDepth) {
				return false;
			}
			if(visited.contains(nextPos)) {
				continue;
			}
			visited.add(nextPos);
			if(random.nextInt(invPossibility) == 0) {
				continue;
			}
			if(blockstate.is(replaceBlock) || blockstate.is(Blocks.STONE) || blockstate.is(Blocks.ANDESITE) || blockstate.is(Blocks.DIORITE) || blockstate.is(Blocks.GRANITE)) {
				if(tryPlace(replaceBlock, targetBlock, maxDepth, invPossibility,
						level, visited, nextPos, random,
						predicate, depth + 1, toPlace)) {
					toPlace = true;
				}
				if(toPlace) {
					this.safeSetBlock(level, nextPos, targetBlock, predicate);
				}
			}
		}
		return toPlace;
	}

	@Override
	public boolean place(FeaturePlaceContext<OceanStoneConfiguration> context) {
		Predicate<BlockState> predicate = Feature.isReplaceable(BlockTags.FEATURES_CANNOT_REPLACE);
		BlockPos blockpos = context.origin();
		RandomSource random = context.random();
		WorldGenLevel worldgenlevel = context.level();
		OceanStoneConfiguration configuration = context.config();

		final Set<BlockPos> visited = new HashSet<>();
		return tryPlace(
				configuration.replaceBlock(), configuration.targetBlock().defaultBlockState(), configuration.depth(), configuration.invPossibility(),
				worldgenlevel, visited, blockpos, random, predicate, 0, false
		);
	}
}
