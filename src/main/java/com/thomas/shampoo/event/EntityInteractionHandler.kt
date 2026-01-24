package com.thomas.shampoo.event;

import com.thomas.shampoo.ShampooMod;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.animal.CatVariant;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Minecart;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ShampooMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EntityInteractionHandler {

    @SubscribeEvent
    public static void onEntityInteract(PlayerInteractEvent.EntityInteractSpecific event) {
        // Check if the entity is a cat and the item used is a name tag
        Entity target = event.getTarget();
        Level level = event.getLevel();
        Player player = event.getEntity();
        if (target instanceof Cat cat && event.getItemStack().getItem() == Items.NAME_TAG) {
            // Check if the name tag has the name "Morgana"
            if ("Morgana".equals(event.getItemStack().getHoverName().getString()) && cat.getVariant() == BuiltInRegistries.CAT_VARIANT.get(CatVariant.BLACK)) {
                if (!level.isClientSide()) {
                    // Create a minecart at the cat's location
                    Minecart minecart = EntityType.MINECART.create(level);
                    if (minecart != null) {
                        minecart.setPos(cat.getX(), cat.getY(), cat.getZ());
                        level.addFreshEntity(minecart);
                        cat.remove(Entity.RemovalReason.DISCARDED);  // Remove the cat
                    }
                }
                event.setCancellationResult(InteractionResult.SUCCESS);
                event.setCanceled(true);
            }
        }
    }
}
