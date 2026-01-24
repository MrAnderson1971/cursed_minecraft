package com.thomas.shampoo.entity

import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.damagesource.DamageSource
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.projectile.Fireball
import net.minecraft.world.level.Level
import net.minecraft.world.phys.EntityHitResult
import net.minecraft.world.phys.HitResult

interface Unfireballable

class CustomFireball : Fireball {
  constructor(type: EntityType<CustomFireball?>, level: Level) : super(type, level)

  constructor(
      level: Level,
      owner: LivingEntity,
      accelX: Double,
      accelY: Double,
      accelZ: Double,
  ) : super(EntityType.FIREBALL, owner, accelX, accelY, accelZ, level)

  override fun onHitEntity(hit: EntityHitResult) {
    if (hit.entity is Unfireballable) {
      return
    }
    super.onHitEntity(hit)
    if (!this.level().isClientSide) {
      val entity = hit.entity
      val entity1 = this.owner
      if (entity1 is LivingEntity) {
        entity.hurt(this.damageSources().fireball(this, entity1), 0.01f)
      }
      if (entity is LivingEntity) {
        val lightningBolt = EntityType.LIGHTNING_BOLT.create(entity.level())
        lightningBolt!!.moveTo(entity.x, entity.y, entity.z)
        lightningBolt.cause = entity1 as? ServerPlayer
        entity.level().addFreshEntity(lightningBolt)
      }
    }
  }

  override fun onHit(hit: HitResult) {
    if (hit.type == HitResult.Type.ENTITY && (hit as EntityHitResult).entity is Unfireballable) {
      // If the hit entity is Unfireballable, do not discard the fireball
      return
    }
    super.onHit(hit)
    if (!this.level().isClientSide) {
      this.discard()
    }
  }

  override fun isPickable(): Boolean {
    return false
  }

  override fun hurt(p_37381_: DamageSource, p_37382_: Float): Boolean {
    return false
  }
}
