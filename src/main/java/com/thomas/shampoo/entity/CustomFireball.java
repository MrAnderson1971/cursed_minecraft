package com.thomas.shampoo.entity;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.projectile.Fireball;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class CustomFireball extends Fireball {
    private static final int explosionPower = 1;

    public CustomFireball(EntityType<CustomFireball> type, Level level) {
        super(type, level);
    }

    public CustomFireball(Level level, LivingEntity owner, double accelX, double accelY, double accelZ) {
        super(EntityType.FIREBALL, owner, accelX, accelY, accelZ, level);
    }

    protected void onHitEntity(EntityHitResult hit) {
        if (hit.getEntity() instanceof Unfireballable) {
            return;
        }
        super.onHitEntity(hit);
        if (!this.level().isClientSide) {
            Entity entity = hit.getEntity();
            Entity entity1 = this.getOwner();
            if (entity1 instanceof LivingEntity) {
                entity.hurt(this.damageSources().fireball(this, entity1), 0.01F);
            }
            if (entity instanceof LivingEntity) {
                LightningBolt lightningBolt = EntityType.LIGHTNING_BOLT.create(entity.level());
                lightningBolt.moveTo(entity.getX(), entity.getY(), entity.getZ());
                lightningBolt.setCause(entity1 instanceof ServerPlayer ? (ServerPlayer) entity1 : null);
                entity.level().addFreshEntity(lightningBolt);
            }
        }
    }

    protected void onHit(HitResult hit) {
        if (hit.getType() == HitResult.Type.ENTITY && ((EntityHitResult)hit).getEntity() instanceof Unfireballable) {
            // If the hit entity is Unfireballable, do not discard the fireball
            return;
        }
        super.onHit(hit);
        if (!this.level().isClientSide) {
            this.discard();
        }
    }

    public boolean isPickable() {
        return false;
    }

    public boolean hurt(DamageSource p_37381_, float p_37382_) {
        return false;
    }
}
