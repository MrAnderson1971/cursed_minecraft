package com.thomas.shampoo.block

import net.minecraft.core.BlockPos
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.Shapes
import net.minecraft.world.phys.shapes.VoxelShape

class JadePyramidBlock(properties: Properties) : Block(properties) {
  @Deprecated("Deprecated in Java")
  override fun getShape(
      state: BlockState,
      world: BlockGetter,
      pos: BlockPos,
      context: CollisionContext,
  ): VoxelShape {
    return PYRAMID_SHAPE
  }

  companion object {
    // Define the voxel shapes for each tier of the pyramid.
    private val BASE = box(0.0, 0.0, 0.0, 16.0, 4.0, 16.0)
    private val MIDDLE = box(2.0, 4.0, 2.0, 14.0, 8.0, 14.0)
    private val TOP = box(4.0, 8.0, 4.0, 12.0, 12.0, 12.0)
    private val APEX = box(6.0, 12.0, 6.0, 10.0, 16.0, 10.0)

    // Combine the tiers to create the full pyramid shape.
    private val PYRAMID_SHAPE = Shapes.or(BASE, MIDDLE, TOP, APEX)
  }
}
