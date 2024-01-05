package com.hexagram2021.oceanworld.client.models;

import com.hexagram2021.oceanworld.common.entities.SeaCucumberEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import static com.hexagram2021.oceanworld.OceanWorld.MODID;

@OnlyIn(Dist.CLIENT)
public class SeaCucumberModel<T extends SeaCucumberEntity> extends EntityModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(MODID, "sea_cucumber"), "main");

	private final ModelPart bodyPart;

	public SeaCucumberModel(ModelPart root) {
		this.bodyPart = root.getChild("bodyPart");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition bodyPart = partdefinition.addOrReplaceChild("bodyPart", CubeListBuilder.create(), PartPose.offset(0.0F, 20.0F, 1.0F));

		bodyPart.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 19).addBox(-1.5F, -3.0F, -2.5F, 3.0F, 3.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-1.25F, -3.75F, -1.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 0).addBox(0.25F, -3.75F, -1.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 0).addBox(-1.25F, -3.75F, 1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 0).addBox(0.25F, -3.75F, 1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 0).addBox(-1.25F, -3.75F, 3.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 0).addBox(0.25F, -3.75F, 3.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 0).addBox(-2.25F, -2.5F, 3.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 0).addBox(-2.25F, -2.5F, 1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 0).addBox(-2.25F, -2.5F, -1.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 0).addBox(1.25F, -2.5F, 3.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 0).addBox(1.25F, -2.5F, 1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
		.texOffs(0, 0).addBox(1.25F, -2.5F, -1.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F)), PartPose.offset(0.0F, 4.0F, -1.0F));

		return LayerDefinition.create(meshdefinition, 64, 32);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.bodyPart.yRot = Mth.cos(ageInTicks * 0.9F) * (float)Math.PI * 0.05F;
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay,
							   float r, float g, float b, float a) {
		this.bodyPart.render(poseStack, vertexConsumer, packedLight, packedOverlay, r, g, b, a);
	}
}