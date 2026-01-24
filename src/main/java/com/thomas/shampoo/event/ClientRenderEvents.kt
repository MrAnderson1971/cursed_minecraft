package com.thomas.shampoo.event

import com.thomas.shampoo.entity.EntityInit
import com.thomas.shampoo.renderer.CustomFireballRenderer
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.client.event.EntityRenderersEvent.RegisterRenderers
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod.EventBusSubscriber

@EventBusSubscriber(value = [Dist.CLIENT], bus = EventBusSubscriber.Bus.MOD)
object ClientRenderEvents {
  @SubscribeEvent
  fun onRegisterRenderers(event: RegisterRenderers) {
    event.registerEntityRenderer(
        EntityInit.CUSTOM_FIREBALL.get(),
    ) { context ->
      CustomFireballRenderer(context)
    }
  }
}
