package com.hexagram2021.oceanworld.common;

import com.hexagram2021.oceanworld.common.entities.OysterEntity;
import com.hexagram2021.oceanworld.common.entities.SeaCucumberEntity;
import com.hexagram2021.oceanworld.common.register.*;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.monster.PatrollingMonster;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegisterEvent;

import java.util.function.Consumer;

import static com.hexagram2021.oceanworld.OceanWorld.MODID;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class OWContent {
	public static void modConstruction(IEventBus bus, @SuppressWarnings("unused") Consumer<Runnable> runLater) {
		ModsLoadedHelper.compatModLoaded();

		OWMultiNoiseBiomeSourceParameterListPresets.init();
		OWCreativeModeTabs.init(bus);

		OWBlocks.init(bus);
		OWItems.init(bus);

		OWPlacementModifierTypes.init(bus);

		OWBiomeTags.init();
		OWBlockTags.init();
		OWConfiguredFeatureKeys.init();
		OWPlacedFeatureKeys.init();
		OWStructureTypes.init(bus);
		OWStructureKeys.init();
		OWStructureSetKeys.init();
	}

	@SubscribeEvent
	public static void onRegister(RegisterEvent event) {
		OWSounds.init(event);

		OWFeatures.init(event);
		OWStructurePieceTypes.init();

		OWProcessorType.init(event);

		OWEntities.init(event);
	}

	public static void init() {
	}

	@SubscribeEvent
	public static void registerEntitySpawnPlacement(SpawnPlacementRegisterEvent event) {
		event.register(OWEntities.SEA_CUCUMBER, SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				SeaCucumberEntity::checkSeaCucumberSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
		event.register(OWEntities.OCEANOLOGER, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				PatrollingMonster::checkPatrollingMonsterSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
		event.register(OWEntities.BLACKLIP_OYSTER, SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				OysterEntity::checkOysterSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
		event.register(OWEntities.WHITELIP_OYSTER, SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				OysterEntity::checkOysterSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
	}
}
