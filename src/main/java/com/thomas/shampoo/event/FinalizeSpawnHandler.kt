package com.thomas.shampoo.event

import com.thomas.shampoo.ShampooMod
import com.thomas.shampoo.entity.StevenArmstrong
import net.minecraftforge.event.entity.living.MobSpawnEvent.FinalizeSpawn
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod.EventBusSubscriber

@EventBusSubscriber(modid = ShampooMod.MODID, bus = EventBusSubscriber.Bus.FORGE)
class FinalizeSpawnHandler {
  @SubscribeEvent
  fun onMobSpawnFinalize(event: FinalizeSpawn) {
    val mob = event.entity
    if (mob is StevenArmstrong) {
      mob.hasTriggeredLowHealthState = false
      mob.hasTriggeredStartState = false
    }
  }
}
