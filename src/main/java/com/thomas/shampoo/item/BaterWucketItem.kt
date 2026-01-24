package com.thomas.shampoo.item

import net.minecraft.sounds.SoundEvents
import net.minecraft.sounds.SoundSource
import net.minecraft.world.InteractionResult
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.context.UseOnContext
import net.minecraft.world.level.block.Blocks

class BaterWucketItem(properties: Properties) : Item(properties) {
  override fun useOn(context: UseOnContext): InteractionResult {
    val world = context.level
    val pos = context.clickedPos.relative(context.clickedFace)

    if (
        !world.isClientSide && world.isEmptyBlock(pos)
    ) { // Ensures the block placement position is empty
      val blockState = Blocks.IRON_BLOCK.defaultBlockState()
      world.playSound(null, pos, SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS, 1.0f, 1.0f)
      world.setBlock(pos, blockState, 3) // Places an iron block in the world

      if (!context.player!!.isCreative) {
        context.itemInHand.shrink(1) // Consume the Bater Wucket
        context.player!!
            .inventory
            .add(ItemStack(ItemInit.WUCKET.get())) // Adds a Wucket to player's inventory
      }
      return InteractionResult.sidedSuccess(false)
    }
    return InteractionResult.PASS
  }
}
