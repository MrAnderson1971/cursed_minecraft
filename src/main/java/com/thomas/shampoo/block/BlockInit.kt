package com.thomas.shampoo.block;

import net.minecraft.core.BlockPos;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.thomas.shampoo.ShampooMod.MODID;

public class BlockInit {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);

    // Register a simple block with properties similar to iron ore
    public static final RegistryObject<Block> THOMAS_ORE = BLOCKS.register("thomas_ore",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE)));

    public static final RegistryObject<Block> JADE_ORE = BLOCKS.register("jade_ore",
            () -> new DropExperienceBlock(
                    BlockBehaviour.Properties.copy(Blocks.DIAMOND_ORE), UniformInt.of(4, 7)
            ));

    public static final RegistryObject<Block> JADE_PYRAMID = BLOCKS.register("jade_pyramid",
            () -> new JadePyramidBlock(BlockBehaviour.Properties.copy(Blocks.STONE).isRedstoneConductor((BlockState p_50806_, BlockGetter p_50807_, BlockPos p_50808_) -> false)));

    public static final RegistryObject<Block> DEEP_SUBSTRATE_FOLIATED_KALKITE = BLOCKS.register("deep_substrate_foliated_kalkite",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.DIAMOND_ORE)));
}
