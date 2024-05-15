package com.thomas.shampoo.entity;

import com.thomas.shampoo.entity.ai.ArmstrongGoals;
import com.thomas.shampoo.entity.ai.ArmstrongNodeEvaluator;
import com.thomas.shampoo.entity.ai.ArmstrongPathNavigation;
import com.thomas.shampoo.network.PacketHandler;
import com.thomas.shampoo.network.SStopArmstrongMusicPacket;
import com.thomas.shampoo.world.ModSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.BossEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fluids.FluidType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class StevenArmstrong extends Monster {

    private static final float SIGHT_RANGE = 50.0F; // Increased sight range
    private static final float BASE_SPEED = 0.5F;
    private static final float MAX_HEALTH = 500;
    public static final float ATTACK_DAMAGE = 15.0F;
    private static final int MAX_COOLDOWN = 200;  // Cooldown time in ticks

    private double jumpCooldown;
    private final ServerBossEvent bossEvent = (ServerBossEvent) (new ServerBossEvent(this.getDisplayName(), BossEvent.BossBarColor.PURPLE, BossEvent.BossBarOverlay.PROGRESS)).setDarkenScreen(true);
    public static final Music ARMSTRONG_MUSIC = new Music(ModSounds.ARMSTRONG_MUSIC.getHolder().orElseThrow(), 100, 200, true);

    private boolean hasTriggeredStartState = false;
    private boolean hasTriggeredLowHealthState = false;

    private int attackCooldown;
    private boolean jumped;
    private boolean offGround;

    private Goal activeGoal;
    private final LookAtPlayerGoal lookAtPlayerGoal;
    private final MeleeAttackGoal meleeAttackGoal;
    private final RandomLookAroundGoal randomLookAroundGoal;
    private final WaterAvoidingRandomStrollGoal waterAvoidingRandomStrollGoal;
    private final HurtByTargetGoal hurtByTargetGoal;
    private final NearestAttackableTargetGoal<LivingEntity> nearestAttackableTargetGoal;
    private final List<Goal> armstrongGoals = List.of(new ArmstrongGoals.SonicBoomGoal(this),
            new ArmstrongGoals.SmokeExplosionGoal(this),
            new ArmstrongGoals.LavaTrapGoal(this));

    public AnimationState attackAnimationState = new AnimationState();
    public AnimationState sonicBoomAnimationState = new AnimationState();

    public StevenArmstrong(EntityType<? extends Monster> type, Level level) {
        super(type, level);

        lookAtPlayerGoal = new LookAtPlayerGoal(this, Player.class, 8.0F);
        meleeAttackGoal = new MeleeAttackGoal(this, 1.0D, true);
        randomLookAroundGoal = new RandomLookAroundGoal(this);
        waterAvoidingRandomStrollGoal = new WaterAvoidingRandomStrollGoal(this, 1.0D);
        hurtByTargetGoal = new HurtByTargetGoal(this);
        nearestAttackableTargetGoal = new NearestAttackableTargetGoal<>(this, LivingEntity.class, 0,
                false, false, e -> !(e instanceof StevenArmstrong || e instanceof Villager));

        // Initially, only the look at player goal is high priority
        this.goalSelector.addGoal(3, lookAtPlayerGoal);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, MAX_HEALTH)
                .add(Attributes.MOVEMENT_SPEED, BASE_SPEED)
                .add(Attributes.FOLLOW_RANGE, SIGHT_RANGE)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D)
                .add(Attributes.ATTACK_DAMAGE, ATTACK_DAMAGE);
    }

    @Override
    protected @NotNull PathNavigation createNavigation(@NotNull Level level) {
        return new ArmstrongPathNavigation(this, level) {
            @Override
            protected @NotNull PathFinder createPathFinder(int range) {
                nodeEvaluator = new ArmstrongNodeEvaluator();
                return new PathFinder(this.nodeEvaluator, range);
            }
        };
    }

    @Override
    public void handleEntityEvent(byte eventCode) {
        switch (eventCode) {
            case 4:
                attackAnimationState.start(tickCount);
                break;
            case 62:
                attackAnimationState.stop();
                sonicBoomAnimationState.start(tickCount);
                break;
            default:
                super.handleEntityEvent(eventCode);
        }
    }

    @Override
    public void aiStep() {
        super.aiStep();
        // Check health percentage
        double healthPercent = this.getHealth() / this.getMaxHealth();

        if (healthPercent > 0.9) {
            // If health is above 90%, reset the low health state trigger if it was set
            if (hasTriggeredStartState) {
                hasTriggeredStartState = false;
            }
            // If health is above 90%, focus on looking at the player
            ensureGoalActive(goalSelector, lookAtPlayerGoal, 3);
            removeGoalIfActive(goalSelector, meleeAttackGoal);
            removeGoalIfActive(goalSelector, randomLookAroundGoal);
            removeGoalIfActive(goalSelector, waterAvoidingRandomStrollGoal);
            removeGoalIfActive(targetSelector, hurtByTargetGoal);
            removeGoalIfActive(targetSelector, nearestAttackableTargetGoal);
        } else if (!hasTriggeredStartState) {
            hasTriggeredStartState = true;
            playSound(ModSounds.ARMSTRONG_EMERGE.get(), 10.0F, 1.0F);
            // If health drops below 90%, activate all goals
            removeGoalIfActive(goalSelector, lookAtPlayerGoal);
            ensureGoalActive(goalSelector, meleeAttackGoal, 8);
            ensureGoalActive(goalSelector, randomLookAroundGoal, 8);
            ensureGoalActive(goalSelector, waterAvoidingRandomStrollGoal, 7);
            ensureGoalActive(goalSelector, lookAtPlayerGoal, 8);
            ensureGoalActive(targetSelector, hurtByTargetGoal, 3);
            ensureGoalActive(targetSelector, nearestAttackableTargetGoal, 2);
        }

        if (healthPercent < 0.5) {
            if (!hasTriggeredLowHealthState) {
                hasTriggeredLowHealthState = true;
                playSound(ModSounds.ARMSTRONG_DIG.get(), 10.0F, 1.0F);
            }
            // Apply Regeneration II and Resistance I if below 50% health
            this.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 40, 1, false, false, true));
            this.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 40, 0, false, false, true));
        }

        // Decrement the jump cooldown if it's above 0
        if (this.jumpCooldown > 0) {
            this.jumpCooldown--;
        }

        LivingEntity target = this.getTarget();
        if (target != null && this.onGround() && this.jumpCooldown == 0 && shouldJumpToTarget(target)) {
            this.jumpTowardsTarget(target);  // Jump towards the target
            this.jumpCooldown = 100;  // Reset cooldown
        }

        // Play boss music if not playing already
        if (!Minecraft.getInstance().getMusicManager().isPlayingMusic(ARMSTRONG_MUSIC) && isAlive()) {
            Minecraft.getInstance().getMusicManager().startPlaying(ARMSTRONG_MUSIC);
        }

        if (attackCooldown > 0) {
            attackCooldown--;
        }

        if (attackCooldown <= 0 && this.getTarget() != null && activeGoal == null) {
            int choice = random.nextInt(armstrongGoals.size());
            Goal candidateGoal = armstrongGoals.get(choice);

            if (candidateGoal.canUse()) {
                activeGoal = candidateGoal;
                activeGoal.start();
                attackCooldown = MAX_COOLDOWN;  // Reset cooldown
                level().broadcastEntityEvent(this, (byte) 62);
            }
        }

        if (activeGoal != null) {
            if (activeGoal.canContinueToUse()) {
                activeGoal.tick();
            } else {
                activeGoal.stop();
                activeGoal = null;  // Reset the active goal after stopping
            }
        }

        if (jumped && !onGround()) {
            offGround = true;
        }

        if (this.onGround() && offGround) {
            jumped = false; // Reset the jump flag
            offGround = false;
            triggerGroundPound();
        }
    }

    private void triggerGroundPound() {
        if (this.getHealth() <= this.getMaxHealth() * 0.5) {
            // Radius within which the effect is applied
            double radius = 5.0;
            List<LivingEntity> nearbyEntities = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(radius), e -> e != this && e.isAlive());
            double radiusSquared = radius * radius;  // Calculate square of radius to use in comparison

            // Filter entities to include only those within the spherical distance
            nearbyEntities = nearbyEntities.stream()
                    .filter(e -> e.position().distanceToSqr(this.position()) <= radiusSquared
                            && EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(e))
                    .toList();

            // Apply a knock-up effect and show particles
            for (LivingEntity entity : nearbyEntities) {
                double upForce = 1.5;
                entity.setDeltaMovement(entity.getDeltaMovement().add(0, upForce, 0));
                entity.hurtMarked = true;
            }

            // Display particles in a circle around the mob
            displaySweepParticles();
        }
    }

    private void displaySweepParticles() {
        double radius = 5.0;
        if (level() instanceof ServerLevel sl) {
            for (int i = 0; i < 360; i += 10) {
                double rad = Math.toRadians(i);
                double x = this.getX() + radius * Math.cos(rad);
                double z = this.getZ() + radius * Math.sin(rad);
                sl.sendParticles(ParticleTypes.SWEEP_ATTACK, x, this.getY(), z, 10, 0.1, 0.1, 0.1, 0);
            }
        }
    }

    private void ensureGoalActive(GoalSelector selector, Goal goal, int priority) {
        if (!isGoalActive(selector, goal)) {
            selector.addGoal(priority, goal);
        }
    }

    private void removeGoalIfActive(GoalSelector selector, Goal goal) {
        if (isGoalActive(selector, goal)) {
            selector.removeGoal(goal);
        }
    }

    private boolean isGoalActive(GoalSelector selector, Goal goal) {
        return selector.getAvailableGoals().stream()
                .anyMatch(wrappedGoal -> wrappedGoal.getGoal() == goal);
    }

    private boolean shouldJumpToTarget(LivingEntity target) {
        // Check if the target is within a straight line but on a different elevation
        double dx = Math.abs(this.getX() - target.getX());
        double dz = Math.abs(this.getZ() - target.getZ());
        double dy = target.getY() - this.getY();
        return dx <= 5 && dz <= 5 && dy > 1 && dy < 4;  // Example condition, adjust as needed
    }

    private void jumpTowardsTarget(LivingEntity target) {
        if (this.isAlive()) {
            Vec3 direction = new Vec3(target.getX() - this.getX(), 0, target.getZ() - this.getZ()).normalize();
            double horizontalSpeedFactor = 0.8;
            double verticalSpeedFactor = 0.9;
            this.setDeltaMovement(direction.x * horizontalSpeedFactor, verticalSpeedFactor, direction.z * horizontalSpeedFactor);
            this.hasImpulse = true;
            jumped = true;
        }
    }

    @Override
    public void die(@NotNull DamageSource cause) {
        super.die(cause);

        if (!level().isClientSide) {  // Ensure this runs on the server side
            // Check if there are any other entities of this type still alive
            boolean anyAlive = level().getEntitiesOfClass(StevenArmstrong.class, this.getBoundingBox().inflate(10000)).stream()
                    .anyMatch(e -> e.isAlive() && e != this);

            if (!anyAlive) {
                // Inform the client to stop the music, typically through a custom packet or event
                PacketHandler.sendToServer(new SStopArmstrongMusicPacket());
            }
        }
    }

    @Override
    protected void customServerAiStep() {
        this.bossEvent.setProgress(this.getHealth() / this.getMaxHealth());
    }

    @Override
    public void startSeenByPlayer(@NotNull ServerPlayer player) {
        super.startSeenByPlayer(player);
        this.bossEvent.addPlayer(player);
    }

    @Override
    public void stopSeenByPlayer(@NotNull ServerPlayer player) {
        super.stopSeenByPlayer(player);
        this.bossEvent.removePlayer(player);
    }

    @Override
    protected void populateDefaultEquipmentSlots(@NotNull RandomSource r, @NotNull DifficultyInstance d) {
        // Create an ItemStack for leather boots
        ItemStack boots = new ItemStack(Items.LEATHER_BOOTS);

        // Apply the Depth Strider III enchantment to the boots
        boots.enchant(Enchantments.DEPTH_STRIDER, 3);

        // Set the enchanted boots to the mob's feet slot
        setItemSlot(EquipmentSlot.FEET, boots);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.ARMSTRONG_ROAR.get();
    }

    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource d) {
        return ModSounds.ARMSTRONG_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.ARMSTRONG_DEATH.get();
    }

    @Override
    protected void playStepSound(@NotNull BlockPos p, @NotNull BlockState s) {
        this.playSound(ModSounds.ARMSTRONG_STEP.get(), 10.0F, 1.0F);
    }

    @Override
    public boolean doHurtTarget(@NotNull Entity target) {
        level().broadcastEntityEvent(this, (byte) 4);
        playSound(ModSounds.ARMSTRONG_ATTACK_IMPACT.get(), 10.0F, getVoicePitch());
        return super.doHurtTarget(target);
    }

    @Override
    public boolean removeWhenFarAway(double d) {
        return false;
    }

    @Override
    public boolean isPushedByFluid(FluidType fluidType) {
        return false;
    }
}
