package com.thomas.shampoo.entity;

import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;
import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;

public class LaserAttackGoal extends Goal {
    private final Monster mob;
    private final Predicate<LivingEntity> targetPredicate;
    private final Predicate<LivingEntity> additionalCriteria;
    private LivingEntity target;

    public LaserAttackGoal(Monster mob, List<Class<? extends LivingEntity>> targetClasses) {
        this.mob = mob;
        this.setFlags(EnumSet.of(Flag.LOOK));
        this.targetPredicate = entity -> targetClasses.stream().anyMatch(clazz -> clazz.isAssignableFrom(entity.getClass()));
        this.additionalCriteria = entity -> EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(entity) && !(entity instanceof Unlaserable);
    }

    @Override
    public boolean canUse() {
        this.target = findNearestTarget();
        return this.target != null && additionalCriteria.test(target);
    }

    private LivingEntity findNearestTarget() {
        List<LivingEntity> potentialTargets = mob.getCommandSenderWorld().getEntitiesOfClass(LivingEntity.class, this.mob.getBoundingBox().inflate(10), targetPredicate.and(additionalCriteria));
        return potentialTargets.isEmpty() ? null : potentialTargets.get(0);
    }

    @Override
    public void start() {
        this.mob.getLookControl().setLookAt(this.target, 30.0F, 30.0F);
    }

    @Override
    public void tick() {
        if (this.target != null) {
            this.mob.getLookControl().setLookAt(this.target, 30.0F, 30.0F);
            // Trigger laser attack here if applicable
        }
    }
}
