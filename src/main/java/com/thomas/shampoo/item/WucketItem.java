package com.thomas.shampoo.item;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import static com.thomas.shampoo.item.ItemInit.BATER_WUCKET;

public class WucketItem extends Item {
    public WucketItem(Properties properties) {
        super(properties);
    }

    @Override
    // Replace with bater wucket on right click of iron block.
    public @NotNull InteractionResult useOn(UseOnContext context) {
        Level world = context.getLevel();
        if (!world.isClientSide) {
            BlockPos pos = context.getClickedPos();
            BlockState state = world.getBlockState(pos);
            ItemStack stack = context.getItemInHand();
            ServerPlayer player = (ServerPlayer) context.getPlayer();

            if (state.is(Blocks.IRON_BLOCK)) {
                world.removeBlock(pos, false);
                world.playSound(null, pos, SoundEvents.BUCKET_FILL, SoundSource.BLOCKS, 1.0F, 1.0F);
                stack.shrink(1); // Reduces the number of wuckets in hand by one
                if (!player.getInventory().add(new ItemStack(BATER_WUCKET.get()))) {
                    player.drop(new ItemStack(BATER_WUCKET.get()), false);
                }
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }
}
