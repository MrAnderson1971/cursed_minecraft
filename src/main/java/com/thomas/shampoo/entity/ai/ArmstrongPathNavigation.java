package com.thomas.shampoo.entity.ai;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;

public class ArmstrongPathNavigation extends GroundPathNavigation {

    public ArmstrongPathNavigation(Mob mob, Level level) {
        super(mob, level);
    }

    @Override
    protected void followThePath() {
        int pathIndex = this.path.getNextNodeIndex();

        // Check only the next segment of the path or a few blocks ahead rather than the entire path segment ahead.
        int checkAheadRange = Math.min(pathIndex + 3, this.path.getNodeCount());  // Check up to 3 nodes ahead for more focused block destruction

        debugWool();
        this.checkAndDestroyBlocks(checkAheadRange);

        super.followThePath();
    }

    private void debugWool() {
        for (int i = path.getNextNodeIndex(); i < path.getNodeCount(); i++) {
            BlockPos nextNodePos;
            try {
                nextNodePos = path.getNode(i).asBlockPos();
            } catch (IndexOutOfBoundsException e) {
                return;
            }
            if (level instanceof ServerLevel sl) {
                sl.sendParticles(ParticleTypes.FLAME, nextNodePos.getX(), nextNodePos.getY(), nextNodePos.getZ(), 1, 0.0D, 0.0D, 0.0D, 0.0D);
            }
        }
    }

    private void checkAndDestroyBlocks(int checkAheadRange) {
        int width = Mth.ceil(this.mob.getBbWidth());
        int height = Mth.ceil(this.mob.getBbHeight());

        for (int i = this.path.getNextNodeIndex(); i < checkAheadRange; ++i) {
            try {
                BlockPos currentPos = this.path.getNode(i).asBlockPos();
                BlockPos nextPos = i + 1 < this.path.getNodeCount() ? this.path.getNode(i + 1).asBlockPos() : null;

                boolean isDiagonal = nextPos != null && currentPos.getX() != nextPos.getX() && currentPos.getZ() != nextPos.getZ();
                int range = isDiagonal ? width : width / 2; // Increase range if moving diagonally

                for (int dx = -range; dx <= range; dx++) {
                    for (int dz = -range; dz <= range; dz++) {
                        for (int dy = 0; dy <= height; dy++) {
                            BlockPos blockToCheck = currentPos.offset(dx, dy, dz);
                            if (shouldDestroyBlock(blockToCheck)) {
                                this.level.destroyBlock(blockToCheck, true);
                            }
                        }
                    }
                }
            } catch (IndexOutOfBoundsException e) {
                return;
            }
        }
    }

    private boolean shouldDestroyBlock(BlockPos pos) {
        BlockState state = this.level.getBlockState(pos);
        return WitherBoss.canDestroy(state) || !state.getFluidState().isEmpty(); // This method should return true for blocks that can be destroyed by the entity.
    }

    @Override
    protected boolean hasValidPathType(BlockPathTypes pathTypes) {
        return pathTypes != BlockPathTypes.OPEN;
    }
}
