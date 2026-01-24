package com.thomas.shampoo.entity

import com.thomas.shampoo.entity.ai.*
import com.thomas.shampoo.network.PacketHandler.sendToServer
import com.thomas.shampoo.network.SStartPlayingArmstrongMusicPacket
import com.thomas.shampoo.network.SStopArmstrongMusicPacket
import com.thomas.shampoo.world.ModSounds
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin
import net.minecraft.core.BlockPos
import net.minecraft.core.particles.ParticleTypes
import net.minecraft.server.level.ServerBossEvent
import net.minecraft.server.level.ServerLevel
import net.minecraft.server.level.ServerPlayer
import net.minecraft.sounds.Music
import net.minecraft.sounds.SoundEvent
import net.minecraft.util.RandomSource
import net.minecraft.world.BossEvent
import net.minecraft.world.Difficulty
import net.minecraft.world.DifficultyInstance
import net.minecraft.world.damagesource.DamageSource
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.effect.MobEffects
import net.minecraft.world.entity.*
import net.minecraft.world.entity.ai.attributes.AttributeSupplier
import net.minecraft.world.entity.ai.attributes.Attributes
import net.minecraft.world.entity.ai.goal.*
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal
import net.minecraft.world.entity.ai.navigation.PathNavigation
import net.minecraft.world.entity.monster.Monster
import net.minecraft.world.entity.npc.Villager
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.world.item.enchantment.Enchantments
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.pathfinder.PathFinder
import net.minecraft.world.phys.Vec3
import net.minecraftforge.fluids.FluidType

class StevenArmstrong(type: EntityType<out Monster>, level: Level) : Monster(type, level) {
  private var jumpCooldown = 0.0
  private val bossEvent =
      (ServerBossEvent(
              this.displayName,
              BossEvent.BossBarColor.PURPLE,
              BossEvent.BossBarOverlay.PROGRESS,
          ))
          .setDarkenScreen(true) as ServerBossEvent
  // FinalizeSpawnHandler.java
  var hasTriggeredStartState: Boolean = false
  var hasTriggeredLowHealthState: Boolean = false

  private var attackCooldown = 0
  private var jumped = false
  private var offGround = false

  private var activeGoal: Goal? = null
  private val lookAtPlayerGoal = LookAtPlayerGoal(this, Player::class.java, 8.0f)
  private val meleeAttackGoal = MeleeAttackGoal(this, 1.0, true)
  private val randomLookAroundGoal = RandomLookAroundGoal(this)
  private val waterAvoidingRandomStrollGoal = WaterAvoidingRandomStrollGoal(this, 1.0)
  private val hurtByTargetGoal = HurtByTargetGoal(this)
  private val nearestAttackableTargetGoal =
      NearestAttackableTargetGoal(
          this,
          LivingEntity::class.java,
          0,
          false,
          false,
      ) { e ->
        !(e is StevenArmstrong || e is Villager)
      }
  private val armstrongGoals =
      listOf(SonicBoomGoal(this), SmokeExplosionGoal(this), LavaTrapGoal(this))

  var attackAnimationState: AnimationState = AnimationState()
  var sonicBoomAnimationState: AnimationState = AnimationState()
  var roarAnimationState: AnimationState = AnimationState()

  init {

    // Initially, only the look at player goal is high priority
    this.goalSelector.addGoal(3, lookAtPlayerGoal)
  }

  override fun createNavigation(level: Level): PathNavigation {
    return object : ArmstrongPathNavigation(this, level) {
      override fun createPathFinder(range: Int): PathFinder {
        nodeEvaluator = ArmstrongNodeEvaluator()
        return PathFinder(this.nodeEvaluator, range)
      }
    }
  }

  override fun handleEntityEvent(eventCode: Byte) {
    when (eventCode.toInt()) {
      4 -> attackAnimationState.start(tickCount)
      62 -> {
        attackAnimationState.stop()
        sonicBoomAnimationState.start(tickCount)
      }
      63 -> {
        attackAnimationState.stop()
        sonicBoomAnimationState.stop()
        roarAnimationState.start(tickCount)
      }
      else -> super.handleEntityEvent(eventCode)
    }
  }

  override fun aiStep() {
    super.aiStep()
    // Check health percentage
    val healthPercent = (this.health / this.maxHealth).toDouble()

    if (healthPercent > 0.9) {
      // If health is above 90%, focus on looking at the player
      ensureGoalActive(goalSelector, lookAtPlayerGoal, 3)
      removeGoalIfActive(goalSelector, meleeAttackGoal)
      removeGoalIfActive(goalSelector, randomLookAroundGoal)
      removeGoalIfActive(goalSelector, waterAvoidingRandomStrollGoal)
      removeGoalIfActive(targetSelector, hurtByTargetGoal)
      removeGoalIfActive(targetSelector, nearestAttackableTargetGoal)
    } else if (!hasTriggeredStartState) {
      hasTriggeredStartState = true
      playSound(ModSounds.ARMSTRONG_EMERGE.get(), 10.0f, 1.0f)
      // If health drops below 90%, activate all goals
      removeGoalIfActive(goalSelector, lookAtPlayerGoal)
      ensureGoalActive(goalSelector, meleeAttackGoal, 8)
      ensureGoalActive(goalSelector, randomLookAroundGoal, 8)
      ensureGoalActive(goalSelector, waterAvoidingRandomStrollGoal, 7)
      ensureGoalActive(goalSelector, lookAtPlayerGoal, 8)
      ensureGoalActive(targetSelector, hurtByTargetGoal, 3)
      ensureGoalActive(targetSelector, nearestAttackableTargetGoal, 2)
    }

    if (healthPercent < 0.5) {
      if (!hasTriggeredLowHealthState) {
        hasTriggeredLowHealthState = true
        playSound(ModSounds.ARMSTRONG_DIG.get(), 10.0f, 1.0f)
        level().broadcastEntityEvent(this, 63.toByte())
      }
      // Apply Regeneration II and Resistance I if below 50% health
      this.addEffect(MobEffectInstance(MobEffects.REGENERATION, 40, 1, false, false, true))
      this.addEffect(MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 40, 0, false, false, true))
      this.addEffect(MobEffectInstance(MobEffects.DAMAGE_BOOST, 40, 0, false, false, true))
    }

    // Decrement the jump cooldown if it's above 0
    if (this.jumpCooldown > 0) {
      this.jumpCooldown--
    }

    val target = this.target
    if (
        target != null && this.onGround() && this.jumpCooldown == 0.0 && shouldJumpToTarget(target)
    ) {
      this.jumpTowardsTarget(target) // Jump towards the target
      this.jumpCooldown = 100.0 // Reset cooldown
    }

    // Play boss music if not playing already
    if (isAlive && level().isClientSide()) {
      sendToServer(SStartPlayingArmstrongMusicPacket())
    }

    if (attackCooldown > 0) {
      attackCooldown--
    }

    if (attackCooldown <= 0 && this.target != null && activeGoal == null) {
      val choice = random.nextInt(armstrongGoals.size)
      val candidateGoal = armstrongGoals[choice]

      if (candidateGoal.canUse()) {
        activeGoal = candidateGoal
        activeGoal!!.start()
        attackCooldown = MAX_COOLDOWN // Reset cooldown
        level().broadcastEntityEvent(this, 62.toByte())
      }
    }

    if (activeGoal != null) {
      if (activeGoal!!.canContinueToUse()) {
        activeGoal!!.tick()
      } else {
        activeGoal!!.stop()
        activeGoal = null // Reset the active goal after stopping
      }
    }

    if (jumped && !onGround()) {
      offGround = true
    }

    if (this.onGround() && offGround) {
      jumped = false // Reset the jump flag
      offGround = false
      triggerGroundPound()
    }
  }

  private fun triggerGroundPound() {
    if (this.health <= this.maxHealth * 0.5) {
      // Radius within which the effect is applied
      val radius = 5.0
      var nearbyEntities =
          this.level().getEntitiesOfClass<LivingEntity>(
              LivingEntity::class.java,
              this.boundingBox.inflate(radius),
          ) { e ->
            e !== this && e.isAlive
          }
      val radiusSquared = radius * radius // Calculate square of radius to use in comparison

      // Filter entities to include only those within the spherical distance
      nearbyEntities =
          nearbyEntities
              .stream()
              .filter { e ->
                e.position().distanceToSqr(this.position()) <= radiusSquared &&
                    EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(e)
              }
              .toList()

      // Apply a knock-up effect and show particles
      for (entity in nearbyEntities) {
        val upForce = 1.5
        entity.deltaMovement = entity.deltaMovement.add(0.0, upForce, 0.0)
        entity.hurtMarked = true
      }

      // Display particles in a circle around the mob
      displaySweepParticles()
    }
  }

  private fun displaySweepParticles() {
    val radius = 5.0
    val sl = level()
    if (sl is ServerLevel) {
      var i = 0
      while (i < 360) {
        val rad = Math.toRadians(i.toDouble())
        val x = this.x + radius * cos(rad)
        val z = this.z + radius * sin(rad)
        sl.sendParticles(
            ParticleTypes.SWEEP_ATTACK,
            x,
            this.y,
            z,
            10,
            0.1,
            0.1,
            0.1,
            0.0,
        )
        i += 10
      }
    }
  }

  private fun ensureGoalActive(selector: GoalSelector, goal: Goal, priority: Int) {
    if (!isGoalActive(selector, goal)) {
      selector.addGoal(priority, goal)
    }
  }

  private fun removeGoalIfActive(selector: GoalSelector, goal: Goal) {
    if (isGoalActive(selector, goal)) {
      selector.removeGoal(goal)
    }
  }

  private fun isGoalActive(selector: GoalSelector, goal: Goal?): Boolean {
    return selector.availableGoals.stream().anyMatch { wrappedGoal -> wrappedGoal.goal === goal }
  }

  private fun shouldJumpToTarget(target: LivingEntity): Boolean {
    // Check if the target is within a straight line but on a different elevation
    val dx = abs(this.x - target.x)
    val dz = abs(this.z - target.z)
    val dy = target.y - this.y
    return dx <= 5 && dz <= 5 && dy > 1 && dy < 4 // Example condition, adjust as needed
  }

  private fun jumpTowardsTarget(target: LivingEntity) {
    if (this.isAlive) {
      val direction = Vec3(target.x - this.x, 0.0, target.z - this.z).normalize()
      val horizontalSpeedFactor = 0.8
      val verticalSpeedFactor = 0.9
      this.setDeltaMovement(
          direction.x * horizontalSpeedFactor,
          verticalSpeedFactor,
          direction.z * horizontalSpeedFactor,
      )
      this.hasImpulse = true
      jumped = true
    }
  }

  override fun die(cause: DamageSource) {
    super.die(cause)

    var anyAlive = false
    if (!level().isClientSide) { // Ensure this runs on the server side
      // Check if there are any other entities of this type still alive
      anyAlive =
          level()
              .getEntitiesOfClass<StevenArmstrong>(
                  StevenArmstrong::class.java,
                  this.boundingBox.inflate(10000.0),
              )
              .stream()
              .anyMatch { e -> e.isAlive && e !== this }
    }
    if (level().isClientSide() && !anyAlive) {
      // Inform the client to stop the music, typically through a custom packet or event
      sendToServer(SStopArmstrongMusicPacket())
    }
  }

  override fun customServerAiStep() {
    this.bossEvent.setProgress(this.health / this.maxHealth)
  }

  override fun startSeenByPlayer(player: ServerPlayer) {
    super.startSeenByPlayer(player)
    this.bossEvent.addPlayer(player)
  }

  override fun stopSeenByPlayer(player: ServerPlayer) {
    super.stopSeenByPlayer(player)
    this.bossEvent.removePlayer(player)
  }

  override fun populateDefaultEquipmentSlots(r: RandomSource, d: DifficultyInstance) {
    // Create an ItemStack for leather boots
    val boots = ItemStack(Items.LEATHER_BOOTS)

    // Apply the Depth Strider III enchantment to the boots
    boots.enchant(Enchantments.DEPTH_STRIDER, 3)

    // Set the enchanted boots to the mob's feet slot
    setItemSlot(EquipmentSlot.FEET, boots)
  }

  override fun getAmbientSound(): SoundEvent {
    return ModSounds.ARMSTRONG_ROAR.get()
  }

  override fun getHurtSound(d: DamageSource): SoundEvent {
    return ModSounds.ARMSTRONG_HURT.get()
  }

  override fun getDeathSound(): SoundEvent {
    return ModSounds.ARMSTRONG_DEATH.get()
  }

  override fun playStepSound(p: BlockPos, s: BlockState) {
    this.playSound(ModSounds.ARMSTRONG_STEP.get(), 10.0f, 1.0f)
  }

  override fun doHurtTarget(target: Entity): Boolean {
    level().broadcastEntityEvent(this, 4.toByte())
    playSound(ModSounds.ARMSTRONG_ATTACK_IMPACT.get(), 10.0f, voicePitch)
    return super.doHurtTarget(target)
  }

  override fun removeWhenFarAway(d: Double): Boolean {
    return false
  }

  override fun checkDespawn() {
    if (level().difficulty == Difficulty.PEACEFUL && shouldDespawnInPeaceful()) {
      discard() // Despawn the entity if the difficulty is set to Peaceful.
    }
  }

  override fun isPushedByFluid(fluidType: FluidType?): Boolean {
    return false
  }

  companion object {
    private const val SIGHT_RANGE = 50.0f // Increased sight range
    private const val BASE_SPEED = 0.5f
    private const val MAX_HEALTH = 500f
    const val ATTACK_DAMAGE: Float = 15.0f
    private const val MAX_COOLDOWN = 200 // Cooldown time in ticks

    val ARMSTRONG_MUSIC: Music =
        Music(ModSounds.ARMSTRONG_MUSIC.getHolder().orElseThrow(), 100, 200, true)

    fun createAttributes(): AttributeSupplier.Builder {
      return createMobAttributes()
          .add(Attributes.MAX_HEALTH, MAX_HEALTH.toDouble())
          .add(Attributes.MOVEMENT_SPEED, BASE_SPEED.toDouble())
          .add(Attributes.FOLLOW_RANGE, SIGHT_RANGE.toDouble())
          .add(Attributes.KNOCKBACK_RESISTANCE, 1.0)
          .add(Attributes.ATTACK_DAMAGE, ATTACK_DAMAGE.toDouble())
    }
  }
}
