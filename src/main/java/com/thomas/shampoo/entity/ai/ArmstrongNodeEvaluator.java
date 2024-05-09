package com.thomas.shampoo.entity.ai;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
import java.util.EnumSet;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.PathNavigationRegion;
import net.minecraft.world.level.block.BaseRailBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.*;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class ArmstrongNodeEvaluator extends NodeEvaluator {

    private final Long2ObjectMap<BlockPathTypes> pathTypesByPosCache = new Long2ObjectOpenHashMap<>();
    private final Object2BooleanMap<AABB> collisionCache = new Object2BooleanOpenHashMap<>();

    public void prepare(@NotNull PathNavigationRegion region, @NotNull Mob mob) {
        super.prepare(region, mob);
        mob.onPathfindingStart();
    }

    public void done() {
        this.mob.onPathfindingDone();
        this.pathTypesByPosCache.clear();
        this.collisionCache.clear();
        super.done();
    }

    public @NotNull Node getStart() {
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
        int i = this.mob.getBlockY();
        BlockState blockstate = this.level.getBlockState(blockpos$mutableblockpos.set(this.mob.getX(), (double)i, this.mob.getZ()));
        if (!this.mob.canStandOnFluid(blockstate.getFluidState())) {
            if (this.canFloat() && this.mob.isInWater()) {
                while(true) {
                    if (!blockstate.is(Blocks.WATER) && blockstate.getFluidState() != Fluids.WATER.getSource(false)) {
                        --i;
                        break;
                    }

                    ++i;
                    blockstate = this.level.getBlockState(blockpos$mutableblockpos.set(this.mob.getX(), (double)i, this.mob.getZ()));
                }
            } else if (this.mob.onGround()) {
                i = Mth.floor(this.mob.getY() + 0.5D);
            } else {
                BlockPos blockpos;
                for(blockpos = this.mob.blockPosition(); (this.level.getBlockState(blockpos).isAir() || this.level.getBlockState(blockpos).isPathfindable(this.level, blockpos, PathComputationType.LAND)) && blockpos.getY() > this.mob.level().getMinBuildHeight(); blockpos = blockpos.below()) {
                }

                i = blockpos.above().getY();
            }
        } else {
            while(this.mob.canStandOnFluid(blockstate.getFluidState())) {
                ++i;
                blockstate = this.level.getBlockState(blockpos$mutableblockpos.set(this.mob.getX(), (double)i, this.mob.getZ()));
            }

            --i;
        }

        BlockPos blockpos1 = this.mob.blockPosition();
        if (!this.canStartAt(blockpos$mutableblockpos.set(blockpos1.getX(), i, blockpos1.getZ()))) {
            AABB aabb = this.mob.getBoundingBox();
            if (this.canStartAt(blockpos$mutableblockpos.set(aabb.minX, (double)i, aabb.minZ)) || this.canStartAt(blockpos$mutableblockpos.set(aabb.minX, (double)i, aabb.maxZ)) || this.canStartAt(blockpos$mutableblockpos.set(aabb.maxX, (double)i, aabb.minZ)) || this.canStartAt(blockpos$mutableblockpos.set(aabb.maxX, (double)i, aabb.maxZ))) {
                return this.getStartNode(blockpos$mutableblockpos);
            }
        }

        return this.getStartNode(new BlockPos(blockpos1.getX(), i, blockpos1.getZ()));
    }

    protected Node getStartNode(BlockPos pos) {
        Node node = this.getNode(pos);
        node.type = this.getBlockPathType(this.mob, node.asBlockPos());
        node.costMalus = this.mob.getPathfindingMalus(node.type);
        return node;
    }

    protected boolean canStartAt(BlockPos pos) {
        Block block = this.level.getBlockState(pos).getBlock();
        // Assuming isUnbreakable is a method that checks if the block is unbreakable
        return !isUnbreakable(block);
    }

    private boolean isUnbreakable(Block block) {
        // Example: Add unbreakable blocks here
        return block == Blocks.BEDROCK || block == Blocks.COMMAND_BLOCK || block == Blocks.BARRIER;
    }

    public Target getGoal(double x, double y, double z) {
        return this.getTargetFromNode(this.getNode(Mth.floor(x), Mth.floor(y), Mth.floor(z)));
    }

    public int getNeighbors(Node @NotNull [] neighbors, Node pos) {
        int i = 0;
        int j = 0;
        BlockPathTypes blockpathtypes = this.getCachedBlockType(this.mob, pos.x, pos.y + 1, pos.z);
        BlockPathTypes blockpathtypes1 = this.getCachedBlockType(this.mob, pos.x, pos.y, pos.z);
        if (this.mob.getPathfindingMalus(blockpathtypes) >= 0.0F && blockpathtypes1 != BlockPathTypes.STICKY_HONEY) {
            j = Mth.floor(Math.max(1.0F, this.mob.getStepHeight()));
        }

        double d0 = this.getFloorLevel(new BlockPos(pos.x, pos.y, pos.z));
        Node node = this.findAcceptedNode(pos.x, pos.y, pos.z + 1, j, d0, Direction.SOUTH, blockpathtypes1);
        if (this.isNeighborValid(node, pos)) {
            neighbors[i++] = node;
        }

        Node node1 = this.findAcceptedNode(pos.x - 1, pos.y, pos.z, j, d0, Direction.WEST, blockpathtypes1);
        if (this.isNeighborValid(node1, pos)) {
            neighbors[i++] = node1;
        }

        Node node2 = this.findAcceptedNode(pos.x + 1, pos.y, pos.z, j, d0, Direction.EAST, blockpathtypes1);
        if (this.isNeighborValid(node2, pos)) {
            neighbors[i++] = node2;
        }

        Node node3 = this.findAcceptedNode(pos.x, pos.y, pos.z - 1, j, d0, Direction.NORTH, blockpathtypes1);
        if (this.isNeighborValid(node3, pos)) {
            neighbors[i++] = node3;
        }

        Node node4 = this.findAcceptedNode(pos.x - 1, pos.y, pos.z - 1, j, d0, Direction.NORTH, blockpathtypes1);
        if (this.isDiagonalValid(pos, node1, node3, node4)) {
            neighbors[i++] = node4;
        }

        Node node5 = this.findAcceptedNode(pos.x + 1, pos.y, pos.z - 1, j, d0, Direction.NORTH, blockpathtypes1);
        if (this.isDiagonalValid(pos, node2, node3, node5)) {
            neighbors[i++] = node5;
        }

        Node node6 = this.findAcceptedNode(pos.x - 1, pos.y, pos.z + 1, j, d0, Direction.SOUTH, blockpathtypes1);
        if (this.isDiagonalValid(pos, node1, node, node6)) {
            neighbors[i++] = node6;
        }

        Node node7 = this.findAcceptedNode(pos.x + 1, pos.y, pos.z + 1, j, d0, Direction.SOUTH, blockpathtypes1);
        if (this.isDiagonalValid(pos, node2, node, node7)) {
            neighbors[i++] = node7;
        }

        return i;
    }

    protected boolean isNeighborValid(@Nullable Node node, Node other) {
        return node != null && !node.closed && (node.costMalus >= 0.0F || other.costMalus < 0.0F);
    }

    protected boolean isDiagonalValid(Node currentNode, @Nullable Node adjacentNodeX, @Nullable Node adjacentNodeZ, @Nullable Node diagonalNode) {
        if (diagonalNode != null && adjacentNodeZ != null && adjacentNodeX != null) {
            if (diagonalNode.closed) {
                // If the diagonal node is already closed, it's not valid for pathfinding.
                return false;
            } else if (adjacentNodeZ.y <= currentNode.y && adjacentNodeX.y <= currentNode.y) {
                // Check that both adjacent nodes are not above the current node and that no door blocks the diagonal path.
                if (adjacentNodeX.type != BlockPathTypes.WALKABLE_DOOR && adjacentNodeZ.type != BlockPathTypes.WALKABLE_DOOR && diagonalNode.type != BlockPathTypes.WALKABLE_DOOR) {
                    boolean isFenceDiagonal = adjacentNodeZ.type == BlockPathTypes.FENCE && adjacentNodeX.type == BlockPathTypes.FENCE && (double)this.mob.getBbWidth() < 0.5D;
                    // The diagonal node is valid if it's not closed and either lower than the current node or doesn't have a cost penalty, or if it's between two fences and the mob is narrow enough.
                    return diagonalNode.costMalus >= 0.0F && (adjacentNodeZ.y < currentNode.y || adjacentNodeZ.costMalus >= 0.0F || isFenceDiagonal) && (adjacentNodeX.y < currentNode.y || adjacentNodeX.costMalus >= 0.0F || isFenceDiagonal);
                } else {
                    // Path is blocked by a door.
                    return false;
                }
            } else {
                // One of the adjacent nodes is above the current node, making the diagonal step invalid.
                return false;
            }
        } else {
            // If any of the necessary nodes are null, the diagonal move is not valid.
            return false;
        }
    }

    private static boolean doesBlockHavePartialCollision(BlockPathTypes pathType) {
        return pathType == BlockPathTypes.FENCE || pathType == BlockPathTypes.DOOR_WOOD_CLOSED || pathType == BlockPathTypes.DOOR_IRON_CLOSED;
    }

    private boolean canReachWithoutCollision(Node node) {
        AABB aabb = this.mob.getBoundingBox();
        Vec3 vec3 = new Vec3((double)node.x - this.mob.getX() + aabb.getXsize() / 2.0D, (double)node.y - this.mob.getY() + aabb.getYsize() / 2.0D, (double)node.z - this.mob.getZ() + aabb.getZsize() / 2.0D);
        int i = Mth.ceil(vec3.length() / aabb.getSize());
        vec3 = vec3.scale((double)(1.0F / (float)i));

        for(int j = 1; j <= i; ++j) {
            aabb = aabb.move(vec3);
            if (this.hasCollisions(aabb)) {
                return false;
            }
        }

        return true;
    }

    protected double getFloorLevel(BlockPos floor) {
        return (this.canFloat() || this.isAmphibious()) && this.level.getFluidState(floor).is(FluidTags.WATER) ? (double)floor.getY() + 0.5D : getFloorLevel(this.level, floor);
    }

    public static double getFloorLevel(BlockGetter p_77612_, BlockPos p_77613_) {
        BlockPos blockpos = p_77613_.below();
        VoxelShape voxelshape = p_77612_.getBlockState(blockpos).getCollisionShape(p_77612_, blockpos);
        return (double)blockpos.getY() + (voxelshape.isEmpty() ? 0.0D : voxelshape.max(Direction.Axis.Y));
    }

    protected boolean isAmphibious() {
        return false;
    }

    @Nullable
    protected Node findAcceptedNode(int x, int y, int z, int stepUpTries, double previousFloorLevel, Direction moveDirection, BlockPathTypes intendedPathType) {
        Node node = null;
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
        double currentFloorLevel = this.getFloorLevel(mutableBlockPos.set(x, y, z));

        // If the difference in floor levels is greater than the mob's jump height, return null
        if (currentFloorLevel - previousFloorLevel > this.getMobJumpHeight()) {
            return null;
        }

        BlockPathTypes currentBlockType = this.getCachedBlockType(this.mob, x, y, z);
        float pathfindingPenalty = this.mob.getPathfindingMalus(currentBlockType);
        double mobWidthHalf = this.mob.getBbWidth() / 2.0;

        // Check if the node is initially acceptable
        if (pathfindingPenalty >= 0.0F) {
            node = this.getNodeAndUpdateCostToMax(x, y, z, currentBlockType, pathfindingPenalty);
        }

        // Check for collisions if the block type partially obstructs movement
        if (doesBlockHavePartialCollision(intendedPathType) && node != null && node.costMalus >= 0.0F && !this.canReachWithoutCollision(node)) {
            node = null;
        }

        // Further checks based on the type of the block
        if (currentBlockType != BlockPathTypes.WALKABLE && (!this.isAmphibious() || currentBlockType != BlockPathTypes.WATER)) {
            if ((node == null || node.costMalus < 0.0F) && stepUpTries > 0 && (currentBlockType != BlockPathTypes.FENCE || this.canWalkOverFences()) && currentBlockType != BlockPathTypes.UNPASSABLE_RAIL && currentBlockType != BlockPathTypes.TRAPDOOR && currentBlockType != BlockPathTypes.POWDER_SNOW) {
                node = this.findAcceptedNode(x, y + 1, z, stepUpTries - 1, previousFloorLevel, moveDirection, intendedPathType);
            }

            // Handle water specific behavior
            if (!this.isAmphibious() && currentBlockType == BlockPathTypes.WATER && !this.canFloat()) {
                while (y > this.mob.level().getMinBuildHeight()) {
                    --y;
                    BlockPathTypes belowBlockType = this.getCachedBlockType(this.mob, x, y, z);
                    if (belowBlockType != BlockPathTypes.WATER) {
                        return node;
                    }
                    node = this.getNodeAndUpdateCostToMax(x, y, z, belowBlockType, this.mob.getPathfindingMalus(belowBlockType));
                }
            }

            // Handle open blocks leading potentially to falls
            if (currentBlockType == BlockPathTypes.OPEN) {
                int fallDistance = 0;
                while (currentBlockType == BlockPathTypes.OPEN) {
                    --y;
                    // Remove or adjust the condition below because the mob does not take fall damage
                    if (y < this.mob.level().getMinBuildHeight()) {
                        return this.getBlockedNode(x, y, z);
                    }

                    currentBlockType = this.getCachedBlockType(this.mob, x, y, z);
                    pathfindingPenalty = this.mob.getPathfindingMalus(currentBlockType);
                    if (currentBlockType != BlockPathTypes.OPEN && pathfindingPenalty >= 0.0F) {
                        node = this.getNodeAndUpdateCostToMax(x, y, z, currentBlockType, pathfindingPenalty);
                        break;
                    }

                    if (pathfindingPenalty < 0.0F) {
                        return this.getBlockedNode(x, y, z);
                    }
                }
            }

            // Check if node is blocked by obstacles
            if (doesBlockHavePartialCollision(currentBlockType) && node == null) {
                node = this.getNode(x, y, z);
                node.closed = true;
                node.type = currentBlockType;
                node.costMalus = currentBlockType.getMalus();
            }
        }

        return node;
    }

    private double getMobJumpHeight() {
        return 10;
    }

    private Node getNodeAndUpdateCostToMax(int x, int y, int z, BlockPathTypes pathType, float cost) {
        Node node = this.getNode(x, y, z);
        node.type = pathType;
        node.costMalus = Math.max(node.costMalus, cost);
        return node;
    }

    private Node getBlockedNode(int x, int y, int z) {
        Node node = this.getNode(x, y, z);
        node.type = BlockPathTypes.BLOCKED;
        node.costMalus = -1.0F;
        return node;
    }

    private boolean hasCollisions(AABB aabb) {
        return this.collisionCache.computeIfAbsent(aabb, (p_192973_) -> {
            return !this.level.noCollision(this.mob, aabb);
        });
    }

    public @NotNull BlockPathTypes getBlockPathType(@NotNull BlockGetter blockGetter, int x, int y, int z, @NotNull Mob mob) {
        BlockPos pos = new BlockPos(x, y, z);
        Block block = blockGetter.getBlockState(pos).getBlock();
        if (isUnbreakable(block)) {
            return BlockPathTypes.BLOCKED;
        } else {
            // Assume all other blocks are walkable unless specific logic needed
            return BlockPathTypes.WALKABLE;
        }
    }

    public BlockPathTypes getBlockPathTypes(BlockGetter blockGetter, int originX, int originY, int originZ, EnumSet<BlockPathTypes> collectedPathTypes, BlockPathTypes defaultPathType, BlockPos entityPosition) {
        // Loop through the dimensions of the entity to check the path types around its position
        for (int offsetX = 0; offsetX < this.entityWidth; ++offsetX) {
            for (int offsetY = 0; offsetY < this.entityHeight; ++offsetY) {
                for (int offsetZ = 0; offsetZ < this.entityDepth; ++offsetZ) {
                    // Calculate the absolute position based on the offset and origin
                    int x = offsetX + originX;
                    int y = offsetY + originY;
                    int z = offsetZ + originZ;

                    // Retrieve the block path type at the current position
                    BlockPathTypes currentBlockType = this.getBlockPathType(blockGetter, x, y, z);

                    // Evaluate the block path type in context of the entity's position
                    currentBlockType = this.evaluateBlockPathType(blockGetter, entityPosition, currentBlockType);

                    // Set the default path type based on the central block of the entity
                    if (offsetX == 0 && offsetY == 0 && offsetZ == 0) {
                        defaultPathType = currentBlockType;
                    }

                    // Add the evaluated path type to the collection
                    collectedPathTypes.add(currentBlockType);
                }
            }
        }

        // Return the default path type calculated from the central position of the entity
        return defaultPathType;
    }

    protected BlockPathTypes evaluateBlockPathType(BlockGetter blockGetter, BlockPos position, BlockPathTypes currentType) {
        // Check if the mob can interact with doors
        boolean canInteractWithDoors = this.canPassDoors();

        // If the block type is a closed wooden door and the entity can open doors, set it as walkable
        if (currentType == BlockPathTypes.DOOR_WOOD_CLOSED && this.canOpenDoors() && canInteractWithDoors) {
            currentType = BlockPathTypes.WALKABLE_DOOR;
        }

        // If the block type is an open door and the entity cannot pass through doors, mark it as blocked
        if (currentType == BlockPathTypes.DOOR_OPEN && !canInteractWithDoors) {
            currentType = BlockPathTypes.BLOCKED;
        }

        // Check if the block type is a rail that's not based on a proper rail block, then mark as unpassable
        if (currentType == BlockPathTypes.RAIL) {
            boolean isBaseRailAbove = blockGetter.getBlockState(position).getBlock() instanceof BaseRailBlock;
            boolean isBaseRailBelow = blockGetter.getBlockState(position.below()).getBlock() instanceof BaseRailBlock;
            if (!isBaseRailAbove && !isBaseRailBelow) {
                currentType = BlockPathTypes.UNPASSABLE_RAIL;
            }
        }

        return currentType;
    }

    protected BlockPathTypes getBlockPathType(Mob mob, BlockPos pos) {
        return this.getCachedBlockType(mob, pos.getX(), pos.getY(), pos.getZ());
    }

    protected BlockPathTypes getCachedBlockType(Mob mob, int x, int y, int z) {
        return this.pathTypesByPosCache.computeIfAbsent(BlockPos.asLong(x, y, z), (p_265015_) -> {
            return this.getBlockPathType(this.level, x, y, z, mob);
        });
    }

    public @NotNull BlockPathTypes getBlockPathType(@NotNull BlockGetter getter, int x, int y, int z) {
        return getBlockPathTypeStatic(getter, new BlockPos.MutableBlockPos(x, y, z));
    }

    public static BlockPathTypes getBlockPathTypeStatic(BlockGetter getter, BlockPos.MutableBlockPos pos) {
        int i = pos.getX();
        int j = pos.getY();
        int k = pos.getZ();
        BlockPathTypes blockpathtypes = getBlockPathTypeRaw(getter, pos);
        if (blockpathtypes == BlockPathTypes.OPEN && j >= getter.getMinBuildHeight() + 1) {
            BlockPathTypes blockpathtypes1 = getBlockPathTypeRaw(getter, pos.set(i, j - 1, k));
            blockpathtypes = blockpathtypes1 != BlockPathTypes.WALKABLE && blockpathtypes1 != BlockPathTypes.OPEN && blockpathtypes1 != BlockPathTypes.WATER && blockpathtypes1 != BlockPathTypes.LAVA ? BlockPathTypes.WALKABLE : BlockPathTypes.OPEN;
        }

        if (blockpathtypes == BlockPathTypes.WALKABLE) {
            blockpathtypes = checkNeighbourBlocks(getter, pos.set(i, j, k), blockpathtypes);
        }

        return blockpathtypes;
    }

    public static BlockPathTypes checkNeighbourBlocks(BlockGetter getter, BlockPos.MutableBlockPos pos, BlockPathTypes pathTypes) {
        int i = pos.getX();
        int j = pos.getY();
        int k = pos.getZ();

        for(int l = -1; l <= 1; ++l) {
            for(int i1 = -1; i1 <= 1; ++i1) {
                for(int j1 = -1; j1 <= 1; ++j1) {
                    if (l != 0 || j1 != 0) {
                        pos.set(i + l, j + i1, k + j1);
                        BlockState blockstate = getter.getBlockState(pos);
                        BlockPathTypes blockPathType = blockstate.getAdjacentBlockPathType(getter, pos, null, pathTypes);
                        if (blockPathType != null) return blockPathType;
                        FluidState fluidState = blockstate.getFluidState();
                        BlockPathTypes fluidPathType = fluidState.getAdjacentBlockPathType(getter, pos, null, pathTypes);
                        if (fluidPathType != null) return fluidPathType;

                        if (getter.getFluidState(pos).is(FluidTags.WATER)) {
                            return BlockPathTypes.WATER_BORDER;
                        }
                    }
                }
            }
        }

        return pathTypes;
    }

    protected static BlockPathTypes getBlockPathTypeRaw(BlockGetter getter, BlockPos pos) {
        BlockState blockstate = getter.getBlockState(pos);
        BlockPathTypes type = blockstate.getBlockPathType(getter, pos, null);
        if (type != null) return type;
        Block block = blockstate.getBlock();
        if (blockstate.isAir()) {
            return BlockPathTypes.OPEN;
        } else if (!blockstate.is(BlockTags.TRAPDOORS) && !blockstate.is(Blocks.LILY_PAD) && !blockstate.is(Blocks.BIG_DRIPLEAF)) {
            if (blockstate.is(Blocks.POWDER_SNOW)) {
                return BlockPathTypes.POWDER_SNOW;
            } else if (!blockstate.is(Blocks.CACTUS) && !blockstate.is(Blocks.SWEET_BERRY_BUSH)) {
                if (blockstate.is(Blocks.HONEY_BLOCK)) {
                    return BlockPathTypes.STICKY_HONEY;
                } else if (blockstate.is(Blocks.COCOA)) {
                    return BlockPathTypes.COCOA;
                } else if (!blockstate.is(Blocks.WITHER_ROSE) && !blockstate.is(Blocks.POINTED_DRIPSTONE)) {
                    FluidState fluidstate = getter.getFluidState(pos);
                    BlockPathTypes nonLoggableFluidPathType = fluidstate.getBlockPathType(getter, pos, null, false);
                    if (nonLoggableFluidPathType != null) return nonLoggableFluidPathType;
                    if (fluidstate.is(FluidTags.LAVA)) {
                        return BlockPathTypes.LAVA;
                    } else if (isBurningBlock(blockstate)) {
                        return BlockPathTypes.DAMAGE_FIRE;
                    } else if (block instanceof DoorBlock) {
                        DoorBlock doorblock = (DoorBlock)block;
                        if (blockstate.getValue(DoorBlock.OPEN)) {
                            return BlockPathTypes.DOOR_OPEN;
                        } else {
                            return doorblock.type().canOpenByHand() ? BlockPathTypes.DOOR_WOOD_CLOSED : BlockPathTypes.DOOR_IRON_CLOSED;
                        }
                    } else if (block instanceof BaseRailBlock) {
                        return BlockPathTypes.RAIL;
                    } else if (block instanceof LeavesBlock) {
                        return BlockPathTypes.LEAVES;
                    } else if (!blockstate.is(BlockTags.FENCES) && !blockstate.is(BlockTags.WALLS) && (!(block instanceof FenceGateBlock) || blockstate.getValue(FenceGateBlock.OPEN))) {
                        if (!blockstate.isPathfindable(getter, pos, PathComputationType.LAND)) {
                            return BlockPathTypes.BLOCKED;
                        } else {
                            BlockPathTypes loggableFluidPathType = fluidstate.getBlockPathType(getter, pos, null, true);
                            if (loggableFluidPathType != null) return loggableFluidPathType;
                            return fluidstate.is(FluidTags.WATER) ? BlockPathTypes.WATER : BlockPathTypes.OPEN;
                        }
                    } else {
                        return BlockPathTypes.FENCE;
                    }
                } else {
                    return BlockPathTypes.DAMAGE_CAUTIOUS;
                }
            } else {
                return BlockPathTypes.DAMAGE_OTHER;
            }
        } else {
            return BlockPathTypes.TRAPDOOR;
        }
    }

    public static boolean isBurningBlock(BlockState state) {
        return state.is(BlockTags.FIRE) || state.is(Blocks.LAVA) || state.is(Blocks.MAGMA_BLOCK) || CampfireBlock.isLitCampfire(state) || state.is(Blocks.LAVA_CAULDRON);
    }
}
