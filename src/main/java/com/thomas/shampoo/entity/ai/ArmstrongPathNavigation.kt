package com.thomas.shampoo.entity.ai

import net.minecraft.core.BlockPos
import net.minecraft.core.particles.ParticleTypes
import net.minecraft.server.level.ServerLevel
import net.minecraft.util.Mth
import net.minecraft.world.entity.Mob
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation
import net.minecraft.world.entity.boss.wither.WitherBoss
import net.minecraft.world.level.Level
import net.minecraft.world.level.pathfinder.BlockPathTypes
import kotlin.math.min

open class ArmstrongPathNavigation(mob: Mob, level: Level) : GroundPathNavigation(mob, level) {
  override fun followThePath() {
    if (this.path == null) {
      return
    }
    val pathIndex = this.path!!.nextNodeIndex

    // Check only the next segment of the path or a few blocks ahead rather than the entire path
    // segment ahead.
    val checkAheadRange =
        min(
            pathIndex + 3,
            this.path!!.nodeCount,
        ) // Check up to 3 nodes ahead for more focused block destruction

    // debugWool();
    this.checkAndDestroyBlocks(checkAheadRange)

    super.followThePath()
  }

  private fun debugWool() {
    if (this.path == null) {
      return
    }
    for (i in path!!.nextNodeIndex..<path!!.nodeCount) {
      val nextNodePos: BlockPos?
      try {
        nextNodePos = path!!.getNode(i).asBlockPos()
      } catch (_: IndexOutOfBoundsException) {
        return
      }
      if (level is ServerLevel) {
        (level as ServerLevel).sendParticles(
            ParticleTypes.FLAME,
            nextNodePos.x.toDouble(),
            nextNodePos.y.toDouble(),
            nextNodePos.z.toDouble(),
            1,
            0.0,
            0.0,
            0.0,
            0.0,
        )
      }
    }
  }

  private fun checkAndDestroyBlocks(checkAheadRange: Int) {
    if (path == null) {
      return
    }
    val width = Mth.ceil(this.mob.bbWidth)
    val height = Mth.ceil(this.mob.bbHeight)

    for (i in this.path!!.getNextNodeIndex()..<checkAheadRange) {
      try {
        val currentPos = this.path!!.getNode(i).asBlockPos()
        val nextPos =
            if (i + 1 < this.path!!.nodeCount) this.path!!.getNode(i + 1).asBlockPos()
            else null

        val isDiagonal =
            nextPos != null &&
                currentPos.x != nextPos.x &&
                currentPos.z != nextPos.z
        val range = if (isDiagonal) width else width / 2 // Increase range if moving diagonally

        for (dx in -range..range) {
          for (dz in -range..range) {
            for (dy in 0..height) {
              val blockToCheck = currentPos.offset(dx, dy, dz)
              if (shouldDestroyBlock(blockToCheck)) {
                this.level.destroyBlock(blockToCheck, true)
              }
            }
          }
        }
      } catch (_: IndexOutOfBoundsException) {
        return
      }
    }
  }

  private fun shouldDestroyBlock(pos: BlockPos): Boolean {
    val state = this.level.getBlockState(pos)
    return WitherBoss.canDestroy(state) ||
        !state
            .fluidState
            .isEmpty // This method should return true for blocks that can be destroyed by the
                       // entity.
  }

  override fun hasValidPathType(pathTypes: BlockPathTypes): Boolean {
    return pathTypes != BlockPathTypes.OPEN
  }
}
