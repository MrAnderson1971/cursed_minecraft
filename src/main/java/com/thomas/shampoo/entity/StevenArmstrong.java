package com.thomas.shampoo.entity;

import com.thomas.shampoo.entity.ai.ArmstrongNodeEvaluator;
import com.thomas.shampoo.entity.ai.ArmstrongPathNavigation;
import com.thomas.shampoo.network.PacketHandler;
import com.thomas.shampoo.network.SStopArmstrongMusicPacket;
import com.thomas.shampoo.world.ModSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.BossEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.behavior.warden.SonicBoom;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
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
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Mob.class, 0,
                false, false, e -> !(e instanceof StevenArmstrong)));
        this.goalSelector.addGoal(3, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
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
    public void aiStep() {
        super.aiStep();

        // Decrement the jump cooldown if it's above 0
        if (this.jumpCooldown > 0) {
            this.jumpCooldown--;
        }

        LivingEntity target = this.getTarget();
        if (target != null && this.onGround() && this.jumpCooldown == 0 && shouldJumpToTarget(target)) {
            this.jump();  // Jump towards the target
            this.jumpCooldown = 100;  // Reset cooldown
        }

        // Play boss music if not playing already
        if (!Minecraft.getInstance().getMusicManager().isPlayingMusic(ARMSTRONG_MUSIC) && isAlive()) {
            Minecraft.getInstance().getMusicManager().startPlaying(ARMSTRONG_MUSIC);
        }
    }

    private boolean shouldJumpToTarget(LivingEntity target) {
        // Check if the target is within a straight line but on a different elevation
        double dx = Math.abs(this.getX() - target.getX());
        double dz = Math.abs(this.getZ() - target.getZ());
        double dy = target.getY() - this.getY();
        return dx <= 5 && dz <= 5 && dy > 1 && dy < 4;  // Example condition, adjust as needed
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

    private void jump() {
        if (this.isAlive()) {
            Vec3 motion = this.getDeltaMovement();
            this.setDeltaMovement(motion.x, 1, motion.z);  // You can adjust the 0.5 to change the jump strength
            this.hasImpulse = true;
        }
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
