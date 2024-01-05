package com.hexagram2021.oceanworld.common.register;

import com.hexagram2021.oceanworld.common.world.structures.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import static com.hexagram2021.oceanworld.OceanWorld.MODID;

public class OWStructureTypes {
	private static final DeferredRegister<StructureType<?>> REGISTER = DeferredRegister.create(Registries.STRUCTURE_TYPE, MODID);
	public static final RegistryObject<StructureType<OceanAltarFeature>> OCEAN_ALTAR_TYPE = register("ocean_altar", () -> OceanAltarFeature.CODEC);
	public static final RegistryObject<StructureType<IllusionerShelterFeature>> ILLUSIONER_SHELTER_TYPE = register("illusioner_shelter", () -> IllusionerShelterFeature.CODEC);
	public static final RegistryObject<StructureType<PrismarineCastleFeature>> PRISMARINE_CASTLE_TYPE = register("prismarine_castle", () -> PrismarineCastleFeature.CODEC);
	public static final RegistryObject<StructureType<UnderwaterJigsawStructureFeature>> UNDERWATER_JIGSAW = register("underwater_jigsaw", () -> UnderwaterJigsawStructureFeature.CODEC);
	public static final RegistryObject<StructureType<OceanologerShipFeature>> OCEANOLOGER_SHIP_TYPE = register("oceanologer_ship", () -> OceanologerShipFeature.CODEC);


	private static <T extends Structure> RegistryObject<StructureType<T>> register(String name, StructureType<T> codec) {
		return REGISTER.register(name, () -> codec);
	}

	public static void init(IEventBus bus) {
		REGISTER.register(bus);
	}
}
