package com.hexagram2021.oceanworld.client.renderers;

import com.hexagram2021.oceanworld.common.entities.DripIceEntity;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import static com.hexagram2021.oceanworld.OceanWorld.MODID;

@OnlyIn(Dist.CLIENT)
public class DripIceRenderer extends ArrowRenderer<DripIceEntity> {
	public static final ResourceLocation TEXTURE = new ResourceLocation(MODID, "textures/entity/projectiles/drip_ice.png");

	public DripIceRenderer(EntityRendererProvider.Context context) {
		super(context);
	}

	@Override
	public ResourceLocation getTextureLocation(DripIceEntity entity) {
		return TEXTURE;
	}
}
