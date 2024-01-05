package com.hexagram2021.oceanworld.common.register;

import com.hexagram2021.oceanworld.common.world.features.OceanStoneFeature;
import com.hexagram2021.oceanworld.common.world.features.configuration.OceanStoneConfiguration;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.RegisterEvent;

import static com.hexagram2021.oceanworld.OceanWorld.MODID;

public class OWFeatures {
	public static final OceanStoneFeature OCEAN_STONE_FEATURE = new OceanStoneFeature(OceanStoneConfiguration.CODEC);

	public static void init(RegisterEvent event) {
		event.register(Registries.FEATURE, helper -> helper.register(new ResourceLocation(MODID, "ocean_stone_feature"), OCEAN_STONE_FEATURE));
	}
}
