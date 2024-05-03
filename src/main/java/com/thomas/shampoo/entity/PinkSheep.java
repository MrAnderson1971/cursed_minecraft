package com.thomas.shampoo.entity;

import com.thomas.shampoo.effect.EffectInit;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.AbstractGolem;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.DifficultyInstance;

import java.util.List;

public class PinkSheep extends Monster implements Unlaserable {
    private static final double BASE_SPEED = 0.6;

    public PinkSheep(EntityType<? extends Monster> type, Level worldIn) {
        super(type, worldIn);
        this.goalSelector.addGoal(1, new LaserAttackGoal(this, List.of(Player.class, Wolf.class, AbstractGolem.class)));
//        this.goalSelector.addGoal(2, new MaintainDistanceGoal(this, List.of(Player.class, Wolf.class, AbstractGolem.class), 10, 30,
//                BASE_SPEED, 2 * BASE_SPEED));
        this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, Player.class, 6, BASE_SPEED, 2 * BASE_SPEED));
        this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, Wolf.class, 6, BASE_SPEED, 2 * BASE_SPEED));
        this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, AbstractGolem.class, 6, BASE_SPEED, 2 * BASE_SPEED));
    }

    @Override
    public void tick() {
        super.tick();
        Level level = getCommandSenderWorld();
        if (!level.isClientSide) {
            DifficultyInstance difficulty = level.getCurrentDifficultyAt(this.blockPosition());
            int amplifier = Math.max(0, level.getDifficulty().getId() - 1); // Easy = 0, Normal = 1, Hard = 2
            this.addEffect(new MobEffectInstance(EffectInit.LASER.get(), 40, amplifier, false, false));
        }
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 20)
                .add(Attributes.MOVEMENT_SPEED, BASE_SPEED)
                .add(Attributes.FOLLOW_RANGE, 30.0D);
    }
}
