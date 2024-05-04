package com.thomas.shampoo.entity;

import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;

public class MaintainDistanceGoal extends Goal {
    protected final PathfinderMob mob;
    private final double normalSpeed;
    private final double backOffSpeed;
    @Nullable
    protected LivingEntity targetEntity;
    protected final float minDist;
    protected final float maxDist;
    @Nullable
    protected Path path;
    protected final PathNavigation navigation;
    protected final List<Class<? extends LivingEntity>> targetClasses;
    protected final Predicate<LivingEntity> targetPredicate;
    private final TargetingConditions targetingConditions;

    public MaintainDistanceGoal(PathfinderMob mob, List<Class<? extends LivingEntity>> targetClasses, float minDist, float maxDist,
                                double normalSpeed, double backOffSpeed, Predicate<LivingEntity> targetPredicate) {
        this.mob = mob;
        this.targetClasses = targetClasses;
        this.minDist = minDist;
        this.maxDist = maxDist;
        this.normalSpeed = normalSpeed;
        this.backOffSpeed = backOffSpeed;
        this.targetPredicate = targetPredicate;
        this.navigation = mob.getNavigation();
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        this.targetingConditions = TargetingConditions.forCombat().range((double)maxDist).selector(targetPredicate.and(
                entity -> targetClasses.stream().anyMatch(clazz -> clazz.isAssignableFrom(entity.getClass()))
        ));
    }

    public MaintainDistanceGoal(PathfinderMob mob, List<Class<? extends LivingEntity>> targetClasses, float minDist, float maxDist,
                                double normalSpeed, double backOffSpeed) {
        this(mob, targetClasses, minDist, maxDist, normalSpeed, backOffSpeed, EntitySelector.NO_CREATIVE_OR_SPECTATOR::test);
    }

    public boolean canUse() {
        this.targetEntity = this.mob.level().getNearestEntity(
                this.mob.level().getEntitiesOfClass(LivingEntity.class, this.mob.getBoundingBox().inflate(this.maxDist),
                        entity -> targetClasses.stream().anyMatch(clazz -> clazz.isAssignableFrom(entity.getClass())) && this.targetPredicate.test(entity)),
                this.targetingConditions, this.mob, this.mob.getX(), this.mob.getY(), this.mob.getZ());
        return this.targetEntity != null;
    }

    public boolean canContinueToUse() {
        return this.targetEntity != null && !this.navigation.isDone();
    }

    public void start() {
        this.moveTowardsIdealDistance();
    }

    public void stop() {
        this.targetEntity = null;
        this.navigation.stop();
    }

    public void tick() {
        if (this.mob.distanceToSqr(this.targetEntity) < this.minDist * this.minDist) {
            this.navigation.moveTo(this.path, this.backOffSpeed);
        } else if (this.mob.distanceToSqr(this.targetEntity) > this.maxDist * this.maxDist) {
            this.navigation.moveTo(this.path, this.normalSpeed);
        }
    }

    private void moveTowardsIdealDistance() {
        double idealDist = (this.minDist + this.maxDist) / 2.0;  // Ideal distance between mob and target
        // Attempt to find a random position towards the target but at the ideal distance
        Vec3 directionToTarget = this.targetEntity.position().subtract(this.mob.position()).normalize();
        Vec3 idealPosition = this.mob.position().add(directionToTarget.scale(idealDist));

        // Try to find a suitable path to that position
        Vec3 positionAway = DefaultRandomPos.getPosTowards(this.mob, 16, 7, idealPosition, 0);
        if (positionAway != null) {
            this.path = this.navigation.createPath(positionAway.x, positionAway.y, positionAway.z, 0);
            if (this.path != null) {
                this.navigation.moveTo(this.path, this.normalSpeed);
            }
        }
    }
}
