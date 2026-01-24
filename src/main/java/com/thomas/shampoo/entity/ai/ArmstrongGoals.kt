package com.thomas.shampoo.entity.ai

import com.thomas.shampoo.entity.StevenArmstrong
import com.thomas.shampoo.world.ModSounds
import java.util.*
import kotlin.math.cos
import kotlin.math.sin
import net.minecraft.core.BlockPos
import net.minecraft.core.particles.ParticleTypes
import net.minecraft.server.level.ServerLevel
import net.minecraft.util.Mth
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.effect.MobEffects
import net.minecraft.world.entity.ai.attributes.Attributes
import net.minecraft.world.entity.ai.goal.Goal
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.LiquidBlock
import net.minecraft.world.phys.Vec3

class SmokeExplosionGoal(private val mob: StevenArmstrong) : Goal() {
  private var timer = 0
  private var targetPos: BlockPos? = null
  private var using = false
  private var animationDelay = 0

  init {
    this.setFlags(EnumSet.of<Flag?>(Flag.MOVE))
  }

  override fun canUse(): Boolean {
    val target = mob.target
    return target != null && mob.distanceToSqr(target) <= 225 && mob.distanceToSqr(target) > 16
  }

  override fun start() {
    animationDelay = 40 // Delay before starting the explosion sequence
    timer = -1 // Ensure timer is inactive until animationDelay is complete
    using = true // Start using this goal
  }

  override fun canContinueToUse(): Boolean {
    return using
  }

  override fun tick() {
    if (animationDelay > 0) {
      animationDelay--
      if (animationDelay == 0) {
        if (mob.target == null) {
          return
        }
        // Now start the explosion timer
        timer = 20 // Set the timer for the explosion countdown
        mob.playSound(ModSounds.ARMSTRONG_AGITATED.get(), 3.0f, 1.0f)
        targetPos = mob.target!!.blockPosition()
        spawnInitialParticles(mob.level(), mob.eyePosition)
      }
    } else if (timer > 0) {
      timer--
      val sl = mob.level()
      if (sl is ServerLevel) {
        sl.sendParticles(
            ParticleTypes.SMOKE,
            targetPos!!.x.toDouble(),
            targetPos!!.y.toDouble(),
            targetPos!!.z.toDouble(),
            1,
            0.0,
            0.05,
            0.0,
            0.01,
        )
      }
      if (timer == 0) {
        // When timer expires, perform the explosion
        val world = mob.level()
        world.explode(
            mob,
            targetPos!!.x.toDouble(),
            targetPos!!.y.toDouble(),
            targetPos!!.z.toDouble(),
            4.0f,
            Level.ExplosionInteraction.MOB,
        )
        using = false // Stop using this goal after the explosion
      }
    }
  }

  private fun spawnInitialParticles(world: Level?, position: Vec3) {
    if (world is ServerLevel) {
      val d0 = sin(mob.yRot * (Math.PI / 180))
      val d1 = cos(mob.yRot * (Math.PI / 180))
      for (i in 0..29) {
        world.sendParticles(
            ParticleTypes.SMOKE,
            position.x + d1 * i * 0.1,
            position.y,
            position.z + d0 * i * 0.1,
            1,
            0.0,
            0.05,
            0.0,
            0.01,
        )
        world.sendParticles(
            ParticleTypes.ANGRY_VILLAGER,
            position.x + d1 * i * 0.1,
            position.y,
            position.z + d0 * i * 0.1,
            1,
            0.0,
            0.0,
            0.0,
            0.01,
        )
      }
    }
  }
}

class LavaTrapGoal(private val mob: StevenArmstrong) : Goal() {
  private var animationDelay = 0

  override fun canUse(): Boolean {
    val target = mob.target ?: return false
    val pos = target.blockPosition().below()
    return mob.level().getBlockState(pos).isSolidRender(mob.level(), pos) &&
        mob.distanceToSqr(target) <= 225 &&
        mob.distanceToSqr(target) > 16 &&
        !mob.fireImmune()
  }

  override fun canContinueToUse(): Boolean {
    return animationDelay >= 0
  }

  override fun start() {
    animationDelay = 40
  }

  override fun tick() {
    if (animationDelay-- <= 0) {
      val target = mob.target
      val serverLevel = mob.level()

      if (target != null && serverLevel is ServerLevel) {
        mob.playSound(ModSounds.ARMSTRONG_ANGRY.get(), 3.0f, 1.0f)
        val centerPos = target.blockPosition() // The block directly on the target

        val flowingLava =
            Blocks.LAVA.defaultBlockState().setValue(LiquidBlock.LEVEL, 9) // Example level
        serverLevel.setBlockAndUpdate(centerPos, flowingLava)

        // Spawn particles to visually indicate the lava placement
        spawnParticles(serverLevel, mob.eyePosition)
      }
    }
  }

  private fun spawnParticles(world: Level?, position: Vec3) {
    if (world is ServerLevel) {
      val d0 = sin(mob.yRot * (Math.PI / 180))
      val d1 = cos(mob.yRot * (Math.PI / 180))
      for (i in 0..29) {
        world.sendParticles(
            ParticleTypes.FLAME,
            position.x + d1 * i * 0.1,
            position.y,
            position.z + d0 * i * 0.1,
            1,
            0.0,
            0.05,
            0.0,
            0.01,
        )
        world.sendParticles(
            ParticleTypes.LAVA,
            position.x + d1 * i * 0.1,
            position.y,
            position.z + d0 * i * 0.1,
            1,
            0.0,
            0.0,
            0.0,
            0.01,
        )
      }
    }
  }
}

class SonicBoomGoal(private val mob: StevenArmstrong) : Goal() {
  private var animationDelay = 0

  init {
    this.setFlags(EnumSet.of<Flag?>(Flag.LOOK))
  }

  override fun canUse(): Boolean {
    val target = mob.target ?: return false
    return mob.distanceToSqr(target) <= 225 &&
        mob.distanceToSqr(target) > 16 // Effective within 15 blocks and not too close
  }

  override fun canContinueToUse(): Boolean {
    return animationDelay >= 0
  }

  override fun start() {
    animationDelay = 40
  }

  override fun tick() {
    if (animationDelay-- <= 0) {
      val target = mob.target
      val serverLevel = mob.level()
      if (serverLevel is ServerLevel && target != null) {
        mob.playSound(ModSounds.ARMSTRONG_SONIC.get(), 3.0f, 1.0f)
        val from = mob.position().add(0.0, mob.eyeHeight.toDouble(), 0.0)
        val to = target.position()
        val direction = to.subtract(from).normalize()

        // Emit particles along the path
        for (i in 0..<Mth.floor(from.distanceTo(to)) + 7) {
          val point = from.add(direction.scale(i.toDouble()))
          serverLevel.sendParticles(
              ParticleTypes.SONIC_BOOM,
              point.x,
              point.y,
              point.z,
              1,
              0.0,
              0.0,
              0.0,
              0.0,
          )
        }

        val damage = 10.0f
        target.hurt(serverLevel.damageSources().sonicBoom(mob), damage)
        target.addEffect(
            MobEffectInstance(MobEffects.WEAKNESS, 200, 0)
        ) // Apply weakness for 10 seconds

        val attractionHorizontal = 2.5
        val dx =
            -direction.x *
                attractionHorizontal *
                (1.0 - target.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE))
        val attractionVertical = 0.5
        val dy =
            -direction.y *
                attractionVertical *
                (1.0 - target.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE))
        val dz =
            -direction.z *
                attractionHorizontal *
                (1.0 - target.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE))
        target.push(dx, dy, dz) // Pull the target towards the mob
      }
    }
  }
}
