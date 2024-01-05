package com.hexagram2021.oceanworld.common.register;

import com.hexagram2021.oceanworld.common.world.processors.AristocratsResidenceProcessor;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraftforge.registries.RegisterEvent;

import static com.hexagram2021.oceanworld.OceanWorld.MODID;

public class OWProcessorType {
	public static final StructureProcessorType<AristocratsResidenceProcessor> ARISTOCRATS_RESIDENCE = () -> AristocratsResidenceProcessor.CODEC;

	public static void init(RegisterEvent event) {
		event.register(Registries.STRUCTURE_PROCESSOR, helper ->
				helper.register(new ResourceLocation(MODID, "aristocrats_residence"), ARISTOCRATS_RESIDENCE));
	}
}
