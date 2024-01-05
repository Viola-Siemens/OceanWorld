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

public class OceanologerShipPieces {
	private static final double bannerReplacePossibility = 0.075D;
	private static final double treasurePossibility = 0.6D;

	private static final ResourceLocation OCEANOLOGER_SHIP = new ResourceLocation(MODID, "oceanologer_ship/oceanologer_ship");

	public static void addPieces(StructureTemplateManager structureManager, BlockPos pos, Rotation rotation, StructurePieceAccessor pieces) {
		pieces.addPiece(new OceanologerShipPieces.OceanologerShipPiece(structureManager, OCEANOLOGER_SHIP, pos, rotation));
	}

	public static class OceanologerShipPiece extends TemplateStructurePiece {
		public OceanologerShipPiece(StructureTemplateManager structureManager, ResourceLocation location, BlockPos pos, Rotation rotation) {
			super(OWStructurePieceTypes.OCEANOLOGER_SHIP_TYPE, 0, structureManager, location, location.toString(), makeSettings(rotation), pos);
		}

		public OceanologerShipPiece(StructurePieceSerializationContext context, CompoundTag tag) {
			super(OWStructurePieceTypes.OCEANOLOGER_SHIP_TYPE, tag, context.structureTemplateManager(), (location) -> makeSettings(Rotation.valueOf(tag.getString("Rot"))));
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
			for(int x = 0; x < curBoundingBox.getXSpan(); ++x) {
				for(int z = 0; z < curBoundingBox.getZSpan(); ++z) {
					for(int y = 0; y < curBoundingBox.getYSpan(); ++y) {
						BlockState blockstate = this.getBlock(level, x, y, z, boundingBox);
						if(blockstate.is(Blocks.DIAMOND_BLOCK) ||
								blockstate.is(Blocks.GOLD_BLOCK) ||
								blockstate.is(Blocks.EMERALD_BLOCK) ||
								blockstate.is(Blocks.WET_SPONGE)) {
							if(random.nextDouble() > treasurePossibility) {
								this.placeBlock(level, Blocks.DARK_OAK_PLANKS.defaultBlockState(), x, y, z, boundingBox);
							}
						} else if(blockstate.is(Blocks.BLUE_WALL_BANNER)) {
							if(random.nextDouble() < bannerReplacePossibility) {
								this.placeBlock(level, Blocks.AIR.defaultBlockState(), x, y, z, boundingBox);
							}
						}
					}
				}
			}
		}
	}
}
