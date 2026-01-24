package com.thomas.shampoo.event

import com.thomas.shampoo.ShampooMod
import com.thomas.shampoo.entity.EntityInit
import com.thomas.shampoo.entity.StevenArmstrong
import net.minecraft.core.BlockPos
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.CarvedPumpkinBlock
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.pattern.BlockInWorld
import net.minecraft.world.level.block.state.pattern.BlockPattern
import net.minecraft.world.level.block.state.pattern.BlockPattern.BlockPatternMatch
import net.minecraft.world.level.block.state.pattern.BlockPatternBuilder
import net.minecraft.world.level.block.state.predicate.BlockStatePredicate
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.event.level.BlockEvent.EntityPlaceEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod.EventBusSubscriber

@EventBusSubscriber(
    modid = ShampooMod.MODID,
    bus = EventBusSubscriber.Bus.FORGE,
    value = [Dist.CLIENT],
)
object BlockPlaceHandler {
  private var stevenArmstrongFull: BlockPattern? = null

  private val PUMPKINS_PREDICATE: (BlockState?) -> Boolean = { b ->
    b != null && (b.`is`(Blocks.CARVED_PUMPKIN) || b.`is`(Blocks.JACK_O_LANTERN))
  }

  @SubscribeEvent
  fun onBlockPlace(event: EntityPlaceEvent) {
    if (event.entity !is Player) {
      return
    }

    val world = event.level as Level
    val pos = event.pos
    val placedBlockState = world.getBlockState(pos)

    // Check if a Pumpkin or Jack o'Lantern is placed
    if (
        placedBlockState.block === Blocks.CARVED_PUMPKIN ||
            placedBlockState.block === Blocks.JACK_O_LANTERN
    ) {
      val blockPatternMatch = orCreateStevenArmstrongFull.find(world, pos)
      if (blockPatternMatch != null) {
        val armstrong = StevenArmstrong(EntityInit.STEVEN_ARMSTRONG.get(), world)
        spawnArmstrongInWorld(
            world,
            blockPatternMatch,
            armstrong,
            blockPatternMatch.getBlock(1, 2, 0).pos,
        )
      }
    }
  }

  private fun spawnArmstrongInWorld(
      level: Level,
      blockPatternMatch: BlockPatternMatch,
      entity: Entity,
      pos: BlockPos,
  ) {
    CarvedPumpkinBlock.clearPatternBlocks(level, blockPatternMatch)
    entity.moveTo(
        pos.x.toDouble() + 0.5,
        pos.y.toDouble() + 0.05,
        pos.z.toDouble() + 0.5,
        0.0f,
        0.0f,
    )
    level.addFreshEntity(entity)

    CarvedPumpkinBlock.updatePatternBlocks(level, blockPatternMatch)
  }

  private val orCreateStevenArmstrongFull: BlockPattern
    get() {
      if (stevenArmstrongFull == null) {
        stevenArmstrongFull =
            BlockPatternBuilder.start()
                .aisle("~^~", "###", "~#~")
                .where('^', BlockInWorld.hasState(PUMPKINS_PREDICATE))
                .where(
                    '#',
                    BlockInWorld.hasState(BlockStatePredicate.forBlock(Blocks.DIAMOND_BLOCK)),
                )
                .where('~') { b: BlockInWorld? -> b!!.getState().isAir }
                .build()
      }

      return stevenArmstrongFull!!
    }
}
