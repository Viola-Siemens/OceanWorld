package com.hexagram2021.oceanworld.common.register;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.Structure;

import static com.hexagram2021.oceanworld.OceanWorld.MODID;

@SuppressWarnings("unused")
public class OWStructureKeys {
	public static final ResourceKey<Structure> OCEAN_ALTAR = createKey("ocean_altar");
	public static final ResourceKey<Structure> ILLUSIONER_SHELTER = createKey("illusioner_shelter");
	public static final ResourceKey<Structure> PRISMARINE_CASTLE = createKey("prismarine_castle");
	public static final ResourceKey<Structure> ARISTOCRATS_RESIDENCE = createKey("aristocrats_residence");
	public static final ResourceKey<Structure> OCEANOLOGER_SHIP = createKey("oceanologer_ship");

	private static ResourceKey<Structure> createKey(String name) {
		return ResourceKey.create(Registries.STRUCTURE, new ResourceLocation(MODID, name));
	}

	public static void init() {

	}
}
