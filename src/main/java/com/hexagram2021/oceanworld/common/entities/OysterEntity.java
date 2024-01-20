package com.hexagram2021.oceanworld.common.entities;

import com.hexagram2021.oceanworld.common.OWSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.UUID;

@SuppressWarnings("deprecation")
public abstract class OysterEntity extends Animal implements Bucketable {
	private static final UUID OYSTER_ARMOR_MODIFIER_UUID = UUID.fromString("C7A94ED1-297A-3AF1-8F77-8117271B1768");	//uuid3, namespace: oceanworld, content: oyster_armor_modifier

	//TODO: oyster close sound?
	private static final AttributeModifier OYSTER_ARMOR_MODIFIER = new AttributeModifier(
			OYSTER_ARMOR_MODIFIER_UUID, "Oyster armor bonus", 4.0, AttributeModifier.Operation.ADDITION
	);
	private static final EntityDataAccessor<Boolean> FROM_BUCKET = SynchedEntityData.defineId(OysterEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> IS_OPEN = SynchedEntityData.defineId(OysterEntity.class, EntityDataSerializers.BOOLEAN);

	int openShellRemainTicks = 20;
	int closeShellRemainTicks = 0;
	int productPearlRemainTicks = 56000;
	boolean availableToProducePearl = false;

	protected OysterEntity(EntityType<? extends Animal> type, Level level) {
		super(type, level);
	}

	@Override
	public float getWalkTargetValue(BlockPos blockPos, LevelReader level) {
		return 0.0F;
	}

	@Override
	public void aiStep() {
		super.aiStep();
		if(this.level().isClientSide) {
			return;
		}
		if (this.isAlive() && this.openShellRemainTicks == 1) {
			for(Mob mob : this.level().getEntitiesOfClass(Mob.class, this.getBoundingBox().inflate(0.3D))) {
				if (mob.isAlive() && mob != this) {
					this.touch(mob);
				}
			}
		}
		if(this.openShellRemainTicks > 0) {
			--this.openShellRemainTicks;
			if(this.openShellRemainTicks <= 0) {
				this.setOpen(false);
				this.closeShellRemainTicks = 1200 + this.random.nextInt(120);
			} else if(!this.isOpen()) {
				this.setOpen(true);
			}
		} else {
			--this.closeShellRemainTicks;
			if(this.closeShellRemainTicks <= 0) {
				this.setOpen(true);
				this.openShellRemainTicks = 80 + this.random.nextInt(20);
				if(this.availableToProducePearl) {
					this.availableToProducePearl = false;
					this.productPearlRemainTicks = 50000 + this.random.nextInt(6000);
					this.spawnAtLocation(this.dropPearlItem());
				}
			} else if(this.isOpen()) {
				this.setOpen(false);
			}
		}

		if(!this.availableToProducePearl) {
			--this.productPearlRemainTicks;
			if (this.productPearlRemainTicks <= 0) {
				if (this.isOpen()) {
					this.productPearlRemainTicks = 50000 + this.random.nextInt(6000);
					this.spawnAtLocation(this.dropPearlItem());
				} else {
					this.availableToProducePearl = true;
				}
			}
		}
	}

	private void touch(Mob mob) {
		if (mob.hurt(this.damageSources().mobAttack(this), 2.0F)) {
			this.playSound(OWSounds.OYSTER_ATTACK);
		}
	}

	@Override
	public void playerTouch(Player player) {
		if (this.isAlive() && this.openShellRemainTicks == 1) {
			if (player instanceof ServerPlayer && player.hurt(this.damageSources().mobAttack(this), 2.0F)) {
				this.playSound(OWSounds.OYSTER_ATTACK);
			}
		}
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(FROM_BUCKET, false);
		this.entityData.define(IS_OPEN, true);
	}

	@Override
	public void addAdditionalSaveData(CompoundTag nbt) {
		super.addAdditionalSaveData(nbt);
		nbt.putBoolean("FromBucket", this.fromBucket());
		nbt.putInt("OpenRemainTick", this.openShellRemainTicks);
		nbt.putInt("CloseRemainTick", this.closeShellRemainTicks);
		nbt.putInt("ProductRemainTick", this.productPearlRemainTicks);
		nbt.putBoolean("AvailableToProducePearl", this.availableToProducePearl);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag nbt) {
		super.readAdditionalSaveData(nbt);
		this.setFromBucket(nbt.getBoolean("FromBucket"));
		this.openShellRemainTicks = nbt.getInt("OpenRemainTick");
		this.closeShellRemainTicks = nbt.getInt("CloseRemainTick");
		this.productPearlRemainTicks = nbt.getInt("ProductRemainTick");
		this.availableToProducePearl = nbt.getBoolean("AvailableToProducePearl");
	}

	@Override
	public boolean canBreatheUnderwater() {
		return true;
	}

	@Override
	public boolean isPushedByFluid() {
		return false;
	}

	@Override
	public boolean checkSpawnObstruction(LevelReader level) {
		return level.isUnobstructed(this);
	}

	@Override
	public MobType getMobType() {
		return MobType.WATER;
	}

	@Override @Nullable
	public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob mob) {
		return null;
	}

	@Override
	public boolean fromBucket() {
		return this.entityData.get(FROM_BUCKET);
	}

	@Override
	public void setFromBucket(boolean fromBucket) {
		this.entityData.set(FROM_BUCKET, fromBucket);
	}

	public boolean isOpen() {
		return this.entityData.get(IS_OPEN);
	}

	private void setOpen(boolean open) {
		this.entityData.set(IS_OPEN, open);
		AttributeInstance attributeInstance = Objects.requireNonNull(this.getAttribute(Attributes.ARMOR));
		if(open) {
			if(attributeInstance.hasModifier(OYSTER_ARMOR_MODIFIER)) {
				attributeInstance.removeModifier(OYSTER_ARMOR_MODIFIER);
			}
		} else {
			if(!attributeInstance.hasModifier(OYSTER_ARMOR_MODIFIER)) {
				attributeInstance.addPermanentModifier(OYSTER_ARMOR_MODIFIER);
			}
		}
	}

	@Override
	public boolean requiresCustomPersistence() {
		return super.requiresCustomPersistence() || this.fromBucket();
	}

	@Override
	public boolean canBeSeenAsEnemy() {
		return this.isOpen() && super.canBeSeenAsEnemy();
	}

	@Override
	public int getMaxAirSupply() {
		return 6000;
	}

	@Override
	public void baseTick() {
		int i = this.getAirSupply();
		super.baseTick();
		if (!this.isNoAi()) {
			this.handleAirSupply(i);
		}
	}

	@Override
	public boolean hurt(DamageSource damageSource, float value) {
		this.availableToProducePearl = false;
		this.productPearlRemainTicks += 100 + this.random.nextInt(100);
		if(this.isOpen()) {
			this.openShellRemainTicks = 4;
		}
		return super.hurt(damageSource, value);
	}

	protected void handleAirSupply(int airSupply) {
		if (this.isAlive() && !this.isInWaterRainOrBubble()) {
			this.setAirSupply(airSupply - 1);
			if (this.getAirSupply() == -40) {
				this.setAirSupply(0);
				this.hurt(this.damageSources().dryOut(), 1.0F);
			}
		} else {
			this.setAirSupply(this.getMaxAirSupply());
		}

	}

	@Override
	public void saveToBucketTag(ItemStack itemStack) {
		Bucketable.saveDefaultDataToBucketTag(this, itemStack);
		CompoundTag nbt = itemStack.getOrCreateTag();
		nbt.putInt("OpenRemainTick", this.openShellRemainTicks);
		nbt.putInt("CloseRemainTick", this.closeShellRemainTicks);
		nbt.putInt("ProductRemainTick", this.productPearlRemainTicks);
		nbt.putBoolean("AvailableToProducePearl", this.availableToProducePearl);
	}

	@Override
	public void loadFromBucketTag(CompoundTag nbt) {
		Bucketable.loadDefaultDataFromBucketTag(this, nbt);
		if(nbt.contains("FromBucket", Tag.TAG_BYTE)) {
			this.setFromBucket(nbt.getBoolean("FromBucket"));
		}
		if(nbt.contains("OpenRemainTick", Tag.TAG_INT)) {
			this.openShellRemainTicks = nbt.getInt("OpenRemainTick");
		}
		if(nbt.contains("CloseRemainTick", Tag.TAG_INT)) {
			this.closeShellRemainTicks = nbt.getInt("CloseRemainTick");
		}
		if(nbt.contains("ProductRemainTick", Tag.TAG_INT)) {
			this.productPearlRemainTicks = nbt.getInt("ProductRemainTick");
		}
		this.availableToProducePearl = nbt.getBoolean("AvailableToProducePearl");
	}

	@Override @Nullable
	protected SoundEvent getAmbientSound() {
		return OWSounds.OYSTER_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		return OWSounds.OYSTER_HURT;
	}

	@Override @Nullable
	protected SoundEvent getDeathSound() {
		return OWSounds.OYSTER_DEATH;
	}

	@Override
	public SoundEvent getPickupSound() {
		return OWSounds.BUCKET_FILL_OYSTER;
	}

	@Override
	public InteractionResult mobInteract(Player player, InteractionHand hand) {
		return Bucketable.bucketMobPickup(player, hand, this).orElse(super.mobInteract(player, hand));
	}

	@Override
	protected void dropCustomDeathLoot(DamageSource damageSource, int looting, boolean recentlyHitIn) {
		super.dropCustomDeathLoot(damageSource, looting, recentlyHitIn);
		int count = this.random.nextInt(looting + 1) + (this.availableToProducePearl ? 1 : 0);
		if(count > 0) {
			this.spawnAtLocation(new ItemStack(this.dropPearlItem(), count));
		}
	}

	@Override @Nullable
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag nbt) {
		this.setOpen(false);
		this.closeShellRemainTicks = 1200 + this.random.nextInt(120);
		this.productPearlRemainTicks = 50000 + this.random.nextInt(6000);
		return super.finalizeSpawn(level, difficultyInstance, mobSpawnType, spawnGroupData, nbt);
	}

	protected abstract Item dropPearlItem();

	public static AttributeSupplier.Builder createAttributes() {
		return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 8.0D).add(Attributes.KNOCKBACK_RESISTANCE, 0.5D).add(Attributes.ARMOR, 1.0D).add(Attributes.MOVEMENT_SPEED, 0.0D).add(Attributes.ATTACK_DAMAGE, 2.0D);
	}

	@SuppressWarnings("unused")
	public static boolean checkOysterSpawnRules(EntityType<? extends OysterEntity> entityType, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
		int brightness = level.getRawBrightness(pos, 0);
		return brightness < 8 && (spawnType.equals(MobSpawnType.SPAWNER) || (
				pos.getY() <= level.getSeaLevel() - 8 && pos.getY() >= 8 && level.getBlockState(pos.above()).is(Blocks.WATER)
		));
	}
}
