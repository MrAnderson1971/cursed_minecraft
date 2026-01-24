package com.thomas.shampoo.entity

import kotlin.math.sqrt
import net.minecraft.world.entity.*
import net.minecraft.world.entity.ai.attributes.AttributeSupplier
import net.minecraft.world.entity.ai.attributes.Attributes
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal
import net.minecraft.world.entity.ai.goal.RangedAttackGoal
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal
import net.minecraft.world.entity.monster.Enemy
import net.minecraft.world.entity.monster.Monster
import net.minecraft.world.entity.monster.RangedAttackMob
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.Level

class Obama(type: EntityType<out Monster>, worldIn: Level) :
    Monster(type, worldIn), RangedAttackMob, Unfireballable {
  init {
    this.goalSelector.addGoal(2, RangedAttackGoal(this, BASE_SPEED, 1, 20, 16f))
    this.targetSelector.addGoal(
        2,
        NearestAttackableTargetGoal<Mob>(
            this,
            Mob::class.java,
            10,
            true,
            false,
        ) { e ->
          e !is Obama && e is Enemy
        },
    )
    this.targetSelector.addGoal(
        3,
        NearestAttackableTargetGoal<Player>(
            this,
            Player::class.java,
            10,
            true,
            false,
        ) { _ ->
          true
        },
    )
    this.goalSelector.addGoal(
        1,
        AvoidEntityGoal<Mob>(
            this,
            Mob::class.java,
            10.0f,
            BASE_SPEED,
            2 * BASE_SPEED,
        ) { e ->
          selection(e) && EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(e)
        },
    )
    this.goalSelector.addGoal(7, WaterAvoidingRandomStrollGoal(this, 1.0))
    this.goalSelector.addGoal(8, RandomLookAroundGoal(this))
  }

  override fun performRangedAttack(target: LivingEntity, distanceFactor: Float) {
    val level = level()

    // Calculate direction towards target
    val d0 = target.x - x
    val d1 = target.getY(0.5) - eyeY // Aim directly at the target's eyes for better accuracy
    val d2 = target.z - z
    val dist = sqrt(d0 * d0 + d2 * d2)

    // Calculate how hard the fireball should be thrown
    val accelX = d0 / dist * 0.3 // You may want to adjust the multiplier based on desired speed
    val accelY = d1 / dist * 0.3 // Direct line aim since fireballs are not affected by gravity
    val accelZ = d2 / dist * 0.3

    // Create the CustomFireball entity
    val fireball = CustomFireball(level, this, accelX, accelY, accelZ)

    // Set the fireball's position slightly above and in front of the owner to prevent collision
    // with the owner
    fireball.setPos(x + d0 / dist * 0.5, eyeY, z + d2 / dist * 0.5)

    // Add the fireball to the world
    level.addFreshEntity(fireball)
  }

  companion object {
    private const val BASE_SPEED = 0.6

    private fun selection(e: Entity?): Boolean {
      return e !is Obama && (e is Enemy || e is Player)
    }

    @JvmStatic
    fun createAttributes(): AttributeSupplier.Builder {
      return createMobAttributes()
          .add(Attributes.MAX_HEALTH, 20.0)
          .add(Attributes.MOVEMENT_SPEED, BASE_SPEED)
          .add(Attributes.FOLLOW_RANGE, 30.0)
    }
  }
}
