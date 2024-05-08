package com.thomas.shampoo.entity;

import com.thomas.shampoo.network.PacketHandler;
import com.thomas.shampoo.network.SStopArmstrongMusicPacket;
import com.thomas.shampoo.world.ArmstrongNodeEvaluator;
import com.thomas.shampoo.world.ModSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.behavior.warden.SonicBoom;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class StevenArmstrong extends Monster {

    private static final float SIGHT_RANGE = 50.0F; // Increased sight range
    private double jumpCooldown;
    private final ServerBossEvent bossEvent = (ServerBossEvent)(new ServerBossEvent(this.getDisplayName(), BossEvent.BossBarColor.PURPLE, BossEvent.BossBarOverlay.PROGRESS)).setDarkenScreen(true);
    public static final Music ARMSTRONG_MUSIC = new Music(ModSounds.ARMSTRONG_MUSIC.getHolder().orElseThrow(), 100, 200, true);;

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

        // Play boss music if not playing already
        if (!Minecraft.getInstance().getMusicManager().isPlayingMusic(ARMSTRONG_MUSIC) && isAlive()) {
            Minecraft.getInstance().getMusicManager().startPlaying(ARMSTRONG_MUSIC);
        }
    }

    @Override
    public void die(DamageSource cause) {
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

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.ARMSTRONG_ROAR.get();
    }

    protected SoundEvent getHurtSound(DamageSource p_219440_) {
        return ModSounds.ARMSTRONG_HURT.get();
    }

    protected SoundEvent getDeathSound() {
        return ModSounds.ARMSTRONG_DEATH.get();
    }

    protected void playStepSound(BlockPos p_219431_, BlockState p_219432_) {
        this.playSound(ModSounds.ARMSTRONG_STEP.get(), 10.0F, 1.0F);
    }

    public boolean doHurtTarget(Entity p_219472_) {
        this.level().broadcastEntityEvent(this, (byte)4);
        this.playSound(ModSounds.ARMSTRONG_ATTACK_IMPACT.get(), 10.0F, this.getVoicePitch());
        SonicBoom.setCooldown(this, 40);
        return super.doHurtTarget(p_219472_);
    }

    // Additional methods for sonic boom, etc., would be implemented here.
}
