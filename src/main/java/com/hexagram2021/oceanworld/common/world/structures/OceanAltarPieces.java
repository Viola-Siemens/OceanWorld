package com.hexagram2021.oceanworld.common.world.structures;

import com.hexagram2021.oceanworld.common.register.OWStructurePieceTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

import static com.hexagram2021.oceanworld.OceanWorld.MODID;

public class OceanAltarPieces {
	private static final ResourceLocation OCEAN_ALTAR = new ResourceLocation(MODID, "ocean_altar/ocean_altar");

	public static void addPieces(StructureTemplateManager structureManager, BlockPos pos, Rotation rotation, StructurePieceAccessor pieces) {
		pieces.addPiece(new OceanAltarPieces.OceanAltarPiece(structureManager, OCEAN_ALTAR, pos, rotation));
	}

	public static class OceanAltarPiece extends TemplateStructurePiece {
		public OceanAltarPiece(StructureTemplateManager structureManager, ResourceLocation location, BlockPos pos, Rotation rotation) {
			super(OWStructurePieceTypes.OCEAN_ALTAR_TYPE, 0, structureManager, location, location.toString(), makeSettings(rotation), pos);
		}

		public OceanAltarPiece(StructurePieceSerializationContext context, CompoundTag tag) {
			super(OWStructurePieceTypes.OCEAN_ALTAR_TYPE, tag, context.structureTemplateManager(), (location) -> makeSettings(Rotation.valueOf(tag.getString("Rot"))));
		}

		private static StructurePlaceSettings makeSettings(Rotation rotation) {
			return (new StructurePlaceSettings())
					.setRotation(rotation)
					.setMirror(Mirror.LEFT_RIGHT)
					.setRotationPivot(BlockPos.ZERO)
					.addProcessor(BlockIgnoreProcessor.STRUCTURE_BLOCK);
		}


		@Override
		protected void addAdditionalSaveData(StructurePieceSerializationContext context, CompoundTag tag) {
			super.addAdditionalSaveData(context, tag);
			tag.putString("Rot", this.placeSettings.getRotation().name());
		}

		@Override
		protected void handleDataMarker(String function, BlockPos pos, ServerLevelAccessor level, RandomSource random, BoundingBox sbb) { }

		@Override
		public void postProcess(WorldGenLevel level, StructureManager structureFeatureManager,
								ChunkGenerator chunkGenerator, RandomSource random,
								BoundingBox boundingBox, ChunkPos chunkPos, BlockPos blockPos) {
			BoundingBox curBoundingBox = this.getBoundingBox();
			super.postProcess(level, structureFeatureManager, chunkGenerator, random, boundingBox, chunkPos, blockPos);
			for(int e = 0; e < 256; ++e) {
				int erodeX = random.nextInt(curBoundingBox.getXSpan());
				int erodeY = random.nextInt(curBoundingBox.getYSpan());
				int erodeZ = random.nextInt(curBoundingBox.getZSpan());
				this.placeBlock(level, Blocks.WATER.defaultBlockState(), erodeX, erodeY, erodeZ, boundingBox);
			}
		}
	}
}
