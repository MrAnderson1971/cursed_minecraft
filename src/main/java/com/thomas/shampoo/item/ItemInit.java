package com.thomas.shampoo.item;

import com.thomas.shampoo.ShampooMod;
import com.thomas.shampoo.block.BlockInit;
import net.minecraft.world.item.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = ShampooMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ItemInit {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ShampooMod.MODID);

    // Block item for the Thomas ore
    public static final RegistryObject<Item> THOMAS_ORE_ITEM = ITEMS.register("thomas_ore",
            () -> new BlockItem(BlockInit.THOMAS_ORE.get(), new Item.Properties()));

    // Smelted Thomas
    public static final RegistryObject<Item> SMELTED_THOMAS = ITEMS.register("smelted_thomas",
            () -> new Item(new Item.Properties()));

    @SubscribeEvent
    public static void buildContents(BuildCreativeModeTabContentsEvent event) {
        // Put my Thomas Ore in the building blocks creative tab, right after acacia log.
        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
            event.getEntries().putAfter(Items.ACACIA_LOG.getDefaultInstance(), THOMAS_ORE_ITEM.get().getDefaultInstance(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        }
    }
}
