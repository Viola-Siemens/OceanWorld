package com.hexagram2021.oceanworld.client.renderers.curios;

import com.hexagram2021.oceanworld.client.OWModelLayers;
import com.hexagram2021.oceanworld.common.items.compat.CuriosItem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

import java.util.Objects;

import static com.hexagram2021.oceanworld.OceanWorld.MODID;

public class NecklaceRenderer implements ICurioRenderer {
	private final HumanoidModel<LivingEntity> model;

	public NecklaceRenderer() {
		this.model = new HumanoidModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(OWModelLayers.NECKLACE));
	}

	@Override
	public <T extends LivingEntity, M extends EntityModel<T>> void render(ItemStack stack, SlotContext slotContext, PoseStack matrixStack, RenderLayerParent<T, M> renderLayerParent,
																		  MultiBufferSource renderTypeBuffer, int light, float limbSwing, float limbSwingAmount,
																		  float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
		if(stack.getItem() instanceof CuriosItem curiosItem) {
			ICurioRenderer.followBodyRotations(slotContext.entity(), this.model);
			VertexConsumer vertexConsumer = renderTypeBuffer.getBuffer(RenderType.entityCutout(new ResourceLocation(
					MODID, "textures/model/curios/" + Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(curiosItem)).getPath() + ".png"
			)));
			this.model.renderToBuffer(matrixStack, vertexConsumer, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
		}
	}
}
