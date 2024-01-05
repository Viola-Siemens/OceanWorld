package com.hexagram2021.oceanworld.common.register;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

import static com.hexagram2021.oceanworld.OceanWorld.MODID;

@SuppressWarnings("unused")
public class OWBlockTags {
	public static final TagKey<Block> INFINIBURN_OCEANWORLD = create("infiniburn_oceanworld");

	public static void init() {
	}

	@SuppressWarnings("SameParameterValue")
	private static TagKey<Block> create(String name) {
		return TagKey.create(Registries.BLOCK, new ResourceLocation(MODID, name));
	}
}
