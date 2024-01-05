package com.hexagram2021.oceanworld.client.renderers;

import com.hexagram2021.oceanworld.common.entities.OceanologerEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.IllagerModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.IllagerRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import static com.hexagram2021.oceanworld.OceanWorld.MODID;

@OnlyIn(Dist.CLIENT)
public class OceanologerRenderer extends IllagerRenderer<OceanologerEntity> {
	public static final ResourceLocation TEXTURE = new ResourceLocation(MODID, "textures/entity/illager/oceanologer.png");

	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(MODID, "oceanologer"), "main");

	public OceanologerRenderer(EntityRendererProvider.Context context) {
		super(context, new IllagerModel<>(context.bakeLayer(LAYER_LOCATION)), 0.5F);
		this.addLayer(new ItemInHandLayer<>(this, context.getItemInHandRenderer()) {
			public void render(PoseStack transform, MultiBufferSource source,
							   int offset, OceanologerEntity entity,
							   float x1, float y1, float z1, float x2, float y2, float z2) {
				if (entity.isCastingSpell() || entity.isAggressive()) {
					super.render(transform, source, offset, entity, x1, y1, z1, x2, y2, z2);
				}

			}
		});
	}

	@Override
	public ResourceLocation getTextureLocation(OceanologerEntity entity) {
		return TEXTURE;
	}
}
