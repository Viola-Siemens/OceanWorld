package com.hexagram2021.oceanworld.common.register;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

import static com.hexagram2021.oceanworld.OceanWorld.MODID;

public class OWDimensionKeys {
	public static final ResourceKey<Level> OCEANWORLD = ResourceKey.create(Registries.DIMENSION, new ResourceLocation(MODID, "oceanworld"));
}
