package com.thomas.shampoo.entity.ai;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.FlyNodeEvaluator;
import org.jetbrains.annotations.NotNull;

public class ArmstrongNodeEvaluator extends FlyNodeEvaluator {

    @Override
    public @NotNull BlockPathTypes getBlockPathType(@NotNull BlockGetter p_77576_, int p_77577_, int p_77578_, int p_77579_) {
        return getBlockPathTypeStatic(p_77576_, new BlockPos.MutableBlockPos(p_77577_, p_77578_, p_77579_));
    }

    public static @NotNull BlockPathTypes getBlockPathTypeStatic(BlockGetter getter, BlockPos.MutableBlockPos pos) {
        int i = pos.getX();
        int j = pos.getY();
        int k = pos.getZ();
        BlockPathTypes blockpathtypes = ArmstrongNodeEvaluator.getBlockPathTypeRaw(getter, pos);
        if (blockpathtypes == BlockPathTypes.OPEN && j >= getter.getMinBuildHeight() + 1) {
            BlockPathTypes blockpathtypes1 = getBlockPathTypeRaw(getter, pos.set(i, j - 1, k));
            blockpathtypes = blockpathtypes1 != BlockPathTypes.WALKABLE && blockpathtypes1 != BlockPathTypes.OPEN && blockpathtypes1 != BlockPathTypes.WATER && blockpathtypes1 != BlockPathTypes.LAVA ? BlockPathTypes.WALKABLE : BlockPathTypes.OPEN;
        }

        if (blockpathtypes == BlockPathTypes.WALKABLE) {
            blockpathtypes = checkNeighbourBlocks(getter, pos.set(i, j, k), blockpathtypes);
        }

        return blockpathtypes;
    }

    protected static @NotNull BlockPathTypes getBlockPathTypeRaw(BlockGetter level, @NotNull BlockPos pos) {
        BlockState blockState = level.getBlockState(pos);
        if (WitherBoss.canDestroy(blockState)) {
            return BlockPathTypes.OPEN; // The mob can walk through this block if it's destructible
        }

        // Check for air directly above a solid block
        if (blockState.isAir() && level.getBlockState(pos.below()).isSolidRender(level, pos.below())) {
            return BlockPathTypes.OPEN; // Treat air above solid blocks as walkable.
        }

        // Remaining cases
        if (!blockState.isAir()) {
            return BlockPathTypes.BLOCKED; // Default to BLOCKED if none of the conditions are met.
        }
        return BlockPathTypes.OPEN; // Default open for any other non-handled cases.
    }
}
