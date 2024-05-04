package com.thomas.shampoo.entity;

import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;
import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;

public class LaserAttackGoal extends Goal {
    private final Mob mob;
    private final Class<? extends LivingEntity> targetClass; // Single class
    private final Predicate<LivingEntity> additionalCriteria;
    private LivingEntity target;

    public LaserAttackGoal(Mob mob, Class<? extends LivingEntity> targetClass, Predicate<LivingEntity> customCriteria) {
        this.mob = mob;
        this.targetClass = targetClass;
        // New custom criteria
        this.setFlags(EnumSet.of(Flag.LOOK));
        // General criteria including not attacking creative or spectator mode players and custom criteria
        this.additionalCriteria = entity -> EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(entity) &&
                customCriteria.test(entity) && !(entity instanceof Unlaserable);
    }

    @Override
    public boolean canUse() {
        this.target = findNearestTarget();
        return this.target != null;
    }

    private LivingEntity findNearestTarget() {
        // Adjusted to check for the specific class and additional criteria
        List<? extends LivingEntity> potentialTargets = mob.getCommandSenderWorld().getEntitiesOfClass(targetClass,
                this.mob.getBoundingBox().inflate(10), additionalCriteria);
        return potentialTargets.isEmpty() ? null : potentialTargets.get(0);
    }

    @Override
    public void start() {
        // Called once when the goal is initialized
        this.mob.getLookControl().setLookAt(this.target, 30.0F, 30.0F);
    }

    @Override
    public void tick() {
        // Called every tick while the goal is active
        if (this.target != null) {
            this.mob.getLookControl().setLookAt(this.target, 30.0F, 30.0F);
            // Optionally trigger laser attack here if applicable
        }
    }
}
