package com.thomas.shampoo.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.thomas.shampoo.ShampooMod.MODID;

public class BlockInit {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);

    // Register a simple block with properties similar to iron ore
    public static final RegistryObject<Block> THOMAS_ORE = BLOCKS.register("thomas_ore",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE)));
}
