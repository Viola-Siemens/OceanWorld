package com.hexagram2021.oceanworld.client.renderers;

import com.hexagram2021.oceanworld.client.models.OysterModel;
import com.hexagram2021.oceanworld.common.entities.BlacklipOysterEntity;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import static com.hexagram2021.oceanworld.OceanWorld.MODID;

@OnlyIn(Dist.CLIENT)
public class BlacklipOysterRenderer extends MobRenderer<BlacklipOysterEntity, OysterModel<BlacklipOysterEntity>> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "blacklip_oyster"), "main");

	public static final ResourceLocation TEXTURE = new ResourceLocation(MODID, "textures/entity/oyster/blacklip_oyster.png");

	public BlacklipOysterRenderer(EntityRendererProvider.Context manager) {
		super(manager, new OysterModel<>(manager.bakeLayer(BlacklipOysterRenderer.LAYER_LOCATION)), 0.7F);
	}

	@Override
	public ResourceLocation getTextureLocation(BlacklipOysterEntity entity) { return TEXTURE; }
}
