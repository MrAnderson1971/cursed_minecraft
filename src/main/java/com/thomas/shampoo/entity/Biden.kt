package com.thomas.shampoo.entity

import com.thomas.shampoo.effect.EffectInit
import com.thomas.shampoo.effect.Unlaserable
import com.thomas.shampoo.entity.ai.LaserAttackGoal
import kotlin.math.max
import net.minecraft.world.effect.MobEffectInstance
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
import net.minecraft.world.entity.monster.Phantom
import net.minecraft.world.entity.monster.RangedAttackMob
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.Level

class Biden(type: EntityType<out Monster>, worldIn: Level) :
    Monster(type, worldIn), RangedAttackMob {
  private var summonCooldown = 0 // Tracks cooldown time for summoning phantoms

  init {
    this.goalSelector.addGoal(
        2,
        LaserAttackGoal(this, Mob::class.java) { e -> selection(e) },
    )
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
          e !is Unlaserable && e is Enemy
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
            8.0f,
            BASE_SPEED,
            2 * BASE_SPEED,
        ) { e ->
          selection(e) && EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(e)
        },
    )
    this.goalSelector.addGoal(7, WaterAvoidingRandomStrollGoal(this, 1.0))
    this.goalSelector.addGoal(8, RandomLookAroundGoal(this))
  }

  public override fun getStandingEyeHeight(p_21131_: Pose, p_21132_: EntityDimensions): Float {
    return 1.8f
  }

  override fun performRangedAttack(p_33317_: LivingEntity, p_33318_: Float) {
    val level = commandSenderWorld
    if (!level.isClientSide) {
      val amplifier = max(0, level.difficulty.id - 1) // Easy = 0, Normal = 1, Hard = 2
      this.addEffect(MobEffectInstance(EffectInit.LASER.get(), 40, amplifier, false, false))
    }
  }

  override fun tick() {
    super.tick()

    if (!level().isClientSide) {
      // Increment cooldown
      summonCooldown++

      // Check if it's time to summon phantoms
      if (summonCooldown >= SUMMON_INTERVAL) {
        summonCooldown = random.nextInt(SUMMON_INTERVAL / 2) // Reset cooldown

        // Summon 1-3 phantoms randomly
        val phantomCount = 1 + random.nextInt(3)
        for (i in 0..<phantomCount) {
          summonPhantom()
        }
      }
    }
  }

  private fun summonPhantom() {
    // Create a new Phantom entity
    val phantom = EntityType.PHANTOM.create(level())
    if (phantom != null) {
      // Position the phantom near Biden
      val spawnPosition =
          this.position().add(random.nextGaussian() * 2, 1.0, random.nextGaussian() * 2)
      phantom.moveTo(
          spawnPosition.x,
          spawnPosition.y,
          spawnPosition.z,
          random.nextFloat() * 360,
          0f,
      )

      // Spawn the phantom in the world
      level().addFreshEntity(phantom)
    }
  }

  companion object {
    private const val BASE_SPEED = 0.6
    private const val SUMMON_INTERVAL = 200 // Time in ticks (10 seconds at 20 ticks per second)

    private fun selection(e: Entity): Boolean {
      return !(e is Unlaserable || e is Phantom) && (e is Enemy || e is Player)
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
