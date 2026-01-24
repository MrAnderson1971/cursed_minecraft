package com.thomas.shampoo.entity

import com.thomas.shampoo.ShampooMod
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.MobCategory
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import net.minecraftforge.registries.RegistryObject

object EntityInit {
  val ENTITIES: DeferredRegister<EntityType<*>> =
      DeferredRegister.create(
          ForgeRegistries.ENTITY_TYPES,
          ShampooMod.MODID,
      )

  @JvmField
  val CUSTOM_FIREBALL: RegistryObject<EntityType<CustomFireball>> =
      ENTITIES.register(
          "custom_fireball",
      ) {
        EntityType.Builder.of<CustomFireball>(
                { type, level -> CustomFireball(type, level) },
                MobCategory.MISC,
            )
            .sized(1.0f, 1.0f)
            .build(ResourceLocation("minecraft", "textures/entity/fireball.png").toString())
      }

  @JvmField
  val BIDEN: RegistryObject<EntityType<Biden>> =
      ENTITIES.register(
          "biden",
      ) {
        EntityType.Builder.of(
                { type, worldIn -> Biden(type, worldIn) },
                MobCategory.MONSTER,
            )
            .sized(0.6f, 1.8f) // These are typical dimensions for a player.
            .build(ResourceLocation(ShampooMod.Companion.MODID, "biden").toString())
      }

  @JvmField
  val OBAMA: RegistryObject<EntityType<Obama>> =
      ENTITIES.register(
          "obama",
      ) {
        EntityType.Builder.of(
                { type, worldIn -> Obama(type, worldIn) },
                MobCategory.MONSTER,
            )
            .sized(0.6f, 1.8f)
            .build(ResourceLocation(ShampooMod.MODID, "obama").toString())
      }

  @JvmField
  val STEVEN_ARMSTRONG: RegistryObject<EntityType<StevenArmstrong>> =
      ENTITIES.register(
          "steven_armstrong",
      ) {
        EntityType.Builder.of(
                { type, level -> StevenArmstrong(type, level) },
                MobCategory.MONSTER,
            )
            .sized(0.9f, 2.9f)
            .build(ResourceLocation(ShampooMod.MODID, "steven_armstrong").toString())
      }
}
