package com.thomas.shampoo.effect

import com.thomas.shampoo.ShampooMod
import net.minecraft.world.effect.MobEffect
import net.minecraft.world.effect.MobEffectCategory
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import net.minecraftforge.registries.RegistryObject

class BasicCustomEffect // Effects handled in PlayerTickHandler.java
(mobEffectCategory: MobEffectCategory, i: Int) : MobEffect(mobEffectCategory, i)

object EffectInit {
  val MOB_EFFECTS: DeferredRegister<MobEffect> =
      DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, ShampooMod.Companion.MODID)

  @JvmField
  val FLYING: RegistryObject<MobEffect> =
      MOB_EFFECTS.register(
          "flying",
      ) {
        BasicCustomEffect(MobEffectCategory.BENEFICIAL, 0x2FADCF)
      }

  @JvmField
  val LASER: RegistryObject<MobEffect> =
      MOB_EFFECTS.register(
          "laser",
      ) {
        LaserEffect(MobEffectCategory.BENEFICIAL, 0xE80505)
      }
}
