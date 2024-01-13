package com.hexagram2021.oceanworld;

import com.hexagram2021.oceanworld.common.OWContent;
import com.hexagram2021.oceanworld.common.OWFoods;
import com.hexagram2021.oceanworld.common.config.OWCommonConfig;
import com.hexagram2021.oceanworld.common.register.OWDimensionKeys;
import com.hexagram2021.oceanworld.common.register.OWItems;
import com.hexagram2021.oceanworld.common.world.spawners.OWSpawners;
import com.hexagram2021.oceanworld.common.world.spawners.OceanPatrolSpawner;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.ModLoadingStage;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.Optional;
import java.util.function.Consumer;

import static com.hexagram2021.oceanworld.common.OWLogger.LOGGER;

@SuppressWarnings("unused")
@Mod(OceanWorld.MODID)
public class OceanWorld {
	public static final String MODID = "oceanworld";
	public static final String MODNAME = "Ocean World";
	public static final String VERSION = "${version}";

	public OceanWorld() {
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		DeferredWorkQueue queue = DeferredWorkQueue.lookup(Optional.of(ModLoadingStage.CONSTRUCT)).orElseThrow();
		Consumer<Runnable> runLater = job -> queue.enqueueWork(
				ModLoadingContext.get().getActiveContainer(), job
		);
		OWContent.modConstruction(bus, runLater);

		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, OWCommonConfig.getConfig());

		bus.addListener(this::setup);
		bus.addListener(this::enqueueIMC);
		MinecraftForge.EVENT_BUS.register(this);
	}

	private void setup(final FMLCommonSetupEvent event) {
		LOGGER.info("Welcome to the ocean world!");
		OWContent.init();
		event.enqueueWork(() -> {
			if(OWCommonConfig.SPAWN_OCEAN_PATROL_OVERWORLD.get()) {
				OWSpawners.register(Level.OVERWORLD, new OceanPatrolSpawner());
			}
			if(OWCommonConfig.SPAWN_OCEAN_PATROL_OCEANWORLD.get()) {
				OWSpawners.register(OWDimensionKeys.OCEANWORLD, new OceanPatrolSpawner(true));
			}
		});
	}

	private void enqueueIMC(final InterModEnqueueEvent event) {
		if(ModList.get().isLoaded("diet")) {
			//Hello, TheIllusiveC4? Curios API is using reloadable datapacks instead of IMC, so how about Diet?
			OWFoods.compatDiet(OWItems.SEA_CUCUMBER.get(), OWFoods.SEA_CUCUMBER);
			OWFoods.compatDiet(OWItems.OYSTER_MEAT.get(), OWFoods.OYSTER_MEAT);
			OWFoods.compatDiet(OWItems.COOKED_OYSTER_MEAT.get(), OWFoods.COOKED_OYSTER_MEAT);
		}
	}
}
