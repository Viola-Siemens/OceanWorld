package com.hexagram2021.oceanworld.client;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

import static com.hexagram2021.oceanworld.OceanWorld.MODID;

public class OWModelLayers {
	public static final ModelLayerLocation NECKLACE = register("necklace");
	public static final ModelLayerLocation BRACELET = register("bracelet");


	private static ModelLayerLocation register(String name) {
		return register(name, "main");
	}

	@SuppressWarnings("SameParameterValue")
	private static ModelLayerLocation register(String name, String layer) {
		return new ModelLayerLocation(new ResourceLocation(MODID, name), layer);
	}
}
