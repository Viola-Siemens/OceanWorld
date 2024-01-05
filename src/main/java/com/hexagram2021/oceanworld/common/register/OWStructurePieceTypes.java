package com.hexagram2021.oceanworld.common.register;

import com.hexagram2021.oceanworld.common.world.structures.IllusionerShelterPieces;
import com.hexagram2021.oceanworld.common.world.structures.OceanAltarPieces;
import com.hexagram2021.oceanworld.common.world.structures.OceanologerShipPieces;
import com.hexagram2021.oceanworld.common.world.structures.PrismarineCastlePieces;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;

import static com.hexagram2021.oceanworld.OceanWorld.MODID;

public class OWStructurePieceTypes {
	public static final StructurePieceType OCEAN_ALTAR_TYPE = register("ocean_altar", OceanAltarPieces.OceanAltarPiece::new);
	public static final StructurePieceType ILLUSIONER_SHELTER_TYPE = register("illusioner_shelter", IllusionerShelterPieces.IllusionerShelterPiece::new);
	public static final StructurePieceType PRISMARINE_CASTLE_TYPE = register("prismarine_castle", PrismarineCastlePieces.PrismarineCastlePiece::new);
	public static final StructurePieceType OCEANOLOGER_SHIP_TYPE = register("oceanologer_ship", OceanologerShipPieces.OceanologerShipPiece::new);

	public static void init() {
	}

	private static StructurePieceType register(String name, StructurePieceType type) {
		return Registry.register(BuiltInRegistries.STRUCTURE_PIECE, new ResourceLocation(MODID, name), type);
	}
}
