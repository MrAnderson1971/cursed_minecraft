package com.thomas.shampoo.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.phys.shapes.CollisionContext;

public class JadePyramidBlock extends Block {

    // Define the voxel shapes for each tier of the pyramid.
    private static final VoxelShape BASE = Block.box(0, 0, 0, 16, 4, 16);
    private static final VoxelShape MIDDLE = Block.box(2, 4, 2, 14, 8, 14);
    private static final VoxelShape TOP = Block.box(4, 8, 4, 12, 12, 12);
    private static final VoxelShape APEX = Block.box(6, 12, 6, 10, 16, 10);

    // Combine the tiers to create the full pyramid shape.
    private static final VoxelShape PYRAMID_SHAPE = Shapes.or(BASE, MIDDLE, TOP, APEX);

    public JadePyramidBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return PYRAMID_SHAPE;
    }
}
