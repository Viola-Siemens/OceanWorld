package com.hexagram2021.oceanworld.common.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FrostedIceBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class FakeFrostedIceBlock extends FrostedIceBlock {
	public FakeFrostedIceBlock(Properties properties) {
		super(properties);
	}

	@Override
	public void playerDestroy(Level level, Player player, BlockPos blockPos, BlockState blockState,
							  @Nullable BlockEntity blockEntity, ItemStack handItemStack) {
		player.awardStat(Stats.BLOCK_MINED.get(this));
		player.causeFoodExhaustion(0.005F);
		dropResources(blockState, level, blockPos, blockEntity, player, handItemStack);

		this.clearNeighborWater(level, blockPos);
	}

	@Override
	protected void melt(BlockState blockState, Level level, BlockPos pos) {
		level.removeBlock(pos, false);

		this.clearNeighborWater(level, pos);
	}

	private void clearNeighborWater(Level level, BlockPos pos) {
		for(int b = 1; b <= 6; ++b) {
			int s = ((b & 4) >> 2);
			int b1 = (b & 3) + s;
			int y = (b1 & 1) ^ ((b1 & 2) >> 1) ^ 1;
			int x = (b1 & 1) ^ y;
			int z = ((b1 & 2) >> 1) ^ y;

			x *= 1 - 2 * s;
			y *= 1 - 2 * s;
			z *= 1 - 2 * s;

			BlockPos newPos = pos.offset(x, y, z);
			BlockState state = level.getBlockState(newPos);
			if(state.is(Blocks.WATER) || state.is(Blocks.BUBBLE_COLUMN)) {
				level.setBlock(newPos, Blocks.AIR.defaultBlockState(), UPDATE_ALL);
			}
		}
	}
}
