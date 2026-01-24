package com.thomas.shampoo.renderer

import net.minecraft.client.animation.AnimationChannel
import net.minecraft.client.animation.AnimationDefinition
import net.minecraft.client.animation.Keyframe
import net.minecraft.client.animation.KeyframeAnimations

object ArmstrongAnimation {
  @JvmField
  val ARMSTRONG_ATTACK: AnimationDefinition =
      AnimationDefinition.Builder.withLength(0.33333f)
          .addAnimation(
              "body",
              AnimationChannel(
                  AnimationChannel.Targets.ROTATION,
                  Keyframe(
                      0.0f,
                      KeyframeAnimations.degreeVec(0.0f, 0.0f, 0.0f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
                  Keyframe(
                      0.0417f,
                      KeyframeAnimations.degreeVec(-22.5f, 0.0f, 0.0f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
                  Keyframe(
                      0.2083f,
                      KeyframeAnimations.degreeVec(22.5f, 0.0f, 0.0f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
                  Keyframe(
                      0.3333f,
                      KeyframeAnimations.degreeVec(0.0f, 0.0f, 0.0f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
              ),
          )
          .addAnimation(
              "body",
              AnimationChannel(
                  AnimationChannel.Targets.POSITION,
                  Keyframe(
                      0.0f,
                      KeyframeAnimations.posVec(0.0f, 0.0f, 0.0f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
                  Keyframe(
                      0.0417f,
                      KeyframeAnimations.posVec(0.0f, 0.0f, 0.0f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
                  Keyframe(
                      0.2083f,
                      KeyframeAnimations.posVec(0.0f, -1.0f, -2.0f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
                  Keyframe(
                      0.3333f,
                      KeyframeAnimations.posVec(0.0f, 0.0f, 0.0f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
              ),
          )
          .addAnimation(
              "head",
              AnimationChannel(
                  AnimationChannel.Targets.ROTATION,
                  Keyframe(
                      0.0f,
                      KeyframeAnimations.degreeVec(0.0f, 0.0f, 0.0f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
                  Keyframe(
                      0.0417f,
                      KeyframeAnimations.degreeVec(22.5f, 0.0f, 0.0f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
                  Keyframe(
                      0.25f,
                      KeyframeAnimations.degreeVec(-30.17493f, 0.0f, 0.0f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
                  Keyframe(
                      0.3333f,
                      KeyframeAnimations.degreeVec(0.0f, 0.0f, 0.0f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
              ),
          )
          .addAnimation(
              "head",
              AnimationChannel(
                  AnimationChannel.Targets.POSITION,
                  Keyframe(
                      0.0f,
                      KeyframeAnimations.posVec(0.0f, 0.0f, 0.0f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
                  Keyframe(
                      0.0417f,
                      KeyframeAnimations.posVec(0.0f, 0.0f, 0.0f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
                  Keyframe(
                      0.25f,
                      KeyframeAnimations.posVec(0.0f, -2.0f, -2.0f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
                  Keyframe(
                      0.3333f,
                      KeyframeAnimations.posVec(0.0f, 0.0f, 0.0f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
              ),
          )
          .addAnimation(
              "right_arm",
              AnimationChannel(
                  AnimationChannel.Targets.ROTATION,
                  Keyframe(
                      0.0f,
                      KeyframeAnimations.degreeVec(0.0f, 0.0f, 0.0f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
                  Keyframe(
                      0.0417f,
                      KeyframeAnimations.degreeVec(-120.36119f, 40.78947f, -20.94102f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
                  Keyframe(
                      0.1667f,
                      KeyframeAnimations.degreeVec(-90.0f, -45.0f, 0.0f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
                  Keyframe(
                      0.3333f,
                      KeyframeAnimations.degreeVec(0.0f, 0.0f, 0.0f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
              ),
          )
          .addAnimation(
              "right_arm",
              AnimationChannel(
                  AnimationChannel.Targets.POSITION,
                  Keyframe(
                      0.0f,
                      KeyframeAnimations.posVec(0.0f, 0.0f, 0.0f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
                  Keyframe(
                      0.0417f,
                      KeyframeAnimations.posVec(4.0f, 0.0f, 5.0f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
                  Keyframe(
                      0.1667f,
                      KeyframeAnimations.posVec(0.0f, 0.0f, 0.0f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
                  Keyframe(
                      0.3333f,
                      KeyframeAnimations.posVec(0.0f, 0.0f, 0.0f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
              ),
          )
          .addAnimation(
              "left_arm",
              AnimationChannel(
                  AnimationChannel.Targets.ROTATION,
                  Keyframe(
                      0.0f,
                      KeyframeAnimations.degreeVec(0.0f, 0.0f, 0.0f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
                  Keyframe(
                      0.0417f,
                      KeyframeAnimations.degreeVec(-120.36119f, -40.78947f, 20.94102f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
                  Keyframe(
                      0.1667f,
                      KeyframeAnimations.degreeVec(-61.1632f, 42.85882f, 11.52421f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
                  Keyframe(
                      0.3333f,
                      KeyframeAnimations.degreeVec(0.0f, 0.0f, 0.0f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
              ),
          )
          .addAnimation(
              "left_arm",
              AnimationChannel(
                  AnimationChannel.Targets.POSITION,
                  Keyframe(
                      0.0f,
                      KeyframeAnimations.posVec(0.0f, 0.0f, 0.0f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
                  Keyframe(
                      0.0417f,
                      KeyframeAnimations.posVec(-4.0f, 0.0f, 5.0f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
                  Keyframe(
                      0.1667f,
                      KeyframeAnimations.posVec(0.0f, 0.0f, 0.0f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
                  Keyframe(
                      0.3333f,
                      KeyframeAnimations.posVec(0.0f, 0.0f, 0.0f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
              ),
          )
          .build()

  @JvmField
  val ARMSTRONG_SONIC_BOOM: AnimationDefinition =
      AnimationDefinition.Builder.withLength(3.0f)
          .addAnimation(
              "body",
              AnimationChannel(
                  AnimationChannel.Targets.ROTATION,
                  Keyframe(
                      0.0f,
                      KeyframeAnimations.degreeVec(0.0f, 0.0f, 0.0f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
                  Keyframe(
                      1.0833f,
                      KeyframeAnimations.degreeVec(47.5f, 0.0f, 0.0f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
                  Keyframe(
                      1.625f,
                      KeyframeAnimations.degreeVec(55.0f, 0.0f, 0.0f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
                  Keyframe(
                      1.9167f,
                      KeyframeAnimations.degreeVec(0.0f, 0.0f, 0.0f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
                  Keyframe(
                      2.0f,
                      KeyframeAnimations.degreeVec(-32.5f, 0.0f, 0.0f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
                  Keyframe(
                      2.4583f,
                      KeyframeAnimations.degreeVec(-32.5f, 0.0f, 0.0f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
                  Keyframe(
                      2.7083f,
                      KeyframeAnimations.degreeVec(0.0f, 0.0f, 0.0f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
                  Keyframe(
                      2.875f,
                      KeyframeAnimations.degreeVec(0.0f, 0.0f, 0.0f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
              ),
          )
          .addAnimation(
              "body",
              AnimationChannel(
                  AnimationChannel.Targets.POSITION,
                  Keyframe(
                      0.0f,
                      KeyframeAnimations.posVec(0.0f, 0.0f, 0.0f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
                  Keyframe(
                      1.0833f,
                      KeyframeAnimations.posVec(0.0f, -3.0f, 0.0f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
                  Keyframe(
                      1.625f,
                      KeyframeAnimations.posVec(0.0f, -4.0f, -1.0f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
                  Keyframe(
                      1.9167f,
                      KeyframeAnimations.posVec(0.0f, 0.0f, 0.0f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
                  Keyframe(
                      2.7083f,
                      KeyframeAnimations.posVec(0.0f, 0.0f, 0.0f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
                  Keyframe(
                      2.875f,
                      KeyframeAnimations.posVec(0.0f, 0.0f, 0.0f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
              ),
          )
          .addAnimation(
              "head",
              AnimationChannel(
                  AnimationChannel.Targets.ROTATION,
                  Keyframe(
                      0.0f,
                      KeyframeAnimations.degreeVec(0.0f, 0.0f, 0.0f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
                  Keyframe(
                      1.0f,
                      KeyframeAnimations.degreeVec(67.5f, 0.0f, 0.0f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
                  Keyframe(
                      1.75f,
                      KeyframeAnimations.degreeVec(80.0f, 0.0f, 0.0f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
                  Keyframe(
                      1.9167f,
                      KeyframeAnimations.degreeVec(-45.0f, 0.0f, 0.0f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
                  Keyframe(
                      2.5f,
                      KeyframeAnimations.degreeVec(-45.0f, 0.0f, 0.0f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
                  Keyframe(
                      2.7083f,
                      KeyframeAnimations.degreeVec(-45.0f, 0.0f, 0.0f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
                  Keyframe(
                      2.875f,
                      KeyframeAnimations.degreeVec(0.0f, 0.0f, 0.0f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
              ),
          )
          .addAnimation(
              "head",
              AnimationChannel(
                  AnimationChannel.Targets.POSITION,
                  Keyframe(
                      0.0f,
                      KeyframeAnimations.posVec(0.0f, 0.0f, 0.0f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
                  Keyframe(
                      1.9167f,
                      KeyframeAnimations.posVec(0.0f, 0.0f, -3.0f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
                  Keyframe(
                      2.5f,
                      KeyframeAnimations.posVec(0.0f, 0.0f, -3.0f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
                  Keyframe(
                      2.7083f,
                      KeyframeAnimations.posVec(0.0f, 0.0f, -3.0f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
                  Keyframe(
                      2.875f,
                      KeyframeAnimations.posVec(0.0f, 0.0f, 0.0f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
              ),
          )
          .addAnimation(
              "right_arm",
              AnimationChannel(
                  AnimationChannel.Targets.ROTATION,
                  Keyframe(
                      0.0f,
                      KeyframeAnimations.degreeVec(0.0f, 0.0f, 0.0f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
                  Keyframe(
                      0.875f,
                      KeyframeAnimations.degreeVec(-42.28659f, -32.69813f, -5.00825f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
                  Keyframe(
                      1.1667f,
                      KeyframeAnimations.degreeVec(-29.83757f, -35.39626f, -45.28089f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
                  Keyframe(
                      1.3333f,
                      KeyframeAnimations.degreeVec(-29.83757f, -35.39626f, -45.28089f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
                  Keyframe(
                      1.6667f,
                      KeyframeAnimations.degreeVec(-72.28659f, -32.69813f, -5.00825f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
                  Keyframe(
                      1.8333f,
                      KeyframeAnimations.degreeVec(35.26439f, -30.0f, 35.26439f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
                  Keyframe(
                      1.9167f,
                      KeyframeAnimations.degreeVec(73.75484f, -13.0931f, 19.20518f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
                  Keyframe(
                      2.5f,
                      KeyframeAnimations.degreeVec(73.75484f, -13.0931f, 19.20518f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
                  Keyframe(
                      2.75f,
                      KeyframeAnimations.degreeVec(58.20713f, -21.1064f, 28.7261f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
                  Keyframe(
                      3.0f,
                      KeyframeAnimations.degreeVec(0.0f, 0.0f, 0.0f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
              ),
          )
          .addAnimation(
              "right_arm",
              AnimationChannel(
                  AnimationChannel.Targets.POSITION,
                  Keyframe(
                      0.0f,
                      KeyframeAnimations.posVec(0.0f, 0.0f, 0.0f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
                  Keyframe(
                      1.8333f,
                      KeyframeAnimations.posVec(3.0f, 0.0f, 0.0f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
                  Keyframe(
                      2.75f,
                      KeyframeAnimations.posVec(3.0f, 0.0f, 0.0f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
                  Keyframe(
                      3.0f,
                      KeyframeAnimations.posVec(0.0f, 0.0f, 0.0f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
              ),
          )
          .addAnimation(
              "left_arm",
              AnimationChannel(
                  AnimationChannel.Targets.ROTATION,
                  Keyframe(
                      0.0f,
                      KeyframeAnimations.degreeVec(0.0f, 0.0f, 0.0f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
                  Keyframe(
                      0.875f,
                      KeyframeAnimations.degreeVec(-33.80694f, 32.31058f, 6.87997f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
                  Keyframe(
                      1.1667f,
                      KeyframeAnimations.degreeVec(-17.87827f, 34.62115f, 49.02433f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
                  Keyframe(
                      1.3333f,
                      KeyframeAnimations.degreeVec(-17.87827f, 34.62115f, 49.02433f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
                  Keyframe(
                      1.6667f,
                      KeyframeAnimations.degreeVec(-51.30694f, 32.31058f, 6.87997f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
                  Keyframe(
                      1.8333f,
                      KeyframeAnimations.degreeVec(35.26439f, 30.0f, -35.26439f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
                  Keyframe(
                      1.9167f,
                      KeyframeAnimations.degreeVec(73.75484f, 13.0931f, -19.20518f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
                  Keyframe(
                      2.5f,
                      KeyframeAnimations.degreeVec(73.75484f, 13.0931f, -19.20518f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
                  Keyframe(
                      2.75f,
                      KeyframeAnimations.degreeVec(58.20713f, 21.1064f, -28.7261f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
                  Keyframe(
                      3.0f,
                      KeyframeAnimations.degreeVec(0.0f, 0.0f, 0.0f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
              ),
          )
          .addAnimation(
              "left_arm",
              AnimationChannel(
                  AnimationChannel.Targets.POSITION,
                  Keyframe(
                      0.0f,
                      KeyframeAnimations.posVec(0.0f, 0.0f, 0.0f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
                  Keyframe(
                      1.8333f,
                      KeyframeAnimations.posVec(-3.0f, 0.0f, 0.0f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
                  Keyframe(
                      2.75f,
                      KeyframeAnimations.posVec(-3.0f, 0.0f, 0.0f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
                  Keyframe(
                      3.0f,
                      KeyframeAnimations.posVec(0.0f, 0.0f, 0.0f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
              ),
          )
          .build()

  @JvmField
  val ARMSTRONG_ROAR: AnimationDefinition =
      AnimationDefinition.Builder.withLength(4.2f)
          .addAnimation(
              "body",
              AnimationChannel(
                  AnimationChannel.Targets.ROTATION,
                  Keyframe(
                      0.0f,
                      KeyframeAnimations.degreeVec(0.0f, 0.0f, 0.0f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
                  Keyframe(
                      1.24f,
                      KeyframeAnimations.degreeVec(-25.0f, 0.0f, 0.0f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
                  Keyframe(
                      1.6f,
                      KeyframeAnimations.degreeVec(32.5f, 0.0f, -7.5f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
                  Keyframe(
                      1.84f,
                      KeyframeAnimations.degreeVec(38.33f, 0.0f, 2.99f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
                  Keyframe(
                      2.08f,
                      KeyframeAnimations.degreeVec(40.97f, 0.0f, -4.3f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
                  Keyframe(
                      2.36f,
                      KeyframeAnimations.degreeVec(44.41f, 0.0f, 6.29f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
                  Keyframe(
                      3.0f,
                      KeyframeAnimations.degreeVec(47.5f, 0.0f, 0.0f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
                  Keyframe(
                      4.2f,
                      KeyframeAnimations.degreeVec(0.0f, 0.0f, 0.0f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
              ),
          )
          .addAnimation(
              "body",
              AnimationChannel(
                  AnimationChannel.Targets.POSITION,
                  Keyframe(
                      0.0f,
                      KeyframeAnimations.posVec(0.0f, 0.0f, 0.0f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
                  Keyframe(
                      1.24f,
                      KeyframeAnimations.posVec(0.0f, -1.0f, 3.0f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
                  Keyframe(
                      1.6f,
                      KeyframeAnimations.posVec(0.0f, -3.0f, -6.0f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
                  Keyframe(
                      3.0f,
                      KeyframeAnimations.posVec(0.0f, -3.0f, -6.0f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
                  Keyframe(
                      4.2f,
                      KeyframeAnimations.posVec(0.0f, 0.0f, 0.0f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
              ),
          )
          .addAnimation(
              "head",
              AnimationChannel(
                  AnimationChannel.Targets.ROTATION,
                  Keyframe(
                      0.0f,
                      KeyframeAnimations.degreeVec(0.0f, 0.0f, 0.0f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
                  Keyframe(
                      1.24f,
                      KeyframeAnimations.degreeVec(-32.5f, 0.0f, 0.0f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
                  Keyframe(
                      1.6f,
                      KeyframeAnimations.degreeVec(-32.5f, 0.0f, -27.5f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
                  Keyframe(
                      1.8f,
                      KeyframeAnimations.degreeVec(-32.5f, 0.0f, 26.0f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
                  Keyframe(
                      2.04f,
                      KeyframeAnimations.degreeVec(-32.5f, 0.0f, -27.5f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
                  Keyframe(
                      2.44f,
                      KeyframeAnimations.degreeVec(-32.5f, 0.0f, 26.0f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
                  Keyframe(
                      2.84f,
                      KeyframeAnimations.degreeVec(-5.0f, 0.0f, -12.5f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
                  Keyframe(
                      4.2f,
                      KeyframeAnimations.degreeVec(0.0f, 0.0f, 0.0f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
              ),
          )
          .addAnimation(
              "head",
              AnimationChannel(
                  AnimationChannel.Targets.POSITION,
                  Keyframe(
                      0.0f,
                      KeyframeAnimations.posVec(0.0f, 0.0f, 0.0f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
                  Keyframe(
                      1.24f,
                      KeyframeAnimations.posVec(0.0f, 0.0f, 0.0f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
                  Keyframe(
                      1.6f,
                      KeyframeAnimations.posVec(0.0f, -2.0f, -6.0f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
                  Keyframe(
                      2.2f,
                      KeyframeAnimations.posVec(0.0f, -2.0f, -6.0f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
                  Keyframe(
                      2.48f,
                      KeyframeAnimations.posVec(0.0f, -2.0f, -6.0f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
                  Keyframe(
                      4.2f,
                      KeyframeAnimations.posVec(0.0f, 0.0f, 0.0f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
              ),
          )
          .addAnimation(
              "right_arm",
              AnimationChannel(
                  AnimationChannel.Targets.ROTATION,
                  Keyframe(
                      0.0f,
                      KeyframeAnimations.degreeVec(0.0f, 0.0f, 0.0f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
                  Keyframe(
                      0.72f,
                      KeyframeAnimations.degreeVec(-120.0f, 0.0f, -20.0f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
                  Keyframe(
                      1.24f,
                      KeyframeAnimations.degreeVec(-77.5f, 3.75f, 15.0f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
                  Keyframe(
                      1.48f,
                      KeyframeAnimations.degreeVec(67.5f, -32.5f, 20.0f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
                  Keyframe(
                      2.48f,
                      KeyframeAnimations.degreeVec(37.5f, -32.5f, 25.0f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
                  Keyframe(
                      2.88f,
                      KeyframeAnimations.degreeVec(27.6f, -17.1f, 32.5f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
                  Keyframe(
                      4.2f,
                      KeyframeAnimations.degreeVec(0.0f, 0.0f, 0.0f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
              ),
          )
          .addAnimation(
              "right_arm",
              AnimationChannel(
                  AnimationChannel.Targets.POSITION,
                  Keyframe(
                      0.0f,
                      KeyframeAnimations.posVec(0.0f, 0.0f, 0.0f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
                  Keyframe(
                      0.72f,
                      KeyframeAnimations.posVec(3.0f, -2.0f, 0.0f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
                  Keyframe(
                      1.48f,
                      KeyframeAnimations.posVec(4.0f, -2.0f, 0.0f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
                  Keyframe(
                      2.48f,
                      KeyframeAnimations.posVec(4.0f, -2.0f, 0.0f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
                  Keyframe(
                      4.2f,
                      KeyframeAnimations.posVec(0.0f, 0.0f, 0.0f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
              ),
          )
          .addAnimation(
              "left_arm",
              AnimationChannel(
                  AnimationChannel.Targets.ROTATION,
                  Keyframe(
                      0.0f,
                      KeyframeAnimations.degreeVec(0.0f, 0.0f, 0.0f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
                  Keyframe(
                      0.72f,
                      KeyframeAnimations.degreeVec(-125.0f, 0.0f, 20.0f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
                  Keyframe(
                      1.24f,
                      KeyframeAnimations.degreeVec(-76.25f, -17.5f, -7.5f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
                  Keyframe(
                      1.48f,
                      KeyframeAnimations.degreeVec(62.5f, 42.5f, -12.5f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
                  Keyframe(
                      2.48f,
                      KeyframeAnimations.degreeVec(37.5f, 27.5f, -27.5f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
                  Keyframe(
                      2.88f,
                      KeyframeAnimations.degreeVec(25.0f, 18.4f, -30.0f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
                  Keyframe(
                      4.2f,
                      KeyframeAnimations.degreeVec(0.0f, 0.0f, 0.0f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
              ),
          )
          .addAnimation(
              "left_arm",
              AnimationChannel(
                  AnimationChannel.Targets.POSITION,
                  Keyframe(
                      0.0f,
                      KeyframeAnimations.posVec(0.0f, 0.0f, 0.0f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
                  Keyframe(
                      0.72f,
                      KeyframeAnimations.posVec(-3.0f, -2.0f, 0.0f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
                  Keyframe(
                      1.48f,
                      KeyframeAnimations.posVec(-4.0f, -2.0f, 0.0f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
                  Keyframe(
                      2.48f,
                      KeyframeAnimations.posVec(-4.0f, -2.0f, 0.0f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
                  Keyframe(
                      4.2f,
                      KeyframeAnimations.posVec(0.0f, 0.0f, 0.0f),
                      AnimationChannel.Interpolations.CATMULLROM,
                  ),
              ),
          )
          .build()
}
