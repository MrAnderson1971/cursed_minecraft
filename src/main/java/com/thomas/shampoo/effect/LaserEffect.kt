package com.thomas.shampoo.effect

import com.thomas.shampoo.world.ModDamageSources
import java.util.function.Predicate
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.core.particles.ParticleTypes
import net.minecraft.core.particles.SimpleParticleType
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.effect.MobEffect
import net.minecraft.world.effect.MobEffectCategory
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.level.ClipContext
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.phys.AABB
import net.minecraft.world.phys.HitResult
import net.minecraft.world.phys.Vec3

interface Unlaserable

class LaserEffect(category: MobEffectCategory, color: Int) : MobEffect(category, color) {
  override fun applyEffectTick(entity: LivingEntity, amplifier: Int) {
    val level = entity.commandSenderWorld
    if (!level.isClientSide()) {
      val start = entity.getEyePosition(1.0f)
      val look = entity.getViewVector(1.0f)
      var end = start.add(look.scale(LASER_MAX_RANGE.toDouble())) // Range of the laser

      // Perform a ray trace to check for block collisions
      val blockContext =
          ClipContext(start, end, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, entity)
      val blockHitResult = level.clip(blockContext)

      // Adjust the end of the ray to the collision point if a block is hit
      if (blockHitResult.type != HitResult.Type.MISS) {
        end = blockHitResult.getLocation()
      }

      // Define the area to check for entities within the reach of the laser
      val aabb = AABB(start, end).inflate(1.0) // Slightly enlarge the search area for entities
      val entities =
          level.getEntities(
              entity,
              aabb,
              Predicate { e: Entity? -> e!!.isAlive && (entity != e) && e is LivingEntity },
          )
      var minDistance = Double.Companion.MAX_VALUE
      var closestEntity: Entity? = null

      // Check entities in the path
      for (target in entities) {
        val optional = target.boundingBox.clip(start, end)
        if (optional.isPresent) {
          val distance = start.distanceTo(optional.get())
          if (distance < minDistance) {
            closestEntity = target
            minDistance = distance
          }
        }
      }

      // If an entity is found and is closer than any block collision, trigger effects
      if (
          closestEntity != null &&
              (blockHitResult.type == HitResult.Type.MISS ||
                  start.distanceTo(Vec3.atCenterOf(blockHitResult.blockPos)) > minDistance)
      ) {
        triggerEffects(level, entity, closestEntity, amplifier + 1)
      }
    }
  }

  private fun triggerEffects(level: Level, shooter: Entity, target: Entity, power: Int) {
    if (target is Unlaserable) {
      return
    }
    if (target is LivingEntity) {
      val laser = ModDamageSources(level.registryAccess()).laser(shooter)
      target.hurt(laser, power * 2.0f)
      target.setSecondsOnFire(5 * power)

      val posUnderEntity = BlockPos(target.blockX, target.blockY - 1, target.getBlockZ())
      val blockUnderEntity = level.getBlockState(posUnderEntity)
      if (
          blockUnderEntity.block.isFlammable(blockUnderEntity, level, posUnderEntity, Direction.UP)
      ) {
        level.setBlock(posUnderEntity, Blocks.FIRE.defaultBlockState(), 3)
      }
    }

    if (level is ServerLevel) {
      val start = shooter.getEyePosition(1.0f)
      val look = shooter.getViewVector(1.0f)
      var end =
          start.add(
              look.scale(LASER_MAX_RANGE.toDouble())
          ) // Extend the look direction far beyond the target to ensure it reaches or exceeds it

      // Get the actual impact point or farthest point it can reach before hitting an obstacle or
      // reaching the target
      val actualEnd =
          target
              .position()
              .add(
                  0.0,
                  target.bbHeight / 2.0,
                  0.0,
              ) // Considering the target's mid-point for accurate effect
      if (start.distanceTo(actualEnd) < start.distanceTo(end)) {
        end = actualEnd // If the target is closer than the maximal range, cut the ray there
      }

      val direction = end.subtract(start).normalize().scale(0.5) // Calculate the direction vector

      var particlePos = start
      val distanceToEnd = start.distanceTo(end)
      while (particlePos.distanceTo(start) < distanceToEnd) {
        level.sendParticles<SimpleParticleType?>(
            ParticleTypes.END_ROD,
            particlePos.x,
            particlePos.y,
            particlePos.z,
            1,
            0.0,
            0.0,
            0.0,
            0.0,
        )
        particlePos = particlePos.add(direction) // Move along the vector
      }
    }
  }

  override fun shouldApplyEffectTickThisTick(duration: Int, amplifier: Int): Boolean {
    return true
  }

  companion object {
    const val LASER_MAX_RANGE: Int = 50
  }
}
