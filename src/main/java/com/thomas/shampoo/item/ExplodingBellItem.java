package com.thomas.shampoo.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.BlockPos;

import javax.annotation.Nullable;
import java.util.List;

public class ExplodingBellItem extends Item {
    public ExplodingBellItem(Properties properties) {
        super(properties);
    }

    // Trigger an explosion when the item is added to the player's inventory
    @Override
    public void inventoryTick(ItemStack stack, Level world, Entity entity, int itemSlot, boolean isSelected) {
        if (!world.isClientSide && entity instanceof Player player) {
            // Check if the player is in Creative mode
            if (!player.isCreative()) {
                BlockPos pos = player.blockPosition();

                // Create an explosion at the player's location
                world.explode(player, pos.getX(), pos.getY(), pos.getZ(), 4.0F, true, Level.ExplosionInteraction.TNT);

                // Remove the item from inventory after explosion
                stack.shrink(1);
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, world, tooltip, flag);
        // Adding custom lore, now in italic and red
        tooltip.add(Component.literal("Last chance to look at me, Hector.")
                .withStyle(ChatFormatting.RED, ChatFormatting.ITALIC));
    }
}
