package com.thomas.shampoo.entity.ai

import net.minecraft.core.BlockPos
import net.minecraft.core.BlockPos.MutableBlockPos
import net.minecraft.world.entity.boss.wither.WitherBoss
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.pathfinder.BlockPathTypes
import net.minecraft.world.level.pathfinder.FlyNodeEvaluator
import net.minecraft.world.level.pathfinder.Node

class ArmstrongNodeEvaluator : FlyNodeEvaluator() {
  override fun getBlockPathType(block: BlockGetter, x: Int, y: Int, z: Int): BlockPathTypes {
    return getBlockPathTypeStatic(block, MutableBlockPos(x, y, z), y)
  }

  override fun findAcceptedNode(x: Int, y: Int, z: Int): Node? {
    val level: BlockGetter = this.mob.level() // Assuming 'mob' has a reference to the level.
    val pos = BlockPos(x, y, z)
    val blockState = level.getBlockState(pos)

    // If the block is air and the block directly below is also air, return null.
    if (blockState.isAir && level.getBlockState(pos.below()).isAir) {
      return null
    }
    return super.findAcceptedNode(x, y, z)
  }

  companion object {
    fun getBlockPathTypeStatic(getter: BlockGetter, pos: MutableBlockPos, y: Int): BlockPathTypes {
      val i = pos.x
      val j = pos.y
      val k = pos.z
      var blockpathtypes = getBlockPathTypeRaw(getter, pos, y)
      if (blockpathtypes == BlockPathTypes.OPEN && j >= getter.minBuildHeight + 1) {
        val blockpathtypes1: BlockPathTypes = getBlockPathTypeRaw(getter, pos.set(i, j - 1, k), y)
        blockpathtypes =
            if (
                blockpathtypes1 != BlockPathTypes.WALKABLE &&
                    blockpathtypes1 != BlockPathTypes.OPEN &&
                    blockpathtypes1 != BlockPathTypes.WATER &&
                    blockpathtypes1 != BlockPathTypes.LAVA
            )
                BlockPathTypes.WALKABLE
            else BlockPathTypes.OPEN
      }

      if (blockpathtypes == BlockPathTypes.WALKABLE) {
        blockpathtypes = checkNeighbourBlocks(getter, pos.set(i, j, k), blockpathtypes)
      }

      return blockpathtypes
    }

    fun getBlockPathTypeRaw(level: BlockGetter, pos: BlockPos, y: Int): BlockPathTypes {
      val blockState = level.getBlockState(pos)
      if (WitherBoss.canDestroy(blockState)) {
        return BlockPathTypes.OPEN // The mob can walk through this block if it's destructible
      }

      // Check for air directly above a solid block
      if (
          blockState.isAir &&
              (level.getBlockState(pos.below()).isSolidRender(level, pos.below()) || pos.y <= y)
      ) {
        return BlockPathTypes.OPEN // Treat air above solid blocks as walkable.
      }

      // Remaining cases
      if (!blockState.isAir) {
        return BlockPathTypes.BLOCKED // Default to BLOCKED if none of the conditions are met.
      }
      return BlockPathTypes.OPEN // Default open for any other non-handled cases.
    }
  }
}
