package com.thomas.shampoo;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod(ShampooMod.MODID)
public class ShampooMod
{
    public static final String MODID = "shampoo"; // Define mod ID
    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);

    // Register a simple block with properties similar to stone
    public static final RegistryObject<Block> THOMAS_ORE = BLOCKS.register("thomas_ore",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE))); // Map color

    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    public static final RegistryObject<Item> THOMAS_ORE_ITEM = ITEMS.register("thomas_ore",
            () -> new BlockItem(THOMAS_ORE.get(), new Item.Properties()));

    public ShampooMod()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        BLOCKS.register(modEventBus); // Register blocks to the mod event bus
        ITEMS.register(modEventBus);
    }

    @SubscribeEvent
    public static void onBuildCreativeTab(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
            event.accept(THOMAS_ORE_ITEM.get());
            event.accept(THOMAS_ORE.get());
        }
    }
}
