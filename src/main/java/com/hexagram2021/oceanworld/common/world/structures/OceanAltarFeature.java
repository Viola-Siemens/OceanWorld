package com.hexagram2021.oceanworld.common.world.structures;

import com.hexagram2021.oceanworld.common.register.OWStructureTypes;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;

import java.util.Optional;

public class OceanAltarFeature extends Structure {
	public static final Codec<OceanAltarFeature> CODEC = simpleCodec(OceanAltarFeature::new);

	public OceanAltarFeature(Structure.StructureSettings settings) {
		super(settings);
	}

	@Override
	public Optional<GenerationStub> findGenerationPoint(Structure.GenerationContext context) {
		return StructureUtils.onTopOfChunkCenterAndUnder(context, Heightmap.Types.OCEAN_FLOOR_WG, 56, builder -> this.generatePieces(builder, context));
	}

	private void generatePieces(StructurePiecesBuilder builder, Structure.GenerationContext context) {
		BlockPos centerOfChunk = new BlockPos(context.chunkPos().getMinBlockX() + 7, 0, context.chunkPos().getMinBlockZ() + 7);
		BlockPos blockpos = new BlockPos(
				context.chunkPos().getMinBlockX(),
				context.chunkGenerator().getBaseHeight(
						centerOfChunk.getX(), centerOfChunk.getZ(), Heightmap.Types.OCEAN_FLOOR_WG, context.heightAccessor(), context.randomState()
				),
				context.chunkPos().getMinBlockZ()
		);
		Rotation rotation = Rotation.getRandom(context.random());
		OceanAltarPieces.addPieces(context.structureTemplateManager(), blockpos, rotation, builder);
	}

	@Override
	public StructureType<?> type() {
		return OWStructureTypes.OCEAN_ALTAR_TYPE.get();
	}
}
