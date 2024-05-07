package com.thomas.shampoo.entity;

import com.thomas.shampoo.world.ArmstrongNodeEvaluator;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.server.level.ServerPlayer;

import javax.annotation.Nullable;

import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import static com.thomas.shampoo.world.ModSounds.ARMSTRONG_MUSIC;

public class StevenArmstrong extends Warden {

    private static final float SIGHT_RANGE = 50.0F; // Increased sight range
    private double jumpCooldown;
    private final ServerBossEvent bossEvent = (ServerBossEvent)(new ServerBossEvent(this.getDisplayName(), BossEvent.BossBarColor.PURPLE, BossEvent.BossBarOverlay.PROGRESS)).setDarkenScreen(true);
    private boolean isPlayingSound = false;

    public StevenArmstrong(EntityType<? extends Monster> type, Level level) {
        super(type, level);
        this.getAttribute(Attributes.FOLLOW_RANGE).setBaseValue(SIGHT_RANGE);
    }

    @Override
    protected PathNavigation createNavigation(Level level) {
        return new GroundPathNavigation(this, level) {
            @Override
            protected PathFinder createPathFinder(int range) {
                nodeEvaluator = new ArmstrongNodeEvaluator();
                return new PathFinder(this.nodeEvaluator, range);
            }
        };
    }

    @Override
    public void aiStep() {
        super.aiStep();
        // Decrement the jump cooldown if it's above 0
        if (this.jumpCooldown > 0) {
            this.jumpCooldown--;
        }

        // Check conditions for jumping
        if (this.onGround() && this.jumpCooldown == 0) {
            this.jump();  // This triggers the jump
            this.jumpCooldown = 100;  // Reset cooldown to prevent frequent jumping
        }

        // Play sound only if not already playing
        if (!isPlayingSound) {
            this.level().playSound(null, this.blockPosition(), ARMSTRONG_MUSIC.get(), SoundSource.NEUTRAL, 1.0F, 1.0F);
            isPlayingSound = true;
        }
    }

    @Override
    public void die(DamageSource cause) {
        super.die(cause);
        // Stop the sound when the mob dies
        isPlayingSound = false;
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

    private void jump() {
        if (this.isAlive()) {
            Vec3 motion = this.getDeltaMovement();
            this.setDeltaMovement(motion.x, 1, motion.z);  // You can adjust the 0.5 to change the jump strength
            this.hasImpulse = true;
        }
    }

    //
//    @Override
//    protected void checkAndPerformAttack(LivingEntity enemy, double distToEnemySqr) {
//        super.checkAndPerformAttack(enemy, distToEnemySqr);
//        if (distToEnemySqr < getAttackReachSqr(enemy) && this.getAttackCooldown() <= 0) {
//            this.performMeleeAttack(enemy); // Perform melee attack
//        } else if (distToEnemySqr <= 400.0D) { // Range for sonic boom
//            this.performSonicBoomAttack(enemy);
//        }
//    }
//
    @Override
    public void move(MoverType type, Vec3 movement) {
        if (this.jumpCooldown > 0) {
            // This controls the entity while it's in the jump cooldown phase
            movement = movement.add(0, 0.1, 0);  // Increase upward motion slightly
        }
        super.move(type, movement);
    }

    // Additional methods for sonic boom, etc., would be implemented here.
}
