package com.thomas.shampoo.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.entity.item.PrimedTnt;

public class Obama extends Monster implements RangedAttackMob, Unfireballable {
    private static final double BASE_SPEED = 0.6;

    public Obama(EntityType<? extends Monster> type, Level worldIn) {
        super(type, worldIn);
        this.goalSelector.addGoal(2, new RangedAttackGoal(this, BASE_SPEED, 1, 20, 16));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Mob.class, 10,
                true, false, e -> !(e instanceof Obama) && e instanceof Enemy));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, e -> true));
        this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, Mob.class, 10.0F, BASE_SPEED,
                2 * BASE_SPEED, (e) -> Obama.selection(e) && EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(e)));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
    }

    private static boolean selection(Entity e) {
        return !(e instanceof Obama) && (e instanceof Enemy || e instanceof Player);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 20)
                .add(Attributes.MOVEMENT_SPEED, BASE_SPEED)
                .add(Attributes.FOLLOW_RANGE, 30.0D);
    }

    @Override
    public void performRangedAttack(LivingEntity target, float distanceFactor) {
        Level level = level();

        // Calculate direction towards target
        double d0 = target.getX() - getX();
        double d1 = target.getY(0.5) - getEyeY(); // Aim directly at the target's eyes for better accuracy
        double d2 = target.getZ() - getZ();
        double dist = Math.sqrt(d0 * d0 + d2 * d2);

        // Calculate how hard the fireball should be thrown
        double accelX = d0 / dist * 0.3; // You may want to adjust the multiplier based on desired speed
        double accelY = d1 / dist * 0.3; // Direct line aim since fireballs are not affected by gravity
        double accelZ = d2 / dist * 0.3;

        // Create the CustomFireball entity
        CustomFireball fireball = new CustomFireball(level, this, accelX, accelY, accelZ);

        // Set the fireball's position slightly above and in front of the owner to prevent collision with the owner
        fireball.setPos(getX() + d0 / dist * 0.5, getEyeY(), getZ() + d2 / dist * 0.5);

        // Add the fireball to the world
        level.addFreshEntity(fireball);
    }
}
