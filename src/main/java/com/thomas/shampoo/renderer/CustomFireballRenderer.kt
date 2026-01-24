package com.thomas.shampoo.renderer

import com.thomas.shampoo.entity.CustomFireball
import net.minecraft.client.renderer.entity.EntityRenderer
import net.minecraft.client.renderer.entity.EntityRendererProvider
import net.minecraft.resources.ResourceLocation

class CustomFireballRenderer(context: EntityRendererProvider.Context) :
    EntityRenderer<CustomFireball>(context) {
  override fun getTextureLocation(entity: CustomFireball): ResourceLocation {
    return FIREBALL_TEXTURE // points directly to the vanilla fireball texture
  }

  companion object {
    private val FIREBALL_TEXTURE = ResourceLocation("minecraft", "textures/entity/fireball.png")
  }
}
