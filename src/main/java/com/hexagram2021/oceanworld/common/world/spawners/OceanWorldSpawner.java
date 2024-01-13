package com.hexagram2021.oceanworld.common.world.spawners;

import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;

public interface OceanWorldSpawner {
	void tick(ServerLevel serverLevel);

	void reset();

	record Registry(OceanWorldSpawner spawner, ResourceKey<Level> dimension) {
	}
}
