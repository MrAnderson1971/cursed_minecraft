package com.thomas.shampoo.item

import com.thomas.shampoo.item.ItemInit.BATER_WUCKET
import net.minecraft.server.level.ServerPlayer
import net.minecraft.sounds.SoundEvents
import net.minecraft.sounds.SoundSource
import net.minecraft.world.InteractionResult
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.context.UseOnContext
import net.minecraft.world.level.block.Blocks

class WucketItem(properties: Properties) : Item(properties) {
  // Replace with bater wucket on right click of iron block.
  override fun useOn(context: UseOnContext): InteractionResult {
    val world = context.level
    if (!world.isClientSide) {
      val pos = context.clickedPos
      val state = world.getBlockState(pos)
      val stack = context.itemInHand
      val player = context.player as ServerPlayer?

      if (state.`is`(Blocks.IRON_BLOCK)) {
        world.removeBlock(pos, false)
        world.playSound(null, pos, SoundEvents.BUCKET_FILL, SoundSource.BLOCKS, 1.0f, 1.0f)
        stack.shrink(1) // Reduces the number of wuckets in hand by one
        if (!player!!.inventory.add(ItemStack(BATER_WUCKET.get()))) {
          player.drop(ItemStack(BATER_WUCKET.get()), false)
        }
        return InteractionResult.SUCCESS
      }
    }
    return InteractionResult.PASS
  }
}
