package com.thomas.shampoo.renderer

import com.mojang.blaze3d.vertex.PoseStack
import com.thomas.shampoo.ShampooMod
import com.thomas.shampoo.entity.Obama
import net.minecraft.client.model.geom.ModelLayers
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.entity.EntityRendererProvider
import net.minecraft.client.renderer.entity.LivingEntityRenderer
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation

class ObamaRenderer(context: EntityRendererProvider.Context) :
    LivingEntityRenderer<Obama, PlayerlikeModel<Obama>>(
        context,
        PlayerlikeModel(context.bakeLayer(ModelLayers.PLAYER)),
        0.5f,
    ) {
  override fun getTextureLocation(entity: Obama): ResourceLocation {
    return OBAMA_TEXTURE
  }

  override fun renderNameTag(
      entity: Obama,
      displayName: Component,
      poseStack: PoseStack,
      bufferSource: MultiBufferSource,
      packedLight: Int,
  ) {
    // Do nothing, this will prevent the name tag from rendering
  }

  companion object {
    private val OBAMA_TEXTURE =
        ResourceLocation(ShampooMod.MODID, "textures/entity/obama.png")
  }
}
