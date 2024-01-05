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
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

import static com.hexagram2021.oceanworld.OceanWorld.MODID;

public class PrismarineCastlePieces {
	private static final ResourceLocation PRISMARINE_CASTLE = new ResourceLocation(MODID, "prismarine_castle/prismarine_castle");

	public static void addPieces(StructureTemplateManager structureManager, BlockPos pos, Rotation rotation, StructurePieceAccessor pieces) {
		pieces.addPiece(new PrismarineCastlePieces.PrismarineCastlePiece(structureManager, PRISMARINE_CASTLE, pos, rotation));
	}

	public static class PrismarineCastlePiece extends TemplateStructurePiece {
		public PrismarineCastlePiece(StructureTemplateManager structureManager, ResourceLocation location, BlockPos pos, Rotation rotation) {
			super(OWStructurePieceTypes.PRISMARINE_CASTLE_TYPE, 0, structureManager, location, location.toString(), makeSettings(rotation), pos);
		}

		public PrismarineCastlePiece(StructurePieceSerializationContext context, CompoundTag tag) {
			super(OWStructurePieceTypes.PRISMARINE_CASTLE_TYPE, tag, context.structureTemplateManager(), (location) -> makeSettings(Rotation.valueOf(tag.getString("Rot"))));
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
			for(int e = 0; e < 64; ++e) {
				int treasureX = random.nextInt(curBoundingBox.getXSpan());
				int treasureY = random.nextInt(curBoundingBox.getYSpan());
				int treasureZ = random.nextInt(curBoundingBox.getZSpan());
				BlockState blockState = this.getBlock(level, treasureX, treasureY, treasureZ, boundingBox);
				if(blockState.is(Blocks.DARK_PRISMARINE)) {
					boolean placeGold = true;
					for(int b = 1; b <= 6; ++b) {
						int s = ((b & 4) >> 2);
						int b1 = (b & 3) + s;
						int y = (b1 & 1) ^ ((b1 & 2) >> 1) ^ 1;
						int x = (b1 & 1) ^ y;
						int z = ((b1 & 2) >> 1) ^ y;

						x *= 1 - 2 * s;
						y *= 1 - 2 * s;
						z *= 1 - 2 * s;

						BlockState nextBlockState = this.getBlock(level, treasureX + x, treasureY + y, treasureZ + z, boundingBox);

						if(nextBlockState.is(Blocks.AIR) || nextBlockState.is(Blocks.WATER)) {
							placeGold = false;
							break;
						}
					}
					if(placeGold) {
						this.placeBlock(level, Blocks.GOLD_BLOCK.defaultBlockState(), treasureX, treasureY, treasureZ, boundingBox);
					}
				}
			}
		}
	}
}
