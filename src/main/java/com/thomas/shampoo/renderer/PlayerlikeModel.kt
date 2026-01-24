package com.thomas.shampoo.renderer

import java.util.function.Function
import kotlin.math.cos
import net.minecraft.client.model.HumanoidModel
import net.minecraft.client.model.geom.ModelPart
import net.minecraft.client.model.geom.PartPose
import net.minecraft.client.model.geom.builders.CubeDeformation
import net.minecraft.client.model.geom.builders.CubeListBuilder
import net.minecraft.client.model.geom.builders.MeshDefinition
import net.minecraft.client.renderer.RenderType
import net.minecraft.world.entity.LivingEntity
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn

@OnlyIn(Dist.CLIENT)
class PlayerlikeModel<T : LivingEntity>(root: ModelPart) :
    HumanoidModel<T>(
        root,
        Function { resourceLocation -> RenderType.entityTranslucent(resourceLocation) },
    ) {
  override fun setupAnim(
      entity: T,
      limbSwing: Float,
      limbSwingAmount: Float,
      ageInTicks: Float,
      netHeadYaw: Float,
      headPitch: Float,
  ) {
    // This is where you setup animations. Simplified to only manage walking.
    this.head.yRot = netHeadYaw * (Math.PI.toFloat() / 180f)
    this.head.xRot = headPitch * (Math.PI.toFloat() / 180f)

    this.rightArm.xRot =
        cos(limbSwing * 0.6662f + Math.PI).toFloat() * 2.0f * limbSwingAmount * 0.5f
    this.leftArm.xRot =
        cos((limbSwing * 0.6662f).toDouble()).toFloat() * 2.0f * limbSwingAmount * 0.5f

    this.rightLeg.xRot = cos((limbSwing * 0.6662f).toDouble()).toFloat() * 1.4f * limbSwingAmount
    this.leftLeg.xRot = cos(limbSwing * 0.6662f + Math.PI).toFloat() * 1.4f * limbSwingAmount
  }

  companion object {
    fun createMesh(cubeDeformation: CubeDeformation): MeshDefinition {
      val meshdefinition = createMesh(cubeDeformation, 0.0f)
      val partdefinition = meshdefinition.getRoot()

      // Standard humanoid parts that include animation setups
      partdefinition.addOrReplaceChild(
          "head",
          CubeListBuilder.create()
              .texOffs(0, 0)
              .addBox(-4.0f, -8.0f, -4.0f, 8.0f, 8.0f, 8.0f, cubeDeformation),
          PartPose.ZERO,
      )
      partdefinition.addOrReplaceChild(
          "body",
          CubeListBuilder.create()
              .texOffs(16, 16)
              .addBox(-4.0f, 0.0f, -2.0f, 8.0f, 12.0f, 4.0f, cubeDeformation),
          PartPose.ZERO,
      )
      partdefinition.addOrReplaceChild(
          "right_arm",
          CubeListBuilder.create()
              .texOffs(40, 16)
              .mirror()
              .addBox(-3.0f, -2.0f, -2.0f, 4.0f, 12.0f, 4.0f, cubeDeformation),
          PartPose.offset(-5.0f, 2.0f, 0.0f),
      )
      partdefinition.addOrReplaceChild(
          "left_arm",
          CubeListBuilder.create()
              .texOffs(32, 48)
              .addBox(-1.0f, -2.0f, -2.0f, 4.0f, 12.0f, 4.0f, cubeDeformation),
          PartPose.offset(5.0f, 2.0f, 0.0f),
      )
      partdefinition.addOrReplaceChild(
          "right_leg",
          CubeListBuilder.create()
              .texOffs(0, 16)
              .mirror()
              .addBox(-2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f, cubeDeformation),
          PartPose.offset(-1.9f, 12.0f, 0.0f),
      )
      partdefinition.addOrReplaceChild(
          "left_leg",
          CubeListBuilder.create()
              .texOffs(16, 48)
              .addBox(-2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f, cubeDeformation),
          PartPose.offset(1.9f, 12.0f, 0.0f),
      )

      return meshdefinition
    }
  }
}
