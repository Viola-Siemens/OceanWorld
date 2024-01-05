package com.hexagram2021.oceanworld.client;

import com.hexagram2021.oceanworld.client.renderers.curios.BraceletRenderer;
import com.hexagram2021.oceanworld.client.renderers.curios.NecklaceRenderer;
import com.hexagram2021.oceanworld.common.register.OWItems;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;

public class CuriosRenderers {
	public static void registerRenderers() {
		CuriosRendererRegistry.register(OWItems.WHITE_PEARL_NECKLACE.get(), NecklaceRenderer::new);
		CuriosRendererRegistry.register(OWItems.BLACK_PEARL_NECKLACE.get(), NecklaceRenderer::new);
		CuriosRendererRegistry.register(OWItems.GOLDEN_PEARL_NECKLACE.get(), NecklaceRenderer::new);
		CuriosRendererRegistry.register(OWItems.WHITE_PEARL_BRACELET.get(), BraceletRenderer::new);
		CuriosRendererRegistry.register(OWItems.BLACK_PEARL_BRACELET.get(), BraceletRenderer::new);
		CuriosRendererRegistry.register(OWItems.GOLDEN_PEARL_BRACELET.get(), BraceletRenderer::new);
	}
}
