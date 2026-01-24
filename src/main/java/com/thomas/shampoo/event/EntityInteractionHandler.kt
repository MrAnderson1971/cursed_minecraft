package com.thomas.shampoo.event

import com.thomas.shampoo.ShampooMod
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.animal.Cat
import net.minecraft.world.entity.animal.CatVariant
import net.minecraft.world.item.Items
import net.minecraftforge.event.entity.player.PlayerInteractEvent.EntityInteractSpecific
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod.EventBusSubscriber

@EventBusSubscriber(modid = ShampooMod.MODID, bus = EventBusSubscriber.Bus.FORGE)
object EntityInteractionHandler {
  @SubscribeEvent
  fun onEntityInteract(event: EntityInteractSpecific) {
    // Check if the entity is a cat and the item used is a name tag
    val target = event.target
    val level = event.level
    if (target is Cat && event.itemStack.item === Items.NAME_TAG) {
      // Check if the name tag has the name "Morgana"
      if (
          "Morgana" == event.itemStack.getHoverName().string &&
              target.variant === BuiltInRegistries.CAT_VARIANT.get(CatVariant.BLACK)
      ) {
        if (!level.isClientSide()) {
          // Create a minecart at the cat's location
          val minecart = EntityType.MINECART.create(level)
          if (minecart != null) {
            minecart.setPos(target.x, target.y, target.z)
            level.addFreshEntity(minecart)
            target.remove(Entity.RemovalReason.DISCARDED) // Remove the cat
          }
        }
          event.cancellationResult = InteractionResult.SUCCESS
          event.isCanceled = true
      }
    }
  }
}
