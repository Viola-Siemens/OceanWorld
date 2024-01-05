package com.hexagram2021.oceanworld.common.world.structures;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;

import java.util.Optional;
import java.util.function.Consumer;

public final class StructureUtils {
	public static Optional<Structure.GenerationStub> onTopOfChunkCenterAndUnder(Structure.GenerationContext context,
																				Heightmap.Types heightmapType,
																				int maxHeight,
																				Consumer<StructurePiecesBuilder> generate) {
		ChunkPos chunkpos = context.chunkPos();
		int x = chunkpos.getMiddleBlockX();
		int z = chunkpos.getMiddleBlockZ();
		int y = context.chunkGenerator().getFirstOccupiedHeight(x, z, heightmapType, context.heightAccessor(), context.randomState());
		if(y > maxHeight) {
			return Optional.empty();
		}
		return Optional.of(new Structure.GenerationStub(new BlockPos(x, y, z), generate));
	}

	private StructureUtils() {
	}
}
