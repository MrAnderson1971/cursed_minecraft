package com.thomas.shampoo.renderer

import com.thomas.shampoo.ShampooMod
import net.minecraft.client.model.geom.ModelLayerLocation
import net.minecraft.resources.ResourceLocation

object ModModelLayers {
  val ARMSTRONG_LAYER: ModelLayerLocation =
      ModelLayerLocation(
          ResourceLocation(ShampooMod.MODID, "steven_armstrong_layer"),
          "main",
      )
}
