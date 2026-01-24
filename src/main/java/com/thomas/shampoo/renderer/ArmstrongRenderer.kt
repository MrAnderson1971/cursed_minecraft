package com.thomas.shampoo.renderer

import com.thomas.shampoo.ShampooMod
import com.thomas.shampoo.entity.StevenArmstrong
import net.minecraft.client.renderer.entity.EntityRendererProvider
import net.minecraft.client.renderer.entity.MobRenderer
import net.minecraft.resources.ResourceLocation
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn

@OnlyIn(Dist.CLIENT)
class ArmstrongRenderer(context: EntityRendererProvider.Context) :
    MobRenderer<StevenArmstrong, ArmstrongModel<StevenArmstrong>>(
        context,
        ArmstrongModel<StevenArmstrong>(context.bakeLayer(ModModelLayers.ARMSTRONG_LAYER)),
        0.9f,
    ) {
  override fun getTextureLocation(entity: StevenArmstrong): ResourceLocation {
    return TEXTURE
  }

  companion object {
    private val TEXTURE = ResourceLocation(ShampooMod.MODID, "textures/entity/steven_armstrong.png")
  }
}
