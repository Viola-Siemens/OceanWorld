package com.hexagram2021.oceanworld.client.renderers;

import com.hexagram2021.oceanworld.client.models.OysterModel;
import com.hexagram2021.oceanworld.common.entities.WhitelipOysterEntity;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import static com.hexagram2021.oceanworld.OceanWorld.MODID;

@OnlyIn(Dist.CLIENT)
public class WhitelipOysterRenderer extends MobRenderer<WhitelipOysterEntity, OysterModel<WhitelipOysterEntity>> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "whitelip_oyster"), "main");
	
	public static final ResourceLocation TEXTURE = new ResourceLocation(MODID, "textures/entity/oyster/whitelip_oyster.png");

	public WhitelipOysterRenderer(EntityRendererProvider.Context manager) {
		super(manager, new OysterModel<>(manager.bakeLayer(WhitelipOysterRenderer.LAYER_LOCATION)), 0.7F);
	}

	@Override
	public ResourceLocation getTextureLocation(WhitelipOysterEntity entity) { return TEXTURE; }
}
