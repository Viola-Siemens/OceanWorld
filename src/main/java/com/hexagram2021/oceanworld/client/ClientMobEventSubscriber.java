package com.hexagram2021.oceanworld.client;

import com.hexagram2021.oceanworld.client.models.OysterModel;
import com.hexagram2021.oceanworld.client.models.SeaCucumberModel;
import com.hexagram2021.oceanworld.client.renderers.*;
import com.hexagram2021.oceanworld.common.register.OWEntities;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.IllagerModel;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import static com.hexagram2021.oceanworld.OceanWorld.MODID;

@Mod.EventBusSubscriber(modid = MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientMobEventSubscriber {
	@SubscribeEvent
	public static void onRegisterLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
		event.registerLayerDefinition(SeaCucumberModel.LAYER_LOCATION, SeaCucumberModel::createBodyLayer);
		event.registerLayerDefinition(OceanologerRenderer.LAYER_LOCATION, IllagerModel::createBodyLayer);
		event.registerLayerDefinition(BlacklipOysterRenderer.LAYER_LOCATION, OysterModel::createBodyLayer);
		event.registerLayerDefinition(WhitelipOysterRenderer.LAYER_LOCATION, OysterModel::createBodyLayer);
		event.registerLayerDefinition(OWModelLayers.BRACELET, () -> LayerDefinition.create(HumanoidModel.createMesh(new CubeDeformation(0.25F), 0.0F), 64, 64));
		event.registerLayerDefinition(OWModelLayers.NECKLACE, () -> LayerDefinition.create(HumanoidModel.createMesh(new CubeDeformation(0.25F), 0.0F), 64, 64));
	}

	@SubscribeEvent
	public static void onRegisterRenderer(EntityRenderersEvent.RegisterRenderers event) {
		event.registerEntityRenderer(OWEntities.SEA_CUCUMBER, SeaCucumberRenderer::new);
		event.registerEntityRenderer(OWEntities.OCEANOLOGER, OceanologerRenderer::new);
		event.registerEntityRenderer(OWEntities.BLACKLIP_OYSTER, BlacklipOysterRenderer::new);
		event.registerEntityRenderer(OWEntities.WHITELIP_OYSTER, WhitelipOysterRenderer::new);
		event.registerEntityRenderer(OWEntities.DRIP_ICE, DripIceRenderer::new);
	}

	@SubscribeEvent
	public static void onClientSetup(FMLClientSetupEvent event) {
		event.enqueueWork(() -> {
			if(ModList.get().isLoaded("curios")) {
				CuriosRenderers.registerRenderers();
			}
		});
	}
}