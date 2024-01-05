package com.hexagram2021.oceanworld.common.register;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

import static com.hexagram2021.oceanworld.OceanWorld.MODID;

@SuppressWarnings("unused")
public class OWBiomeTags {
	public static final TagKey<Biome> HAS_OCEAN_ALTAR = create("has_structure/ocean_altar");
	public static final TagKey<Biome> HAS_ILLUSIONER_SHELTER = create("has_structure/illusioner_shelter");
	public static final TagKey<Biome> HAS_PRISMARINE_CASTLE = create("has_structure/prismarine_castle");
	public static final TagKey<Biome> HAS_ARISTOCRATS_RESIDENCE = create("has_structure/aristocrats_residence");
	public static final TagKey<Biome> HAS_OCEANOLOGER_SHIP = create("has_structure/oceanologer_ship");

	public static void init() {
	}

	private static TagKey<Biome> create(String name) {
		return TagKey.create(Registries.BIOME, new ResourceLocation(MODID, name));
	}
}
