package com.hexagram2021.oceanworld.client.models;

import com.hexagram2021.oceanworld.common.entities.OysterEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

public class OysterModel<T extends OysterEntity> extends EntityModel<T> {
	private final ModelPart root;

	private final ModelPart shell2;
	private final ModelPart meat;

	public OysterModel(ModelPart root) {
		this.root = root;
		this.shell2 = root.getChild("shell2");
		this.meat = root.getChild("meat");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition root = meshdefinition.getRoot();

		PartDefinition shell1 = root.addOrReplaceChild("shell1", CubeListBuilder.create(), PartPose.offset(0.0F, 23.0F, 4.5F));
		shell1.addOrReplaceChild("bottom_shell",
				CubeListBuilder.create()
						.texOffs(0, 0).addBox(-7.0F, -0.5F, -3.0F, 14.0F, 2.0F, 8.0F, new CubeDeformation(-0.5F))
						.texOffs(0, 10).addBox(-3.0F, -0.5F, -6.0F, 6.0F, 2.0F, 4.0F, new CubeDeformation(-0.5F)),
				PartPose.offset(0.0F, -0.5F, -4.5F));

		PartDefinition shell2 = root.addOrReplaceChild("shell2", CubeListBuilder.create(), PartPose.offset(0.0F, 23.0F, 4.5F));
		shell2.addOrReplaceChild("head_shell",
				CubeListBuilder.create().mirror()
						.texOffs(0, 0).addBox(-7.0F, -1.0F, -3.0F, 14.0F, 2.0F, 8.0F, new CubeDeformation(-0.5F))
						.texOffs(0, 10).addBox(-3.0F, -1.0F, -6.0F, 6.0F, 2.0F, 4.0F, new CubeDeformation(-0.5F)),
				PartPose.offsetAndRotation(0.0F, -0.5F, -4.5F, 0.0F, 0.0F, -Mth.PI));

		root.addOrReplaceChild("back",
				CubeListBuilder.create().texOffs(32, 10).addBox(-2.0F, -2.5F, 4.0F, 4.0F, 3.0F, 2.0F, new CubeDeformation(-0.5F)),
				PartPose.offset(0.0F, 24.0F, 0.0F));

		root.addOrReplaceChild("meat",
				CubeListBuilder.create().texOffs(0, 16).addBox(-4.0F, -2.25F, -2.0F, 8.0F, 2.0F, 6.0F, new CubeDeformation(-0.75F)),
				PartPose.offset(0.0F, 24.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 32);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		if(entity.isOpen()) {
			this.shell2.xRot = -1.0F;
		} else {
			this.shell2.xRot = -0.05F - 0.05F * Mth.sin(Mth.HALF_PI * ageInTicks / 12.0F + Mth.HALF_PI / 2.0F);
		}

		this.meat.xScale = 1.0F + 0.05F * Mth.sin(0.2F * ageInTicks) + 0.02F * Mth.sin(0.5F * ageInTicks) + 0.015F * Mth.sin(0.7F * ageInTicks);
		this.meat.yScale = 1.1F + 0.1F * Mth.sin(0.1F * ageInTicks - Mth.HALF_PI);
		this.meat.zScale = 1.0F + 0.03F * Mth.sin(0.2F * ageInTicks) + 0.015F * Mth.sin(0.27F * ageInTicks) + 0.01F * Mth.sin(0.75F * ageInTicks);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay,
							   float r, float g, float b, float a) {
		this.root.render(poseStack, vertexConsumer, packedLight, packedOverlay, r, g, b, a);
	}
}