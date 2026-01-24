package com.thomas.shampoo.event

import com.thomas.shampoo.ShampooMod
import com.thomas.shampoo.effect.EffectInit
import net.minecraftforge.event.TickEvent
import net.minecraftforge.event.TickEvent.PlayerTickEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod.EventBusSubscriber

@EventBusSubscriber(modid = ShampooMod.MODID, bus = EventBusSubscriber.Bus.FORGE)
object PlayerTickHandler {
  @SubscribeEvent
  fun playerTick(event: PlayerTickEvent) {
    handleFlying(event)
  }

  // Allows flying with the FLYING status effect.
  private fun handleFlying(event: PlayerTickEvent) {
    if (event.phase != TickEvent.Phase.START) {
      return
    }

    val player = event.player
    if (player.isSpectator || player.isCreative) {
      return
    }
    val flyingEffect = player.getEffect(EffectInit.FLYING.get())

    // Handle enabling/disabling flight
    val canFly = flyingEffect != null
    player.abilities.mayfly = canFly

    if (!player.abilities.flying && canFly && !player.onGround()) {
      // Allow flying only if not on the ground and capable of flight
      player.abilities.flying = true
    } else if (player.abilities.flying && !canFly) {
      // Disable flying if currently flying but should no longer be able to
      player.abilities.flying = false
    }

    if (player.abilities.mayfly != canFly || player.abilities.flying != canFly) {
      player.onUpdateAbilities() // Update player abilities if there's a change
    }

    if (canFly) {
      player.fallDistance = 0.0f
    }
  }
}
