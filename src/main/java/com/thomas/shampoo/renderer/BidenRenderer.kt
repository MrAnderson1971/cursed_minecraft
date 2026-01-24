package com.thomas.shampoo.renderer

import com.mojang.blaze3d.vertex.PoseStack
import com.thomas.shampoo.ShampooMod
import com.thomas.shampoo.entity.Biden
import net.minecraft.client.model.geom.ModelLayers
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.entity.EntityRendererProvider
import net.minecraft.client.renderer.entity.LivingEntityRenderer
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation

class BidenRenderer(context: EntityRendererProvider.Context) :
    LivingEntityRenderer<Biden, PlayerlikeModel<Biden>>(
        context,
        PlayerlikeModel(context.bakeLayer(ModelLayers.PLAYER)),
        0.5f,
    ) {
  override fun getTextureLocation(entity: Biden): ResourceLocation {
    return BIDEN_TEXTURE // Assigning Biden specific texture.
  }

  override fun renderNameTag(
      entity: Biden,
      displayName: Component,
      poseStack: PoseStack,
      bufferSource: MultiBufferSource,
      packedLight: Int,
  ) {
    // Do nothing, this will prevent the name tag from rendering
  }

  companion object {
    private val BIDEN_TEXTURE =
        ResourceLocation(ShampooMod.MODID, "textures/entity/biden.png")
  }
}
