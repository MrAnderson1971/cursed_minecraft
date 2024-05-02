package com.thomas.shampoo.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import java.util.List;
import java.util.Optional;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;

public class LaserEffect extends MobEffect {

    public static final int LASER_MAX_RANGE = 50;

    public LaserEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        Level level = entity.getCommandSenderWorld();
        if (!level.isClientSide()) {
            Vec3 start = entity.getEyePosition(1.0F);
            Vec3 look = entity.getViewVector(1.0F);
            Vec3 end = start.add(look.scale(LASER_MAX_RANGE)); // Range of the laser

            // Perform a ray trace to check for block collisions
            ClipContext blockContext = new ClipContext(start, end, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, entity);
            BlockHitResult blockHitResult = level.clip(blockContext);

            // Adjust the end of the ray to the collision point if a block is hit
            if (blockHitResult.getType() != HitResult.Type.MISS) {
                end = blockHitResult.getLocation();
            }

            // Define the area to check for entities within the reach of the laser
            AABB aabb = new AABB(start, end).inflate(1.0); // Slightly enlarge the search area for entities
            List<Entity> entities = level.getEntities(entity, aabb, e -> e.isAlive() && !entity.equals(e) && e instanceof LivingEntity);
            double minDistance = Double.MAX_VALUE;
            Entity closestEntity = null;

            // Check entities in the path
            for (Entity target : entities) {
                Optional<Vec3> optional = target.getBoundingBox().clip(start, end);
                if (optional.isPresent()) {
                    double distance = start.distanceTo(optional.get());
                    if (distance < minDistance) {
                        closestEntity = target;
                        minDistance = distance;
                    }
                }
            }

            // If an entity is found and is closer than any block collision, trigger effects
            if (closestEntity != null && (blockHitResult.getType() == HitResult.Type.MISS || start.distanceTo(Vec3.atCenterOf(blockHitResult.getBlockPos())) > minDistance)) {
                triggerEffects(level, entity, closestEntity, amplifier + 1);
            }
        }
    }

    private void triggerEffects(Level level, Entity shooter, Entity target, int power) {
        if (target instanceof LivingEntity livingEntity) {
            livingEntity.hurt(level.damageSources().sonicBoom(shooter), power * 2.0F);
            livingEntity.setSecondsOnFire(5 * power);

            BlockPos posUnderEntity = new BlockPos(target.getBlockX(), target.getBlockY() - 1, target.getBlockZ());
            BlockState blockUnderEntity = level.getBlockState(posUnderEntity);
            if (blockUnderEntity.getBlock().isFlammable(blockUnderEntity, level, posUnderEntity, Direction.UP)) {
                level.setBlock(posUnderEntity, Blocks.FIRE.defaultBlockState(), 3);
            }
        }

        if (level instanceof ServerLevel sl) {
            Vec3 start = shooter.getEyePosition(1.0F);
            Vec3 look = shooter.getViewVector(1.0F);
            Vec3 end = start.add(look.scale(LASER_MAX_RANGE)); // Extend the look direction far beyond the target to ensure it reaches or exceeds it

            // Get the actual impact point or farthest point it can reach before hitting an obstacle or reaching the target
            Vec3 actualEnd = target.position().add(0, target.getBbHeight() / 2.0, 0); // Considering the target's mid-point for accurate effect
            if (start.distanceTo(actualEnd) < start.distanceTo(end)) {
                end = actualEnd; // If the target is closer than the maximal range, cut the ray there
            }

            Vec3 direction = end.subtract(start).normalize().scale(0.5); // Calculate the direction vector

            Vec3 particlePos = start;
            double distanceToEnd = start.distanceTo(end);
            while (particlePos.distanceTo(start) < distanceToEnd) {
                sl.sendParticles(ParticleTypes.ENCHANTED_HIT, particlePos.x, particlePos.y, particlePos.z, 1, 0.0D, 0.0D, 0.0D, 0.0D);
                particlePos = particlePos.add(direction); // Move along the vector
            }
        }
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }
}
