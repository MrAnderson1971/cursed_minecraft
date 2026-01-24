package com.thomas.shampoo.world

import com.thomas.shampoo.ShampooMod
import net.minecraft.core.Registry
import net.minecraft.core.RegistryAccess
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.damagesource.DamageSource
import net.minecraft.world.damagesource.DamageType
import net.minecraft.world.entity.Entity

class ModDamageSources(registryAccess: RegistryAccess) {
  private val damageTypes: Registry<DamageType> =
      registryAccess.registryOrThrow(Registries.DAMAGE_TYPE)

  fun laser(causingEntity: Entity?): DamageSource {
    return DamageSource(damageTypes.getHolderOrThrow(ModDamageTypes.LASER), causingEntity)
  }
}

interface ModDamageTypes {
  companion object {
    val LASER: ResourceKey<DamageType> =
        ResourceKey.create(
            Registries.DAMAGE_TYPE,
            ResourceLocation(ShampooMod.Companion.MODID, "laser"),
        )
  }
}
