package com.thomas.shampoo.block

import com.thomas.shampoo.ShampooMod
import net.minecraft.util.valueproviders.UniformInt
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.DropExperienceBlock
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import net.minecraftforge.registries.RegistryObject

object BlockInit {
  val BLOCKS: DeferredRegister<Block> =
      DeferredRegister.create(ForgeRegistries.BLOCKS, ShampooMod.Companion.MODID)

  // Register a simple block with properties similar to iron ore
  @JvmField
  val THOMAS_ORE: RegistryObject<Block> =
      BLOCKS.register(
          "thomas_ore",
      ) {
        Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE))
      }

  @JvmField
  val JADE_ORE: RegistryObject<Block> =
      BLOCKS.register(
          "jade_ore",
          {
            DropExperienceBlock(
                BlockBehaviour.Properties.copy(Blocks.DIAMOND_ORE),
                UniformInt.of(4, 7),
            )
          },
      )

  @JvmField
  val JADE_PYRAMID: RegistryObject<Block> =
      BLOCKS.register(
          "jade_pyramid",
      ) {
        JadePyramidBlock(
            BlockBehaviour.Properties.copy(Blocks.STONE).isRedstoneConductor { _, _, _ -> false }
        )
      }

  @JvmField
  val DEEP_SUBSTRATE_FOLIATED_KALKITE: RegistryObject<Block> =
      BLOCKS.register(
          "deep_substrate_foliated_kalkite",
      ) {
        Block(BlockBehaviour.Properties.copy(Blocks.DIAMOND_ORE))
      }
}
