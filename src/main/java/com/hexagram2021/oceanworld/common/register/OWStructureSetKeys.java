package com.hexagram2021.oceanworld.common.register;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.StructureSet;

import static com.hexagram2021.oceanworld.OceanWorld.MODID;

@SuppressWarnings("unused")
public class OWStructureSetKeys {
	public static final ResourceKey<StructureSet> OCEAN_ALTAR = createKey("ocean_altar");
	public static final ResourceKey<StructureSet> ILLUSIONER_SHELTER = createKey("illusioner_shelter");
	public static final ResourceKey<StructureSet> PRISMARINE_CASTLE = createKey("prismarine_castle");
	public static final ResourceKey<StructureSet> ARISTOCRATS_RESIDENCE = createKey("aristocrats_residence");
	public static final ResourceKey<StructureSet> OCEANOLOGER_SHIP = createKey("oceanologer_ship");

	private static ResourceKey<StructureSet> createKey(String name) {
		return ResourceKey.create(Registries.STRUCTURE_SET, new ResourceLocation(MODID, name));
	}

	public static void init() {

	}
}
