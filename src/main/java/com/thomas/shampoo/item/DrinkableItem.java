package com.thomas.shampoo.item;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class DrinkableItem extends Item {
    public DrinkableItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack stack) {
        return UseAnim.DRINK;  // Set the use animation to DRINK
    }

    public @NotNull SoundEvent getDrinkingSound() {
        return SoundEvents.GENERIC_DRINK;
    }

    public @NotNull SoundEvent getEatingSound() {
        return SoundEvents.GENERIC_DRINK;
    }

    @Override
    public @NotNull ItemStack finishUsingItem(@NotNull ItemStack stack, @NotNull Level world, @NotNull LivingEntity entity) {
        // Call the super method to apply the effects and decrease the stack size
        ItemStack resultStack = super.finishUsingItem(stack, world, entity);

        // Return an empty ItemStack if the stack size goes to 0 to ensure no container item is left
        if (resultStack.isEmpty()) {
            return ItemStack.EMPTY; // Returns an empty ItemStack if all were consumed
        }
        // Normally, the returned stack is the input stack with one less item, unless a container item is specified
        return resultStack;  // Return the decremented stack without adding a container
    }
}
