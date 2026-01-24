package com.thomas.shampoo.item;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.InteractionResult;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import static com.thomas.shampoo.item.ItemInit.WUCKET;

public class BaterWucketItem extends Item {
    public BaterWucketItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        Level world = context.getLevel();
        BlockPos pos = context.getClickedPos().relative(context.getClickedFace());

        if (!world.isClientSide && world.isEmptyBlock(pos)) { // Ensures the block placement position is empty
            BlockState blockState = Blocks.IRON_BLOCK.defaultBlockState();
            world.playSound(null, pos, SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
            world.setBlock(pos, blockState, 3); // Places an iron block in the world

            if (!context.getPlayer().isCreative()) {
                context.getItemInHand().shrink(1); // Consume the Bater Wucket
                context.getPlayer().getInventory().add(new ItemStack(WUCKET.get())); // Adds a Wucket to player's inventory
            }
            return InteractionResult.sidedSuccess(world.isClientSide);
        }
        return InteractionResult.PASS;
    }
}
