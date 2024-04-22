package com.thomas.shampoo;

import com.thomas.shampoo.item.ItemInit;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import com.thomas.shampoo.block.*;

@Mod(ShampooMod.MODID)
public class ShampooMod
{
    public static final String MODID = "shampoo"; // Define mod ID

    public ShampooMod()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ItemInit.ITEMS.register(modEventBus);
        BlockInit.BLOCKS.register(modEventBus);
    }


}
