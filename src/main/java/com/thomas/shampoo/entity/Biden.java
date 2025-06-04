package com.thomas.shampoo.entity;

import com.thomas.shampoo.effect.EffectInit;
import com.thomas.shampoo.entity.ai.LaserAttackGoal;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Phantom;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class Biden extends Monster implements Unlaserable, RangedAttackMob {
    private static final double BASE_SPEED = 0.6;
    private static final int SUMMON_INTERVAL = 200; // Time in ticks (10 seconds at 20 ticks per second)

    private int summonCooldown = 0; // Tracks cooldown time for summoning phantoms

    public Biden(EntityType<? extends Monster> type, Level worldIn) {
        super(type, worldIn);
        this.goalSelector.addGoal(2, new LaserAttackGoal(this, Mob.class, Biden::selection));
        this.goalSelector.addGoal(2, new RangedAttackGoal(this, BASE_SPEED, 1, 20, 16));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Mob.class, 10,
                true, false, e -> !(e instanceof Unlaserable) && e instanceof Enemy));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, e -> true));
        this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, Mob.class, 8.0F, BASE_SPEED,
                2 * BASE_SPEED, (e) -> Biden.selection(e) && EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(e)));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
    }

    private static boolean selection(Entity e) {
        return !(e instanceof Unlaserable || e instanceof Phantom) && (e instanceof Enemy || e instanceof Player);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 20)
                .add(Attributes.MOVEMENT_SPEED, BASE_SPEED)
                .add(Attributes.FOLLOW_RANGE, 30.0D);
    }

    @Override
    public float getStandingEyeHeight(@NotNull Pose p_21131_, @NotNull EntityDimensions p_21132_) {
        return 1.8F;
    }

    @Override
    public void performRangedAttack(@NotNull LivingEntity p_33317_, float p_33318_) {
        Level level = getCommandSenderWorld();
        if (!level.isClientSide) {
            int amplifier = Math.max(0, level.getDifficulty().getId() - 1); // Easy = 0, Normal = 1, Hard = 2
            this.addEffect(new MobEffectInstance(EffectInit.LASER.get(), 40, amplifier, false, false));
        }
    }

    @Override
    public void tick() {
        super.tick();

        if (!level().isClientSide) {
            // Increment cooldown
            summonCooldown++;

            // Check if it's time to summon phantoms
            if (summonCooldown >= SUMMON_INTERVAL) {
                summonCooldown = random.nextInt(SUMMON_INTERVAL / 2); // Reset cooldown

                // Summon 1-3 phantoms randomly
                int phantomCount = 1 + random.nextInt(3);
                for (int i = 0; i < phantomCount; i++) {
                    summonPhantom();
                }
            }
        }
    }

    private void summonPhantom() {
        // Create a new Phantom entity
        Phantom phantom = EntityType.PHANTOM.create(level());
        if (phantom != null) {
            // Position the phantom near Biden
            Vec3 spawnPosition = this.position().add(random.nextGaussian() * 2, 1, random.nextGaussian() * 2);
            phantom.moveTo(spawnPosition.x, spawnPosition.y, spawnPosition.z, random.nextFloat() * 360, 0);

            // Spawn the phantom in the world
            level().addFreshEntity(phantom);
        }
    }
}
