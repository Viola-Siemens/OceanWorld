package com.hexagram2021.oceanworld.common.entities;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

public class DripIceEntity extends AbstractArrow {
	private static final float MOVE_MULTI_RATE = 2.0F;
	private static final double MAX_CORRECT = 0.05D;

	public static final float FORCE_ROTATION = 10.0F;
	private float forceRotateAngle = FORCE_ROTATION;

	public DripIceEntity(EntityType<? extends DripIceEntity> entityType, Level level) {
		super(entityType, level);
	}

	@Override
	protected ItemStack getPickupItem() {
		return new ItemStack(Items.ARROW);
	}

	@Override
	public void tick() {
		super.tick();
		if(this.getOwner() instanceof Mob owner && owner.getTarget() != null) {
			Vec3 targetPosition = owner.getTarget().position().add(owner.getTarget().getDeltaMovement().scale(MOVE_MULTI_RATE)).add(0.0D, 1.0D, 0.0D);
			Vec3 expectedVelocity = targetPosition.subtract(this.position());
			Vec3 deltaMovement = this.getDeltaMovement();
			Vec3 deltaMovementNorm = deltaMovement.normalize();
			Vec3 correction = expectedVelocity.subtract(
					deltaMovementNorm.scale(expectedVelocity.dot(deltaMovementNorm.normalize()))
			);
			double correctionSpeed = correction.length();
			if(correctionSpeed > MAX_CORRECT) {
				correction = correction.scale(MAX_CORRECT / correctionSpeed);
			}
			this.setDeltaMovement(deltaMovement.add(correction));
		}
	}

	@Override
	protected SoundEvent getDefaultHitGroundSoundEvent() {
		return SoundEvents.GLASS_BREAK;
	}

	@Override
	protected void onHitBlock(BlockHitResult hit) {
		super.onHitBlock(hit);
		this.discard();
	}

	@Override
	protected void onHitEntity(EntityHitResult hit) {
		Entity entity = hit.getEntity();

		Entity owner = this.getOwner();
		DamageSource damagesource = this.damageSources().arrow(this, owner == null ? this : owner);
		SoundEvent soundevent = SoundEvents.GLASS_BREAK;
		if (entity.hurt(damagesource, 1.0F)) {
			if (entity.getType() == EntityType.ENDERMAN) {
				this.discard();
				return;
			}

			if (entity instanceof LivingEntity livingEntity) {
				if (owner instanceof LivingEntity) {
					EnchantmentHelper.doPostHurtEffects(livingEntity, owner);
					EnchantmentHelper.doPostDamageEffects((LivingEntity)owner, livingEntity);
					entity.setYRot((entity.getYRot() + this.forceRotateAngle) % 360.0F);
				}

				this.doPostHurtEffects(livingEntity);
			}
		}

		this.playSound(soundevent, 1.0F, 1.0F);

		this.discard();
	}

	public float getForceRotateAngle() {
		return this.forceRotateAngle;
	}

	public void setForceRotateAngle(float forceRotateAngle) {
		this.forceRotateAngle = forceRotateAngle;
	}
}
