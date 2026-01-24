package com.thomas.shampoo.renderer

import com.thomas.shampoo.entity.StevenArmstrong
import java.util.function.Function
import kotlin.math.min
import net.minecraft.client.model.HierarchicalModel
import net.minecraft.client.model.geom.ModelPart
import net.minecraft.client.model.geom.PartPose
import net.minecraft.client.model.geom.builders.CubeListBuilder
import net.minecraft.client.model.geom.builders.LayerDefinition
import net.minecraft.client.model.geom.builders.MeshDefinition
import net.minecraft.client.renderer.RenderType
import net.minecraft.util.Mth
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn

@OnlyIn(Dist.CLIENT)
class ArmstrongModel<T : StevenArmstrong>(private val root: ModelPart) :
    HierarchicalModel<T>(
        Function { resourceLocation -> RenderType.entityCutoutNoCull(resourceLocation) }
    ) {
  protected val bone = root.getChild("bone")
  protected val body = this.bone.getChild("body")
  protected val head = this.body.getChild("head")
  protected val leftLeg = this.bone.getChild("left_leg")
  protected val leftArm = this.body.getChild("left_arm")
  protected val rightArm = this.body.getChild("right_arm")
  protected val rightLeg = this.bone.getChild("right_leg")

  override fun setupAnim(
      entity: T,
      limbSwing: Float,
      limbSwingAmount: Float,
      ageInTicks: Float,
      netHeadYaw: Float,
      headPitch: Float,
  ) {
    root().allParts.forEach { obj: ModelPart? -> obj!!.resetPose() }
    animateWalk(limbSwing, limbSwingAmount)
    animateHeadLookTarget(netHeadYaw, headPitch)
    animateIdlePose(ageInTicks)

    animate(entity.attackAnimationState, ArmstrongAnimation.ARMSTRONG_ATTACK, ageInTicks)
    animate(entity.sonicBoomAnimationState, ArmstrongAnimation.ARMSTRONG_SONIC_BOOM, ageInTicks)
    animate(entity.roarAnimationState, ArmstrongAnimation.ARMSTRONG_ROAR, ageInTicks)
  }

  private fun animateIdlePose(ageInTicks: Float) {
    val f = ageInTicks * 0.1f
    val f1 = Mth.cos(f)
    val f2 = Mth.sin(f)
    this.head.zRot += 0.06f * f1
    this.head.xRot += 0.06f * f2
    this.body.zRot += 0.025f * f2
    this.body.xRot += 0.025f * f1
  }

  private fun animateHeadLookTarget(netHeadYaw: Float, headPitch: Float) {
    this.head.xRot = headPitch * (Math.PI.toFloat() / 180f)
    this.head.yRot = netHeadYaw * (Math.PI.toFloat() / 180f)
  }

  private fun animateWalk(limbSwing: Float, limbSwingAmount: Float) {
    val f = min(0.5f, 3.0f * limbSwingAmount)
    val f1 = limbSwing * 0.8662f
    val f2 = Mth.cos(f1)
    val f3 = Mth.sin(f1)
    val f4 = min(0.35f, f)
    this.head.zRot += 0.3f * f3 * f
    this.head.xRot += 1.2f * Mth.cos(f1 + (Math.PI.toFloat() / 2f)) * f4
    this.body.zRot = 0.1f * f3 * f
    this.body.xRot = 1.0f * f2 * f4
    this.leftLeg.xRot = 1.0f * f2 * f
    this.rightLeg.xRot = 1.0f * Mth.cos(f1 + Math.PI.toFloat()) * f
    this.leftArm.xRot = -(0.8f * f2 * f)
    this.leftArm.zRot = 0.0f
    this.rightArm.xRot = -(0.8f * f3 * f)
    this.rightArm.zRot = 0.0f
    this.resetArmPoses()
  }

  private fun resetArmPoses() {
    this.leftArm.yRot = 0.0f
    this.leftArm.z = 1.0f
    this.leftArm.x = 13.0f
    this.leftArm.y = -13.0f
    this.rightArm.yRot = 0.0f
    this.rightArm.z = 1.0f
    this.rightArm.x = -13.0f
    this.rightArm.y = -13.0f
  }

  override fun root(): ModelPart {
    return root
  }

  companion object {
    fun createBodyLayer(): LayerDefinition {
      val meshdefinition = MeshDefinition()
      val partdefinition = meshdefinition.getRoot()
      val partdefinition1 =
          partdefinition.addOrReplaceChild(
              "bone",
              CubeListBuilder.create(),
              PartPose.offset(0.0f, 24.0f, 0.0f),
          )
      val partdefinition2 =
          partdefinition1.addOrReplaceChild(
              "body",
              CubeListBuilder.create()
                  .texOffs(0, 0)
                  .addBox(-9.0f, -13.0f, -4.0f, 18.0f, 21.0f, 11.0f),
              PartPose.offset(0.0f, -21.0f, 0.0f),
          )
      val partdefinition3 =
          partdefinition2.addOrReplaceChild(
              "head",
              CubeListBuilder.create()
                  .texOffs(0, 32)
                  .addBox(-8.0f, -16.0f, -5.0f, 16.0f, 16.0f, 10.0f),
              PartPose.offset(0.0f, -13.0f, 0.0f),
          )
      partdefinition2.addOrReplaceChild(
          "right_arm",
          CubeListBuilder.create().texOffs(44, 50).addBox(-4.0f, 0.0f, -4.0f, 8.0f, 28.0f, 8.0f),
          PartPose.offset(-13.0f, -13.0f, 1.0f),
      )
      partdefinition2.addOrReplaceChild(
          "left_arm",
          CubeListBuilder.create().texOffs(0, 58).addBox(-4.0f, 0.0f, -4.0f, 8.0f, 28.0f, 8.0f),
          PartPose.offset(13.0f, -13.0f, 1.0f),
      )
      partdefinition1.addOrReplaceChild(
          "right_leg",
          CubeListBuilder.create().texOffs(76, 48).addBox(-3.1f, 0.0f, -3.0f, 6.0f, 13.0f, 6.0f),
          PartPose.offset(-5.9f, -13.0f, 0.0f),
      )
      partdefinition1.addOrReplaceChild(
          "left_leg",
          CubeListBuilder.create().texOffs(76, 76).addBox(-2.9f, 0.0f, -3.0f, 6.0f, 13.0f, 6.0f),
          PartPose.offset(5.9f, -13.0f, 0.0f),
      )
      return LayerDefinition.create(meshdefinition, 128, 128)
    }
  }
}
