package com.thomas.shampoo.world;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.core.Registry;

public class ModDamageSources {
    private final Registry<DamageType> damageTypes;

    public ModDamageSources(RegistryAccess registryAccess) {
        this.damageTypes = registryAccess.registryOrThrow(Registries.DAMAGE_TYPE);
    }

    public DamageSource laser(Entity causingEntity) {
        return new DamageSource(damageTypes.getHolderOrThrow(ModDamageTypes.LASER), causingEntity);
    }
}
