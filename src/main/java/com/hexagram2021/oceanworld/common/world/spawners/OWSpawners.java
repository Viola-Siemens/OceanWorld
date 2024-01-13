package com.hexagram2021.oceanworld.common.world.spawners;

import com.google.common.collect.Lists;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

import static com.hexagram2021.oceanworld.OceanWorld.MODID;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class OWSpawners {
	private static final List<OceanWorldSpawner.Registry> REGISTERED_SPAWNERS = Lists.newArrayList();

	@SubscribeEvent(priority = EventPriority.HIGH)
	public static void onServerStarted(ServerStartedEvent event) {
		REGISTERED_SPAWNERS.forEach(registry -> registry.spawner().reset());
	}

	@SubscribeEvent
	public static void onWorldTick(TickEvent.LevelTickEvent event) {
		if(event.phase == TickEvent.Phase.END && event.level instanceof ServerLevel serverLevel) {
			REGISTERED_SPAWNERS.stream()
					.filter(registry -> registry.dimension().equals(serverLevel.dimension()))
					.forEach(registry -> registry.spawner().tick(serverLevel));
		}
	}

	public static void register(ResourceKey<Level> dimension, OceanWorldSpawner spawner) {
		REGISTERED_SPAWNERS.add(new OceanWorldSpawner.Registry(spawner, dimension));
	}

	private OWSpawners() {
	}
}
