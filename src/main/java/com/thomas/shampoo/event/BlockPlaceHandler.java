package com.thomas.shampoo.event;

import com.thomas.shampoo.entity.EntityInit;
import com.thomas.shampoo.entity.StevenArmstrong;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.block.state.pattern.BlockPattern;
import net.minecraft.world.level.block.state.pattern.BlockPatternBuilder;
import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nullable;
import java.util.function.Predicate;

import static com.thomas.shampoo.ShampooMod.MODID;
import static net.minecraft.world.level.block.CarvedPumpkinBlock.clearPatternBlocks;
import static net.minecraft.world.level.block.CarvedPumpkinBlock.updatePatternBlocks;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class BlockPlaceHandler {
    @Nullable
    private static BlockPattern stevenArmstrongFull;

    private static final Predicate<BlockState> PUMPKINS_PREDICATE = (b) ->
         b != null && (b.is(Blocks.CARVED_PUMPKIN) || b.is(Blocks.JACK_O_LANTERN));

    @SubscribeEvent
    public static void onBlockPlace(BlockEvent.EntityPlaceEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }

        Level world = (Level) event.getLevel();
        BlockPos pos = event.getPos();
        BlockState placedBlockState = world.getBlockState(pos);

        // Check if a Pumpkin or Jack o'Lantern is placed
        if (placedBlockState.getBlock() == Blocks.CARVED_PUMPKIN || placedBlockState.getBlock() == Blocks.JACK_O_LANTERN) {
            BlockPattern.BlockPatternMatch blockpattern$blockpatternmatch1 = getOrCreateStevenArmstrongFull().find(world, pos);
            if (blockpattern$blockpatternmatch1 != null) {
                StevenArmstrong armstrong = new StevenArmstrong(EntityInit.STEVEN_ARMSTRONG.get(), world);
                spawnArmstrongInWorld(world, blockpattern$blockpatternmatch1, armstrong,
                        blockpattern$blockpatternmatch1.getBlock(1, 2, 0).getPos());
            }
        }
    }

    private static void spawnArmstrongInWorld(Level level, BlockPattern.BlockPatternMatch blockPatternMatch, Entity entity, BlockPos pos) {
        clearPatternBlocks(level, blockPatternMatch);
        entity.moveTo((double)pos.getX() + 0.5D, (double)pos.getY() + 0.05D, (double)pos.getZ() + 0.5D, 0.0F, 0.0F);
        level.addFreshEntity(entity);

        updatePatternBlocks(level, blockPatternMatch);
    }

    private static BlockPattern getOrCreateStevenArmstrongFull() {
        if (stevenArmstrongFull == null) {
            stevenArmstrongFull = BlockPatternBuilder.start().aisle("~^~", "###", "~#~")
                    .where('^', BlockInWorld.hasState(PUMPKINS_PREDICATE))
                    .where('#', BlockInWorld.hasState(BlockStatePredicate.forBlock(Blocks.DIAMOND_BLOCK)))
                    .where('~', (b) -> b.getState().isAir()
            ).build();
        }

        return stevenArmstrongFull;
    }
}
