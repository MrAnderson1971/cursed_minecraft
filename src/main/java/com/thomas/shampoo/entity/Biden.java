package com.thomas.shampoo.entity;

import com.thomas.shampoo.effect.EffectInit;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.AbstractGolem;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.DifficultyInstance;
import org.jetbrains.annotations.NotNull;

public class Biden extends AbstractGolem implements Unlaserable, RangedAttackMob {
    private static final double BASE_SPEED = 0.6;

    public Biden(EntityType<? extends AbstractGolem> type, Level worldIn) {
        super(type, worldIn);
        this.goalSelector.addGoal(2, new LaserAttackGoal(this, Mob.class, (e) -> e instanceof Enemy || e instanceof Player));
        this.goalSelector.addGoal(2, new RangedAttackGoal(this, BASE_SPEED, 1, 20, 16));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Mob.class, 10,
                true, false, (e) -> e instanceof Enemy || e instanceof Player));
        this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, Mob.class, 8.0F, BASE_SPEED,
                2 * BASE_SPEED, (e) -> e instanceof Enemy || e instanceof Player && EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(e)));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 20)
                .add(Attributes.MOVEMENT_SPEED, BASE_SPEED)
                .add(Attributes.FOLLOW_RANGE, 30.0D);
    }

    @Override
    public float getStandingEyeHeight(@NotNull Pose p_21131_, @NotNull EntityDimensions p_21132_){
        return 1.8F;
    }

    @Override
    public void performRangedAttack(@NotNull LivingEntity p_33317_, float p_33318_) {
        Level level = getCommandSenderWorld();
        if (!level.isClientSide) {
            DifficultyInstance difficulty = level.getCurrentDifficultyAt(this.blockPosition());
            int amplifier = Math.max(0, level.getDifficulty().getId() - 1); // Easy = 0, Normal = 1, Hard = 2
            this.addEffect(new MobEffectInstance(EffectInit.LASER.get(), 40, amplifier, false, false));
        }
    }
}
