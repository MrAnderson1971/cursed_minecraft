package com.thomas.shampoo.entity.ai

import com.thomas.shampoo.effect.Unlaserable
import java.util.*
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EntitySelector
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.Mob
import net.minecraft.world.entity.ai.goal.Goal

class LaserAttackGoal(
    private val mob: Mob, // Single class
    private val targetClass: Class<out LivingEntity?>,
    customCriteria: (LivingEntity) -> Boolean,
) : Goal() {
  private val additionalCriteria: (LivingEntity) -> Boolean
  private var target: LivingEntity? = null

  init {
    // New custom criteria
    this.setFlags(EnumSet.of<Flag?>(Flag.LOOK))
    // General criteria including not attacking creative or spectator mode players and custom
    // criteria
    this.additionalCriteria = { entity ->
      EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(entity) &&
          customCriteria(entity) &&
          (entity !is Unlaserable)
    }
  }

  override fun canUse(): Boolean {
    this.target = findNearestTarget()
    return this.target != null
  }

  private fun findNearestTarget(): LivingEntity? {
    // Adjusted to check for the specific class and additional criteria
    val potentialTargets =
        mob.commandSenderWorld.getEntitiesOfClass(
            targetClass,
            this.mob.boundingBox.inflate(10.0),
            additionalCriteria,
        )
    return if (potentialTargets.isEmpty()) null else potentialTargets[0]
  }

  override fun start() {
    // Called once when the goal is initialized
    this.mob.getLookControl().setLookAt(this.target!! as Entity, 30.0f, 30.0f)
  }

  override fun tick() {
    // Called every tick while the goal is active
    if (this.target != null) {
      this.mob.getLookControl().setLookAt(this.target as Entity, 30.0f, 30.0f)
    }
  }
}
