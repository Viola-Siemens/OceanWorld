package com.hexagram2021.oceanworld.common.entities.goals;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public class RideBoatGoal extends Goal {
	private static final float MAX_ROTATE_DEGREE = 1.2F;

	private final PathfinderMob mob;
	@Nullable
	private Vec3 movePos = null;

	public RideBoatGoal(PathfinderMob mob) {
		this.mob = mob;
	}

	@Override
	public boolean canUse() {
		return !this.mob.isVehicle() && this.mob.getControlledVehicle() instanceof Boat;
	}

	@Override
	public boolean requiresUpdateEveryTick() {
		return true;
	}

	@Override
	public boolean canContinueToUse() {
		return !this.mob.getNavigation().isDone() && !this.mob.isVehicle() && this.mob.getControlledVehicle() instanceof Boat;
	}

	@Override
	public void tick() {
		super.tick();
		if(this.mob.getControlledVehicle() instanceof Boat boat) {
			//Near Land?
			Vec3 defaultDismountLocation = new Vec3(boat.getX(), boat.getBoundingBox().maxY, boat.getZ());
			if (boat.getDismountLocationForPassenger(this.mob).distanceTo(defaultDismountLocation) >= 0.25D) {
				//Dismount
				this.mob.stopRiding();
				boat.hurt(this.mob.damageSources().mobAttack(this.mob), 10.0F);
				return;
			}

			if(this.movePos == null) {
				if (this.mob.getTarget() == null) {
					//Randomly ride
					this.movePos = this.getPosition();
				} else {
					//Move to target
					this.movePos = this.mob.getTarget().position();
				}
				if(this.movePos == null) {
					return;
				}
			}
			Vec3 deltaMove = this.movePos.subtract(this.mob.position()).multiply(1.0D, 0.0D, 1.0D);
			float targetYRot = (float)Mth.atan2(deltaMove.z, deltaMove.x) * Mth.RAD_TO_DEG - 90.0F;
			float diff = Mth.degreesDifference(boat.getYRot(), targetYRot);
			if(Mth.abs(diff) < MAX_ROTATE_DEGREE) {
				boat.setYRot(targetYRot);
			} else {
				float newYRot = Mth.wrapDegrees(boat.getYRot() + Mth.sign(diff) * MAX_ROTATE_DEGREE);
				boat.setYRot(newYRot);
			}
			float movementSpeed;
			if(Mth.abs(diff) > 90.0F) {
				movementSpeed = -0.008F;
			} else if(Mth.abs(diff) > 60.0F) {
				movementSpeed = 0.0F;
			} else if(deltaMove.length() > 5.0D) {
				movementSpeed = 0.06F;
			} else if(deltaMove.length() > 2.0D) {
				movementSpeed = 0.04F;
			} else {
				movementSpeed = 0.0F;
				if(this.mob.getTarget() == null) {
					this.movePos = this.getPosition();
				}
			}
			boat.setDeltaMovement(boat.getDeltaMovement().add(
					Mth.sin(-boat.getYRot() * Mth.DEG_TO_RAD) * movementSpeed,
					0.0D,
					Mth.cos(boat.getYRot() * Mth.DEG_TO_RAD) * movementSpeed
			));
		}
	}

	@Nullable
	protected Vec3 getPosition() {
		return this.mob.position().add(
				(this.mob.getRandom().nextDouble() * 10.0D + this.mob.getRandom().nextDouble() * 10.0D) * (this.mob.getRandom().nextBoolean() ? 1 : -1),
				0.0D,
				(this.mob.getRandom().nextDouble() * 10.0D + this.mob.getRandom().nextDouble() * 10.0D) * (this.mob.getRandom().nextBoolean() ? 1 : -1)
		);
	}
}
