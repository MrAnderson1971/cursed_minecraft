package com.thomas.shampoo.item

import net.minecraft.ChatFormatting
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.level.Level

class ExplodingBellItem(properties: Properties) : Item(properties) {
  // Trigger an explosion when the item is added to the player's inventory
  override fun inventoryTick(
      stack: ItemStack,
      world: Level,
      entity: Entity,
      itemSlot: Int,
      isSelected: Boolean,
  ) {
    if (!world.isClientSide && entity is Player) {
      // Check if the player is in Creative mode
      if (!entity.isCreative) {
        val pos = entity.blockPosition()

        // Create an explosion at the player's location
        world.explode(
            null,
            pos.x.toDouble(),
            pos.y.toDouble(),
            pos.z.toDouble(),
            4.0f,
            true,
            Level.ExplosionInteraction.TNT,
        )

        // Remove the item from inventory after explosion
        stack.shrink(1)
      }
    }
  }

  override fun appendHoverText(
      stack: ItemStack,
      world: Level?,
      tooltip: MutableList<Component>,
      flag: TooltipFlag,
  ) {
    super.appendHoverText(stack, world, tooltip, flag)
    // Adding custom lore, now in italic and red
    tooltip.add(
        Component.literal("Last chance to look at me, Hector.")
            .withStyle(ChatFormatting.RED, ChatFormatting.ITALIC)
    )
  }
}
