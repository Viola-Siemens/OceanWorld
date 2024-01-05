package com.hexagram2021.oceanworld.common.world.structures;

import com.hexagram2021.oceanworld.common.register.OWStructurePieceTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

import static com.hexagram2021.oceanworld.OceanWorld.MODID;

public class IllusionerShelterPieces {
	private static final ResourceLocation ILLUSIONER_SHELTER = new ResourceLocation(MODID, "illusioner_shelter/illusioner_shelter");

	public static void addPieces(StructureTemplateManager structureManager, BlockPos pos, Rotation rotation, StructurePieceAccessor pieces) {
		pieces.addPiece(new IllusionerShelterPieces.IllusionerShelterPiece(structureManager, ILLUSIONER_SHELTER, pos, rotation));
	}

	public static class IllusionerShelterPiece extends TemplateStructurePiece {
		public IllusionerShelterPiece(StructureTemplateManager structureManager, ResourceLocation location, BlockPos pos, Rotation rotation) {
			super(OWStructurePieceTypes.ILLUSIONER_SHELTER_TYPE, 0, structureManager, location, location.toString(), makeSettings(rotation), pos);
		}

		public IllusionerShelterPiece(StructurePieceSerializationContext context, CompoundTag tag) {
			super(OWStructurePieceTypes.ILLUSIONER_SHELTER_TYPE, tag, context.structureTemplateManager(), (location) -> makeSettings(Rotation.valueOf(tag.getString("Rot"))));
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
	}
}
