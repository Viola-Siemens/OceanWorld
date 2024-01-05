package com.hexagram2021.oceanworld.client.renderers;

import com.hexagram2021.oceanworld.client.models.SeaCucumberModel;
import com.hexagram2021.oceanworld.common.entities.SeaCucumberEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import static com.hexagram2021.oceanworld.OceanWorld.MODID;

@OnlyIn(Dist.CLIENT)
public class SeaCucumberRenderer extends MobRenderer<SeaCucumberEntity, SeaCucumberModel<SeaCucumberEntity>> {
	public static final ResourceLocation TEXTURE = new ResourceLocation(MODID, "textures/entity/sea_cucumber/sea_cucumber.png");

	public SeaCucumberRenderer(EntityRendererProvider.Context manager) {
		super(manager, new SeaCucumberModel<>(manager.bakeLayer(SeaCucumberModel.LAYER_LOCATION)), 0.7F);
	}

	@Override
	public ResourceLocation getTextureLocation(SeaCucumberEntity entity) { return TEXTURE; }
}
