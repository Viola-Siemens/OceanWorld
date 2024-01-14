package com.hexagram2021.oceanworld.common.world.spawners;

import com.hexagram2021.oceanworld.common.OWLogger;
import com.hexagram2021.oceanworld.common.config.OWCommonConfig;
import com.hexagram2021.oceanworld.common.register.OWEntities;
import net.minecraft.SharedConstants;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BiomeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.monster.PatrollingMonster;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;

@SuppressWarnings("deprecation")
public class OceanPatrolSpawner implements OceanWorldSpawner {
	private final boolean again;
	private int nextTick = OWCommonConfig.OCEAN_PATROL_SPAWN_INTERVAL.get() * SharedConstants.TICKS_PER_SECOND;

	public OceanPatrolSpawner() {
		this(false);
	}
	public OceanPatrolSpawner(boolean tryAgain) {
		this.again = tryAgain;
	}

	@Override
	public void tick(ServerLevel serverLevel) {
		if(!serverLevel.getChunkSource().spawnEnemies || !serverLevel.getGameRules().getBoolean(GameRules.RULE_DO_PATROL_SPAWNING)) {
			return;
		}
		RandomSource random = serverLevel.random;
		--this.nextTick;
		if (this.nextTick <= 0) {
			this.nextTick += OWCommonConfig.OCEAN_PATROL_SPAWN_INTERVAL.get() * SharedConstants.TICKS_PER_SECOND + random.nextInt(OWCommonConfig.OCEAN_PATROL_SPAWN_INTERVAL.get() * SharedConstants.TICKS_PER_SECOND / 10);
			if (serverLevel.getDayTime() / SharedConstants.TICKS_PER_GAME_DAY >= 10L && serverLevel.isDay()) {
				if(random.nextInt(100) < OWCommonConfig.OCEAN_PATROL_SPAWN_CHANCE.get()) {
					if(!this.again || random.nextInt(120) >= OWCommonConfig.OCEAN_PATROL_SPAWN_CHANCE.get()) {
						return;
					}
				}
				int playerCount = serverLevel.players().size();
				if(playerCount < 1) {
					return;
				}
				ServerPlayer player = serverLevel.players().get(random.nextInt(playerCount));
				if(player.isSpectator() || serverLevel.isCloseToVillage(player.blockPosition(), 2)) {
					return;
				}

				int x = (32 + random.nextInt(16)) * (random.nextBoolean() ? -1 : 1);
				int z = (32 + random.nextInt(16)) * (random.nextBoolean() ? -1 : 1);
				BlockPos.MutableBlockPos spawnPos = player.blockPosition().mutable().move(x, 0, z).setY(serverLevel.getSeaLevel() + 1);
				if(!serverLevel.hasChunksAt(spawnPos.getX() - 10, spawnPos.getZ() - 10, spawnPos.getX() + 10, spawnPos.getZ() + 10)) {
					return;
				}
				Holder<Biome> holder = serverLevel.getBiome(spawnPos);
				if(!holder.is(BiomeTags.IS_OCEAN) ||
						!serverLevel.getBlockState(spawnPos.below()).isAir() ||
						!serverLevel.getBlockState(spawnPos.below(2)).is(Blocks.WATER)) {
					return;
				}

				int tries = (int)Math.ceil(serverLevel.getCurrentDifficultyAt(spawnPos).getEffectiveDifficulty()) + 1;

				Boat.Type boatType = switch(random.nextInt(7)) {
					case 0 -> Boat.Type.BIRCH;
					case 1, 2, 3, 4 -> Boat.Type.DARK_OAK;
					default -> Boat.Type.OAK;
				};
				for(int i = 0; i < tries; ++i) {
					if (i == 0) {
						if (!this.spawnPatrolMember(serverLevel, spawnPos, boatType, player, true)) {
							return;
						}
					} else {
						this.spawnPatrolMember(serverLevel, spawnPos, boatType, player, false);
					}

					spawnPos.setX(spawnPos.getX() + random.nextInt(8) - random.nextInt(8));
					spawnPos.setZ(spawnPos.getZ() + random.nextInt(8) - random.nextInt(8));
				}
				OWLogger.LOGGER.debug("Spawn ocean patrol at %s (%d, %d, %d).".formatted(serverLevel.dimension(), spawnPos.getX(), spawnPos.getY(), spawnPos.getZ()));
			}
		}
	}

	@Override
	public void reset() {
		this.nextTick = OWCommonConfig.OCEAN_PATROL_SPAWN_INTERVAL.get();
	}

	private boolean spawnPatrolMember(ServerLevel serverLevel, BlockPos blockPos, Boat.Type boatType, ServerPlayer player, boolean leader) {
		if (!checkOceanPatrollingMonsterSpawnRules(serverLevel, blockPos)) {
			return false;
		}
		Boat boat = EntityType.BOAT.create(serverLevel);
		if(boat == null) {
			return false;
		}
		boat.setVariant(boatType);
		PatrollingMonster oceanologer = OWEntities.OCEANOLOGER.create(serverLevel);
		if (oceanologer != null) {
			if (leader) {
				oceanologer.setPatrolLeader(true);
				oceanologer.setPatrolTarget(player.blockPosition());
			}
			oceanologer.setPos(blockPos.getX(), blockPos.getY(), blockPos.getZ());
			oceanologer.setTarget(player);
			oceanologer.finalizeSpawn(serverLevel, serverLevel.getCurrentDifficultyAt(blockPos), MobSpawnType.PATROL, null, null);
			boat.setPos(blockPos.getX(), blockPos.getY(), blockPos.getZ());
			oceanologer.startRiding(boat);
			serverLevel.addFreshEntity(oceanologer);
			serverLevel.addFreshEntity(boat);
			return true;
		}
		boat.discard();
		return false;
	}

	private static boolean checkOceanPatrollingMonsterSpawnRules(LevelAccessor level, BlockPos blockPos) {
		return level.getBrightness(LightLayer.BLOCK, blockPos) <= 8 && level.getDifficulty() != Difficulty.PEACEFUL;
	}
}
