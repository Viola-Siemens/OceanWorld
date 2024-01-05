package com.hexagram2021.oceanworld.common.entities;

import com.hexagram2021.oceanworld.common.OWSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class SeaCucumberEntity extends Animal {
	public SeaCucumberEntity(EntityType<? extends SeaCucumberEntity> entityType, Level level) {
		super(entityType, level);
		this.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
	}

	@Override
	public float getWalkTargetValue(BlockPos blockPos, LevelReader level) {
		return 0.0F;
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(1, new PanicGoal(this, 1.25D));
		this.goalSelector.addGoal(2, new RandomStrollGoal(this, 1.0D));
		this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
	}

	@Override @Nullable
	protected SoundEvent getAmbientSound() {
		return OWSounds.SEA_CUCUMBER_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		return OWSounds.SEA_CUCUMBER_HURT;
	}

	@Override
	public boolean checkSpawnObstruction(LevelReader level) {
		return level.isUnobstructed(this);
	}

	@Override
	public MobType getMobType() {
		return MobType.WATER;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return OWSounds.SEA_CUCUMBER_DEATH;
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 6.0D).add(Attributes.MOVEMENT_SPEED, 0.1D);
	}

	@Override
	public boolean isBaby() {
		return false;
	}

	@Override
	public boolean canMate(Animal animal) {
		return false;
	}

	@Override @Nullable
	public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob mob) {
		return null;
	}

	@Override
	public boolean canBreatheUnderwater() {
		return true;
	}

	@SuppressWarnings("unused")
	public static boolean checkSeaCucumberSpawnRules(EntityType<SeaCucumberEntity> entityType, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
		return spawnType.equals(MobSpawnType.SPAWNER) ||
				(pos.getY() <= level.getSeaLevel() - 16 && pos.getY() >= 8 && level.getBlockState(pos.above()).is(Blocks.WATER));
	}
}
