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

public class OceanologerShipFeature extends Structure {
	public static final Codec<OceanologerShipFeature> CODEC = simpleCodec(OceanologerShipFeature::new);

	public OceanologerShipFeature(Structure.StructureSettings settings) {
		super(settings);
	}

	@Override
	public Optional<GenerationStub> findGenerationPoint(Structure.GenerationContext context) {
		return StructureUtils.onTopOfChunkCenterAndUnder(context, Heightmap.Types.OCEAN_FLOOR_WG, 48, builder -> this.generatePieces(builder, context));
	}

	private void generatePieces(StructurePiecesBuilder builder, Structure.GenerationContext context) {
		BlockPos blockpos = new BlockPos(context.chunkPos().getMinBlockX(), 61, context.chunkPos().getMinBlockZ());
		Rotation rotation = Rotation.getRandom(context.random());
		OceanologerShipPieces.addPieces(context.structureTemplateManager(), blockpos, rotation, builder);
	}

	@Override
	public StructureType<?> type() {
		return OWStructureTypes.OCEANOLOGER_SHIP_TYPE.get();
	}
}
