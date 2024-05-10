package com.thomas.shampoo.entity.ai;

import com.thomas.shampoo.entity.StevenArmstrong;
import com.thomas.shampoo.world.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class ArmstrongGoals {
    public static class SmokeExplosionGoal extends Goal {
        private final StevenArmstrong mob;
        private int timer;
        private BlockPos targetPos;
        private boolean using;

        public SmokeExplosionGoal(StevenArmstrong mob) {
            this.mob = mob;
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            LivingEntity target = mob.getTarget();
            return target != null && mob.distanceToSqr(target) <= 225 && mob.distanceToSqr(target) > 16;
        }

        @Override
        public void start() {
            mob.playSound(ModSounds.ARMSTRONG_AGITATED.get(),3.0F, 1.0F);
            this.targetPos = mob.getTarget().blockPosition();
            this.timer = 20; // 1 second until explosion
            using = true;
            Level world = mob.level();
            spawnInitialParticles(world, mob.getEyePosition());
        }

        @Override
        public boolean canContinueToUse() {
            return using;
        }

        private void spawnInitialParticles(Level world, Vec3 position) {
            if (world instanceof ServerLevel serverLevel) {
                double d0 = Math.sin(mob.getYRot() * (Math.PI / 180));
                double d1 = Math.cos(mob.getYRot() * (Math.PI / 180));
                for (int i = 0; i < 30; i++) {
                    serverLevel.sendParticles(ParticleTypes.SMOKE, position.x + d1 * i * 0.1, position.y, position.z + d0 * i * 0.1, 1, 0.0, 0.05, 0.0, 0.01);
                    serverLevel.sendParticles(ParticleTypes.ANGRY_VILLAGER, position.x + d1 * i * 0.1, position.y, position.z + d0 * i * 0.1, 1, 0.0, 0.0, 0.0, 0.01);
                }
            }
        }

        @Override
        public void tick() {
            if (mob.level() instanceof ServerLevel sl) {
                sl.sendParticles(ParticleTypes.SMOKE, targetPos.getX(), targetPos.getY(), targetPos.getZ(), 1, 0.0, 0.05, 0.0, 0.01);
            }
            if (timer-- <= 0) {
                Level world = mob.level();
                world.explode(mob, targetPos.getX(), targetPos.getY(), targetPos.getZ(), 4.0F, Level.ExplosionInteraction.MOB);
                using = false;
            }
        }
    }

    public static class LavaTrapGoal extends Goal {
        private final StevenArmstrong mob;

        public LavaTrapGoal(StevenArmstrong mob) {
            this.mob = mob;
        }

        @Override
        public boolean canUse() {
            LivingEntity target = mob.getTarget();
            if (target == null) {
                return false;
            }
            BlockPos pos = target.blockPosition().below();
            return mob.level().getBlockState(pos).isSolidRender(mob.level(), pos)
                    && mob.distanceToSqr(target) <= 225 && mob.distanceToSqr(target) > 16
                    && !mob.fireImmune();
        }

        @Override
        public boolean canContinueToUse() {
            return false;
        }

        @Override
        public void start() {
            LivingEntity target = mob.getTarget();
            if (target != null && mob.level() instanceof ServerLevel serverLevel) {
                mob.playSound(ModSounds.ARMSTRONG_ANGRY.get(),3.0F, 1.0F);
                BlockPos centerPos = target.blockPosition();  // The block directly on the target

                BlockState flowingLava = Blocks.LAVA.defaultBlockState().setValue(LiquidBlock.LEVEL, 9); // Example level
                serverLevel.setBlockAndUpdate(centerPos, flowingLava);

                // Spawn particles to visually indicate the lava placement
                spawnParticles(serverLevel, mob.getEyePosition());
            }
        }

        private void spawnParticles(Level world, Vec3 position) {
            if (world instanceof ServerLevel serverLevel) {
                double d0 = Math.sin(mob.getYRot() * (Math.PI / 180));
                double d1 = Math.cos(mob.getYRot() * (Math.PI / 180));
                for (int i = 0; i < 30; i++) {
                    serverLevel.sendParticles(ParticleTypes.FLAME, position.x + d1 * i * 0.1, position.y, position.z + d0 * i * 0.1, 1, 0.0, 0.05, 0.0, 0.01);
                    serverLevel.sendParticles(ParticleTypes.LAVA, position.x + d1 * i * 0.1, position.y, position.z + d0 * i * 0.1, 1, 0.0, 0.0, 0.0, 0.01);
                }
            }
        }
    }

    public static class SonicBoomGoal extends Goal {
        private final StevenArmstrong mob;

        public SonicBoomGoal(StevenArmstrong mob) {
            this.mob = mob;
            this.setFlags(EnumSet.of(Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            LivingEntity target = mob.getTarget();
            if (target == null) {
                return false;
            }
            return mob.distanceToSqr(target) <= 225 && mob.distanceToSqr(target) > 16; // Effective within 15 blocks and not too close
        }

        @Override
        public boolean canContinueToUse() {
            return false;
        }

        @Override
        public void start() {
            LivingEntity target = mob.getTarget();
            if (mob.level() instanceof ServerLevel serverLevel && target != null) {
                mob.playSound(ModSounds.ARMSTRONG_SONIC.get(), 3.0F, 1.0F);
                Vec3 from = mob.position().add(0.0D, mob.getEyeHeight(), 0.0D);
                Vec3 to = target.position();
                Vec3 direction = to.subtract(from).normalize();

                // Emit particles along the path
                for (int i = 0; i < Mth.floor(from.distanceTo(to)) + 7; ++i) {
                    Vec3 point = from.add(direction.scale(i));
                    serverLevel.sendParticles(ParticleTypes.SONIC_BOOM, point.x, point.y, point.z, 1, 0.0D, 0.0D, 0.0D, 0.0D);
                }

                float damage = 10.0F;
                target.hurt(serverLevel.damageSources().sonicBoom(mob), damage);
                target.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 200, 0)); // Apply weakness for 10 seconds

                double attractionHorizontal = 2.5;
                double dx = -direction.x * attractionHorizontal * (1.0 - target.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
                double attractionVertical = 0.5;
                double dy = -direction.y * attractionVertical * (1.0 - target.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
                double dz = -direction.z * attractionHorizontal * (1.0 - target.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
                target.push(dx, dy, dz); // Pull the target towards the mob
            }
        }
    }
}
