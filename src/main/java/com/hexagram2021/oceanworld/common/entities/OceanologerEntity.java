package com.hexagram2021.oceanworld.common.entities;

import com.google.common.collect.Lists;
import com.hexagram2021.oceanworld.common.OWSounds;
import com.hexagram2021.oceanworld.common.entities.goals.RideBoatGoal;
import com.hexagram2021.oceanworld.common.register.OWBlocks;
import com.hexagram2021.oceanworld.common.register.OWEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.SpellcasterIllager;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.EntityGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.ForgeEventFactory;

import javax.annotation.Nullable;
import java.util.List;

public class OceanologerEntity extends SpellcasterIllager {
	/**
	 * An illager who can <b>kill all boat entities</b>, <b>summon drip ice</b> and <b>set water blocks</b>.
	 */
	@Nullable
	private Sheep wololoTarget;

	public OceanologerEntity(EntityType<? extends OceanologerEntity> entityType, Level level) {
		super(entityType, level);
		this.xpReward = 12;
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(0, new RideBoatGoal(this));
		this.goalSelector.addGoal(1, new OceanologerEntity.CastingSpellGoal());
		this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, Player.class, 6.0F, 0.6D, 1.0D));
		this.goalSelector.addGoal(4, new OceanologerEntity.AttackSpellGoal());
		this.goalSelector.addGoal(5, new OceanologerEntity.TrapSpellGoal());
		this.goalSelector.addGoal(6, new OceanologerEntity.WololoSpellGoal());
		this.goalSelector.addGoal(8, new RandomStrollGoal(this, 0.6D));
		this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
		this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Mob.class, 8.0F));
		this.targetSelector.addGoal(1, (new HurtByTargetGoal(this, Raider.class)).setAlertOthers());
		this.targetSelector.addGoal(2, (new NearestAttackableTargetGoal<>(this, Player.class, true)).setUnseenMemoryTicks(300));
		this.targetSelector.addGoal(3, (new NearestAttackableTargetGoal<>(this, AbstractVillager.class, false)).setUnseenMemoryTicks(300));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, false));
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Monster.createMonsterAttributes()
				.add(Attributes.MOVEMENT_SPEED, 0.5D)
				.add(Attributes.FOLLOW_RANGE, 12.0D)
				.add(Attributes.MAX_HEALTH, 24.0D);
	}

	@Override
	public SoundEvent getCelebrateSound() {
		return OWSounds.OCEANOLOGER_CELEBRATE;
	}

	@Override
	public void addAdditionalSaveData(CompoundTag nbt) {
		super.addAdditionalSaveData(nbt);
	}

	@Override
	public boolean isAlliedTo(Entity entity) {
		if (super.isAlliedTo(entity)) {
			return true;
		}
		if (entity instanceof LivingEntity && ((LivingEntity)entity).getMobType() == MobType.ILLAGER) {
			return this.getTeam() == null && entity.getTeam() == null;
		}
		return false;
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return OWSounds.OCEANOLOGER_AMBIENT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return OWSounds.OCEANOLOGER_DEATH;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		return OWSounds.OCEANOLOGER_HURT;
	}

	void setWololoTarget(@Nullable Sheep sheep) {
		this.wololoTarget = sheep;
	}


	@SuppressWarnings("deprecation")
	@Override
	public boolean canBreatheUnderwater() {
		return true;
	}

	@Nullable
	Sheep getWololoTarget() {
		return this.wololoTarget;
	}

	@Override
	protected SoundEvent getCastingSoundEvent() {
		return OWSounds.OCEANOLOGER_CAST_SPELL;
	}

	@Override
	public void applyRaidBuffs(int group, boolean raidSpawn) {
	}

	@Override
	public void findPatrolTarget() {
		super.findPatrolTarget();
		this.setPatrolTarget(this.blockPosition().offset(-64 + this.random.nextInt(128), 0, -64 + this.random.nextInt(128)));
	}

	class TrapSpellGoal extends SpellcasterIllager.SpellcasterUseSpellGoal {

		@Override
		protected int getCastingTime() {
			return 100;
		}

		@Override
		protected int getCastingInterval() {
			return 1000;
		}

		@Override
		public boolean canUse() {
			LivingEntity target = OceanologerEntity.this.getTarget();
			if(!super.canUse() || OceanologerEntity.this.isPassenger() || target == null || target.distanceTo(OceanologerEntity.this) >= 16.0D) {
				return false;
			}
			return ForgeEventFactory.getMobGriefingEvent(OceanologerEntity.this.level(), OceanologerEntity.this);
		}

		@Override
		public boolean canContinueToUse() {
			LivingEntity target = OceanologerEntity.this.getTarget();
			return super.canContinueToUse() && target != null && target.distanceTo(OceanologerEntity.this) < 16.0D;
		}

		private void spellSetBlock(BlockPos blockPos, BlockState blockState, boolean forced) {
			BlockState defaultBlockState = OceanologerEntity.this.level().getBlockState(blockPos);
			if(forced) {
				if(!defaultBlockState.is(BlockTags.FEATURES_CANNOT_REPLACE)) {
					OceanologerEntity.this.level().setBlock(blockPos, blockState, Block.UPDATE_ALL);
				}
			} else {
				if(defaultBlockState.is(Blocks.AIR) ||
						defaultBlockState.is(Blocks.FIRE) ||
						defaultBlockState.is(BlockTags.LEAVES) ||
						defaultBlockState.is(Blocks.WATER) ||
						defaultBlockState.is(Blocks.LAVA) ||
						defaultBlockState.is(Blocks.ICE)) {
					OceanologerEntity.this.level().setBlock(blockPos, blockState, Block.UPDATE_ALL);
				}
			}
		}

		@Override
		protected void performSpellCasting() {
			LivingEntity livingentity = OceanologerEntity.this.getTarget();
			assert livingentity != null;
			BlockPos blockPos = livingentity.blockPosition();
			livingentity.teleportTo(blockPos.getX() + 0.5D, blockPos.getY(), blockPos.getZ() + 0.5D);
			BlockPos magmaBlockPos = blockPos.below();
			BlockPos aboveBlockPos = blockPos.above();
			spellSetBlock(magmaBlockPos, OWBlocks.IceDecoration.FAKE_FROSTED_ICE.defaultBlockState(), true);
			spellSetBlock(magmaBlockPos.north(), OWBlocks.IceDecoration.FAKE_FROSTED_ICE.defaultBlockState(), false);
			spellSetBlock(magmaBlockPos.south(), OWBlocks.IceDecoration.FAKE_FROSTED_ICE.defaultBlockState(), false);
			spellSetBlock(magmaBlockPos.east(), OWBlocks.IceDecoration.FAKE_FROSTED_ICE.defaultBlockState(), false);
			spellSetBlock(magmaBlockPos.west(), OWBlocks.IceDecoration.FAKE_FROSTED_ICE.defaultBlockState(), false);
			spellSetBlock(blockPos, Blocks.BUBBLE_COLUMN.defaultBlockState(), true);
			spellSetBlock(blockPos.north(), OWBlocks.IceDecoration.FAKE_FROSTED_ICE.defaultBlockState(), false);
			spellSetBlock(blockPos.south(), OWBlocks.IceDecoration.FAKE_FROSTED_ICE.defaultBlockState(), false);
			spellSetBlock(blockPos.east(), OWBlocks.IceDecoration.FAKE_FROSTED_ICE.defaultBlockState(), false);
			spellSetBlock(blockPos.west(), OWBlocks.IceDecoration.FAKE_FROSTED_ICE.defaultBlockState(), false);
			spellSetBlock(aboveBlockPos, Blocks.BUBBLE_COLUMN.defaultBlockState(), true);
			spellSetBlock(aboveBlockPos.north(), OWBlocks.IceDecoration.FAKE_FROSTED_ICE.defaultBlockState(), false);
			spellSetBlock(aboveBlockPos.south(), OWBlocks.IceDecoration.FAKE_FROSTED_ICE.defaultBlockState(), false);
			spellSetBlock(aboveBlockPos.east(), OWBlocks.IceDecoration.FAKE_FROSTED_ICE.defaultBlockState(), false);
			spellSetBlock(aboveBlockPos.west(), OWBlocks.IceDecoration.FAKE_FROSTED_ICE.defaultBlockState(), false);
			spellSetBlock(aboveBlockPos.above(), OWBlocks.IceDecoration.FAKE_FROSTED_ICE.defaultBlockState(), true);

			List<Boat> list = getNearbyBoats(
					OceanologerEntity.this.level(), OceanologerEntity.this,
					OceanologerEntity.this.getBoundingBox().inflate(16.0D, 4.0D, 16.0D)
			);
			if (!list.isEmpty()) {
				for(Boat boat: list) {
					boat.discard();
				}
			}
		}

		private List<Boat> getNearbyBoats(EntityGetter level, LivingEntity entity, AABB aabb) {
			List<Boat> list = level.getEntitiesOfClass(Boat.class, aabb, target -> !(target.getFirstPassenger() instanceof OceanologerEntity));
			List<Boat> list1 = Lists.newArrayList();

			for(Boat boat : list) {
				if (entity.closerThan(boat, 16.0D)) {
					list1.add(boat);
				}
			}

			return list1;
		}

		@Override
		protected SoundEvent getSpellPrepareSound() {
			return OWSounds.OCEANOLOGER_PREPARE_SUMMON;
		}

		@Override
		protected SpellcasterIllager.IllagerSpell getSpell() {
			return SpellcasterIllager.IllagerSpell.FANGS;
		}
	}

	class CastingSpellGoal extends SpellcasterIllager.SpellcasterCastingSpellGoal {
		@Override
		public void tick() {
			if (OceanologerEntity.this.getTarget() != null) {
				OceanologerEntity.this.getLookControl().setLookAt(
						OceanologerEntity.this.getTarget(),
						(float)OceanologerEntity.this.getMaxHeadYRot(),
						(float)OceanologerEntity.this.getMaxHeadXRot()
				);
			} else if (OceanologerEntity.this.getWololoTarget() != null) {
				OceanologerEntity.this.getLookControl().setLookAt(
						OceanologerEntity.this.getWololoTarget(),
						(float)OceanologerEntity.this.getMaxHeadYRot(),
						(float)OceanologerEntity.this.getMaxHeadXRot()
				);
			}
		}
	}

	class AttackSpellGoal extends SpellcasterIllager.SpellcasterUseSpellGoal {
		@Override
		protected int getCastingTime() {
			return 40;
		}

		@Override
		protected int getCastingInterval() {
			return 100;
		}

		@Override
		public boolean canUse() {
			LivingEntity target = OceanologerEntity.this.getTarget();
			return super.canUse() && target != null && target.distanceTo(OceanologerEntity.this) < 20.0D;
		}

		@Override
		public boolean canContinueToUse() {
			LivingEntity target = OceanologerEntity.this.getTarget();
			return super.canContinueToUse() && target != null && target.distanceTo(OceanologerEntity.this) < 20.0D;
		}

		@Override
		protected void performSpellCasting() {
			ServerLevel serverlevel = (ServerLevel)OceanologerEntity.this.level();

			float forceRotateAngle = (OceanologerEntity.this.random.nextInt(2) * 2 - 1) * DripIceEntity.FORCE_ROTATION;
			for(int i = 0; i < 8; ++i) {
				BlockPos blockpos = OceanologerEntity.this.blockPosition()
						.offset(-2 + OceanologerEntity.this.random.nextInt(5), 2, -2 + OceanologerEntity.this.random.nextInt(5));
				DripIceEntity dripIce = OWEntities.DRIP_ICE.create(OceanologerEntity.this.level());
				if(dripIce != null) {
					dripIce.moveTo(blockpos, 0.0F, 0.0F);
					dripIce.shoot(0.0D, 1.0D, 0.0D, 0.8F, 20.0F);
					dripIce.setOwner(OceanologerEntity.this);
					dripIce.setForceRotateAngle(forceRotateAngle);
					serverlevel.addFreshEntityWithPassengers(dripIce);
				}
			}
		}

		@Override
		protected SoundEvent getSpellPrepareSound() {
			return OWSounds.OCEANOLOGER_PREPARE_ATTACK;
		}

		@Override
		protected SpellcasterIllager.IllagerSpell getSpell() {
			return SpellcasterIllager.IllagerSpell.SUMMON_VEX;
		}
	}

	public class WololoSpellGoal extends SpellcasterIllager.SpellcasterUseSpellGoal {
		private final TargetingConditions wololoTargeting = TargetingConditions.forNonCombat().range(16.0D).selector((entity) ->
				((Sheep)entity).getColor() == DyeColor.RED
		);

		@Override
		public boolean canUse() {
			if (OceanologerEntity.this.getTarget() != null) {
				return false;
			} else if (OceanologerEntity.this.isCastingSpell()) {
				return false;
			} else if (OceanologerEntity.this.tickCount < this.nextAttackTickCount) {
				return false;
			} else if (!ForgeEventFactory.getMobGriefingEvent(OceanologerEntity.this.level(), OceanologerEntity.this)) {
				return false;
			} else {
				List<Sheep> list = OceanologerEntity.this.level().getNearbyEntities(
						Sheep.class, this.wololoTargeting, OceanologerEntity.this,
						OceanologerEntity.this.getBoundingBox().inflate(16.0D, 4.0D, 16.0D)
				);
				if (list.isEmpty()) {
					return false;
				} else {
					OceanologerEntity.this.setWololoTarget(list.get(OceanologerEntity.this.random.nextInt(list.size())));
					return true;
				}
			}
		}

		@Override
		public boolean canContinueToUse() {
			return OceanologerEntity.this.getWololoTarget() != null && this.attackWarmupDelay > 0;
		}

		@Override
		public void stop() {
			super.stop();
			OceanologerEntity.this.setWololoTarget(null);
		}

		@Override
		protected void performSpellCasting() {
			Sheep sheep = OceanologerEntity.this.getWololoTarget();
			if (sheep != null && sheep.isAlive()) {
				sheep.setColor(DyeColor.BLUE);
			}

		}

		@Override
		protected int getCastWarmupTime() {
			return 40;
		}

		@Override
		protected int getCastingTime() {
			return 60;
		}

		@Override
		protected int getCastingInterval() {
			return 140;
		}

		@Override
		protected SoundEvent getSpellPrepareSound() {
			return OWSounds.OCEANOLOGER_PREPARE_WOLOLO;
		}

		@Override
		protected SpellcasterIllager.IllagerSpell getSpell() {
			return SpellcasterIllager.IllagerSpell.WOLOLO;
		}
	}
}
