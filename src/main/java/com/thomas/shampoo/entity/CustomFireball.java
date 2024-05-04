package com.thomas.shampoo.entity;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
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

    protected void onHit(HitResult hitResult) {
        super.onHit(hitResult);
        if (!level().isClientSide) {
            if (hitResult.getType() == HitResult.Type.BLOCK) {
                level().explode(getOwner(), getX(), getY(), getZ(), explosionPower, Level.ExplosionInteraction.TNT);
            }
            discard();
        }
    }

    protected void onHitEntity(EntityHitResult entityHitResult) {
        super.onHitEntity(entityHitResult);
        if (!level().isClientSide) {
            Entity entity = entityHitResult.getEntity();
            Entity entity1 = getOwner();
            if (entity == entity1) {
                return; // no friendly fire
            }
            entity.hurt(damageSources().fireball(this, entity1), 6.0F);
            if (entity1 instanceof LivingEntity) {
                this.doEnchantDamageEffects((LivingEntity)entity1, entity);
            }
        }
    }
}
