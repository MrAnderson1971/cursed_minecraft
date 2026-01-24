package com.thomas.shampoo.world

import com.thomas.shampoo.ShampooMod
import net.minecraft.resources.ResourceLocation
import net.minecraft.sounds.SoundEvent
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import net.minecraftforge.registries.RegistryObject

object ModSounds {
  val SOUNDS: DeferredRegister<SoundEvent> =
      DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, ShampooMod.MODID)

  // Dynamically generated sound events
  @JvmField
  val ARMSTRONG_MUSIC: RegistryObject<SoundEvent> =
      SOUNDS.register(
          "armstrong_music",
      ) {
        SoundEvent.createVariableRangeEvent(ResourceLocation(ShampooMod.MODID, "armstrong_music"))
      }

  val ARMSTRONG_AGITATED: RegistryObject<SoundEvent> =
      SOUNDS.register(
          "armstrong_agitated",
      ) {
        SoundEvent.createVariableRangeEvent(
            ResourceLocation(ShampooMod.MODID, "armstrong_agitated")
        )
      }

  val ARMSTRONG_AMBIENT: RegistryObject<SoundEvent> =
      SOUNDS.register(
          "armstrong_ambient",
      ) {
        SoundEvent.createVariableRangeEvent(ResourceLocation(ShampooMod.MODID, "armstrong_ambient"))
      }

  val ARMSTRONG_ANGRY: RegistryObject<SoundEvent> =
      SOUNDS.register(
          "armstrong_angry",
      ) {
        SoundEvent.createVariableRangeEvent(ResourceLocation(ShampooMod.MODID, "armstrong_angry"))
      }

  @JvmField
  val ARMSTRONG_ATTACK_IMPACT: RegistryObject<SoundEvent> =
      SOUNDS.register(
          "armstrong_attack_impact",
      ) {
        SoundEvent.createVariableRangeEvent(
            ResourceLocation(ShampooMod.MODID, "armstrong_attack_impact")
        )
      }

  @JvmField
  val ARMSTRONG_DEATH: RegistryObject<SoundEvent> =
      SOUNDS.register(
          "armstrong_death",
      ) {
        SoundEvent.createVariableRangeEvent(ResourceLocation(ShampooMod.MODID, "armstrong_death"))
      }

  @JvmField
  val ARMSTRONG_DIG: RegistryObject<SoundEvent> =
      SOUNDS.register(
          "armstrong_dig",
      ) {
        SoundEvent.createVariableRangeEvent(ResourceLocation(ShampooMod.MODID, "armstrong_dig"))
      }

  @JvmField
  val ARMSTRONG_EMERGE: RegistryObject<SoundEvent> =
      SOUNDS.register(
          "armstrong_emerge",
      ) {
        SoundEvent.createVariableRangeEvent(ResourceLocation(ShampooMod.MODID, "armstrong_emerge"))
      }

  val ARMSTRONG_HEARTBEAT: RegistryObject<SoundEvent> =
      SOUNDS.register(
          "armstrong_heartbeat",
      ) {
        SoundEvent.createVariableRangeEvent(
            ResourceLocation(ShampooMod.MODID, "armstrong_heartbeat")
        )
      }

  @JvmField
  val ARMSTRONG_HURT: RegistryObject<SoundEvent> =
      SOUNDS.register(
          "armstrong_hurt",
      ) {
        SoundEvent.createVariableRangeEvent(
            ResourceLocation(ShampooMod.Companion.MODID, "armstrong_hurt")
        )
      }

  val ARMSTRONG_LISTENING: RegistryObject<SoundEvent> =
      SOUNDS.register(
          "armstrong_listening",
      ) {
        SoundEvent.createVariableRangeEvent(
            ResourceLocation(ShampooMod.Companion.MODID, "armstrong_listening")
        )
      }

  val ARMSTRONG_LISTENING_ANGRY: RegistryObject<SoundEvent> =
      SOUNDS.register(
          "armstrong_listening_angry",
      ) {
        SoundEvent.createVariableRangeEvent(
            ResourceLocation(ShampooMod.Companion.MODID, "armstrong_listening_angry")
        )
      }

  val ARMSTRONG_NEARBY_CLOSER: RegistryObject<SoundEvent> =
      SOUNDS.register(
          "armstrong_nearby_closer",
      ) {
        SoundEvent.createVariableRangeEvent(
            ResourceLocation(ShampooMod.Companion.MODID, "armstrong_nearby_closer")
        )
      }

  val ARMSTRONG_NEARBY_CLOSEST: RegistryObject<SoundEvent> =
      SOUNDS.register(
          "armstrong_nearby_closest",
      ) {
        SoundEvent.createVariableRangeEvent(
            ResourceLocation(ShampooMod.Companion.MODID, "armstrong_nearby_closest")
        )
      }

  val ARMSTRONG_NEARBY_CLOSE: RegistryObject<SoundEvent> =
      SOUNDS.register(
          "armstrong_nearby_close",
      ) {
        SoundEvent.createVariableRangeEvent(
            ResourceLocation(ShampooMod.Companion.MODID, "armstrong_nearby_close")
        )
      }

  @JvmField
  val ARMSTRONG_ROAR: RegistryObject<SoundEvent> =
      SOUNDS.register(
          "armstrong_roar",
      ) {
        SoundEvent.createVariableRangeEvent(
            ResourceLocation(ShampooMod.Companion.MODID, "armstrong_roar")
        )
      }

  val ARMSTRONG_SNIFF: RegistryObject<SoundEvent> =
      SOUNDS.register(
          "armstrong_sniff",
      ) {
        SoundEvent.createVariableRangeEvent(
            ResourceLocation(ShampooMod.Companion.MODID, "armstrong_sniff")
        )
      }

  val ARMSTRONG_SONIC: RegistryObject<SoundEvent> =
      SOUNDS.register(
          "armstrong_sonic",
      ) {
        SoundEvent.createVariableRangeEvent(
            ResourceLocation(ShampooMod.Companion.MODID, "armstrong_sonic")
        )
      }

  @JvmField
  val ARMSTRONG_STEP: RegistryObject<SoundEvent> =
      SOUNDS.register(
          "armstrong_step",
      ) {
        SoundEvent.createVariableRangeEvent(
            ResourceLocation(ShampooMod.Companion.MODID, "armstrong_step")
        )
      }

  val ARMSTRONG_TENDRIL_CLICKS: RegistryObject<SoundEvent> =
      SOUNDS.register(
          "armstrong_tendril_clicks",
      ) {
        SoundEvent.createVariableRangeEvent(
            ResourceLocation(ShampooMod.Companion.MODID, "armstrong_tendril_clicks")
        )
      }
}
