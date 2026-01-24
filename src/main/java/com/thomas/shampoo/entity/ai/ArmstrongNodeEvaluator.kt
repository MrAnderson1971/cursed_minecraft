package com.thomas.shampoo.entity.ai;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.FlyNodeEvaluator;
import net.minecraft.world.level.pathfinder.Node;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class ArmstrongNodeEvaluator extends FlyNodeEvaluator {

    @Override
    public @NotNull BlockPathTypes getBlockPathType(@NotNull BlockGetter block, int x, int y, int z) {
        return getBlockPathTypeStatic(block, new BlockPos.MutableBlockPos(x, y, z), y);
    }

    public static @NotNull BlockPathTypes getBlockPathTypeStatic(BlockGetter getter, BlockPos.MutableBlockPos pos, int y) {
        int i = pos.getX();
        int j = pos.getY();
        int k = pos.getZ();
        BlockPathTypes blockpathtypes = ArmstrongNodeEvaluator.getBlockPathTypeRaw(getter, pos, y);
        if (blockpathtypes == BlockPathTypes.OPEN && j >= getter.getMinBuildHeight() + 1) {
            BlockPathTypes blockpathtypes1 = getBlockPathTypeRaw(getter, pos.set(i, j - 1, k), y);
            blockpathtypes = blockpathtypes1 != BlockPathTypes.WALKABLE && blockpathtypes1 != BlockPathTypes.OPEN && blockpathtypes1 != BlockPathTypes.WATER && blockpathtypes1 != BlockPathTypes.LAVA ? BlockPathTypes.WALKABLE : BlockPathTypes.OPEN;
        }

        if (blockpathtypes == BlockPathTypes.WALKABLE) {
            blockpathtypes = checkNeighbourBlocks(getter, pos.set(i, j, k), blockpathtypes);
        }

        return blockpathtypes;
    }

    protected static @NotNull BlockPathTypes getBlockPathTypeRaw(BlockGetter level, @NotNull BlockPos pos, int y) {
        BlockState blockState = level.getBlockState(pos);
        if (WitherBoss.canDestroy(blockState)) {
            return BlockPathTypes.OPEN; // The mob can walk through this block if it's destructible
        }

        // Check for air directly above a solid block
        if (blockState.isAir() && (level.getBlockState(pos.below()).isSolidRender(level, pos.below()) || pos.getY() <= y)) {
            return BlockPathTypes.OPEN; // Treat air above solid blocks as walkable.
        }

        // Remaining cases
        if (!blockState.isAir()) {
            return BlockPathTypes.BLOCKED; // Default to BLOCKED if none of the conditions are met.
        }
        return BlockPathTypes.OPEN; // Default open for any other non-handled cases.
    }

    @Override
    @Nullable
    protected Node findAcceptedNode(int x, int y, int z) {
        BlockGetter level = this.mob.level();  // Assuming 'mob' has a reference to the level.
        BlockPos pos = new BlockPos(x, y, z);
        BlockState blockState = level.getBlockState(pos);

        // If the block is air and the block directly below is also air, return null.
        if (blockState.isAir() && level.getBlockState(pos.below()).isAir()) {
            return null;
        }
        return super.findAcceptedNode(x, y, z);
    }
}
