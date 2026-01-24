package com.thomas.shampoo.event

import com.thomas.shampoo.ShampooMod
import com.thomas.shampoo.entity.EntityInit
import com.thomas.shampoo.renderer.ArmstrongModel
import com.thomas.shampoo.renderer.ArmstrongRenderer
import com.thomas.shampoo.renderer.BidenRenderer
import com.thomas.shampoo.renderer.ModModelLayers
import com.thomas.shampoo.renderer.ObamaRenderer
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.client.event.EntityRenderersEvent.RegisterLayerDefinitions
import net.minecraftforge.client.event.EntityRenderersEvent.RegisterRenderers
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod.EventBusSubscriber

@EventBusSubscriber(
    modid = ShampooMod.MODID,
    bus = EventBusSubscriber.Bus.MOD,
    value = [Dist.CLIENT],
)
object ClientModEvents {
  @SubscribeEvent
  fun onClientSetup(event: RegisterRenderers) {
    event.registerEntityRenderer(
        EntityInit.BIDEN.get(),
    ) { context ->
      BidenRenderer(context)
    }
    event.registerEntityRenderer(
        EntityInit.OBAMA.get(),
    ) { context ->
      ObamaRenderer(context)
    }
    event.registerEntityRenderer(
        EntityInit.STEVEN_ARMSTRONG.get(),
    ) { context ->
      ArmstrongRenderer(context)
    }
  }

  @SubscribeEvent
  fun onRegisterLayers(event: RegisterLayerDefinitions) {
    event.registerLayerDefinition(
        ModModelLayers.ARMSTRONG_LAYER,
    ) {
      ArmstrongModel.createBodyLayer()
    }
  }
}
