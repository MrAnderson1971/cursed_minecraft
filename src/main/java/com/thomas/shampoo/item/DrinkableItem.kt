package com.thomas.shampoo.item

import net.minecraft.sounds.SoundEvent
import net.minecraft.sounds.SoundEvents
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.UseAnim
import net.minecraft.world.level.Level

class DrinkableItem(properties: Properties) : Item(properties) {
  override fun getUseAnimation(stack: ItemStack): UseAnim {
    return UseAnim.DRINK // Set the use animation to DRINK
  }

  override fun getDrinkingSound(): SoundEvent {
    return SoundEvents.GENERIC_DRINK
  }

  override fun getEatingSound(): SoundEvent {
    return SoundEvents.GENERIC_DRINK
  }

  override fun finishUsingItem(stack: ItemStack, world: Level, entity: LivingEntity): ItemStack {
    // Call the super method to apply the effects and decrease the stack size
    val resultStack = super.finishUsingItem(stack, world, entity)

    // Return an empty ItemStack if the stack size goes to 0 to ensure no container item is left
    if (resultStack.isEmpty) {
      return ItemStack.EMPTY // Returns an empty ItemStack if all were consumed
    }
    // Normally, the returned stack is the input stack with one less item, unless a container item
    // is specified
    return resultStack // Return the decremented stack without adding a container
  }
}
