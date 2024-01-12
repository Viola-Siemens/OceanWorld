package com.hexagram2021.oceanworld.common.world.processors;

import com.hexagram2021.oceanworld.common.register.OWBlocks;
import com.hexagram2021.oceanworld.common.register.OWProcessorType;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

import javax.annotation.Nullable;

public class AristocratsResidenceProcessor extends StructureProcessor {
	public static final Codec<AristocratsResidenceProcessor> CODEC = RecordCodecBuilder.create((instance) ->
			instance.group(
					Codec.DOUBLE.fieldOf("replace_possibility").orElse(0.2D).forGetter((processor) -> processor.replacePossibility),
					Codec.DOUBLE.fieldOf("treasure_possibility").orElse(0.15D).forGetter((processor) -> processor.treasurePossibility)
			).apply(instance, AristocratsResidenceProcessor::new)
	);

	private final double replacePossibility;
	private final double treasurePossibility;
	public AristocratsResidenceProcessor(double replacePossibility, double treasurePossibility) {
		this.replacePossibility = replacePossibility;
		this.treasurePossibility = treasurePossibility;
	}

	@Override @Nullable
	public StructureTemplate.StructureBlockInfo process(LevelReader level, BlockPos blockPos, BlockPos placedPos,
														StructureTemplate.StructureBlockInfo worldInfo,
														StructureTemplate.StructureBlockInfo structureInfo,
														StructurePlaceSettings settings,
														@Nullable StructureTemplate template) {
		BlockPos pos = structureInfo.pos();
		RandomSource random = settings.getRandom(pos);
		BlockState blockstate = structureInfo.state();
		if(blockstate.is(Blocks.DIAMOND_BLOCK)) {
			if(random.nextDouble() > treasurePossibility) {
				return new StructureTemplate.StructureBlockInfo(pos, OWBlocks.BrickDecoration.RED_CLAY.defaultBlockState(), null);
			}
		} else if(random.nextDouble() < replacePossibility) {
			if (blockstate.is(OWBlocks.BrickDecoration.RED_CLAY.get())) {
				if (random.nextInt(4) == 2) {
					return new StructureTemplate.StructureBlockInfo(pos, Blocks.WATER.defaultBlockState(), null);
				}
			} else if (blockstate.is(Blocks.WAXED_EXPOSED_COPPER)) {
				return switch (random.nextInt(5)) {
					case 0 -> new StructureTemplate.StructureBlockInfo(pos, Blocks.WATER.defaultBlockState(), null);
					case 1 -> new StructureTemplate.StructureBlockInfo(pos, Blocks.WAXED_OXIDIZED_COPPER.defaultBlockState(), null);
					default -> new StructureTemplate.StructureBlockInfo(pos, Blocks.WAXED_WEATHERED_COPPER.defaultBlockState(), null);
				};
			} else if (blockstate.is(Blocks.WAXED_EXPOSED_CUT_COPPER)) {
				return switch (random.nextInt(5)) {
					case 0 -> new StructureTemplate.StructureBlockInfo(pos, Blocks.WATER.defaultBlockState(), null);
					case 1 -> new StructureTemplate.StructureBlockInfo(pos, Blocks.WAXED_OXIDIZED_CUT_COPPER.defaultBlockState(), null);
					default -> new StructureTemplate.StructureBlockInfo(pos, Blocks.WAXED_WEATHERED_CUT_COPPER.defaultBlockState(), null);
				};
			} else if (blockstate.is(Blocks.WAXED_EXPOSED_CUT_COPPER_SLAB)) {
				return switch (random.nextInt(5)) {
					case 0 -> new StructureTemplate.StructureBlockInfo(pos, Blocks.WATER.defaultBlockState(), null);
					case 1 -> new StructureTemplate.StructureBlockInfo(pos, Blocks.WAXED_OXIDIZED_CUT_COPPER_SLAB.defaultBlockState()
							.setValue(SlabBlock.TYPE, blockstate.getValue(SlabBlock.TYPE))
							.setValue(SlabBlock.WATERLOGGED, Boolean.TRUE), null);
					default -> new StructureTemplate.StructureBlockInfo(pos, Blocks.WAXED_WEATHERED_CUT_COPPER_SLAB.defaultBlockState()
							.setValue(SlabBlock.TYPE, blockstate.getValue(SlabBlock.TYPE))
							.setValue(SlabBlock.WATERLOGGED, Boolean.TRUE), null);
				};
			} else if (blockstate.is(Blocks.WAXED_EXPOSED_CUT_COPPER_STAIRS)) {
				return switch (random.nextInt(5)) {
					case 0 -> new StructureTemplate.StructureBlockInfo(pos, Blocks.WATER.defaultBlockState(), null);
					case 1 -> new StructureTemplate.StructureBlockInfo(pos, Blocks.WAXED_OXIDIZED_CUT_COPPER_STAIRS.defaultBlockState()
							.setValue(StairBlock.FACING, blockstate.getValue(StairBlock.FACING))
							.setValue(StairBlock.HALF, blockstate.getValue(StairBlock.HALF))
							.setValue(StairBlock.SHAPE, blockstate.getValue(StairBlock.SHAPE))
							.setValue(StairBlock.WATERLOGGED, Boolean.TRUE), null);
					default -> new StructureTemplate.StructureBlockInfo(pos, Blocks.WAXED_WEATHERED_CUT_COPPER_STAIRS.defaultBlockState()
							.setValue(StairBlock.FACING, blockstate.getValue(StairBlock.FACING))
							.setValue(StairBlock.HALF, blockstate.getValue(StairBlock.HALF))
							.setValue(StairBlock.SHAPE, blockstate.getValue(StairBlock.SHAPE))
							.setValue(StairBlock.WATERLOGGED, Boolean.TRUE), null);
				};
			} else {
				return new StructureTemplate.StructureBlockInfo(pos, Blocks.WATER.defaultBlockState(), null);
			}
		}

		return structureInfo;
	}

	@Override
	protected StructureProcessorType<?> getType() {
		return OWProcessorType.ARISTOCRATS_RESIDENCE;
	}
}
